package br.com.ged.admin.relatorio;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import br.com.ged.domain.Mensagem;
import br.com.ged.domain.entidade.DepartamentoEnum;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractValidacao;
import br.com.ged.framework.InterceptionViewMenssage;

@Stateless
@Interceptors(InterceptionViewMenssage.class)
public class RelatorioMonitoramentoValidadorView extends AbstractValidacao{
	
	public void valida(Long idGrupoUsuario, Long idUsuario, DepartamentoEnum departamento) throws NegocioException {

		if (inteiroNaoInformado(idGrupoUsuario)){
			throw new NegocioException(Mensagem.REL2);
		}
		
		if (inteiroNaoInformado(idUsuario)){
			throw new NegocioException(Mensagem.REL3);
		}
		
		if (departamento == null){
			throw new NegocioException(Mensagem.REL4);
		}
	}
}