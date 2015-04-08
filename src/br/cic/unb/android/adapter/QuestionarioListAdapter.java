package br.cic.unb.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.cic.unb.android.R;
import br.cic.unb.android.dominio.Questionario;

/**
 * Adapter customizado que exibe o layout definido em smile_row.xml
 * 
 * As imagens são exibidas no widget ImageView
 * 
 * @author ricardo
 * 
 */
public class QuestionarioListAdapter extends BaseAdapter {
	private Context context;
	private List<Questionario> lista;

	public QuestionarioListAdapter(Context context, List<Questionario> lista) {
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
		// Recupera o Carro da posicao atual
		Questionario c = lista.get(position);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.linha_tabela_questionario, null);

		// Atualiza o valor do TextView
		TextView nome = (TextView) view.findViewById(R.id.nome);
		nome.setText(c.getNome());

		TextView descricao = (TextView) view.findViewById(R.id.descricao);
		descricao.setText(c.getDescricao());

		return view;
	}
}