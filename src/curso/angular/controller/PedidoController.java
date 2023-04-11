package curso.angular.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import curso.angular.dao.DaoImplementacao;
import curso.angular.dao.DaoInterface;
import curso.angular.model.ItemPedido;
import curso.angular.model.Pedido;
import curso.angular.model.PedidoBean;

@Controller
@RequestMapping(value = "/pedido")
public class PedidoController extends DaoImplementacao<Pedido> implements DaoInterface<Pedido> {

	@Autowired
	private ItemPedidoController itemPedidoController;

	public PedidoController(Class<Pedido> persistenceClass) {
		super(persistenceClass);
	}

	@RequestMapping(value = "grafico", method = RequestMethod.GET)
	public @ResponseBody String grafico() {

		String sql = "SELECT TRUNC(AVG(ip.quantidade),2) AS media, l.titulo"
				+ " FROM livro l "
				+ " INNER JOIN  itempedido ip ON ip.livro_id = l.id"
				+ " GROUP BY l.id";
		
		List<Object[]> lista = getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
		Object[] retorno = new Object[lista.size() + 1];
		int cont = 0;
		retorno[cont] = "[\"" + "Livro" +  "\"," + "\"" + "Quantidade " + "\"]";
		cont ++;
		
		for (Object[] object : lista) {
			retorno[cont] = "[\"" + object[1] +  "\"," + "\"" + object[0] + "\"]";
			cont ++;
		}
		return Arrays.toString(retorno); 
	}

	@RequestMapping(value = "finalizar", method = RequestMethod.POST)
	@ResponseBody
	public String finalizar(@RequestBody String jsonPedido) throws Exception {

		PedidoBean pedidoBean = new Gson().fromJson(jsonPedido, PedidoBean.class);

		Pedido pedido = pedidoBean.getPedido();
		pedido = super.merge(pedido);

		List<ItemPedido> inItemPedidos = pedidoBean.getItens();
		for (ItemPedido itemPedido : inItemPedidos) {
			itemPedido.setPedido(pedido);
			itemPedidoController.salvar(itemPedido);
		}
		return pedido.getId().toString();
	}

	@RequestMapping(value = "listar", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public byte[] listar() throws Exception {
		return new Gson().toJson(super.lista()).getBytes("UTF-8");
	}

	@RequestMapping(value = "deletar/{codPedido}", method = RequestMethod.DELETE)
	public @ResponseBody String deletar(@PathVariable("codPedido") String codPedido) throws Exception {

		List<ItemPedido> itemPedidos = itemPedidoController.lista("pedido.id", Long.parseLong(codPedido));

		for (ItemPedido itemPedido : itemPedidos) {
			itemPedidoController.deletar(itemPedido);
		}
		super.deletar(loadObjeto(Long.parseLong(codPedido)));
		
		return new Gson().toJson(super.lista());
	}
}
