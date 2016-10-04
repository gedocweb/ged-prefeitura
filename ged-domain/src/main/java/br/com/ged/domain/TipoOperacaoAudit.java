package br.com.ged.domain;

public enum TipoOperacaoAudit {
	
	//Documento...
	
	CADASTRADO("Cadastro"),
	
	EXPORTADO("Exportado"),
	
	ALTERADO_PRE("Alterado"),
	
	ALTERADO_POS("Alterado"),
	
	BAIXADO("Baixado"),
	
	EXCLUIDO("Excluido"),
	
	VISUALIZADO("Visualizado");
	
	private String label;
	
	private TipoOperacaoAudit(String label) {
		
		this.label = label;
	}

	public Boolean getOperacaoAlterar() {
		return operacaoAlterar(this);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static boolean operacaoAlterar(TipoOperacaoAudit tipoOperacaoAudit) {
		return ALTERADO_PRE.equals(tipoOperacaoAudit) || ALTERADO_POS.equals(tipoOperacaoAudit);
	}
}