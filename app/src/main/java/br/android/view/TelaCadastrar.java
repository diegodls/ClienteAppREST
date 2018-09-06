package br.android.view;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import br.android.applivrossqlite.R;
import br.android.model.Aluno;

import static android.view.WindowManager.*;

public class TelaCadastrar extends AppCompatActivity implements View.OnClickListener{

    private EditText edtNome;
    private EditText edtCurso;


    private Button btnSalvar;
    private Button btnCancelar;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tela_cadastrar);

        edtNome = (EditText)findViewById(R.id.edtNome);
        edtCurso = (EditText)findViewById(R.id.edtCurso);


        btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnSalvar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        Intent it = getIntent();
        if (it != null){
            try{
                id = it.getIntExtra("id",0);
                Aluno aluno = null; //TO-DO: pesquisar dados do aluno pelo ID
                if (aluno != null){
                    edtNome.setText(aluno.getNome());
                    edtCurso.setText(aluno.getCurso());
                }

            }catch(Exception e){
                Log.e("ERRO", e.getMessage());
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCancelar){
            super.onBackPressed();
        }else if (v.getId() == R.id.btnSalvar){
            try {
                String titulo = edtNome.getText().toString();
                String autor = edtCurso.getText().toString();

                if ( id == 0){
                    id = -1;   //TO-DO: inserir um novo aluno
                }else{
                    id = -1;   //TO-DO: atualizar dados do aluno
                }

            }catch(Exception e){
                Log.e("ERRO", e.getMessage());
            }


            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Alunos App");
            dlg.setMessage("Operação realizada com sucesso!\nId do aluno = " + id);
            dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dlg.show();


        }
    }
}
