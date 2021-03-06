package br.com.ged.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.dto.FiltroDocumentoDTO;
import br.com.ged.entidades.Documento;
import br.com.ged.generics.ConsultasDaoJpa;

@Stateless
public class DocumentoServiceImpl implements DocumentoService{
	
	@EJB
	private ConsultasDaoJpa<Documento> reposiroty;

	@Override
	public List<Documento> pesquisar(FiltroDocumentoDTO filtro, String... hbInitialize) {
		return ignoraValoresRepetidos(filtro, hbInitialize);
	}

	private List<Documento> ignoraValoresRepetidos(FiltroDocumentoDTO filtro, String... hbInitialize) {
		List<Documento> listDoc = reposiroty.filtrarPesquisa(filtro, Documento.class, hbInitialize);
		
		return new ArrayList<Documento>(new HashSet<Documento>(listDoc));
	}
}