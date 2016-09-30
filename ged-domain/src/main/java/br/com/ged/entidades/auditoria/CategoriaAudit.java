package br.com.ged.entidades.auditoria;
 
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.ged.domain.Situacao;
import br.com.ged.generics.EntidadeBasicaAudit;
 
@Entity
@Table(name = "tb_categoria_audit")
public class CategoriaAudit extends EntidadeBasicaAudit implements Serializable, Comparable<CategoriaAudit>{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@Id
	@Column(name = "id_categoria_audit")
	@GeneratedValue(generator = "seq_categoria_audit", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "seq_categoria_audit", sequenceName = "seq_categoria_audit",allocationSize=1)
	private Long id;
	
	@Column(name="descricao")
	private String descricao;

	@Column(name="situacao")
	private Situacao situacao;
	
	@ManyToOne
	@JoinColumn(name="id_categoria_pai")
	private CategoriaAudit categoriaPai;
	
	@OneToMany
	@JoinTable(
		name = "rl_cat_grup_usr_audit", 
		joinColumns = @JoinColumn(name = "id_categoria"), 
		inverseJoinColumns = @JoinColumn(name = "id_grupo_usuario")
	)
    private List<GrupoUsuarioAudit> listGrupoUsuario;
	
	@OneToMany(mappedBy="categoriaPai", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	private List<CategoriaAudit> categoriaFilha;
	
	public CategoriaAudit(){
		situacao = Situacao.ATIVO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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