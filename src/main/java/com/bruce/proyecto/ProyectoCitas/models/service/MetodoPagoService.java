package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.proyecto.ProyectoCitas.models.dao.IMetodoPagoDao;
import com.bruce.proyecto.ProyectoCitas.models.entity.MetodoPago;

@Service
public class MetodoPagoService implements IMetodoPagoService {

	@Autowired
	private IMetodoPagoDao dao;

	@Override
	@Transactional
	public MetodoPago save(MetodoPago met) {
		return dao.save(met);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<MetodoPago> findAll() {
		return (List<MetodoPago>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<MetodoPago> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public MetodoPago findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public List<MetodoPago> findByEnabled() {
		return dao.findByEnabled();
	}

}
