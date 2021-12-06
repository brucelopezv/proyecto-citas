package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bruce.proyecto.ProyectoCitas.models.entity.Especialidad;

public interface IEspecialidadService {

	public Especialidad save(Especialidad esp);

	public void delete(Long id);

	public List<Especialidad> findAll();

	public Page<Especialidad> findAll(Pageable pageable);

	public Especialidad findById(Long id);

	public List<Especialidad> findByEnabled();

}
