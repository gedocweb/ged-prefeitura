package br.com.ged.domain.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public enum MesEnum implements Serializable{

	JANEIRO(1,"Janeiro"),
	FEVEREIRO(2,"Fevereiro"),
	MARCO(3,"Março"),
	ABRIL(4, "Abril"),
	MAIO(5, "Maio"),
	JUNHO(6, "Junho"),
	JULHO(7, "Julho"),
	AGOSTO(8, "Agosto"),
	SETEMBRO(9, "Setembro"),
	OUTUBRO(10, "Outubro"),
	NOVEMBRO(11, "Novembro"),
	DEZEMBRO(12, "Dezembro")
	
	;
	
	private int numeroMes;
	private String label;
	
	private MesEnum(int numeroMes, String label){
		this.numeroMes = numeroMes;
		this.label = label;
	}
	
	public static List<SelectItem> selectItems(){
		
		List<SelectItem> list = new ArrayList<>();
		
		for (MesEnum mes : values()){
			
			SelectItem si = new SelectItem();
			
			si.setLabel(mes.label);
			si.setValue(mes);
			
			list.add(si);
		}
		
		return list;
	}

	public int getNumeroMes() {
		return numeroMes;
	}

	public String getLabel() {
		return label;
	}
}