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
import luhanlacerda.entity.Signin;
import luhanlacerda.repository.SigninRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AssignmentApiApplication.class, H2TestConfig.class })
public class SigninRepositoryTest {

	@Autowired
	private SigninRepository signinRepository;

	@Before
	public void cleanUp() {
		signinRepository.deleteAll();
	}

	@Test
	public void getAllSigininsTest() {
		List<Signin> foundSignin = (List<Signin>) signinRepository.findAll();

		assertThat(foundSignin).hasSize(0);
	}

	@Test
	public void insertSigninTest() {
		Signin signin = new Signin("teste@teste.com", "1234");

		signinRepository.save(signin);

		List<Signin> foundSignin = (List<Signin>) signinRepository.findAll();

		assertThat(foundSignin).hasSize(1);
	}

	@Test
	public void findSigninByIdTest() {
		Signin signin = new Signin("teste@teste.com", "1234");

		signinRepository.save(signin);

		Optional<Signin> foundSignin = signinRepository.findById(2);

		assertThat(foundSignin).isNotEmpty();
	}

	@Test
	public void findSingninByEmailTest() {
		Signin signin = new Signin("teste@teste.com", "1234");

		signinRepository.save(signin);

		Signin signinFromRepository = signinRepository.findByEmail("teste@teste.com");

		assertThat(signinFromRepository).isEqualTo(signin);
	}

}
