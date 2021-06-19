package com.uniovi.services;

import java.util.LinkedList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Oferta;
import com.uniovi.entities.User;
import com.uniovi.repositories.OfertasRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class OfertaService {

	@Autowired
	private OfertasRepository ofertasRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private UsersService usersService;

	public Page<Oferta> getOfertas(Pageable pageable, User user) {
		return ofertasRepository.findByUser(pageable, user);
	}
	
	public Oferta getOferta(Long id) {
		return ofertasRepository.findById(id).get();
	}

	public void addOferta(Oferta oferta, User user) {
		// Si en Id es null le asignamos el ultimo + 1 de la lista
		oferta.setUser(user);
		ofertasRepository.save(oferta);
	}
	
	public void editOferta(Oferta oferta) {
		ofertasRepository.save(oferta);
	}

	public void deleteOferta(Long id) {
		ofertasRepository.deleteById(id);
	}
	
	public void comprarOfertaIdOf(Long id, User user) {
		comprarOferta(user, ofertasRepository.findById(id).get());	
	}

	public void comprarOferta(User usuarioComprador, Oferta oferta) {
		User usuarioVendedor = usersRepository.findByEmail(oferta.getUser().getEmail());
		//actualizamos el saldo del vendedor y del comprador
		usuarioComprador.setDinero(usuarioComprador.getDinero() - oferta.getPrecio());
		usuarioVendedor.setDinero(usuarioVendedor.getDinero()+oferta.precio);
		oferta.setComprador(usuarioComprador);
		usuarioComprador.comprarOferta(oferta);
		oferta.setComprable(false);
		usersService.editUser(usuarioComprador);
		editOferta(oferta);
		
	
	}
	
	

	
	public double precioOferta(Long id) {
		Optional<Oferta> oferta = ofertasRepository.findById(id);
		return oferta.get().getPrecio();
	}
	
	
	public Page<Oferta> searchOfertaByTitulo(Pageable pageable, String searchText, User user) {
		Page<Oferta> ofertas = new PageImpl<Oferta>(new LinkedList<Oferta>());
		searchText = "%" + searchText + "%";

		ofertas = ofertasRepository.findByTitulo(pageable, searchText, user);

		return ofertas;
	}
	
	public Page<Oferta> searchAllOfertas(Pageable pageable) {
		Page<Oferta> ofertas = new PageImpl<Oferta>(new LinkedList<Oferta>());

		ofertas = ofertasRepository.findAllOfertas(pageable);

		return ofertas;
	}
	
	public Page<Oferta> searchAllOfertasUnlessMine(Pageable pageable, User user) {
		Page<Oferta> ofertas = new PageImpl<Oferta>(new LinkedList<Oferta>());

		ofertas = ofertasRepository.findAllOfertasUnlessMine(pageable, user);

		return ofertas;
	}

	public void guardarCompra(User user1, Oferta oferta1) {
		usersRepository.save(user1);
		ofertasRepository.save(oferta1);
		
	}

	





	
	


}
