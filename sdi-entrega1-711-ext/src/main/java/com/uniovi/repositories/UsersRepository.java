package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {

	/**
	 * Busca al usuario por email
	 * 
	 * @param email
	 * @return
	 */
	User findByEmail(String email);
}
