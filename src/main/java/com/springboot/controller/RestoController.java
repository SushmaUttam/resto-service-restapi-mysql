package com.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.entity.Resto;
import com.springboot.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/resto")
public class RestoController {

	@Autowired
	private JpaRepository<Resto, Long> restoRepository;
	
	// get all resto
	@GetMapping
	public List<Resto> getAllResto(){
		return this.restoRepository.findAll();
	}

	// get resto by id
	@GetMapping("/{id}")
	public Resto getRestoById(@PathVariable (value = "id") long restoId) {
		return this.restoRepository.findById(restoId)
				.orElseThrow(() -> new ResourceNotFoundException("Resto not found by id: " + restoId));
	}
	
	// create resto
	@PostMapping
	public Resto createResto(@RequestBody Resto resto) {
		return this.restoRepository.save(resto);
	}
	
	// update resto by id
	@PutMapping("/{id}")
	public Resto updateResto(@RequestBody Resto resto, @PathVariable ("id") long restoId) {
		Resto existingResto = this.restoRepository.findById(restoId)
				.orElseThrow(() -> new ResourceNotFoundException("Resto not found by id: " + restoId));
		existingResto.setName(resto.getName());
		existingResto.setAddress(resto.getAddress());
		existingResto.setMobile(resto.getMobile());
		existingResto.setEmail(resto.getEmail());
		return this.restoRepository.save(existingResto);
	}
	
	// delete resto by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Resto> deleteResto(@PathVariable ("id") long restoId){
		Resto existingResto = this.restoRepository.findById(restoId)
				.orElseThrow(() -> new ResourceNotFoundException("Resto not found by id: " + restoId));
		this.restoRepository.delete(existingResto);
		return ResponseEntity.ok().build();
	}
	
}
