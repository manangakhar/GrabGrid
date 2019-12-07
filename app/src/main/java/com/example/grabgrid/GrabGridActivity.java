package com.example.grabgrid;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import lombok.AllArgsConstructor;
import lombok.Data;

public class GrabGridActivity extends AppCompatActivity {

    private static float factor;
    private static final int BOX_DPI = 45;
    private static final int ROWS = 9;
    private static final int COLS = 9;
    public static Grid grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        factor = getResources().getDisplayMetrics().density;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_grid);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final LinearLayout linearLayoutGrid = findViewById(R.id.gridId);
        linearLayoutGrid.invalidate();
        grid = new Grid();
        renderGrid(grid);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                ImageView imageView = imageGrid[0][0];
//                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
//                animation.setRepeatCount(Animation.INFINITE);
//                imageView.setAnimation(animation);
//                imageView.startAnimation(animation);
//                ImageView viewById = (ImageView) findViewById(R.id.sonic);
//                AnimationDrawable d = (AnimationDrawable) viewById.getDrawable();
//                d.start();
                modifyPosition(new Position(2, 0), BoxType.VISITED);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void modifyPosition(Position position, BoxType boxType) {
        GridSpot gridSpot = grid.getGridSpot(position);
        gridSpot.setBoxType(boxType);
        gridSpot.getImageView().setImageResource(getImageForBox(gridSpot));
    }

    public void renderGrid(Grid inputGrid) {
        final LinearLayout linearLayoutGrid = findViewById(R.id.gridId);
        linearLayoutGrid.removeAllViews();

        for (int row = 0; row < ROWS; row++) {
            // create row
            LinearLayout linearLayoutRow = new LinearLayout(getApplicationContext());
            linearLayoutRow.setOrientation(LinearLayout.HORIZONTAL);
            linearLayoutRow.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // create each column in row
            for (int col = 0; col < COLS; col++) {
                // create image per box
                ImageView imageView = new ImageView(getApplicationContext());
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) (BOX_DPI * factor), (int) (BOX_DPI * factor));
                imageView.setLayoutParams(layoutParams);
                GridSpot gridSpot = inputGrid.getGridSpot(new Position(row, col));
                imageView.setImageResource(getImageForBox(gridSpot));
                // set the image grid
                gridSpot.setImageView(imageView);
                // add column to row
                linearLayoutRow.addView(imageView);
            }
            // add row to main layout
            linearLayoutGrid.addView(linearLayoutRow);
        }
    }

    private int getImageForBox(GridSpot gridSpot) {
        switch (gridSpot.getBoxType()) {
            case START:
                return R.drawable.tile018;
            case UNVISITED_END:
                return R.drawable.unvisited;
            case UNVISITED:
                return R.drawable.unvisited;
            case VISITED:
                return R.drawable.visited;
            case UP:
                return R.drawable.arrow_up;
            case DOWN:
                return R.drawable.arrow_down;
            case LEFT:
                return R.drawable.arrow_left;
            case RIGHT:
                return R.drawable.arrow_right;
            case PAY:
                return R.drawable.pay;
            case TRANSPORT:
                return R.drawable.transport;
            case FOOD:
                return R.drawable.food;
            case MOVIE:
                return R.drawable.movie;
            case OPTION:
                return R.drawable.option;
            case BLANK:
                return 0;
            default:
                return R.mipmap.ic_launcher;
        }
    }

    public static class Grid {
        private final GridSpot[][] gridSpots;
        private static final int GRID_LENGTH = 9;
        private final int rows;
        private final int cols;

        public Grid() {
            this.rows = GRID_LENGTH;
            this.cols = GRID_LENGTH;
            gridSpots = new GridSpot[rows][cols];
            BoxType[][] initialGrid = Utils.initialGrid;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    GridSpot gridSpot = new GridSpot();
                    gridSpot.setBoxType(initialGrid[row][col]);
                    gridSpots[row][col] = gridSpot;
                }
            }
        }

        public GridSpot getGridSpot(Position position) {
            return gridSpots[position.x][position.y];
        }
    }

    @Data
    public static class GridSpot {
        private BoxType boxType;
        private ImageView imageView;
    }

    @Data
    @AllArgsConstructor
    public class Position {
        private final int x;
        private final int y;
    }

}
