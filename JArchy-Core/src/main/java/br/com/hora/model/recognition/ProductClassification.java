package br.com.hora.model.recognition;

import java.util.HashMap;
import java.util.Map;

import br.com.hora.struct.JArchyCell;
import br.com.hora.struct.JArchyRow;
import br.com.hora.struct.annotations.recognition.ClassRecognition;

public class ProductClassification implements ClassRecognition {

	public Boolean isClass(JArchyRow mapColumnCell, String variableName) {
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
