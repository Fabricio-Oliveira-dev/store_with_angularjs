 package curso.angular.controller;

import java.util.ArrayList;
import java.util.List;

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
import curso.angular.model.Cliente;

@Controller
@RequestMapping(value="/cliente")
public class ClienteController extends DaoImplementacao<Cliente> implements 
		DaoInterface<Cliente> {

	public ClienteController(Class<Cliente> persistenceClass) { 
		super(persistenceClass); 
	}
	  
	/**
	 * Salva ou atualiza o cliente
	 * @param jsonCliente
	 * @return ResponseEntity
	 * @throws Exception
	 */
	 @RequestMapping(value="salvar", method= RequestMethod.POST)
	 @ResponseBody
	 public ResponseEntity salvar (@RequestBody String jsonCliente) throws Exception {
		 Cliente cliente = new Gson().fromJson(jsonCliente, Cliente.class);
		 
		 if (cliente != null && cliente.getAtivo() == null){
			 cliente.setAtivo(false);
		 }
		 
		 super.salvarOuAtualizar(cliente);
		 return new ResponseEntity<>(HttpStatus.CREATED);
		 
	 }
	
	
	 /**
	  * Retorna a lista de clientes cadastrados
	  * @return JSON String de Clientes
	  * @throws Exception
	  */
	@RequestMapping(value="listar/{numeroPagina}", method=RequestMethod.GET, headers = "Accept=application/json") 
	@ResponseBody
	public String listar(@PathVariable("numeroPagina") String numeroPagina) throws Exception {
		return new Gson().toJson(super.consultaPaginada(numeroPagina)); 
	}
	
	
	@RequestMapping(value="totalPagina", method=RequestMethod.GET, headers = "Accept=application/json") 
	@ResponseBody
	public String totalPagina() throws Exception {
		return ""+super.quantidadePagina(); 
	}
	 
	/**
	 * Delete o cliente informado
	 * @param codCliente
	 * @return String vazia como resposta
	 * @throws Exception
	 */
	@RequestMapping(value="deletar/{codCliente}", method=RequestMethod.DELETE)
	public  @ResponseBody String deletar (@PathVariable("codCliente") String codCliente) throws Exception {
		super.deletar(loadObjeto(Long.parseLong(codCliente)));
		return "";
	}
	
	
	/**
	 * Consulta e retorna o cliente com o codigo informado
	 * @param codCliente
	 * @return JSON cliente pesquisado
	 * @throws Exception
	 */
	@RequestMapping(value="buscarcliente/{codCliente}", method=RequestMethod.GET)
	public  @ResponseBody String buscarCliente (@PathVariable("codCliente") String codCliente) throws Exception {
		Cliente objeto = super.loadObjeto(Long.parseLong(codCliente));
		if (objeto == null) {
			return "{}";
		}
		return new Gson().toJson(objeto);
	}
	
	
	/**
	 * Consulta e retorna o cliente com o nome  informado
	 * @param nomeCliente
	 * @return JSON cliente pesquisado
	 * @throws Exception
	 */
	@RequestMapping(value="buscarnome/{nomeCliente}", method=RequestMethod.GET)
	public  @ResponseBody String buscarNome (@PathVariable("nomeCliente") String nomeCliente) throws Exception {
		List<Cliente> clientes = new ArrayList<Cliente>();
		clientes = super.listaLikeExpression("nome", nomeCliente);

		if (clientes == null || clientes.isEmpty() ) {
			return "{}";
		}
		
		return new Gson().toJson(clientes);
	}
	
}
