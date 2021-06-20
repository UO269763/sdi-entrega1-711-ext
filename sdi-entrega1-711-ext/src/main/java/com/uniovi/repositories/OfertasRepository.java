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

	/**
	 * Busca las ofertas de un usuario
	 * 
	 * @param pageable
	 * @param user
	 * @return
	 */
	@Query("SELECT r FROM Oferta r WHERE r.user = ?1 ORDER BY r.id ASC ")
	Page<Oferta> findByUser(Pageable pageable, User user);

	/**
	 * Borra las ofertas para un id de un usuario
	 * 
	 * @param id
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Oferta o WHERE o.user.id = ?1")
	void deleteForUserId(Long id);

	/**
	 * Busca las ofertas según título ignorando minúsicas y mayúsculas y omitiendo
	 * al usuario pasado como parámetro
	 * 
	 * @param pageable
	 * @param searchText
	 * @param user
	 * @return
	 */
	@Query("SELECT u FROM Oferta u WHERE (LOWER(u.titulo) LIKE LOWER(?1)) AND u.user<>?2")
	Page<Oferta> findByTitulo(Pageable pageable, String searchText, User user);

	/**
	 * Obtiene todas las ofertas
	 * 
	 * @param pageable
	 * @return
	 */
	@Query("SELECT u from Oferta u")
	Page<Oferta> findAllOfertas(Pageable pageable);

	/**
	 * Busca todas las ofertas excepto las de un usuario dado
	 * 
	 * @param pageable
	 * @param user
	 * @return
	 */
	@Query("SELECT u FROM Oferta u WHERE u.user<>?1")
	Page<Oferta> findAllOfertasUnlessMine(Pageable pageable, User user);

	/**
	 * Obtiene la lista de ofertas destacadas excepto las propias
	 * 
	 * @param pageable
	 * @param user
	 * @return
	 */
	@Query("SELECT u FROM Oferta u WHERE u.user<>?1 and u.destacada=true")
	Page<Oferta> findOfertasDestacadas(Pageable pageable, User user);

}
