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

import br.com.senai.model.Loja;

@Repository
public class LojaDao {
	private Connection conexao;

	@Autowired
	public LojaDao(DataSource data) {
		try {
			this.conexao = data.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void adiciona(Loja lojas) {
		String sql = "INSERT INTO loja (cnpj,telefone,rua,numRua,complemento,bairro,cidade,uf,cep,tipo) values(?,?,?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			start.setString(1, lojas.getCnpj());
			start.setString(2, lojas.getTelefone());
			start.setString(3, lojas.getRua());
			start.setString(4, lojas.getNumRua());
			start.setString(5, lojas.getComplemento());
			start.setString(6, lojas.getBairro());
			start.setString(7, lojas.getCidade());
			start.setString(8, lojas.getUf());
			start.setString(9, lojas.getCep());
			start.setString(10, lojas.getTipo());

			start.execute();
			start.close();
			conexao.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// metodo selecionar
	public List<Loja> ListaLoja() {
		try {
			List<Loja> lojas = new ArrayList<Loja>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("Select * from loja");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Loja loja = new Loja();
				loja.setId((int) rs.getLong("id"));
				loja.setCnpj(rs.getString("cnpj"));
				loja.setTelefone(rs.getString("telefone"));
				loja.setRua(rs.getString("rua"));
				loja.setNumRua(rs.getString("numRua"));
				loja.setComplemento(rs.getString("complemento"));
				loja.setBairro(rs.getString("bairro"));
				loja.setCidade(rs.getString("cidade"));
				loja.setUf(rs.getString("uf"));
				loja.setCep(rs.getString("cep"));
				loja.setTipo(rs.getString("tipo"));
				lojas.add(loja);

			}
			rs.close();
			stmt.close();
			return lojas;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover(Loja loja) {
		try {

			PreparedStatement stmt = conexao
					.prepareStatement("delete from loja where id=?");
			stmt.setLong(1, loja.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	// metodo buscar id
	public Loja buscaId(long id) {

		Loja l = null;
		String sql = "SELECT * FROM loja WHERE id =?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				l = new Loja();
				l.setId(rs.getLong("id"));
				l.setCnpj(rs.getString("cnpj"));
				l.setTelefone(rs.getString("telefone"));
				l.setRua(rs.getString("rua"));
				l.setNumRua(rs.getString("numRua"));
				l.setComplemento(rs.getString("complemento"));
				l.setBairro(rs.getString("bairro"));
				l.setCidade(rs.getString("cidade"));
				l.setUf(rs.getString("uf"));
				l.setCep(rs.getString("cep"));
				l.setTipo(rs.getString("tipo"));
			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return l;
	}

	// metodo buscar id
	public Loja buscar(String bairro) {
		System.out.println("passou aqui");
		System.out.println(bairro);
		Loja l = null;
		String sql = "SELECT * FROM loja WHERE (bairro =?)";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, bairro);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				l = new Loja();
				l.setId(Integer.parseInt(rs.getString("id")));
				l.setBairro(rs.getString("bairro"));

			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return l;
	}

	// metodo de alterar
	public void altera(Loja loja) {
		String sql = "update loja set cnpj=?,telefone=?,rua=?,numRua=?,complemento=?,bairro=?,cidade=?,uf=?,cep=?,tipo=?  where id=?";

		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, loja.getCnpj());
			stmt.setString(2, loja.getTelefone());
			stmt.setString(3, loja.getRua());
			stmt.setString(4, loja.getNumRua());
			stmt.setString(5, loja.getComplemento());
			stmt.setString(6, loja.getBairro());
			stmt.setString(7, loja.getCidade());
			stmt.setString(8, loja.getUf());
			stmt.setString(9, loja.getCep());
			stmt.setString(10, loja.getTipo());
			stmt.setLong(11, loja.getId());

			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public Loja buscaMatriz(long id) {

		Loja l = null;
		String sql = "SELECT * FROM loja WHERE id = ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, 1);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				l = new Loja();
				l.setId(rs.getLong("id"));
				l.setCnpj(rs.getString("cnpj"));
				l.setTelefone(rs.getString("telefone"));
				l.setRua(rs.getString("rua"));
				l.setNumRua(rs.getString("numRua"));
				l.setComplemento(rs.getString("complemento"));
				l.setBairro(rs.getString("bairro"));
				l.setCidade(rs.getString("cidade"));
				l.setUf(rs.getString("uf"));
				l.setCep(rs.getString("cep"));
				l.setTipo(rs.getString("tipo"));
			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return l;
	}
}
