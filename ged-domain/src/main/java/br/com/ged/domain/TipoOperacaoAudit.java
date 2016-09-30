package br.com.ged.domain;

public enum TipoOperacaoAudit {
	
	CADASTRO,
	
	EXPORTADO,
	
	ALTERACAO(Tempo.ANTES),
	
	BAIXADOS,
	
	EXCLUIR,
	
	VISUALIZACAO;
	
	private Tempo tempo;
	
	private TipoOperacaoAudit() {
		tempo = null;
	}
	
	private TipoOperacaoAudit(Tempo tempo) {
		
		this.tempo = tempo;
	}

	public Tempo getTempo() {
		return tempo;
	}

	public void setTempo(Tempo tempo) {
		this.tempo = tempo;
	}
}