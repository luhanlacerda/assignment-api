package luhanlacerda.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import luhanlacerda.utils.CustomListSerializer;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Equipe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2823647818347222201L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(nullable = false)
	private String nome;

	@OneToMany(mappedBy = "equipe", fetch = FetchType.LAZY)
	@JsonSerialize(using = CustomListSerializer.class)
	private List<Funcionario> listFuncionario;

	public Equipe() {
		super();
	}

	public Equipe(String nome, List<Funcionario> listFuncionario) {
		super();
		this.nome = nome;
		this.setListFuncionario(listFuncionario);
	}

	public Equipe(String nome) {
		super();
		this.nome = nome;
	}

	public Equipe(Integer id, String nome, List<Funcionario> listFuncionario) {
		super();
		this.id = id;
		this.nome = nome;
		this.setListFuncionario(listFuncionario);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Funcionario> getListFuncionario() {
		return listFuncionario;
	}

	public void setListFuncionario(List<Funcionario> listFuncionario) {
		this.listFuncionario = listFuncionario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((listFuncionario == null) ? 0 : listFuncionario.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipe other = (Equipe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listFuncionario == null) {
			if (other.listFuncionario != null)
				return false;
		} else if (!listFuncionario.equals(other.listFuncionario))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Equipe [id=" + id + ", nome=" + nome + ", listFuncionario=" + listFuncionario + "]";
	}

}
