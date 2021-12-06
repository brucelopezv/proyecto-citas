package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.proyecto.ProyectoCitas.models.dao.IServicioDao;
import com.bruce.proyecto.ProyectoCitas.models.entity.Servicio;

@Service
public class ServicioImpl implements IServicioService {

	@Autowired
	private IServicioDao dao;

	@Override
	@Transactional
	public Servicio save(Servicio servicio) {
		return dao.save(servicio);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Servicio> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Servicio findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByEnabled() {
		return dao.findByEnabled();
	}

}
