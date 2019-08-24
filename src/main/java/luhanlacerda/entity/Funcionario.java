package luhanlacerda.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer matricula;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar dataDeNascimento;

	@Embedded
	private Endereco endereco;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar dataDeContratacao;

	// TODO implementar atributo FOTO!

	@ManyToOne
	@JoinColumn(name = "equipe_id")
	@JsonBackReference
	private Equipe equipe;

	public Funcionario(String nome, Calendar dataDeNascimento, Endereco endereco, Calendar dataDeContratacao) {
		super();
		this.nome = nome;
		this.dataDeNascimento = dataDeNascimento;
		this.endereco = endereco;
		this.dataDeContratacao = dataDeContratacao;
	}

	public Funcionario(String nome) {
		super();
		this.nome = nome;
	}

	public Funcionario(Integer matricula, String nome, Calendar dataDeNascimento, Endereco endereco,
			Calendar dataDeContratacao, Equipe equipe) {
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.dataDeNascimento = dataDeNascimento;
		this.endereco = endereco;
		this.dataDeContratacao = dataDeContratacao;
		this.equipe = equipe;
	}

	public Funcionario() {
		super();
		this.endereco = new Endereco();
		this.equipe = new Equipe();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Calendar getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Calendar dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Calendar getDataDeContratacao() {
		return dataDeContratacao;
	}

	public void setDataDeContratacao(Calendar dataDeContratacao) {
		this.dataDeContratacao = dataDeContratacao;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataDeContratacao == null) ? 0 : dataDeContratacao.hashCode());
		result = prime * result + ((dataDeNascimento == null) ? 0 : dataDeNascimento.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((equipe == null) ? 0 : equipe.hashCode());
		result = prime * result + matricula;
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
		Funcionario other = (Funcionario) obj;
		if (dataDeContratacao == null) {
			if (other.dataDeContratacao != null)
				return false;
		} else if (!dataDeContratacao.equals(other.dataDeContratacao))
			return false;
		if (dataDeNascimento == null) {
			if (other.dataDeNascimento != null)
				return false;
		} else if (!dataDeNascimento.equals(other.dataDeNascimento))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (equipe == null) {
			if (other.equipe != null)
				return false;
		} else if (!equipe.equals(other.equipe))
			return false;
		if (matricula != other.matricula)
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
		return "Funcionario [nome=" + nome + ", dataDeNascimento=" + dataDeNascimento + ", endereco=" + endereco
				+ ", dataDeContratacao=" + dataDeContratacao + ", equipe=" + equipe + ", matricula=" + matricula + "]";
	}

}
