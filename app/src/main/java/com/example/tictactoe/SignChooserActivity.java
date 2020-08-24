package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignChooserActivity extends AppCompatActivity {

    @BindView(R.id.X)
    TextView symbolX;

    @BindView(R.id.O)
    TextView symbolO;

    @BindView(R.id.next)
    Button next;

    static String selected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_chooser);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
        next.setEnabled(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        symbolX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                symbolO.setBackgroundResource(R.color.goodColor);
                view.setBackgroundResource(R.color.greyedOut);
                next.setEnabled(true);
                next.setBackgroundResource(R.color.colorAccent);
                selected = "X";
            }
        });

        symbolO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                symbolX.setBackgroundResource(R.color.goodColor);
                view.setBackgroundResource(R.color.greyedOut);
                next.setEnabled(true);
                next.setBackgroundResource(R.color.colorAccent);
                selected = "O";
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignChooserActivity.this, MainActivity.class);
                intent.putExtra("playerSymbol", selected);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(SignChooserActivity.this);
        builder1.setMessage("Do you want to exit?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finishAffinity();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}