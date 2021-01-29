package com.carrabba.tris;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class GiocoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private Button logout;

    private boolean turnoGiocatore1 = true;

    private int contatoreRound;

    private int puntiGiocatore1;
    private int puntiGiocatore2;

    private TextView txtPlayer1;
    private TextView txtPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gioco);

        txtPlayer1 = findViewById(R.id.txtGiocatore1);
        txtPlayer2 = findViewById(R.id.txtGiocatore2);
        logout = findViewById(R.id.btnLogout);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btnCampo_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.btnReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (turnoGiocatore1) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        contatoreRound++;

        if (verificaMatch()) {
            if (turnoGiocatore1) {
                giocatore1Win();
            } else {
                giocatore2Win();
            }
        } else if (contatoreRound == 9) {
            pareggio();
        } else {
            turnoGiocatore1 = !turnoGiocatore1;
        }
    }

    private boolean verificaMatch()
    {
        String[][] campo = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                campo[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i=0; i<3; i++) {
            if (campo[i][0].equals(campo[i][1]) && campo[i][0].equals(campo[i][2]) && !campo[i][0].equals("")) {
                return true;
            }
        }

        for (int i=0; i<3; i++) {
            if (campo[0][i].equals(campo[1][i]) && campo[0][i].equals(campo[2][i]) && !campo[0][i].equals("")) {
                return true;
            }
        }

        if (campo[0][0].equals(campo[1][1]) && campo[0][0].equals(campo[2][2]) && !campo[0][0].equals("")) {
            return true;
        }

        if (campo[0][2].equals(campo[1][1]) && campo[0][2].equals(campo[2][0]) && !campo[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void giocatore1Win() {
        puntiGiocatore1++;
        Toast.makeText(GiocoActivity.this, "Giocatore 1 ha vinto!", Toast.LENGTH_SHORT).show();
        aggiornamentoPunti();
        resetBoard();
    }

    private void giocatore2Win() {
        puntiGiocatore2++;
        Toast.makeText(GiocoActivity.this, "Giocatore 2 ha vinto!", Toast.LENGTH_SHORT).show();
        aggiornamentoPunti();
        resetBoard();
    }

    private void pareggio() {
        Toast.makeText(GiocoActivity.this, "Pareggio!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void aggiornamentoPunti() {
        txtPlayer1.setText("Giocatore 1: " + puntiGiocatore1);
        txtPlayer2.setText("Giocatore 2: " + puntiGiocatore2);
    }

    private void resetBoard() {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j].setText("");
            }
        }

        contatoreRound = 0;
        turnoGiocatore1 = true;
    }

    private void resetGame() {
        puntiGiocatore1 = 0;
        puntiGiocatore2 = 0;
        aggiornamentoPunti();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@Nullable Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("contatoreRound", contatoreRound);
        outState.putInt("Puntigiocatore1", puntiGiocatore1);
        outState.putInt("puntiGiocatore2", puntiGiocatore2);
        outState.putBoolean("turnoGiocatore1", turnoGiocatore1);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        contatoreRound = savedInstanceState.getInt("contatoreRound");
        puntiGiocatore1 = savedInstanceState.getInt("puntiGiocatore1");
        puntiGiocatore2 = savedInstanceState.getInt("puntiGiocatore2");
        turnoGiocatore1 = savedInstanceState.getBoolean("turnoGiocatore1");
    }

    public void esci(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}