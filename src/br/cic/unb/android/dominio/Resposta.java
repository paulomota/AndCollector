package br.cic.unb.android.dominio;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import br.cic.unb.android.dominio.Questionario.Questionarios;

public class Resposta {

	public static final String[] colunas = new String[]{
														Respostas._ID, 
														Respostas.DESCRICAO, 
														Respostas.ID_QUESTAO
														};
	
	public static final String AUTHORITY = "br.livro.android.provider.resposta";

	private long id;
	private String descricao;
	private long idQuestao;
	
	
	public Resposta(){
		
	}
	
	public Resposta(String descricao, long idQuestao) {
		this.descricao = descricao;
		this.idQuestao = idQuestao;
	}

	public Resposta(String descricao, long idQuestao, long idResposta) {
		this.descricao = descricao;
		this.idQuestao = idQuestao;
		this.id = idResposta;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public long getIdQuestao() {
		return idQuestao;
	}
	
	public void setIdQuestao(long idQuestao) {
		this.idQuestao = idQuestao;
	}
	
	
	@Override
	public String toString() {
		String string = "resposta id = " + this.getId() +
						", descricao = " + this.getDescricao() +
						", id_questao = " + this.getIdQuestao();
				
			
		return string;
	}
	
	public static final class Respostas implements BaseColumns {
		
		// Nao pode instanciar esta Classe
		private Respostas() {
		}
	
		// content://br.livro.android.provider.respostas/respostas
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/respostas");
	
		// Mime Type para todos as respostas
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.respostas";
	
		// Mime Type para um unica respostas
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.respostas";
	
		// Ordenacao default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
	
		public static final String DESCRICAO = "descricao";
		public static final String ID_QUESTAO = "id_questao";
	
		// Metodo que constroi uma Uri para uma resposta especifica, com o seu id
		// A Uri é no formato "content://br.livro.android.provider.respostas/respostas/id"
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /respostas
			Uri uri = ContentUris.withAppendedId(Questionarios.CONTENT_URI, id);
			return uri;
		}
	}
	
}
