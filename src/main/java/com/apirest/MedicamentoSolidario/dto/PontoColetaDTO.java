package com.apirest.MedicamentoSolidario.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.apirest.MedicamentoSolidario.Models.PontoColeta;

public class PontoColetaDTO {
	private long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String cnpj;
	@NotBlank
	private String cidade;
	@NotBlank
	private String estado;
	@NotBlank
	private String rua;
	@NotBlank
	private String bairro;
	@NotBlank
	private String numero;
	
	private String complemento;
	@NotBlank
	private String cep;
	@NotBlank
	private String atividadePrincipal;
	private LocalDateTime dataCadastro;
	
	public PontoColeta transformarParaObjSalvar() {
		return new PontoColeta(nome, cnpj, cidade, estado, rua, bairro,
				numero, complemento, cep, atividadePrincipal, dataCadastro);
	}
	public PontoColeta transformarParaObjEditar() {
		return new PontoColeta(id, nome, cnpj, cidade, estado, rua,
				bairro, numero, complemento, cep, atividadePrincipal, dataCadastro);
	}
	public long getId() {
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
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getAtividadePrincipal() {
		return atividadePrincipal;
	}
	public void setAtividadePrincipal(String atividadePrincipal) {
		this.atividadePrincipal = atividadePrincipal;
	}
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
}
