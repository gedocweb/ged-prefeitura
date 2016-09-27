package br.com.ged.domain;

public enum LayoutLoginEnum implements ConfigLayoutCliente{

	PRATIKA("logo_pratika.png","padding-left: 32%;","500px","250px"), 
	SOLUCAO("logo_solucao.png","padding-left: 32%;","500px","250px");

	private String nomeImage;
	private String styleCss;
	private String width;
	private String height;

	private LayoutLoginEnum (String nomeImagem, String styleCss, String width, String height){
		
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
