package br.cic.unb.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.cic.unb.android.R;
import br.cic.unb.android.dominio.Questionario;

/**
 * Buscar um Questionario.
 * 
 * @author paulomota
 * 
 */
public class BuscarQuestionarioActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.form_buscar_questionario);

		Button btBuscar = (Button) findViewById(R.id.btBuscar);
		btBuscar.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Cancela para nao ficar nada pendente na tela
		setResult(RESULT_CANCELED);

		finish();
	}

	/**
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View view) {

		EditText nome = (EditText) findViewById(R.id.campoNome);
		EditText descricao = (EditText) findViewById(R.id.campoDescricao);

		String nomeQuestionario = nome.getText().toString();

		Questionario questionario = buscarQuestionario(nomeQuestionario);

		if (questionario != null) {
			// Atualiza os campos com o resultado
			descricao.setText(questionario.getDescricao());
		} else {
			// Limpa os campos
			descricao.setText("");

			Toast.makeText(BuscarQuestionarioActivity.this, "Nenhum carro encontrado", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Busca um questionario pelo nome
	 * @param nomeQuestionario
	 * @return
	 */
	protected Questionario buscarQuestionario(String nomeQuestionario) {
		return CadastroQuestionarioActivity.questionarioFacade.buscarQuestionarioPorNome(nomeQuestionario);
	}
}
