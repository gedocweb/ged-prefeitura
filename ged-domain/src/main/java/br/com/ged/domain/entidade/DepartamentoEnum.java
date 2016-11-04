package br.com.ged.domain.entidade;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.ged.domain.TipoApp;
import br.com.ged.util.InitConfigProperties;

public enum DepartamentoEnum {

	BALANCETE("Balancete", TipoApp.PREFEITURA),
	RH("RH - Recurso Humano", TipoApp.TODOS),
	PROC_LICITA("Processo Licitatório", TipoApp.PREFEITURA),
	LEI("Lei", TipoApp.PREFEITURA),
	OUTROS("Outros", TipoApp.TODOS);
	
	private String label;
	private TipoApp tipoApp;
	
	private DepartamentoEnum(String label, TipoApp tipoApp){
		this.label = label;
		this.tipoApp = tipoApp;
	}
	
	public static List<SelectItem> selectItems(){
		
		List<SelectItem> list = new ArrayList<SelectItem>();
		
		String modoName = InitConfigProperties.getValue(TipoApp.CONFIG_PARAM_PROPERTIE);
		
		for (DepartamentoEnum tpIndexEnum : values()){
			
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

	private static void addTipoIndexacao(List<SelectItem> list, DepartamentoEnum tpIndexEnum) {
		
		SelectItem si = new SelectItem();
		
		si.setLabel(tpIndexEnum.getLabel());
		si.setValue(tpIndexEnum);
		
		list.add(si);
	}

	public String getLabel() {
		return label;
	}
	
	public boolean isBalancete(){
		return this.equals(BALANCETE);
	}
	public boolean isLei(){
		return this.equals(LEI);
	}
	public boolean getRh(){
		return this.equals(RH);
	}
	public boolean getProcessoLicitatorio(){
		return this.equals(PROC_LICITA);
	}
	public boolean isOutros(){
		return this.equals(OUTROS);
	}
}