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
		ColumnNode root = new ColumnNode(this.type, row);		
	}
	
}
