package com.bruce.proyecto.ProyectoCitas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bruce.proyecto.ProyectoCitas.models.entity.Medico;

public interface IMedicoDao extends JpaRepository<Medico, Long> {

	@Query("select e from Medico e where e.enabled = 1")
	public List<Medico> findByEnabled();

}
