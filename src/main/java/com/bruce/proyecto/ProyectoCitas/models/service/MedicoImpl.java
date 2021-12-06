package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.proyecto.ProyectoCitas.models.dao.IMedicoDao;
import com.bruce.proyecto.ProyectoCitas.models.entity.Medico;

@Service
public class MedicoImpl implements IMedicoService {

	@Autowired
	private IMedicoDao dao;

	@Override
	@Transactional
	public Medico save(Medico med) {
		return dao.save(med);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Medico> findAll() {
		return (List<Medico>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Medico> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Medico findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public List<Medico> findByEnabled() {
		return dao.findByEnabled();
	}

}
