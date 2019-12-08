package com.example.grabgrid.Handler;

import com.example.grabgrid.Entities.Maze;
import com.example.grabgrid.GrabGridActivity;
import com.example.grabgrid.MazeGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class MazeFileHandler {


    public void save(Maze maze) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerFor(maze.getClass());
        String json;
        try {
            json = objectWriter.writeValueAsString(maze);
            FileOutputStream fOut = GrabGridActivity.activity.getApplicationContext().openFileOutput("samplefile.txt",
                    MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            // Write the string to the file
            osw.write(json);

            /* ensure that everything is
             * really written out and close */
            osw.flush();
            osw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Maze load() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader objectReader = objectMapper.readerFor(Maze.class);
        String json;
        try {
            FileInputStream fileInputStream = GrabGridActivity.activity.getApplicationContext().openFileInput("samplefile.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            char[] inputBuffer = new char[100000000];

            // Fill the Buffer with data from the file
            inputStreamReader.read(inputBuffer);

            // Transform the chars to a String
            json = new String(inputBuffer);

            Maze maze = objectReader.readValue(json);
            return maze;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
