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
	private UsersService usersService; // servicio de usuarios

	@Autowired
	private OfertaService ofertasService; // servicio de ofertas

	@Autowired
	private OfertaValidator ofertaValidator; // validador de ofertas

	@Autowired
	private HttpSession httpSession; // httpSession

	private static final Logger logger = LogManager.getLogger(OfertasController.class); // logger para registrar eventos

	/**
	 * Obtiene el listado de ofertas de las que es dueño el usuario logueado
	 * 
	 * @param model
	 * @param pageable
	 * @param principal
	 * @return oferta/list
	 */
	@RequestMapping(value = "/oferta/list", method = RequestMethod.GET)
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

	/**
	 * Recarga la tabla cuando un usuario destaca una oferta propia
	 * 
	 * @param model
	 * @param pageable
	 * @param principal
	 * @return oferta/list :: tableOfertasList
	 */
	@RequestMapping(value = "/oferta/list/update", method = RequestMethod.GET)
	public String getListUpdate(Model model, Pageable pageable, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		httpSession.setAttribute("dinero", user.getDinero());
		Page<Oferta> ofertas = new PageImpl<Oferta>(new LinkedList<Oferta>());
		ofertas = ofertasService.getOfertas(pageable, user);
		model.addAttribute("ofertaList", ofertas.getContent());
		return "oferta/list :: tableOfertasList";

	}

	/**
	 * Obtiene el listado de ofertas de otros usuarios, excepto las del propio
	 * usuario, además tiene una búsqueda y permite comprar las ofertas en caso de
	 * que estén disponibles (ya sea porque ya han sido compradas por otro usuario o
	 * por nosotros o por no tener saldo suficiente)
	 * 
	 * @param model
	 * @param pageable
	 * @param principal
	 * @param searchText
	 * @return oferta/search
	 */
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

	/**
	 * Obtiene el listado anterior de ofertas de otros usuarios actualizado, para
	 * cuando un usuario compra una oferta y hay que actualizar
	 * 
	 * @param model
	 * @param pageable
	 * @param principal
	 * @return oferta/search :: tableOfertas
	 */
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

	/**
	 * Retorna la plantilla para añadir una oferta
	 * 
	 * @param model
	 * @param pageable
	 * @return oferta/add
	 */
	@RequestMapping(value = "/oferta/add")
	public String getOferta(Model model, Pageable pageable) {
		model.addAttribute("oferta", new Oferta());
		return "oferta/add";
	}

	/**
	 * Metodo post para cuando se añade una oferta, retorna el listado de ofertas
	 * propias en caso de success y un error si algún campo está mal rellenado
	 * 
	 * @param oferta
	 * @param result
	 * @param principal
	 * @return redirect:/oferta/list
	 */
	@RequestMapping(value = "/oferta/add", method = RequestMethod.POST)
	public String setOferta(@Validated Oferta oferta, BindingResult result, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		httpSession.setAttribute("dinero", user.getDinero());
		ofertaValidator.validate(oferta, result);
		if (result.hasErrors()) {
			return "oferta/add";
		}
		if (oferta.getDestacada()) {
			if (user.getDinero()>=20) {
				logger.debug(String.format("Oferta añadida como destacada", user.getEmail()));
				user.destacarOferta();
				usersService.editUser(user);
			}
			
		}
		ofertasService.addOferta(oferta, user);
		httpSession.setAttribute("dinero", user.getDinero());
		logger.debug(String.format("Oferta añadida", user.getEmail()));
		return "redirect:/oferta/list";
	}

	/**
	 * Método para borrar una oferta desde el listado de ofertas propias,
	 * redirecciona a oferta/list, es decir al mismo sitio
	 * 
	 * @param id
	 * @return oferta/list
	 */
	@RequestMapping(value = "/oferta/delete/{id}", method = RequestMethod.GET)
	public String deleteOferta(@PathVariable Long id) {
		ofertasService.deleteOferta(id);
		return "redirect:/oferta/list";
	}

	/**
	 * Método para comprar una oferta desde /oferta/search que nos redirecciona al
	 * mismo sitio
	 * 
	 * @param id
	 * @param principal
	 * @return redirect:/oferta/search
	 */
	@RequestMapping("/oferta/comprar/{id}")
	public String comprarOferta(@PathVariable Long id, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		double precioOferta = ofertasService.precioOferta(id);
		double dineroUsuario = user.getDinero();
		if (precioOferta <= dineroUsuario) {
			ofertasService.comprarOfertaIdOf(id, user);
			logger.debug(String.format("Oferta comprada", user.getEmail()));
		}
		return "redirect:/oferta/search";
	}

	/**
	 * Método encargado de comprar una oferta destacada en la vista de home en caso
	 * de que el usuario tenga dinero Redirecciona a home
	 * 
	 * @param model
	 * @param principal
	 * @param id
	 * @return redirect:/home
	 */
	@RequestMapping(value = "/home/oferta/comprar/{id}", method = RequestMethod.GET)
	public String comprarOfertaHome(Model model, Principal principal, @PathVariable Long id) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		double precioOferta = ofertasService.precioOferta(id);
		double dineroUsuario = user.getDinero();
		if (precioOferta <= dineroUsuario) {
			ofertasService.comprarOfertaIdOf(id, user);
			logger.debug(String.format("Oferta comprada", user.getEmail()));
		}
		return "redirect:/home";
	}

	/**
	 * Retorna la lista de compras del usuario logueado
	 * 
	 * @param model
	 * @param principal
	 * @return oferta/listCompras
	 */
	@RequestMapping("/oferta/listCompras")
	public String getListCompras(Model model, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		httpSession.setAttribute("dinero", user.getDinero());
		model.addAttribute("ofertaList", user.getOfertasCompradas());
		return "oferta/listCompras";
	}

	/**
	 * Marca una oferta como destacada en caso de que el usuario logueado tenga el
	 * dinero suficiente (20 euros)
	 * 
	 * @param model
	 * @param id
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/oferta/destacada/{id}", method = RequestMethod.GET)
	public String setDestacada(@PathVariable Long id, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		if (user.getDinero() >= 20) { // si el usuario tiene dinero suficiente se marca como destacada
			user.destacarOferta();
			httpSession.setAttribute("dinero", user.getDinero());
			usersService.editUser(user);
			ofertasService.setOfertaDestacada(true, id);
		}
		return "redirect:/oferta/list";

	}

	/**
	 * Marca una oferta como no destacada
	 * 
	 * @param model
	 * @param id
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "oferta/nodestacada/{id}", method = RequestMethod.GET)
	public String setNoDestacada(@PathVariable Long id) {
		ofertasService.setOfertaDestacada(false, id);
		return "redirect:/oferta/list";
	}

	/**
	 * Metodo que recarga el saldo cuando un usuario compra/destaca una oferta
	 * 
	 * @param model
	 * @param pageable
	 * @param principal
	 * @return
	 */
	@RequestMapping("/oferta/reload/dinero")
	public String updateSaldo(Model model, Pageable pageable, Principal principal) {
		return "oferta/search :: nav";
	}

	/**
	 * Se encarga de recarga la página de home cuando se compra una oferta destacada
	 * 
	 * @param model
	 * @param pageable
	 * @param principal
	 * @return /home/update
	 */
	@RequestMapping("/home/update")
	public String updateHome(Model model, Pageable pageable, Principal principal) {
		String email = principal.getName(); // email es el name de la autenticación
		User user = usersService.getUserByEmail(email);
		httpSession.setAttribute("dinero", user.getDinero());
		Page<Oferta> ofertas = ofertasService.getOfertasDestacadas(pageable, user);
		model.addAttribute("ofertasDestacadasList", ofertas.getContent());
		model.addAttribute("user", user);
		model.addAttribute("page", ofertas);
		return "home :: tablaOfertasDestacadas";
	}

}
