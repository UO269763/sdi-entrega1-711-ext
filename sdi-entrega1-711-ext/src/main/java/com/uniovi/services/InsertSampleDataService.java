package com.uniovi.services;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Oferta;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {

	@Autowired
	private UsersService usersService;

	@Autowired
	private OfertaService ofertasService;

	@PostConstruct
	public void init() {
		User user1 = new User("pedro@email.es", "Pedro", "Díaz");
		user1.setPassword("123456");
		user1.setRole("ROLE_USER");
		Oferta oferta1 = new Oferta(user1, "Camiseta azul", "Talla L", 20);
		Oferta oferta2 = new Oferta(user1, "Sudadera azul", "Talla M", 10);
		Oferta oferta3 = new Oferta(user1, "Pantalon azul", "Talla XL", 120);

		User user2 = new User("lucas@email.es", "Lucas", "Núñez");
		user2.setPassword("123456");
		user2.setRole("ROLE_USER");
		Oferta oferta4 = new Oferta(user1, "Zapatos nike", "Talla L", 20);
		Oferta oferta5 = new Oferta(user1, "Sudadera nike", "Talla M", 10);
		Oferta oferta6 = new Oferta(user1, "Pantalon nike", "Talla XL", 120);

		User user3 = new User("maria@email.es", "María", "Rodríguez");
		user3.setPassword("123456");
		user3.setRole("ROLE_USER");
		Oferta oferta7 = new Oferta(user1, "Zapatos adidas", "Talla L", 20);
		Oferta oferta8 = new Oferta(user1, "Sudadera adidas", "Talla M", 10);
		Oferta oferta9 = new Oferta(user1, "Pantalon adidas", "Talla XL", 120);

		User user4 = new User("almonte@email.es", "Marta", "Almonte");
		user4.setPassword("123456");
		user4.setRole("ROLE_USER");
		Oferta oferta10 = new Oferta(user1, "Calcetines", "Seminuevos", 2);
		Oferta oferta11 = new Oferta(user1, "Chandal completo", "Talla M", 60);
		Oferta oferta12 = new Oferta(user1, "Chubasquero", "Talla XL", 120);

		User user5 = new User("valdes@email.es", "Pelayo", "Valdes");
		user5.setPassword("123456");
		user5.setRole("ROLE_USER");
		Oferta oferta13 = new Oferta(user1, "Zapatos", "Seminuevos", 2);
		Oferta oferta14 = new Oferta(user1, "Sudadera", "Talla M", 60);
		Oferta oferta15 = new Oferta(user1, "Pantalon", "Talla XL", 120);

		User user6 = new User("admin@email.es", "Edward", "Núñez");
		user6.setPassword("123456");
		user6.setRole("ROLE_ADMIN");
		
		User user7 = new User("marcos@email.es", "Marcos", "Marino");
		user7.setPassword("123456");
		user7.setRole("ROLE_USER");
		Oferta oferta16 = new Oferta(user1, "Gorra", "Seminueva", 10);
		Oferta oferta17 = new Oferta(user1, "Abrigo", "Azul", 90);
		Oferta oferta18 = new Oferta(user1, "Sombrero", "Amarillo", 120);
		// Añadir usuarios al service
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(user7);

		// Añadir ofertas al service
		ofertasService.addOferta(oferta1, user1);
		ofertasService.addOferta(oferta2, user1);
		ofertasService.addOferta(oferta3, user1);

		ofertasService.addOferta(oferta4, user2);
		ofertasService.addOferta(oferta5, user2);
		ofertasService.addOferta(oferta6, user2);

		ofertasService.addOferta(oferta7, user3);
		ofertasService.addOferta(oferta8, user3);
		ofertasService.addOferta(oferta9, user4);

		ofertasService.addOferta(oferta10, user4);
		ofertasService.addOferta(oferta11, user4);
		ofertasService.addOferta(oferta12, user4);

		ofertasService.addOferta(oferta13, user5);
		ofertasService.addOferta(oferta14, user5);
		ofertasService.addOferta(oferta15, user5);
		
		ofertasService.addOferta(oferta16, user7);
		ofertasService.addOferta(oferta17, user7);
		ofertasService.addOferta(oferta18, user7);

		Set<Oferta> user1Compras = new HashSet<Oferta>() {
			{
				add(oferta4);
				add(oferta5);
			}
		};
		user1.setOfertasCompradas(user1Compras);
		for (Oferta o : user1Compras) {
			o.setComprable(false);
			o.setComprador(user1);
			user1.comprarOferta(o);
			ofertasService.guardarCompra(user1, o);
		}

		Set<Oferta> user2Compras = new HashSet<Oferta>() {
			{
				add(oferta7);
				add(oferta8);
			}
		};
		user2.setOfertasCompradas(user2Compras);
		for (Oferta o : user2Compras) {
			o.setComprable(false);
			o.setComprador(user2);
			user2.comprarOferta(o);
			ofertasService.guardarCompra(user2, o);
		}

		Set<Oferta> user3Compras = new HashSet<Oferta>() {
			{
				add(oferta10);
				add(oferta11);
			}
		};
		user3.setOfertasCompradas(user3Compras);
		for (Oferta o : user3Compras) {
			o.setComprable(false);
			o.setComprador(user2);
			user3.comprarOferta(o);
			ofertasService.guardarCompra(user3, o);
		}

		Set<Oferta> user4Compras = new HashSet<Oferta>() {
			{
				add(oferta13);
				add(oferta14);
			}
		};
		user4.setOfertasCompradas(user4Compras);
		for (Oferta o : user4Compras) {
			o.setComprable(false);
			o.setComprador(user4);
			user4.comprarOferta(o);
			ofertasService.guardarCompra(user4, o);
		}

		Set<Oferta> user5Compras = new HashSet<Oferta>() {
			{
				add(oferta1);
				add(oferta2);
			}
		};
		user5.setOfertasCompradas(user5Compras);
		for (Oferta o : user5Compras) {
			o.setComprable(false);
			o.setComprador(user5);
			user5.comprarOferta(o);
			ofertasService.guardarCompra(user5, o);
		}
	}

}