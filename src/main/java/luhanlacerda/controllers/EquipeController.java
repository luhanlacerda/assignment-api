package luhanlacerda.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import luhanlacerda.dtos.EquipeDTO;
import luhanlacerda.entities.Equipe;
import luhanlacerda.repositories.EquipeRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(path = "/equipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class EquipeController {

	@Autowired
	EquipeRepository equipeRepository;

	@GetMapping
	private ResponseEntity<?> index() throws IOException, URISyntaxException {
		return ResponseEntity.ok(equipeRepository.findAll());
	}

	@PostMapping
	private ResponseEntity<?> create(@Valid @RequestBody EquipeDTO equipeDTO) {

		Equipe equipe = buildEquipeEntity(new Equipe(), equipeDTO);

		Equipe equipeSaved = equipeRepository.save(equipe);

		return ResponseEntity.status(HttpStatus.CREATED).body(equipeSaved);
	}

	@PutMapping("/{id}")
	private ResponseEntity<?> update(@PathVariable(required = true) Integer id,
			@Valid @RequestBody EquipeDTO equipeDTO) {
		Optional<Equipe> findById = equipeRepository.findById(id);

		if (!findById.isPresent())
			return ResponseEntity.notFound().build();

		Equipe equipe = buildEquipeEntity(findById.get(), equipeDTO);

		return ResponseEntity.ok(equipeRepository.save(equipe));
	}

	@GetMapping("/{id}")
	private ResponseEntity<?> findOne(@PathVariable(required = true) Integer id) {
		Optional<Equipe> findById = equipeRepository.findById(id);

		if (findById.isPresent())
			return ResponseEntity.ok(findById.get());

		return ResponseEntity.notFound().build();
	}

	private Equipe buildEquipeEntity(Equipe equipe, @Valid EquipeDTO equipeDto) {

		equipe.setNome(equipeDto.getNome());
		equipe.setListFuncionario(equipeDto.getListFuncionario());

		return equipe;
	}

}
