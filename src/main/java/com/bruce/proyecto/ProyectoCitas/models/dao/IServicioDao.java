package com.bruce.proyecto.ProyectoCitas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.bruce.proyecto.ProyectoCitas.models.entity.Servicio;

public interface IServicioDao extends JpaRepository<Servicio, Long> {

	@Query("select e from Servicio e where e.enabled = 1")
	public List<Servicio> findByEnabled();
}
