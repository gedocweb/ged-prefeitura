package br.com.ged.admin.controller.documento.processoLicitatorio;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.Mensagem;
import br.com.ged.entidades.ProcessoLicitatorio;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractValidacao;
import br.com.ged.framework.InterceptionViewMenssage;

@Stateless
@Interceptors(InterceptionViewMenssage.class)
public class ProcessoLicitatorioValidadorView extends AbstractValidacao{
	
	public void valida(ProcessoLicitatorio processoLicitatorio) throws NegocioException {

		if (processoLicitatorio.getAno() == null || processoLicitatorio.getAno() == null){
			throw new NegocioException(Mensagem.PROCESSOLICIT1);
		}
		
		if (processoLicitatorio.getModalidadeLicit() == null){
			throw new NegocioException(Mensagem.PROCESSOLICIT2);
		}

		if (StringUtils.isBlank(processoLicitatorio.getNumeroProcesso())){
			throw new NegocioException(Mensagem.PROCESSOLICIT3);
		}
		
		if (StringUtils.isBlank(processoLicitatorio.getObjeto())){
			throw new NegocioException(Mensagem.PROCESSOLICIT4);
		}

		if (processoLicitatorio.getDataIndexacao() == null){
			throw new NegocioException(Mensagem.PROCESSOLICIT5);
		}

		if (processoLicitatorio.getArquivo() == null){
			throw new NegocioException(Mensagem.PROCESSOLICIT6);
		}
	}
}