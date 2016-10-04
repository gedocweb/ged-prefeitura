package br.com.ged.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DataUtil {
	
	private DataUtil(){
		//Evita Instanciação dessa classe
	}
	
	public static String formataData(Date date, String pattern) {
		String dataUltimaAlteracaoFormat;
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		
		dataUltimaAlteracaoFormat = dateFormat.format(date);
		return dataUltimaAlteracaoFormat;
	}
	
	public static String parseYYYYmmDD(Date date){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		return df.format(date);
	}
	
	public static Date primeiraDataDoMesAtual(){
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	public static Date ultimaDataDoMesAtual(){
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}

	public static String parseYYYYmmDD_horaMinima(Date date) {
		return parseYYYYmmDD(date) +" 00:00:00";
	}

	public static String parseYYYYmmDD_horaMaxima(Date date) {
		return parseYYYYmmDD(date) +" 23:59:59";
	}
}