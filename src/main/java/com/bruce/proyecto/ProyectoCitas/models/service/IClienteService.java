package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bruce.proyecto.ProyectoCitas.models.entity.Cita;
import com.bruce.proyecto.ProyectoCitas.models.entity.Cliente;

public interface IClienteService {

	public Cliente save(Cliente cliente);

	public void delete(Long id);

	public List<Cliente> findAll();

	public Page<Cliente> findAll(Pageable pageable);

	public Cliente findById(Long id);

	public List<Cita> findCitaByClienteId(Long id);

}
