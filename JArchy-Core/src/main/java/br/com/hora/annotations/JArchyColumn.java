package br.com.hora.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.hora.annotations.recognition.DefaultValueRecognition;
import br.com.hora.annotations.recognition.ValueRecognition;
import br.com.hora.enums.InstanceMethod;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JArchyColumn {

	String[] columns() default "";

	InstanceMethod instanceMethod() default InstanceMethod.DEFAULT;

	Class<? extends ValueRecognition> recognitionMethod() default DefaultValueRecognition.class;

}