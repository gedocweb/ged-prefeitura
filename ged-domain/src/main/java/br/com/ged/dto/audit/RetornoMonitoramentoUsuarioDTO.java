package br.com.ged.dto.audit;

import br.com.ged.domain.DepartamentoEnum;

public class RetornoMonitoramentoUsuarioDTO {

	private DepartamentoEnum deparamento;
	
	private String nomeUsuarioPessoa;
	
	private Integer qntInseridos;
	
	private Integer qntAlterados;
	
	private Integer qntExcluidos;
	
	private Integer qntBaixados;
	
	private Integer qntVisualizados;
	
	private Integer qntExportados;

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

	public Integer getQntInseridos() {
		return qntInseridos;
	}

	public void setQntInseridos(Integer qntInseridos) {
		this.qntInseridos = qntInseridos;
	}

	public Integer getQntAlterados() {
		return qntAlterados;
	}

	public void setQntAlterados(Integer qntAlterados) {
		this.qntAlterados = qntAlterados;
	}

	public Integer getQntExcluidos() {
		return qntExcluidos;
	}

	public void setQntExcluidos(Integer qntExcluidos) {
		this.qntExcluidos = qntExcluidos;
	}

	public Integer getQntBaixados() {
		return qntBaixados;
	}

	public void setQntBaixados(Integer qntBaixados) {
		this.qntBaixados = qntBaixados;
	}

	public Integer getQntVisualizados() {
		return qntVisualizados;
	}

	public void setQntVisualizados(Integer qntVisualizados) {
		this.qntVisualizados = qntVisualizados;
	}

	public Integer getQntExportados() {
		return qntExportados;
	}

	public void setQntExportados(Integer qntExportados) {
		this.qntExportados = qntExportados;
	}
}