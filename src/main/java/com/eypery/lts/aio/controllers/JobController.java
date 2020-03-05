package com.eypery.lts.aio.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eypery.lts.aio.entities.Cliente;
import com.eypery.lts.aio.entities.Job;
import com.eypery.lts.aio.exceptions.ClienteNotFoundException;
import com.eypery.lts.aio.exceptions.JobNotFoundException;
import com.eypery.lts.aio.repositories.ClienteRepository;
import com.eypery.lts.aio.repositories.JobRepository;

@RestController
@RequestMapping(value = "/clientes")
public class JobController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private JobRepository jobRepository;

	@GetMapping("/{codigoCliente}/jobs")
	public List<Job> getAllJobs(@PathVariable Long codigoCliente) throws ClienteNotFoundException {
		Optional<Cliente> optCliente = clienteRepository.findById(codigoCliente);

		if (!optCliente.isPresent())
			throw new ClienteNotFoundException("Cliente no encontrado. No se pueden obtener los Jobs");

		return optCliente.get().getJobs();
	}

	@PostMapping("/{codigoCliente}/jobs")
	public Job createJob(@PathVariable Long codigoCliente, @RequestBody Job job) throws ClienteNotFoundException {

		Optional<Cliente> optCliente = clienteRepository.findById(codigoCliente);

		if (!optCliente.isPresent())
			throw new ClienteNotFoundException("Cliente no encontrado. No se puede registrar el Job");

		Cliente cliente = optCliente.get();
		job.setCliente(cliente);
		return jobRepository.save(job);
	}

	@GetMapping("/{codigoCliente}/jobs/{codigoJob}")
	public Job getJobByCodigo(@PathVariable Long codigoCliente, @PathVariable Long codigoJob)
			throws ClienteNotFoundException, JobNotFoundException {

		Optional<Cliente> optCliente = clienteRepository.findById(codigoCliente);

		if (!optCliente.isPresent())
			throw new ClienteNotFoundException("Cliente no encontrado. No se puede registrar el Job");

		Optional<Job> optJob = jobRepository.findById(codigoJob);

		if (!optJob.isPresent())
			throw new JobNotFoundException("Job no encontrado.");

		Job job = optJob.get();

		if (job.getCliente().getCodigo() != codigoCliente)
			throw new JobNotFoundException("Job no encontrado.");

		return job;
	}

}
