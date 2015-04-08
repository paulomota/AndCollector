package br.cic.unb.android.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import br.cic.unb.android.Constantes;
import br.cic.unb.android.R;
import br.cic.unb.android.dominio.Questao;
import br.cic.unb.android.dominio.Resposta;
import br.cic.unb.android.dominio.Questao.Questoes;
import br.cic.unb.android.dominio.Questionario.Questionarios;
import br.cic.unb.android.enums.Acao;
import br.cic.unb.android.enums.TipoQuestao;
import br.cic.unb.android.facade.QuestaoFacade;
import br.cic.unb.android.facade.RespostaFacade;

public class EditarQuestaoActivity extends Activity {
	private QuestaoFacade questaoFacade;
	private RespostaFacade respostaFacade;

	private Questao questao = null;
	private List<Resposta> respostas = null;
	
	// Campos texto
	private EditText campoDescricao;
	private EditText campoResposta1;
	private EditText campoResposta2;
	private EditText campoResposta3;
	private EditText campoResposta4;
	
	private RadioGroup radioGroup;
	private RadioButton radioSimOuNao;
	private RadioButton radioMultiplaEscolha;
	private RadioButton radioAberta;
	
	private TableLayout formMultiplaEscolha;
	
	//parametros vindo da tela anterior
	private Long id;
	private int tipoQuestao;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		questaoFacade = new QuestaoFacade(this);
		respostaFacade = new RespostaFacade(this);
		
		setContentView(R.layout.form_editar_questao);

		recuperarValorDosCamposDaTela();
		
		id = null;

		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			id = extras.getLong(Questoes._ID);

			if (id != null) {
				//eh uma edicao, busca a questao e as respostas associadas
				this.questao = questaoFacade.buscarQuestao(id);
				Log.d(Constantes.DEBUG_AND_COLLECTOR, "recuperou " + questao);
				
				respostas = respostaFacade.recuperarRepostasEditar(questao);
				
				if(questao.getTipo() == TipoQuestao.SIM_OU_NAO.getCodigo()){
					radioSimOuNao.setChecked(true);
					formMultiplaEscolha.setVisibility(View.INVISIBLE);
					
				}else if(questao.getTipo() == TipoQuestao.MULTIPLA_ESCOLHA.getCodigo()){
					radioMultiplaEscolha.setChecked(true);
					formMultiplaEscolha.setVisibility(View.VISIBLE);
					
				}else if(questao.getTipo() == TipoQuestao.ABERTA.getCodigo()){
					radioAberta.setChecked(true);
					formMultiplaEscolha.setVisibility(View.INVISIBLE);
					
				}
				
				campoDescricao.setText(questao.getDescricao());
				campoResposta1.setText(respostas.get(0).getDescricao());
				campoResposta2.setText(respostas.get(1).getDescricao());
				campoResposta3.setText(respostas.get(2).getDescricao());
				campoResposta4.setText(respostas.get(3).getDescricao());
			}
		}
		
		//BOTAO CANCELAR
		Button btCancelar = (Button) findViewById(R.id.btCancelar);
		btCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

		//BOTAO SALVAR
		Button btSalvar = (Button) findViewById(R.id.salvarQuestao);
		btSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				salvar(questao, respostas);
			}
		});
		
		/*
		//BOTAO EXCLUIR
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
		*/
	}


	public void salvar(Questao questao, List<Resposta> listaRespostas) {
		if(questao.getId() > 0){
			Log.d(Constantes.DEBUG_AND_COLLECTOR, "Editando questao de id " + questao.getId() + " do questionario id " + questao.getIdQuestionario());
		}
		
		questao.setDescricao(campoDescricao.getText().toString());
		questao.setTipo(tipoQuestao);
		
		questaoFacade.salvar(questao);
		
		listaRespostas.get(0).setDescricao(campoResposta1.getText().toString());
		listaRespostas.get(1).setDescricao(campoResposta2.getText().toString());
		listaRespostas.get(2).setDescricao(campoResposta3.getText().toString());
		listaRespostas.get(3).setDescricao(campoResposta4.getText().toString());
		
		Log.d(Constantes.DEBUG_AND_COLLECTOR, "Texto da resposta 1 informada:" + campoResposta1.getText().toString());
		
		respostaFacade.salvar(listaRespostas);

		Intent it = new Intent(this, EditarQuestionarioActivity.class);
		it.putExtra(Questionarios._ID, questao.getIdQuestionario());
		
		finish();
		startActivity(it);
	}

	/**
	 * Excluir uma questao
	 */
	public void excluir() {
		if (id != null) {
			questaoFacade.remover(id);
		}

		setResult(RESULT_OK, new Intent(this, ListarQuestoesActivity.class));
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, Acao.SALVAR.getCodigo(), 0, "Adicionar Resposta");
		menu.add(0, Acao.PROCURAR.getCodigo(), 0, "Visualizar Respostas");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// Clicou no menu
		switch (item.getItemId()) {
			case 1:
				// Abre a tela com o formulario para adicionar
				Intent it = new Intent(this, CadastroQuestaoActivity.class);
				it.putExtra(Questoes._ID, id);
				
				startActivityForResult(it, Acao.SALVAR.getCodigo());
				break;
			case 2:
				Intent it2 = new Intent(this, ListarQuestoesActivity.class);
				it2.putExtra(Questoes._ID, id);
				
				startActivity(it2);
		}
		return true;
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		setResult(RESULT_CANCELED);
		finish();
	}
	
	public void recuperarValorDosCamposDaTela(){
		campoDescricao = (EditText) findViewById(R.id.descricaoQuestao);
		campoResposta1 = (EditText) findViewById(R.id.resposta1);
		campoResposta2 = (EditText) findViewById(R.id.resposta2);
		campoResposta3 = (EditText) findViewById(R.id.resposta3);
		campoResposta4 = (EditText) findViewById(R.id.resposta4);
		
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		radioSimOuNao = (RadioButton) findViewById(R.id.radioSimOuNao);
		radioMultiplaEscolha = (RadioButton) findViewById(R.id.radioMultiplaEscolha);
		radioAberta = (RadioButton) findViewById(R.id.radioAberta);

		formMultiplaEscolha = (TableLayout) findViewById(R.id.formMultiplaEscolha);
		
		
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				boolean simOuNao = R.id.radioSimOuNao == checkedId;
				boolean multiplaEscolha = R.id.radioMultiplaEscolha == checkedId;
				boolean aberta = R.id.radioAberta == checkedId;
				
				if(simOuNao){
					tipoQuestao = TipoQuestao.SIM_OU_NAO.getCodigo();
					formMultiplaEscolha.setVisibility(View.INVISIBLE);
					
				}else if(multiplaEscolha){
					tipoQuestao = TipoQuestao.MULTIPLA_ESCOLHA.getCodigo();
					formMultiplaEscolha.setVisibility(View.VISIBLE);
					
				}else if(aberta){
					tipoQuestao = TipoQuestao.ABERTA.getCodigo();
					formMultiplaEscolha.setVisibility(View.INVISIBLE);

				}
			}
		});
	}
}

















