package curso.angular.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import curso.angular.dao.DaoImplementacao;
import curso.angular.dao.DaoInterface;
import curso.angular.model.ItemPedido;
import curso.angular.model.Livro;
import curso.angular.model.Pedido;

@Controller
@RequestMapping(value="/itempedido")
public class ItemPedidoController extends DaoImplementacao<ItemPedido> implements
		DaoInterface<ItemPedido> {
	
	@Autowired
	private LivroController livroController;

	public ItemPedidoController(Class<ItemPedido> persistenceClass) {
		super(persistenceClass);
	}
	
	@RequestMapping(value="processar/{itens}")
	public @ResponseBody String processar(@PathVariable("itens") String itens) throws Exception{
		List<Livro> livros = livroController.lista(itens);
		List<ItemPedido> itemPedidos = new ArrayList<ItemPedido>();
		
		Pedido pedido = new Pedido();
		BigDecimal valorTotal = BigDecimal.ZERO;
		for (Livro livro: livros) {
			String valor = livro.getValor().replace("R", "").replace("$", "").replaceAll("\\.", "").replaceAll("\\,", ".");
			valorTotal = valorTotal.add(new BigDecimal(valor.trim()));
		}
		
		pedido.setValorTotal("R$" + valorTotal.setScale(2, RoundingMode.HALF_DOWN).toString());
		for (Livro livro: livros) {
			ItemPedido itemPedido  = new ItemPedido();
			itemPedido.setLivro(livro);
			itemPedido.setPedido(pedido);
			itemPedido.setQuantidade(1L);
			itemPedidos.add(itemPedido);
		}
		
		return new Gson().toJson(itemPedidos);
	}
	

}
