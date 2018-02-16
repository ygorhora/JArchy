package br.com.hora.struct;

public class ColumnNode {
	
	public static <T> T getColumnNode(Class<T> type, JArchyRow row) {
		return (T) NodeManager.getInstance(type, row);
	}
}
