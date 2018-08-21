package com.pruebas.soap.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.pruebas.soap.generated.ws.AddBancoRequest;
import com.pruebas.soap.generated.ws.AddBancoResponse;
import com.pruebas.soap.generated.ws.BancoInfo;
import com.pruebas.soap.generated.ws.DeleteBancoRequest;
import com.pruebas.soap.generated.ws.DeleteBancoResponse;
import com.pruebas.soap.generated.ws.GetAllBancosResponse;
import com.pruebas.soap.generated.ws.GetBancoByIdRequest;
import com.pruebas.soap.generated.ws.GetBancoByIdResponse;
import com.pruebas.soap.generated.ws.ServiceStatus;
import com.pruebas.soap.generated.ws.UpdateBancoRequest;
import com.pruebas.soap.generated.ws.UpdateBancoResponse;
import com.pruebas.soap.entity.Banco;
import com.pruebas.soap.service.IBancoService;

@Endpoint
public class BancoEndpoint {
	private static final String NAMESPACE_URI = "http://www.miservicio.com/banco-ws";
	@Autowired
	private IBancoService bancoService;	

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBancoByIdRequest")
	@ResponsePayload
	public GetBancoByIdResponse getBanco(@RequestPayload GetBancoByIdRequest request) {
		GetBancoByIdResponse response = new GetBancoByIdResponse();
		BancoInfo bancoInfo = new BancoInfo();
		BeanUtils.copyProperties(bancoService.getBancoById(request.getBancoId()), bancoInfo);
		response.setBancoInfo(bancoInfo);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllBancosRequest")
	@ResponsePayload
	public GetAllBancosResponse getAllBancos() {
		GetAllBancosResponse response = new GetAllBancosResponse();
		List<BancoInfo> bancoInfoList = new ArrayList<>();
		List<Banco> bancoList = bancoService.getAllBancos();
		for (int i = 0; i < bancoList.size(); i++) {
			 BancoInfo ob = new BancoInfo();
		     BeanUtils.copyProperties(bancoList.get(i), ob);
		     bancoInfoList.add(ob);    
		}
		response.getBancoInfo().addAll(bancoInfoList);
		return response;
	}	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addBancoRequest")
	@ResponsePayload
	public AddBancoResponse addBanco(@RequestPayload AddBancoRequest request) {
		AddBancoResponse response = new AddBancoResponse();		
    	ServiceStatus serviceStatus = new ServiceStatus();		
		Banco banco = new Banco();
		banco.setBancoId(request.getId());
		banco.setNombre(request.getNombre());
        boolean flag = bancoService.addBanco(banco);
        if (flag == false) {
        	serviceStatus.setStatusCode("CONFLICTO");
        	serviceStatus.setMessage("Banco ya existe");
        	response.setServiceStatus(serviceStatus);
        } else {
			BancoInfo bancoInfo = new BancoInfo();
	        BeanUtils.copyProperties(banco, bancoInfo);
			response.setBancoInfo(bancoInfo);
        	serviceStatus.setStatusCode("EXITO");
        	serviceStatus.setMessage("Banco agregado exitosamente");
        	response.setServiceStatus(serviceStatus);
        }
        return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateBancoRequest")
	@ResponsePayload
	public UpdateBancoResponse updateBanco(@RequestPayload UpdateBancoRequest request) {
		Banco banco = new Banco();
		BeanUtils.copyProperties(request.getBancoInfo(), banco);
		bancoService.updateBanco(banco);
    	ServiceStatus serviceStatus = new ServiceStatus();
    	serviceStatus.setStatusCode("EXITO");
    	serviceStatus.setMessage("Banco actualizado exitosamente");
    	UpdateBancoResponse response = new UpdateBancoResponse();
    	response.setServiceStatus(serviceStatus);
    	return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteBancoRequest")
	@ResponsePayload
	public DeleteBancoResponse deleteBanco(@RequestPayload DeleteBancoRequest request) {
		Banco banco = bancoService.getBancoById(request.getBancoId());
    	ServiceStatus serviceStatus = new ServiceStatus();
		if (banco == null ) {
	    	serviceStatus.setStatusCode("FALLO");
	    	serviceStatus.setMessage("Banco no existe");
		} else {
			bancoService.deleteBanco(banco);
	    	serviceStatus.setStatusCode("EXITO");
	    	serviceStatus.setMessage("Banco eliminado exitosamente");
		}
    	DeleteBancoResponse response = new DeleteBancoResponse();
    	response.setServiceStatus(serviceStatus);
		return response;
	}	
}
