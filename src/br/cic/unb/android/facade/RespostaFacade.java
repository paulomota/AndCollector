package br.cic.unb.android.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.util.Log;
import br.cic.unb.android.Constantes;
import br.cic.unb.android.banco.RepositorioResposta;
import br.cic.unb.android.dominio.Questao;
import br.cic.unb.android.dominio.Resposta;

public class RespostaFacade {

	private RepositorioResposta repositorioResposta;
	
	public RespostaFacade(Context context) {
		repositorioResposta = new RepositorioResposta(context);
	}

	/**
	 * Salva uma resposta de uma questao
	 * @param resposta
	 */
	public void salvar(Resposta resposta) {
		repositorioResposta.salvar(resposta);
	}

	
	/**
	 * Salva uma lista de respostas de uma mesma questao
	 * @param listaRespostas
	 */
	public void salvar(List<Resposta> listaRespostas) {
		List<Resposta> listaRespostasFinal = new ArrayList<Resposta>();
		
		for(Resposta resposta : listaRespostas){
			if(resposta.getDescricao() != null && resposta.getDescricao().length() > 0){
				listaRespostasFinal.add(resposta);
				Log.i(Constantes.DEBUG_AND_COLLECTOR, resposta.getDescricao() +  " - " + resposta.getIdQuestao());
			}
		}
		
		Log.i(Constantes.DEBUG_AND_COLLECTOR, "salvou " + listaRespostasFinal.size() + " respostas.");
		repositorioResposta.salvar(listaRespostasFinal);
	}

	/**
	 * recupera as respostas cadastradas em uma questao, para exibir na tela de edicao
	 * @param questao
	 * @return
	 */
	public List<Resposta> recuperarRepostasEditar(Questao questao) {
		List<Resposta> respostas = repositorioResposta.recuperarRespostas(questao); 
		
		int qtd = respostas.size();
		
		//preenche as respostas nao cadastradas com descricao vazia para se exibido na tela
		for(int i = qtd; i <= 4; i++){
			Resposta resposta = new Resposta();
			resposta.setDescricao("");
			resposta.setIdQuestao(questao.getId());
			
			respostas.add(resposta);
		}
		
		return respostas;
	}
	
	/**
	 * recupera as respostas cadastradas em uma questao
	 * @param questao
	 * @return
	 */
	public List<Resposta> recuperarRepostas(Questao questao) {
		Log.d(Constantes.DEBUG_AND_COLLECTOR, "Recuperou respostas da questao " + questao);
		List<Resposta> respostas = repositorioResposta.recuperarRespostas(questao); 

		Collections.sort(respostas, new Comparator<Resposta>() {
			public int compare(Resposta object1, Resposta object2) {
				if(object1.getId() > object2.getId()) return 1;
				else if(object1.getId() < object2.getId()) return -1;
				return 0;
			}
		});
		
		return respostas;
	}

}
