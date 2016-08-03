package edu.illinois.cs.cogcomp.sentiment.twitterTokenizer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shivambharuka.
 */
public class Emoticon extends Tokenizer{

    Emoticon(String text) throws IOException {
        word = text;
        label = TwitterDictionary.emoticons.get(word);
    }

    static Boolean containsKey(String word) throws IOException {
        return TwitterDictionary.emoticons.containsKey(word);
    }


}
