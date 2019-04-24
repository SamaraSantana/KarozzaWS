package br.com.senai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.model.Infracao;

@Repository
public class InfracaoDao {
	private Connection conexao;

	@Autowired
	public InfracaoDao(DataSource data) {
		try {
			this.conexao = data.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void adiciona(Infracao infracao) {
		String sql = "INSERT INTO infracao (descricao,gravidade,codigo,pontos,preco) values(?,?,?,?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			start.setString(1, infracao.getDescricao());
			start.setString(2, infracao.getGravidade());
			start.setString(3, infracao.getCodigo());
			start.setInt(4, infracao.getPontos());
			start.setDouble(5, infracao.getPreco());

			start.execute();
			start.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void alterar(Infracao infracoes)  {
		try {
			PreparedStatement start = conexao
					.prepareStatement("update infracao set descricao=?,gravidade=?,codigo=?,pontos=?,preco=? where id=?");
			start.setString(1, infracoes.getDescricao());
			start.setString(2, infracoes.getGravidade());
			start.setString(3, infracoes.getCodigo());
			start.setInt(4, infracoes.getPontos());
			start.setDouble(5, infracoes.getPreco());
			start.setLong(6, infracoes.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Infracao> listar() {
		try {
			List<Infracao> lista = new ArrayList<Infracao>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from Infracao");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Infracao i = new Infracao();
				i.setId(rs.getInt("id"));
				i.setDescricao(rs.getString("descricao"));
				i.setGravidade(rs.getString("gravidade"));
				i.setCodigo(rs.getString("codigo"));
				i.setPontos(rs.getInt("pontos"));
				i.setPreco(rs.getDouble("preco"));

				lista.add(i);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Infracao buscaId(int id) {
		Infracao i = null;
		String sql = "select * from infracao where id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				i = new Infracao();
				i.setId(rs.getInt("id"));
				i.setDescricao(rs.getString("descricao"));
				i.setGravidade(rs.getString("gravidade"));
				i.setCodigo(rs.getString("codigo"));
				i.setPontos(rs.getInt("pontos"));
				i.setPreco(rs.getDouble("preco"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return i;
	}
	
	public void remover(Infracao infracoes)  {
		try {
			PreparedStatement start = conexao
					.prepareStatement("delete from infracao where id=?");
			start.setLong(1, infracoes.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
