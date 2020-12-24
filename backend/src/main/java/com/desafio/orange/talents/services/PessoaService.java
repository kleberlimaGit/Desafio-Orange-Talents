package com.desafio.orange.talents.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.orange.talents.domain.Pessoa;
import com.desafio.orange.talents.dto.PessoaDTO;
import com.desafio.orange.talents.repositories.PessoaRepository;
import com.desafio.orange.talents.services.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	
	@Transactional(readOnly = true)
	public Page<PessoaDTO> findAllPaged(PageRequest pageRequest){
		
		Page<Pessoa> pessoa = repository.findAll(pageRequest);
		
		Page<PessoaDTO> pessoaDto = pessoa.map(x -> new PessoaDTO(x));
		
		return pessoaDto;
	}
	
	
	@Transactional(readOnly = true)
	public PessoaDTO findById(Long id) {
		
		Optional<Pessoa> obj = repository.findById(id);
		
		Pessoa pessoa = obj.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));
		
		return new PessoaDTO(pessoa);
	}
	
	public PessoaDTO insertPessoa(PessoaDTO dto) {
		Pessoa pessoa = new Pessoa();
		pessoa.setName(dto.getName());
		pessoa.setEmail(dto.getEmail());
		pessoa.setCpf(dto.getCpf());
		pessoa.setBirthDate(dto.getBirthDate());
		
		pessoa = repository.save(pessoa);
		return new PessoaDTO(pessoa);
	}
	
	public PessoaDTO updatePessoa(Long id, PessoaDTO dto) {
		
		try {
			Pessoa pessoa = repository.getOne(id);
			pessoa.setName(dto.getName());
			pessoa.setEmail(dto.getEmail());
			pessoa.setCpf(dto.getCpf());
			pessoa.setBirthDate(dto.getBirthDate());
			
			pessoa = repository.save(pessoa);
			return new PessoaDTO(pessoa);
		}
		catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Identificador " + id + " não encontrado.");
		}
	}
	
	public void deletePessoa(Long id) {
		try {
			repository.deleteById(id);
		}
		
		catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Identificador " + id + " não encontrado.");
		}
	}
	
	
}
