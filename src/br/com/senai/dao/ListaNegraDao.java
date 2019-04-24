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
import br.com.senai.model.Cliente;
import br.com.senai.model.Grupo;
import br.com.senai.model.ListaNegra;
import br.com.senai.model.Loja;

@Repository
public class ListaNegraDao {

	private final Connection conexao;

	@Autowired
	public ListaNegraDao(DataSource dataSource) {
		try {
			this.conexao = dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("passou aqui");
			throw new RuntimeException(e);
		}
	}

	public void adiciona(ListaNegra listaNegra ) {
		String sql = "INSERT INTO listaNegra (motivo,cliente_id) values(?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			start.setString(1, listaNegra.getMotivo());
			start.setString(2, listaNegra.getCliente());
			
			start.execute();
			start.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Cliente> listarCliente()  {
		try {
			List<Cliente> lista = new ArrayList<Cliente>();
			PreparedStatement stmt = this.conexao.prepareStatement("select * from cliente");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Cliente c= new Cliente();
				c.setId(rs.getLong("id"));
				c.setNome(rs.getString("nome"));
				c.setDataNascimento(rs.getString("dataNascimento"));
				c.setRg(rs.getString("rg"));
				c.setCpf(rs.getString("cpf"));
				c.setSexo(rs.getString("sexo"));
				c.setEmail(rs.getString("email"));
				c.setTelefone(rs.getString("telefone"));
				c.setTipoTelefone(rs.getString("tipoTelefone"));
				c.setRua(rs.getString("rua"));
				c.setNumRua(rs.getInt("numRua"));
				c.setComplemento(rs.getString("complemento"));
				c.setBairro(rs.getString("bairro"));
				c.setCidade(rs.getString("cidade"));
				c.setUf(rs.getString("uf"));
				c.setCep(rs.getString("cep"));
				c.setCnh(rs.getString("cnh"));
				c.setValidadeCnh(rs.getString("validadeCnh"));
				c.setEmissaoCnh(rs.getString("emissaoCnh"));

				lista.add(c);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void remover(ListaNegra listaNegra)  {
		try {

			PreparedStatement stmt = conexao
					.prepareStatement("delete from listaNegra where id=?");
			stmt.setLong(1, listaNegra.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	
	public void alterar(ListaNegra listaNegra){
		try {
			PreparedStatement start = conexao
					.prepareStatement("update listaNegra set motivo=?,cliente_id=? where id=?");
			start.setString(1, listaNegra.getMotivo());
			start.setString(2, listaNegra.getCliente());
			start.setLong(3, listaNegra.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<ListaNegra> listarListaNegra()  {
		try {
			List<ListaNegra> lista = new ArrayList<ListaNegra>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from listaNegra, cliente where cliente.id = listaNegra.cliente_id");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ListaNegra l = new ListaNegra();
				l.setId(rs.getLong("id"));
				l.setMotivo(rs.getString("motivo"));
				l.setCliente(rs.getString("cliente.nome"));
				
				lista.add(l);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public ListaNegra buscaId(long id)  {
		ListaNegra l = null;
		String sql = "select * from listaNegra where id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
			    l = new ListaNegra();
				l.setId(rs.getLong("id"));
				l.setMotivo(rs.getString("motivo"));
				l.setCliente(rs.getString("cliente_id"));
			}
			stmt.close();

		} catch (Exception e) {

		}
		return l;
	}
	
	
}
