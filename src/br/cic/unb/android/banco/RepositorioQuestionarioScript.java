package br.cic.unb.android.banco;

import android.content.Context;

/**
 * Repositorio para carros que utiliza o SQLite internamente
 * 
 * @author jeffersonmotta e paulomota
 * 
 */
public class RepositorioQuestionarioScript extends RepositorioQuestionario {

	// Controle de versao
	private static final int VERSAO_BANCO = 8;

	private static final String SCRIPT_DATABASE_DELETE[] = new String[]{
		"DROP TABLE IF EXISTS questionario",
		"DROP TABLE IF EXISTS questao",
		"DROP TABLE IF EXISTS resposta",
		"DROP TABLE IF EXISTS coleta_questionario",
		"DROP TABLE IF EXISTS coleta_resposta"
	};

	private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
			"create table questionario ( _id integer primary key autoincrement, nome text not null, descricao text not null, ic_anonimo text not null);",
			"insert into questionario(nome,descricao, ic_anonimo) values('Eleições 2010','Pesquisa de boca de urna', 'S');",
			"insert into questionario(nome,descricao, ic_anonimo) values('Simulado UnB','Voltado para estudantes em fase prepatória para o vestibular', 'S');",
			
			"create table questao(_id integer primary key autoincrement, descricao text not null, tipo integer not null, id_questionario integer not null);",
			"insert into questao(descricao, tipo, id_questionario) values('Em quem vc vota para presidente?', 1, 1);",
			"insert into questao(descricao, tipo, id_questionario) values('E para governador?', 1, 1);",
			"insert into questao(descricao, tipo, id_questionario) values('Vírus são considerados seres vivos pelo fato de apresentarem sitema nervoso.', 1, 2);",
			
			"create table resposta(_id integer primary key autoincrement, descricao text not null, id_questao integer not null);",
			"insert into resposta(descricao, id_questao) values('Dilma', 1);",
			"insert into resposta(descricao, id_questao) values('José Serra', 1);",
			"insert into resposta(descricao, id_questao) values('Certo', 3);",
			"insert into resposta(descricao, id_questao) values('Errado', 3);",
			
			"create table coleta_questionario(_id integer primary key autoincrement, id_questionario integer not null, entrevistado text, data datetime not null);",
			"insert into coleta_questionario(id_questionario, entrevistado, data) values(1, 'Michael Phelps', 14/11/2009);",
			
			"create table coleta_resposta(_id integer primary key autoincrement, id_coleta_questionario integer not null, id_questao integer not null, id_resposta_dada integer, resposta_aberta text);",
			"insert into coleta_resposta(id_coleta_questionario, id_questao, id_resposta_dada) values(1, 1, 1);",
			"insert into coleta_resposta(id_coleta_questionario, id_questao, resposta_aberta) values(2, 3, 'biologia');"
			};

	private static final String NOME_BANCO = "and_collector";


	// Classe utilitaria para abrir, criar, e atualizar o banco de dados
	private SQLiteHelper dbHelper;

	// Cria o banco de dados com um script SQL
	public RepositorioQuestionarioScript(Context ctx) {
		// Criar utilizando um script SQL
		dbHelper = new SQLiteHelper(ctx, RepositorioQuestionarioScript.NOME_BANCO, RepositorioQuestionarioScript.VERSAO_BANCO,
				RepositorioQuestionarioScript.SCRIPT_DATABASE_CREATE, RepositorioQuestionarioScript.SCRIPT_DATABASE_DELETE);

		// abre o banco no modo escrita para poder alterar também
		database = dbHelper.getWritableDatabase();
	}

	@Override
	public void fechar() {
		super.fechar();
		if (dbHelper != null) {
			dbHelper.close();
		}
	}
}
