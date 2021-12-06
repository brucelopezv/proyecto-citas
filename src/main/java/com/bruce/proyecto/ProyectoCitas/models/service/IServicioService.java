package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bruce.proyecto.ProyectoCitas.models.entity.Servicio;

public interface IServicioService {

	public Servicio save(Servicio servicio);

	public void delete(Long id);

	public List<Servicio> findAll();

	public Page<Servicio> findAll(Pageable pageable);

	public Servicio findById(Long id);

	public List<Servicio> findByEnabled();

}
