package com.bruce.proyecto.ProyectoCitas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.bruce.proyecto.ProyectoCitas.models.entity.Servicio;
import com.bruce.proyecto.ProyectoCitas.models.service.IServicioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ServicioRestController {
	@Autowired
	private IServicioService ser;

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/servicios")
	public List<Servicio> getServicios() {
		return ser.findAll();
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/servicios/enabled")
	public List<Servicio> getServiciosEnabled() {
		return ser.findByEnabled();
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/servicios/page/{page}")
	public Page<Servicio> index(@PathVariable Integer page) {
		return ser.findAll(PageRequest.of(page, 10));
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/servicios/{id}")
	public ResponseEntity<?> getServicio(@PathVariable Long id) {
		Servicio servicio = null;

		Map<String, Object> response = new HashMap<>();
		try {
			servicio = ser.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar consulta en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (servicio == null) {
			response.put("mensaje", "El servicio ID: ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Servicio>(servicio, HttpStatus.OK);

	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/servicios")
	public ResponseEntity<?> create(@Valid @RequestBody Servicio servicio, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		Servicio servicioNew = null;
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream().map(err -> {
				return "El campo: '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			servicioNew = ser.save(servicio);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar insert en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El servicio ha sido creado con exito");
		response.put("servicio", servicioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/servicios/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Servicio servicio, BindingResult result,
			@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Servicio serActualizado = null;
		Servicio serActual = ser.findById(id);
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream().map(err -> {
				return "El campo: ' " + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (serActual == null) {
			response.put("mensaje", "La especialidad ID: ".concat(id.toString()).concat("no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			serActual.setNombre(servicio.getNombre());
			serActual.setEnabled(servicio.isEnabled());
			serActualizado = ser.save(serActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar update en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El servicio ha sido actualizada con exito");
		response.put("servicio", serActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/servicios/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			ser.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El servicio ha sido eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
