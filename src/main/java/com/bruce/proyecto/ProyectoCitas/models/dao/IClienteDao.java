package com.bruce.proyecto.ProyectoCitas.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruce.proyecto.ProyectoCitas.models.entity.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long>{

}
