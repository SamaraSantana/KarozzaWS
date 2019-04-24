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

import br.com.senai.model.Grupo;
import br.com.senai.model.Loja;

@Repository
public class GrupoDao {
	private Connection conexao;

	@Autowired
	public GrupoDao(DataSource data) {
		try {
			this.conexao = data.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void adiciona(Grupo grupos)  {
		String sql = "INSERT INTO grupo (nome,descricao,preco,foto,loja_id) values(?,?,?,?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			start.setString(1, grupos.getNome());
			start.setString(2, grupos.getDescricao());
			start.setDouble(3, grupos.getPreco());
			start.setBlob(4,( grupos.getFoto() == null) ? null : new ByteArrayInputStream(grupos.getFoto()));
			start.setString(5, grupos.getLoja());
			
			start.execute();
			start.close();
		
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
	
	public void alterar(Grupo grupos)  {
		try {
			PreparedStatement start = conexao
					.prepareStatement("update grupo set nome=?,descricao=?,preco=?,loja_id=? where id=?");
			start.setString(1, grupos.getNome());
			start.setString(2, grupos.getDescricao());
			start.setDouble(3, grupos.getPreco());
			start.setString(4, grupos.getLoja());
			start.setLong(5, grupos.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Grupo> listar() {
		try {
			List<Grupo> lista = new ArrayList<Grupo>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from grupo, loja where loja.id = grupo.loja_id");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Grupo g = new Grupo();
				g.setId(rs.getInt("id"));
				g.setNome(rs.getString("nome"));
				g.setDescricao(rs.getString("descricao"));
				g.setPreco(rs.getDouble("preco"));
				g.setFoto(rs.getBytes("foto"));
				g.setLoja(rs.getString("loja.bairro"));
				
				lista.add(g);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Grupo buscaId(int id)  {
		Grupo g = null;
		String sql = "select * from grupo where id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				g = new Grupo();
				g.setId(rs.getInt("id"));
				g.setNome(rs.getString("nome"));
				g.setDescricao(rs.getString("descricao"));
				g.setPreco(rs.getDouble("preco"));
				g.setFoto(rs.getBytes("foto"));
				g.setLoja(rs.getString("loja_id"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return g;
	}
	
	public void remover(Grupo grupos)  {
		try {
			PreparedStatement start = conexao
					.prepareStatement("delete from grupo where id=?");
			start.setLong(1, grupos.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
