package br.com.ged.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public enum TipoDocumentoRH implements Serializable {

	RG("RG / Identidade"), 
	CPF("CPF"), 
	COMPROVANTE_ENDERECO("Comprovante de endereço"), 
	TITULO_ELEITOR("Titulo de Eleitor"), 
	CERTIDAO_NASCIMENTO("Certidão de Nascimento"), 
	CERTIDAO_CASAMENTO("Certidão de Casamento"), 
	RESERVISTA("Reservista"),
	CARTEIRA_TRABALHO("CTPS - Carteira de Trabalho"),

	;

	private String label;

	private TipoDocumentoRH(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static List<SelectItem> selectItems() {

		List<SelectItem> list = new ArrayList<>();

		for (TipoDocumentoRH tp : values()) {

			SelectItem si = new SelectItem();

			si.setLabel(tp.label);
			si.setValue(tp);

			list.add(si);

		}

		return list;
	}
}