package br.com.ged.dto;

import br.com.ged.anotations.EntityProperty;
import br.com.ged.generics.DataFiltroBetween;

public class FiltroLeiDTO {
	
	@EntityProperty(value="dataIndexacao")
	private DataFiltroBetween dataIndexacaoBetween;

	@EntityProperty(value="ano")
	private Integer ano;
	
	@EntityProperty(value="numeroLei")
	private String numeroLei;
	
	@EntityProperty(value="objeto")
	private String objeto;
	
	@EntityProperty(value="observacao")
	private String observacao;
	
	public FiltroLeiDTO(){
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

	public String getNumeroLei() {
		return numeroLei;
	}

	public void setNumeroLei(String numeroLei) {
		this.numeroLei = numeroLei;
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
}