package br.com.ged.dto.audit;

import br.com.ged.anotations.EntityProperty;
import br.com.ged.domain.entidade.TipoOperacaoAudit;

public abstract class AbstractFiltroAuditDTO {

	@EntityProperty("tipoOperacaoAudit")
	protected TipoOperacaoAudit tipoOperacaoAudit;
	
	public TipoOperacaoAudit getTipoOperacaoAudit() {
		return tipoOperacaoAudit;
	}

	public void setTipoOperacaoAudit(TipoOperacaoAudit tipoOperacaoAudit) {
		this.tipoOperacaoAudit = tipoOperacaoAudit;
	}

}
