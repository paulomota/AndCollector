package br.cic.unb.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import br.cic.unb.android.Constantes;
import br.cic.unb.android.R;
import br.cic.unb.android.dominio.ColetaResposta;
import br.cic.unb.android.dominio.Questao;
import br.cic.unb.android.dominio.Questionario;
import br.cic.unb.android.dominio.Resposta;
import br.cic.unb.android.dominio.Questionario.Questionarios;
import br.cic.unb.android.facade.ColetaRespostaFacade;
import br.cic.unb.android.facade.QuestaoFacade;
import br.cic.unb.android.facade.QuestionarioFacade;
import br.cic.unb.android.facade.RespostaFacade;

public class ColetaDeDadosActivity extends Activity {
	private QuestionarioFacade questionarioFacade;
	private QuestaoFacade questaoFacade;
	private RespostaFacade respostaFacade;
	private ColetaRespostaFacade coletaRespostaFacade;
	
	//parametros vindos da tela anterior
	private long idQuestionario;
	private long idQuestao;
	private long idColetaQuestionario;
	private int contadorQuestao = 1;
	
	//auxiliares
	private List<Questao> listaQuestoes = null;
	private List<Resposta> listaRespostas = null;
	private Questionario questionario = null;
	private Questao questao = null;
	private Resposta resposta = null;
	private int indexRespostaDada;
	
	private long idAnterior;
	private long idProxima;
	
	//campos da tela
	private TextView infoQuestao;
	private TextView nomeQuestionario;
	private EditText campoDescricao;
	private EditText campoRespostaAberta;
	private RadioGroup radioGroup;
	private RadioButton resposta1;
	private RadioButton resposta2;
	private RadioButton resposta3;
	private RadioButton resposta4;
	
	private Button btFinalizar;
	private ImageButton btProximo;
	private ImageButton btAnterior;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		questionarioFacade = new QuestionarioFacade(this);
		questaoFacade = new QuestaoFacade(this);
		respostaFacade = new RespostaFacade(this);
		coletaRespostaFacade = new ColetaRespostaFacade(this);
		
		init();
		
		questionario = questionarioFacade.buscarQuestionario(idQuestionario);
		listaQuestoes = questaoFacade.listarQuestoes(idQuestionario);
		
		if(listaQuestoes != null){
			if(this.getIdQuestao() == 0){
				questao = listaQuestoes.get(0);
			}else{
				questao = questaoFacade.buscarQuestao(this.getIdQuestao());
			}
		}
		
		infoQuestao.setText("Questão " + contadorQuestao + "/" + listaQuestoes.size());
		nomeQuestionario.setText(questionario.getNome());
		campoDescricao.setText(questao.getDescricao());
		listaRespostas = respostaFacade.recuperarRepostas(questao);
		
		exibirRespostas(listaRespostas);
		tratarEventoBotoes(listaQuestoes);
	}
	
	
	/**
	 * Inicializa todos os campos e parametros necessarios que serao utlizados pela tela
	 */
	private void init(){
		setContentView(R.layout.form_coleta_dados);
		
		recuperarCamposDaTela();
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			idQuestionario = extras.getLong(Questionarios._ID);
			Log.d(Constantes.DEBUG_AND_COLLECTOR, "Recuperou da pagina anterior, idQuestionario = " + getIdQuestionario());
			
			idQuestao = extras.getLong("idQuestao");
			Log.d(Constantes.DEBUG_AND_COLLECTOR, "Recuperou da pagina anterior, idQuestao = " + getIdQuestao());
			
			idColetaQuestionario = extras.getLong("idColetaQuestionario");
			Log.d(Constantes.DEBUG_AND_COLLECTOR, "Recuperou da pagina anterior, idColetaQuestionario = " + getIdColetaQuestionario());
			
			contadorQuestao = extras.getInt("contador");
		}
	}

	private void tratarEventoBotoes(final List<Questao> listaQuestoes) {
		List<Long> listaIds = getListaIdQuestoes(listaQuestoes);

		long idAtual = questao.getId();
		idQuestao = idAtual;
		
		int posicao = 0;
		
		for(Long id : listaIds){
			if(idAtual == id){
				break;
			}
			posicao++;
		}
		
		Log.d(Constantes.DEBUG_AND_COLLECTOR,"Posicao = " + posicao);
		
		if(posicao > 0){
			idAnterior = listaIds.get(posicao - 1);
		}
		if(posicao < listaIds.size() - 1){
			idProxima = listaIds.get(posicao + 1);
		}
		
		Log.d(Constantes.DEBUG_AND_COLLECTOR, "idAnterior = " + idAnterior);
		Log.d(Constantes.DEBUG_AND_COLLECTOR,"idAtual = " + idAtual);
		Log.d(Constantes.DEBUG_AND_COLLECTOR, "idProxima = " + idProxima);
		
		//botao questao anterior
		btAnterior = (ImageButton) findViewById(R.id.btAnterior);
		btAnterior.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent it = new Intent(ColetaDeDadosActivity.this, ColetaDeDadosActivity.class);
				it.putExtra(Questionarios._ID, getIdQuestionario());
				it.putExtra("idQuestao", idAnterior);
				
				if(contadorQuestao - 1 <= 0){
					it.putExtra("contador", 1);
				}else{
					it.putExtra("contador", contadorQuestao - 1);
				}
				
				
				finish();
				startActivity(it);
			}
		});
		
		//botao proxima questao
		btProximo = (ImageButton) findViewById(R.id.btProximo);
		btProximo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				if(idProxima > 0){
					salvarColetaResposta();
					
					Intent it = new Intent(ColetaDeDadosActivity.this, ColetaDeDadosActivity.class);
					it.putExtra(Questionarios._ID, getIdQuestionario());
					it.putExtra("idQuestao", idProxima);
					it.putExtra("idColetaQuestionario", idColetaQuestionario);
					
					if(contadorQuestao + 1 > listaQuestoes.size()){
						it.putExtra("contador", listaQuestoes.size());
					}else{
						it.putExtra("contador", contadorQuestao + 1);
					}
					
					finish();
					startActivity(it);
					
				}else{
					//Toast.makeText(ColetaDeDadosActivity.this, "Não existe próxima, clique em finalizar.", Toast.LENGTH_SHORT).show();
					btFinalizar.setVisibility(View.VISIBLE);
					btProximo.setVisibility(View.INVISIBLE);
				}
			}
		});
		
		//botao finalizar coleta
		btFinalizar = (Button) findViewById(R.id.btConcluir);
		btFinalizar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				salvarColetaResposta();
				Toast.makeText(ColetaDeDadosActivity.this, "Coleta de dados concluida. Os dados foram salvos com sucesso!", Toast.LENGTH_LONG).show();
				finish();
			}
		});
	}


	/**
	 * salva um registro de coletaResposta com as informacoes recuperadas da tela com a questao e a resposta do usuario
	 * @return
	 */
	public long salvarColetaResposta(){
		ColetaResposta coleta = new ColetaResposta();
		coleta.setIdColetaQuestionario(idColetaQuestionario);
		coleta.setIdQuestao(idQuestao);
		
		List<Resposta> lista = respostaFacade.recuperarRepostas(questao);
		
		if(lista != null && lista.size() > 0){
			coleta.setIdRespostaDada(lista.get(indexRespostaDada).getId());
		}
		coleta.setRespostaAberta(campoRespostaAberta.getText().toString());
		
		return coletaRespostaFacade.salvar(coleta);
	}
	
	
	/**
	 * Retorna uma lista de ids de questoes, na ordem em que elas foram cadastradas
	 * @param listaQuestoes
	 * @return
	 */
	private List<Long> getListaIdQuestoes(List<Questao> listaQuestoes) {
		List<Long> listaId = new ArrayList<Long>();
		
		for (Questao questao : listaQuestoes) {
			if(questao.getIdQuestionario() == questionario.getId()){
				listaId.add(questao.getId());
				Log.d(Constantes.DEBUG_AND_COLLECTOR, "Questao ID = " + questao.getId());
			}
		}
		
		return listaId;
	}

	/**
	 * Exibe os radioButton com as respostas de acordo com a quantidade de respostas cadastradas para a questao
	 * @param listaRespostas
	 */
	private void exibirRespostas(List<Resposta> listaRespostas) {
		int qtdRespostas = listaRespostas.size();
		Log.d(Constantes.DEBUG_AND_COLLECTOR, "Exibindo quantidade " + qtdRespostas + " de respostas");
		
		switch(qtdRespostas){
			case 0:
				campoRespostaAberta.setVisibility(View.VISIBLE);
				resposta1.setVisibility(View.INVISIBLE);
				resposta2.setVisibility(View.INVISIBLE);
				resposta3.setVisibility(View.INVISIBLE);
				resposta4.setVisibility(View.INVISIBLE);
				
				break;
			case 1:
				campoRespostaAberta.setVisibility(View.INVISIBLE);
				resposta1.setVisibility(View.VISIBLE);
				resposta2.setVisibility(View.INVISIBLE);
				resposta3.setVisibility(View.INVISIBLE);
				resposta4.setVisibility(View.INVISIBLE);
				
				resposta1.setText(listaRespostas.get(0).getDescricao());
				
				break;
			case 2:
				campoRespostaAberta.setVisibility(View.INVISIBLE);
				resposta1.setVisibility(View.VISIBLE);
				resposta2.setVisibility(View.VISIBLE);
				resposta3.setVisibility(View.INVISIBLE);
				resposta4.setVisibility(View.INVISIBLE);
				
				resposta1.setText(listaRespostas.get(0).getDescricao());
				resposta2.setText(listaRespostas.get(1).getDescricao());
				
				break;
			case 3:
				campoRespostaAberta.setVisibility(View.INVISIBLE);
				resposta1.setVisibility(View.VISIBLE);
				resposta2.setVisibility(View.VISIBLE);
				resposta3.setVisibility(View.VISIBLE);
				resposta4.setVisibility(View.INVISIBLE);
				
				resposta1.setText(listaRespostas.get(0).getDescricao());
				resposta2.setText(listaRespostas.get(1).getDescricao());
				resposta3.setText(listaRespostas.get(2).getDescricao());
				
				break;
			case 4:
				campoRespostaAberta.setVisibility(View.INVISIBLE);
				resposta1.setVisibility(View.VISIBLE);
				resposta2.setVisibility(View.VISIBLE);
				resposta3.setVisibility(View.VISIBLE);
				resposta4.setVisibility(View.VISIBLE);
				
				resposta1.setText(listaRespostas.get(0).getDescricao());
				resposta2.setText(listaRespostas.get(1).getDescricao());
				resposta3.setText(listaRespostas.get(2).getDescricao());
				resposta4.setText(listaRespostas.get(3).getDescricao());
				
				break;
		}
	}

	/**
	 * Recupera os campos na tela, para permitir popula-los e tambem recuperar os valores informados
	 */
	public void recuperarCamposDaTela(){
		infoQuestao = (TextView) findViewById(R.id.infoQuestao);
		nomeQuestionario = (TextView) findViewById(R.id.nomeQuestionario);
		campoDescricao = (EditText) findViewById(R.id.campoDescricao);
		campoRespostaAberta = (EditText) findViewById(R.id.campoRespostaAberta);
		
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		resposta1 = (RadioButton) findViewById(R.id.resposta1);
		resposta2 = (RadioButton) findViewById(R.id.resposta2);
		resposta3 = (RadioButton) findViewById(R.id.resposta3);
		resposta4 = (RadioButton) findViewById(R.id.resposta4);
		
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				boolean isResposta1 = R.id.resposta1 == checkedId;
				boolean isResposta2 = R.id.resposta2 == checkedId;
				boolean isResposta3 = R.id.resposta3 == checkedId;
				boolean isResposta4 = R.id.resposta4 == checkedId;
				
				if(isResposta1){
					setIndexRespostaDada(0);
				}else if(isResposta2){
					setIndexRespostaDada(1);
				}else if(isResposta3){
					setIndexRespostaDada(2);
				}else if(isResposta4){
					setIndexRespostaDada(3);
				}
			}
		});
		
	}
	
	
	/* getters and setters */
	public long getIdQuestionario() {
		return idQuestionario;
	}

	public void setIdQuestionario(long idQuestionario) {
		this.idQuestionario = idQuestionario;
	}

	public QuestionarioFacade getQuestionarioFacade() {
		return questionarioFacade;
	}

	public void setQuestionarioFacade(QuestionarioFacade questionarioFacade) {
		this.questionarioFacade = questionarioFacade;
	}

	public QuestaoFacade getQuestaoFacade() {
		return questaoFacade;
	}

	public void setQuestaoFacade(QuestaoFacade questaoFacade) {
		this.questaoFacade = questaoFacade;
	}

	public List<Questao> getListaQuestoes() {
		return listaQuestoes;
	}

	public void setListaQuestoes(List<Questao> listaQuestoes) {
		this.listaQuestoes = listaQuestoes;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public EditText getCampoDescricao() {
		return campoDescricao;
	}

	public void setCampoDescricao(EditText campoDescricao) {
		this.campoDescricao = campoDescricao;
	}

	public RadioGroup getRadioGroup() {
		return radioGroup;
	}

	public void setRadioGroup(RadioGroup radioGroup) {
		this.radioGroup = radioGroup;
	}

	public RadioButton getResposta1() {
		return resposta1;
	}

	public void setResposta1(RadioButton resposta1) {
		this.resposta1 = resposta1;
	}

	public RadioButton getResposta2() {
		return resposta2;
	}

	public void setResposta2(RadioButton resposta2) {
		this.resposta2 = resposta2;
	}

	public RadioButton getResposta3() {
		return resposta3;
	}

	public void setResposta3(RadioButton resposta3) {
		this.resposta3 = resposta3;
	}

	public RadioButton getResposta4() {
		return resposta4;
	}

	public void setResposta4(RadioButton resposta4) {
		this.resposta4 = resposta4;
	}

	public Resposta getResposta() {
		return resposta;
	}

	public void setResposta(Resposta resposta) {
		this.resposta = resposta;
	}

	public int getIndexRespostaDada() {
		return indexRespostaDada;
	}

	public void setIndexRespostaDada(int indexRespostaDada) {
		this.indexRespostaDada = indexRespostaDada;
	}

	public RespostaFacade getRespostaFacade() {
		return respostaFacade;
	}

	public void setRespostaFacade(RespostaFacade respostaFacade) {
		this.respostaFacade = respostaFacade;
	}

	public List<Resposta> getListaRespostas() {
		return listaRespostas;
	}

	public void setListaRespostas(List<Resposta> listaRespostas) {
		this.listaRespostas = listaRespostas;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public long getIdAnterior() {
		return idAnterior;
	}

	public void setIdAnterior(long idAnterior) {
		this.idAnterior = idAnterior;
	}

	public long getIdProxima() {
		return idProxima;
	}

	public void setIdProxima(long idProxima) {
		this.idProxima = idProxima;
	}

	public TextView getNomeQuestionario() {
		return nomeQuestionario;
	}

	public void setNomeQuestionario(TextView nomeQuestionario) {
		this.nomeQuestionario = nomeQuestionario;
	}

	public long getIdQuestao() {
		return idQuestao;
	}

	public void setIdQuestao(long idQuestao) {
		this.idQuestao = idQuestao;
	}

	public EditText getCampoRespostaAberta() {
		return campoRespostaAberta;
	}

	public void setCampoRespostaAberta(EditText campoRespostaAberta) {
		this.campoRespostaAberta = campoRespostaAberta;
	}

	public long getIdColetaQuestionario() {
		return idColetaQuestionario;
	}

	public void setIdColetaQuestionario(long idColetaQuestionario) {
		this.idColetaQuestionario = idColetaQuestionario;
	}


	public ColetaRespostaFacade getColetaRespostaFacade() {
		return coletaRespostaFacade;
	}


	public void setColetaRespostaFacade(ColetaRespostaFacade coletaRespostaFacade) {
		this.coletaRespostaFacade = coletaRespostaFacade;
	}


	public Button getBtFinalizar() {
		return btFinalizar;
	}


	public void setBtFinalizar(Button btFinalizar) {
		this.btFinalizar = btFinalizar;
	}


	public ImageButton getBtProximo() {
		return btProximo;
	}


	public void setBtProximo(ImageButton btProximo) {
		this.btProximo = btProximo;
	}


	public ImageButton getBtAnterior() {
		return btAnterior;
	}


	public void setBtAnterior(ImageButton btAnterior) {
		this.btAnterior = btAnterior;
	}


	public int getContadorQuestao() {
		return contadorQuestao;
	}


	public void setContadorQuestao(int contadorQuestao) {
		this.contadorQuestao = contadorQuestao;
	}


	public TextView getInfoQuestao() {
		return infoQuestao;
	}


	public void setInfoQuestao(TextView infoQuestao) {
		this.infoQuestao = infoQuestao;
	}
	
}
