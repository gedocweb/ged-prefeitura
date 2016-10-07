package br.com.ged.dto.audit;

import br.com.ged.anotations.EntityProperty;
import br.com.ged.generics.DataFiltroBetween;

public class FiltroDocumentoAuditDTO extends AbstractFiltroAuditDTO {

	@EntityProperty("dataIndexacao")
	private DataFiltroBetween dataBetween;
	
	private Boolean desteMes;
	
	private Long idUsuario;
	
	public FiltroDocumentoAuditDTO(){
		
		this.dataBetween = new DataFiltroBetween();
	}

	public DataFiltroBetween getDataBetween() {
		return dataBetween;
	}

	public void setDataBetween(DataFiltroBetween dataBetween) {
		this.dataBetween = dataBetween;
	}

	public Boolean getDesteMes() {
		return desteMes;
	}

	public void setDesteMes(Boolean desteMes) {
		this.desteMes = desteMes;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
}