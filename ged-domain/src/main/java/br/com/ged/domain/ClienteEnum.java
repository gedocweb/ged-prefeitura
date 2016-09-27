package br.com.ged.domain;

import br.com.ged.util.InitConfigProperties;

public enum ClienteEnum {

	PRATIKA(ConfigClienteEnum.ARAUJO),
	SOLUCAO(ConfigClienteEnum.SOLUCAO);
	
	private static final String CONFIG_PARAM_EMPRESA = "empresa";
	private static final String CONFIG_PARAM_CLIENTE = "cliente";
	
	private ConfigClienteEnum[] config;
	
	private ClienteEnum(ConfigClienteEnum... config){
		this.config = config;
	}

	public ConfigClienteEnum[] getConfig() {
		return config;
	}
	
	public static ConfigClienteEnum configClienteEnumAtivo(){
		
		String nameClienteEnum = InitConfigProperties.getValue(CONFIG_PARAM_EMPRESA);
		String nameConfigClienteEnum = InitConfigProperties.getValue(CONFIG_PARAM_CLIENTE);
		
		for (ClienteEnum clEnum : values()){
			
			if (clEnum.name().equals(nameClienteEnum)){
				
				if (clEnum.config == null)
					continue;
				
				for (ConfigClienteEnum config : clEnum.config){
					
					if (config.name().equals(nameConfigClienteEnum)){
						return config;
					}
				}
			}
		}
		
		return null;
	}
	
	public static ConfigLayoutCliente configLayoutLoginClientePorProperties(){
		
		return configClienteEnumAtivo().getLogin();
	}
	
	public static ConfigLayoutCliente configLayoutInfoUsuarioClientePorProperties(){
		
		return configClienteEnumAtivo().getInfoUsuario();
	}
	
	public static ConfigLayoutCliente configLayoutMarcaDaguaClientePorProperties(){
		
		return configClienteEnumAtivo().getMarcaDagua();
	}
}