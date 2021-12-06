package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.List;

import com.bruce.proyecto.ProyectoCitas.models.entity.Cita;

public interface ICitaService {

	public Cita save(Cita cita);

	public void delete(Long id);

	public List<Cita> findAll();

	public Cita findById(Long id);

	public List<Cita> findByDay();

}
