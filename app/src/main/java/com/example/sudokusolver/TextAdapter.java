package com.example.sudokusolver;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextAdapter extends BaseAdapter {
    private Context mContext;
    public Integer[] grid = new Integer[81];
    public Integer[][] grid2d = new Integer[9][9];

    public TextView getTextViewNeeded() {
        return textViewNeeded;
    }

    public void setTextViewNeeded(TextView textViewNeeded) {
        this.textViewNeeded = textViewNeeded;
    }

    public TextView textViewNeeded;

    public int getClickedButton() {
        return clickedButton;
    }

    public void setClickedButton(int clickedButton) {
        this.clickedButton = clickedButton;
    }

    public int clickedButton=10;

    public int getGridCellClicked() {
        return gridCellClicked;
    }

    public void setGridCellClicked(int gridCellClicked) {
        this.gridCellClicked = gridCellClicked;
    }

    public int gridCellClicked=100;
    public TextAdapter(Context c, InputStream inputStream) throws IOException {
        mContext = c;

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line;


        int i = 0;
        int row = 0, col = 0;
        while((line = in.readLine()) != null){
            String[] lineArray = line.split(" ");
            for (String element : lineArray) {
                if (element.equals("*")) {
                    grid[i] = 0;
                    if (col % 9 == 0 && col != 0) {
                        col = 0;
                        row++;
                    }
                    grid2d[row][col] = 0;
                } else {
                    if (col % 9 == 0 && col != 0) {
                        col = 0;
                        row++;
                    }
                    grid[i] = Integer.valueOf(element);
                    grid2d[row][col] = Integer.valueOf(element);
                }
                col++;
                i++;
            }
        }
    }

    public int getCount() {
        return 81;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final TextView textView;


         if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = new EditText(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            textView.setPadding(0, 0, 0, 0);
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

            GradientDrawable gd = new GradientDrawable();
            gd.setStroke(1, 0xFF000000);
            textView.setBackgroundDrawable(gd);

            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(1);
            textView.setFilters(FilterArray);


        } else {
            textView = (EditText) convertView;
        }
        if (grid[position] != 0) {
            textView.setText(String.valueOf(grid[position]));
            textView.setEnabled(false);

        } else {

            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                        setGridCellClicked(position);
                        long color = 0xffffffffL & textView.getCurrentTextColor();
                        Toast.makeText(view.getContext(), Long.toHexString(color),
                                Toast.LENGTH_LONG).show();
                        textView.getBackground().setAlpha(100);
                        textView.setBackgroundColor(Color.parseColor("#a4c639"));
                        //textView.setBackgroundResource(R.color.colorAccent);


                        setTextViewNeeded(textView);
                        updateGrid();
                    }
                    return true;
                }
            });
        }

        return textView;
    }

    @SuppressWarnings("ResourceAsColor")
    public void updateGrid(){
        if(getClickedButton()!=10 && getGridCellClicked()!=100){
            grid[getGridCellClicked()]=getClickedButton();
            grid2d=MainActivity.make2d(grid);
            if(getClickedButton()==0){
                getTextViewNeeded().setText("");
            }else  getTextViewNeeded().setText(String.valueOf(grid[getGridCellClicked()]));
            setClickedButton(10);
            //getTextViewNeeded().setBackgroundColor(Color.TRANSPARENT);

        }
    }
}

