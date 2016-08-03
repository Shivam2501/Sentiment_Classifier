package edu.illinois.cs.cogcomp.sentiment.twitterTokenizer;

import edu.illinois.cs.cogcomp.core.datastructures.Pair;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by shivambharuka.
 */

public class Hashtag extends Tokenizer{

    private String splitWords;
    private static List<Pair<String,Double>> combinations;

    Hashtag(String text){
        word = text.toLowerCase();
    }

    private void workBreak() throws IOException {
        combinations = new ArrayList<>();
        word = word.substring(1,word.length());
        split(word,word.length(),"");
    }

    private void split(String str, int size, String result) {
        for (int i=1; i<=size; i++) {
            String prefix = str.substring(0, i);
            double newValue = 0.0;
            if (TwitterDictionary.hashtags.containsKey(prefix)) {
                if (i == size) {
                    result += prefix;
                    for(String word:result.split("\\s+")) {
                        if(word.trim().equals("")) {
                            continue;
                        }
                        newValue +=  Math.log10(Double.parseDouble(TwitterDictionary.hashtags.get(word))/TwitterDictionary.count);
                    }
                    combinations.add(new Pair<String, Double>(result,newValue));
                    return;
                }
                split(str.substring(i, size), size-i, result+prefix+" ");
            }
        }
    }

    /*
    private boolean splitWord(String str) {
        int size = str.length();

        if(size == 0)
            return true;

        boolean[] wb = new boolean[size+1];
        Arrays.fill(wb,false);

        String[] result = new String[size+1];
        Arrays.fill(result,"");

        for(int i=1; i<=size; i++) {
            if(!wb[i] && TwitterDictionary.hashtags.containsKey(str.substring(0,i))) {
                wb[i] = true;
            }

            if(wb[i]) {
                if(i == size) {
                    return true;
                }

                for(int j=i+1; j<=size; j++) {
                    if(!wb[j] && TwitterDictionary.hashtags.containsKey(str.substring(i, j-1))) {
                        wb[j] = true;
                    }

                    if(j==size && wb[j]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    */

    public String getLabels() throws IOException {
        workBreak();
        double value = 0.0;
        for(Pair word: combinations) {
           if(value == 0.0) {
               value = (double) word.getSecond();
               splitWords = (String) word.getFirst();
           }else if((double)word.getSecond()>value) {
               value = (double) word.getSecond();
               splitWords = (String) word.getFirst();
           }
        }
        return splitWords;
    }
}
