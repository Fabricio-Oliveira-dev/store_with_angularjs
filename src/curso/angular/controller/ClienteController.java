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
import curso.angular.model.Cliente;

@Controller
@RequestMapping(value="/clientelist")
public class ClienteController extends DaoImplementacao<Cliente> implements 
		DaoInterface<Cliente> {

	public ClienteController(Class<Cliente> persistenceClass) { 
		super(persistenceClass);
	}
	@RequestMapping(value="salvar", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity salvar(@RequestBody String jsonCliente) throws Exception {
		Cliente cliente = new Gson().fromJson(jsonCliente, Cliente.class);
		
		if (cliente != null && cliente.getAtivo() == null) {
			cliente.setAtivo(false);
		}
		
		super.salvarOuAtualizar(cliente);
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value="listar", method=RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String listar() throws Exception {
		return new Gson().toJson(super.lista());
	}
	
	@RequestMapping(value="deletar/{codCliente}", method=RequestMethod.DELETE)
	public @ResponseBody String deletar(@PathVariable("codCliente") String codCliente) throws Exception {
		
		Cliente objeto = new Cliente();
		objeto.setId(Long.parseLong(codCliente));
		super.deletar(objeto);
		
		return "";
	}
	
	@RequestMapping(value="buscarcliente/{codCliente}", method=RequestMethod.GET)
	public @ResponseBody String buscarcliente(@PathVariable("codCliente") String codCliente) throws Exception {
		
		Cliente objeto = super.loadObjeto(Long.parseLong(codCliente));
		if (objeto == null) {
			return "{}";
		}
		return new Gson().toJson(objeto);
	}

}
