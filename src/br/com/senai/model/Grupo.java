package br.com.senai.model;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class Grupo {
	private long id;
	private String nome;
	private String descricao;
	private double preco;
	private byte[] foto;
	private String loja;

	public String getLoja() {
		return loja;
	}

	public void setLoja(String loja) {
		this.loja = loja;
	}

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
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
