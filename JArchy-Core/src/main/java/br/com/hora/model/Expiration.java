package br.com.hora.model;

import java.util.List;

import br.com.hora.annotations.JArchyColumn;
import br.com.hora.enums.InstanceMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JArchyColumn(columns = {"expiration"}, instanceMethod = InstanceMethod.ALL_DISTINCT)
public class Expiration {
	Integer time;
	List<Amount> listAmount;
}
