package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository; // repositorio de usuarios

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Metodo de inicio
	 */
	@PostConstruct
	public void init() {
	}

	/**
	 * Retorna los todos los usuarios del repositorio
	 * 
	 * @return
	 */
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}

	/**
	 * Retorna un usuario según su id
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	/**
	 * Añade un usuario al repositorio y encripta la contraseña
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	/**
	 * Borra el usuario del repositorio
	 * 
	 * @param id
	 */
	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}

	/**
	 * Edita el usuario
	 * 
	 * @param user
	 */
	public void editUser(User user) {
		usersRepository.save(user);

	}

	/**
	 * Retorna el usuario según su email
	 * 
	 * @param email
	 * @return user
	 */
	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

}
