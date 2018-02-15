package br.com.hora.struct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JArchyCell {
	Object value;
	Class<?> type;
	
	public JArchyCell(Object object) {
		this.type = object.getClass();
		this.value = object;
	}
}
