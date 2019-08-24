package luhanlacerda.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Ferias {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Calendar periodoInicial;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Calendar periodoFinal;

	public Ferias(Integer id, Funcionario funcionario, Calendar periodoInicial, Calendar periodoFinal) {
		super();
		this.id = id;
		this.funcionario = funcionario;
		this.periodoInicial = periodoInicial;
		this.periodoFinal = periodoFinal;
	}

	public Ferias(Funcionario funcionario, Calendar periodoInicial, Calendar periodoFinal) {
		super();
		this.funcionario = funcionario;
		this.periodoInicial = periodoInicial;
		this.periodoFinal = periodoFinal;
	}

	public Ferias() {
		super();
		this.funcionario = new Funcionario();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((periodoFinal == null) ? 0 : periodoFinal.hashCode());
		result = prime * result + ((periodoInicial == null) ? 0 : periodoInicial.hashCode());
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
		Ferias other = (Ferias) obj;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (periodoFinal == null) {
			if (other.periodoFinal != null)
				return false;
		} else if (!periodoFinal.equals(other.periodoFinal))
			return false;
		if (periodoInicial == null) {
			if (other.periodoInicial != null)
				return false;
		} else if (!periodoInicial.equals(other.periodoInicial))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ferias [id=" + id + ", funcionario=" + funcionario + ", periodoInicial=" + periodoInicial
				+ ", periodoFinal=" + periodoFinal + "]";
	}

}
