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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import luhanlacerda.AssignmentApiApplication;
import luhanlacerda.dto.EquipeDTO;
import luhanlacerda.entity.Equipe;
import luhanlacerda.repository.EquipeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
		AssignmentApiApplication.class })
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class EquipeControllerTest {

	@Autowired
	private EquipeRepository equipeRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	private Equipe equipe;

	@Before
	public void before() {
		equipeRepository.deleteAll();

		equipe = equipeRepository.save(new Equipe("Equipe Teste"));
	}

	@Test
	public void listEquipeSuccessfullyTest() {

		ResponseEntity<List<Equipe>> res = restTemplate.exchange("/equipes", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Equipe>>() {
				});

		List<Equipe> body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertThat(body.size(), is(1));

		Equipe firstRow = body.get(0);
		assertEquals(firstRow.getId(), equipe.getId());
		assertEquals(firstRow.getNome(), equipe.getNome());
		assertEquals(firstRow.getListFuncionario(), equipe.getListFuncionario());
	}

	@Test
	public void saveEquipeSuccessfullyTest() {

		HttpEntity<Equipe> requestBody = new HttpEntity<>(equipe);

		ResponseEntity<Equipe> res = restTemplate.exchange("/equipes", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Equipe>() {
				});

		Equipe body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(equipe.getNome(), body.getNome());
		// TODO ajustar
		assertEquals(equipe.getListFuncionario(), body.getListFuncionario());

	}

	@Test
	public void saveEquipeWithoutNomeTest() throws Exception {

		EquipeDTO equipeDTO = new EquipeDTO();
		equipeDTO.setNome(null);
		equipeDTO.setListFuncionario(null);
		HttpEntity<EquipeDTO> requestBody = new HttpEntity<>(equipeDTO);

		ResponseEntity<EquipeDTO> res = restTemplate.exchange("/equipes", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<EquipeDTO>() {
				});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
	}

	@Test
	public void saveEquipeWithoutFuncionariosTest() throws Exception {

		EquipeDTO equipeDTO = new EquipeDTO();
		equipeDTO.setNome("Equipe");
		equipeDTO.setListFuncionario(null);
		HttpEntity<EquipeDTO> requestBody = new HttpEntity<>(equipeDTO);

		ResponseEntity<EquipeDTO> res = restTemplate.exchange("/equipes", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<EquipeDTO>() {
				});

		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	public void updateSignintSuccessfullyTest() throws Exception {

		equipe.setNome("novo nome da equipe");
		HttpEntity<Equipe> requestBody = new HttpEntity<>(equipe);

		ResponseEntity<Equipe> res = restTemplate.exchange("/equipes/" + equipe.getId(), HttpMethod.PUT, requestBody,
				new ParameterizedTypeReference<Equipe>() {
				});

		Equipe body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(equipe.getNome(), body.getNome());
	}

	@Test
	public void updateEquipeNotFoundTest() throws Exception {

		HttpEntity<Equipe> requestBody = new HttpEntity<>(equipe);

		ResponseEntity<Equipe> res = restTemplate.exchange("/equipes/98098", HttpMethod.PUT, requestBody,
				new ParameterizedTypeReference<Equipe>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	public void getEquipeSuccessfullyTest() throws Exception {

		ResponseEntity<Equipe> res = restTemplate.exchange("/equipes/" + equipe.getId(), HttpMethod.GET, null,
				new ParameterizedTypeReference<Equipe>() {
				});

		Equipe body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(equipe.getId(), body.getId());
		assertEquals(equipe.getNome(), body.getNome());
		// TODO ajustar
		assertEquals(equipe.getListFuncionario().toArray(), body.getListFuncionario());

	}

	@Test
	public void getEquipeNotFoundTest() throws Exception {

		ResponseEntity<Equipe> res = restTemplate.exchange("/equipes/9087", HttpMethod.GET, null,
				new ParameterizedTypeReference<Equipe>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	public void deleteEquipeSuccessfullyTest() throws Exception {

		ResponseEntity<Equipe> res = restTemplate.exchange("/equipes/" + equipe.getId(), HttpMethod.DELETE, null,
				new ParameterizedTypeReference<Equipe>() {
				});

		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

}
