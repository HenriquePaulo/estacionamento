package br.com.estacionamento.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.estacionamento.model.Veiculo;

public interface VeiculoRepository extends CrudRepository<Veiculo, Long> {

	Veiculo findById(long id);
	Veiculo findByPlaca(String placa);
}
