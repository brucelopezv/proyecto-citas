package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bruce.proyecto.ProyectoCitas.models.entity.Medico;

public interface IMedicoService {

	public Medico save(Medico med);

	public void delete(Long id);

	public List<Medico> findAll();

	public List<Medico> findByEnabled();

	public Page<Medico> findAll(Pageable pageable);

	public Medico findById(Long id);
}
