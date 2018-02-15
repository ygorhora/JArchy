package br.com.hora.app;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import br.com.hora.annotations.recognition.ClassRecognition;
import br.com.hora.struct.JArchyCell;

public class ProductClassification implements ClassRecognition {

	public boolean isClass(LinkedHashMap<String, JArchyCell> mapColumnCell, String variableName) {
		JArchyCell cell = mapColumnCell.get("product"); // cell used to classify
		Map<String, String> map = createRuleClassification(); // rule of classification
		String target = map.get(cell.getValue()); // apply rule to cell value to know target variable

		return variableName.equals(target); // compare with variableName to know if target matches.
	}

	private Map<String, String> createRuleClassification() {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("EC", "euc");
		map.put("PC", "pin");
		map.put("PP", "pin");
		map.put("EP", "euc");
		
		return map;
	}

}
