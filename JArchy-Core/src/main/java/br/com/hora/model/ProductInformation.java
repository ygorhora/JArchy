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
@JArchyColumn(columns = {"accessibility", "quality"}, instanceMethod = InstanceMethod.ALL_DISTINCT)
public class ProductInformation {
	@JArchyValue(columns = {"accessibility"})
	String accessibility;
	
	@JArchyValue(columns = {"quality"})
	String quality;
	
	Integer totalAmount;
	
	List<Expiration> listExpiration;
	
	@JArchyPostProcessing
	void processTotalAmount() {
		this.totalAmount = 0;
		if(listExpiration != null) {
			for(Expiration exp: listExpiration) {
				this.totalAmount += exp.getTotalAmount();
			}
		}
	}
	
	public String toString() {
		return this.accessibility + " - " + this.quality;
	}
}
