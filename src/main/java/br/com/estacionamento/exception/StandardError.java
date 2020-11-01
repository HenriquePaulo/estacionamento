package br.com.estacionamento.exception;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class StandardError {
	
	private Integer status;
	private String message;
	private Long timestamp;
	private String error;
	private String path;
}
