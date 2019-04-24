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

import br.com.senai.model.Opcional;

@Repository
public class OpcionalDao {
	private Connection conexao;

	@Autowired
	public OpcionalDao(DataSource data) {
		try {
			this.conexao = data.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void adiciona(Opcional opcional)  {
		String sql = "INSERT INTO opcional (nome,preco,foto) values(?,?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			start.setString(1, opcional.getNome());
			start.setDouble(2, opcional.getPreco());
			start.setBlob(3,( opcional.getFoto() == null) ? null : new ByteArrayInputStream(opcional.getFoto()));

			start.execute();
			start.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} 
	}

	// metodo selecionar
	public List<Opcional> listaOpcional() {
		try {
			List<Opcional> opcionais = new ArrayList<Opcional>();
			PreparedStatement stmt = this.conexao.prepareStatement("Select * from opcional");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Opcional opcional = new Opcional();
				opcional.setId((int) rs.getLong("id"));
				opcional.setNome(rs.getString("nome"));
				opcional.setPreco(rs.getDouble("preco"));
				opcional.setFoto(rs.getBytes("foto"));

				opcionais.add(opcional);

			}
			rs.close();
			stmt.close();
			return opcionais;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} 
	}

	public void remover(Opcional opcional) {
		try {

			PreparedStatement stmt = conexao.prepareStatement("delete from opcional where id=?");
			stmt.setLong(1, opcional.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	// metodo buscar id
	public Opcional buscaId(long id)  {

		Opcional o = null;
		String sql = "SELECT * FROM opcional WHERE id =?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				o = new Opcional();
				o.setId(id);
				o.setNome(rs.getString("nome"));
				o.setPreco(rs.getDouble("preco"));
				o.setFoto(rs.getBytes("foto"));

			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		return o;
	}

	// metodo buscar id
	public Opcional buscar(String nome) {
		System.out.println("passou aqui");
		System.out.println(nome);
		Opcional o = null;
		String sql = "SELECT * FROM opcional WHERE (nome =?)";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				o = new Opcional();
				o.setId(rs.getLong("id"));
				o.setNome(rs.getString("nome"));

			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 

		return o;
	}

	// metodo de alterar
	public void altera(Opcional opcional)  {
		String sql = "update opcional set nome=?,preco=?  where id=?";

		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, opcional.getNome());
			stmt.setDouble(2, opcional.getPreco());
			stmt.setLong(3, opcional.getId());

			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException();
		} 
	}

}
