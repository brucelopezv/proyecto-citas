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
import com.bruce.proyecto.ProyectoCitas.models.entity.MetodoPago;
import com.bruce.proyecto.ProyectoCitas.models.service.MetodoPagoService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class PagoMetodoRestController {

	@Autowired
	private MetodoPagoService service;

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/metodos")
	public List<MetodoPago> getMetodos() {
		return service.findAll();
	}

	@GetMapping("/metodos/enabled")
	public List<MetodoPago> getEspecialidadesEnabled() {
		return service.findByEnabled();
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/metodos/page/{page}")
	public Page<MetodoPago> index(@PathVariable Integer page) {
		return service.findAll(PageRequest.of(page, 10));
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/metodos/{id}")
	public ResponseEntity<?> getEspecialidad(@PathVariable Long id) {
		MetodoPago met = null;
		Map<String, Object> response = new HashMap<>();
		try {
			met = service.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar consulta en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (met == null) {
			response.put("mensaje", "El metodo ID: ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MetodoPago>(met, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/metodos")
	public ResponseEntity<?> create(@Valid @RequestBody MetodoPago met, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		MetodoPago metNew = null;
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream().map(err -> {
				return "El campo: '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			metNew = service.save(met);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar insert en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El metodo ha sido creada con exito");
		response.put("metodo", metNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/metodos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody MetodoPago met, BindingResult result, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		MetodoPago metActualizado = null;
		MetodoPago metActual = service.findById(id);
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream().map(err -> {
				return "El campo: ' " + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (metActual == null) {
			response.put("mensaje", "La especialidad ID: ".concat(id.toString()).concat("no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			metActual.setNombre(met.getNombre());
			metActual.setEnabled(met.isEnabled());
			metActualizado = service.save(metActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar update en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El metodo ha sido actualizado con exito");
		response.put("metodo", metActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/metodos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			service.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El metodo ha sido eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
