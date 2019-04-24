package br.com.senai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.senai.model.Automovel;
import br.com.senai.model.Cliente;
import br.com.senai.model.Grupo;
import br.com.senai.model.Locacao;
import br.com.senai.model.Loja;
import br.com.senai.model.Opcional;
import br.com.senai.model.Protecao;


@Repository
public class LocacaoDao {
	private Connection conexao;

	@Autowired
	public LocacaoDao(DataSource data) {
		try {
			this.conexao = data.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void alteraStatusExemplar(String status, int id) {
		try {
			PreparedStatement start = conexao
					.prepareStatement("update exemplar as e inner join locacao as l on e.id = l.exemplar_id set e.status=? where l.id=?");
			start.setString(1, status);
			start.setLong(2, id);

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void adiciona(Locacao locacoes) {
		try {
			PreparedStatement start = conexao
					.prepareStatement("update locacao set local_Reserva=?,data_Retirada=?,hora_Retirada=?,data_Prevista=?,hora_Prevista=?,protecao_id=?,grupo_id=?,cliente_id=? where id=?");

			start.setString(1, locacoes.getLocal_Reserva());
			start.setDate(2, formataData(locacoes.getData_Retirada()));
			start.setTime(3, formataHora(locacoes.getHora_Retirada()));
			start.setDate(4, formataData(locacoes.getData_Prevista()));
			start.setTime(5, formataHora(locacoes.getHora_Prevista()));
			start.setString(6, locacoes.getProtecao());
			start.setString(7, locacoes.getGrupo());
			start.setString(8, locacoes.getCliente());
			start.setLong(9, ultimoId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void adicionaCliente(Cliente clientes) {
		String sql = "INSERT INTO cliente (nome,dataNascimento,rg,cpf,sexo,email,"
				+ "telefone,tipoTelefone,rua,numRua,complemento,bairro,cidade,uf,cep,cnh,validadeCnh,emissaoCnh) values"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);

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
			start.setString(17, clientes.getValidadeCnh());
			start.setString(18, clientes.getEmissaoCnh());

			start.executeUpdate();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void adicionaItem(Locacao locacoes) {
		String sql = "INSERT INTO itemOpcional (opcional_id,locacao_id)"
				+ "values(?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			long opcional = 0;

			for (int i = 0; i < locacoes.getOpcional().length; i++) {

				opcional = locacoes.getOpcional()[i];

				start.setLong(1, opcional);
				start.setLong(2, ultimoId());
				start.execute();
			}
			start.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void adicionaReserva(Locacao locacoes) {
		String sql = "INSERT INTO locacao (local_Reserva,data_Retirada,hora_Retirada,data_Prevista,hora_Prevista,protecao_id,grupo_id,cliente_id)"
				+ "values(?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			start.setString(1, locacoes.getLocal_Reserva());
			start.setDate(2, formataData(locacoes.getData_Retirada()));
			start.setTime(3, formataHora(locacoes.getHora_Retirada()));
			start.setDate(4, formataData(locacoes.getData_Prevista()));
			start.setTime(5, formataHora(locacoes.getHora_Prevista()));
			start.setString(6, 1 + "");
			start.setString(7, 1 + "");
			start.setString(8, 1 + "");

			start.executeUpdate();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int ultimoId() throws SQLException {
		String sql = "SELECT MAX(ID) as id FROM locacao";
		PreparedStatement stmt = (PreparedStatement) conexao
				.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int ultimoId = rs.getInt("id");

		rs.close();
		stmt.close();

		return ultimoId;
	}

	public int ultimoIdCliente() throws SQLException {
		String sql = "SELECT MAX(ID) as id FROM cliente";
		PreparedStatement stmt = (PreparedStatement) conexao
				.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int ultimoId = rs.getInt("id");

		rs.close();
		stmt.close();

		return ultimoId;
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

	public static java.sql.Time formataHora(String hora) throws Exception {
		if (hora == null || hora.equals(""))
			return null;
		java.sql.Time hor = null;
		try {
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			hor = new java.sql.Time((formatter.parse(hora)).getTime());
		} catch (ParseException e) {
			throw e;
		}
		return hor;
	}

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

	public List<Automovel> listarAutomovel() {
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

	public List<Protecao> listarProtecao() {
		try {
			List<Protecao> protecoes = new ArrayList<Protecao>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("Select * from protecao");
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

	public List<Opcional> listarOpcional() {
		try {
			List<Opcional> opcionais = new ArrayList<Opcional>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("Select * from opcional");
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

	public List<Grupo> listarGrupo() {
		try {
			List<Grupo> lista = new ArrayList<Grupo>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from grupo");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Grupo g = new Grupo();
				g.setId(rs.getInt("id"));
				g.setNome(rs.getString("nome"));
				g.setDescricao(rs.getString("descricao"));
				g.setPreco(rs.getDouble("preco"));
				g.setFoto(rs.getBytes("foto"));
				g.setLoja(rs.getString("loja_id"));

				lista.add(g);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Locacao buscaId(long id) {
		Locacao l = null;
		String sql = "select * from locacao where id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				l = new Locacao();
				l.setId(rs.getLong("id"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return l;
	}

	
	// Resumo da Locação
	public Locacao buscaLocacao() {
		Locacao l = null;
		String sql = "select l.id, l.data_Retirada, l.hora_Retirada, l.data_Prevista, l.hora_Prevista, loja.bairro from locacao as l, loja where loja.id = l.local_Reserva and l.id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setLong(1, ultimoId());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				l = new Locacao();
				l.setId(rs.getLong("l.id"));
				l.setLocal_Reserva(rs.getString("loja.bairro"));

				Date g = rs.getDate("l.data_Retirada");
				SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
				l.setData_Retirada(formata.format(g));

				Time t = rs.getTime("l.hora_Retirada");
				SimpleDateFormat formata3 = new SimpleDateFormat("HH:mm");
				l.setHora_Retirada(formata3.format(t));

				Date g2 = rs.getDate("l.data_Prevista");
				SimpleDateFormat formata2 = new SimpleDateFormat("dd/MM/yyyy");
				l.setData_Prevista(formata2.format(g2));

				Time t2 = rs.getTime("l.hora_Prevista");
				SimpleDateFormat formata4 = new SimpleDateFormat("HH:mm");
				l.setHora_Prevista(formata4.format(t2));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return l;
	}
	
	public Locacao buscaLocacao(long id) {
		Locacao l = null;
		String sql = "select l.id as LocacaoId, e.id as ExemplarId, e.placa from locacao as l, exemplar as e where l.exemplar_id = e.id and l.id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				l = new Locacao();
				l.setId(rs.getLong("LocacaoId"));
				l.setExemplar(rs.getString("ExemplarId"));
				l.setCliente(rs.getString("e.placa"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return l;
	}

	public Cliente buscaCliente() {
		Cliente c = null;
		String sql = "select * from cliente where id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setLong(1, ultimoIdCliente());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				c = new Cliente();
				c.setId(rs.getLong("id"));
				c.setNome(rs.getString("nome"));
				c.setRg(rs.getString("rg"));
				c.setCpf(rs.getString("cpf"));
				c.setSexo(rs.getString("sexo"));
				c.setEmail(rs.getString("email"));
				c.setTelefone(rs.getString("telefone"));
				c.setTipoTelefone(rs.getString("tipoTelefone"));
				c.setDataNascimento(rs.getString("dataNascimento"));
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
	
	public Cliente buscaCliente(int id) {
		Cliente c = null;
		String sql = "select * from cliente as c, locacao where locacao.Cliente_id = c.id and locacao.id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				c = new Cliente();
				c.setId(rs.getLong("c.id"));
				c.setNome(rs.getString("c.nome"));
				c.setRg(rs.getString("c.rg"));
				c.setCpf(rs.getString("c.cpf"));
				c.setSexo(rs.getString("c.sexo"));
				c.setEmail(rs.getString("c.email"));
				c.setTelefone(rs.getString("c.telefone"));
				c.setTipoTelefone(rs.getString("c.tipoTelefone"));
				c.setDataNascimento(rs.getString("c.dataNascimento"));
				c.setRua(rs.getString("c.rua"));
				c.setNumRua(rs.getInt("c.numRua"));
				c.setComplemento(rs.getString("c.complemento"));
				c.setBairro(rs.getString("c.bairro"));
				c.setCidade(rs.getString("c.cidade"));
				c.setUf(rs.getString("c.uf"));
				c.setCep(rs.getString("c.cep"));
				c.setCnh(rs.getString("c.cnh"));
				c.setValidadeCnh(rs.getString("c.validadeCnh"));
				c.setEmissaoCnh(rs.getString("c.emissaoCnh"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return c;
	}

	public int diasLocacao() {
		int day = 0, day2 = 0, dayFinal = 0;
		String sql = "select data_Retirada, data_Prevista from locacao where id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setLong(1, ultimoId());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				Calendar c = Calendar.getInstance();
				Date g2 = rs.getDate("data_Prevista");
				c.setTime(g2);
				day = c.get(Calendar.DAY_OF_MONTH);

				Calendar c2 = Calendar.getInstance();
				Date g = rs.getDate("data_Retirada");
				c2.setTime(g);
				day2 = c2.get(Calendar.DAY_OF_MONTH);

				dayFinal = day - day2;
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return dayFinal;
	}
	
	public int diasLocacao(int id) {
		int day = 0, day2 = 0, dayFinal = 0;
		String sql = "select data_Retirada, data_Prevista from locacao where id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				Calendar c = Calendar.getInstance();
				Date g2 = rs.getDate("data_Prevista");
				c.setTime(g2);
				day = c.get(Calendar.DAY_OF_MONTH);

				Calendar c2 = Calendar.getInstance();
				Date g = rs.getDate("data_Retirada");
				c2.setTime(g);
				day2 = c2.get(Calendar.DAY_OF_MONTH);

				dayFinal = day - day2;
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return dayFinal;
	}

	public Grupo buscaGrupo() {
		Grupo g = null;
		String sql = "select g.id, g.nome, g.descricao, g.preco, g.foto from grupo as g, locacao where g.id = locacao.grupo_id and locacao.id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, ultimoId());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				g = new Grupo();
				g.setId(rs.getInt("g.id"));
				g.setNome(rs.getString("g.nome"));
				g.setDescricao(rs.getString("g.descricao"));
				g.setPreco(rs.getDouble("g.preco"));
				g.setFoto(rs.getBytes("g.foto"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return g;
	}

	public Grupo buscaGrupo(int id) {
		Grupo g = null;
		String sql = "select g.id, g.nome, g.descricao, g.preco, g.foto from grupo as g, locacao where g.id = locacao.grupo_id and locacao.id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				g = new Grupo();
				g.setId(rs.getInt("g.id"));
				g.setNome(rs.getString("g.nome"));
				g.setDescricao(rs.getString("g.descricao"));
				g.setPreco(rs.getDouble("g.preco"));
				g.setFoto(rs.getBytes("g.foto"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return g;
	}

	public Protecao buscarProtecao() {

		Protecao p = null;
		String sql = "select p.id, p.descricao, p.preco from protecao as p, locacao where p.id = locacao.protecao_id and locacao.id = ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, ultimoId());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				p = new Protecao();
				p.setId(rs.getInt("p.id"));
				p.setDescricao(rs.getString("p.descricao"));
				p.setPreco(rs.getDouble("p.preco"));

			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return p;
	}
	
	public Protecao buscarProtecao(int id) {

		Protecao p = null;
		String sql = "select p.id, p.descricao, p.preco from protecao as p, locacao where p.id = locacao.protecao_id and locacao.id = ?";
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				p = new Protecao();
				p.setId(rs.getInt("p.id"));
				p.setDescricao(rs.getString("p.descricao"));
				p.setPreco(rs.getDouble("p.preco"));

			}
			stmt.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return p;
	}

	public List<Opcional> buscarOpcionais() {
		try {
			List<Opcional> opcionais = new ArrayList<Opcional>();
			String sql = "select o.id, o.nome, o.preco from locacao as l, opcional as o, itemopcional as io where l.id = io.Locacao_id and o.id = io.Opcional_id and l.id = ?";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, ultimoId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Opcional o = new Opcional();
				o.setId((int) rs.getLong("o.id"));
				o.setNome(rs.getString("o.nome"));
				o.setPreco(rs.getDouble("o.preco"));

				opcionais.add(o);
			}
			rs.close();
			stmt.close();
			return opcionais;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Opcional> buscarOpcionais(int id) {
		try {
			List<Opcional> opcionais = new ArrayList<Opcional>();
			String sql = "select o.id, o.nome, o.preco from locacao as l, opcional as o, itemopcional as io where l.id = io.Locacao_id and o.id = io.Opcional_id and l.id = ?";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Opcional o = new Opcional();
				o.setId((int) rs.getLong("o.id"));
				o.setNome(rs.getString("o.nome"));
				o.setPreco(rs.getDouble("o.preco"));

				opcionais.add(o);
			}
			rs.close();
			stmt.close();
			return opcionais;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public double somaOpcionais() {
		try {
			List<Double> n = new ArrayList<Double>();
			String sql = "select o.id, o.nome, o.preco from locacao as l, opcional as o, itemopcional as io where l.id = io.Locacao_id and o.id = io.Opcional_id and l.id = ?";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, ultimoId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				n.add(rs.getDouble("o.preco"));
			}
			double total = 0;
			for (double k : n)
				total = total + k;

			rs.close();
			stmt.close();
			return total;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public double somaOpcionais(int id) {
		try {
			List<Double> n = new ArrayList<Double>();
			String sql = "select o.id, o.nome, o.preco from locacao as l, opcional as o, itemopcional as io where l.id = io.Locacao_id and o.id = io.Opcional_id and l.id = ?";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				n.add(rs.getDouble("o.preco"));
			}
			double total = 0;
			for (double k : n)
				total = total + k;

			rs.close();
			stmt.close();
			return total;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void remover(Locacao locacao) {
		try {

			PreparedStatement stmt = conexao
					.prepareStatement("delete from locacao where id=?");
			stmt.setLong(1, locacao.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public List<Locacao> ListaLocacao() {
		try {
			List<Locacao> locacoes = new ArrayList<Locacao>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("Select * from locacao");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Locacao locacao = new Locacao();
				locacao.setId((int) rs.getLong("id"));
				locacao.setGrupo(rs.getString("grupo_id"));
				locacao.setProtecao(rs.getString("protecao_id"));

				locacoes.add(locacao);

			}
			rs.close();
			stmt.close();
			return locacoes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void altera(Locacao locacoes) {
		try {
			PreparedStatement start = conexao.prepareStatement(
					"update locacao set local_Reserva=?,data_Retirada=?,hora_Retirada=?,data_Prevista=?,hora_Prevista=?,protecao_id=?,grupo_id=?,cliente_id=? where id=?");

			start.setString(1, locacoes.getLocal_Reserva());
			start.setDate(2, formataData(locacoes.getData_Retirada()));
			start.setTime(3, formataHora(locacoes.getHora_Retirada()));
			start.setDate(4, formataData(locacoes.getData_Prevista()));
			start.setTime(5, formataHora(locacoes.getHora_Prevista()));
			start.setString(6, locacoes.getProtecao());
			start.setString(7, locacoes.getGrupo());
			start.setString(8, locacoes.getCliente());
			start.setLong(9, locacoes.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public Locacao buscaUltimoId() {
		Locacao l = null;
		String sql = "select * from locacao, protecao, grupo where locacao.Protecao_id = protecao.id and locacao.Grupo_id = grupo.id and locacao.id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setLong(1, ultimoId());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				l = new Locacao();
				l.setId(rs.getLong("id"));
				l.setLocal_Reserva(rs.getString("local_Reserva"));

				Date g = rs.getDate("data_Retirada");
				SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
				l.setData_Retirada(formata.format(g));

				Time t = rs.getTime("hora_Retirada");
				SimpleDateFormat formata3 = new SimpleDateFormat("HH:mm");
				l.setHora_Retirada(formata3.format(t));

				Date g2 = rs.getDate("data_Prevista");
				SimpleDateFormat formata2 = new SimpleDateFormat("dd/MM/yyyy");
				l.setData_Prevista(formata2.format(g2));
				
				Time t2 = rs.getTime("hora_Prevista");
				SimpleDateFormat formata4 = new SimpleDateFormat("HH:mm");
				l.setHora_Prevista(formata4.format(t2));
				

				l.setGrupo(rs.getString("grupo.preco"));
				l.setProtecao(rs.getString("protecao.preco"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return l;
	}

	
		
		public int exemplaresDisponiveis() {
			int exemplarDisponivel = 0;
			try {
				PreparedStatement stmt = conexao
						.prepareStatement("select count(*) from exemplar where status='disponivel'");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {

					exemplarDisponivel = rs.getInt("count(*)");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return exemplarDisponivel;
		}
		
		
		
		public int exemplaresAlugados() {
			int exemplarAlugado = 0;
			try {
				PreparedStatement stmt = conexao
						.prepareStatement("select count(*) from exemplar where status='alugados'");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {

					exemplarAlugado = rs.getInt("count(*)");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return exemplarAlugado;
		}

		
}
