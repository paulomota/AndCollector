package br.cic.unb.android.facade;

import android.content.Context;
import br.cic.unb.android.banco.RepositorioColetaResposta;
import br.cic.unb.android.dominio.ColetaResposta;

public class ColetaRespostaFacade {

	RepositorioColetaResposta repositorioColetaResposta;
	
	public ColetaRespostaFacade(Context context) {
		repositorioColetaResposta = new RepositorioColetaResposta(context);
	}

	public long salvar(ColetaResposta coleta) {
		long id = repositorioColetaResposta.salvar(coleta);
		coleta.setId(id);
		
		return id;
	}

}
