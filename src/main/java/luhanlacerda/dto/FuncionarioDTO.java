package luhanlacerda.dto;

import luhanlacerda.entity.Endereco;
import luhanlacerda.entity.Equipe;

public class FuncionarioDTO {

	private Integer matricula;

	private String nome;

	private String dataDeNascimento;

	private Endereco endereco;

	private String dataDeContratacao;

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

	public String getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(String dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getDataDeContratacao() {
		return dataDeContratacao;
	}

	public void setDataDeContratacao(String dataDeContratacao) {
		this.dataDeContratacao = dataDeContratacao;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

}
