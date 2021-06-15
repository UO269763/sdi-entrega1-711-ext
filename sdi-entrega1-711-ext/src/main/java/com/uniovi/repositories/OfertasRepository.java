package com.uniovi.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Oferta;
import com.uniovi.entities.User;

public interface OfertasRepository extends CrudRepository<Oferta, Long> {

	@Query("SELECT r FROM Oferta r WHERE r.user = ?1 ORDER BY r.id ASC ")
	Page<Oferta> findByUser(Pageable pageable,User user);

	@Transactional
	@Modifying
	@Query("DELETE FROM Oferta o WHERE o.user.id = ?1")
	void deleteForUserId(Long id);
	
	@Query("SELECT u FROM Oferta u WHERE (LOWER(u.titulo) LIKE LOWER(?1))")
	Page<Oferta> findByTitulo(Pageable pageable, String searchText);

	@Query("SELECT u from Oferta u")
	Page<Oferta> findAllOfertas(Pageable pageable);
	

}
