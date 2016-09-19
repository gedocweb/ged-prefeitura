package br.com.ged.admin.documento;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.Mensagem;
import br.com.ged.entidades.Balancete;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractValidacao;
import br.com.ged.framework.InterceptionViewMenssage;

@Stateless
@Interceptors(InterceptionViewMenssage.class)
public class BalanceteValidadorView extends AbstractValidacao{
	
	public void valida(Balancete documento) throws NegocioException {

		if (documento.getAno() == null || documento.getAno() == null){
			throw new NegocioException(Mensagem.BALANC1);
		}
		
		if (documento.getMes() == null || documento.getMes() == null){
			throw new NegocioException(Mensagem.BALANC2);
		}

		if (StringUtils.isBlank(documento.getVolume())){
			throw new NegocioException(Mensagem.BALANC3);
		}

		if (documento.getDataIndexacao() == null){
			throw new NegocioException(Mensagem.BALANC4);
		}

		if (documento.getArquivo() == null){
			throw new NegocioException(Mensagem.BALANC5);
		}
	}
}