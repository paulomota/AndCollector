package br.cic.unb.android.dominio;

import java.sql.Timestamp;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import br.cic.unb.android.dominio.Questionario.Questionarios;

public class ColetaQuestionario {
	public static String[] colunas = new String[] { 
												  ColetaQuestionarios._ID, 
												  ColetaQuestionarios.ID_QUESTIONARIO, 
												  ColetaQuestionarios.ENTREVISTADO, 
												  ColetaQuestionarios.DATA
												  };
	
	/**
	 * Pacote do Content Provider. Precisa ser único
	 */
	public static final String AUTHORITY = "br.livro.android.provider.questionario";
	
	private long id;
	private long idQuestionario;
	private String entrevistado;
	private Timestamp data;
	
	
	
	/**
	 * Classe interna para representar as colunas e ser utilizada por um Content
	 * Provider
	 * 
	 * Filha de BaseColumns que já define (_id e _count), para seguir o padrao
	 * Android
	 */
	public static final class ColetaQuestionarios implements BaseColumns {
	
		// Nao pode instanciar esta Classe
		private ColetaQuestionarios() {
		}
	
		// content://br.livro.android.provider.coletaquestionario/ColetaQuestionarios
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/coletaquestionarios");
	
		// Mime Type para todos os ColetaQuestionarios
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.coletaquestionarios";
	
		// Mime Type para um unico coletaquestionarios
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.coletaquestionarios";
	
		// Ordenacao default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
	
		public static final String ID_QUESTIONARIO = "id_questionario";
		public static final String ENTREVISTADO = "entrevistado";
		public static final String DATA = "data";
	
		// Metodo que constroi uma Uri para um questionario especifico, com o seu id
		// A Uri é no formato "content://br.livro.android.provider.coletaquestionario/coletaquestionarios/id"
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /coletaquestionarios
			Uri uri = ContentUris.withAppendedId(Questionarios.CONTENT_URI, id);
			return uri;
		}
	}

	@Override
	public String toString() {
		return "id: " + id + ", id_questionario: " + idQuestionario + ", Entrevistado: " + entrevistado + ", Data: " + data;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdQuestionario() {
		return idQuestionario;
	}
	public void setIdQuestionario(long idQuestionario) {
		this.idQuestionario = idQuestionario;
	}
	public String getEntrevistado() {
		return entrevistado;
	}
	public void setEntrevistado(String entrevistado) {
		this.entrevistado = entrevistado;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	
	
	
}
