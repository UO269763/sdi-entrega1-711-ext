package com.uniovi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Controller
public class UsersController {

	@Autowired
	private RolesService rolesService; // servicio de roles

	@Autowired
	private UsersService usersService; // servicio de usuarios

	@Autowired
	private SecurityService securityService; // servicio de seguridad

	@Autowired
	private SignUpFormValidator signUpFormValidator; // validador de registro

	@Autowired
	private HttpSession httpSession; // httpSession

	private static final Logger logger = LogManager.getLogger(UsersController.class); // logger para registrar eventos

	/**
	 * Retorna la plantilla de registro y añade el nuevo usuario al modelo
	 * 
	 * @param model
	 * @return signup
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	/**
	 * Método post de signup en el que se loguea al usuario y se guardan todas sus
	 * credenciales. Se redirecciona a home
	 * 
	 * @param user
	 * @param result
	 * @return redirect::home
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		logger.debug(String.format("Auto login", user.getEmail()));
		return "redirect:home";
	}

	/**
	 * Método get del login
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	/**
	 * Método que retorna la página principal de la página web home. Asigna el
	 * dinero y el usuario.
	 * 
	 * @param model
	 * @param user
	 * @param principal
	 * @return home
	 */
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model, @ModelAttribute User user, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		user = usersService.getUserByEmail(email);
		httpSession.setAttribute("dinero", user.getDinero());
		model.addAttribute("user", user);
		return "home";
	}

	/**
	 * Método que obtiene el listado de usuarios del sistema, excepto el admin Desde
	 * este listado se podrá borrar, mediante el uso de checkboxes a los usuarios
	 * que el administrador desee Además esta vista solo es accesible por el
	 * administador
	 * 
	 * @param model
	 * @return user/list
	 */
	@RequestMapping("/user/list")
	public String getListado(Model model) {
		List<User> usuarios = usersService.getUsers();
		List<User> nueva = new ArrayList<>();
		for (User u : usuarios) {
			if (!u.getEmail().contentEquals("admin@email.es")) {
				nueva.add(u);
			}
		}
		model.addAttribute("usersList", nueva);
		return "user/list";
	}

	/**
	 * Método que actualiza el listado anterior, para cuando se elimina un usuario
	 * Simplemente recarga la tabla de usuarios
	 * 
	 * @param model
	 * @return /user/list :: tableUsers
	 */
	@RequestMapping("/user/list/update")
	public String updateList(Model model) {
		List<User> usuarios = usersService.getUsers();
		List<User> nueva = new ArrayList<>();
		for (User u : usuarios) {
			if (!u.getEmail().contentEquals("admin@email.es")) {
				nueva.add(u);
			}
		}
		model.addAttribute("usersList", nueva);
		return "user/list :: tableUsers";
	}

	/**
	 * Método get que borra un usuario del sistema, solo accesible por el admin
	 * Redirecciona al listado de usuarios
	 * 
	 * @param id
	 * @return redirect:/user/list
	 */
	@RequestMapping("/user/delete/{id}")
	public String delete(@PathVariable Long id) {
		usersService.deleteUser(id);
		return "redirect:/user/list";
	}

}
