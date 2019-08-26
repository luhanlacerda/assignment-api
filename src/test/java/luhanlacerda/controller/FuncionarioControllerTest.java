package luhanlacerda.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import luhanlacerda.AssignmentApiApplication;
import luhanlacerda.dto.FuncionarioDTO;
import luhanlacerda.entity.Endereco;
import luhanlacerda.entity.Funcionario;
import luhanlacerda.repository.FuncionarioRepository;
import luhanlacerda.utils.ConvertDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
		AssignmentApiApplication.class })
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class FuncionarioControllerTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	private Funcionario funcionario;

	private HttpHeaders headers;
	private HttpEntity<String> entity;
	private MultiValueMap<String, String> header;

	@Before
	public void before() {
		header = new LinkedMultiValueMap<String, String>();
		header.add("Authorization",
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbmlzdHJhZG9yLmZlcmlhc0BjYXN0Z3JvdXAuY29tLmJyIiwiZXhwIjoxNTY3NjM5OTc3fQ.sUcRTQkMkA4Vkb_YYKqXxLdeVuLjI8PMCzF1a8V1Vg3DzZfo5zLT3VVQ3k-l6KXd_4X6NeY7kTcbXZpCIfOEPg");

		headers = new HttpHeaders();
		headers.set("Authorization",
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbmlzdHJhZG9yLmZlcmlhc0BjYXN0Z3JvdXAuY29tLmJyIiwiZXhwIjoxNTY3NjM5OTc3fQ.sUcRTQkMkA4Vkb_YYKqXxLdeVuLjI8PMCzF1a8V1Vg3DzZfo5zLT3VVQ3k-l6KXd_4X6NeY7kTcbXZpCIfOEPg");
		entity = new HttpEntity<>("body", headers);

		funcionarioRepository.deleteAll();

		funcionario = funcionarioRepository
				.save(new Funcionario("Funcionario", new ConvertDate().stringToDateDDMMYYYY("10/10/2010"),
						new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"),
						new ConvertDate().stringToDateDDMMYYYY("10/10/2010")));

	}

	@Test
	public void listFuncionarioSuccessfullyTest() {

		ResponseEntity<List<Funcionario>> res = restTemplate.exchange("/funcionarios", HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Funcionario>>() {
				});

		List<Funcionario> body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertThat(body.size(), is(1));

		Funcionario firstRow = body.get(0);
		assertEquals(firstRow.getMatricula(), funcionario.getMatricula());
		assertEquals(firstRow.getDataDeContratacao().toInstant(), funcionario.getDataDeContratacao().toInstant());
		assertEquals(firstRow.getDataDeNascimento().toInstant(), funcionario.getDataDeNascimento().toInstant());
		assertEquals(firstRow.getNome(), funcionario.getNome());

	}

	@Test
	public void saveFuncionarioSuccessfullyTest() {

		HttpEntity<Funcionario> requestBody = new HttpEntity<>(funcionario, header);

		ResponseEntity<Funcionario> res = restTemplate.exchange("/funcionarios", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Funcionario>() {
				});

		Funcionario body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(funcionario.getMatricula(), body.getMatricula());
		assertEquals(funcionario.getDataDeContratacao().toInstant(), body.getDataDeContratacao().toInstant());
		assertEquals(funcionario.getDataDeNascimento().toInstant(), body.getDataDeNascimento().toInstant());
		assertEquals(funcionario.getNome(), body.getNome());

	}

	@Test
	public void saveFuncionarioWithoutNameTest() throws Exception {

		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setNome(null);
		HttpEntity<FuncionarioDTO> requestBody = new HttpEntity<>(funcionarioDTO, header);

		ResponseEntity<Funcionario> res = restTemplate.exchange("/funcionarios", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Funcionario>() {
				});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
	}

	@Test
	public void saveFuncionarioWithoutDataDeNascimentoTest() throws Exception {

		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setNome("Teste");
		funcionarioDTO.setDataDeNascimento(null);
		funcionarioDTO.setDataDeContratacao("10/10/2010");
		funcionarioDTO.setEndereco(new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"));
		HttpEntity<FuncionarioDTO> requestBody = new HttpEntity<>(funcionarioDTO, header);

		ResponseEntity<Funcionario> res = restTemplate.exchange("/funcionarios", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Funcionario>() {
				});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
	}

	@Test
	public void saveFuncionarioWithoutDataDeContratacaoTest() throws Exception {

		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setNome("Teste");
		funcionarioDTO.setDataDeContratacao(null);
		funcionarioDTO.setDataDeNascimento("10/10/2010");
		funcionarioDTO.setEndereco(new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"));
		HttpEntity<FuncionarioDTO> requestBody = new HttpEntity<>(funcionarioDTO, header);

		ResponseEntity<Funcionario> res = restTemplate.exchange("/funcionarios", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Funcionario>() {
				});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
	}

	@Test
	public void saveFuncionarioWithoutEnderecoTest() throws Exception {

		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setNome("Teste");
		funcionarioDTO.setDataDeContratacao("10/10/2010");
		funcionarioDTO.setDataDeNascimento("10/10/2010");
		funcionarioDTO.setEndereco(null);
		HttpEntity<FuncionarioDTO> requestBody = new HttpEntity<>(funcionarioDTO, header);

		ResponseEntity<Funcionario> res = restTemplate.exchange("/funcionarios", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Funcionario>() {
				});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
	}

	@Test
	public void updateFuncionariotSuccessfullyTest() throws Exception {

		funcionario.setEndereco(new Endereco("nova rua", "12", "casa 1", "Boa Viagem", "Recife", "PE"));
		HttpEntity<Funcionario> requestBody = new HttpEntity<>(funcionario, header);

		ResponseEntity<Funcionario> res = restTemplate.exchange("/funcionarios/" + funcionario.getMatricula(),
				HttpMethod.POST, requestBody, new ParameterizedTypeReference<Funcionario>() {
				});

		Funcionario body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(funcionario.getEndereco(), body.getEndereco());
	}

	@Test
	public void updateFuncionarioNotFoundTest() throws Exception {

		HttpEntity<Funcionario> requestBody = new HttpEntity<>(funcionario, header);

		ResponseEntity<Funcionario> res = restTemplate.exchange("/funcionarios/98098", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Funcionario>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	public void getFuncionarioSuccessfullyTest() throws Exception {

		ResponseEntity<Funcionario> res = restTemplate.exchange("/funcionarios/" + funcionario.getMatricula(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<Funcionario>() {
				});

		Funcionario body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(funcionario.getMatricula(), body.getMatricula());
		assertEquals(funcionario.getEndereco(), body.getEndereco());
		assertEquals(funcionario.getDataDeContratacao().toInstant(), body.getDataDeContratacao().toInstant());
		assertEquals(funcionario.getDataDeNascimento().toInstant(), body.getDataDeNascimento().toInstant());
		assertEquals(funcionario.getNome(), body.getNome());

	}

	@Test
	public void getFuncionarioNotFoundTest() throws Exception {

		ResponseEntity<Funcionario> res = restTemplate.exchange("/funcionarios/9087", HttpMethod.GET, entity,
				new ParameterizedTypeReference<Funcionario>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	public void deleteFuncionarioSuccessfullyTest() throws Exception {

		ResponseEntity<Funcionario> res = restTemplate.exchange("/funcionarios/" + funcionario.getMatricula(),
				HttpMethod.DELETE, entity, new ParameterizedTypeReference<Funcionario>() {
				});

		assertEquals(HttpStatus.OK, res.getStatusCode());
	}
}
