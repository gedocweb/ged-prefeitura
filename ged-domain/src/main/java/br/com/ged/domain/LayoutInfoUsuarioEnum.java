package br.com.ged.domain;

public enum LayoutInfoUsuarioEnum implements ConfigLayoutCliente{
	
	ARAUJO("logo_araujo.png","padding-left: 36%;","100px","50px"), 
	SOLUCAO("logo_solucao.png","padding-left: 32%;","150px","50px");

	private String nomeImage;
	private String styleCss;
	private String width;
	private String height;

	private LayoutInfoUsuarioEnum (String nomeImagem, String styleCss, String width, String height){
		
		this.nomeImage = nomeImagem;
		this.styleCss = styleCss;
		this.width = width;
		this.height = height;
	}

	public String getNomeImage() {
		return nomeImage;
	}

	public String getStyleCss() {
		return styleCss;
	}

	public String getWidth() {
		return width;
	}

	public String getHeight() {
		return height;
	}
}
