package br.com.hora.struct;

import java.util.HashMap;
import java.util.List;

public class JArchyRow {
	
	HashMap<String, JArchyCell> columnCell;

	public JArchyRow(List<String> headers, List<Object> list) {
		if(headers.size() != list.size())
			throw new RuntimeException("Headers and content list differ in size.");
		
		this.columnCell = new HashMap<String, JArchyCell>();
		
		for(int i = 0; i < headers.size(); ++i) {
			String header = headers.get(i);
			JArchyCell cell = createCell(list.get(i));
			this.columnCell.put(header, cell);
		}
	}

	private JArchyCell createCell(Object elem) {
		return new JArchyCell(elem);
	}

}
