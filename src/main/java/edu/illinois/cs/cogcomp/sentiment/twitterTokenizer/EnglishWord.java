package edu.illinois.cs.cogcomp.sentiment.twitterTokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivambharuka.
 */

public class EnglishWord extends Tokenizer{

    EnglishWord(String text) throws IOException {
        word = text.toLowerCase();
        label = emphaticReduce(word);
    }

    private static String emphaticReduce(String word) {
        String emphaticWord = null;
        if(TwitterDictionary.englishWords.contains(word)) {
            emphaticWord = word;
        }else if(TwitterDictionary.englishWords.contains(word.replaceAll("(.)\\1{1,}", "$1$1"))) { //check if double rep exists in the dict
            emphaticWord = word.replaceAll("(.)\\1{1,}", "$1$1");
        }else if(TwitterDictionary.englishWords.contains(word.replaceAll("(.)\\1{1,}", "$1"))) { //check if single rep exists in the dict
            emphaticWord = word.replaceAll("(.)\\1{1,}", "$1");
        }else if(TwitterDictionary.englishWords.contains(word.replaceAll("[^\\w\\s]", ""))) { //remove all non-words and check in the dict
            emphaticWord = word.replaceAll("[^\\w\\s]", "");
        }
        return emphaticWord;
    }

    static Boolean containsKey(String word) throws Exception {
        return emphaticReduce(word) != null;
    }

}
