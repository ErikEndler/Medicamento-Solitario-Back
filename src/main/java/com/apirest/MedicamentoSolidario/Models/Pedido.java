package com.apirest.MedicamentoSolidario.Models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PEDIDO")
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private long id;
	private String justificativa;
	private LocalDateTime dataCriacao;
	private String status;
	@ManyToOne
	private Usuario usuario;
	@OneToOne(mappedBy = "pedido")
	private Recebimento recebimento;
	@OneToMany(mappedBy = "pedido")
	private List<PedidoMedicamento> pedido_med;
	@ManyToOne
	private PontoColeta ponto;

	public Pedido() {
	}

	public Pedido(long id2, String justificativa2, Usuario usuario, PontoColeta ponto) {
		this.id = id2;
		this.justificativa = justificativa2;
		this.usuario = usuario;
		this.ponto = ponto;

	}

	public Pedido(String justificativa, LocalDateTime dataCriacao, Usuario usuario, String status, PontoColeta ponto) {
		this.justificativa = justificativa;
		this.setDataCriacao(dataCriacao);
		this.usuario = usuario;
		this.status = status;
		this.ponto = ponto;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Recebimento getRecebimento() {
		return recebimento;
	}

	public void setRecebimento(Recebimento recebimento) {
		this.recebimento = recebimento;
	}

	public List<PedidoMedicamento> getPedido_med() {
		return pedido_med;
	}

	public void setPedido_med(List<PedidoMedicamento> pedido_med) {
		this.pedido_med = pedido_med;
	}

	public PontoColeta getPonto() {
		return ponto;
	}

	public void setPonto(PontoColeta ponto) {
		this.ponto = ponto;
	}
	
}
