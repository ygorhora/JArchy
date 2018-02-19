package br.com.hora.model;

import java.util.List;

import br.com.hora.struct.annotations.JArchyColumn;
import br.com.hora.struct.annotations.JArchyPostProcessing;
import br.com.hora.struct.annotations.JArchyValue;
import br.com.hora.struct.enums.InstanceMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JArchyColumn(columns = {"expiration"}, instanceMethod = InstanceMethod.ALL_DISTINCT)
public class Expiration {
	
	@JArchyValue(columns = {"expiration"})
	Integer time;
	
	Integer totalAmount;
	
	List<Amount> listAmount;
	
	@JArchyPostProcessing
	void processTotalAmount() {
		this.totalAmount = 0;
		if(listAmount != null) {
			for(Amount amount: listAmount) {
				this.totalAmount += amount.getAmount();
			}
		}
	}
	
	public String toString() {
		return this.time.toString();
	}
}
