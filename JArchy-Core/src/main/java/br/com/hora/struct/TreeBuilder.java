package br.com.hora.struct;

import java.util.LinkedHashSet;

public class TreeBuilder<T> {

	LinkedHashSet<T> nodes = new LinkedHashSet<T>();
	private final Class<T> type;
	
	public TreeBuilder(Class<T> type) {
		this.type = type; //TODO: take at runtime.
	}

	public void add(JArchyRow row) {
		T col = (T) NodeManager.getInstance(this.type, row);
		T column = ColumnNode.getColumnNode(this.type, row);
		this.nodes.add(column); // distinct as modeled
		
	}
	
	public LinkedHashSet<T> getTree(){
		return this.nodes;
	}
}
