package br.com.hora.struct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import br.com.hora.struct.annotations.JArchyColumn;

public class ColumnNodeManager {
	private Multimap<List<String>, Object> identifierToInstance;

	public ColumnNodeManager() {
		this.identifierToInstance = ArrayListMultimap.create();
	}

	public Object getInstance(Class<?> type, JArchyRow row) {
		JArchyColumn columnAnnotation = type.getDeclaredAnnotation(JArchyColumn.class);
		List<String> identifier = getIdentifier(columnAnnotation, row);
		Object instance = null;
		
		try {
			switch (columnAnnotation.instanceMethod()) {
			case ALL_DISTINCT:
				if (!this.identifierToInstance.containsKey(identifier)) {
					instance = Class.forName(type.getCanonicalName()).newInstance();
					this.identifierToInstance.put(identifier, instance);
					return instance;
				} else {
					return this.identifierToInstance.get(identifier).toArray()[0];
				}
			case DEFAULT:
			default:
				instance = Class.forName(type.getCanonicalName()).newInstance();
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
