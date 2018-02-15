package br.com.hora.struct;

import java.util.Arrays;
import java.util.List;

import br.com.hora.annotations.JArchyColumn;

public class TreeBuilder {

	private final Class<?> type;
	
	public TreeBuilder(Class<?> type) {
		this.type = type;
	}

	public void add(JArchyRow row) {
		JArchyColumn classAnnotation = this.type.getDeclaredAnnotation(JArchyColumn.class);
		
		if(classAnnotation == null) {
			throw new RuntimeException("Annotation JArchyColumn is not defined to class.");
		}
		
		List<String> columns = Arrays.asList(classAnnotation.columns());
		
	}
	
}
