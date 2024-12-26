package com.treinamentoJasper.spring_jasper_treinamento.service.jasper;

import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.RELATORIO_FORMATO_HTML;
import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.RELATORIO_FORMATO_ODS;
import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.RELATORIO_FORMATO_ODT;
import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.RELATORIO_FORMATO_PDF;
import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.RELATORIO_FORMATO_XLSX;
import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.RELATORIO_FORMATO_DOCX;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.Tipos;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;


@Service
public class JasperService {

	public static final String REPORTS_PATH_SRC = "/template/src/main/resources/reports/";

	/**
	 * 
	 * Método para geração do relatório. Recebe a requisição e chama o método de
	 * geração de relatórios.
	 * 
	 * @param nomeRelatorio {@link String}
	 * @param parametros    {@link HashMap} parâmetros do relatório
	 * @param dataSource    {@link JRBeanCollectionDataSource} conjunto de dados que
	 *                      será percorrido para preencher o relatório
	 * @param tema
	 * @param response      {@link HttpServletResponse} Fluxo de saída da requisição
	 *                      HTTP GET
	 * @throws JRException
	 * @throws IOException
	 */
	public void gerandoRelatorio(String nomeRelatorio, HashMap<String, Object> parametros,
			JRBeanCollectionDataSource dataSource, Tipos tipo, HttpServletResponse response)
			throws JRException, IOException {
		
		String formato = tipo.toString();
		JasperReport jasperReport = compileReport(nomeRelatorio);
		JasperPrint jasperPrint = null;

//		// Tema
//        ArrayList<JRSimpleTemplate> templateList = themeService.getTheme(tema);
//        templateList.forEach(s -> System.out.println(s));
//        parametros.put(JRParameter.REPORT_TEMPLATES, templateList);

		if (dataSource == null)
			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
		else
			jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

		switch (formato) {
			case RELATORIO_FORMATO_PDF: {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline; filename=" + nomeRelatorio + "." + RELATORIO_FORMATO_PDF);
	
				final OutputStream outStream = response.getOutputStream();
	
				JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			}
			case RELATORIO_FORMATO_HTML: {
				byte[] bytes = export(jasperPrint);
	
				response.setContentType("text/html");
				response.setHeader("Content-Disposition", "inline; filename=" + nomeRelatorio + "." + RELATORIO_FORMATO_HTML);
				response.setContentLength(bytes.length);
	
				final OutputStream outStream = response.getOutputStream();
				outStream.write(bytes, 0, bytes.length);
				outStream.flush();
				outStream.close();
			}
			case RELATORIO_FORMATO_DOCX: {
	
				response.setContentType("application/ms-word");
				response.setHeader("Content-Disposition", "inline; filename=" + nomeRelatorio + "." + RELATORIO_FORMATO_DOCX);
	
				final OutputStream outStream = response.getOutputStream();
	
				try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
				/* OutputStream fileOutputStream = new FileOutputStream(outputFile) */) {
					JRDocxExporter exporter = new JRDocxExporter();
					exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
					exporter.exportReport();
					byteArrayOutputStream.writeTo(outStream);
				}
			}
			case RELATORIO_FORMATO_ODT: {
	
				response.setContentType("application/vnd.oasis.opendocument.text");
				response.setHeader("Content-Disposition", "inline; filename=" + nomeRelatorio + "." + RELATORIO_FORMATO_ODT);
	
				final OutputStream outStream = response.getOutputStream();
	
				try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
					JROdtExporter exporter = new JROdtExporter();
					exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
					exporter.exportReport();
					byteArrayOutputStream.writeTo(outStream);
				}
			}
			case RELATORIO_FORMATO_ODS: {
	
				response.setContentType("application/vnd.oasis.opendocument.text");
				response.setHeader("Content-Disposition", "inline; filename=" + nomeRelatorio + "." + RELATORIO_FORMATO_ODS);
	
				final OutputStream outStream = response.getOutputStream();
	
				try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
					JROdsExporter exporter = new JROdsExporter();
					exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
					exporter.exportReport();
					byteArrayOutputStream.writeTo(outStream);
				}
			}
			case RELATORIO_FORMATO_XLSX: {
	
				response.setContentType("application/ms-excel");
				response.setHeader("Content-Disposition", "inline; filename=" + nomeRelatorio + "." + RELATORIO_FORMATO_XLSX);
	
				final OutputStream outStream = response.getOutputStream();
	
				try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
				/* OutputStream fileOutputStream = new FileOutputStream(outputFile) */) {
					JRXlsxExporter exporter = new JRXlsxExporter();
					exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
					exporter.exportReport();
					byteArrayOutputStream.writeTo(outStream);
				}
			}
		}

	}

	/**
	 * 
	 * Compila um arquivo .jrxml para .jasper. Por padrão, todos os arquivos
	 * relacionados aos relatórios estão no diretório "/resources/reports".
	 * 
	 * @param reportFileName {@link String} nome do arquivo .jrxml a ser compilado
	 *                       para .jasper
	 * @return um objeto {@link JasperReport} já com os binários do relatório
	 *         carregados
	 */

	public JasperReport compileReport(String reportFileName) {

		JasperReport jasperReport = null;
		try {
			ClassPathResource classPathResource = new ClassPathResource("./reports/" + reportFileName + ".jrxml");

			InputStream inputStream = classPathResource.getInputStream();

			File tempFile = File.createTempFile("temp", ".jrxml");
			OutputStream outStream = new FileOutputStream(tempFile);

			try {
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1)
					outStream.write(buffer, 0, bytesRead);

			} finally {
				inputStream.close();
				outStream.close();
			}

			jasperReport = JasperCompileManager.compileReport(tempFile.getPath());

		} catch (IOException | JRException ex) {
			Logger.getLogger(JasperService.class.getName()).log(Level.SEVERE, null, ex);
		}
		return jasperReport;
	}

	/**
	 * 
	 * Método para compilar uma {@link JRBeanCollectionDataSource} e {@link HashMap}
	 * em um único {@link HashMap}.
	 * 
	 * @param dataSource {@link JRBeanCollectionDataSource}
	 * @param parametros {@link HashMap}
	 * @return {@link HashMap}
	 */
	public HashMap<String, Object> dadosResultantes(JRBeanCollectionDataSource dataSource,
			HashMap<String, Object> parametros) {
		HashMap<String, Object> dadosResultantes = new HashMap<>();
		dadosResultantes.put("parametros", parametros);
		dadosResultantes.put("dataSource", dataSource);
		return dadosResultantes;
	}

	/**
	 * 
	 * Método para fazer exportação do relatório
	 * 
	 * @param print {@link JasperPrint}
	 * @return o conteúdo atual deste fluxo como uma matriz de bytes.
	 * 
	 */

	public byte[] export(final JasperPrint print) throws JRException {
		final HtmlExporter exporter;
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		exporter = new HtmlExporter();

		SimpleHtmlExporterOutput sHtmlOut = new SimpleHtmlExporterOutput(out);

		exporter.setExporterOutput(sHtmlOut);
		exporter.setExporterInput(new SimpleExporterInput(print));

		SimpleHtmlReportConfiguration reportExportConfiguration = new SimpleHtmlReportConfiguration();
		reportExportConfiguration.setWhitePageBackground(false);
		reportExportConfiguration.setRemoveEmptySpaceBetweenRows(true);
		exporter.setConfiguration(reportExportConfiguration);

		exporter.exportReport();

		return out.toByteArray();
	}

}
