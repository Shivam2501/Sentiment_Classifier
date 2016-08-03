package edu.illinois.cs.cogcomp.sentiment.sentiment;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by shivambharuka.
 */

class Tweet {
    private String id;
    private String text;

    private Tweet(String id, String text) {
        this.id = id;
        this.text = text;
    }

    String getId() { return id; }
    String getText() { return text; }
}
