package br.cic.unb.android.dominio;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import br.cic.unb.android.dominio.Questionario.Questionarios;

public class ColetaResposta {

	public static String[] colunas = new String[]{
													ColetaRespostas.ID_COLETA_QUESTIONARIO,
													ColetaRespostas.ID_QUESTAO,
													ColetaRespostas.ID_RESPOSTA_DADA,
													ColetaRespostas.RESPOSTA_ABERTA
												 };
	
	/**
	 * Pacote do Content Provider. Precisa ser único
	 */
	public static final String AUTHORITY = "br.livro.android.provider.coletaResposta";
	
	private long id;
	private long idColetaQuestionario;
	private long idQuestao;
	private long idRespostaDada;
	private String respostaAberta;
	
	
	/**
	 * Classe interna para representar as colunas e ser utilizada por um Content
	 * Provider
	 * 
	 * Filha de BaseColumns que já define (_id e _count), para seguir o padrao
	 * Android
	 */
	public static final class ColetaRespostas implements BaseColumns {
		private ColetaRespostas() {
		}
	
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/coletarespostas");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.coletarespostas";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.coletarespostas";
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
	
		//colunas da tabela coletaresposta no banco
		public static final String ID_COLETA_QUESTIONARIO = "id_coleta_questionario";
		public static final String ID_QUESTAO = "id_questao";
		public static final String ID_RESPOSTA_DADA = "id_resposta_dada";
		public static final String RESPOSTA_ABERTA = "resposta_aberta";
	
		public static Uri getUriId(long id) {
			Uri uri = ContentUris.withAppendedId(Questionarios.CONTENT_URI, id);
			return uri;
		}
	}

	@Override
	public String toString() {
		return "id: " + id + 
		", idColetaQuestionario: " + idColetaQuestionario + 
		", idQuestao: " + idQuestao + 
		", idRespostaDada: " + idRespostaDada + 
		", Resposta Aberta: " + respostaAberta;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdColetaQuestionario() {
		return idColetaQuestionario;
	}
	public void setIdColetaQuestionario(long idColetaQuestionario) {
		this.idColetaQuestionario = idColetaQuestionario;
	}
	public long getIdQuestao() {
		return idQuestao;
	}
	public void setIdQuestao(long idQuestao) {
		this.idQuestao = idQuestao;
	}
	public long getIdRespostaDada() {
		return idRespostaDada;
	}
	public void setIdRespostaDada(long idRespostaDada) {
		this.idRespostaDada = idRespostaDada;
	}
	public String getRespostaAberta() {
		return respostaAberta;
	}
	public void setRespostaAberta(String respostaAberta) {
		this.respostaAberta = respostaAberta;
	}
	
	
}
