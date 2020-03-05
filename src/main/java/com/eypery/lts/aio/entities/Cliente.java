package com.eypery.lts.aio.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Cliente", schema = "dbo")
//@Table(name = "Cliente")
public class Cliente {

	@Id
	@Column(name = "Codigo")
//	@GeneratedValue
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;

	@NotEmpty(message = "El campo Razón Social es obligatorio")
	@Column(name = "RazonSocial", length = 500, nullable = false)
	private String razonSocial;

	@NotEmpty(message = "El campo Estado es obligatorio")
	@Column(name = "Estado", length = 1, nullable = false)
	private String estado;

	@Column(name = "Referencia", length = 50, nullable = true)
	private String referencia;

	@OneToMany(mappedBy = "cliente")
	private List<Job> jobs;

	public Cliente() {
	}

	public Cliente(Long codigo, @NotEmpty(message = "El campo Razón Social es obligatorio") String razonSocial,
			@NotEmpty(message = "El campo Estado es obligatorio") String estado, String referencia, List<Job> jobs) {
		this.codigo = codigo;
		this.razonSocial = razonSocial;
		this.estado = estado;
		this.referencia = referencia;
		this.jobs = jobs;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	@Override
	public String toString() {
		return "Cliente [codigo=" + codigo + ", razonSocial=" + razonSocial + ", estado=" + estado + ", referencia="
				+ referencia + ", jobs=" + jobs + "]";
	}

}
