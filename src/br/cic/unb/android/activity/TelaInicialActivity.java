package br.cic.unb.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import br.cic.unb.android.R;

public class TelaInicialActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tela_inicial);
		
		ImageButton btConstrucao = (ImageButton) findViewById(R.id.construcao);
		btConstrucao.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(TelaInicialActivity.this, CadastroQuestionarioActivity.class));
			}
		});
		
		ImageButton btColeta = (ImageButton) findViewById(R.id.coleta);
		btColeta.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(TelaInicialActivity.this, ListarQuestionariosParaColetaActivity.class));
				//Toast.makeText(TelaInicialActivity.this, "Disponível em Breve!", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
