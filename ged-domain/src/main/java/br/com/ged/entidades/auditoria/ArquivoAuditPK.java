package br.com.ged.entidades.auditoria;
 
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.ged.domain.entidade.TipoOperacaoAudit;
 
@Embeddable
public class ArquivoAuditPK implements Serializable{
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2468726583893245067L;

	@Column(name="data_hora")
	protected Long dataHora;

	@Column(name="tp_operacao")
	@Enumerated(EnumType.STRING)
	protected TipoOperacaoAudit tipoOperacaoAudit;
	
	@Column(name="id_arquivo")
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

	public Long getDataHora() {
		return dataHora;
	}

	public void setDataHora(Long dataHora) {
		this.dataHora = dataHora;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataHora == null) ? 0 : dataHora.hashCode());
		result = prime * result + ((idEntidade == null) ? 0 : idEntidade.hashCode());
		result = prime * result + ((tipoOperacaoAudit == null) ? 0 : tipoOperacaoAudit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArquivoAuditPK other = (ArquivoAuditPK) obj;
		if (dataHora == null) {
			if (other.dataHora != null)
				return false;
		} else if (!dataHora.equals(other.dataHora))
			return false;
		if (idEntidade == null) {
			if (other.idEntidade != null)
				return false;
		} else if (!idEntidade.equals(other.idEntidade))
			return false;
		if (tipoOperacaoAudit != other.tipoOperacaoAudit)
			return false;
		return true;
	}
}