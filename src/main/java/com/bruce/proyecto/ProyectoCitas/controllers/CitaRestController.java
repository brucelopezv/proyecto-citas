package com.bruce.proyecto.ProyectoCitas.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bruce.proyecto.ProyectoCitas.models.entity.Cita;
import com.bruce.proyecto.ProyectoCitas.models.service.CitaImpl;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class CitaRestController {

	@Autowired
	private CitaImpl serv;

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/citas")
	public List<Cita> getEspecialidades() {
		return serv.findAll();
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/citas/medicos")
	public List<?> getMedicosReporte() {
		return serv.groupByMedico();
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/citas/medicos/{inicio}/{fin}")
	public List<?> getMedicosReportePorFecha(
			@PathVariable("inicio") @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@PathVariable("inicio") @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin) {
		return serv.groupByMedicoAndDate(inicio, fin);
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/citas/hoy")
	public List<Cita> getCitasHoy() {
		return serv.findByDay();
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/citas/{id}")
	public ResponseEntity<?> getCitaId(@PathVariable Long id) {
		Cita cita = null;
		Map<String, Object> response = new HashMap<>();
		try {
			cita = serv.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar consulta en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (cita == null) {
			response.put("mensaje", "La cita ID: ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cita>(cita, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/citas")
	public ResponseEntity<?> create(@Valid @RequestBody Cita cita, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		Cita citaNew = null;
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream().map(err -> {
				return "El campo: '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			citaNew = serv.save(cita);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar insert en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La cita ha sido creada con exito");
		response.put("cita", citaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/citas/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cita cita, BindingResult result, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Cita citaActualizada = null;
		Cita citaActual = serv.findById(id);
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream().map(err -> {
				return "El campo: ' " + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (citaActual == null) {
			response.put("mensaje", "La cita ID: ".concat(id.toString()).concat("no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			citaActual.setFechaCita(cita.getFechaCita());
			citaActual.setHoraInicio(cita.getHoraInicio());
			citaActual.setHoraFin(cita.getHoraFin());
			citaActual.setCliente(cita.getCliente());
			citaActual.setMedico(cita.getMedico());
			citaActual.setServicio(cita.getServicio());
			citaActual.setUsuario(cita.getUsuario());
			citaActualizada = serv.save(citaActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar update de cita en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La cita ha sido actualizada con exito");
		response.put("cita", citaActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/citas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			serv.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La cita ha sido eliminada con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
