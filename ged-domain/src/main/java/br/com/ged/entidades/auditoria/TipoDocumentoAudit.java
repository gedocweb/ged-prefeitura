package br.com.ged.entidades.auditoria;
 
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.ged.domain.entidade.Situacao;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.entidades.TipoDocumento;
import br.com.ged.generics.EntidadeBasicaAudit;
 
@Entity
@Table(name = "tb_tp_doc_audit")
public class TipoDocumentoAudit extends EntidadeBasicaAudit implements Comparable<TipoDocumentoAudit>{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@EmbeddedId
	private TipoDocumentoAuditPK id;
	
	@Column(name="descricao")
	private String descricao;

	@Column(name="situacao")
	private Situacao situacao;
	
	public TipoDocumentoAudit(){
		situacao = Situacao.ATIVO;
	}

	public TipoDocumentoAudit(TipoDocumento tipoDocumento, TipoOperacaoAudit tpAudit, Long dateMili) {

		TipoDocumentoAuditPK pk = new TipoDocumentoAuditPK();
		
		pk.setDataHora(dateMili);
		pk.setIdEntidade(tipoDocumento.getId());
		pk.setTipoOperacaoAudit(tpAudit);
		
		this.setDescricao(tipoDocumento.getDescricao());
		this.setSituacao(tipoDocumento.getSituacao());
		this.setId(pk);
	}

	public TipoDocumentoAuditPK getId() {
		return id;
	}

	public void setId(TipoDocumentoAuditPK id) {
		this.id = id;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int compareTo(TipoDocumentoAudit o) {
		return this.getDescricao().toLowerCase().compareTo(o.getDescricao().toLowerCase());
	}
}