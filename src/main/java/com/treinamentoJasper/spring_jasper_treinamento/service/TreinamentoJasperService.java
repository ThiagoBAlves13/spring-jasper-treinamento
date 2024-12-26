package com.treinamentoJasper.spring_jasper_treinamento.service;

import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.CURSO_ESPECIFICATION;
import static com.treinamentoJasper.spring_jasper_treinamento.ServletInitializer.CURSO_JASPER;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.treinamentoJasper.spring_jasper_treinamento.controller.jasper.pojo.TreinamentoJasperPOJO;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.model.CursoUsuario;
import com.treinamentoJasper.spring_jasper_treinamento.persistence.repository.CursoUsuarioRepository;
import com.treinamentoJasper.spring_jasper_treinamento.service.helper.DataHoraHelper;
import com.treinamentoJasper.spring_jasper_treinamento.service.jasper.JasperService;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class TreinamentoJasperService {

	@Autowired
	CursoUsuarioRepository cursoUsuarioRepository;

	@Autowired
	JasperService jasperService;

	@Autowired
	DataHoraHelper dataHoraHelperService;

	public HashMap<String, Object> gerarRelatorioTreinamentoJasper(String uf, String cidade, boolean subtitulo) {

		HashMap<String, Object> parametros = new HashMap<>();
		List<TreinamentoJasperPOJO> treinamentosJasperPojo = new ArrayList<>();
		List<CursoUsuario> cursoUsuario = cursoUsuarioRepository.findAll();
		this.preencheParametros(parametros, uf, cidade, subtitulo);
		List<CursoUsuario> cursoJasper = cursoUsuario.stream()
				.filter(c -> c.getId().getCurso().getNomeCurso().equalsIgnoreCase(CURSO_JASPER))
				.collect(Collectors.toList());
		List<CursoUsuario> cursoEspecification = cursoUsuario.stream()
				.filter(c -> c.getId().getCurso().getNomeCurso().equalsIgnoreCase(CURSO_ESPECIFICATION))
				.collect(Collectors.toList());

		treinamentosJasperPojo.add(this.preencheTreinamentoJasperPojo(cursoJasper));
		treinamentosJasperPojo.add(this.preencheTreinamentoJasperPojo(cursoEspecification));

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(treinamentosJasperPojo);

		return jasperService.dadosResultantes(dataSource, parametros);
	}

	private void preencheParametros(HashMap<String, Object> parametros, String uf, String cidade, boolean subtitulo) {

		ClassPathResource markupImage = new ClassPathResource("./images/markup.png");
		ClassPathResource noImage = new ClassPathResource("./images/noImage.png");
		ClassPathResource inserirParametro = new ClassPathResource("./images/inserindoParametro.png");
		ClassPathResource parametroImagem = new ClassPathResource("./images/parNoImage.png");
		ClassPathResource clonandoJrBean = new ClassPathResource("./images/clonandoJrBean.png");

//		BufferedImage img = new BufferedImage(2480, 3508, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g2d = img.createGraphics();
//
//		AffineTransform at = new AffineTransform();
//		Font font = new Font("SansSerif", Font.CENTER_BASELINE, 150);
//		at.setToRotation(Math.PI / -4.0);
//		g2d.setFont(font);
//		g2d.setTransform(at);
//		AlphaComposite ac = 
//			      AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.1f); 
//			  g2d.setComposite(ac); 
//		int width = -1200, height = 2100;
//		g2d.setPaint(Color.black);
//		g2d.drawString("Sistemas Informatizados", width, height);
//
//		File imagem = new File("marcaDagua.png");
//		try {
//			ImageIO.write(img, "png", imagem);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		parametros.put("marcaDagua", imagem.getPath());
		parametros.put("parMarkup", markupImage.getPath());
		parametros.put("parNoImage", noImage.getPath());
		parametros.put("parInserirParametro", inserirParametro.getPath());
		parametros.put("parParametroImagem", parametroImagem.getPath());
		parametros.put("clonandoJrBean", clonandoJrBean.getPath());
		parametros.put("cidade", cidade);
		parametros.put("uf", uf != null ? uf.toUpperCase() : uf);
		parametros.put("ano", LocalDate.now().getYear());
		parametros.put("dia", LocalDate.now().getDayOfMonth());
		parametros.put("mes", LocalDate.now().getMonth().toString().toLowerCase());
		parametros.put("subtitulo", subtitulo);

	}

	public TreinamentoJasperPOJO preencheTreinamentoJasperPojo(List<CursoUsuario> cursosUsuario) {
		TreinamentoJasperPOJO treinamentoJasperPOJO = new TreinamentoJasperPOJO();
		List<String> alunos = new ArrayList<>();

		treinamentoJasperPOJO.setCurso(cursosUsuario.get(0).getId().getCurso().getNomeCurso());

		cursosUsuario.forEach(c -> {
			String aluno = new String();
			aluno = c.getId().getUsuario().getNome();
			alunos.add(aluno);
		});

		treinamentoJasperPOJO.setNomes(alunos);
		treinamentoJasperPOJO.setJrNomes(new JRBeanCollectionDataSource(alunos));
		return treinamentoJasperPOJO;
	}

}
