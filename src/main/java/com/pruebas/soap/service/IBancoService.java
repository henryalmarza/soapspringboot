package com.pruebas.soap.service;

import java.util.List;

import com.pruebas.soap.entity.Banco;

public interface IBancoService {
     List<Banco> getAllBancos();
     Banco getBancoById(String bancoId);
     boolean addBanco(Banco banco);
     void updateBanco(Banco banco);
     void deleteBanco(Banco banco);
}
