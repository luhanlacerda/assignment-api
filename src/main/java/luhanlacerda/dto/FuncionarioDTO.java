package luhanlacerda.dto;

import java.util.Calendar;

import luhanlacerda.entity.Endereco;
import luhanlacerda.entity.Equipe;

public class FuncionarioDTO {

	private Integer matricula;

	private String nome;

	private Calendar dataDeNascimento;

	private Endereco endereco;

	private Calendar dataDeContratacao;

	// TODO implementar atributo FOTO!

	private Equipe equipe;

	public FuncionarioDTO() {
		super();
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
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

}
