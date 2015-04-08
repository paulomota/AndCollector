package br.cic.unb.android.facade;

import android.content.Context;
import br.cic.unb.android.banco.RepositorioColetaQuestionario;
import br.cic.unb.android.dominio.ColetaQuestionario;

public class ColetaQuestionarioFacade {

	private RepositorioColetaQuestionario repositorioColetaQuestionario;

	public ColetaQuestionarioFacade(Context context) {
		repositorioColetaQuestionario = new RepositorioColetaQuestionario(context);
	}

	public long salvar(ColetaQuestionario coleta) {
		return repositorioColetaQuestionario.salvar(coleta);
	}

}
