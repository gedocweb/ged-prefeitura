package br.com.ged.generics;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import br.com.ged.domain.Tempo;
import br.com.ged.domain.TipoOperacaoAudit;

@MappedSuperclass
public abstract class EntidadeBasicaAudit implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Column(name="tipo_operacao_auditoria", nullable=false)
	@Enumerated(EnumType.STRING)
	protected TipoOperacaoAudit tipoOperacaoAudit;
	
	@Column(name="id_entidade")
	protected Long idEntidade;
	
	@Column(name="tempo")
	protected Tempo tempo;
	
	public EntidadeBasicaAudit(Long idEntidade, TipoOperacaoAudit tipoOperacaoAudit) {
		setIdEntidade(idEntidade);
		setTipoOperacaoAudit(tipoOperacaoAudit);
	}
	
	public EntidadeBasicaAudit(){
		
	}

	public abstract Long getId();

	public TipoOperacaoAudit getTipoOperacaoAudit() {
		return tipoOperacaoAudit;
	}

	public void setTipoOperacaoAudit(TipoOperacaoAudit tipoOperacaoAudit) {
		this.tipoOperacaoAudit = tipoOperacaoAudit;
	}

	public Long getIdEntidade() {
		return idEntidade;
	}

	public void setIdEntidade(Long idEntidade) {
		this.idEntidade = idEntidade;
	}

	public Tempo getTempo() {
		return tempo;
	}

	public void setTempo(Tempo tempo) {
		this.tempo = tempo;
	} 
}
