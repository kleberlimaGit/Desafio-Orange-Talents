package com.desafio.orange.talents.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.desafio.orange.talents.dto.PessoaDTO;
import com.desafio.orange.talents.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaService service;
	
	@GetMapping
	public ResponseEntity<Page<PessoaDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<PessoaDTO> pageList = service.findAllPaged(pageRequest);
		
		return ResponseEntity.ok().body(pageList);
	
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PessoaDTO> findById(@PathVariable Long id){
		
		PessoaDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto);
	}
	
	
	@PostMapping
	public ResponseEntity<PessoaDTO> insert(@Valid @RequestBody PessoaDTO dto){
		PessoaDTO newDto = service.insertPessoa(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto); 
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<PessoaDTO> update(@PathVariable Long id ,@Valid @RequestBody PessoaDTO dto){
		PessoaDTO newDto = service.updatePessoa(id,dto);
			return ResponseEntity.ok().body(newDto); 
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<PessoaDTO> delete(@PathVariable Long id){
		service.deletePessoa(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
