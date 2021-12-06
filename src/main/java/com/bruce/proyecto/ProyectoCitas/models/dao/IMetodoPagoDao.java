package com.bruce.proyecto.ProyectoCitas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.bruce.proyecto.ProyectoCitas.models.entity.MetodoPago;

public interface IMetodoPagoDao extends JpaRepository<MetodoPago, Long> {

	@Query("select m from MetodoPago m where m.enabled = 1")
	public List<MetodoPago> findByEnabled();

}
