package br.com.estacionamento.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.estacionamento.serializer.HistoricoSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

@Getter
@Setter
@Builder
@With
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonSerialize(using = HistoricoSerializer.class)
@Entity
public class Historico {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
	public Veiculo veiculo;
    
    public UUID numeroReserva;
    
	public Date dataEntrada;
	public Date dataSaida;
	public String tempoPermanencia;
	public Boolean pago;
	public Boolean saiu;
	
	@PrePersist
	public void prePersist() {
		dataEntrada = new Date();
		numeroReserva = UUID.randomUUID();
	}
}
