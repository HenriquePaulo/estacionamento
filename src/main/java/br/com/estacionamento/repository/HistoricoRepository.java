package br.com.estacionamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.estacionamento.model.Historico;

public interface HistoricoRepository extends CrudRepository<Historico, Long> {
	@Query("select hist from Historico hist join hist.veiculo veic where veic.placa = :placa")
	List<Historico> findByPlaca(@Param("placa") String placa);

	Historico findById(long id);
}
