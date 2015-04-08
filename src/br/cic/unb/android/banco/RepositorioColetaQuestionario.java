package br.cic.unb.android.banco;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import br.cic.unb.android.Constantes;
import br.cic.unb.android.dominio.ColetaQuestionario;
import br.cic.unb.android.dominio.ColetaQuestionario.ColetaQuestionarios;
import br.cic.unb.android.enums.Tabela;

public class RepositorioColetaQuestionario {

	public RepositorioColetaQuestionario(Context context) {
		// TODO Auto-generated constructor stub
	}

	public long salvar(ColetaQuestionario coleta) {
		ContentValues values = new ContentValues();
		
		values.put(ColetaQuestionarios.ID_QUESTIONARIO, coleta.getIdQuestionario());
		values.put(ColetaQuestionarios.ENTREVISTADO, coleta.getEntrevistado());
		values.put(ColetaQuestionarios.DATA, coleta.getData().toString());

		long id = RepositorioQuestionario.database.insert(Tabela.COLETA_QUESTIONARIO.getNome(), "", values);
		coleta.setId(id);
		Log.d(Constantes.DEBUG_AND_COLLECTOR, "Salvou COLETA_QUESTIONARIO: " + coleta);
		
		return id;
	}

}
