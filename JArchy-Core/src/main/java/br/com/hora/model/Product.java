package br.com.hora.model;

import java.util.List;

import br.com.hora.struct.annotations.JArchyValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	@JArchyValue(columns = {"product"})
	String name;
	
	List<ProductInformation> listInformation;
	
	public String toString() {
		return this.name;
	}
}
