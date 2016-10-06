package br.com.ged.dto;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.ged.anotations.EntityProperty;
import br.com.ged.domain.entidade.MesEnum;
import br.com.ged.domain.entidade.OrgaoEnum;
import br.com.ged.generics.DataFiltroBetween;

public class FiltroBalanceteDTO {
	
	@EntityProperty(value="dataIndexacao")
	private DataFiltroBetween dataIndexacaoBetween;

	@EntityProperty(value="dataIndexacao")
	private Date dataIndexacao;
	
	@EntityProperty(value="ano")
	private Integer ano;
	
	@EntityProperty(value="mes")
	private MesEnum mes;
	
	@EntityProperty(value="volume")
	private String volume;
	
	@EntityProperty(value="orgao")
	private OrgaoEnum orgao;
	
	@EntityProperty(value="observacao")
	private String observacao;
	
	private List<SelectItem> listSelectItemMes;
	private List<SelectItem> listSelectItemOrgao;
	
	public FiltroBalanceteDTO(){
		listSelectItemMes = MesEnum.selectItems();
		listSelectItemOrgao = OrgaoEnum.selectItems();
		
		dataIndexacaoBetween = new DataFiltroBetween();
	}

	public Date getDataIndexacao() {
		return dataIndexacao;
	}

	public void setDataIndexacao(Date dataIndexacao) {
		this.dataIndexacao = dataIndexacao;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public MesEnum getMes() {
		return mes;
	}

	public void setMes(MesEnum mes) {
		this.mes = mes;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public OrgaoEnum getOrgao() {
		return orgao;
	}

	public void setOrgao(OrgaoEnum orgao) {
		this.orgao = orgao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<SelectItem> getListSelectItemMes() {
		return listSelectItemMes;
	}

	public List<SelectItem> getListSelectItemOrgao() {
		return listSelectItemOrgao;
	}

	public DataFiltroBetween getDataIndexacaoBetween() {
		return dataIndexacaoBetween;
	}

	public void setDataIndexacaoBetween(DataFiltroBetween dataIndexacaoBetween) {
		this.dataIndexacaoBetween = dataIndexacaoBetween;
	}
}