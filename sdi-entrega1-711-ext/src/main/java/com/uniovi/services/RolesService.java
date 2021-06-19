package com.uniovi.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {

	String[] roles = { "ROLE_USER", "ROLE_ADMIN" };

	/**
	 * Retorna el rol del usuario
	 * 
	 * @return
	 */
	public String[] getRoles() {
		return roles;
	}

}
