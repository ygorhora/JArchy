package br.com.hora.struct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.com.hora.annotations.JArchyColumn;

public class NodeManager {

	private static HashMap<Class<?>, NodeManager> classToNodeManager = new HashMap<Class<?>, NodeManager>();
	private HashMap<List<String>, Object> identifierToInstance;
	private Class<?> type;
	private JArchyRow row;
	
	public NodeManager(Class<?> type, JArchyRow row) {
		this.type = type;
		this.row = row;
	}
	
	public Object getInstance() {
		if (!classToNodeManager.containsKey(this.type)) {
			NodeManager nm = new NodeManager();
			Object instance = nm.createInstance(this.row, this.type);
			classToNodeManager.put(this.type, nm);
			return instance;
		} else {
			NodeManager nm = classToNodeManager.get(this.type);
			return nm.createInstance(this.row, this.type);
		}
	}

	private NodeManager() {
		this.identifierToInstance = new HashMap<List<String>, Object>();
	}

	private Object createInstance(JArchyRow row, Class<?> type) {
		
		JArchyColumn columnAnnotation = type.getDeclaredAnnotation(JArchyColumn.class);
		List<String> identifier = getIdentifier(columnAnnotation, row);
		Object instance = null;
		
		try {
			switch (columnAnnotation.instanceMethod()) {
				case ALL_DISTINCT:
					if(!this.identifierToInstance.containsKey(identifier)) {
						instance = Class.forName(type.getCanonicalName());
						this.identifierToInstance.put(identifier, instance);
					} else {
						return this.identifierToInstance.get(identifier);
					}
				case DEFAULT:
				default:
					instance = Class.forName(type.getCanonicalName());
					this.identifierToInstance.put(identifier, instance);
			}
		} catch (Exception e) {
			throw new RuntimeException("Class " + type.getName() + " could not be created.");
		}
		
		return instance;
	}

	private List<String> getIdentifier(JArchyColumn columnAnnotation, JArchyRow row) {
		List<String> consideredColumns = Arrays.asList(columnAnnotation.columns());
		List<String> identifier = new ArrayList<String>();

		for (String column : consideredColumns) {
			identifier.add(row.get(column).getValue());
		}

		return identifier;
	}

}
