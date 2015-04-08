package br.cic.unb.android.banco;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import br.cic.unb.android.Constantes;
import br.cic.unb.android.dominio.Questao;
import br.cic.unb.android.dominio.Resposta;
import br.cic.unb.android.dominio.Questao.Questoes;
import br.cic.unb.android.dominio.Resposta.Respostas;
import br.cic.unb.android.enums.Tabela;

public class RepositorioResposta {

	public RepositorioResposta(Context context) {

	}

	/**
	 * Insere ou atualiza uma resposta no banco
	 * @param resposta
	 * @return
	 */
	public long salvar(Resposta resposta) {
		long id = resposta.getId();
		
		if(id != 0){
			atualizar(resposta);
		} else{
			id = inserir(resposta);
		}
		
		return id;
		
	}

	/**
	 * Chama o metodo salvar para cada resposta de uma lista de respostas
	 * @param listaRespostas
	 */
	public void salvar(List<Resposta> listaRespostas) {
		for(Resposta resposta : listaRespostas){
			this.salvar(resposta);
		}
		
	}

	/**
	 * Insere uma nova resposta
	 * @param resposta
	 * @return
	 */
	public long inserir(Resposta resposta) {
		ContentValues values = new ContentValues();
		
		values.put(Respostas.DESCRICAO, resposta.getDescricao());
		values.put(Respostas.ID_QUESTAO, resposta.getIdQuestao());

		long id = RepositorioQuestionario.database.insert(Tabela.RESPOSTA.getNome(), "", values);
		
		Log.i(Constantes.DEBUG_AND_COLLECTOR, "inserido resposta na questao de id: " + resposta.getIdQuestao());
		
		return id;
	}

	/**
	 * Atualiza os dados de uma resposta no banco
	 * @param resposta
	 * @return
	 */
	public int atualizar(Resposta resposta) {
		ContentValues values = new ContentValues();
		
		values.put(Respostas.DESCRICAO, resposta.getDescricao());
		values.put(Respostas.ID_QUESTAO, resposta.getIdQuestao());

		String _id = String.valueOf(resposta.getId());

		String where = Questoes._ID + "=?";
		String[] whereArgs = new String[] { _id };

		int count = RepositorioQuestionario.database.update(Tabela.RESPOSTA.getNome(), values, where, whereArgs);
		
		Log.i(Constantes.DEBUG_AND_COLLECTOR, "Atualizou [" + count + "] registros");
		
		return count;
	}

	/**
	 * Recupera uma lista de respostas de uma dada questao
	 * @param questao
	 * @return
	 */
	public List<Resposta> recuperarRespostas(Questao questao) {
		Cursor c = RepositorioQuestionario.database.query(
								true, 
								Tabela.RESPOSTA.getNome(), 
								Resposta.colunas, 
								Respostas.ID_QUESTAO + "=" + questao.getId(), 
								null, null, null, null, null);

		List<Resposta> respostas = new ArrayList<Resposta>();
		
		int idxId = c.getColumnIndex(Respostas._ID);
		int idxDescricao = c.getColumnIndex(Respostas.DESCRICAO);
		int idxIdQuestao = c.getColumnIndex(Respostas.ID_QUESTAO);
		
		if(c.moveToFirst()){
			
			do{
			Resposta resposta = new Resposta();
			resposta.setId(c.getLong(idxId));
			resposta.setDescricao(c.getString(idxDescricao));
			resposta.setIdQuestao(c.getLong(idxIdQuestao));
			
			respostas.add(resposta);

			Log.d(Constantes.DEBUG_AND_COLLECTOR, "Recuperou: " + resposta);
				
			}while(c.moveToNext());
		}
		
		return respostas;
	}

	/**
	 * Remove uma resposta do banco de dados
	 * @param resposta
	 */
	public void remover(Resposta resposta) {
		String where = Respostas._ID + "=?";

		String _id = String.valueOf(resposta.getId());
		String[] whereArgs = new String[] { _id };

		RepositorioQuestionario.database.delete(Tabela.RESPOSTA.getNome(), where, whereArgs);
		Log.i(Constantes.DEBUG_AND_COLLECTOR, "Deletou [" + resposta + "]");
	}

	
}
