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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
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
        textAdapter.grid2d=temp;
        sudokuSolver.solve(0, 0, textAdapter.grid2d);
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
}

