package com.eypery.lts.aio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eypery.lts.aio.entities.Cliente;
import com.eypery.lts.aio.exceptions.ClienteExistsException;
import com.eypery.lts.aio.exceptions.ClienteNotFoundException;
import com.eypery.lts.aio.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	// getAll
	public List<Cliente> getAllClientes() {
		return clienteRepository.findAll();
	}

	// create
	public Cliente createCliente(Cliente cliente) throws ClienteExistsException {

		Cliente existingCliente = clienteRepository.findByRazonSocial(cliente.getRazonSocial());

		if (existingCliente != null)
			throw new ClienteExistsException("El cliente ya está registrado.");

		return clienteRepository.save(cliente);

	}

	// getByCodigo
	public Optional<Cliente> getClienteByCodigo(Long codigo) throws ClienteNotFoundException {
		Optional<Cliente> cliente = clienteRepository.findById(codigo);

		if (!cliente.isPresent())
			throw new ClienteNotFoundException("El cliente no está registrado.");

		return cliente;
	}

	// updateByCodigo
	public Cliente updateClienteByCodigo(Long codigo, Cliente cliente) throws ClienteNotFoundException {
		Optional<Cliente> optionalCliente = clienteRepository.findById(codigo);

		if (!optionalCliente.isPresent())
			throw new ClienteNotFoundException("El cliente no está registrado.");

		cliente.setCodigo(codigo);
		return clienteRepository.save(cliente);
	}

	// deleteByCodigo
	public void deleteClienteByCodigo(Long codigo) {

		Optional<Cliente> optionalCliente = clienteRepository.findById(codigo);

		if (!optionalCliente.isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente no está registrado.");

		clienteRepository.deleteById(codigo);

	}

	// getByRazonSocial
	public Cliente getClienteByRazonSocial(String razonSocial) {
		return clienteRepository.findByRazonSocial(razonSocial);
	}

}
