package br.com.ged.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.com.ged.domain.Mensagem;

public final class InitConfigProperties {
	
	private static final String ARQUIVO_CONFIGURACAO = "/config.properties";
	public static final String LIMITE_USUARIO = "limite-usuario";
	
	private static Properties properties;
	
	private InitConfigProperties(){
		//Evitando instanciação
	}

	static {
		InputStream inStream;
		properties = new Properties();
		inStream = InitConfigProperties.class.getResourceAsStream(ARQUIVO_CONFIGURACAO);
		try {
			properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties getProperties() {
		return properties;
	}
	
	public static String getValue(String param) {
		return properties.getProperty(param);
	}
}