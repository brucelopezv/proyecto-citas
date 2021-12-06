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
import com.bruce.proyecto.ProyectoCitas.models.entity.Especialidad;
import com.bruce.proyecto.ProyectoCitas.models.service.IEspecialidadService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class EspecialidadRestController {

	@Autowired
	private IEspecialidadService espService;

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/especialidades")
	public List<Especialidad> getEspecialidades() {
		return espService.findAll();
	}
	
	@GetMapping("/especialidades/enabled")
	public List<Especialidad> getEspecialidadesEnabled() {
		return espService.findByEnabled();
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/especialidades/page/{page}")
	public Page<Especialidad> index(@PathVariable Integer page) {
		return espService.findAll(PageRequest.of(page, 10));

	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/especialidades/{id}")
	public ResponseEntity<?> getEspecialidad(@PathVariable Long id) {
		Especialidad esp = null;

		Map<String, Object> response = new HashMap<>();
		try {
			esp = espService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar consulta en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (esp == null) {
			response.put("mensaje", "La especialidad ID: ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Especialidad>(esp, HttpStatus.OK);

	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/especialidades")
	public ResponseEntity<?> create(@Valid @RequestBody Especialidad esp, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		Especialidad espNew = null;
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream().map(err -> {
				return "El campo: '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			espNew = espService.save(esp);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar insert en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La especialidad ha sido creada con exito");
		response.put("especialidad", espNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/especialidades/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Especialidad esp, BindingResult result, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Especialidad espActualizado = null;
		Especialidad espActual = espService.findById(id);
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream().map(err -> {
				return "El campo: ' " + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (espActual == null) {
			response.put("mensaje", "La especialidad ID: ".concat(id.toString()).concat("no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			espActual.setNombre(esp.getNombre());
			espActual.setEnabled(esp.isEnabled());
			espActualizado = espService.save(espActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar update en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La especialidad ha sido actualizada con exito");
		response.put("especialidad", espActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/especialidades/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			espService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
