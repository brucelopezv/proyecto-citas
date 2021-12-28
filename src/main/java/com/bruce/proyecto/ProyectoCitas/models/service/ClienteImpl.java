package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruce.proyecto.ProyectoCitas.models.dao.IClienteDao;
import com.bruce.proyecto.ProyectoCitas.models.entity.Cita;
import com.bruce.proyecto.ProyectoCitas.models.entity.Cliente;

@Service
public class ClienteImpl implements IClienteService {

	@Autowired
	private IClienteDao dao;

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return dao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public List<Cita> findCitaByClienteId(Long id) {
		return (List<Cita>) dao.findCitaByClienteId(id);
	}

}
