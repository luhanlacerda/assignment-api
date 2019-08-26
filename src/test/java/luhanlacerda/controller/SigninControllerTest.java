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
import luhanlacerda.dto.SigninDTO;
import luhanlacerda.entity.Signin;
import luhanlacerda.repository.SigninRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
		AssignmentApiApplication.class })
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class SigninControllerTest {

	@Autowired
	private SigninRepository signinRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	private Signin signin;
	
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

		signinRepository.deleteAll();

		signin = signinRepository.save(new Signin("teste@teste.com", "123"));
	}

	@Test
	public void listSigninSuccessfullyTest() {

		ResponseEntity<List<Signin>> res = restTemplate.exchange("/signins", HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Signin>>() {
				});

		List<Signin> body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertThat(body.size(), is(1));

		Signin firstRow = body.get(0);
		assertEquals(firstRow.getId(), signin.getId());
		assertEquals(firstRow.getEmail(), signin.getEmail());
		assertEquals(firstRow.getPassword(), signin.getPassword());
	}

	@Test
	public void saveSigninSuccessfullyTest() {

		HttpEntity<Signin> requestBody = new HttpEntity<>(signin, header);

		ResponseEntity<Signin> res = restTemplate.exchange("/signins", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Signin>() {
				});

		Signin body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(signin.getEmail(), body.getEmail());
		assertEquals(signin.getPassword(), body.getPassword());

	}

	@Test
	public void saveSigninWithoutEmailTest() throws Exception {

		SigninDTO signinDTO = new SigninDTO();
		signinDTO.setEmail(null);
		signinDTO.setPassword("teste");
		HttpEntity<SigninDTO> requestBody = new HttpEntity<>(signinDTO, header);

		ResponseEntity<Signin> res = restTemplate.exchange("/signins", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Signin>() {
				});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
	}

	@Test
	public void saveSigninWithoutPasswordTest() throws Exception {

		SigninDTO signinDTO = new SigninDTO();
		signinDTO.setEmail("teste@teste.com");
		signinDTO.setPassword(null);
		HttpEntity<SigninDTO> requestBody = new HttpEntity<>(signinDTO, header);

		ResponseEntity<Signin> res = restTemplate.exchange("/signins", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Signin>() {
				});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
	}

	@Test
	public void updateSignintSuccessfullyTest() throws Exception {

		signin.setEmail("novo_email@teste.com");
		signin.setPassword("novas_senha");
		HttpEntity<Signin> requestBody = new HttpEntity<>(signin, header);

		ResponseEntity<Signin> res = restTemplate.exchange("/signins/" + signin.getId(), HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Signin>() {
				});

		Signin body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(signin.getEmail(), body.getEmail());
		assertEquals(signin.getPassword(), body.getPassword());
	}

	@Test
	public void updateSigninNotFoundTest() throws Exception {

		HttpEntity<Signin> requestBody = new HttpEntity<>(signin, header);

		ResponseEntity<Signin> res = restTemplate.exchange("/signins/98098", HttpMethod.POST, requestBody,
				new ParameterizedTypeReference<Signin>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	public void getSigninSuccessfullyTest() throws Exception {

		ResponseEntity<Signin> res = restTemplate.exchange("/signins/" + signin.getId(), HttpMethod.GET, entity,
				new ParameterizedTypeReference<Signin>() {
				});

		Signin body = res.getBody();

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(signin.getId(), body.getId());
		assertEquals(signin.getEmail(), body.getEmail());
		assertEquals(signin.getPassword(), body.getPassword());

	}

	@Test
	public void getSigninNotFoundTest() throws Exception {

		ResponseEntity<Signin> res = restTemplate.exchange("/signins/9087", HttpMethod.GET, entity,
				new ParameterizedTypeReference<Signin>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

	@Test
	public void deleteSigninSuccessfullyTest() throws Exception {

		ResponseEntity<Signin> res = restTemplate.exchange("/signins/" + signin.getId(), HttpMethod.DELETE, entity,
				new ParameterizedTypeReference<Signin>() {
				});

		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

}
