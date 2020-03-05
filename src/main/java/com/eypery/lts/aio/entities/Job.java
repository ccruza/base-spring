package com.eypery.lts.aio.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Job", schema = "dbo")
public class Job {

	@Id
	@Column(name = "Codigo")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;

	@NotEmpty(message = "El campo Descripción es obligatorio")
	@Column(name = "Descripcion", length = 500, nullable = false)
	private String descripcion;

	@NotEmpty(message = "El campo Estado es obligatorio")
	@Column(name = "Estado", length = 1, nullable = false)
	private String estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Cliente cliente;

	public Job() {
	}

	public Job(Long codigo, @NotEmpty(message = "El campo Descripción es obligatorio") String descripcion,
			@NotEmpty(message = "El campo Estado es obligatorio") String estado, Cliente cliente) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.estado = estado;
		this.cliente = cliente;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Job [codigo=" + codigo + ", descripcion=" + descripcion + ", estado=" + estado + ", cliente=" + cliente
				+ "]";
	}

}
