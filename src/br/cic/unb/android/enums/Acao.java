package br.cic.unb.android.enums;

public enum Acao {

	SALVAR(1, "Salvar"),
	PROCURAR(2, "Procurar"),
	EXCLUIR(3, "Excluir");
	
	private int codigo;
	private String descricao;
	
	Acao(int codigo, String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}

	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
