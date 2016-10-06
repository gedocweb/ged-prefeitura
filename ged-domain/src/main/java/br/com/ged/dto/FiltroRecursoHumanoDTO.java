package br.com.ged.dto;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.ged.anotations.EntityProperty;
import br.com.ged.domain.entidade.TipoDocumentoRH;
import br.com.ged.generics.DataFiltroBetween;

public class FiltroRecursoHumanoDTO {
	
	@EntityProperty(value="dataIndexacao")
	private DataFiltroBetween dataIndexacaoBetween;

	@EntityProperty(value="tipoDocumento")
	private TipoDocumentoRH tipoDocumento;
	
	@EntityProperty(value="pessoa.nome")
	private String nome;
	
	@EntityProperty(value="pessoa.rg")
	private String rg;
	
	@EntityProperty(value="pessoa.cpf")
	private String cpf;
	
	@EntityProperty(value="observacao")
	private String observacao;
	
	private List<SelectItem> listTipoDocumentoRH;
	
	public FiltroRecursoHumanoDTO(){
		listTipoDocumentoRH = TipoDocumentoRH.selectItems();
		
		dataIndexacaoBetween = new DataFiltroBetween();
	}

	public DataFiltroBetween getDataIndexacaoBetween() {
		return dataIndexacaoBetween;
	}

	public void setDataIndexacaoBetween(DataFiltroBetween dataIndexacaoBetween) {
		this.dataIndexacaoBetween = dataIndexacaoBetween;
	}

	public TipoDocumentoRH getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoRH tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<SelectItem> getListTipoDocumentoRH() {
		return listTipoDocumentoRH;
	}
}