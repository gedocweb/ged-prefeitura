package br.com.ged.admin.documento;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;

import br.com.ged.domain.Mensagem;
import br.com.ged.entidades.RecursoHumano;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.AbstractValidacao;
import br.com.ged.framework.InterceptionViewMenssage;

@Stateless
@Interceptors(InterceptionViewMenssage.class)
public class RecursoHumanoValidadorView extends AbstractValidacao{
	
	public void valida(RecursoHumano rh) throws NegocioException {

		if (StringUtils.isBlank(rh.getPessoa().getNome())){
			throw new NegocioException(Mensagem.RH1);
		}
		
		if (StringUtils.isBlank(rh.getPessoa().getCpf())){
			throw new NegocioException(Mensagem.RH2);
		}

		if (inteiroNaoInformado(rh.getPessoa().getRg())){
			throw new NegocioException(Mensagem.RH3);
		}
		
		if (rh.getTipoDocumento() == null){
			throw new NegocioException(Mensagem.RH4);
		}

		if (rh.getDataIndexacao() == null){
			throw new NegocioException(Mensagem.RH5);
		}

		if (rh.getArquivo() == null){
			throw new NegocioException(Mensagem.RH6);
		}
	}
}