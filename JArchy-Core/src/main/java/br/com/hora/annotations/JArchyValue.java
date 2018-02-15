package br.com.hora.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JArchyValue {
	// TODO: criar método que traduz valor
	String[] columns() default "";
}
