package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.topFirst)
    TextView topFirst;

    @BindView(R.id.topSecond)
    TextView topSecond;

    @BindView(R.id.topThird)
    TextView topThird;

    @BindView(R.id.secondLeft)
    TextView secondLeft;

    @BindView(R.id.secondMiddle)
    TextView secondMiddle;

    @BindView(R.id.secondRight)
    TextView secondRight;

    @BindView(R.id.thirdLeft)
    TextView thirdLeft;

    @BindView(R.id.thirdMiddle)
    TextView thirdMiddle;

    @BindView(R.id.thirdRight)
    TextView thirdRight;

    @BindView(R.id.winner)
    TextView winner;

    @BindView(R.id.tryAgain)
    TextView tryAgain;

    @BindView(R.id.grid)
    GridLayout layout;

    static String playerSymbol;
    static String botSymbol;
    int clicks = 1;
    Map<String, String> winCombinations = new HashMap<>();
    Map<String, TextView> viewMap = new HashMap<>();
    List<TextView> views = new ArrayList<>();
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
        setUpWinCombinations();
        bundle = getIntent().getExtras();

        if (null != bundle) {
            playerSymbol = bundle.getString("playerSymbol");
        }

        if (Objects.requireNonNull(playerSymbol).equalsIgnoreCase("X")) {
            botSymbol = "O";
        } else {
            botSymbol = "X";
        }

        topFirst.setOnClickListener(new PositionClickListener());
        topFirst.setText("");
        topSecond.setOnClickListener(new PositionClickListener());
        topSecond.setText("");
        topThird.setOnClickListener(new PositionClickListener());
        topThird.setText("");

        secondLeft.setOnClickListener(new PositionClickListener());
        secondLeft.setText("");
        secondMiddle.setOnClickListener(new PositionClickListener());
        secondMiddle.setText("");
        secondRight.setOnClickListener(new PositionClickListener());
        secondRight.setText("");

        thirdLeft.setOnClickListener(new PositionClickListener());
        thirdLeft.setText("");
        thirdMiddle.setOnClickListener(new PositionClickListener());
        thirdMiddle.setText("");
        thirdRight.setOnClickListener(new PositionClickListener());
        thirdRight.setText("");

        views.add(topFirst);
        views.add(topSecond);
        views.add(topThird);
        views.add(secondLeft);
        views.add(secondMiddle);
        views.add(secondRight);
        views.add(thirdLeft);
        views.add(thirdMiddle);
        views.add(thirdRight);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (TextView textView : views) {
                    textView.setText("");
                    textView.setClickable(true);
                    clicks = 1;
                    textView.setBackgroundResource(R.color.goodColor);
                    winner.setVisibility(View.INVISIBLE);
                    tryAgain.setVisibility(View.INVISIBLE);
                    layout.setBackgroundResource(R.color.goodColor);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage("Do you want to exit?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        bundle.clear();
                        finish();
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

    private class PositionClickListener implements View.OnClickListener {

        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View view) {
            TextView viewID = ((TextView) view);
            winner.setVisibility(View.INVISIBLE);
            if (viewID.getText() == "") {
                if (clicks % 2 == 0) {
                    viewID.setText(botSymbol);
                    viewID.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    viewID.setText(playerSymbol);
                    viewID.setTextColor(getResources().getColor(R.color.X_color));
                }

                String idName = view.getResources().getResourceName(view.getId()).substring(25);
                viewMap.put(idName, viewID);
                String combinations = winCombinations.get(idName);
                String[] allCombinations = Objects.requireNonNull(combinations).split("@");
                for (String allCombination : allCombinations) {
                    String[] subParts = allCombination.split(",");
                    TextView view1 = viewMap.get(subParts[0]);
                    TextView view2 = viewMap.get(subParts[1]);
                    if (null != view1 && null != view2) {
                        if (view1.getText().toString().equalsIgnoreCase(viewID.getText().toString()) && view2.getText().toString().equalsIgnoreCase(viewID.getText().toString())) {
                            viewID.setTextColor(getResources().getColor(R.color.colorPrimary));
                            view1.setTextColor(getResources().getColor(R.color.colorPrimary));
                            view2.setTextColor(getResources().getColor(R.color.colorPrimary));

                            winner.setText(R.string.we_have_a_winner);
                            winner.setVisibility(View.VISIBLE);
                            winner.setBackgroundResource(R.color.X_color);
                            tryAgain.setVisibility(View.VISIBLE);
                            clicks = 1;
                            for (TextView textView : views) {
                                textView.setClickable(false);
                            }
                        }
                    }
                }
                clicks += 1;
                System.out.println(clicks);
                if (clicks == 10) {
                    winner.setText("Oops! That's a draw...");
                    winner.setBackgroundResource(R.color.colorAccent);
                    winner.setVisibility(View.VISIBLE);
                    tryAgain.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void setUpWinCombinations() {
        winCombinations.put("topFirst", "topSecond,topThird@secondLeft,thirdLeft@secondMiddle,thirdRight");
        winCombinations.put("topSecond", "topFirst,topThird@secondMiddle,ThirdMiddle");
        winCombinations.put("topThird", "topSecond,topFirst@secondMiddle,thirdLeft@secondRight,thirdRight");
        winCombinations.put("secondLeft", "topFirst,thirdLeft@secondMiddle,secondRight");
        winCombinations.put("secondMiddle", "topSecond,thirdMiddle@secondLeft,secondRight@topFirst,thirdRight");
        winCombinations.put("secondRight", "topThird,thirdRight@secondLeft,secondMiddle");
        winCombinations.put("thirdLeft", "topFirst,secondLeft@secondMiddle,topThird@thirdMiddle,thirdRight");
        winCombinations.put("thirdMiddle", "thirdLeft,thirdRight@secondMiddle,topSecond");
        winCombinations.put("thirdRight", "topFirst,secondMiddle@secondRight,topThird@thirdLeft,thirdMiddle");
    }
}