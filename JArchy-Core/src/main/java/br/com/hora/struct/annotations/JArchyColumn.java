package br.com.hora.struct.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.hora.struct.enums.InstanceMethod;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JArchyColumn {

	String[] columns() default "";

	InstanceMethod instanceMethod() default InstanceMethod.DEFAULT;

	// TODO: Class<? extends ValueRecognition> recognitionMethod() default DefaultValueRecognition.class;

}
