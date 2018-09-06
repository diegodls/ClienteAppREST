package br.android.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.android.applivrossqlite.R;
import br.android.model.Aluno;

public class TelaPrincipal extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView lstDados;
    private String resposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        lstDados = (ListView) findViewById(R.id.lstDados);
        lstDados.setLongClickable(true);
        lstDados.setOnItemClickListener(this);
        lstDados.setOnItemLongClickListener(this);

        Button btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TelaCadastrar.class));
            }
        });

        preencherListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencherListView();
    }

    private void preencherListView() {

        new ListarAlunos().execute("");
        List<Aluno> lista = new Gson().fromJson(resposta,
                new TypeToken<ArrayList<Aluno>>(){}.getType());

        AdapterListView adp = new AdapterListView(this, lista);
        lstDados.setAdapter(adp);
        adp.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Aluno aluno = (Aluno) parent.getItemAtPosition(position);
        Intent it = new Intent(getApplicationContext(), TelaCadastrar.class);
        it.putExtra("id", aluno.getId());
        startActivity(it);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Aluno aluno = (Aluno) parent.getItemAtPosition(position);

        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("Alunos App");
        dlg.setMessage("Tem certeza que deseja remover o aluno " + aluno.getNome() + "?");
        dlg.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TO-DO: chamar operação para deletar aluno
                preencherListView();
            }
        });
        dlg.setNegativeButton("NÃO", null);
        dlg.show();
        return true;
    }


    //
    // CLASSES PARA UTILIZAÇÃO COM RECURSOS RESTful
    //

    private class ListarAlunos extends AsyncTask<String, String, String> {
        private ProgressDialog dlg;


        @Override
        protected void onPreExecute() {
            dlg = ProgressDialog.show(TelaPrincipal.this, "Aguarde...", "Conectando ao servidor...");
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                URL url = new URL("http://127.0.0.1:8080/aluno/listar");
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                if (con.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    resposta = sb.toString();
                    return sb.toString();
                }

            } catch (Exception e) {
                Log.e("ERRO", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dlg.dismiss();
        }
    }


}