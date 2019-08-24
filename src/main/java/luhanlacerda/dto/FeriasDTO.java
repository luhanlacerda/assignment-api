package luhanlacerda.dto;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import luhanlacerda.entity.Funcionario;

public class FeriasDTO {

	private Integer id;

	@NotNull
	private Funcionario funcionario;

	@NotNull
	private Calendar periodoInicial;

	@NotNull
	private Calendar periodoFinal;

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

	public Calendar getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Calendar periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Calendar getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Calendar periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

}
