package curso.angular.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import curso.angular.model.ItemPedido;
import curso.angular.model.Pedido;
import curso.angular.model.PedidoBean;

@Controller
@RequestMapping("/imprimirPedido")
public class FileDownloadController {
	
	
	@Autowired
	private ReportUtil reportUtil;
	
	@Autowired
	private PedidoController pedidoController;
	
	@Autowired
	private ItemPedidoController itemPedidoController;

	/**
	 *Tamanho de um buffer de bytes para ler / gravar arquivo
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Caminho do arquivo a ser baixado, relativo ao diretório do aplicativo
	 */
	private String filePath = "";

	/**
	 * Método para tratamento de solicitação de download de arquivo do cliente
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(value="pedido/{codigoPedido}", method = RequestMethod.GET)
	public void downloadPdfPedido(HttpServletRequest request,
			HttpServletResponse response, @PathVariable("codigoPedido") String codigoPedido) throws NumberFormatException, Exception {

		ServletContext context = request.getServletContext();
		
		Pedido pedido = pedidoController.loadObjeto(Long.parseLong(codigoPedido));
		ArrayList<ItemPedido> itemPedidos =(ArrayList<ItemPedido>) itemPedidoController.lista("pedido.id", Long.parseLong(codigoPedido));
		
		PedidoBean pedidoBean = new PedidoBean(); 
		pedidoBean.setPedido(pedido);
		pedidoBean.setItens(itemPedidos);
		
		List dados =  new ArrayList();
		dados.add(pedidoBean);
		
		filePath = reportUtil.geraRelatorio(dados,
				new HashMap(),
				"rel_pedido", 
				"rel_pedido", 
				context);
		
		//Construir o caminho completo absoluto do arquivo
		File downloadFile = new File(filePath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		//Obter o tipo MIME do arquivo
		String mimeType = context.getMimeType(filePath);
		if (mimeType == null) {
			// Definido como tipo binário se mapeamento MIME não encontrado
			mimeType = "application/octet-stream";
		}

		// Definir atributos de conteúdo para a resposta
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// Definir cabeçalhos para a resposta
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// Obter o fluxo de saída da resposta
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// Escrever bytes lidos a partir do fluxo de entrada para o fluxo de saída
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outStream.close();

	}
}