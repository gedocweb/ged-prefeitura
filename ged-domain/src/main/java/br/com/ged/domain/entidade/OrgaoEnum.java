package br.com.ged.domain.entidade;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public enum OrgaoEnum {

	FUNDEB("FUNDEB - Fundo de Manutenção e Desenvolvimento da Educação Básica"),
	FMS("FMS - Fundação Municipal de Saúde"),
	FMAS("FMAS - Fundo Municipal de Assistência Social"),
	EXECUTIVO("Executivo"),
	FUNPREV("FUNPREV - Fundo Financeiro da Previdência Social"),
	RPS("RPS - Recibo Provisório de Serviço") 
	;
	
	private String label;
	
	private OrgaoEnum(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

	public static List<SelectItem> selectItems() {
		
		List<SelectItem> list = new ArrayList<>();
		
		for (OrgaoEnum org : values()){
			
			SelectItem si = new SelectItem();
			
			si.setLabel(org.label);
			si.setValue(org);
			
			list.add(si);
		}
		
		return list;
	}
}