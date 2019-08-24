package luhanlacerda.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import luhanlacerda.entity.Ferias;

public interface FeriasRepository extends CrudRepository<Ferias, Integer> {

	@Query("SELECT f FROM Ferias f WHERE f.funcionario.matricula = :matricula")
	public Optional<Ferias> findByMatriculaFuncionario(@Param("matricula") Integer matricula);

	@Query("SELECT f FROM Ferias f WHERE f.funcionario.equipe.id = :equipeId")
	public List<Ferias> findByEquipe(@Param("equipeId") Integer equipeId);

}
