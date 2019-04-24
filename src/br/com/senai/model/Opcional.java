package br.com.senai.model;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Opcional {
	private long id;
	private String nome;
	private Double preco;
	private byte[] foto;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	public String getFoto64() {
		// converte os bytes em base 64
		String encodedImage = Base64.encode(foto);
		return encodedImage;
	}
}
