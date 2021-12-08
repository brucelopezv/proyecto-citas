package com.bruce.proyecto.ProyectoCitas.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.bruce.proyecto.ProyectoCitas.models.entity.Cita;

public interface ICitaDao extends JpaRepository<Cita, Long> {

	@Query("select c from Cita c where date_format(c.fechaCita,  '%d/%m/%Y') = date_format(sysdate(),  '%d/%m/%Y')")
	public List<Cita> findByDay();

	@Query("select m.nombre, count(*) from Cita c INNER JOIN Medico m ON c.medico = m GROUP BY m.nombre")
	public List<?> groupByMedico();

	@Query("select m.nombre, count(*) "
			+ "from Cita c "			
			+ "INNER JOIN Medico m ON c.medico = m "
			+ "WHERE (c.fechaCita BETWEEN ?1 AND ?2) "
			+ "GROUP BY m.nombre")
	public List<?> groupByMedicoAndDate(Date inicio, Date fin);
}
