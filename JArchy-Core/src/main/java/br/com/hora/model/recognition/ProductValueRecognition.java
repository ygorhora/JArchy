package br.com.hora.model.recognition;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import br.com.hora.struct.JArchyCell;
import br.com.hora.struct.annotations.recognition.ValueRecognition;

public class ProductValueRecognition implements ValueRecognition {

	public String getIdentierValue(LinkedHashMap<String, JArchyCell> mapColumnCell) {
		JArchyCell cell = mapColumnCell.get("product");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("EC", "type_1");
		map.put("EP", "type_1");
		map.put("PC", "type_2");
		map.put("PP", "type_2");
		
		return map.get(cell.getValue());
	}

}
