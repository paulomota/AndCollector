package br.cic.unb.android.enums;

public enum TipoQuestao {

	SIM_OU_NAO(1, "Sim ou Não"),
	MULTIPLA_ESCOLHA(2, "Mutipla Escolha"),
	ABERTA(3, "Aberta");
	
	private int codigo;
	private String descricao;
	
	TipoQuestao(int codigo, String descricao){
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
	
	public String getDescricao(TipoQuestao tipoQuestao){
		return tipoQuestao.getDescricao();
	}

	public static int getSize(){
		return TipoQuestao.values().length;
	}
	
	public static TipoQuestao newInstance(int codigo){
		switch(codigo){
			case 1:
				return SIM_OU_NAO;
			case 2:
				return MULTIPLA_ESCOLHA;
			case 3:
				return ABERTA;
			default:
				return null;
		}
	}
}
