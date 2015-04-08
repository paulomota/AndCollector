package br.cic.unb.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import br.cic.unb.android.Constantes;
import br.cic.unb.android.adapter.QuestaoListAdapter;
import br.cic.unb.android.dominio.Questao;
import br.cic.unb.android.dominio.Questao.Questoes;
import br.cic.unb.android.dominio.Questionario.Questionarios;
import br.cic.unb.android.enums.Acao;
import br.cic.unb.android.facade.QuestaoFacade;

public class ListarQuestoesActivity extends ListActivity {

	private QuestaoFacade questaoFacade;
	private List<Questao> listaQuestoes = new ArrayList<Questao>();
	
	private long idQuestionario;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		questaoFacade = new QuestaoFacade(this);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			idQuestionario = extras.getLong(Questionarios._ID);
			Log.i(Constantes.DEBUG_AND_COLLECTOR, "recuperou id de questionario = " + idQuestionario);
		}
		
		atualizarLista();
		
	}
	
	/**
	 * atualiza a lista de questoes de um questionario
	 */
	public void atualizarLista(){
		listaQuestoes = questaoFacade.listarQuestoes(idQuestionario);
		setListAdapter(new QuestaoListAdapter(this, listaQuestoes));
	}
	
	@Override
	protected void onListItemClick(ListView listView, View view, int posicao, long id) {
		super.onListItemClick(listView, view, posicao, id);
		editarQuestao(posicao);
	}


	/**
	 * Recupera a questao e chama a activity responsavel pela edicao
	 * @param posicao
	 */
	protected void editarQuestao(int posicao) {
		// Usuario clicou em alguma questao da lista
		Questao questao = listaQuestoes.get(posicao);
		
		// Cria a intent para abrir a tela de editar
		Intent it = new Intent(this, EditarQuestaoActivity.class);
		
		// Passa o id da questao como parametro
		it.putExtra(Questoes._ID, questao.getId());
		
		// Abre a tela de edicao
		startActivityForResult(it, Acao.SALVAR.getCodigo());
	}
	
	@Override
	protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
		super.onActivityResult(codigo, codigoRetorno, it);

		if (codigoRetorno == RESULT_OK) {
			atualizarLista();
		}
	}
}













