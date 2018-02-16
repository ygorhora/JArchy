package br.com.hora.struct;

import br.com.hora.annotations.JArchyColumn;

public class ColumnNode {
	private JArchyColumn columnAnnotation;
	private JArchyRow row;
	private Class<?> type;

	public ColumnNode(Class<?> type, JArchyRow row) {
		NodeManager nm = new NodeManager(type, row);
		nm.getInstance();
		
	}
}
