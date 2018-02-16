package br.com.hora.struct;

import java.util.HashMap;

public class NodeManager {

	private static HashMap<Class<?>, Node> classToNode = new HashMap<Class<?>, Node>();
	
	public static Object getInstance(Class<?> type, JArchyRow row) {
		if (!classToNode.containsKey(type)) {
			Node node = new Node();
			Object instance = node.createInstance(row, type);
			classToNode.put(type, node);
			return instance;
		} else {
			Node node = classToNode.get(type);
			return node.createInstance(row, type);
		}
	}
}
