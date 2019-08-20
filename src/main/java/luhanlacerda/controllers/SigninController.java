package luhanlacerda.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import luhanlacerda.dtos.SigninDTO;
import luhanlacerda.entities.Signin;
import luhanlacerda.repositories.SigninRepository;

@RestController
@RequestMapping(path = "/signins", produces = MediaType.APPLICATION_JSON_VALUE)
public class SigninController {

	@Autowired
	SigninRepository signinRepository;

	@GetMapping
	private ResponseEntity<?> index() throws IOException, URISyntaxException {
		return ResponseEntity.ok(signinRepository.findAll());
	}

	@PostMapping
	private ResponseEntity<?> create(@Valid @RequestBody SigninDTO signinDTO) {

		Signin signin = buildSigninEntity(new Signin(), signinDTO);

		Signin equipeSaved = signinRepository.save(signin);

		return ResponseEntity.status(HttpStatus.CREATED).body(equipeSaved);
	}

	@PutMapping("/{id}")
	private ResponseEntity<?> update(@PathVariable(required = true) Integer id,
			@Valid @RequestBody SigninDTO signinDTO) {
		Optional<Signin> findById = signinRepository.findById(id);

		if (!findById.isPresent())
			return ResponseEntity.notFound().build();

		Signin signin = buildSigninEntity(findById.get(), signinDTO);

		return ResponseEntity.ok(signinRepository.save(signin));
	}

	@GetMapping("/{id}")
	private ResponseEntity<?> findOne(@PathVariable(required = true) Integer id) {
		Optional<Signin> findById = signinRepository.findById(id);

		if (findById.isPresent())
			return ResponseEntity.ok(findById.get());

		return ResponseEntity.notFound().build();
	}

	private Signin buildSigninEntity(Signin signin, @Valid SigninDTO signinDto) {

		signin.setEmail(signinDto.getEmail());
		signin.setPassword(signinDto.getPassword());

		return signin;
	}
}
