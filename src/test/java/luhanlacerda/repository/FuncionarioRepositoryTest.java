package luhanlacerda.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import luhanlacerda.AssignmentApiApplication;
import luhanlacerda.configuration.H2TestConfig;
import luhanlacerda.entity.Funcionario;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AssignmentApiApplication.class, H2TestConfig.class })
public class FuncionarioRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Before
	public void cleanUp() {
		funcionarioRepository.deleteAll();
	}

	@Test
	public void insertEquipeTest() {
		Funcionario funcionario = new Funcionario("Funcionario");

		funcionarioRepository.save(funcionario);

		List<Funcionario> foundFuncionario = (List<Funcionario>) funcionarioRepository.findAll();

		assertThat(foundFuncionario).hasSize(1);
	}

	@Test
	public void findEquipeByIdTest() {
		Funcionario funcionario = new Funcionario("Funcionario");

		funcionarioRepository.save(funcionario);

		Optional<Funcionario> foundFuncionario = funcionarioRepository.findById(3);

		assertThat(foundFuncionario).isNotEmpty();
	}

	@Test
	public void findEquipeByNomeTest() {
		Funcionario funcionario = new Funcionario("Funcionario");

		funcionarioRepository.save(funcionario);

		Funcionario funcionarioFromRepository = funcionarioRepository.findByNome("Funcionario");

		assertEquals(funcionarioFromRepository.getNome(), funcionario.getNome());
	}

}
