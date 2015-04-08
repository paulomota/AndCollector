package br.cic.unb.android.activity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import br.cic.unb.android.adapter.QuestionarioListAdapter;
import br.cic.unb.android.dominio.ColetaQuestionario;
import br.cic.unb.android.dominio.Questionario;
import br.cic.unb.android.dominio.Questionario.Questionarios;
import br.cic.unb.android.enums.Acao;
import br.cic.unb.android.facade.ColetaQuestionarioFacade;
import br.cic.unb.android.facade.QuestionarioFacade;

public class ListarQuestionariosParaColetaActivity extends ListActivity {

	public static QuestionarioFacade questionarioFacade;
	public static ColetaQuestionarioFacade coletaQuestionarioFacade;
	private List<Questionario> listaQuestionarios;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		questionarioFacade = new QuestionarioFacade(this);
		coletaQuestionarioFacade = new ColetaQuestionarioFacade(this);
		atualizarLista();
	}

	protected void atualizarLista() {
		// Pega a lista de questionario e exibe na tela
		listaQuestionarios = questionarioFacade.listarQuestionarios();

		// Adaptador de lista customizado para cada linha de um questionario
		setListAdapter(new QuestionarioListAdapter(this, listaQuestionarios));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int posicao, long id) {
		super.onListItemClick(l, v, posicao, id);
		coletarDados(posicao);
	}


	protected void coletarDados(int posicao) {
		// Usuario clicou em algum questionario da lista
		Questionario quest = listaQuestionarios.get(posicao);
		
		ColetaQuestionario coleta = new ColetaQuestionario();
		coleta.setIdQuestionario(quest.getId());
		
		long agora = new Date().getTime();
		coleta.setData(new Timestamp(agora));

		if(quest.isAnonimo()){
			coleta.setEntrevistado(null);
			long idColetaQuestionario = coletaQuestionarioFacade.salvar(coleta);
			
			Intent it = new Intent(this, ColetaDeDadosActivity.class);
			it.putExtra(Questionarios._ID, quest.getId());
			it.putExtra("idColetaQuestionario", idColetaQuestionario);
			
			startActivityForResult(it, Acao.SALVAR.getCodigo());

		}else{
			Intent it = new Intent(this, InformarEntrevistadoActivity.class);
			it.putExtra(Questionarios._ID, quest.getId());
			startActivity(it);
		}
		
	}

	@Override
	protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
		super.onActivityResult(codigo, codigoRetorno, it);

		// Quando a activity EditarQuestionario retornar, seja se foi para adicionar vamos atualizar a lista
		if (codigoRetorno == RESULT_OK) {
			atualizarLista();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		questionarioFacade.fechar();
	}
}
