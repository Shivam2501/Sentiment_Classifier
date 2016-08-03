package edu.illinois.cs.cogcomp.sentiment.twitterTokenizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shivambharuka.
 */
public class Abbreviations extends Tokenizer {

    Abbreviations(String text) throws IOException {
        word = text.toLowerCase();
        label = TwitterDictionary.abbreviations.get(word);
    }

    static Boolean containsKey(String word) throws IOException {
        return TwitterDictionary.abbreviations.containsKey(word);
    }

}
