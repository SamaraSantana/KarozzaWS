package br.com.senai.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.model.Automovel;
import br.com.senai.model.Exemplar;
import br.com.senai.model.Loja;

@Repository
public class ExemplarDao {
	private Connection conexao;

	@Autowired
	public ExemplarDao(DataSource data) {
		try {
			this.conexao = data.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void adiciona(Exemplar exemplares) {
		String sql = "INSERT INTO exemplar (placa,renavam,chassi,odometro,cor,status,foto,loja_id,automovel_id) values(?,?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			start.setString(1, exemplares.getPlaca());
			start.setString(2, exemplares.getRenavam());
			start.setString(3, exemplares.getChassi());
			start.setString(4, exemplares.getOdometro());
			start.setString(5, exemplares.getCor());
			start.setString(6, exemplares.getStatus());
			start.setBlob(7,( exemplares.getFoto() == null) ? null : new ByteArrayInputStream(exemplares.getFoto()));
			start.setString(8, exemplares.getLoja());
			start.setString(9, exemplares.getAutomovel());

			start.execute();
			start.close();
			conexao.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Loja> listarLoja() {
		try {
			List<Loja> lista = new ArrayList<Loja>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from loja");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Loja l = new Loja();
				l.setId(rs.getInt("id"));
				l.setCnpj(rs.getString("cnpj"));
				l.setTipo(rs.getString("tipo"));
				l.setBairro(rs.getString("bairro"));

				lista.add(l);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Automovel> listarAutomovel() {
		try {
			List<Automovel> lista = new ArrayList<Automovel>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from automovel");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Automovel a = new Automovel();
				a.setId(rs.getInt("id"));
				a.setMarca(rs.getString("marca"));
				a.setModelo(rs.getString("modelo"));

				lista.add(a);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void alterar(Exemplar exemplares) {
		try {
			PreparedStatement start = conexao
					.prepareStatement("update exemplar set placa=?,renavam=?,chassi=?,odometro=?,cor=?,status=?,loja_id=?,automovel_id=? where id=?");
			start.setString(1, exemplares.getPlaca());
			start.setString(2, exemplares.getRenavam());
			start.setString(3, exemplares.getChassi());
			start.setString(4, exemplares.getOdometro());
			start.setString(5, exemplares.getCor());
			start.setString(6, exemplares.getStatus());
			start.setString(7, exemplares.getLoja());
			start.setString(8, exemplares.getAutomovel());
			start.setLong(9, exemplares.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Exemplar> listar() {
		try {
			List<Exemplar> lista = new ArrayList<Exemplar>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from exemplar,automovel where automovel.id = exemplar.automovel_id");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Exemplar e = new Exemplar();
				e.setId(rs.getInt("id"));
				e.setPlaca(rs.getString("placa"));
				e.setRenavam(rs.getString("renavam"));
				e.setChassi(rs.getString("chassi"));
				e.setOdometro(rs.getString("odometro"));
				e.setCor(rs.getString("cor"));
				e.setStatus(rs.getString("status"));
				e.setFoto(rs.getBytes("foto"));
				e.setLoja(rs.getString("loja_id"));
				e.setAutomovel(rs.getString("automovel.modelo"));

				lista.add(e);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Exemplar buscaId(int id) {
		Exemplar e = null;
		String sql = "select * from exemplar where id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				e = new Exemplar();
				e.setId(rs.getInt("id"));
				e.setPlaca(rs.getString("placa"));
				e.setRenavam(rs.getString("renavam"));
				e.setChassi(rs.getString("chassi"));
				e.setOdometro(rs.getString("odometro"));
				e.setCor(rs.getString("cor"));
				e.setStatus(rs.getString("status"));
				e.setFoto(rs.getBytes("foto"));
				e.setLoja(rs.getString("loja_id"));
				e.setAutomovel(rs.getString("automovel_id"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e2) {

		}
		return e;
	}
	
	public void remover(Exemplar exemplares) {
		try {
			PreparedStatement start = conexao
					.prepareStatement("delete from exemplar where id=?");
			start.setLong(1, exemplares.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
