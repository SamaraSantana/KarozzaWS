package br.com.senai.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.model.Vistoria;


	
	@Repository
	public class VistoriaDao {
		private Connection conexao;

		@Autowired
		public VistoriaDao(DataSource data) {
			try {
				this.conexao = data.getConnection();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

		public void adiciona(Vistoria vistoria) {
			String sql = "INSERT INTO vistoria (km,documentacao,ferramentas,opcionais,outros,observacao,status,foto,locacao_id,manutencao_id,exemplar_id) values(?,?,?,?,?,?,?,?,?,?,?)";

			try {
				PreparedStatement start = conexao.prepareStatement(sql);
				start.setInt(1, vistoria.getKm());
				start.setString(2, vistoria.getDocumentacao());
				start.setString(3, vistoria.getFerramentas());
				start.setString(4, vistoria.getOpcionais());
				start.setString(5, vistoria.getOutros());
				start.setString(6, vistoria.getObservacao());
				start.setString(7, vistoria.getStatus());
				start.setBlob(8,( vistoria.getFoto() == null) ? null : new ByteArrayInputStream(vistoria.getFoto()));
				start.setString(9, vistoria.getLocacao());
				start.setString(10, vistoria.getManutencao());
				start.setString(11, vistoria.getExemplar());
				
				start.execute();
				start.close();
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

}
