package luhanlacerda.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import luhanlacerda.dto.FeriasDTO;
import luhanlacerda.entity.Ferias;
import luhanlacerda.repository.FeriasRepository;

@RestController
@RequestMapping(path = "/ferias", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeriasController {

	@Autowired
	FeriasRepository feriasRepository;

	@GetMapping
	private ResponseEntity<?> index() throws IOException, URISyntaxException {
		return ResponseEntity.ok(feriasRepository.findAll());
	}

	@PostMapping
	private ResponseEntity<?> create(@Valid @RequestBody FeriasDTO feriasDTO) {
		// Validacao para ver se o funcionario tem mais de um ano de contratacao

//		Calendar dateToCompare = feriasDTO.getFuncionario().getDataDeContratacao();
//		dateToCompare.add(Calendar.YEAR, 1);
//
//		if (dateToCompare.after(Calendar.getInstance())) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//
//		}

//		if (feriasDTO.getListFuncionarios().isEmpty()
//				|| feriasDTO.getListFuncionarios().stream().filter(f -> f.getEquipe() == null) == null
//				|| feriasDTO.getListFuncionarios().stream().filter(f -> f.getEquipe().getListFuncionario() <= 4) <= 4) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		} else {
		Ferias ferias = buildFeriasEntity(new Ferias(), feriasDTO);

		Ferias feriasSaved = feriasRepository.save(ferias);

		return ResponseEntity.status(HttpStatus.CREATED).body(feriasSaved);
//		}

	}

	@PostMapping("/{id}")
	private ResponseEntity<?> update(@PathVariable(required = true) Integer id,
			@Valid @RequestBody FeriasDTO feriasDTO) {
		Optional<Ferias> findById = feriasRepository.findById(id);

		if (!findById.isPresent())
			return ResponseEntity.notFound().build();

		Ferias ferias = buildFeriasEntity(findById.get(), feriasDTO);

		return ResponseEntity.ok(feriasRepository.save(ferias));
	}

	@GetMapping("/{id}")
	private ResponseEntity<?> findOne(@PathVariable(required = true) Integer id) {
		Optional<Ferias> findById = feriasRepository.findById(id);

		if (findById.isPresent())
			return ResponseEntity.ok(findById.get());

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<?> delete(@PathVariable(required = true) Integer id) {

		feriasRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	private Ferias buildFeriasEntity(Ferias ferias, @Valid FeriasDTO feriasDTO) {

		ferias.setFuncionario(feriasDTO.getFuncionario());
		ferias.setId(feriasDTO.getId());
		ferias.setPeriodoFinal(feriasDTO.getPeriodoFinal());
		ferias.setPeriodoInicial(feriasDTO.getPeriodoInicial());

		return ferias;
	}

}
