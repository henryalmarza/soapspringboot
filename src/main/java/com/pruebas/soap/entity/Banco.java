package com.pruebas.soap.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bancos")
public class Banco implements Serializable { 
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="codigo_banco")
    private String bancoId;  
	@Column(name="nombre_banco")
    private String nombre;

	public String  getBancoId() {
		return bancoId;
	}
	public void setBancoId(String bancoId) {
		this.bancoId = bancoId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre= nombre;
	}
} 