package br.com.ged.dto.audit;

import br.com.ged.anotations.EntityProperty;
import br.com.ged.domain.TipoOperacaoAudit;

public abstract class AbstractFiltroAuditDTO {

	@EntityProperty("tipoOperacaoAudit")
	protected TipoOperacaoAudit tipoOperacaoAudit;
	
	@EntityProperty("idEntidade")
	protected Long idEntidade;

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
}
