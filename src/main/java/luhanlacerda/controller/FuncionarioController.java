package luhanlacerda.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import luhanlacerda.dto.FuncionarioDTO;
import luhanlacerda.entity.Equipe;
import luhanlacerda.entity.Funcionario;
import luhanlacerda.repository.EquipeRepository;
import luhanlacerda.repository.FuncionarioRepository;
import luhanlacerda.utils.ConvertDate;

@RestController
@RequestMapping(path = "/funcionarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class FuncionarioController {

	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Autowired
	EquipeRepository equipeRepository;

	@GetMapping
	private ResponseEntity<?> index() throws IOException, URISyntaxException {
		return ResponseEntity.ok(funcionarioRepository.findAll());
	}

	@PostMapping
	private ResponseEntity<?> create(@Valid @RequestBody FuncionarioDTO funcionarioDTO) {

		Optional<Equipe> equipe = funcionarioDTO.getEquipe().getId() != null
				? equipeRepository.findById(funcionarioDTO.getEquipe().getId())
				: Optional.empty();

		Funcionario funcionario = buildFuncionarioEntity(new Funcionario(), funcionarioDTO);
		if (equipe.isPresent()) {
			funcionario.setEquipe(equipe.get());
		}

		funcionarioRepository.save(funcionario);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/{matricula}")
	private ResponseEntity<?> update(@PathVariable(required = true) Integer matricula,
			@Valid @RequestBody FuncionarioDTO funcionarioDTO) {

		Optional<Funcionario> findById = funcionarioRepository.findById(matricula);

		if (!findById.isPresent())
			return ResponseEntity.notFound().build();

		Funcionario funcionario = buildFuncionarioEntity(findById.get(), funcionarioDTO);
		funcionarioRepository.save(funcionario);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/{matricula}")
	private ResponseEntity<?> findOne(@PathVariable(required = true) Integer matricula) {

		Optional<Funcionario> findById = funcionarioRepository.findById(matricula);
		if (findById.isPresent()) {
			FuncionarioDTO func = new FuncionarioDTO();
			func.setMatricula(findById.get().getMatricula());
			func.setNome(findById.get().getNome());
			func.setDataDeContratacao(new ConvertDate().dateToString(findById.get().getDataDeContratacao()));
			func.setDataDeNascimento(new ConvertDate().dateToString(findById.get().getDataDeNascimento()));
			func.setEquipe(findById.get().getEquipe());
			return ResponseEntity.ok(findById.get());
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<?> delete(@PathVariable(required = true) Integer id) {

		funcionarioRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	private Funcionario buildFuncionarioEntity(Funcionario funcionario, @Valid FuncionarioDTO funcionarioDto) {

		funcionario.setDataDeContratacao(new ConvertDate().stringToDateDDMMYYYY(funcionarioDto.getDataDeContratacao()));
		funcionario.setDataDeNascimento(new ConvertDate().stringToDateDDMMYYYY(funcionarioDto.getDataDeNascimento()));
		funcionario.setEndereco(funcionarioDto.getEndereco());
		funcionario.setEquipe(funcionarioDto.getEquipe());
		funcionario.setMatricula(funcionarioDto.getMatricula());
		funcionario.setNome(funcionarioDto.getNome());

		return funcionario;
	}
}
