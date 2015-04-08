package br.cic.unb.android.enums;

public enum Tabela {

	QUESTIONARIO("questionario"),
	QUESTAO("questao"),
	RESPOSTA("resposta"),
	COLETA_QUESTIONARIO("coleta_questionario"),
	COLETA_RESPOSTA("coleta_resposta");
	
	private String nome;
	
	Tabela(String nome){
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
