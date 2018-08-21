package com.pruebas.soap.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pruebas.soap.entity.Banco;

public interface BancoRepository extends CrudRepository<Banco, Long>  {
	Banco findByBancoId(String bancoId);
    List<Banco> findByNombre(String nombre);
}
