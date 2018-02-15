package br.com.hora.annotations.recognition;

import java.util.LinkedHashMap;

import br.com.hora.struct.JArchyCell;

public class DefaultClassRecognition implements ClassRecognition {

	public boolean isClass(LinkedHashMap<String, JArchyCell> mapColumnCell, String variableName) {
		return false;
	}
	
}
