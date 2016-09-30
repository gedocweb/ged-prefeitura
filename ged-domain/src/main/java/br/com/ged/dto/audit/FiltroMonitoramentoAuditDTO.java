package br.com.ged.dto.audit;

import br.com.ged.generics.DataFiltroBetween;

public class FiltroMonitoramentoAuditDTO extends AbstractFiltroAuditDTO {

	private Long idUsuario;
	
	private DataFiltroBetween dataFiltroBetween;
	
	public FiltroMonitoramentoAuditDTO(){
		this.dataFiltroBetween = new DataFiltroBetween();
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public DataFiltroBetween getDataFiltroBetween() {
		return dataFiltroBetween;
	}

	public void setDataFiltroBetween(DataFiltroBetween dataFiltroBetween) {
		this.dataFiltroBetween = dataFiltroBetween;
	}
}