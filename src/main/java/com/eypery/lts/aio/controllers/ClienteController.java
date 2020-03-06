package com.eypery.lts.aio.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.eypery.lts.aio.entities.Cliente;
import com.eypery.lts.aio.entities.Usuario;
import com.eypery.lts.aio.exceptions.ClienteExistsException;
import com.eypery.lts.aio.exceptions.ClienteNotFoundException;
import com.eypery.lts.aio.services.ClienteService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@Validated
@RequestMapping(value = "/cliente")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public List<Cliente> getAllClientes() {
		return clienteService.getAllClientes();
	}

	@PostMapping
	public Cliente createCliente(@RequestBody Cliente cliente) {
		try {
			return clienteService.createCliente(cliente);
		} catch (ClienteExistsException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	@GetMapping("/{codigo}")
	public Optional<Cliente> getClienteByCodigo(@PathVariable("codigo") @Min(1) Long codigo) {
		try {
			return clienteService.getClienteByCodigo(codigo);
		} catch (ClienteNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@PutMapping("/{codigo}")
	public Cliente updateClientebyCodigo(@PathVariable("codigo") Long codigo, @RequestBody Cliente cliente) {
		try {
			return clienteService.updateClienteByCodigo(codigo, cliente);
		} catch (ClienteNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	@DeleteMapping("/{codigo}")
	public void deleteClienteByCodigo(@PathVariable("codigo") Long codigo) {
		clienteService.deleteClienteByCodigo(codigo);
	}

	@GetMapping("/buscar/{razonSocial}")
	public Cliente getClienteByRazonSocial(@PathVariable("razonSocial") String razonSocial)
			throws ClienteNotFoundException {
		Cliente cliente = clienteService.getClienteByRazonSocial(razonSocial);

		if (cliente == null) {
			throw new ClienteNotFoundException("No existe ningun Cliente con la razón social: " + razonSocial);
		}

		return cliente;
	}

//	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/login")
	public Usuario login(@RequestBody Usuario usuario) {

		String token = getJWTToken(usuario.getUsername());
		usuario.setToken(token);
		return usuario;

	}

	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ADMIN,SUPER-ADMIN");

		String token = Jwts.builder().setId("softtekJWT").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

}
