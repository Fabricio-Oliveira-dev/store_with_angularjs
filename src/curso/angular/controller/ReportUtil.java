package curso.angular.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.stereotype.Component;

@Component
public class ReportUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final String FOLDER_RELATORIOS = "/relatorios";
	
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	
	private String SEPARATOR = File.separator;
	
	private String caminhoArquivoRelatorio = null;
	
	private JRExporter tipoArquivoExportado = null;
	
	private String caminhoSubreport_Dir = "";
	
	private File arquivoGerado = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String geraRelatorio(
			List<?> listDataBeanColletionReport,
			HashMap parametrosRelatorio, 
			String nomeRelatorioJasper,
			String nomeRelatorioSaida, 
			ServletContext servletContext) throws JRException,
			FileNotFoundException {
		
		/*Cria a lista de collectionDataSource beans que carregam os dados para o relatório*/
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDataBeanColletionReport);

		/*Fornece o caminho fisico até a pasta que contém os relatórios compilador .jasper*/
		String caminhoRelatorio = servletContext.getRealPath(FOLDER_RELATORIOS);

		File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + ".jasper");

		if (caminhoRelatorio == null || (caminhoRelatorio != null && caminhoRelatorio.isEmpty()) || !file.exists()) {
			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIOS).getPath();
			SEPARATOR = "";
		}	
		
		/*caminho para imgens*/
		parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio); 

		/* caminho completo até o relatório compilado indicado */
		String caminhoArquivoJasper = caminhoRelatorio +  SEPARATOR + nomeRelatorioJasper + ".jasper";

		/* Faz o carregamento do relatório indicado. */
		JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivoJasper);

		/* Seta parâmetro SUBREPORT_DIR com o caminho fisico para sub-reports. */
		caminhoSubreport_Dir = caminhoRelatorio + SEPARATOR ;
		parametrosRelatorio.put(SUBREPORT_DIR, caminhoSubreport_Dir);

		/* Carrega o arquivo compilado para a mémoria. */
		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, jrbcds);

		tipoArquivoExportado = new JRPdfExporter();

		/* Caminho relatório exportado */
		caminhoArquivoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + ".pdf";

		/* Cria novo File exportado */
		arquivoGerado = new File(caminhoArquivoRelatorio);

		/* Prepara a impressão */
		tipoArquivoExportado.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);

		/* Nome do arquivo fisico a ser impresso/exportado */
		tipoArquivoExportado.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);

		/* Executa a exportação */
		tipoArquivoExportado.exportReport();

		/* Remove o arquivo do servidor após ser feito o download pelo usuário */
		arquivoGerado.deleteOnExit();
		
		return caminhoArquivoRelatorio;
	}
}
