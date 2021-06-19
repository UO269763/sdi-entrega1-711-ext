package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Oferta;

@Component
public class OfertaValidator implements Validator{
	
	private static final int MAX_LONG_TITULO = 38;

	@Override
	public boolean supports(Class<?> clazz) {
		return Oferta.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Oferta oferta = (Oferta) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titulo", "error.empty"); //valida que el titulo no esté en blanco
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descripcion", "error.empty"); //valida que la descrp. no esté en blanco
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precio", "error.empty"); //valida que el precio no esté en blanco
		if (oferta.getTitulo().length() > MAX_LONG_TITULO) //valida que la longitud del titulo de la oferta sea menor que 38 caracteres
			errors.rejectValue("title", "error.oferta.title.length"); 
		
	}

}
