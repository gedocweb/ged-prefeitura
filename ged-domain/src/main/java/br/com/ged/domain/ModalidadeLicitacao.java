package br.com.ged.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public enum ModalidadeLicitacao implements Serializable {

	CARTA_CONVITE("Carta convite"), 
	PREGAO("Pregão"), 
	CONCORRENCIA_PUBLICA("Concorrência pública"), 
	TOMADA_PRECO("Tomada de preço"), 
	CONCURSO_PUBLICO("Concurso público"), 

	;

	private String label;

	private ModalidadeLicitacao(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static List<SelectItem> selectItems() {

		List<SelectItem> list = new ArrayList<>();

		for (ModalidadeLicitacao tp : values()) {

			SelectItem si = new SelectItem();

			si.setLabel(tp.label);
			si.setValue(tp);

			list.add(si);

		}

		return list;
	}
}