package br.cic.unb.android.facade;

import java.util.List;

import android.content.Context;
import br.cic.unb.android.banco.RepositorioQuestionario;
import br.cic.unb.android.banco.RepositorioQuestionarioScript;
import br.cic.unb.android.dominio.Questionario;

public class QuestionarioFacade {

	public static RepositorioQuestionario repositorio;
	
	public QuestionarioFacade(Context ctx){
		repositorio = new RepositorioQuestionarioScript(ctx);
	}

	public void salvar(Questionario questionario) {
		repositorio.salvar(questionario);
	}
	
	public List<Questionario> listarQuestionarios() {
		return repositorio.listarQuestionarios();
	}

	public Questionario buscarQuestionarioPorNome(String nomeCarro) {
		return repositorio.buscarQuestionarioPorNome(nomeCarro);
	}

	public Questionario buscarQuestionario(long id) {
		return repositorio.buscarQuestionario(id);
	}

	public void deletar(long id) {
		repositorio.deletar(id);
	}

	public int recuperarQtdQuestoes(long id) {
		return repositorio.recuperarQtdQuestoes(id);
	}
	
	public void fechar() {
		repositorio.fechar();
	}
	
}
