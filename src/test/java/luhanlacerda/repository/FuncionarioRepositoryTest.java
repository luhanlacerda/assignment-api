package luhanlacerda.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.GregorianCalendar;
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
import luhanlacerda.entity.Endereco;
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
	public void insertFuncionarioTest() {
		Funcionario funcionario = new Funcionario("Funcionario", new GregorianCalendar(1992, 07, 14),
				new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"),
				new GregorianCalendar(2019, 8, 28));

		funcionarioRepository.save(funcionario);

		List<Funcionario> foundFuncionario = (List<Funcionario>) funcionarioRepository.findAll();

		assertThat(foundFuncionario).hasSize(1);
	}

	@Test
	public void findFuncionarioByIdTest() {
		Funcionario funcionario = new Funcionario("Funcionario", new GregorianCalendar(1992, 07, 14),
				new Endereco("rua 01", "121", "APTO 102", "Recife Antigo", "Recife", "PE"),
				new GregorianCalendar(2019, 8, 28));

		funcionarioRepository.save(funcionario);

		Optional<Funcionario> foundFuncionario = funcionarioRepository.findById(3);

		assertThat(foundFuncionario).isNotEmpty();
	}

}
