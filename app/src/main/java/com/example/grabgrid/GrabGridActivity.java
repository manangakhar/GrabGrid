package com.example.grabgrid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import com.example.grabgrid.Entities.Box;
import com.example.grabgrid.Entities.Coordinate;
import com.example.grabgrid.Entities.Maze;
import com.example.grabgrid.Entities.Reward;
import com.example.grabgrid.Entities.Size;
import com.example.grabgrid.Enums.BoxType;
import com.example.grabgrid.Enums.MazeType;
import com.example.grabgrid.Enums.RewardType;
import com.example.grabgrid.Event.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

public class GrabGridActivity extends AppCompatActivity {

    private static float factor;
    private static final int BOX_DPI = 45;
    private static final int ROWS = 9;
    private static final int COLS = 9;
    public Grid grid;
    private ImageOnClickListener imageOnClickListener = new ImageOnClickListener();
    private Event lastEvent;
    private Maze maze;
    boolean waitForClick = false;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        factor = getResources().getDisplayMetrics().density;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_grid);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final LinearLayout linearLayoutGrid = findViewById(R.id.gridId);
        linearLayoutGrid.invalidate();
        maze = MazeGenerator.getOrCreateMaze(new Size(ROWS, COLS), MazeType.SIMPLE);
        grid = new Grid(maze);
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
                List<Coordinate> allReachableCoordinates = maze.getAllReachableCoordinates();
                for (Coordinate coordinate : allReachableCoordinates)
                    highlightPosition(new Position(coordinate), true);
                waitForClick = true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void highlightPosition(Position position, boolean highlight) {
        GridSpot gridSpot = grid.getGridSpot(position);
        gridSpot.getImageView().setColorFilter(highlight ? Color.CYAN : Color.TRANSPARENT);
    }

    private void modifyPosition(Position position, BoxType boxType, Reward reward) {
        GridSpot gridSpot = grid.getGridSpot(position);
        gridSpot.setBoxType(boxType);
        gridSpot.setReward(reward);
        setImageForBox(gridSpot.getImageView(), gridSpot);
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
                ImageView imageView = new ImageView(getApplicationContext(), new Position(row, col));
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) (BOX_DPI * factor), (int) (BOX_DPI * factor));
                imageView.setLayoutParams(layoutParams);
                GridSpot gridSpot = inputGrid.getGridSpot(new Position(row, col));
                setImageForBox(imageView, gridSpot);
                imageView.setOnClickListener(imageOnClickListener);
                // set the image grid
                gridSpot.setImageView(imageView);
                // add column to row
                linearLayoutRow.addView(imageView);
            }
            // add row to main layout
            linearLayoutGrid.addView(linearLayoutRow);
        }
    }

    public class ImageOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (!(v instanceof ImageView)) {
                throw new UnsupportedOperationException("unexpected click");
            }
            ImageView imageView = (ImageView) v;
            GridSpot gridSpot = grid.getGridSpot(imageView.getPosition());
            Toast.makeText(getApplicationContext(), ((ImageView) v).getPosition().toString(), Toast.LENGTH_SHORT).show();
            if (waitForClick) {
                List<Coordinate> neighbors = maze.getAllReachableCoordinates();
                if (neighbors.contains(new Coordinate(imageView.getPosition().getX(), imageView.getPosition().getY()))) {
                    for (Coordinate coordinate : neighbors) {
                        highlightPosition(new Position(coordinate), false);
                    }
                    waitForClick = false;
                    Reward reward = maze.handleClickEvent(new Coordinate(imageView.getPosition().getX(), imageView.getPosition().getY()));
                    String rewardMessage = generateMessageFromReward(reward);
                    getDialog(rewardMessage).show();
                    modifyPosition(imageView.getPosition(), BoxType.VISITED, reward);
                    if (maze.getAllReachableCoordinates().isEmpty())
                        getDialog("congrats, you finished the maze").show();
                    return;
                }
            }
            if (null != gridSpot.getReward())
                Toast.makeText(getApplicationContext(), generateMessageFromReward(gridSpot.getReward()), Toast.LENGTH_LONG).show();
        }
    }

    private String generateMessageFromReward(Reward reward) {
        return RewardType.NONE == reward.getRewardType() ? "Sorry, no reward points this time" :
                String.format("Congrats you won %d points for %s\nThese Rewards can be claimed under MyRewards.",
                        reward.getRewardPoints(), reward.getRewardType().toString().toLowerCase());
    }

    private Dialog getDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congrats");
        builder.setMessage(message);
        builder.setPositiveButton("Thank you", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Really!?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private void setImageForBox(ImageView imageView, GridSpot gridSpot) {
        int drawableId = 0;
        switch (gridSpot.getBoxType()) {
            case START:
                drawableId = R.drawable.tile018;
                imageView.setImageResource(drawableId);
                return;
            case UNVISITED_END:
                drawableId = R.drawable.unvisited;
                imageView.setImageResource(drawableId);
                return;
            case UNVISITED:
                drawableId = R.drawable.gift;
                imageView.setImageResource(drawableId);
                return;
            case UP:
                drawableId = R.drawable.up_arrow;
                imageView.setImageResource(drawableId);
                imageView.setPadding(25, 25, 25, 25);
                return;
            case DOWN:
                drawableId = R.drawable.down_arrow;
                imageView.setPadding(25, 25, 25, 25);
                imageView.setImageResource(drawableId);
                return;
            case LEFT:
                drawableId = R.drawable.left_arrow;
                imageView.setPadding(25, 25, 25, 25);
                imageView.setImageResource(drawableId);
                return;
            case RIGHT:
                drawableId = R.drawable.right_arrow;
                imageView.setImageResource(drawableId);
                imageView.setPadding(25, 25, 25, 25);
                return;
            case VISITED:
                if (RewardType.NONE == gridSpot.getReward().getRewardType())
                    imageView.setImageResource(R.drawable.circle);
                else
                    imageView.setImageResource(R.drawable.reward);
                return;
            case PAY:
                drawableId = R.drawable.pay;
                imageView.setImageResource(drawableId);
                return;
            case TRANSPORT:
                drawableId = R.drawable.transport;
                imageView.setImageResource(drawableId);
                return;
            case FOOD:
                drawableId = R.drawable.food;
                imageView.setImageResource(drawableId);
                return;
            case MOVIE:
                drawableId = R.drawable.movie;
                imageView.setImageResource(drawableId);
                return;
            case OPTION:
                drawableId = R.drawable.option;
                imageView.setImageResource(drawableId);
                return;
            case BLANK:
                drawableId = 0;
                imageView.setImageResource(drawableId);
                return;
            default:
                drawableId = R.mipmap.ic_launcher;
                imageView.setImageResource(drawableId);
                return;
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

        public Grid(Maze maze) {
            this.rows = maze.getSize().getRows();
            this.cols = maze.getSize().getColumns();
            gridSpots = new GridSpot[rows][cols];
            Box[][] boxes = maze.getBoxes();
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    GridSpot gridSpot = new GridSpot();
                    gridSpot.setReward(boxes[row][col].getReward());
                    gridSpot.setBoxType(boxes[row][col].getBoxType());
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
        private Reward reward;
    }

    @Data
    @AllArgsConstructor
    public class Position {
        private final int x;
        private final int y;

        public Position(Coordinate coordinate) {
            this.x = coordinate.getRow();
            this.y = coordinate.getColumn();
        }
    }

    public class ImageView extends AppCompatImageView {

        private final Position position;

        public ImageView(Context context, Position position) {
            super(context);
            this.position = position;
        }

        public Position getPosition() {
            return position;
        }
    }

}
