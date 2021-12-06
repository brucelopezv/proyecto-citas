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
import com.bruce.proyecto.ProyectoCitas.models.entity.Medico;
import com.bruce.proyecto.ProyectoCitas.models.service.IMedicoService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class MedicoRestController {

	@Autowired
	private IMedicoService medService;

	@PostMapping("medicos")
	public ResponseEntity<?> create(@Valid @RequestBody Medico med, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		Medico medNew = null;
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream().map(err -> {
				return "El campo: '" + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			medNew = medService.save(med);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar insert en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El medico ha sido creado con exito");
		response.put("medico", medNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/medicos/page/{page}")
	public Page<Medico> index(@PathVariable Integer page) {
		return medService.findAll(PageRequest.of(page, 10));

	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/medicos/{id}")
	public ResponseEntity<?> getMedico(@PathVariable Long id) {
		Medico med = null;

		Map<String, Object> response = new HashMap<>();
		try {
			med = medService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar consulta en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (med == null) {
			response.put("mensaje", "El medico ID: ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Medico>(med, HttpStatus.OK);
	}

	@GetMapping("/medicos/enabled")
	public List<Medico> getMedicosEnabled() {
		return medService.findByEnabled();
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/medicos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			medService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El medico ha sido eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/medicos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Medico med, BindingResult result, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Medico medActualizado = null;
		Medico medActual = medService.findById(id);
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream().map(err -> {
				return "El campo: ' " + err.getField() + "' " + err.getDefaultMessage();
			}).collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (medActual == null) {
			response.put("mensaje", "El medico ID: ".concat(id.toString()).concat("no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			medActual.setNombre(med.getNombre());
			medActual.setApellido(med.getApellido());
			medActual.setFoto(med.getFoto());
			medActual.setEspecialidad(med.getEspecialidad());
			medActualizado = medService.save(medActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar update en base de datos.");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El medico ha sido actualizado con exito");
		response.put("medico", medActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
