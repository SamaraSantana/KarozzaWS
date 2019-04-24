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

import br.com.senai.model.Cliente;
import br.com.senai.model.ListaNegra;
import br.com.senai.model.Locacao;
import br.com.senai.model.Loja;

@Repository
public class ClienteDao {
	private Connection conexao;

	@Autowired
	public ClienteDao(DataSource data) {
		try {
			this.conexao = data.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void adiciona(Cliente clientes) {
		String sql = "INSERT INTO cliente (nome,dataNascimento,rg,cpf,sexo,email,"
				+ "telefone,tipoTelefone,rua,numRua,complemento,bairro,cidade,uf,cep,cnh,validadeCnh,emissaoCnh) values"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			start.setString(1, clientes.getNome());
			start.setString(2,clientes.getDataNascimento());
			start.setString(3, clientes.getRg());
			start.setString(4, clientes.getCpf());
			start.setString(5, clientes.getSexo());
			start.setString(6, clientes.getEmail());
			start.setString(7, clientes.getTelefone());
			start.setString(8, clientes.getTipoTelefone());
			start.setString(9, clientes.getRua());
			start.setInt(10, clientes.getNumRua());
			start.setString(11, clientes.getComplemento());
			start.setString(12, clientes.getBairro());
			start.setString(13, clientes.getCidade());
			start.setString(14, clientes.getUf());
			start.setString(15, clientes.getCep());
			start.setString(16, clientes.getCnh());
			start.setString(17,clientes.getValidadeCnh());
			start.setString(18, clientes.getEmissaoCnh());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void alterar(Cliente clientes) {
		try {
			PreparedStatement start = conexao.prepareStatement(
					"update cliente set nome=?,dataNascimento=?,rg=?,cpf=?,sexo=?,email=?,telefone=?,tipoTelefone=?,rua=?,numRua=?,"
							+ "complemento=?,bairro=?,cidade=?,uf=?,cep=?,cnh=?,validadeCnh=?,emissaoCnh=? where id=?");
			start.setString(1, clientes.getNome());
			start.setString(2, clientes.getDataNascimento());
			start.setString(3, clientes.getRg());
			start.setString(4, clientes.getCpf());
			start.setString(5, clientes.getSexo());
			start.setString(6, clientes.getEmail());
			start.setString(7, clientes.getTelefone());
			start.setString(8, clientes.getTipoTelefone());
			start.setString(9, clientes.getRua());
			start.setInt(10, clientes.getNumRua());
			start.setString(11, clientes.getComplemento());
			start.setString(12, clientes.getBairro());
			start.setString(13, clientes.getCidade());
			start.setString(14, clientes.getUf());
			start.setString(15, clientes.getCep());
			start.setString(16, clientes.getCnh());
			start.setDate(17, formataData(clientes.getValidadeCnh()));
			start.setDate(18, formataData(clientes.getEmissaoCnh()));
			start.setLong(19, clientes.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Cliente> listar() {
		try {
			List<Cliente> lista = new ArrayList<Cliente>();
			PreparedStatement stmt = this.conexao.prepareStatement("select * from cliente where status = 1");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Cliente c = new Cliente();
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

	public List<ListaNegra> listarClientesListaNegra() {
		try {
			List<ListaNegra> lista = new ArrayList<ListaNegra>();
			PreparedStatement stmt = this.conexao.prepareStatement("select * from listaNegra");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ListaNegra l = new ListaNegra();
				l.setId(rs.getLong("id"));
				l.setCliente(rs.getString("cliente_id"));
				l.setLoja(rs.getString("loja_id"));
				l.setMotivo(rs.getString("motivo"));

				lista.add(l);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Loja> listarLoja() {
		try {
			List<Loja> lista = new ArrayList<Loja>();
			PreparedStatement stmt = this.conexao.prepareStatement("select * from loja");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Loja l = new Loja();
				l.setId(rs.getLong("id"));
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

	public Cliente buscaId(long id) {
		Cliente c = null;
		String sql = "select * from cliente where id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				c = new Cliente();
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

			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return c;
	}

	public static java.sql.Date formataData(String data) throws Exception {
		if (data == null || data.equals(""))
			return null;
		java.sql.Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
		} catch (ParseException e) {
			throw e;
		}
		return date;
	}

	public void remover(Cliente cliente) {
		try {
			PreparedStatement start = conexao.prepareStatement("delete from cliente where id=?");
			start.setLong(1, cliente.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void enviarListaNegra(ListaNegra ln) {
		try {
			PreparedStatement start = conexao
					.prepareStatement("insert into listaNegra(motivo,loja_id,cliente_id,verificador) values(?,?,?,?)");
			PreparedStatement start2 = conexao.prepareStatement("update cliente set status=0 where id=?");
			start.setString(1, ln.getMotivo());
			start.setString(2, ln.getLoja());
			start.setString(3, ln.getCliente());
			start.setBoolean(4, true);

			start.execute();
			start.close();

			start2.setString(1, ln.getCliente());
			start2.execute();
			start2.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void removerListaNegra(ListaNegra ln) {
		try {
			PreparedStatement start = conexao.prepareStatement("update listaNegra set verificador=? where id=?");
			PreparedStatement start2 = conexao.prepareStatement("update cliente set status=1 where id=?");
			start.setBoolean(1, false);
			start.setLong(2, ln.getId());

			start.execute();
			start.close();

			start2.setString(1, ln.getCliente());
			start2.execute();
			start2.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Cliente existeContato(Cliente c) {

		if (c == null) {
			throw new IllegalArgumentException("Usuario nao deve ser nulo");
		}
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(
					"select * from locacao, cliente, clientelocacao where locacao.id = clientelocacao.Locacao_id and cliente.id = clientelocacao.Cliente_id and cliente.cpf=?");
			// stmt.setLong(1, c.getId());
			stmt.setString(1, c.getCpf());

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
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
			} else {
				c = null;

			}
			rs.close();
			stmt.close();

			return c;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Cliente existeContato(Cliente c, Locacao l) {

		if (c == null) {
			throw new IllegalArgumentException("Usuario nao deve ser nulo");
		}
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(
					"select * from locacao, cliente where locacao.Cliente_id = cliente.id and locacao.id =? and cliente.cpf =?");
			stmt.setLong(1, l.getId());
			stmt.setString(2, c.getCpf());

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				c.setId(rs.getLong("cliente.id"));
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
			} else {
				c = null;

			}
			rs.close();
			stmt.close();

			return c;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	// metodo selecionar
	public List<Locacao> ListaLocacao() {
		try {
			List<Locacao> locacoes = new ArrayList<Locacao>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("Select * from locacao,protecao, grupo where locacao.Protecao_id = protecao.id and locacao.Grupo_id = grupo.id");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Locacao locacao = new Locacao();
				locacao.setId((int) rs.getLong("id"));
				locacao.setGrupo(rs.getString("grupo.nome"));
				locacao.setProtecao(rs.getString("protecao.descricao"));
				locacao.setCliente(rs.getString("cliente_id"));
				
				locacoes.add(locacao);

			}
			rs.close();
			stmt.close();
			return locacoes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
