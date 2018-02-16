package br.com.hora.struct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JArchyCell {
	String value;
	Class<?> type;
	
	public JArchyCell(String value, Class<?> clazz) {
		this.value = value;
		this.type = clazz;
	}
}
