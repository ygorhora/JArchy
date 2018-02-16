package br.com.hora.model;

import br.com.hora.annotations.JArchyClass;
import br.com.hora.annotations.JArchyColumn;
import br.com.hora.annotations.JArchyValue;
import br.com.hora.app.ProductDestClassification;
import br.com.hora.enums.InstanceMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JArchyColumn(columns = {"block"}, instanceMethod = InstanceMethod.ALL_DISTINCT)
public class Block {
	
	@JArchyValue(columns = {"block"})
	String code;
	
	@JArchyClass(classificationMethod = ProductDestClassification.class)
	ProcessProduct processProduct;
	
	@JArchyClass(classificationMethod = ProductDestClassification.class)
	CommerceProduct commerceProduct;
	
	public String toString() {
		return this.code;
	}
}
