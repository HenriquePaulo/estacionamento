package br.com.estacionamento.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estacionamento.exception.ObjectNotFoundException;
import br.com.estacionamento.exception.ValidateException;
import br.com.estacionamento.model.Historico;
import br.com.estacionamento.model.Veiculo;
import br.com.estacionamento.repository.HistoricoRepository;
import br.com.estacionamento.repository.VeiculoRepository;
import br.com.estacionamento.validate.ValidateUtils;

@Service
public class EstacionamentoService {
	
	@Autowired
	private HistoricoRepository historicoRepository;
	
	@Autowired
	private VeiculoRepository veiculoRepository;

	@Transactional(rollbackOn = Exception.class)
	public Historico entrada(Veiculo veiculo) throws ValidateException, Exception {
		
		validaPlaca(veiculo);
		
		veiculo.placa = veiculo.placa.toUpperCase();
		List<Historico> historicoBD = historicoRepository.findByPlaca(veiculo.placa);
		
		if (!historicoBD.stream()
				.filter(p -> !p.saiu)
				.findAny()
				.isEmpty()) {
			throw new ValidateException("Esse Veiculo já se encontra no estacionamento");
		}
		
		Historico historico = Historico.builder()
			.pago(Boolean.FALSE)
			.saiu(Boolean.FALSE)
			.veiculo(veiculo)
			.build();
		
		
		Veiculo veiculoBD = veiculoRepository.findByPlaca(veiculo.placa);
		if (null == veiculoBD) {
			veiculoRepository.save(veiculo);				
		}
		else {
			historico.setVeiculo(veiculoBD);
		}
		
		
		return historicoRepository.save(historico);		
	}

	private void validaPlaca(Veiculo veiculo) throws ValidateException {
		if ( veiculo == null || !ValidateUtils.placaValida(veiculo.placa)) {
			throw new ValidateException("insira um Placa válida");
		}
	}

	@Transactional(rollbackOn = Exception.class)
	public Historico saida(Long id) throws ValidateException, ObjectNotFoundException, Exception {
		Optional<Historico> historicoOptional = isHistoricoOnDatabase(id);
		Historico historico = historicoOptional.get();
		
		if (!historico.pago) {
			throw new ValidateException("O pagamento está pendente!");
		}
		if (historico.saiu) {
			throw new ValidateException("O veiculo Já realizou a saída!");
		}
		
		Date now = new Date();
		String tempoPermanencia = getTempoPermanencia(
				historico.getDataEntrada(), now);
		
		historico.setDataSaida(now);
		historico.setSaiu(Boolean.TRUE);
		historico.setTempoPermanencia(tempoPermanencia);
		
		return historicoRepository.save(historico);
	}

	private Optional<Historico> isHistoricoOnDatabase(Long id) {
		Optional<Historico> historicoOptional = historicoRepository.findById(id);
		
		if (historicoOptional.isEmpty()) {
			throw new ObjectNotFoundException(
					String.format("nenhum dado encontrado para o id %s", id));
		}
		return historicoOptional;
	}

	public Historico pagamento(Long id) {
		Optional<Historico> historicoOptional = isHistoricoOnDatabase(id);
		Historico historico = historicoOptional.get();
		historico.setPago(Boolean.TRUE);
		
		return historicoRepository.save(historico);
	}

	public List<Historico> getHistorico(String placa) throws ValidateException {
		Veiculo veiculo = Veiculo.builder().placa(placa).build();
		validaPlaca(veiculo);
		return historicoRepository.findByPlaca(placa.toUpperCase());
	}
	
	
	public static String getTempoPermanencia(Date date1, Date date2) {
		String result = "";
	    long diffInMillies = date2.getTime() - date1.getTime();
	    long minutes =  TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
	    
	    if (minutes >= 60) {
	    	result += String.format("%d hora(s) e ", minutes / 60);
	    }
	    result += String.format("%d minuto(s)", minutes % 60);
	    
	    return result;
	    
	}
	
	
	
	

}
