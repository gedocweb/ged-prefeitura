package br.com.ged.domain;

public enum LayoutMarcaDaguaEnum implements ConfigLayoutCliente{
	
	ARAUJO("marca_dagua_araujo.png","padding-left: 15%; padding-top: 4%;","700px","450px"), 
	SOLUCAO("marca_dagua_solucao.png","padding-left: 15%; padding-top: 4%;","700px","450px");

	private String nomeImage;
	private String styleCss;
	private String width;
	private String height;

	private LayoutMarcaDaguaEnum (String nomeImagem, String styleCss, String width, String height){
		
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
