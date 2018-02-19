package br.com.hora.struct.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.hora.struct.annotations.recognition.ClassRecognition;
import br.com.hora.struct.annotations.recognition.DefaultClassRecognition;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JArchyClass {
	
	Class<? extends ClassRecognition> classificationMethod() default DefaultClassRecognition.class;
}
