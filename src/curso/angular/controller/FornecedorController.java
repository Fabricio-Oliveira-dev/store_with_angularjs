package curso.angular.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import curso.angular.dao.DaoImplementacao;
import curso.angular.dao.DaoInterface;
import curso.angular.model.Fornecedor;

@Controller
@RequestMapping(value = "/fornecedor")
public class FornecedorController extends DaoImplementacao<Fornecedor> implements DaoInterface<Fornecedor> {

	public FornecedorController(Class<Fornecedor> persistenceClass) {
		super(persistenceClass);
	}

	/**
	 * Salva ou atualiza o fornecedor
	 * 
	 * @param jsonFornecedor
	 * @return ResponseEntity
	 * @throws Exception
	 */
	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity salvar(@RequestBody String jsonFornecedor) throws Exception {
		Fornecedor fornecedor = new Gson().fromJson(jsonFornecedor, Fornecedor.class);

		if (fornecedor != null && fornecedor.getAtivo() == null) {
			fornecedor.setAtivo(false);
		}

		super.salvarOuAtualizar(fornecedor);
		return new ResponseEntity(HttpStatus.CREATED);

	}

	/**
	 * Retorna a lista de fornecedor cadastrados
	 * 
	 * @return JSON String de fornecedor
	 * @throws Exception
	 */
	@RequestMapping(value = "listar/{numeroPagina}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String listar(@PathVariable("numeroPagina") String numeroPagina) throws Exception {
		return new Gson().toJson(super.consultaPaginada(numeroPagina));
	}

	@RequestMapping(value = "listartodos", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String listartodos() throws Exception {
		return new Gson().toJson(super.lista());
	}

	@RequestMapping(value = "totalPagina", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String totalPagina() throws Exception {
		return "" + super.quantidadePagina();
	}

	/**
	 * Delete o fornecedor informado
	 * 
	 * @param codFornecedor
	 * @return String vazia como resposta
	 * @throws Exception
	 */
	@RequestMapping(value = "deletar/{codFornecedor}", method = RequestMethod.DELETE)
	public @ResponseBody String deletar(@PathVariable("codFornecedor") String codFornecedor) throws Exception {
		super.deletar(loadObjeto(Long.parseLong(codFornecedor)));
		return "";
	}

	/**
	 * Consulta e retorna o fornecedor com o codigo informado
	 * 
	 * @param codFornecedor
	 * @return JSON fornecedor pesquisado
	 * @throws Exception
	 */
	@RequestMapping(value = "buscarfornecedor/{codFornecedor}", method = RequestMethod.GET)
	public @ResponseBody String buscarFornecedor(@PathVariable("codFornecedor") String codFornecedor) throws Exception {
		Fornecedor objeto = super.loadObjeto(Long.parseLong(codFornecedor));
		if (objeto == null) {
			return "{}";
		}
		return new Gson().toJson(objeto);
	}

}
