package luhanlacerda.dto;

import javax.validation.constraints.NotNull;

import luhanlacerda.entity.Funcionario;

public class FeriasDTO {

	private Integer id;

	@NotNull
	private Funcionario funcionario;

	@NotNull
	private String periodoInicial;

	@NotNull
	private String periodoFinal;

	public FeriasDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(String periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public String getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(String periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

}
