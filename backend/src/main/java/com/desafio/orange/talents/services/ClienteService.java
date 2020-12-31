package com.desafio.orange.talents.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.orange.talents.domain.Cliente;
import com.desafio.orange.talents.dto.ClienteDTO;
import com.desafio.orange.talents.repositories.ClienteRepository;
import com.desafio.orange.talents.services.exceptions.DatabaseException;
import com.desafio.orange.talents.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@Service
@Transactional
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClienteDTO> findAllPaged(PageRequest pageRequest){
		
		Page<Cliente> cliente = repository.findAll(pageRequest);
		
		Page<ClienteDTO> clienteDto = cliente.map(x -> new ClienteDTO(x));
		
		return clienteDto;
	}
	
	
	@Transactional(readOnly = true)
	public ClienteDTO findById(Long id) {
		
		Optional<Cliente> obj = repository.findById(id);
		
		Cliente cliente = obj.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrada"));
		
		return new ClienteDTO(cliente);
	}
	
	public ClienteDTO insertCliente(ClienteDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setName(dto.getName().trim());
		cliente.setEmail(dto.getEmail());
		cliente.setCpf(dto.getCpf().trim());
		cliente.setBirthDate(dto.getBirthDate());
		
		try {
			cliente = repository.save(cliente);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Email ou cpf já cadastrado");
		}

		return new ClienteDTO(cliente);
	}
	
	public ClienteDTO updateCliente(Long id, ClienteDTO dto) throws InvalidFormatException {
		
		try {
			Cliente cliente = repository.getOne(id);
			cliente.setName(dto.getName());
			cliente.setEmail(dto.getEmail());
			cliente.setCpf(dto.getCpf());
			cliente.setBirthDate(dto.getBirthDate());
			
			cliente = repository.save(cliente);
			return new ClienteDTO(cliente);
		}
		catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Identificador " + id + " não encontrado.");
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Email ou cpf já cadastrado");
		}
	}
	
	public void deleteCliente(Long id) {
		try {
			repository.deleteById(id);
		}
		
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Identificador " + id + " não encontrado.");
		}
	}
	
	
	
	
}
