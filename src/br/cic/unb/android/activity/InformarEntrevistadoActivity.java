package br.cic.unb.android.activity;

import java.sql.Timestamp;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.cic.unb.android.R;
import br.cic.unb.android.dominio.ColetaQuestionario;
import br.cic.unb.android.dominio.Questionario.Questionarios;
import br.cic.unb.android.facade.ColetaQuestionarioFacade;

public class InformarEntrevistadoActivity extends Activity {

	private ColetaQuestionarioFacade coletaQuestionarioFacade;
	
	private EditText entrevistado;
	private long idQuestionario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		coletaQuestionarioFacade = new ColetaQuestionarioFacade(this);
		
		setContentView(R.layout.form_informar_entrevistado);
		
		entrevistado = (EditText) findViewById(R.id.entrevistado);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			idQuestionario = extras.getLong(Questionarios._ID);
		}
		
		
		//BOTAO SALVAR
		Button btSalvar = (Button) findViewById(R.id.btSalvar);
		btSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				salvarColetaQuestionario();
			}
		});
	}
	
	/**
	 * Salva as informacoes de uma coleta especifica
	 */
	private void salvarColetaQuestionario(){
		ColetaQuestionario coleta = new ColetaQuestionario();
		
		coleta.setIdQuestionario(idQuestionario);
		coleta.setEntrevistado(entrevistado.getText().toString());
		long agora = new Date().getTime();
		coleta.setData(new Timestamp(agora));
		
		long idColeta = coletaQuestionarioFacade.salvar(coleta);
		
		finish();
		
		Intent it = new Intent(this, ColetaDeDadosActivity.class);
		it.putExtra(Questionarios._ID, idQuestionario);
		it.putExtra("idColetaQuestionario", idColeta);
		startActivity(it);
	}
	
	
	/* getters and setters */
	public ColetaQuestionarioFacade getColetaQuestionarioFacade() {
		return coletaQuestionarioFacade;
	}

	public void setColetaQuestionarioFacade(
			ColetaQuestionarioFacade coletaQuestionarioFacade) {
		this.coletaQuestionarioFacade = coletaQuestionarioFacade;
	}

	public EditText getEntrevistado() {
		return entrevistado;
	}

	public void setEntrevistado(EditText entrevistado) {
		this.entrevistado = entrevistado;
	}

	public long getIdQuestionario() {
		return idQuestionario;
	}

	public void setIdQuestionario(long idQuestionario) {
		this.idQuestionario = idQuestionario;
	}
	
	
}
