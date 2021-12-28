package com.bruce.proyecto.ProyectoCitas.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bruce.proyecto.ProyectoCitas.models.entity.Cita;
import com.bruce.proyecto.ProyectoCitas.models.entity.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long> {

	@Query("select c from Cita c where c.cliente.id = ?1")
	public List<Cita> findCitaByClienteId(Long id);

}
