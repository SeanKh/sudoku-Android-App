package com.example.sudokusolver;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    GridView gridview;
    AssetManager assetManager;
    int puzzleLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assetManager = getAssets();
        gridview = (GridView) findViewById(R.id.gridView);


        try {
            InputStream inputStream = assetManager.open("empty.in");
            gridview.setAdapter(new TextAdapter(this, inputStream));
        } catch (IOException e) {
            Log.i("Yo", "OH NO");
        }

        Button mClickButton1 = (Button)findViewById(R.id.one);
        mClickButton1.setOnClickListener(this);
        Button mClickButton2 = (Button)findViewById(R.id.two);
        mClickButton2.setOnClickListener(this);
        Button mClickButton3 = (Button)findViewById(R.id.three);
        mClickButton3.setOnClickListener(this);
        Button mClickButton4 = (Button)findViewById(R.id.four);
        mClickButton4.setOnClickListener(this);
        Button mClickButton5 = (Button)findViewById(R.id.five);
        mClickButton5.setOnClickListener(this);
        Button mClickButton6 = (Button)findViewById(R.id.six);
        mClickButton6.setOnClickListener(this);
        Button mClickButton7 = (Button)findViewById(R.id.seven);
        mClickButton7.setOnClickListener(this);
        Button mClickButton8 = (Button)findViewById(R.id.eight);
        mClickButton8.setOnClickListener(this);
        Button mClickButton9 = (Button)findViewById(R.id.nine);
        mClickButton9.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void populateGrid(View view) {
        try {
            InputStream inputStream = assetManager.open("empty.in");
            gridview.setAdapter(new TextAdapter(this, inputStream));
            puzzleLevel = 0;
        } catch (IOException e) {
            Log.i("Yo", "OH NO");

        }



    }

    public Integer[][] make2d(Integer[] var){
        Integer[][] out = new Integer[9][9];
        int r=0,c=0;
        for(int n=0;n<var.length;n++){
            out[n/9][n%9]=var[n];
        }
        return out;
    }

    public void solvePuzzle(View view) {

        TextAdapter textAdapter = (TextAdapter) gridview.getAdapter();
        SudokuSolver sudokuSolver = new SudokuSolver();
        Integer[][] temp=make2d(textAdapter.grid);
        /*for(int n=0;n<temp.length;n++){
            for(int i=0;i<temp.length;i++) {

                if (temp[n][i])
            }
        }*/

        textAdapter.grid2d=temp;

        boolean solved=sudokuSolver.solve(0, 0, textAdapter.grid2d);
        int x = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextView textView = (TextView) gridview.getChildAt(x);
                textView.setText(String.valueOf(textAdapter.grid2d[i][j]));
                x++;
            }
        }

        checkSolution(view);
    }

    public void checkSolution(View view) {
        boolean win = true;

        int[] userArray = new int[81];
        int[] solvedArray = new int[81];

        TextAdapter textAdapter = (TextAdapter) gridview.getAdapter();

        for (int i = 0; i < 81; i++) {
            EditText textView = (EditText) gridview.getChildAt(i);
            String text = String.valueOf(textView.getText());
            if (text.equals("")){
                userArray[i] = 0;
            } else {
                userArray[i] = Integer.valueOf(text);
            }
        }

        SudokuSolver sudokuSolver = new SudokuSolver();

        sudokuSolver.solve(0, 0, textAdapter.grid2d);
        int x = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                solvedArray[x] = textAdapter.grid2d[i][j];
                x++;
            }
        }

        for (int i = 0; i < 81; i++) {
            EditText textView = (EditText) gridview.getChildAt(i);

            if(textView.isEnabled()) {
                if (solvedArray[i] != userArray[i]) {
                    textView.setTextColor(Color.RED);
                    win = false;
                } else {
                    textView.setTextColor(Color.BLACK);
                }
            }
        }


        if(win) {
            Toast.makeText(view.getContext(), "You Win!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(view.getContext(), "Wrong!!",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        TextAdapter textAdapter = (TextAdapter) gridview.getAdapter();

        switch (v.getId()){
            case R.id.one:
                textAdapter.setClickedButton(1);
                break;
            case R.id.two:
                textAdapter.setClickedButton(2);

                break;
            case R.id.three:
                textAdapter.setClickedButton(3);

                break;
            case R.id.four:
                textAdapter.setClickedButton(4);

                break;
            case R.id.five:
                textAdapter.setClickedButton(5);

                break;
            case R.id.six:
                textAdapter.setClickedButton(6);

                break;
            case R.id.seven:
                textAdapter.setClickedButton(7);

                break;
            case R.id.eight:
                textAdapter.setClickedButton(8);

                break;
            case R.id.nine:
                textAdapter.setClickedButton(9);

                break;
            default:
                textAdapter.setClickedButton(0);
                break;
        }
        textAdapter.updateGrid();

    }
}

