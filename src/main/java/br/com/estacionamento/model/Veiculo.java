package br.com.estacionamento.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Veiculo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;	
	
	@Column(unique = true)
	public String placa;
    
	@OneToMany(mappedBy = "veiculo", fetch = FetchType.LAZY)
	public Set<Historico> historico;

}
