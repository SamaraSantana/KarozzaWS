package br.com.senai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.model.Exemplar;
import br.com.senai.model.Infracao;
import br.com.senai.model.Multa;

@Repository
public class MultaDao {
	private Connection conexao;

	@Autowired
	public MultaDao(DataSource data) {
		try {
			this.conexao = data.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void adiciona(Multa multa) throws Exception  {
		String sql = "INSERT INTO multa (data,infracao_id,exemplar_id) values(?,?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			start.setDate(1, formataData(multa.getData()));
			start.setString(2, multa.getInfracao());
			start.setString(3, multa.getExemplar());

			start.execute();
			start.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Infracao> listarInfracao() {
		try {
			List<Infracao> lista = new ArrayList<Infracao>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from infracao");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Infracao i = new Infracao();
				i.setId(rs.getInt("id"));
				i.setDescricao(rs.getString("descricao"));

				lista.add(i);
			}
			rs.close();
			stmt.close();
			
			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Exemplar> listarExemplar()  {
		try {
			List<Exemplar> lista = new ArrayList<Exemplar>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from exemplar");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Exemplar e = new Exemplar();
				e.setId(rs.getInt("id"));
				e.setPlaca(rs.getString("placa"));

				lista.add(e);
			}
			rs.close();
			stmt.close();
			
			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		}
	

	public void alterar(Multa multas)  {
		try {
			PreparedStatement start = conexao
					.prepareStatement("update multa set data=?,infracao_id=?,exemplar_id=? where id=?");
			start.setDate(1, formataData(multas.getData()));
			start.setString(2, multas.getInfracao());
			start.setString(3, multas.getExemplar());
			start.setLong(4, multas.getId());

			start.execute();
			start.close();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Multa> listar()  {
		try {
			List<Multa> lista = new ArrayList<Multa>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from multa, infracao, exemplar where multa.infracao_id = infracao.id and multa.exemplar_id = exemplar.id");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Multa m = new Multa();
				m.setId(rs.getInt("id"));
				
				Date g = rs.getDate("data");
				SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");		
				m.setData(formata.format(g));
				
				m.setInfracao(rs.getString("infracao.descricao"));
				m.setExemplar(rs.getString("exemplar.placa"));
				
				lista.add(m);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Multa buscaId(int id)  {
		Multa m = null;
		String sql = "select * from multa where id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				m = new Multa();
				m.setId(rs.getInt("id"));
				m.setInfracao(rs.getString("infracao_id"));
				m.setExemplar(rs.getString("exemplar_id"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return m;
	}
	
	public static java.sql.Date formataData(String data) throws Exception {
		if (data == null || data.equals(""))
			return null;
		java.sql.Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = new java.sql.Date(
					((java.util.Date) formatter.parse(data)).getTime());
		} catch (ParseException e) {
			throw e;
		}
		return date;
	}
	
	public void remover(Multa multas)  {
		try {
			PreparedStatement start = conexao
					.prepareStatement("delete from multa where id=?");
			start.setLong(1, multas.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
