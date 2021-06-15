package com.uniovi.controllers;

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
	
	private static final Logger logger = LogManager.getLogger(UsersController.class);

	@Autowired
	private RolesService rolesService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private HttpSession httpSession;

	// REGISTRO GET
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	// REGISTRO POST
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

	// INICIO DE SESIÓN
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, @RequestParam(value = "error", required = false) String error) {
		// model.addAttribute("user", new User());
		model.addAttribute("error", error);
		return "login";
	}

	// PÁGINA PRINCIPAL
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model, @ModelAttribute User user) {
		// HttpSession
		model.addAttribute("user", user);
		httpSession.setAttribute("dinero", user.getDinero());
		return "home";
	}

	// AÑADIR NUEVO USUARIO GET
	@RequestMapping(value = "/user/add")
	public String getUser(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		return "user/add";
	}

	// AÑADIR NUEVO USUARIO POST
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String setUser(@ModelAttribute User user) {
		usersService.addUser(user);
		return "redirect:/user/list";
	}

	// OBTENCIÓN DEL LISTADO
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

	@RequestMapping("/user/delete/{id}")
	public String delete(@PathVariable Long id) {
		usersService.deleteUser(id);
		logger.debug(String.format("User eliminado", usersService.getUser(id).getEmail() ));
		return "redirect:/user/list";
	}

}
