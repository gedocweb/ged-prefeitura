package br.com.ged.dto;

import java.io.Serializable;
import java.util.List;

import br.com.ged.anotations.EntityProperty;
import br.com.ged.domain.entidade.Situacao;
import br.com.ged.generics.DataFiltroBetween;

public class FiltroDocumentoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1630873286456635122L;
	
	
	@EntityProperty(value="dataInclusao")
	private DataFiltroBetween dataInclusaoDocumento;
	
	@EntityProperty(value="dataDocumento")
	private DataFiltroBetween dataDocumento;
	
	@EntityProperty(value="dataUltimaAlteracao")
	private DataFiltroBetween dataUltimaALteracao;
	
	@EntityProperty(value="tipoDocumento.id")
	private Long idTipoDocumento;
	
	@EntityProperty(value="descricao")
	private String descricao;
	
	@EntityProperty(value="observacao")
	private String observacao;
	
	@EntityProperty(value="categoria.id", listIds=true)
	private List<Long> subCategorias;
	
	@EntityProperty(value="categoria.id")
	private Long idCategoria;
	
	@EntityProperty(value="situacao")
	private Situacao situacao;
	
	public FiltroDocumentoDTO(){
		dataInclusaoDocumento = new DataFiltroBetween();
		dataDocumento = new DataFiltroBetween();
		dataUltimaALteracao = new DataFiltroBetween();
	}

	public DataFiltroBetween getDataInclusaoDocumento() {
		return dataInclusaoDocumento;
	}

	public void setDataInclusaoDocumento(DataFiltroBetween dataInclusaoDocumento) {
		this.dataInclusaoDocumento = dataInclusaoDocumento;
	}

	public DataFiltroBetween getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(DataFiltroBetween dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public DataFiltroBetween getDataUltimaALteracao() {
		return dataUltimaALteracao;
	}

	public void setDataUltimaALteracao(DataFiltroBetween dataUltimaALteracao) {
		this.dataUltimaALteracao = dataUltimaALteracao;
	}

	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<Long> getSubCategorias() {
		return subCategorias;
	}

	public void setSubCategorias(List<Long> subCategorias) {
		this.subCategorias = subCategorias;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
}