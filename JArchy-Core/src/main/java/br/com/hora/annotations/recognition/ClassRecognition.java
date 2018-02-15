package br.com.hora.annotations.recognition;

import java.util.LinkedHashMap;

import br.com.hora.struct.JArchyCell;

public interface ClassRecognition {
	/**
	 * @param mapColumnCell maps column name to its characteristic
	 * @param variableName variable being tested
	 * @return
	 */
	boolean isClass(LinkedHashMap<String, JArchyCell> mapColumnCell, String variableName);
}
