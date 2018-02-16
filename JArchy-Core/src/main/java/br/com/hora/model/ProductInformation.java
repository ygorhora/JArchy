package br.com.hora.model;

import java.util.List;

import br.com.hora.annotations.JArchyColumn;
import br.com.hora.annotations.JArchyValue;
import br.com.hora.enums.InstanceMethod;
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
	
	List<Expiration> listExpiration;
	
	public String toString() {
		return this.accessibility + " - " + this.quality;
	}
}
