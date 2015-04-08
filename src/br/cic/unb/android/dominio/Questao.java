package br.cic.unb.android.dominio;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import br.cic.unb.android.dominio.Questionario.Questionarios;
import br.cic.unb.android.enums.TipoQuestao;

public class Questao {

	public static final String[] colunas = new String[]{
														Questoes._ID, 
														Questoes.DESCRICAO, 
														Questoes.TIPO,
														Questoes.ID_QUESTIONARIO
														};
	
	public static final String AUTHORITY = "br.livro.android.provider.questao";
	
	private long id;
	private String descricao;
	private int tipo;
	private long idQuestionario;
	
	public Questao(){
		
	}
	
	public Questao(String descricao, long idQuestionario, int tipo){
		this.descricao = descricao;
		this.idQuestionario = idQuestionario;
	}
	
	public Questao(long id, String descricao, long idQuestionario, int tipo){
		this.id = id;
		this.descricao = descricao;
		this.idQuestionario = idQuestionario;
	}

	
	/* getters and setters */
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

	public long getIdQuestionario() {
		return idQuestionario;
	}

	public void setIdQuestionario(long idQuestionario) {
		this.idQuestionario = idQuestionario;
	}
	
	public int getTipo() {
		return tipo;
	}

	public String getTipoAsString(){
		TipoQuestao tipoDescricao = TipoQuestao.newInstance(this.getTipo());
		if(tipoDescricao != null){
			return tipoDescricao.getDescricao();
		}
		return "Erro no tipo";
	}
	
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}



	public static final class Questoes implements BaseColumns {
		
		// Nao pode instanciar esta Classe
		private Questoes() {
		}
	
		// content://br.livro.android.provider.questao/questoes
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/questoes");
	
		// Mime Type para todos os questionarios
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.questoes";
	
		// Mime Type para um unico questao
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.questoes";
	
		// Ordenacao default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
	
		public static final String DESCRICAO = "descricao";
		public static final String TIPO = "tipo";
		public static final String ID_QUESTIONARIO = "id_questionario";
	
		// Método que constroi uma Uri para uma questao especifica, com o seu id
		// A Uri é no formato "content://br.livro.android.provider.questao/questoes/id"
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /questoes
			Uri uri = ContentUris.withAppendedId(Questionarios.CONTENT_URI, id);
			return uri;
		}
	}
	
	@Override
	public String toString() {
		String toString = "QUESTAO - id = " + this.getId() +
					      ", descricao = " + this.getDescricao() +
					      ", tipo = " + this.getTipoAsString() +
						  ", id_questionario = " +  this.getIdQuestionario();
		return toString;
	}
}
