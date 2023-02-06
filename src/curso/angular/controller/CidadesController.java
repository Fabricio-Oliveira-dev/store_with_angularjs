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
public class CidadesController extends DaoImplementacao<Cidades> implements
		DaoInterface<Cidades> {

	public CidadesController(Class<Cidades> persistenceClass) {
		super(persistenceClass);
	}

	/**
	 * Faz o carregamento das cidades de acordo com o estado
	 * @param codigoEstado
	 * @return JSON Cidades em String
	 * @throws Exception
	 */
	@RequestMapping(value = "listar/{idEstado}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String listar(@PathVariable("idEstado") String idEstado)throws Exception {
		return new Gson().toJson(lista(Long.parseLong(idEstado)));
	}
	
	
	/**
	 * Usado para carregar as cidades com jQuery quando o navegador � chrome 
	 * @param idEstado
	 * @return JSON
	 * @throws Exception
	 */
	@RequestMapping(value = "listarchrome", method = RequestMethod.GET)
	@ResponseBody
	public String listarChrome(@RequestParam("idEstado") String idEstado)throws Exception {
		return new Gson().toJson(lista(Long.parseLong(idEstado)));
	}

	/**
	 * Faz o carregamento das cidades de acordo com o estado
	 * @param codigoEstado
	 * @return List<Cidades> 
	 * @throws Exception
	 */
	public List<Cidades> lista(Long codigoEstado) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(getPersistenceClass());
		criteria.add(Restrictions.eq("estados.id", codigoEstado));
		return criteria.list();
	}

}
