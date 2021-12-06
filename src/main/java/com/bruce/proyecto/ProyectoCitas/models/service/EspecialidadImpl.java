package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.proyecto.ProyectoCitas.models.dao.IEspecialdadDao;
import com.bruce.proyecto.ProyectoCitas.models.entity.Especialidad;

@Service
public class EspecialidadImpl implements IEspecialidadService {

	@Autowired
	private IEspecialdadDao dao;

	@Override
	@Transactional
	public Especialidad save(Especialidad esp) {
		return dao.save(esp);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Especialidad> findAll() {
		return (List<Especialidad>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Especialidad> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Especialidad findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public List<Especialidad> findByEnabled() {
		return dao.findByEnabled();
	}

}
