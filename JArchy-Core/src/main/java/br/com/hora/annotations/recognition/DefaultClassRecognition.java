package br.com.hora.annotations.recognition;

import br.com.hora.struct.JArchyRow;

public class DefaultClassRecognition implements ClassRecognition {

	public boolean isClass(JArchyRow mapColumnCell, String variableName) {
		return false;
	}
}
