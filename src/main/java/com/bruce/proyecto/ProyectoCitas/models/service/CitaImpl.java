package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.proyecto.ProyectoCitas.models.dao.ICitaDao;
import com.bruce.proyecto.ProyectoCitas.models.entity.Cita;

@Service
public class CitaImpl implements ICitaService {

	@Autowired
	private ICitaDao dao;

	@Override
	@Transactional
	public Cita save(Cita cita) {
		return dao.save(cita);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(readOnly = true)
	public List<Cita> findAll() {
		return (List<Cita>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cita findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public List<Cita> findByDay() {
		return (List<Cita>) dao.findByDay();
	}

	@Override
	public List<?> groupByMedico() {
		return (List<?>) dao.groupByMedico();
	}

}
