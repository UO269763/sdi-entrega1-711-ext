package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Oferta;
import com.uniovi.entities.User;
import com.uniovi.services.OfertaService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.OfertaValidator;

@Controller
public class OfertasController {

	@Autowired
	private UsersService usersService;

	@Autowired // Inyectar el servicio
	private OfertaService ofertasService;

	@Autowired
	private OfertaValidator ofertaValidator;

	@Autowired
	private HttpSession httpSession;

	private static final Logger logger = LogManager.getLogger(OfertasController.class);

	@RequestMapping(value="/oferta/list", method = RequestMethod.GET)
	public String getList(Model model, Pageable pageable, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		Page<Oferta> ofertas = new PageImpl<Oferta>(new LinkedList<Oferta>());
		httpSession.setAttribute("dinero", user.getDinero());
		ofertas = ofertasService.getOfertas(pageable, user);
		model.addAttribute("ofertaList", ofertas.getContent());
		model.addAttribute("page", ofertas);
		return "oferta/list";
	}

	@RequestMapping(value = "/oferta/search", method = RequestMethod.GET)
	public String getListado(Model model, Pageable pageable, Principal principal,
			@RequestParam(value = "", required = false) String searchText) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		httpSession.setAttribute("dinero", user.getDinero());
		Page<Oferta> ofertas = new PageImpl<Oferta>(new LinkedList<Oferta>());
		// en caso de que sea una cadena vacía se tienen que listar todas las ofertas de
		// la página
		// en caso de que el searchtext no esté vacío mostrar resultados.
		if (searchText != null && !searchText.isEmpty()) {
			ofertas = ofertasService.searchOfertaByTitulo(pageable, searchText, user);
		} else {
			ofertas = ofertasService.searchAllOfertasUnlessMine(pageable, user);
		}
		model.addAttribute("ofertasList", ofertas.getContent());
		model.addAttribute("page", ofertas);

		return "oferta/search";
	}

	@RequestMapping("/oferta/search/update")
	public String updateListado(Model model, Pageable pageable, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		httpSession.setAttribute("dinero", user.getDinero());
		Page<Oferta> ofertas = new PageImpl<Oferta>(new LinkedList<Oferta>());
		ofertas = ofertasService.searchAllOfertasUnlessMine(pageable, user);
		model.addAttribute("ofertasList", ofertas.getContent());
		return "oferta/search :: tableOfertas";
	}

	@RequestMapping(value = "/oferta/add")
	public String getOferta(Model model, Pageable pageable) {
		// Cambiar metodo usersService a pageable
		model.addAttribute("usersList", usersService.getUsers());
		model.addAttribute("oferta", new Oferta());
		return "oferta/add";
	}

	@RequestMapping(value = "/oferta/add", method = RequestMethod.POST)
	public String setOferta(@Validated Oferta oferta, BindingResult result, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		httpSession.setAttribute("dinero", user.getDinero());
		ofertaValidator.validate(oferta, result);
		if (result.hasErrors()) {
			return "oferta/add";
		}
		ofertasService.addOferta(oferta, user);
		logger.debug(String.format("Oferta añadida", user.getEmail()));
		return "redirect:/oferta/list";
	}

	@RequestMapping(value="/oferta/delete/{id}", method = RequestMethod.GET)
	public String deleteOferta(@PathVariable Long id) {
		ofertasService.deleteOferta(id);
		return "redirect:/oferta/list";
	}

	@RequestMapping("/oferta/comprar/{id}")
	public String comprarOferta(@PathVariable Long id, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		double precioOferta = ofertasService.precioOferta(id);
		double dineroUsuario = user.getDinero();
		if (precioOferta <= dineroUsuario) {
			ofertasService.comprarOfertaIdOf(id, user);
			logger.debug(String.format("Oferta comprada", user.getEmail()));
			return "redirect:/oferta/search";
		}
		return "redirect:/oferta/search";
	}

	@RequestMapping("/oferta/listCompras")
	public String getListCompras(Model model, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		httpSession.setAttribute("dinero", user.getDinero());
		model.addAttribute("ofertaList", user.getOfertasCompradas());
		return "oferta/listCompras";
	}
}
