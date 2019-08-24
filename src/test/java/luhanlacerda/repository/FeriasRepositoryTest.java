package luhanlacerda.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
import luhanlacerda.entity.Ferias;
import luhanlacerda.entity.Funcionario;
import luhanlacerda.utils.ConvertDate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AssignmentApiApplication.class, H2TestConfig.class })
public class FeriasRepositoryTest {

	@Autowired
	private FeriasRepository feriasRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	private Optional<Funcionario> funcionario;

	@Before
	public void cleanUp() {
		feriasRepository.deleteAll();
	}

	@Test
	public void insertFeriasTest() {
		funcionarioRepository.save(new Funcionario("Funcionario"));
		funcionario = funcionarioRepository.findById(1);

		Ferias ferias = new Ferias(funcionario.get(), new ConvertDate().stringToDateDDMMYYYY("28/08/2018"),
				new ConvertDate().stringToDateDDMMYYYY("28/09/2018"));

		feriasRepository.save(ferias);

		List<Ferias> foundFerias = (List<Ferias>) feriasRepository.findAll();

		assertThat(foundFerias).hasSize(1);
	}

	@Test
	public void findFeriasByIdTest() {
		funcionarioRepository.save(new Funcionario("Funcionario"));
		funcionario = funcionarioRepository.findById(1);

		Ferias ferias = new Ferias(funcionario.get(), new ConvertDate().stringToDateDDMMYYYY("28/08/2018"),
				new ConvertDate().stringToDateDDMMYYYY("28/09/2018"));

		feriasRepository.save(ferias);

		Optional<Ferias> foundFerias = feriasRepository.findById(6);

		assertThat(foundFerias).isNotEmpty();
	}

	@Test
	public void findFeriasByMatriculaFuncionarioTest() {
		funcionarioRepository.save(new Funcionario("Funcionario"));
		funcionario = funcionarioRepository.findById(1);

		Ferias ferias = new Ferias(funcionario.get(), new ConvertDate().stringToDateDDMMYYYY("28/08/2018"),
				new ConvertDate().stringToDateDDMMYYYY("28/09/2018"));

		feriasRepository.save(ferias);

		Optional<Ferias> feriasFromRepository = feriasRepository
				.findByMatriculaFuncionario(funcionario.get().getMatricula());

		assertThat(feriasFromRepository).isNotEmpty();
	}

}
