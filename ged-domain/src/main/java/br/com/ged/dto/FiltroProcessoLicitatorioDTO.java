package br.com.ged.dto;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.ged.anotations.EntityProperty;
import br.com.ged.domain.ModalidadeLicitacao;
import br.com.ged.generics.DataFiltroBetween;

public class FiltroProcessoLicitatorioDTO {
	
	@EntityProperty(value="dataIndexacao")
	private DataFiltroBetween dataIndexacaoBetween;

	@EntityProperty(value="ano")
	private Integer ano;
	
	@EntityProperty(value="modalidadeLicit")
	private ModalidadeLicitacao modalidadeLicitacao;
	
	@EntityProperty(value="numeroProcesso")
	private String numeroProcesso;
	
	@EntityProperty(value="objeto")
	private String objeto;
	
	@EntityProperty(value="observacao")
	private String observacao;
	
	private List<SelectItem> listSelectItemModalidadeLicit;
	
	public FiltroProcessoLicitatorioDTO(){
		listSelectItemModalidadeLicit = ModalidadeLicitacao.selectItems();
		dataIndexacaoBetween = new DataFiltroBetween();
	}

	public DataFiltroBetween getDataIndexacaoBetween() {
		return dataIndexacaoBetween;
	}

	public void setDataIndexacaoBetween(DataFiltroBetween dataIndexacaoBetween) {
		this.dataIndexacaoBetween = dataIndexacaoBetween;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public ModalidadeLicitacao getModalidadeLicitacao() {
		return modalidadeLicitacao;
	}

	public void setModalidadeLicitacao(ModalidadeLicitacao modalidadeLicitacao) {
		this.modalidadeLicitacao = modalidadeLicitacao;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<SelectItem> getListSelectItemModalidadeLicit() {
		return listSelectItemModalidadeLicit;
	}
}