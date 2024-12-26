package com.treinamentoJasper.spring_jasper_treinamento.service.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.treinamentoJasper.spring_jasper_treinamento.exception.ParametrosInvalidosException;


@Service
public class DataHoraHelper {

	public static final String DATE_PATTERN = "dd/MM/yyyy";
	public static final String TIME_PATTERN = "HH:mm:ss";
	public static final String DATETIME_PATTERN = DATE_PATTERN + " - " + TIME_PATTERN;
	public static final String ZONE_ID = "America/Sao_Paulo";
	public static final String ZONE_OFFSET = "-3";
	public static final Locale BRASIL = new Locale("pt", "BR");
	public static final Long HORAMAIS = 1l;
	public static final String DATE_EXTENSO_PATTERN = "dd 'de' MMMM 'de' yyyy";

	public static final DateTimeFormatter FORMATO_PADRAO_DATA = DateTimeFormatter.ofPattern(DATE_PATTERN);

	public static final DateTimeFormatter FORMATO_PADRAO_DATATIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public static final DateTimeFormatter FORMATO_PADRAO_DATA_EXTENSO = DateTimeFormatter.ofPattern(DATE_EXTENSO_PATTERN);

	/**
	 * 
	 * Método para formatar o {@link LocalDateTime} recebido como parâmetro.
	 * 
	 * @param datetime {@link LocalDateTime}
	 * @return o {@link LocalDateTime} formato pelo método.
	 */

	public String dateTimeFormat(LocalDateTime datetime) {
		return datetime.format(DateTimeFormatter.ofPattern(DATETIME_PATTERN));
	}

	/**
	 * 
	 * Método para formatar a data no padrão dd/MM/yyyy.
	 * 
	 * @param date {@link LocalDateTime}
	 * @return a data formatada
	 */

	public String dateFormat(LocalDate date) {
		return date.format(FORMATO_PADRAO_DATA);
	}

	/**
	 * 
	 * Método para formatar a data no padrão dd/MM/yyyy.
	 * 
	 * @param datetime {@link LocalDateTime}
	 * @return a data formatada
	 */

	public String dateFormat(LocalDateTime datetime) {
		return datetime.format(FORMATO_PADRAO_DATA);
	}

	/**
	 * 
	 * Método para formatar a hora no padrão HH:mm:ss.
	 * 
	 * @param time {@link LocalTime}
	 * @return a hora formatada
	 */

	public String timeFormat(LocalTime time) {
		return time.format(DateTimeFormatter.ofPattern(TIME_PATTERN));
	}

	/**
	 * 
	 * Método que calcula um instante de tempo entre a datatime passada como
	 * parâmetro e a epoch date de acordo com a zona de fuso.
	 * 
	 * @param datetime {@link LocalDateTime}
	 * @return {@link Long} resultante do instante de tempo.
	 */

	public Long toEpochMilli(LocalDateTime datetime) {
		return datetime.toInstant(ZoneOffset.of(ZONE_OFFSET)).toEpochMilli();
	}

	/**
	 * 
	 * Método que calcula um instante de tempo entre a data passada como parâmetro
	 * iniciada a meia noite e a epoch date de acordo com a zona de fuso.
	 * 
	 * @param date {@link LocalDate}
	 * @return {@link Long} resultante do instante de tempo.
	 */

	public Long toEpochMilli(LocalDate date) {
		return date.atStartOfDay().toInstant(ZoneOffset.of(ZONE_OFFSET)).toEpochMilli();
	}

	/**
	 * 
	 * Método que recebe como parâmetro ano e mês que são tratados para retornar o
	 * mês completo conforme calendário gregoriano.
	 * 
	 * @param ano de referência
	 * @param mes de referência
	 * @return {@link String} como mês completo.
	 */

	public String mesCompleto(int ano, int mes) {
		return new GregorianCalendar(ano, mes - 1, 1).getDisplayName(Calendar.MONTH, Calendar.LONG, BRASIL);
	}

	/**
	 * 
	 * Método que recebe uma String de dataTime como parâmetro e trata a string para
	 * o formato dd/MM/yyyy HH:mm e convertendo para {@link LocalDateTime}
	 * 
	 * @param dataHora {@link String}
	 * @return {@link LocalDateTime}
	 */

	public LocalDateTime dataTime(String dataHora) {

		LocalDateTime dataTime = LocalDateTime.parse(dataHora, FORMATO_PADRAO_DATATIME);

		return dataTime;
	}

	/**
	 * 
	 * Método para pegar a dataTime local no momento e somar {@link #HORAMAIS} e
	 * formatar {@link #FORMATO_PADRAO_DATATIME}
	 * 
	 * @return {@link String}
	 */

	public String dataTimeNowPlus() {

		String dataTime = LocalDateTime.now().plusHours(HORAMAIS).format(FORMATO_PADRAO_DATATIME);

		return dataTime;
	}

	/**
	 * 
	 * Método para pegar o DataTime no momento e formatá-lo {@link #FORMATO_PADRAO_DATATIME}.
	 * 
	 * @return {@link String}
	 */
	public String dataTimeNow() {
		
		String dataTime = LocalDateTime.now().format(FORMATO_PADRAO_DATATIME);
		
		return dataTime;
	}

	/**
	 * 
	 * Método que recebe uma String de dataTime como parâmetro e trata a string para
	 * o formato dd/MM/yyyy e convertendo para {@link LocalDate}
	 * 
	 * @param data {@link String}
	 * @return {@link LocalDate}
	 */

	public LocalDate data(String data) {

		LocalDate novaData = LocalDate.parse(data);

		return novaData;
	}
	
	/**
	 * 
	 * Método que recebe uma String "MM" "MMM"
	 * 
	 * @param mes {@link String}
	 * @return {@link String}
	 */
	public String converteMes(String mes) {
		
		switch(mes) {
			case "1": 
			case "01": 
				return "JAN";
			case "2": 
			case "02": 
				return "FEV";
			case "3": 
			case "03": 
				return "MAR";
			case "4": 
			case "04": 
				return "ABR";
			case "5": 
			case "05": 
				return "MAI";
			case "6": 
			case "06": 
				return "JUN";
			case "7": 
			case "07": 
				return "JUL";
			case "8": 
			case "08": 
				return "AGO";
			case "9":
			case "09": 
				return "SET";
			case "10": 
				return "OUT";
			case "11": 
				return "NOV";
			case "12": 
				return "DEZ";
			default:
				throw new ParametrosInvalidosException("Mês não informado!");
		}
	}
	
	/**
	 * 
	 * Método para retornar o mês e ano no padrão MMM/YYYY
	 * 
	 * @param mes {@link String}
	 * @param ano {@link String}
	 * @return {@link String}
	 */
	public String converteMesTrigramaAno(String mes, String ano) {
	
		String mesTrigrama = this.converteMes(mes);
		String mmmAno = mesTrigrama+"/"+ano;
		
		return mmmAno;
	}
	
	/**
	 * 
	 * Método que recebe uma String "yyyy" e converte para "yyyy-12-31"
	 * 
	 * @param data {@link String}
	 * @return {@link LocalDate}
	 */
	public LocalDate dataFimAno(String ano) {
		
		LocalDate data = LocalDate.parse(ano+"-12-31");
		
		return data;
	}

	public String converDataPorExtenso(LocalDate data) {
		
		String dataExtenso = data.format(FORMATO_PADRAO_DATA_EXTENSO);
		
		return dataExtenso;
	}
}
