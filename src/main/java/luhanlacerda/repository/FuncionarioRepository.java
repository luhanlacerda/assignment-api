package luhanlacerda.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import luhanlacerda.entity.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {

	@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome")
	public Funcionario findByNome(@Param("nome") String nome);
}
