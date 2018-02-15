package br.com.hora.annotations.recognition;

import java.util.LinkedHashMap;

import br.com.hora.struct.JArchyCell;

public class DefaultValueRecognition implements ValueRecognition {

	public String getIdentierValue(LinkedHashMap<String, JArchyCell> mapColumnCell) {
		String identifier = "";
		for(JArchyCell cell: mapColumnCell.values()) {
			identifier += cell.getValue().toString();
		}
		
		return identifier;
	}

}
