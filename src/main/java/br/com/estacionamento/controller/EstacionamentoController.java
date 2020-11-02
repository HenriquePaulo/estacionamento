package br.com.estacionamento.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.estacionamento.exception.ValidateException;
import br.com.estacionamento.model.Historico;
import br.com.estacionamento.model.Veiculo;
import br.com.estacionamento.service.EstacionamentoService;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
public class EstacionamentoController {
	
	@Autowired
	EstacionamentoService estacionamentoService;
	
	@PostMapping("/estacionamentos")
	public ResponseEntity<UUID> entrada(@RequestBody Veiculo veiculo) throws ValidateException, Exception {
		Historico historico = estacionamentoService.entrada(veiculo);
		return ResponseEntity.ok(historico.numeroReserva);
	}
	
	@PutMapping("/estacionamentos/{id}/saida") 
	public ResponseEntity<Historico> saida(@PathVariable Long id) 
			throws ValidateException, ObjectNotFoundException, Exception {
		return ResponseEntity.ok(estacionamentoService.saida(id));
	}
	
	@PutMapping("/estacionamentos/{id}/pagamento")
	public ResponseEntity<Historico> pagamento(@PathVariable Long id) {
		return ResponseEntity.ok(estacionamentoService.pagamento(id));
		
	}
	
	@GetMapping("/estacionamentos/{placa}")
	public ResponseEntity<List<Historico>> getHistorico(@PathVariable String placa) throws ValidateException {
		return ResponseEntity.ok(estacionamentoService.getHistorico(placa));
	}
	
	
	
	
	
}
