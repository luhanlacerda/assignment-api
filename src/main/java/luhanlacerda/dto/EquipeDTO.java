package luhanlacerda.dto;

import java.util.List;

import luhanlacerda.entity.Funcionario;

public class EquipeDTO {

	private int id;

	private String nome;

	private List<Funcionario> listFuncionario;

	public EquipeDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

}
