package br.com.hora.struct;

import java.util.HashMap;
import java.util.List;

public class JArchyRow {
	
	private HashMap<String, JArchyCell> columnCell;

	public JArchyRow(List<String> headers, List<Class<?>> typos, List<String> list) {
		if(headers.size() != list.size())
			throw new RuntimeException("Headers and content list differ in size.");
		
		if(headers.size() != typos.size())
			throw new RuntimeException("Headers and type list differ in size.");
		
		this.columnCell = new HashMap<String, JArchyCell>();
		
		for(int i = 0; i < headers.size(); ++i) {
			String header = headers.get(i);
			JArchyCell cell = createCell(list.get(i), typos.get(i));
			this.columnCell.put(header, cell);
		}
	}
	
	public JArchyCell get(String column) {
		return columnCell.get(column);
	}

	private JArchyCell createCell(String elem, Class<?> clazz) {
		return new JArchyCell(elem, clazz);
	}

}
