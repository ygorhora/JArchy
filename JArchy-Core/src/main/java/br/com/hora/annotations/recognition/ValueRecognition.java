package br.com.hora.annotations.recognition;

import java.util.LinkedHashMap;

import br.com.hora.struct.JArchyCell;

public interface ValueRecognition {
	
	/**
	 * @param mapColumnCell maps column name to its characteristic
	 * @return a string representing its unique identifier
	 */
	String getIdentierValue(LinkedHashMap<String, JArchyCell> mapColumnCell);
}
