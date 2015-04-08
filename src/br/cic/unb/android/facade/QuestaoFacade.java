package br.cic.unb.android.facade;

import java.util.List;

import android.content.Context;
import android.util.Log;
import br.cic.unb.android.Constantes;
import br.cic.unb.android.banco.RepositorioQuestao;
import br.cic.unb.android.banco.RepositorioResposta;
import br.cic.unb.android.dominio.Questao;
import br.cic.unb.android.dominio.Resposta;
import br.cic.unb.android.enums.TipoQuestao;

public class QuestaoFacade {

	private RepositorioQuestao repositorio;
	private RepositorioResposta repositorioResposta;
	
	public QuestaoFacade(Context context) {
		repositorio = new RepositorioQuestao(context);
		repositorioResposta = new RepositorioResposta(context);
	}

	public long salvar(Questao questao){
		
		List<Resposta> respostas = repositorio.listarRespostas(questao.getId());
		
		if(respostas != null && respostas.size() > 0){
			if(questao.getTipo() == TipoQuestao.ABERTA.getCodigo()){
				for (Resposta resposta : respostas) {
					repositorioResposta.remover(resposta);
				}
			}
		}
		
		return repositorio.salvar(questao);
	}

	public List<Questao> listarQuestoes(long idQuestionario) {
		return repositorio.listarQuestoes(idQuestionario);
	}

	public int recuperarQtdRespostas(Long id) {
		return repositorio.recuperarQtdRespostas(id);
	}

	public Questao buscarQuestao(long id) {
		Questao questao = repositorio.buscarQuestao(id);
		Log.d(Constantes.DEBUG_AND_COLLECTOR, "Recuperou " + questao);
		
		return questao;
	}

	public void remover(long idQuestao) {
		repositorio.remover(idQuestao);
	}
}
