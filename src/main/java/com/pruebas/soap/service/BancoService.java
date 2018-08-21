package com.pruebas.soap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebas.soap.entity.Banco;
import com.pruebas.soap.repository.BancoRepository;

@Service
public class BancoService implements IBancoService {
	@Autowired
	private BancoRepository bancoRepository;
	
	@Override
	public Banco getBancoById(String bancoId) {
		Banco obj = bancoRepository.findByBancoId(bancoId);
		return obj;
	}
	
	@Override
	public List<Banco> getAllBancos(){
		List<Banco> list = new ArrayList<>();
		bancoRepository.findAll().forEach(e -> list.add(e));
		return list;
	}
	
	@Override
	public synchronized boolean addBanco(Banco banco){
	   banco = bancoRepository.save(banco);
	   return true;
	}
	
	@Override
	public void updateBanco(Banco banco) {
		bancoRepository.save(banco);
	}
	
	@Override
	public void deleteBanco(Banco banco) {
		bancoRepository.delete(banco);
	}
}
