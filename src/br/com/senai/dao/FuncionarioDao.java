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

import br.com.senai.model.Funcionario;
import br.com.senai.model.Loja;

@Repository
public class FuncionarioDao {
	private Connection conexao;

	@Autowired
	public FuncionarioDao(DataSource data) {
		try {
			this.conexao = data.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void adiciona(Funcionario funcionarios) {
		String sql = "INSERT INTO funcionario (nome,dataNascimento,rg,cpf,sexo,email,"
				+ "telefone,rua,numRua,complemento,bairro,cidade,uf,cep,senha,login,tipo,loja_id) values"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement start = conexao.prepareStatement(sql);
			start.setString(1, funcionarios.getNome());
			start.setString(2, funcionarios.getDataNascimento());
			start.setString(3, funcionarios.getRg());
			start.setString(4, funcionarios.getCpf());
			start.setString(5, funcionarios.getSexo());
			start.setString(6, funcionarios.getEmail());
			start.setString(7, funcionarios.getTelefone());
			start.setString(8, funcionarios.getRua());
			start.setInt(9, funcionarios.getNumRua());
			start.setString(10, funcionarios.getComplemento());
			start.setString(11, funcionarios.getBairro());
			start.setString(12, funcionarios.getCidade());
			start.setString(13, funcionarios.getUf());
			start.setString(14, funcionarios.getCep());
			start.setString(15, funcionarios.getSenha());
			start.setString(16, funcionarios.getLogin());
			start.setString(17, funcionarios.getTipo());
			start.setString(18, funcionarios.getLoja());

			start.execute();
			start.close();
			conexao.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void alterar(Funcionario funcionarios) {
		try {
			PreparedStatement start = conexao
					.prepareStatement("update funcionario set nome=?,dataNascimento=?,rg=?,cpf=?,sexo=?,email=?,telefone=?,rua=?,numRua=?,"
							+ "complemento=?,bairro=?,cidade=?,uf=?,cep=?,senha=?,login=?,tipo=?,loja_id=? where id=?");
			start.setString(1, funcionarios.getNome());
			start.setString(2, funcionarios.getDataNascimento());
			start.setString(3, funcionarios.getRg());
			start.setString(4, funcionarios.getCpf());
			start.setString(5, funcionarios.getSexo());
			start.setString(6, funcionarios.getEmail());
			start.setString(7, funcionarios.getTelefone());
			start.setString(8, funcionarios.getRua());
			start.setInt(9, funcionarios.getNumRua());
			start.setString(10, funcionarios.getComplemento());
			start.setString(11, funcionarios.getBairro());
			start.setString(12, funcionarios.getCidade());
			start.setString(13, funcionarios.getUf());
			start.setString(14, funcionarios.getCep());
			start.setString(15, funcionarios.getSenha());
			start.setString(16, funcionarios.getLogin());
			start.setString(17, funcionarios.getTipo());
			start.setString(18, funcionarios.getLoja());
			start.setLong(19, funcionarios.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Funcionario> listar() {
		try {
			List<Funcionario> lista = new ArrayList<Funcionario>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from funcionario");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Funcionario f = new Funcionario();
				f.setId(rs.getLong("id"));
				f.setNome(rs.getString("nome"));
				f.setDataNascimento(rs.getString("dataNascimento"));
				f.setRg(rs.getString("rg"));
				f.setCpf(rs.getString("cpf"));
				f.setSexo(rs.getString("sexo"));
				f.setEmail(rs.getString("email"));
				f.setTelefone(rs.getString("telefone"));
				f.setRua(rs.getString("rua"));
				f.setNumRua(rs.getInt("numRua"));
				f.setComplemento(rs.getString("complemento"));
				f.setBairro(rs.getString("bairro"));
				f.setCidade(rs.getString("cidade"));
				f.setUf(rs.getString("uf"));
				f.setCep(rs.getString("cep"));
				f.setSenha(rs.getString("senha"));
				f.setLogin(rs.getString("login"));
				f.setTipo(rs.getString("tipo"));
				f.setLoja(rs.getString("loja_id"));

				lista.add(f);
			}
			rs.close();
			stmt.close();

			return lista;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Funcionario buscaId(int id) {
		Funcionario f = null;
		String sql = "select * from funcionario where id = ?";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				f = new Funcionario();
				f.setId(rs.getLong("id"));
				f.setNome(rs.getString("nome"));

		
				f.setDataNascimento(rs.getString("dataNascimento"));

				f.setRg(rs.getString("rg"));
				f.setCpf(rs.getString("cpf"));
				f.setSexo(rs.getString("sexo"));
				f.setEmail(rs.getString("email"));
				f.setTelefone(rs.getString("telefone"));
				f.setRua(rs.getString("rua"));
				f.setNumRua(rs.getInt("numRua"));
				f.setComplemento(rs.getString("complemento"));
				f.setBairro(rs.getString("bairro"));
				f.setCidade(rs.getString("cidade"));
				f.setUf(rs.getString("uf"));
				f.setCep(rs.getString("cep"));
				f.setSenha(rs.getString("senha"));
				f.setLogin(rs.getString("login"));
				f.setTipo(rs.getString("tipo"));
				f.setLoja(rs.getString("loja_id"));
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {

		}
		return f;
	}

	public List<Loja> listarLoja() {
		try {
			List<Loja> lista = new ArrayList<Loja>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from loja");
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

	public void remover(Funcionario funcionarios) {
		try {
			PreparedStatement start = conexao
					.prepareStatement("delete from funcionario where id=?");
			start.setLong(1, funcionarios.getId());

			start.execute();
			start.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Funcionario existeFuncionario(Funcionario funcionario) {
		if (funcionario == null) {
			throw new IllegalArgumentException("Funcionário não existe");
		}

		try {
			PreparedStatement stmt = conexao
					.prepareStatement("select * from funcionario where login=? and senha=? and tipo=?");
			stmt.setString(1, funcionario.getLogin());
			stmt.setString(2, funcionario.getSenha());
			stmt.setString(3, funcionario.getTipo());

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				funcionario.setId(rs.getLong("id"));
				funcionario.setNome(rs.getString("nome"));

		
				funcionario.setDataNascimento(rs.getString("dataNascimento"));

				funcionario.setRg(rs.getString("rg"));
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setSexo(rs.getString("sexo"));
				funcionario.setEmail(rs.getString("email"));
				funcionario.setTelefone(rs.getString("telefone"));
				funcionario.setRua(rs.getString("rua"));
				funcionario.setNumRua(rs.getInt("numRua"));
				funcionario.setComplemento(rs.getString("complemento"));
				funcionario.setBairro(rs.getString("bairro"));
				funcionario.setCidade(rs.getString("cidade"));
				funcionario.setUf(rs.getString("uf"));
				funcionario.setCep(rs.getString("cep"));
				funcionario.setSenha(rs.getString("senha"));
				funcionario.setLogin(rs.getString("login"));
				funcionario.setTipo(rs.getString("tipo"));
				funcionario.setLoja(rs.getString("loja_id"));
			} else {
				funcionario = null;
			}
			rs.close();
			stmt.close();

			return funcionario;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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
	
	
	public Funcionario login2(Funcionario funcionario) {
		if (funcionario == null) {
			throw new IllegalArgumentException("Funcionário não existe");
		}

		try {
			PreparedStatement stmt = conexao.prepareStatement("select * from funcionario where login=? and senha=?");
			stmt.setString(1, funcionario.getLogin());
			stmt.setString(2, funcionario.getSenha());
			
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				funcionario.setId(rs.getLong("id"));
				funcionario.setNome(rs.getString("nome"));

		
				funcionario.setDataNascimento(rs.getString("dataNascimento"));

				funcionario.setRg(rs.getString("rg"));
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setSexo(rs.getString("sexo"));
				funcionario.setEmail(rs.getString("email"));
				funcionario.setTelefone(rs.getString("telefone"));
				funcionario.setRua(rs.getString("rua"));
				funcionario.setNumRua(rs.getInt("numRua"));
				funcionario.setComplemento(rs.getString("complemento"));
				funcionario.setBairro(rs.getString("bairro"));
				funcionario.setCidade(rs.getString("cidade"));
				funcionario.setUf(rs.getString("uf"));
				funcionario.setCep(rs.getString("cep"));
				funcionario.setSenha(rs.getString("senha"));
				funcionario.setLogin(rs.getString("login"));
				funcionario.setTipo(rs.getString("tipo"));
				funcionario.setLoja(rs.getString("loja_id"));
			} else {
				funcionario = null;
			}
			rs.close();
			stmt.close();
			
			return funcionario;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Funcionario login(String login, String senha) {
		Funcionario f = null;
		
		try {
			PreparedStatement stmt = conexao.prepareStatement("select * from funcionario where login=? and senha=?");
			stmt.setString(1, 
					login);
			stmt.setString(2, senha);

			ResultSet rs = stmt.executeQuery();
			
			
			if (rs.next()) {
				f = new Funcionario();
				f.setId(rs.getLong("id"));
				f.setNome(rs.getString("nome"));

		
				f.setDataNascimento(rs.getString("dataNascimento"));

				f.setRg(rs.getString("rg"));
				f.setCpf(rs.getString("cpf"));
				f.setSexo(rs.getString("sexo"));
				f.setEmail(rs.getString("email"));
				f.setTelefone(rs.getString("telefone"));
				f.setRua(rs.getString("rua"));
				f.setNumRua(rs.getInt("numRua"));
				f.setComplemento(rs.getString("complemento"));
				f.setBairro(rs.getString("bairro"));
				f.setCidade(rs.getString("cidade"));
				f.setUf(rs.getString("uf"));
				f.setCep(rs.getString("cep"));
				f.setSenha(rs.getString("senha"));
				f.setLogin(rs.getString("login"));
				f.setTipo(rs.getString("tipo"));
				f.setLoja(rs.getString("loja_id"));
			} else {
				f = null;
			}
			
			rs.close();
			stmt.close();
			
			return f;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
