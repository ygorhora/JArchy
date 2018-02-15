package br.com.hora.model;

import br.com.hora.annotations.JArchyColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JArchyColumn(columns = {"amount"})
public class Amount {
	Integer amount;
}
