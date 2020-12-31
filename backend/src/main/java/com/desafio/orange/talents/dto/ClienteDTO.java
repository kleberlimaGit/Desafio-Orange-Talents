package com.desafio.orange.talents.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.desafio.orange.talents.domain.Cliente;

public class ClienteDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo nome não pode ficar em branco.")
	private String name;
	
	@NotBlank(message = "Campo email não pode ficar em branco.")
	@Email(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Por favor digite um email válido.")
	private String email;
	
	@NotBlank(message = "Campo cpf não pode ficar em branco.")
	private String cpf;
	
	
	@DateTimeFormat(iso = ISO.DATE)
	@PastOrPresent(message = "Data de aniversário não pode futura.")
	@NotNull(message = "Campo data não pode ficar em branco.")
	private LocalDate birthDate;


	public ClienteDTO() {
		
	}


	public ClienteDTO(Long id, String name, String email, String cpf, LocalDate birthDate) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.cpf = cpf;
		this.birthDate = birthDate;
	}
	
	public ClienteDTO(Cliente cliente) {
		id = cliente.getId();
		name = cliente.getName();
		email = cliente.getEmail();
		cpf = cliente.getCpf();
		birthDate = cliente.getBirthDate();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public LocalDate getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	
	
}
