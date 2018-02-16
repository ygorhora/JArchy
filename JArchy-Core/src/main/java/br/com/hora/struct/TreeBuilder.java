package br.com.hora.struct;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import br.com.hora.annotations.JArchyClass;
import br.com.hora.annotations.JArchyColumn;
import br.com.hora.annotations.JArchyValue;
import br.com.hora.annotations.recognition.ClassRecognition;
import lombok.experimental.FieldDefaults;

public class TreeBuilder {

	LinkedHashSet<Object> nodes = new LinkedHashSet<Object>();
	private Class<?> type;
	private final NodeManager manager = new NodeManager();
	private Object instance;
	private HashMap<Object, TreeBuilder> classificationInstancesChilds = new HashMap<Object, TreeBuilder>();
	private HashMap<String, TreeBuilder> columnInstances = new HashMap<String, TreeBuilder>();
	private Object lastInstanceInserted;

	public TreeBuilder(Class<?> type) {
		this.type = type;
	}
	
	private TreeBuilder(Object instance) {
		this.instance = instance;
	}

	public boolean add(JArchyRow row) {
		Object instance;
		if(this.instance == null) {
			// column class
			instance = this.manager.getInstance(this.type, row);
		} else {
			// classification
			instance = this.instance;
		}

		setFieldValues(instance, row);

		List<Object> relatedClassificationInstances = createClassificationInstances(instance, row);
		walkThroughClassification(relatedClassificationInstances, row);
		
		createColumnInstances(instance, row);
		
		if(this.nodes.contains(instance)){
			return false;
		} else {
			this.nodes.add(instance);
			this.lastInstanceInserted = instance;
			return true;
		}
	}
	
	public Object lastInstanceInserted() {
		return this.lastInstanceInserted;
	}

	private void createColumnInstances(Object instance, JArchyRow row) {
		List<Field> listFields = getAllListFields(instance);
		List<Field> columnListFields = filterListFieldsWithColumnAnnotation(listFields);
		
		for(Field field: columnListFields) {
			ParameterizedType param = (ParameterizedType) field.getGenericType();
			Class<?> clazz = (Class<?>) param.getActualTypeArguments()[0];
			
			TreeBuilder subtree;
			
			field.setAccessible(true);
			
			if(this.columnInstances.containsKey(field.getName())) {
				subtree = this.columnInstances.get(field.getName());
				boolean isNew = subtree.add(row);
				
				List<Object> list = null;
				try {
					list = (List<Object>) field.get(instance);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(isNew) {
					list.add(subtree.lastInstanceInserted()); // only add to list
				}
			} else {
				subtree = new TreeBuilder(clazz);
				this.columnInstances.put(field.getName(), subtree);
				subtree.add(row);
				
				LinkedHashSet<Object> children = subtree.getTree(); 
				
				// Convert to list of object
				List<Object> listChildren = new ArrayList<Object>();
				for(Object child: children) {
					listChildren.add(child);
				}
				
				try {
					if(listChildren.size() > 0) {
						field.set(instance, listChildren); // create list for field
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // field is a List
			}
		}
	}

	private List<Field> filterListFieldsWithColumnAnnotation(List<Field> listFields) {
		List<Field> columnListFields = new ArrayList<Field>();
		for(Field field: listFields) {
			ParameterizedType param = (ParameterizedType) field.getGenericType();
			Class<?> clazz = (Class<?>) param.getActualTypeArguments()[0];
			if(clazz.getDeclaredAnnotation(JArchyColumn.class) != null) {
				columnListFields.add(field);
			}
		}
		return columnListFields;
	}

	private List<Field> getAllListFields(Object instance) {
		List<Field> allFields = Arrays.asList(instance.getClass().getDeclaredFields());
		List<Field> listFields = new ArrayList<Field>();
		for(Field field: allFields) {
			if(field.getType() == List.class) {
				listFields.add(field);
			}
		}
		return listFields;
	}

	private void walkThroughClassification(List<Object> relatedClassificationInstances, JArchyRow row) {
		for(Object instance : relatedClassificationInstances) {
			try {
				if(classificationInstancesChilds.containsKey(instance)) {
					classificationInstancesChilds.get(instance).add(row);
				} else {
					TreeBuilder subtree = new TreeBuilder(instance);
					classificationInstancesChilds.put(instance, subtree);
					subtree.add(row);
				}				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	private List<Object> createClassificationInstances(Object instance, JArchyRow row) {
		List<Field> fieldsToLink = FieldUtils.getFieldsListWithAnnotation(instance.getClass(), JArchyClass.class);
		List<Object> classificationInstances = new ArrayList<Object>();
		
		for (Field field : fieldsToLink) {
			Class<? extends ClassRecognition> classificationClass = getClassificationMethod(field);
			String variableName = field.getName();
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
				field.setAccessible(true);
				fieldValue = field.get(instance);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (belong) {
				if(fieldValue == null) {
					classificationInstances.add(createNewClassificationInstance(instance, field));
				} else {
					classificationInstances.add(fieldValue);
				}
			}
		}
		
		return classificationInstances;
	}

	private Object createNewClassificationInstance(Object instance, Field field) {
		Object newClassificationInstance = null;
		try {
			newClassificationInstance = field.getType().newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			field.setAccessible(true);
			field.set(instance, newClassificationInstance);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newClassificationInstance;
	}

	private Class<? extends ClassRecognition> getClassificationMethod(Field field) {
		JArchyClass annotation = field.getDeclaredAnnotation(JArchyClass.class);
		return annotation.classificationMethod();
	}

	/**
	 * Set value for all fields annotated with JArchyValue.
	 */
	private void setFieldValues(Object instance, JArchyRow row) {
		List<Field> fieldsToSetValue = FieldUtils.getFieldsListWithAnnotation(instance.getClass(), JArchyValue.class);

		for (Field field : fieldsToSetValue) {
			List<String> columns = getColumnsForField(field);

			if (columns.size() > 1) {
				// TODO: treat with value converter to set field. Create a Default
				// Transformation
			} else if (columns.size() == 1) {
				String column = columns.get(0);
				JArchyCell cell = row.get(column);
				setFieldValue(instance, field, cell);
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

	public LinkedHashSet<Object> getTree() {
		return this.nodes;
	}
}
