package br.cic.unb.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import br.cic.unb.android.R;
import br.cic.unb.android.dominio.Questionario;
import br.cic.unb.android.dominio.Questionario.Questionarios;
import br.cic.unb.android.enums.Acao;
import br.cic.unb.android.facade.QuestionarioFacade;

/**
 * Activity que utiliza o TableLayout para editar o questionario
 * 
 * @author paulomota
 * 
 */
public class EditarQuestionarioActivity extends Activity {

	private QuestionarioFacade questionarioFacade;
	
	// Campos texto
	private EditText campoNome;
	private EditText campoDescricao;
	private EditText campoQtdQuestoes;
	private CheckBox checkAnonimo;
	private Long id;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		questionarioFacade = new QuestionarioFacade(this);
		
		setContentView(R.layout.form_editar_questionario);

		campoNome = (EditText) findViewById(R.id.campoNome);
		campoDescricao = (EditText) findViewById(R.id.campoDescricao);
		campoQtdQuestoes = (EditText) findViewById(R.id.campoQtdQuestoes);
		checkAnonimo = (CheckBox) findViewById(R.id.checkAnonimo);
		
		id = null;
		
		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			id = extras.getLong(Questionarios._ID);

			if (id != null) {
				// eh uma edicao, busca o questionario...
				Questionario questionario = questionarioFacade.buscarQuestionario(id);
				
				int qtdQuestoes = CadastroQuestionarioActivity.questionarioFacade.recuperarQtdQuestoes(id);
				
				campoNome.setText(questionario.getNome());
				campoDescricao.setText(questionario.getDescricao());
				campoQtdQuestoes.setText(String.valueOf(qtdQuestoes));
				checkAnonimo.setChecked(questionario.isAnonimo());
			}
		}

		Button btCancelar = (Button) findViewById(R.id.btCancelar);
		btCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

		// Listener para salvar o questionario
		Button btSalvar = (Button) findViewById(R.id.btSalvar);
		btSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				salvar();
			}
		});
		
		
		Button btExcluir = (Button) findViewById(R.id.btExcluir);

		if (id == null) {
			// Se id esta nulo, nao pode excluir
			btExcluir.setVisibility(View.INVISIBLE);
		} else {
			// Listener para excluir o questionario
			btExcluir.setOnClickListener(new OnClickListener(){
				public void onClick(View view) {
					excluir();
				}
			});
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		setResult(RESULT_CANCELED);
		finish();
	}

	public void salvar() {
		Questionario questionario = new Questionario();
		
		if (id != null) {
			questionario.setId(id);
		}
		
		questionario.setNome(campoNome.getText().toString());
		questionario.setDescricao(campoDescricao.getText().toString());
		
		if(checkAnonimo.isChecked()){
			questionario.setAnonimo(true);
		}else{
			questionario.setAnonimo(false);
		}
		
		questionarioFacade.salvar(questionario);

		setResult(RESULT_OK, new Intent());

		finish();
	}

	public void excluir() {
		if (id != null) {
			questionarioFacade.deletar(id);
		}

		setResult(RESULT_OK, new Intent());
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, Acao.SALVAR.getCodigo(), 0, "Adicionar Questão");
		menu.add(0, Acao.PROCURAR.getCodigo(), 0, "Visualizar Questões");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// Clicou no menu
		switch (item.getItemId()) {
			case 1:
				// Abre a tela com o formulario para adicionar
				Intent it = new Intent(this, CadastroQuestaoActivity.class);
				it.putExtra(Questionarios._ID, id);
				
				startActivityForResult(it, Acao.SALVAR.getCodigo());
				break;
			case 2:
				Intent it2 = new Intent(this, ListarQuestoesActivity.class);
				it2.putExtra(Questionarios._ID, id);
				
				startActivity(it2);
		}
		return true;
	}
}
