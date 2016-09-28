package br.com.ged.domain;

import br.com.ged.util.InitConfigProperties;

public enum EmpresaEnum {

	PRATIKA(ClienteEnum.ARAUJO),
	SOLUCAO(ClienteEnum.SOLUCAO);
	
	private static final String CONFIG_PARAM_EMPRESA = "empresa";
	private static final String CONFIG_PARAM_CLIENTE = "cliente";
	
	private ClienteEnum[] config;
	
	private EmpresaEnum(ClienteEnum... config){
		this.config = config;
	}

	public ClienteEnum[] getConfig() {
		return config;
	}
	
	public static ClienteEnum configClienteEnumAtivo(){
		
		String nameEmpresaEnum = InitConfigProperties.getValue(CONFIG_PARAM_EMPRESA);
		String nameClienteEnum = InitConfigProperties.getValue(CONFIG_PARAM_CLIENTE);
		
		for (EmpresaEnum clEnum : values()){
			
			if (clEnum.name().equals(nameEmpresaEnum)){
				
				if (clEnum.config == null)
					continue;
				
				for (ClienteEnum config : clEnum.config){
					
					if (config.name().equals(nameClienteEnum)){
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