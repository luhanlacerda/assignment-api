package luhanlacerda.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.GregorianCalendar;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import luhanlacerda.AssignmentApiApplication;
import luhanlacerda.dto.FeriasDTO;
import luhanlacerda.entity.Endereco;
import luhanlacerda.entity.Ferias;
import luhanlacerda.entity.Funcionario;
import luhanlacerda.repository.FeriasRepository;
import luhanlacerda.repository.FuncionarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
		AssignmentApiApplication.class })
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class FeriasControllerTest {

	@Autowired
	private FeriasRepository feriasRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	private Ferias ferias;

	private Funcionario funcionario;

	@Before
	public void before() {
		feriasRepository.deleteAll();
		funcionarioRepository.deleteAll();

		funcionario = funcionarioRepository.save(new Funcionario("Funcionario", new GregorianCalendar(1992, 07, 14),
				new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"),
				new GregorianCalendar(2019, 8, 28)));

		ferias = feriasRepository
				.save(new Ferias(funcionario, new GregorianCalendar(2020, 8, 28), new GregorianCalendar(2020, 9, 28)));
	}

	@Test
	public void listFeriasSuccessfullyTest() {

		ResponseEntity<List<Ferias>> res = restTemplate.exchange("/ferias", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Ferias>>() {
				});

		List<Ferias> body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertThat(body.size(), is(1));

		Ferias firstRow = body.get(0);
		assertEquals(firstRow.getId(), ferias.getId());
		assertEquals(firstRow.getFuncionario().getMatricula(), ferias.getFuncionario().getMatricula());
	}

	@Test
	public void saveFeriasSuccessfullyTest() {
//		Funcionario funcionario2 = new Funcionario("Funcionario2", new GregorianCalendar(1992, 07, 14),
//				new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"),
//				new GregorianCalendar(2017, 8, 28));
//
//		Funcionario funcionario3 = new Funcionario("Funcionario3", new GregorianCalendar(1992, 07, 14),
//				new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"),
//				new GregorianCalendar(2017, 8, 28));
//
//		Funcionario funcionario4 = new Funcionario("Funcionario4", new GregorianCalendar(1992, 07, 14),
//				new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"),
//				new GregorianCalendar(2017, 8, 28));
//
//		Funcionario funcionario5 = new Funcionario("Funcionario5", new GregorianCalendar(1992, 07, 14),
//				new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"),
//				new GregorianCalendar(2017, 8, 28));
//
//		Funcionario funcionario6 = new Funcionario("Funcionario6", new GregorianCalendar(1992, 07, 14),
//				new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"),
//				new GregorianCalendar(2017, 8, 28));
//
//		Equipe equipe = equipeRepository.save(new Equipe("teste", new ArrayList<Funcionario>(
//				Arrays.asList(funcionario6, funcionario2, funcionario3, funcionario4, funcionario5))));
//
//		Ferias ferias2 = feriasRepository
//				.save(new Ferias(funcionario5, new GregorianCalendar(2020, 8, 28), new GregorianCalendar(2020, 9, 28)));

		HttpEntity<Ferias> requestBody = new HttpEntity<>(ferias);

		ResponseEntity<Ferias> res = restTemplate.exchange("/ferias", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Ferias>() {
				});

		Ferias body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(ferias.getFuncionario().getMatricula(), body.getFuncionario().getMatricula());
		assertEquals(ferias.getPeriodoInicial().toInstant(), body.getPeriodoInicial().toInstant());
		assertEquals(ferias.getPeriodoFinal().toInstant(), body.getPeriodoFinal().toInstant());

	}

	@Test
	public void saveFeriasWithoutEquipeCom4FuncionariosTest() {

		HttpEntity<Ferias> requestBody = new HttpEntity<>(ferias);

		ResponseEntity<Ferias> res = restTemplate.exchange("/ferias", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Ferias>() {
				});

		assertEquals(HttpStatus.OK, res.getStatusCode());

	}

	@Test
	public void saveFeriasWithoutFuncionarioTest() throws Exception {

		FeriasDTO feriasDTO = new FeriasDTO();
		feriasDTO.setFuncionario(null);
		feriasDTO.setPeriodoInicial(new GregorianCalendar(2020, 8, 28));
		feriasDTO.setPeriodoFinal(new GregorianCalendar(2020, 9, 28));
		HttpEntity<FeriasDTO> requestBody = new HttpEntity<>(feriasDTO);

		ResponseEntity<FeriasDTO> res = restTemplate.exchange("/ferias", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<FeriasDTO>() {
				});

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
	}

	@Test
	public void saveEquipeWithoutPeriodoInicialTest() throws Exception {

		FeriasDTO feriasDTO = new FeriasDTO();
		feriasDTO.setFuncionario(new Funcionario("Funcionario", new GregorianCalendar(1992, 07, 14),
				new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"),
				new GregorianCalendar(2019, 8, 28)));
		feriasDTO.setPeriodoInicial(null);
		feriasDTO.setPeriodoFinal(new GregorianCalendar(2020, 9, 28));
		HttpEntity<FeriasDTO> requestBody = new HttpEntity<>(feriasDTO);

		ResponseEntity<FeriasDTO> res = restTemplate.exchange("/ferias", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<FeriasDTO>() {
				});

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
	}

	@Test
	public void saveEquipeWithoutPeriodoFinalTest() throws Exception {

		FeriasDTO feriasDTO = new FeriasDTO();
		feriasDTO.setFuncionario(new Funcionario("Funcionario", new GregorianCalendar(1992, 07, 14),
				new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"),
				new GregorianCalendar(2019, 8, 28)));
		feriasDTO.setPeriodoFinal(null);
		feriasDTO.setPeriodoInicial(new GregorianCalendar(2020, 9, 28));

		HttpEntity<FeriasDTO> requestBody = new HttpEntity<>(feriasDTO);

		ResponseEntity<FeriasDTO> res = restTemplate.exchange("/ferias", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<FeriasDTO>() {
				});

		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
	}

	@Test
	public void updateFeriasSuccessfullyTest() throws Exception {

		ferias.setPeriodoInicial(new GregorianCalendar(2020, 9, 12));
		HttpEntity<Ferias> requestBody = new HttpEntity<>(ferias);

		ResponseEntity<Ferias> res = restTemplate.exchange("/ferias/" + ferias.getId(), HttpMethod.PUT, requestBody,
				new ParameterizedTypeReference<Ferias>() {
				});

		Ferias body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(ferias.getPeriodoInicial().toInstant(), body.getPeriodoInicial().toInstant());
	}

	@Test
	public void updateFeriasNotFoundTest() throws Exception {

		HttpEntity<Ferias> requestBody = new HttpEntity<>(ferias);

		ResponseEntity<Ferias> res = restTemplate.exchange("/ferias/98098", HttpMethod.PUT, requestBody,
				new ParameterizedTypeReference<Ferias>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	public void getFeriasSuccessfullyTest() throws Exception {

		ResponseEntity<Ferias> res = restTemplate.exchange("/ferias/" + ferias.getId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<Ferias>() {
				});

		Ferias body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(ferias.getId(), body.getId());
		assertEquals(ferias.getFuncionario().getMatricula(), body.getFuncionario().getMatricula());
		assertEquals(ferias.getPeriodoInicial().toInstant(), body.getPeriodoInicial().toInstant());
		assertEquals(ferias.getPeriodoFinal().toInstant(), body.getPeriodoFinal().toInstant());

	}

	@Test
	public void getFeriasNotFoundTest() throws Exception {

		ResponseEntity<Ferias> res = restTemplate.exchange("/ferias/9087", HttpMethod.GET, null,
				new ParameterizedTypeReference<Ferias>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	public void deleteFeriasSuccessfullyTest() throws Exception {

		ResponseEntity<Ferias> res = restTemplate.exchange("/ferias/" + ferias.getId(), HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Ferias>() {
				});

		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

}
