package br.com.estacionamento.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.estacionamento.model.Historico;

public class HistoricoSerializer extends JsonSerializer<Historico> {

	@Override
	public void serialize(Historico value, JsonGenerator jsonGenerator, SerializerProvider serializers) 
			throws IOException {
		jsonGenerator.writeStartObject();
		
		jsonGenerator.writeNumberField("id", value.getId());
		jsonGenerator.writeBooleanField("pago", value.getPago());
		jsonGenerator.writeBooleanField("saiu", value.getSaiu());
		jsonGenerator.writeStringField("tempoPermanencia", value.getTempoPermanencia());
		
		jsonGenerator.writeEndObject();
	}

}


