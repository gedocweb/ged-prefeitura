package br.com.ged.dto.audit;

import br.com.ged.domain.entidade.DepartamentoEnum;

public class RetornoMonitoramentoUsuarioDTO {

	private DepartamentoEnum deparamento;
	
	private String nomeUsuarioPessoa;
	
	private Long qntInseridos;
	
	private Long qntAlterados;
	
	private Long qntExcluidos;
	
	private Long qntBaixados;
	
	private Long qntVisualizados;
	
	private Long qntExportados;

	public DepartamentoEnum getDeparamento() {
		return deparamento;
	}

	public void setDeparamento(DepartamentoEnum deparamento) {
		this.deparamento = deparamento;
	}

	public String getNomeUsuarioPessoa() {
		return nomeUsuarioPessoa;
	}

	public void setNomeUsuarioPessoa(String nomeUsuarioPessoa) {
		this.nomeUsuarioPessoa = nomeUsuarioPessoa;
	}

	public Long getQntInseridos() {
		return qntInseridos;
	}

	public void setQntInseridos(Long qntInseridos) {
		this.qntInseridos = qntInseridos;
	}

	public Long getQntAlterados() {
		return qntAlterados;
	}

	public void setQntAlterados(Long qntAlterados) {
		this.qntAlterados = qntAlterados;
	}

	public Long getQntExcluidos() {
		return qntExcluidos;
	}

	public void setQntExcluidos(Long qntExcluidos) {
		this.qntExcluidos = qntExcluidos;
	}

	public Long getQntBaixados() {
		return qntBaixados;
	}

	public void setQntBaixados(Long qntBaixados) {
		this.qntBaixados = qntBaixados;
	}

	public Long getQntVisualizados() {
		return qntVisualizados;
	}

	public void setQntVisualizados(Long qntVisualizados) {
		this.qntVisualizados = qntVisualizados;
	}

	public Long getQntExportados() {
		return qntExportados;
	}

	public void setQntExportados(Long qntExportados) {
		this.qntExportados = qntExportados;
	}
}