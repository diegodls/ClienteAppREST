package br.android.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.android.applivrossqlite.R;
import br.android.model.Aluno;

public class AdapterListView extends BaseAdapter {

    private List<Aluno> lista;
    private LayoutInflater layout;

    public AdapterListView(Context contexto, List<Aluno> lista){
        this.layout = LayoutInflater.from(contexto);
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return  (lista!=null)?lista.size():0;
    }

    @Override
    public Object getItem(int position) {
        return (lista!=null)?lista.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return ((Aluno)lista.get(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = (Aluno)lista.get(position);
        convertView = layout.inflate(R.layout.item_lista, null);

        //Associar os atributos do objeto aos elementos da lista
        TextView tv1 = (TextView)convertView.findViewById(R.id.txtIdNome);
        tv1.setText(String.format("%s (%d)", aluno.getNome(), aluno.getId()));

        TextView tv2 = (TextView)convertView.findViewById(R.id.txtCurso);
        tv2.setText(aluno.getCurso());

        return convertView;
    }
}
