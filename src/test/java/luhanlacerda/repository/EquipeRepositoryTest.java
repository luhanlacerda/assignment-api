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
import luhanlacerda.entity.Equipe;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AssignmentApiApplication.class, H2TestConfig.class })
public class EquipeRepositoryTest {

	@Autowired
	private EquipeRepository equipeRepository;

	@Before
	public void cleanUp() {
		equipeRepository.deleteAll();
	}

	@Test
	public void insertEquipeTest() {
		Equipe equipe = new Equipe("Equipe Teste");

		equipeRepository.save(equipe);

		List<Equipe> foundEquipe = (List<Equipe>) equipeRepository.findAll();

		assertThat(foundEquipe).hasSize(1);
	}

	@Test
	public void findEquipeByIdTest() {
		Equipe equipe = new Equipe("Equipe Teste");

		equipeRepository.save(equipe);

		Optional<Equipe> foundEquipe = equipeRepository.findById(3);

		assertThat(foundEquipe).isNotEmpty();
	}

	@Test
	public void findEquipeByNomeTest() {
		Equipe equipe = new Equipe("Equipe Teste");

		equipeRepository.save(equipe);

		Equipe equipeFromRepository = equipeRepository.findByNome("Equipe Teste");

		assertEquals(equipeFromRepository.getNome(), equipe.getNome());
	}

}
