package com.example.myapplication.TikTokApp;

import android.util.Log;

import java.io.*;
import java.util.*;

public class Mp4Parse {

    private File file;

    private InputStream inputStream;
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private int chunkSize = 512 * 1024;
    private int parts;
    private int read;
    private int streamSize = 0;
    private ArrayList<byte[]> chunks = new ArrayList<byte[]>();

    Mp4Parse(String path) {
        file = new File(path);
        try {
            inputStream = new FileInputStream(file);
            parts = (inputStream.available() / chunkSize) + 1;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DebugInfo: ParsingException27", e.toString());
        }
    }


    public void parse() {
        int i = 1;
        try {
            read = inputStream.read();
            while ((read) != -1) {
                outputStream.write(read);
                streamSize++;
                if (chunkSize == streamSize && i != parts) {
                    chunks.add(outputStream.toByteArray());
                    i++;
                    streamSize = 0;
                    outputStream.reset();
                }
                read = inputStream.read();
                if (i == parts && read == -1) {
                    chunks.add(outputStream.toByteArray());
                }
            }

            System.out.println("Chunks splitted: " + chunks.size());

            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DebugInfo: ParsingException57", e.toString());
        }
    }

    public byte[] getChunk(int i) {
        return chunks.get(i);
    }


    public int getChunksNumber() {
        return chunks.size();
    }


    public void delete() {
        try {
            inputStream.reset();
        } catch (Exception e) {
            Log.e("DebugInfo: ParsingException75", e.toString());
        }
        outputStream.reset();
        parts = 0;
        read = 0;
        streamSize = 0;
        chunks.clear();
    }
}
