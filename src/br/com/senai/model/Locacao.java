package br.com.senai.model;

public class Locacao {
	private long id;
	
	private String local_Reserva;
	private String data_Retirada;
	private String hora_Retirada;
	private String data_Prevista;
	private String hora_Prevista;
	private String protecao;
	private String grupo;
	private long opcional[];
	private String cliente;
	private String exemplar;
	
	public String getExemplar() {
		return exemplar;
	}
	public void setExemplar(String exemplar) {
		this.exemplar = exemplar;
	}
	public long[] getOpcional() {
		return opcional;
	}
	public void setOpcional(long[] opcional) {
		this.opcional = opcional;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLocal_Reserva() {
		return local_Reserva;
	}
	public void setLocal_Reserva(String local_Reserva) {
		this.local_Reserva = local_Reserva;
	}
	public String getData_Retirada() {
		return data_Retirada;
	}
	public void setData_Retirada(String data_Retirada) {
		this.data_Retirada = data_Retirada;
	}
	public String getHora_Retirada() {
		return hora_Retirada;
	}
	public void setHora_Retirada(String hora_Retirada) {
		this.hora_Retirada = hora_Retirada;
	}
	public String getData_Prevista() {
		return data_Prevista;
	}
	public void setData_Prevista(String data_Prevista) {
		this.data_Prevista = data_Prevista;
	}
	public String getHora_Prevista() {
		return hora_Prevista;
	}
	public void setHora_Prevista(String hora_Prevista) {
		this.hora_Prevista = hora_Prevista;
	}
	public String getProtecao() {
		return protecao;
	}
	public void setProtecao(String protecao) {
		this.protecao = protecao;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
}
