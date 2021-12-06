package com.bruce.proyecto.ProyectoCitas.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.bruce.proyecto.ProyectoCitas.models.entity.MetodoPago;

public interface IMetodoPagoService {

	public MetodoPago save(MetodoPago met);

	public void delete(Long id);

	public List<MetodoPago> findAll();

	public Page<MetodoPago> findAll(Pageable pageable);

	public MetodoPago findById(Long id);

	public List<MetodoPago> findByEnabled();

}
