package br.com.hora.model;

import br.com.hora.annotations.JArchyClass;
import br.com.hora.app.ProductClassification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommerceProduct {
	@JArchyClass(classificationMethod = ProductClassification.class)
	Product pin;
	
	@JArchyClass(classificationMethod = ProductClassification.class)
	Product euc;
}
