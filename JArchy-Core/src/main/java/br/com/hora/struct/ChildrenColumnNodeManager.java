package br.com.hora.struct;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;

public class ChildrenColumnNodeManager {
	private HashMap<String, TreeBuilder> fieldToJArchyColumnTree = new HashMap<String, TreeBuilder>();
	
	public void constructTreeBuilderJArchyColumnInstance(Field field, JArchyRow row, Object rootInstance) {
		field.setAccessible(true);
		Class<?> type = getListType(field);
		
		if(!this.fieldToJArchyColumnTree.containsKey(field.getName())) {
			TreeBuilder subtree = new TreeBuilder(type);
			this.fieldToJArchyColumnTree.put(field.getName(), subtree);
			subtree.add(row);

			List<Object> children = subtree.getTree();

			try {
				field.set(rootInstance, children); // consider field as always List
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			TreeBuilder subtree = this.fieldToJArchyColumnTree.get(field.getName());
			subtree.add(row);
		}
	}
	
	private Class<?> getListType(Field field) {
		ParameterizedType param = (ParameterizedType) field.getGenericType();
		return (Class<?>) param.getActualTypeArguments()[0];
	}
}
