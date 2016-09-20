package br.com.ged.admin.documento;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.Mensagem;
import br.com.ged.entidades.Lei;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractValidacao;
import br.com.ged.framework.InterceptionViewMenssage;

@Stateless
@Interceptors(InterceptionViewMenssage.class)
public class LeiValidadorView extends AbstractValidacao{
	
	public void valida(Lei processoLicitatorio) throws NegocioException {

		if (processoLicitatorio.getAno() == null || processoLicitatorio.getAno() == null){
			throw new NegocioException(Mensagem.LEI1);
		}

		if (StringUtils.isBlank(processoLicitatorio.getNumeroLei())){
			throw new NegocioException(Mensagem.LEI2);
		}
		
		if (StringUtils.isBlank(processoLicitatorio.getObjeto())){
			throw new NegocioException(Mensagem.LEI3);
		}

		if (processoLicitatorio.getDataIndexacao() == null){
			throw new NegocioException(Mensagem.LEI4);
		}

		if (processoLicitatorio.getArquivo() == null){
			throw new NegocioException(Mensagem.LEI5);
		}
	}
}