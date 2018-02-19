package br.com.hora.struct;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import br.com.hora.struct.annotations.JArchyClass;
import br.com.hora.struct.annotations.JArchyColumn;
import br.com.hora.struct.annotations.JArchyPostProcessing;
import br.com.hora.struct.annotations.JArchyValue;
import br.com.hora.struct.annotations.recognition.ClassRecognition;
import br.com.hora.struct.enums.TreeNodeType;

public class TreeBuilder {
	private Class<?> type;
	private final ColumnNodeManager columnNodeManager = new ColumnNodeManager();
	private final ClassNodeManager classNodeManager = new ClassNodeManager();
	private final NodeManager nodeManager = new NodeManager();
	private TreeNodeType treeNodeType;
	private Object originInstance;
	private LinkedHashSet<Object> setNodes = new LinkedHashSet<Object>();
	private List<Object> nodes = new ArrayList<Object>();

	public TreeBuilder(Class<?> type) {
		// TODO: raise exception if type was not annotated with JArchyColumn
		this(TreeNodeType.COLUMN, null);
		this.type = type;
	}

	/**
	 * If tree node type is TreeNodeType.COLUMN origin instance can be null.
	 * @param type class of root
	 * @param treeNodeType
	 * @param originInstance
	 */
	public TreeBuilder(TreeNodeType treeNodeType, Object originInstance) {
		if(originInstance != null)
			this.type = originInstance.getClass();
		this.treeNodeType = treeNodeType;
		this.originInstance = originInstance;
	}

	public void add(JArchyRow row) {
		Object rootInstance = null;

		switch (treeNodeType) {
		case COLUMN:
			rootInstance = this.columnNodeManager.getInstance(this.type, row);
			break;
		case CLASS:
			rootInstance = this.originInstance;
			break;
		}

		populateNode(rootInstance, row);
	}

	private void populateNode(Object rootInstance, JArchyRow row) {
		setJArchyValueFields(rootInstance, row);

		constructTreeBuilderForJArchyClassInstances(rootInstance, row);

		constructJArchyColumnInstances(rootInstance, row);

		if (!this.setNodes.contains(rootInstance)) {
			this.setNodes.add(rootInstance);
			this.nodes.add(rootInstance);
		}
	}

	private void constructJArchyColumnInstances(Object rootInstance, JArchyRow row) {
		List<Field> listJArchyColumnFields = getJArchyColumnFieldsList(rootInstance);

		for (Field field : listJArchyColumnFields) {
			this.nodeManager.constructTreeBuilderJArchyColumnInstance(field, row, rootInstance);
		}
	}

	private List<Field> getJArchyColumnFieldsList(Object rootInstance) {
		List<Field> listFields = getAllListFields(rootInstance);
		return filterListFieldsWithJArchyColumn(listFields);
	}

	private List<Field> filterListFieldsWithJArchyColumn(List<Field> listFields) {
		List<Field> columnListFields = new ArrayList<Field>();
		for (Field field : listFields) {
			Class<?> type = getListType(field);
			if (type.getDeclaredAnnotation(JArchyColumn.class) != null) {
				columnListFields.add(field);
			}
		}
		return columnListFields;
	}

	private Class<?> getListType(Field field) {
		ParameterizedType param = (ParameterizedType) field.getGenericType();
		return (Class<?>) param.getActualTypeArguments()[0];
	}

	private List<Field> getAllListFields(Object instance) {
		List<Field> allFields = Arrays.asList(instance.getClass().getDeclaredFields());
		List<Field> listFields = new ArrayList<Field>();
		for (Field field : allFields) {
			if (field.getType() == List.class) {
				listFields.add(field);
			}
		}
		return listFields;
	}

	private void constructTreeBuilderForJArchyClassInstances(Object rootInstance, JArchyRow row) {
		List<Object> instancesJArchyClassInRootInstance = getJArchyClassFieldsInstances(rootInstance, row);
		
		for (Object instanceJArchyClass : instancesJArchyClassInRootInstance) {
			TreeBuilder treeOfJArchyClassInstance = this.classNodeManager.getTree(instanceJArchyClass);
			treeOfJArchyClassInstance.add(row);
		}
	}

	/**
	 * Get all instances of field marked as JArchyClass related to row. Create
	 * instance if necessary.
	 */
	private List<Object> getJArchyClassFieldsInstances(Object rootInstance, JArchyRow row) {
		List<Field> fieldsToLink = FieldUtils.getFieldsListWithAnnotation(rootInstance.getClass(), JArchyClass.class);
		List<Object> instancesJArchyClassInRootInstance = new ArrayList<Object>();

		for (Field field : fieldsToLink) {
			Class<? extends ClassRecognition> classificationClass = getClassificationClass(field);
			String variableName = field.getName();
			field.setAccessible(true);
			Method classificationMethod = null;

			try {
				classificationMethod = ClassUtils.getPublicMethod(classificationClass, "isClass", JArchyRow.class,
						String.class);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			boolean belong = false;

			try {
				belong = (Boolean) classificationMethod.invoke(classificationClass.newInstance(), row, variableName);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Object fieldValue = null;

			try {
				fieldValue = field.get(rootInstance);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (belong) {
				if (fieldValue == null) {
					instancesJArchyClassInRootInstance.add(createJArchyClassInstance(rootInstance, field));
				} else {
					instancesJArchyClassInRootInstance.add(fieldValue);
				}
			}
		}

		return instancesJArchyClassInRootInstance;
	}

	/**
	 * @param rootInstance
	 *            instance that contains field
	 * @param field
	 *            acessible field
	 * @return
	 */
	private Object createJArchyClassInstance(Object rootInstance, Field field) {
		Object newClassificationInstance = null;
		try {
			newClassificationInstance = field.getType().newInstance();
			field.set(rootInstance, newClassificationInstance);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newClassificationInstance;
	}

	private Class<? extends ClassRecognition> getClassificationClass(Field field) {
		JArchyClass annotation = field.getDeclaredAnnotation(JArchyClass.class);
		return annotation.classificationMethod();
	}

	/**
	 * Set value for all fields annotated with JArchyValue.
	 */
	private void setJArchyValueFields(Object rootInstance, JArchyRow row) {
		List<Field> fieldsToSetValue = FieldUtils.getFieldsListWithAnnotation(rootInstance.getClass(),
				JArchyValue.class);

		for (Field field : fieldsToSetValue) {
			List<String> columns = getColumnsForField(field);

			if (columns.size() > 1) {
				// TODO: treat with value converter to set field. Create a Default
				// Transformation
			} else if (columns.size() == 1) {
				String column = columns.get(0);
				JArchyCell cell = row.get(column);
				setFieldValue(rootInstance, field, cell);
			}
		}
	}

	private void setFieldValue(Object instance, Field field, JArchyCell cell) {
		Class<?> type = cell.getType();
		String value = cell.getValue();
		field.setAccessible(true);

		try {
			if (type == Integer.class) {
				field.set(instance, Integer.parseInt(value));
			} else if (type == String.class) {
				field.set(instance, value);
			} else if (type == Double.class) {
				field.set(instance, Double.parseDouble(value));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<String> getColumnsForField(Field field) {
		JArchyValue annotation = field.getDeclaredAnnotation(JArchyValue.class);
		return Arrays.asList(annotation.columns());
	}

	/**
	 * @return a list of nodes.
	 */
	public List<Object> getTree() {
		return this.nodes;
	}

	public void postProcessing() {
		for(Object instance: this.nodes) {
			postProcessing(instance);
		}
	}
	
	private void postProcessing(Object instance) {
		List<Object> neighboringInstances = getNeighboringInstances(instance);
		
		for(Object neighboringInstance : neighboringInstances) {
			postProcessing(neighboringInstance);
		}
		
		applyPostProcessing(instance);
	}

	private void applyPostProcessing(Object instance) {
		for(Method method: getMethodsAnnotatedWith(instance, JArchyPostProcessing.class)) {
			method.setAccessible(true);
			try {
				method.invoke(instance);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static List<Method> getMethodsAnnotatedWith(Object instance, Class<? extends Annotation> annotation) {
	    final List<Method> methods = new ArrayList<Method>();
	    Class<?> type = instance.getClass();
	    
        List<Method> allMethods = new ArrayList<Method>(Arrays.asList(type.getDeclaredMethods()));       
        for (final Method method : allMethods) {
            if (method.isAnnotationPresent(annotation)) {
                // Annotation annotInstance = method.getAnnotation(annotation);
                // TODO process annotInstance
                methods.add(method);
            }
        }
	    return methods;
	}

	private List<Object> getNeighboringInstances(Object instance) {
		List<Object> neighboringInstances = new ArrayList<Object>();
		neighboringInstances.addAll(getNeighboringInstancesJArchyClass(instance));
		neighboringInstances.addAll(getNeighboringInstancesJArchyColumn(instance));
		return neighboringInstances;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object> getNeighboringInstancesJArchyColumn(Object instance) {
		List<Object> neighboringInstancesJArchyColumn = new ArrayList<Object>();
		
		List<Field> fieldsJArchyColumn = getJArchyColumnFieldsList(instance);
		
		for(Field field: fieldsJArchyColumn) {
			field.setAccessible(true);
			Object fieldValue = null;
			try {
				fieldValue = field.get(instance);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(fieldValue != null) {
				neighboringInstancesJArchyColumn.addAll((List<Object>) fieldValue); 
			}
		}

		return neighboringInstancesJArchyColumn;
	}

	private List<Object> getNeighboringInstancesJArchyClass(Object instance) {
		List<Object> neighboringInstancesJArchyClass = new ArrayList<Object>();
		
		List<Field> fieldsJArchyClass = FieldUtils.getFieldsListWithAnnotation(instance.getClass(), JArchyClass.class);
		
		for(Field field: fieldsJArchyClass) {
			field.setAccessible(true);
			Object fieldValue = null;
			try {
				fieldValue = field.get(instance);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(fieldValue != null) {
				neighboringInstancesJArchyClass.add(fieldValue);
			}
		}
		
		return neighboringInstancesJArchyClass;
	}
}
