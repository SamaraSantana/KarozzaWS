package br.com.senai.json;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.dao.ExemplarDao;
import br.com.senai.dao.FuncionarioDao;
import br.com.senai.dao.LocacaoDao;
import br.com.senai.dao.VistoriaDao;
import br.com.senai.model.Exemplar;
import br.com.senai.model.Funcionario;
import br.com.senai.model.Locacao;
import br.com.senai.model.Vistoria;

import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@RestController
@RequestMapping("services/karozza")
public class GsonKarozza {
	@Autowired
	private FuncionarioDao daoF;
	@Autowired
	private LocacaoDao daoLocacao;
	@Autowired
	private VistoriaDao daoVistoria;

	@RequestMapping(value = "/listarLocacoes", method = RequestMethod.GET, headers = "accept=application/json;charset=utf-8")
	public List<Locacao> lista(Model model) {
		model.addAttribute("locacao", daoLocacao.ListaLocacao());
		return daoLocacao.ListaLocacao();
	}

	@RequestMapping(value = "/login/{login}/{senha}", method = RequestMethod.GET, headers = "accept=application/json;charset=utf-8")
	public Funcionario login(@PathVariable(value = "login") String login,
			@PathVariable(value = "senha") String senha) {
		Funcionario ff = daoF.login(login, senha);
		return ff;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "accept=application/json;charset=utf-8", consumes = "application/json;charset=UTF-8")
	public String logar(@RequestBody String jsonString, Funcionario f) {
		String retorno;

		JSONObject json = new JSONObject(jsonString);
		f = new Funcionario();

		f.setLogin(json.getString("login"));
		f.setSenha(json.getString("senha"));

		if (daoF.login2(f) != null) {
			retorno = "Logado com Sucesso!";
		} else {
			retorno = "Erro Encontrado";
		}
		return retorno;
	}

	@RequestMapping(value = "/locacao_id/{id}", method = RequestMethod.GET, headers = "accept=application/json;charset=utf-8")
	public Locacao buscaPorid(@PathVariable(value = "id") int id) {
		Locacao l = daoLocacao.buscaLocacao(id);

		return l;
	}

	@RequestMapping(value = "/salvar", method = RequestMethod.POST, headers = "accept=application/json;charset=utf-8")
	public String salvar(@RequestBody String jsonString) {
		String retorno = null;
		Gson gson = new Gson();
		System.out.println(jsonString);
		JSONObject json = new JSONObject(jsonString);

		Vistoria c = new Vistoria();
		if (c.equals(null)) {
			retorno = "Vistoria nula";
		} else {
			c.setId(json.getInt("id"));

			byte[] foto = Base64.decode(json.getString("bytearray"));
			if (foto == null) {
				retorno = "Escolha uma foto";
			}
			c.setFoto(foto);

			c.setKm(json.getInt("km"));
			c.setDocumentacao(json.getString("documentacao"));
			c.setFerramentas(json.getString("ferramentas"));
			c.setOpcionais(json.getString("opcionais"));
			c.setOutros(json.getString("outros"));
			c.setStatus(json.getString("status"));
			c.setObservacao(json.getString("observacao"));
			c.setLocacao(json.getString("locacao_id"));
			c.setExemplar(json.getString("exemplar_id"));
			c.setManutencao(json.getString("manutencao_id"));
			daoVistoria.adiciona(c);
			retorno = "inserido com sucesso";
		}
			return retorno;
		
	}

	@RequestMapping(value = "/alterar", method = RequestMethod.POST, headers = "accept=application/json;charset=utf-8")
	public String alterar(@RequestBody String jsonString2) {
		String retorno = null;

		System.out.println(jsonString2);
		JSONObject json = new JSONObject(jsonString2);

		Exemplar e = new Exemplar();

		e.setId(json.getInt("id"));

		e.setStatus(json.getString("status"));

		daoLocacao.alteraStatusExemplar(json.getString("status"),
				json.getInt("id"));
		retorno = "alterado com sucesso";

		return retorno;
	}
}
