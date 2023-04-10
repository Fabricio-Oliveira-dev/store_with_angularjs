package curso.angular.controller;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import curso.angular.dao.DaoImplementacao;
import curso.angular.dao.DaoInterface;
import curso.angular.model.Cidades;

@Controller
@RequestMapping(value = "/cidades")
public class CidadesController extends DaoImplementacao<Cidades> implements DaoInterface<Cidades> {

	public CidadesController(Class<Cidades> persistenceClass) {
		super(persistenceClass);
	}

	/**
	 * Faz o carregamento das cidades de acordo com o estado
	 * @return JSON Cidades em String
	 */
	@RequestMapping(value = "listar/{idEstado}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public byte[] listar(@PathVariable("idEstado") String idEstado)throws Exception {
		return new Gson().toJson(lista(Long.parseLong(idEstado))).getBytes("UTF-8");
	}
	
	/**
	 * Usado para carregar as cidades com jQuery quando o navegador é Google Chrome 
	 * @return JSON
	 */
	@RequestMapping(value = "listarchrome", method = RequestMethod.GET)
	@ResponseBody
	public byte[] listarChrome(@RequestParam("idEstado") String idEstado)throws Exception {
		return new Gson().toJson(lista(Long.parseLong(idEstado))).getBytes("UTF-8");
	}

	/**
	 * Faz o carregamento das cidades de acordo com o estado
	 * @return List<Cidades> 
	 */
	public List<Cidades> lista(Long codigoEstado) throws Exception {
		Criteria criteria = getSessionFactory()
				.getCurrentSession()
				.createCriteria(getPersistenceClass());
		
		criteria.add(Restrictions.eq("estados.id", codigoEstado));
		return criteria.list();
	}
}
