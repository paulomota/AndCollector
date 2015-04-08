package br.cic.unb.android.banco;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import br.cic.unb.android.Constantes;
import br.cic.unb.android.dominio.Questao;
import br.cic.unb.android.dominio.Resposta;
import br.cic.unb.android.dominio.Questao.Questoes;
import br.cic.unb.android.dominio.Resposta.Respostas;
import br.cic.unb.android.enums.Tabela;

public class RepositorioQuestao {

	
	public RepositorioQuestao(Context context) {

	}

	public RepositorioQuestao(){
		
	}
	
	public long salvar(Questao questao) {
		long id = questao.getId();
		
		if(id != 0){
			atualizar(questao);
		} else{
			id = inserir(questao);
		}
		
		Log.d(Constantes.DEBUG_AND_COLLECTOR, "Salvou " + questao);
		return id;
		
	}

	/**
	 * Insere uma nova questao
	 * @param questao
	 * @return
	 */
	public long inserir(Questao questao) {
		ContentValues values = new ContentValues();
		
		values.put(Questoes.DESCRICAO, questao.getDescricao());
		values.put(Questoes.TIPO, questao.getTipo());
		values.put(Questoes.ID_QUESTIONARIO, questao.getIdQuestionario());

		long id = RepositorioQuestionario.database.insert(Tabela.QUESTAO.getNome(), "", values);
		
		Log.i("AND FRAMEWORK", "inserido questao no questionario: " + questao.getIdQuestionario());
		
		return id;
	}

	/**
	 * Atualiza os dados de uma questao no banco
	 * @param questao
	 * @return
	 */
	public int atualizar(Questao questao) {
		ContentValues values = new ContentValues();
		
		values.put(Questoes.DESCRICAO, questao.getDescricao());
		values.put(Questoes.TIPO, questao.getTipo());
		values.put(Questoes.ID_QUESTIONARIO, questao.getIdQuestionario());

		String _id = String.valueOf(questao.getId());

		String where = Questoes._ID + "=?";
		String[] whereArgs = new String[] { _id };

		int count = RepositorioQuestionario.database.update(Tabela.QUESTAO.getNome(), values, where, whereArgs);
		Log.i("AND COLLECTOR", "Atualizou [" + count + "] registros");
		
		return count;
	}

	// Retorna uma lista com todos as questoes de um questionarios
	public List<Questao> listarQuestoes(long idQuestionario) {
		Cursor c = getCursor(idQuestionario);

		List<Questao> questoes = new ArrayList<Questao>();

		if (c.moveToFirst()) {

			int idxId = c.getColumnIndex(Questoes._ID);
			int idxDescricao = c.getColumnIndex(Questoes.DESCRICAO);
			int idxTipo = c.getColumnIndex(Questoes.TIPO);
			int idxIdQuestionario = c.getColumnIndex(Questoes.ID_QUESTIONARIO);

			do {
				Questao questao = new Questao();
				questoes.add(questao);

				questao.setId(c.getLong(idxId));
				questao.setDescricao(c.getString(idxDescricao));
				questao.setTipo(c.getInt(idxTipo));
				questao.setIdQuestionario(c.getLong(idxIdQuestionario));

			} while (c.moveToNext());
		}

		return questoes;
	}
	
	/**
	 * Retorna a lista de objetos Resposta cadastrados para uma determinada questao
	 * @param idQuestao
	 * @return
	 */
	public List<Resposta> listarRespostas(long idQuestao){
		Cursor c = getCursorRespostas(idQuestao);
		
		List<Resposta> respostas = new ArrayList<Resposta>();
		
		if(c.moveToFirst()){
			int idxID = c.getColumnIndex(Respostas._ID);
			int idxDescricao = c.getColumnIndex(Respostas.DESCRICAO);
			int idxIdQuestao = c.getColumnIndex(Respostas.ID_QUESTAO);
			
			do{
				Resposta resposta = new Resposta();
				
				resposta.setId(c.getLong(idxID));
				resposta.setDescricao(c.getString(idxDescricao));
				resposta.setIdQuestao(c.getLong(idxIdQuestao));
				
				respostas.add(resposta);
				
			} while(c.moveToNext());
		}
		
		return respostas;
		
	}

	/**
	 * Retorna a quantidade de respostas ja cadastradas para uma determinada questao
	 * @param idQuestao
	 * @return
	 */
	public int getQuantidadeRespostas(int idQuestao){
		return listarRespostas(idQuestao).size();
	}
	
	/**
	 * Retorna um cursor com todos os questoes
	 */
	public Cursor getCursor(long idQuestionario) {
		try {
			return RepositorioQuestionario.database.query(Tabela.QUESTAO.getNome(), Questao.colunas, Questoes.ID_QUESTIONARIO + "='" + idQuestionario + "'", null, null, null, null, null);
		} catch (SQLException e) {
			Log.e("AND FRAMEWORK", "Erro ao buscar os questoes: " + e.toString());
			return null;
		}
	}
	
	
	/**
	 * Retorna um cursor com todas as respostas
	 * @param idQuestao
	 * @return
	 */
	public Cursor getCursorRespostas(long idQuestao){
		try {
			return RepositorioQuestionario.database.query(Tabela.RESPOSTA.getNome(), Resposta.colunas, Respostas.ID_QUESTAO + "='" + idQuestao + "'", null, null, null, null, null);
		} catch (SQLException e) {
			Log.e("AND FRAMEWORK", "Erro ao buscar os respostas para a questao id: " + idQuestao + e.toString());
			return null;
		}
	}

	
	/**
	 * Recupera a quantidade de respostas cadastradas para uma questao
	 * @param idQuestao
	 * @return
	 */
	public int recuperarQtdRespostas(Long idQuestao) {
		return this.getCursorRespostas(idQuestao).getCount();
	}

	
	/**
	 * Recupera uma questao pelo id
	 * @param id
	 * @return
	 */
	public Questao buscarQuestao(long id) {
		try {
			Cursor c =  RepositorioQuestionario.database.query(Tabela.QUESTAO.getNome(), Questao.colunas, Questoes._ID + "='" + id + "'", null, null, null, null, null);
			
			if (c.getCount() > 0) {

				c.moveToFirst();

				Questao questao = new Questao();

				questao.setId(c.getLong(0));
				questao.setDescricao(c.getString(1));
				questao.setTipo(c.getInt(2));
				questao.setIdQuestionario(c.getLong(3));
				
				return questao;
			}
			
			return null;
			
		} catch (SQLException e) {
			Log.e("AND FRAMEWORK", "Erro ao buscar a questao id: " + id + e.toString());
			return null;
		}
	}

	public void remover(Long idQuestao) {
		String where = Questoes._ID + "=?";

		String _id = String.valueOf(idQuestao);
		String[] whereArgs = new String[] { _id };

		int count = RepositorioQuestionario.database.delete(Tabela.QUESTAO.getNome(), where, whereArgs);
		Log.i(Constantes.DEBUG_AND_COLLECTOR, "Deletou [" + count + "] registros");
	}
	
	
}



















