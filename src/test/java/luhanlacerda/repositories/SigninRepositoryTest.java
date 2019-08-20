package luhanlacerda.repositories;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import luhanlacerda.entities.Signin;

@RunWith(SpringRunner.class)
public class SigninRepositoryTest {

	@Autowired
	private SigninRepository signinRepository;

	@Test
	public void givenEmptyDBWhenFindOneByNameThenReturnEmptyOptional() {
		List<Signin> foundSignin = (List<Signin>) signinRepository.findAll();

		assertEquals(0, foundSignin.isEmpty());
	}

	@After
	public void cleanUp() {
		signinRepository.deleteAll();
	}

}
