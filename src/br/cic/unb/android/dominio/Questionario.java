package br.cic.unb.android.dominio;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Classe entidade para armazenar os valores de Questionario
 * 
 * @author Paulo R Mota - pauloroberto.mota@gmail.com
 * 
 */
public class Questionario {

	public static String[] colunas = new String[] { 
												  Questionarios._ID, 
												  Questionarios.NOME, 
												  Questionarios.DESCRICAO, 
												  Questionarios.IC_ANONIMO
												  };

	/**
	 * Pacote do Content Provider. Precisa ser único
	 */
	public static final String AUTHORITY = "br.livro.android.provider.questionario";

	private long id;
	private String nome;
	private String descricao;
	private boolean anonimo;

	public Questionario() {
	}

	public Questionario(String nome, String descricao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
	}

	public Questionario(long id, String nome, String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAnonimo() {
		return anonimo;
	}

	public void setAnonimo(boolean anonimo) {
		this.anonimo = anonimo;
	}



	/**
	 * Classe interna para representar as colunas e ser utilizada por um Content
	 * Provider
	 * 
	 * Filha de BaseColumns que já define (_id e _count), para seguir o padrao
	 * Android
	 */
	public static final class Questionarios implements BaseColumns {
	
		// Nao pode instanciar esta Classe
		private Questionarios() {
		}
	
		// content://br.livro.android.provider.questionario/questionarios
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/questionarios");
	
		// Mime Type para todos os questionarios
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.questionarios";
	
		// Mime Type para um unico questionario
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.questionarios";
	
		// Ordenacao default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
	
		public static final String NOME = "nome";
		public static final String DESCRICAO = "descricao";
		public static final String IC_ANONIMO = "ic_anonimo";
	
		// Metodo que constroi uma Uri para um questionario especifico, com o seu id
		// A Uri é no formato "content://br.livro.android.provider.questionario/questionarios/id"
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /questionarios
			Uri uri = ContentUris.withAppendedId(Questionarios.CONTENT_URI, id);
			return uri;
		}
	}

	@Override
	public String toString() {
		return "Nome: " + nome + ", Descricao: " + descricao + ", anonimo: " + anonimo;
	}
}
