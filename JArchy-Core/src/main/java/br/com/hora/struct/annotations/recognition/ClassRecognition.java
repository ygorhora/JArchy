package br.com.hora.struct.annotations.recognition;

import br.com.hora.struct.JArchyRow;

public interface ClassRecognition {
	/**
	 * @param mapColumnCell maps column name to its characteristic
	 * @param variableName variable being tested
	 * @return
	 */
	public Boolean isClass(JArchyRow mapColumnCell, String variableName);
}
