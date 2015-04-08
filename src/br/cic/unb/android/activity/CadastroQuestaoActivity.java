package br.cic.unb.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import br.cic.unb.android.R;
import br.cic.unb.android.dominio.Questao;
import br.cic.unb.android.dominio.Resposta;
import br.cic.unb.android.dominio.Questionario.Questionarios;
import br.cic.unb.android.enums.TipoQuestao;
import br.cic.unb.android.facade.QuestaoFacade;
import br.cic.unb.android.facade.RespostaFacade;

public class CadastroQuestaoActivity extends Activity{

	private QuestaoFacade questaoFacade;
	private RespostaFacade respostaFacade;
	
	private Long id;
	
	//representa os campos
	private EditText campoDescricao;
	private EditText resposta1;
	private EditText resposta2;
	private EditText resposta3;
	private EditText resposta4;
	
	private RadioGroup radioGroup;
	TableLayout formMultiplaEscolha;
	
	private Long idQuestionario;
	private int tipoQuestao;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		questaoFacade = new QuestaoFacade(this);
		respostaFacade = new RespostaFacade(this);
		
		setContentView(R.layout.form_editar_questao);
		
		recuperarValorDosCamposDaTela();
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			idQuestionario = extras.getLong(Questionarios._ID);
		}
		
		Button btSalvar = (Button) findViewById(R.id.salvarQuestao);
		btSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				salvar();
			}
		});
		
	}
	
	/**
	 * Salva uma questao e as respostas associadas
	 */
	public void salvar(){
		Questao questao = new Questao();
		
		if(id != null){
			questao.setId(id);
		}
		
		questao.setDescricao(campoDescricao.getText().toString());
		questao.setTipo(tipoQuestao);
		questao.setIdQuestionario(idQuestionario);
		
		long idQuestao = questaoFacade.salvar(questao);
		
		if(tipoQuestao == TipoQuestao.SIM_OU_NAO.getCodigo()){
			respostaFacade.salvar(new Resposta("Sim", idQuestao));
			respostaFacade.salvar(new Resposta("Não", idQuestao));
			
		}else if(tipoQuestao == TipoQuestao.MULTIPLA_ESCOLHA.getCodigo()){
			Resposta r1 = new Resposta(resposta1.getText().toString(), idQuestao);
			Resposta r2 = new Resposta(resposta2.getText().toString(), idQuestao);
			Resposta r3 = new Resposta(resposta3.getText().toString(), idQuestao);
			Resposta r4 = new Resposta(resposta4.getText().toString(), idQuestao);
			
			List<Resposta> listaRespostas = new ArrayList<Resposta>();
			listaRespostas.add(r1);
			listaRespostas.add(r2);
			listaRespostas.add(r3);
			listaRespostas.add(r4);
			
			respostaFacade.salvar(listaRespostas);
		}

		Intent it = new Intent(this, EditarQuestionarioActivity.class);
		it.putExtra(Questionarios._ID, questao.getIdQuestionario());
		
		finish();
		startActivity(it);
	}
	
	public void recuperarValorDosCamposDaTela(){
		campoDescricao = (EditText) findViewById(R.id.descricaoQuestao);
		resposta1 = (EditText) findViewById(R.id.resposta1);
		resposta2 = (EditText) findViewById(R.id.resposta2);
		resposta3 = (EditText) findViewById(R.id.resposta3);
		resposta4 = (EditText) findViewById(R.id.resposta4);
		
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		
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
	
	//getters and setters
	public Long getIdQuestionario() {
		return idQuestionario;
	}

	public void setIdQuestionario(Long idQuestionario) {
		this.idQuestionario = idQuestionario;
	}

	public int getTipoQuestao() {
		return tipoQuestao;
	}

	public void setTipoQuestao(int tipoQuestao) {
		this.tipoQuestao = tipoQuestao;
	}
	
	
	
}
