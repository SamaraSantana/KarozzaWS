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

import br.com.senai.model.Protecao;

@Repository
public class ProtecaoDao {

	private final Connection conexao;

	@Autowired
	public ProtecaoDao(DataSource dataSource) {
		try {
			this.conexao = dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("passou aqui");
			throw new RuntimeException(e);
		}
	}

	public void adiciona(Protecao protecao) {
		String sql = "INSERT INTO protecao (descricao,preco,foto) values(?,?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			start.setString(1, protecao.getDescricao());
			start.setDouble(2, protecao.getPreco());
			start.setBlob(3,( protecao.getFoto() == null) ? null : new ByteArrayInputStream(protecao.getFoto()));


			start.execute();
			start.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// metodo selecionar
	public List<Protecao> listaProtecao() {
		try {
			List<Protecao> protecoes = new ArrayList<Protecao>();
			PreparedStatement stmt = this.conexao.prepareStatement("Select * from protecao");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Protecao protecao = new Protecao();
				protecao.setId((int) rs.getLong("id"));
				protecao.setDescricao(rs.getString("descricao"));
				protecao.setPreco(rs.getDouble("preco"));
				protecao.setFoto(rs.getBytes("foto"));

				protecoes.add(protecao);

			}
			rs.close();
			stmt.close();
			return protecoes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover(Protecao protecao) {
		try {

			PreparedStatement stmt = conexao.prepareStatement("delete from protecao where id=?");
			stmt.setLong(1, protecao.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	// metodo buscar id
	public Protecao buscaId(long id) {

		Protecao p = null;
		String sql = "SELECT * FROM protecao WHERE id =?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				p = new Protecao();
				p.setId(id);
				p.setDescricao(rs.getString("descricao"));
				p.setPreco(rs.getDouble("preco"));
				p.setFoto(rs.getBytes("foto"));

			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return p;
	}

	// metodo buscar id
	public Protecao buscar(String descricao) {
		System.out.println("passou aqui");
		System.out.println(descricao);
		Protecao p = null;
		String sql = "SELECT * FROM opcional WHERE (nome =?)";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, descricao);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				p = new Protecao();
				p.setId(rs.getLong("id"));
				p.setDescricao(rs.getString("descricao"));

			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return p;
	}

	// metodo de alterar
	public void alterarProtecao(Protecao protecao) {
		String sql = "update protecao set descricao=?,preco=?  where id=?";

		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, protecao.getDescricao());
			stmt.setDouble(2, protecao.getPreco());
			stmt.setLong(3, protecao.getId());

			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
}
