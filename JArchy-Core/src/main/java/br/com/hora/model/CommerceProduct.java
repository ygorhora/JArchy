package br.com.hora.model;

import br.com.hora.model.recognition.ProductClassification;
import br.com.hora.struct.annotations.JArchyClass;
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
