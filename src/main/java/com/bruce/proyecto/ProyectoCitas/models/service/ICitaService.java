package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.Date;
import java.util.List;

import com.bruce.proyecto.ProyectoCitas.models.entity.Cita;

public interface ICitaService {

	public Cita save(Cita cita);

	public void delete(Long id);

	public List<Cita> findAll();

	public Cita findById(Long id);

	public List<Cita> findByDay();

	public List<?> groupByMedico();

	public List<?> groupByMedicoAndDate(Date inicio, Date fin);
	
	public List<?> groupByServicioAndDate(Date inicio, Date fin);
	
	public List<?> groupByEstadoAndDate(Date inicio, Date fin);

}
