package com.apirest.MedicamentoSolidario.Models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_RECEBIMENTO")
public class Recebimento {
	// MEDICAMENTO_OUT
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private long id;
	private LocalDateTime dataRetirada;
	private String obs;

	@ManyToOne
	@JoinColumn(name = "voluntario_id")
	private Usuario voluntario;
	@ManyToOne
	@JoinColumn(name = "ponto_coleta_id")
	private PontoColeta ponto;
	@OneToOne
	private Pedido pedido;

	public Recebimento(long id2, LocalDateTime data2, String obs2, Usuario voluntario2, PontoColeta ponto2,
			Pedido pedido2) {
		this.id = id2;
		this.dataRetirada = data2;
		this.obs = obs2;
		this.voluntario = voluntario2;
		this.ponto = ponto2;
		this.pedido = pedido2;
	}

	public Recebimento(LocalDateTime data2, String obs2, Usuario voluntario2, PontoColeta ponto2, Pedido pedido2) {
		this.dataRetirada = data2;
		this.obs = obs2;
		this.voluntario = voluntario2;
		this.ponto = ponto2;
		this.pedido = pedido2;
	}

	public Recebimento() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Usuario getVoluntario() {
		return voluntario;
	}

	public void setVoluntario(Usuario voluntario) {
		this.voluntario = voluntario;
	}

	public PontoColeta getPonto() {
		return ponto;
	}

	public void setPonto(PontoColeta ponto) {
		this.ponto = ponto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public LocalDateTime getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(LocalDateTime dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

}
