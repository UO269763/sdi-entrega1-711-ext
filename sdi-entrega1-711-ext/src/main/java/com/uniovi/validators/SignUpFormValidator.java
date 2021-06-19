
package com.uniovi.validators;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class SignUpFormValidator implements Validator {
	@Autowired
	private UsersService usersService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	/**
	 * Método para validar los campos del formulario de logueo
	 */
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
		if (!user.getEmail().contains("@")) { //valida que el email contenga la letra @
			errors.rejectValue("email", "Error.signup.email.arroba");
		}
		if (usersService.getUserByEmail(user.getEmail()) != null) { //valida que el email no exista en el sistema
			System.out.print(usersService.getUserByEmail(user.getEmail()));
			errors.rejectValue("email", "Error.signup.email.duplicate");
		}
		if (user.getName().length() < 5 || user.getName().length() > 24) { //valida que el nombre esté entre 5 y 24 caracteres
			errors.rejectValue("name", "Error.signup.name.length");
		}
		if (user.getLastName().length() < 5 || user.getLastName().length() > 24) { //valida que el apellido esté entre 5 y 24 caracteres
			errors.rejectValue("lastName", "Error.signup.lastName.length");
		}
		if (user.getPassword().length() < 5 || user.getPassword().length() > 24) { //valida que el password esté entre 5 y 24 caracteres
			errors.rejectValue("password", "Error.signup.password.length");
		}
		if (!user.getPasswordConfirm().equals(user.getPassword())) { //valida que las contraseñas coincidan
			errors.rejectValue("passwordConfirm", "Error.signup.passwordConfirm.coincidence");
		}
	}
}
