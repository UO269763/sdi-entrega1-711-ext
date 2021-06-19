package com.uniovi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	/**
	 * Retorna index
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String index() {
		return "index";
	}

}