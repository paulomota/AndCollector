package br.cic.unb.android.banco;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import br.cic.unb.android.Constantes;
import br.cic.unb.android.dominio.ColetaResposta;
import br.cic.unb.android.dominio.ColetaResposta.ColetaRespostas;
import br.cic.unb.android.enums.Tabela;

public class RepositorioColetaResposta {

	public RepositorioColetaResposta(Context context) {
		// TODO Auto-generated constructor stub
	}

	public long salvar(ColetaResposta coleta) {
		ContentValues values = new ContentValues();
		
		values.put(ColetaRespostas.ID_COLETA_QUESTIONARIO, coleta.getIdColetaQuestionario());
		values.put(ColetaRespostas.ID_QUESTAO, coleta.getIdQuestao());
		values.put(ColetaRespostas.ID_RESPOSTA_DADA, coleta.getIdRespostaDada());
		values.put(ColetaRespostas.RESPOSTA_ABERTA, coleta.getRespostaAberta());

		long id = RepositorioQuestionario.database.insert(Tabela.COLETA_RESPOSTA.getNome(), "", values);
		coleta.setId(id);
		
		Log.d(Constantes.DEBUG_AND_COLLECTOR, "Salvou COLETA_RESPOSTA: " + coleta);
		
		return id;
	}

}
