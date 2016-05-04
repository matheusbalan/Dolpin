package es.esy.dolpin.dolpin;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.SQLException;

import database.MeuPreparedStatement;
import database.dbo.Monitor;
import database.dao.Monitores;
import database.exception.MonitorNotFoundException;

public class LoginActivity extends AppCompatActivity {
    private MeuPreparedStatement conexao;
    private AlertDialog.Builder dlgAlert;
    private EditText txtEmail, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dlgAlert = new AlertDialog.Builder(this);

        try {
            conexao = new MeuPreparedStatement("", "", "", "");

            txtEmail = (EditText) findViewById(R.id.txtEmail);
            txtPassword = (EditText) findViewById(R.id.txtPassword);
        }
        catch (SQLException e) {
            alertarErroNaConexao();
        }
        catch (ClassNotFoundException ex) {
            alertarErroNaConexao();
        }
    }

    private void alertarErroNaConexao() {
        dlgAlert.setTitle("Erro de conexão");
        dlgAlert.setMessage("Falha na conexão com o servidor");

        dlgAlert.setPositiveButton("Ok", null);
        dlgAlert.setCancelable(true);

        dlgAlert.create().show();
    }

    private void alertarErroNoLogin() {
        dlgAlert.setTitle("Login não efetuado");
        dlgAlert.setMessage("E-mail ou senha incorreta");

        dlgAlert.setPositiveButton("Ok", null);
        dlgAlert.setCancelable(true);

        dlgAlert.create().show();
    }

    public void logar(View v) {
        if (conexao != null) {
            try {
                String email, senha;

                email = txtEmail.getText().toString();
                senha = txtPassword.getText().toString();

                Monitor monitor = new Monitor(null, null, email, senha);
                Monitores monitores = new Monitores(conexao);
                monitores.logar(monitor); // Se não conseguir logar, lança exceção

                // Abre a página principal
            } catch (SQLException e) {
                alertarErroNaConexao();
            } catch (MonitorNotFoundException ex) {
                alertarErroNoLogin();
            }
        }
        else
            alertarErroNaConexao();
    }

    public void abrirCadastro(View v) {
        Intent signupActivity = new Intent(this, SignupActivity.class);
        startActivity(signupActivity);
    }
}