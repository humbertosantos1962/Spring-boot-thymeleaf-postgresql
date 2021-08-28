package curso.springboot.controller;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@SuppressWarnings("serial")
@Component
public class ReportUtil implements Serializable {
	
	/*Retorna nosso pdf rm byte para download no navegador*/
	@SuppressWarnings("rawtypes")
	public byte[]  gerarRelatorio (List listDados , String relatorio , 
			ServletContext servletContext) throws Exception  {
		
		
		/*Cria a lista de dados para o relatório com nossa lista de objectos para imprimir*/
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDados);
		
		/*Carregar o caminho do arquivo jasper compilado*/
		String caminhoJasper = servletContext.getRealPath("relatorios") + File.separator + relatorio + ".jasper";
		
		/*Carrega o arquivo jasper passando os dados*/
		@SuppressWarnings("unchecked")
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper,new HashMap(), jrbcds);
		
		
		/*Exporta para byte[] para fazer o download do PDF*/
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}

}
