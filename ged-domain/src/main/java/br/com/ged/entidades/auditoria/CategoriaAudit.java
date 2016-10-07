package br.com.ged.entidades.auditoria;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.ged.domain.entidade.Situacao;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.entidades.Categoria;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.generics.EntidadeBasicaAudit;
 
@Entity
@Table(name = "tb_cat_audit")
public class CategoriaAudit extends EntidadeBasicaAudit implements Serializable, Comparable<CategoriaAudit>{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;
	
	@EmbeddedId
	private CategoriaAuditPK id;
	
	@Column(name="descricao")
	private String descricao;

	@Column(name="situacao")
	private Situacao situacao;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumns({
  	  @JoinColumn(name = "tipo_op_cat_pai", referencedColumnName="tp_operacao"),
  	  @JoinColumn(name = "id_cat_pai", referencedColumnName="id_categoria"),
  	  @JoinColumn(name = "id_dt_hora_cat_pai", referencedColumnName="data_hora")
	})
	private CategoriaAudit categoriaPai;
	
	@OneToMany
	@JoinTable(
		name = "rl_cat_grup_usr_audit", 
		joinColumns ={
					  	  @JoinColumn(name = "tipo_op_cat", referencedColumnName="tp_operacao"),
					  	  @JoinColumn(name = "id_cat_audit", referencedColumnName="id_categoria"),
					  	  @JoinColumn(name = "id_dt_hora_cat", referencedColumnName="data_hora")
					}, 
		inverseJoinColumns = {
			  	  @JoinColumn(name = "tipo_op_grp_usr", referencedColumnName="tp_operacao"),
			  	  @JoinColumn(name = "id_cat_grp_usr_audit", referencedColumnName="id_grupo_usuario"),
			  	  @JoinColumn(name = "id_dt_hora_grp_usr", referencedColumnName="data_hora")
			}
	)
    private List<GrupoUsuarioAudit> listGrupoUsuario;
	
	@OneToMany(mappedBy="categoriaPai", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	private List<CategoriaAudit> categoriaFilha;
	
	public CategoriaAudit(){
		situacao = Situacao.ATIVO;
	}

	public CategoriaAudit(Categoria categoria, TipoOperacaoAudit tpAudit, Long dateMili) {

		this.setId(novaCategoriaPK(categoria, tpAudit, dateMili));
		
		if (categoria.getCategoriaPai() != null)
			this.setCategoriaPai(new CategoriaAudit(categoria.getCategoriaPai(), tpAudit, dateMili));
		
		this.setDescricao(categoria.getDescricao());
		this.setListGrupoUsuario(converteListaGrupoUsuario(categoria.getListGrupoUsuario(), tpAudit, dateMili));
		this.setSituacao(categoria.getSituacao());
	}

	private List<GrupoUsuarioAudit> converteListaGrupoUsuario(List<GrupoUsuario> listGrupoUsuario, TipoOperacaoAudit tpAudit, Long dateMili) {
		
		List<GrupoUsuarioAudit> list = new ArrayList<>();
		
		for (GrupoUsuario grp : listGrupoUsuario){
			
			GrupoUsuarioAudit cateAudit = new GrupoUsuarioAudit(grp, tpAudit, dateMili);
			list.add(cateAudit);
		}
		
		return null;
	}

	private CategoriaAuditPK novaCategoriaPK(Categoria categoria, TipoOperacaoAudit tpAudit, Long dateMili) {
		
		CategoriaAuditPK pk = new CategoriaAuditPK();
		pk.setDataHora(dateMili);
		pk.setIdEntidade(categoria.getId());
		pk.setTipoOperacaoAudit(tpAudit);
		
		return pk;
	}

	public CategoriaAuditPK getId() {
		return id;
	}

	public void setId(CategoriaAuditPK id) {
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

	public CategoriaAudit getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(CategoriaAudit categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public List<CategoriaAudit> getCategoriaFilha() {
		return categoriaFilha;
	}

	public void setCategoriaFilha(List<CategoriaAudit> categoriaFilha) {
		this.categoriaFilha = categoriaFilha;
	}

	public List<GrupoUsuarioAudit> getListGrupoUsuario() {
		return listGrupoUsuario;
	}

	public void setListGrupoUsuario(List<GrupoUsuarioAudit> listGrupoUsuario) {
		this.listGrupoUsuario = listGrupoUsuario;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoriaFilha == null) ? 0 : categoriaFilha.hashCode());
		result = prime * result + ((categoriaPai == null) ? 0 : categoriaPai.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((listGrupoUsuario == null) ? 0 : listGrupoUsuario.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
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
		CategoriaAudit other = (CategoriaAudit) obj;
		if (categoriaFilha == null) {
			if (other.categoriaFilha != null)
				return false;
		} else if (!categoriaFilha.equals(other.categoriaFilha))
			return false;
		if (categoriaPai == null) {
			if (other.categoriaPai != null)
				return false;
		} else if (!categoriaPai.equals(other.categoriaPai))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listGrupoUsuario == null) {
			if (other.listGrupoUsuario != null)
				return false;
		} else if (!listGrupoUsuario.equals(other.listGrupoUsuario))
			return false;
		if (situacao != other.situacao)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

	@Override
	public int compareTo(CategoriaAudit o) {
		 return this.getDescricao().toLowerCase().compareTo(o.getDescricao().toLowerCase());
	}
}