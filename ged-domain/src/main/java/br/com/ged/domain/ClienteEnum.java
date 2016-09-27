package br.com.ged.domain;

public enum ClienteEnum {
	
	ARAUJO(LayoutLoginEnum.PRATIKA,LayoutMarcaDaguaEnum.ARAUJO, LayoutInfoUsuarioEnum.ARAUJO), 
	SOLUCAO(LayoutLoginEnum.SOLUCAO,LayoutMarcaDaguaEnum.SOLUCAO, LayoutInfoUsuarioEnum.SOLUCAO);

	private LayoutLoginEnum login;
	private LayoutMarcaDaguaEnum marcaDagua;
	private LayoutInfoUsuarioEnum infoUsuario;
	
	private ClienteEnum(LayoutLoginEnum login, LayoutMarcaDaguaEnum marcaDagua, LayoutInfoUsuarioEnum infoUsuario){
		
		this.login = login;
		this.marcaDagua = marcaDagua;
		this.infoUsuario = infoUsuario;
	}

	public LayoutLoginEnum getLogin() {
		return login;
	}

	public LayoutMarcaDaguaEnum getMarcaDagua() {
		return marcaDagua;
	}

	public LayoutInfoUsuarioEnum getInfoUsuario() {
		return infoUsuario;
	}
}
