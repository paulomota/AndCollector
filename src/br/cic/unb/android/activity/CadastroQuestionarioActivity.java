package br.cic.unb.android.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import br.cic.unb.android.adapter.QuestionarioListAdapter;
import br.cic.unb.android.dominio.Questionario;
import br.cic.unb.android.dominio.Questionario.Questionarios;
import br.cic.unb.android.enums.Acao;
import br.cic.unb.android.facade.QuestionarioFacade;

/**
 * Activity que demonstra o cadastro de questionarios:
 * 
 * - ListActivity: para listar os questionarios - RepositorioQuestionario para salvar os dados
 * no banco - Questionarios
 * 
 * @author paulomota
 * 
 */
public class CadastroQuestionarioActivity extends ListActivity {
	public static QuestionarioFacade questionarioFacade;
	private List<Questionario> listaQuestionarios;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		questionarioFacade = new QuestionarioFacade(this);
		atualizarLista();
	}

	protected void atualizarLista() {
		// Pega a lista de questionario e exibe na tela
		listaQuestionarios = questionarioFacade.listarQuestionarios();

		// Adaptador de lista customizado para cada linha de um questionario
		setListAdapter(new QuestionarioListAdapter(this, listaQuestionarios));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, Acao.SALVAR.getCodigo(), 0, "Inserir Novo");
		menu.add(0, Acao.PROCURAR.getCodigo(), 0, "Buscar");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case 1:
				startActivityForResult(new Intent(this, EditarQuestionarioActivity.class), Acao.SALVAR.getCodigo());
				break;
			case 2:
				startActivity(new Intent(this, BuscarQuestionarioActivity.class));
				break;
		}
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int posicao, long id) {
		super.onListItemClick(l, v, posicao, id);
		editarQuestionario(posicao);
	}


	protected void editarQuestionario(int posicao) {
		// Usuario clicou em algum questionario da lista
		Questionario quest = listaQuestionarios.get(posicao);
		// Cria a intent para abrir a tela de editar
		Intent it = new Intent(this, EditarQuestionarioActivity.class);
		// Passa o id do questionario como parametro
		it.putExtra(Questionarios._ID, quest.getId());
		// Abre a tela de edicao
		startActivityForResult(it, Acao.SALVAR.getCodigo());
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