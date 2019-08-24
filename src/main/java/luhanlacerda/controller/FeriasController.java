package luhanlacerda.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;
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
import luhanlacerda.entity.Funcionario;
import luhanlacerda.repository.FeriasRepository;
import luhanlacerda.repository.FuncionarioRepository;
import luhanlacerda.utils.ConvertDate;

@RestController
@RequestMapping(path = "/ferias", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeriasController {

	@Autowired
	FeriasRepository feriasRepository;

	@Autowired
	FuncionarioRepository funcionarioRepository;

	@GetMapping
	private ResponseEntity<?> index() throws IOException, URISyntaxException {
		return ResponseEntity.ok(feriasRepository.findAll());
	}

	@PostMapping
	private ResponseEntity<?> create(@Valid @RequestBody FeriasDTO feriasDTO) {
		// Validacao para ver se o funcionario tem mais de um ano de contratacao

		Calendar dateToCompare = Calendar.getInstance();
		dateToCompare.setTime(new ConvertDate().stringToDateDDMMYYYY(feriasDTO.getPeriodoInicial()));
		dateToCompare.add(Calendar.YEAR, 1);

		if (dateToCompare.before(Calendar.getInstance())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		}

		List<Ferias> feriasEquipe = feriasRepository.findByEquipe(feriasDTO.getFuncionario().getEquipe().getId());
		Optional<Ferias> feriasFuncionario = feriasEquipe.stream()
				.filter(f -> f.getFuncionario().getMatricula().equals(feriasDTO.getFuncionario().getMatricula()))
				.findFirst();
		boolean funcionarioEmFerias = feriasFuncionario.isPresent()
				&& feriasFuncionario.get().getPeriodoInicial().after(Calendar.getInstance().getTime());

		if (funcionarioEmFerias || feriasEquipe.size() > 0)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		Optional<Funcionario> funcionario = feriasDTO.getFuncionario().getMatricula() != null
				? funcionarioRepository.findById(feriasDTO.getFuncionario().getMatricula())
				: Optional.empty();

		Ferias ferias = buildFeriasEntity(new Ferias(), feriasDTO);
		if (funcionario.isPresent()) {
			ferias.setFuncionario(funcionario.get());
		}

		feriasRepository.save(ferias);

		return ResponseEntity.ok().build();

	}

	@PostMapping("/{id}")
	private ResponseEntity<?> update(@PathVariable(required = true) Integer id,
			@Valid @RequestBody FeriasDTO feriasDTO) {
		Optional<Ferias> findById = feriasRepository.findById(id);

		if (!findById.isPresent())
			return ResponseEntity.notFound().build();

		Ferias ferias = buildFeriasEntity(findById.get(), feriasDTO);
		feriasRepository.save(ferias);

		return ResponseEntity.ok().build();
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
		ferias.setPeriodoFinal(new ConvertDate().stringToDateDDMMYYYY(feriasDTO.getPeriodoFinal()));
		ferias.setPeriodoInicial(new ConvertDate().stringToDateDDMMYYYY(feriasDTO.getPeriodoInicial()));

		return ferias;
	}

}
