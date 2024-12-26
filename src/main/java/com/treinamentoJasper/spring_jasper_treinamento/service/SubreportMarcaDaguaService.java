package com.treinamentoJasper.spring_jasper_treinamento.service;

import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.CURSO_JASPER;
import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.CURSO_ESPECIFICATION;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.treinamentoJasper.spring_jasper_treinamento.controller.jasper.pojo.SubRelPOJO;
import com.treinamentoJasper.spring_jasper_treinamento.controller.jasper.pojo.TreinamentoJasperPOJO;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.model.CursoUsuario;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.repository.CursoUsuarioRepository;
import com.treinamentoJasper.spring_jasper_treinamento.service.helper.DataHoraHelper;
import com.treinamentoJasper.spring_jasper_treinamento.service.jasper.JasperService;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class SubreportMarcaDaguaService {

	@Autowired
	CursoUsuarioRepository cursoUsuarioRepository;

	@Autowired
	TreinamentoJasperService treinamentoJasperService;

	@Autowired
	JasperService jasperService;

	@Autowired
	DataHoraHelper dataHoraHelperService;

	public HashMap<String, Object> gerarRelatorioSubReportMarcaJasper() {

		HashMap<String, Object> dadosResultantes = new HashMap<>();
		HashMap<String, Object> parametros = new HashMap<>();
		List<SubRelPOJO> globalPojos = new ArrayList<>();
		List<TreinamentoJasperPOJO> treinamentosJasperPojo = new ArrayList<>();
		SubRelPOJO subRelPojo = new SubRelPOJO();
		
		subRelPojo.setData(dataHoraHelperService.converDataPorExtenso(LocalDate.now()));
		
		globalPojos.add(subRelPojo);
		
		List<CursoUsuario> cursoUsuario = cursoUsuarioRepository.findAll();
		List<CursoUsuario> cursoJasper = cursoUsuario.stream()
				.filter(c -> c.getId().getCurso().getNomeCurso().equalsIgnoreCase(CURSO_JASPER))
				.collect(Collectors.toList());
		List<CursoUsuario> cursoEspecification = cursoUsuario.stream()
				.filter(c -> c.getId().getCurso().getNomeCurso().equalsIgnoreCase(CURSO_ESPECIFICATION))
				.collect(Collectors.toList());

		treinamentosJasperPojo.add(treinamentoJasperService.preencheTreinamentoJasperPojo(cursoJasper));
		treinamentosJasperPojo.add(treinamentoJasperService.preencheTreinamentoJasperPojo(cursoEspecification));

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(globalPojos);
		JasperReport subReportCompile = jasperService.compileReport("usandoSubreport");
		parametros.put("parTreinamentoJasperPojo", treinamentosJasperPojo);
		parametros.put("parJrTreinamentoJasperPojo", new JRBeanCollectionDataSource(treinamentosJasperPojo));
		parametros.put("parSubreportCompile", subReportCompile);

		this.criandoMarcaDagua(parametros, "Treinamento JasperReport");
		ClassPathResource markupImage = new ClassPathResource("./images/imagemMarca.png");
		parametros.put("parMarcaDagua", markupImage.getPath());
		
		dadosResultantes.put("parametros", parametros);
		dadosResultantes.put("dataSource", dataSource);

		return dadosResultantes;
	}

	private void criandoMarcaDagua(HashMap<String, Object> parametros, String texto) {
		BufferedImage img = new BufferedImage(2480, 3508, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();

		AffineTransform at = new AffineTransform();
		Font font = new Font("SansSerif", Font.CENTER_BASELINE, 150);
		at.setToRotation(Math.PI / -4.0);
		g2d.setFont(font);
		g2d.setTransform(at);
		AlphaComposite ac = 
			      AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.1f); 
			  g2d.setComposite(ac); 
		int width = -1200, height = 2100;
		g2d.setPaint(Color.black);
		g2d.drawString(texto, width, height);

		File imagem = new File(texto + ".png");
		try {
			ImageIO.write(img, "png", imagem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		parametros.put("parTextoImagem", imagem.getPath());
		
	}

}
