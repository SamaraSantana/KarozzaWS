package br.com.senai.model;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Vistoria {
	private long id;
	// vetor de bytes
	private byte[] foto;
	private int km;
	private String documentacao;
	private String ferramentas;
	private String opcionais;
	private String outros;
	private String observacao;
	private String status;
	private String locacao;
	private String exemplar;
	private String manutencao;

	public String getLocacao() {
		return locacao;
	}

	public void setLocacao(String locacao) {
		this.locacao = locacao;
	}

	public String getExemplar() {
		return exemplar;
	}

	public void setExemplar(String exemplar) {
		this.exemplar = exemplar;
	}

	public String getManutencao() {
		return manutencao;
	}

	public void setManutencao(String manutencao) {
		this.manutencao = manutencao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public int getKm() {
		return km;
	}

	public void setKm(int km) {
		this.km = km;
	}

	public String getDocumentacao() {
		return documentacao;
	}

	public void setDocumentacao(String documentacao) {
		this.documentacao = documentacao;
	}

	public String getFerramentas() {
		return ferramentas;
	}

	public void setFerramentas(String ferramentas) {
		this.ferramentas = ferramentas;
	}

	public String getOpcionais() {
		return opcionais;
	}

	public void setOpcionais(String opcionais) {
		this.opcionais = opcionais;
	}

	public String getOutros() {
		return outros;
	}

	public void setOutros(String outros) {
		this.outros = outros;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFoto64() {
		// converte os bytes em base 64
		String encodedImage = Base64.encode(foto);
		return encodedImage;
	}

}
