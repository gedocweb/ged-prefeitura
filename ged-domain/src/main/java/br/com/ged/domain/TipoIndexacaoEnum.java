package br.com.ged.domain;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.ged.util.InitConfigProperties;

public enum TipoIndexacaoEnum {

	BALANCETE("Balancete", TipoApp.PREFEITURA),
	RH("RH - Recurso Humano", TipoApp.TODOS),
	PROC_LICITA("Processo Licitatório", TipoApp.PREFEITURA),
	LEI("Lei", TipoApp.PREFEITURA),
	OUTROS("Outros", TipoApp.TODOS);
	
	private String label;
	private TipoApp tipoApp;
	
	private TipoIndexacaoEnum(String label, TipoApp tipoApp){
		this.label = label;
		this.tipoApp = tipoApp;
	}
	
	public static List<SelectItem> selectItems(){
		
		List<SelectItem> list = new ArrayList<SelectItem>();
		
		String modoName = InitConfigProperties.getValue(TipoApp.CONFIG_PARAM_PROPERTIE);
		
		for (TipoIndexacaoEnum tpIndexEnum : values()){
			
			if (TipoApp.PREFEITURA.name().equals(modoName) && tpIndexEnum.tipoApp.equals(TipoApp.PREFEITURA)){
				
				addTipoIndexacao(list, tpIndexEnum);
			}
			
			if (TipoApp.PRIVADO.name().equals(modoName) && tpIndexEnum.tipoApp.equals(TipoApp.PRIVADO)){
				
				addTipoIndexacao(list, tpIndexEnum);
			}
			
			if (tpIndexEnum.tipoApp.equals(TipoApp.TODOS)){
				
				addTipoIndexacao(list, tpIndexEnum);
			}
			
		}
		
		return list;
	}

	private static void addTipoIndexacao(List<SelectItem> list, TipoIndexacaoEnum tpIndexEnum) {
		
		SelectItem si = new SelectItem();
		
		si.setLabel(tpIndexEnum.getLabel());
		si.setValue(tpIndexEnum);
		
		list.add(si);
	}

	public String getLabel() {
		return label;
	}
}