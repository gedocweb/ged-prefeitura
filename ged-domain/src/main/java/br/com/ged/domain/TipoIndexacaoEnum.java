package br.com.ged.domain;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public enum TipoIndexacaoEnum {

	BALANCETE("Balancete"),
	RH("RH - Recurso Humano"),
	PROC_LICITA("Processo Licitatório"),
	LEI("Lei"),
	OUTROS("Outros");
	
	private String label;
	
	private TipoIndexacaoEnum(String label){
		this.label = label;
	}
	
	public static List<SelectItem> selectItems(){
		
		List<SelectItem> list = new ArrayList<SelectItem>();
		
		for (TipoIndexacaoEnum tpIndexEnum : values()){
			
			SelectItem si = new SelectItem();
			
			si.setLabel(tpIndexEnum.getLabel());
			si.setValue(tpIndexEnum);
			
			list.add(si);
		}
		
		return list;
	}

	public String getLabel() {
		return label;
	}
}