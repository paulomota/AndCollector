package br.cic.unb.android.banco;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import br.cic.unb.android.dominio.Questionario;
import br.cic.unb.android.dominio.Questionario.Questionarios;
import br.cic.unb.android.enums.Tabela;

/**
 * Repositorio para Questionario que utiliza o SQLite internamente
 * 
 * @author paulomota e jeffersonmotta
 * 
 */
public class RepositorioQuestionario {
	private static final String CATEGORIA = "AND_COLLECTOR";

	protected static SQLiteDatabase database;

	public long salvar(Questionario questionario) {
		long id = questionario.getId();

		if (id != 0) {
			atualizar(questionario);
		} else {
			id = inserir(questionario);
		}

		return id;
	}

	/**
	 * Insere um novo questionario
	 * @param questionario
	 * @return
	 */
	public long inserir(Questionario questionario) {
		ContentValues values = new ContentValues();
		
		values.put(Questionarios.NOME, questionario.getNome());
		values.put(Questionarios.DESCRICAO, questionario.getDescricao());
		
		if(questionario.isAnonimo()){
			values.put(Questionarios.IC_ANONIMO,"S");
		}else{
			values.put(Questionarios.IC_ANONIMO, "N");
		}

		long id = database.insert(Tabela.QUESTIONARIO.getNome(), "", values);
		return id;
	}

	/**
	 * Atualiza os dados de um questionario no banco
	 * @param questionario
	 * @return
	 */
	public int atualizar(Questionario questionario) {
		ContentValues values = new ContentValues();
		
		values.put(Questionarios.NOME, questionario.getNome());
		values.put(Questionarios.DESCRICAO, questionario.getDescricao());
		
		if(questionario.isAnonimo()){
			values.put(Questionarios.IC_ANONIMO, "S");
		}else{
			values.put(Questionarios.IC_ANONIMO, "N");
		}

		String _id = String.valueOf(questionario.getId());

		String where = Questionarios._ID + "=?";
		String[] whereArgs = new String[] { _id };

		int count = database.update(Tabela.QUESTIONARIO.getNome(), values, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		
		return count;
	}

	public int deletar(long id) {
		String where = Questionarios._ID + "=?";

		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };

		int count = database.delete(Tabela.QUESTIONARIO.getNome(), where, whereArgs);
		Log.i(CATEGORIA, "Deletou [" + count + "] registros");
		return count;
	}


	public Questionario buscarQuestionario(long id) {
		// select * from questionario where _id=?
		Cursor c = database.query(true, Tabela.QUESTIONARIO.getNome(), Questionario.colunas, Questionarios._ID + "=" + id, null, null, null, null, null);

		if (c.getCount() > 0) {

			c.moveToFirst();

			Questionario questionario = new Questionario();

			questionario.setId(c.getLong(0));
			questionario.setNome(c.getString(1));
			questionario.setDescricao(c.getString(2));
			
			if(c.getString(3).equals("S")){
				questionario.setAnonimo(true);
			}else{
				questionario.setAnonimo(false);
			}
			
			return questionario;
		}

		return null;
	}

	// Retorna um cursor com todos os questionarios
	public Cursor getCursor() {
		try {
			// select * from questionario
			return database.query(Tabela.QUESTIONARIO.getNome(), Questionario.colunas, null, null, null, null, null, null);
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar os questionarios: " + e.toString());
			return null;
		}
	}

	// Retorna uma lista com todos os questionarios
	public List<Questionario> listarQuestionarios() {
		Cursor c = getCursor();

		List<Questionario> questionarios = new ArrayList<Questionario>();

		if (c.moveToFirst()) {

			int idxId = c.getColumnIndex(Questionarios._ID);
			int idxNome = c.getColumnIndex(Questionarios.NOME);
			int idDescricao = c.getColumnIndex(Questionarios.DESCRICAO);
			int idAnonimo = c.getColumnIndex(Questionarios.IC_ANONIMO);

			do {
				Questionario questionario = new Questionario();
				questionarios.add(questionario);

				questionario.setId(c.getLong(idxId));
				questionario.setNome(c.getString(idxNome));
				questionario.setDescricao(c.getString(idDescricao));
				
				if(c.getString(idAnonimo).equals("S")){
					questionario.setAnonimo(true);
				}else{
					questionario.setAnonimo(false);
				}

			} while (c.moveToNext());
		}

		return questionarios;
	}

	// Busca o Questionario pelo nome "select * from Questionario where nome=?"
	public Questionario buscarQuestionarioPorNome(String nome) {
		Questionario questionario = null;

		try {
			// Idem a: SELECT _id,nome,placa,ano from questionario where nome = ?
			Cursor c = database.query(Tabela.QUESTIONARIO.getNome(), Questionario.colunas, Questionarios.NOME + "='" + nome + "'", null, null, null, null);

			if (c.moveToNext()) {

				questionario = new Questionario();

				questionario.setId(c.getLong(0));
				questionario.setNome(c.getString(1));
				questionario.setDescricao(c.getString(2));
				
				if(c.getString(3).equals("S")){
					questionario.setAnonimo(true);
				}else{
					questionario.setAnonimo(false);
				}
			}
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar o questionario pelo nome: " + e.toString());
			return null;
		}

		return questionario;
	}

	// Busca um Questionario utilizando as configurações definidas no
	// SQLiteQueryBuilder
	// Utilizado pelo Content Provider de questionario
	public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		Cursor c = queryBuilder.query(database, projection, selection, selectionArgs, groupBy, having, orderBy);
		return c;
	}


	public void fechar() {
		if (database != null) {
			database.close();
		}
	}

	/**
	 * Recupera a quantidade total de questoes cadastradas para o questionario com o id informado
	 * @param id do questionario
	 * @return
	 */
	public int recuperarQtdQuestoes(long id) {
		Log.i("AND_FRAMEWORK", "recuperando quantidade de questoes de " + id);
		
		String sql = "select count(*) from "+ Tabela.QUESTAO.getNome() + " where id_questionario = '" + id + "';";
		SQLiteStatement state = database.compileStatement(sql);

		return (int) state.simpleQueryForLong();
	}
}
