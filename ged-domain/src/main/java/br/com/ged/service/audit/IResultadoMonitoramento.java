package br.com.ged.service.audit;

import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;
import br.com.ged.dto.audit.RetornoMonitoramentoUsuarioDTO;

public interface IResultadoMonitoramento {

	RetornoMonitoramentoUsuarioDTO monitoramento(FiltroMonitoramentoAuditDTO filtroMonitoramento);
}
