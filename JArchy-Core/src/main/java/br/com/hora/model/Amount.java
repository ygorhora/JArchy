package br.com.hora.model;

import br.com.hora.struct.annotations.JArchyColumn;
import br.com.hora.struct.annotations.JArchyValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JArchyColumn(columns = {"amount"})
public class Amount {
	@JArchyValue(columns = {"amount"})
	Integer amount;
	
	public String toString() {
		return this.amount.toString();
	}
}
