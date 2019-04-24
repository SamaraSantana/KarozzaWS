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

import br.com.senai.model.Automovel;
import br.com.senai.model.Grupo;

@Repository
public class AutomovelDao {
	private Connection conexao;

	@Autowired
	public AutomovelDao(DataSource data) {
		try {
			this.conexao = data.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void adiciona(Automovel automoveis) {
		String sql = "INSERT INTO automovel (marca,modelo,ano,motor,transmissao,bombaCombustivel,Grupo_id) values(?,?,?,?,?,?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			start.setString(1, automoveis.getMarca());
			start.setString(2, automoveis.getModelo());
			start.setString(3, automoveis.getAno());
			start.setString(4, automoveis.getMotor());
			start.setString(5, automoveis.getTransmissao());
			start.setString(6, automoveis.getBombaCombustivel());
			start.setString(7, automoveis.getGrupo());
			
			start.execute();
			start.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Grupo> listarGrupo() {
		try {
			List<Grupo> lista = new ArrayList<Grupo>();
			PreparedStatement stmt = this.conexao.prepareStatement("select * from grupo");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Grupo g = new Grupo();
				g.setId(rs.getLong("id"));
				g.setNome(rs.getString("nome"));
				g.setDescricao(rs.getString("descricao"));
				g.setPreco(rs.getDouble("preco"));

				lista.add(g);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void alterar(Automovel automoveis) {
		try {
			PreparedStatement start = conexao
					.prepareStatement("update automovel set marca=?,modelo=?,ano=?,motor=?,transmissao=?,bombaCombustivel=?,grupo_id=? where id=?");
			start.setString(1, automoveis.getMarca());
			start.setString(2, automoveis.getModelo());
			start.setString(3, automoveis.getAno());
			start.setString(4, automoveis.getMotor());
			start.setString(5, automoveis.getTransmissao());
			start.setString(6, automoveis.getBombaCombustivel());
			start.setString(7, automoveis.getGrupo());
			start.setLong(8, automoveis.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Automovel> listar() {
		try {
			List<Automovel> lista = new ArrayList<Automovel>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from automovel, grupo where grupo.id = automovel.grupo_id");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Automovel a = new Automovel();
				a.setId(rs.getLong("id"));
				a.setMarca(rs.getString("marca"));
				a.setModelo(rs.getString("modelo"));
				a.setAno(rs.getString("ano"));
				a.setMotor(rs.getString("motor"));
				a.setTransmissao(rs.getString("transmissao"));
				a.setBombaCombustivel(rs.getString("bombaCombustivel"));
				a.setGrupo(rs.getString("grupo.nome"));
				
				lista.add(a);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Automovel buscaId(long id) {
		Automovel a = null;
		String sql = "select * from automovel where id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				a = new Automovel();
				a.setId(rs.getLong("id"));
				a.setMarca(rs.getString("marca"));
				a.setModelo(rs.getString("modelo"));
				a.setAno(rs.getString("ano"));
				a.setMotor(rs.getString("motor"));
				a.setTransmissao(rs.getString("transmissao"));
				a.setBombaCombustivel(rs.getString("bombaCombustivel"));
				a.setGrupo(rs.getString("grupo_id"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return a;
	}
	
	public void remover(Automovel automoveis) {
		try {
			PreparedStatement start = conexao
					.prepareStatement("delete from automovel where id=?");
			start.setLong(1, automoveis.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
