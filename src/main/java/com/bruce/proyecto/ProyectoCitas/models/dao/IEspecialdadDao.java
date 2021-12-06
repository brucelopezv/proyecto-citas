package com.bruce.proyecto.ProyectoCitas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bruce.proyecto.ProyectoCitas.models.entity.Especialidad;

public interface IEspecialdadDao extends JpaRepository<Especialidad, Long> {

	@Query("select e from Especialidad e where e.enabled = 1")
	public List<Especialidad> findByEnabled();

}
