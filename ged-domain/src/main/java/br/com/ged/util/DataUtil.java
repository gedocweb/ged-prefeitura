package br.com.ged.util;

import java.util.Date;

public final class DataUtil {
	
	private DataUtil(){
		//Evita Instanciação dessa classe
	}
	
	public static Date primeiraDataDoMesAtual(){
		return new Date();
	}
	
	public static Date ultimaDataDoMesAtual(){
		
		return new Date();
	}
}