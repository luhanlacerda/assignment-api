package luhanlacerda.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import luhanlacerda.entity.Funcionario;

@SuppressWarnings("serial")
public class CustomListSerializer extends StdSerializer<List<Funcionario>> {

	public CustomListSerializer() {
		this(null);
	}

	public CustomListSerializer(Class<List<Funcionario>> t) {
		super(t);
	}

	@Override
	public void serialize(List<Funcionario> funcionarios, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		List<String> nomes = new ArrayList<>();
		for (Funcionario func : funcionarios) {
			nomes.add(func.getNome());
		}
		generator.writeObject(nomes);
	}
}