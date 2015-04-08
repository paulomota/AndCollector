package br.cic.unb.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.cic.unb.android.R;
import br.cic.unb.android.dominio.Questao;

/**
 * Adapter customizado que exibe o layout definido em questao_linha_tabela.xml
 * As imagens sao exibidas no widget ImageView
 * 
 * @author paulomota e jeffersonmotta
 * 
 */
public class QuestaoListAdapter extends BaseAdapter {
	private Context context;
	private List<Questao> lista;

	public QuestaoListAdapter(Context context, List<Questao> lista) {
		this.context = context;
		this.lista = lista;
	}

	public int getCount() {
		return lista.size();
	}

	public Object getItem(int position) {
		return lista.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Questao questao = lista.get(position);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.linha_tabela_questao, null);

		TextView posicao = (TextView) view.findViewById(R.id.posicao);
		posicao.setText(String.valueOf(position+1)+")");
		
		TextView descricao = (TextView) view.findViewById(R.id.descricao);
		descricao.setText(questao.getDescricao());

		return view;
	}
}