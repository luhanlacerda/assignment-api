package luhanlacerda.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import luhanlacerda.entities.Ferias;

public interface FeriasRepository extends CrudRepository<Ferias, Integer> {

	@Query("SELECT f FROM Fiaser f WHERE f.funcionario.matricula = :matricula")
	public List<Ferias> getFeriasByFuncionario(@Param("matricula") Integer matricula);

}
