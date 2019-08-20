package luhanlacerda.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import luhanlacerda.entity.Signin;

public interface SigninRepository extends CrudRepository<Signin, Integer> {

	@Query("SELECT s FROM Signin s WHERE s.email = :email")
	public Signin findByEmail(@Param("email") String email);

}
