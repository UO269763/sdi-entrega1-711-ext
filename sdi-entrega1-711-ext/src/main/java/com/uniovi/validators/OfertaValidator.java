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

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titulo", "error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descripcion", "error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precio", "error.empty");
		if (oferta.getTitulo().length() > MAX_LONG_TITULO)
			errors.rejectValue("title", "error.oferta.title.length");
		
	}

}
