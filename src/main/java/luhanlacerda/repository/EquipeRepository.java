package luhanlacerda.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import luhanlacerda.entity.Equipe;

public interface EquipeRepository extends CrudRepository<Equipe, Integer> {

	@Query("SELECT e FROM Equipe e WHERE e.nome = :nome")
	public Equipe findByNome(@Param("nome") String nome);
	
}
