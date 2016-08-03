package edu.illinois.cs.cogcomp.sentiment.twitterTokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shivambharuka.
 */
public class TwitterDictionary {
    public static Map<String,String> abbreviations;
    public static Map<String,String> emoticons;
    public static List<String> englishWords;
    public static Map<String,String> hashtags;
    public static double count;

    public TwitterDictionary() throws IOException {
        loadAbbreviations();
        loadEmoticons();
        loadEnglishWords();
        loadHashtags();
    }

    private void loadAbbreviations() throws IOException {
        abbreviations = new HashMap<>();

        FileInputStream fstream = new FileInputStream("data/abbreviations.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();
        while(line!=null){
            if(line.trim().equals("")) {
                line = br.readLine();
                continue;
            }

            String[] parts = line.split("-");

            String abbreviation = parts[0].replaceAll("\\s+","").toLowerCase();
            String meaning = parts[1].replaceAll("\\s{2,}"," ").toLowerCase();

            abbreviations.put(abbreviation,meaning);
            line = br.readLine();
        }
        br.close();
    }

    private void loadEmoticons() throws IOException {
        emoticons = new HashMap<>();

        FileInputStream fstream = new FileInputStream("data/emoticons.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();
        while(line!=null){
            if(line.trim().equals("")) {
                line = br.readLine();
                continue;
            }

            String[] parts = line.split(",");

            String emoticon = parts[0];
            String meaning = parts[1];

            emoticons.put(emoticon,meaning);
            line = br.readLine();
        }
        br.close();
    }

    private void loadEnglishWords() throws IOException {
        englishWords = new ArrayList<>();
        FileInputStream fstream = new FileInputStream("data/en.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();
        while(line!=null){
            if(line.trim().equals("")) {
                line = br.readLine();
                continue;
            }

            String[] parts = line.split("\\s+");

            String word = parts[0];
            englishWords.add(word);

            line = br.readLine();
        }
        br.close();
    }

    private static void loadHashtags() throws IOException {
        hashtags = new HashMap<>();

        FileInputStream fstream = new FileInputStream("data/word_freq.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();
        while(line!=null){
            if(line.trim().equals("")) {
                line = br.readLine();
                continue;
            }

            String[] parts = line.split("\\s+");

            String word = parts[0].replaceAll("\\s+","").toLowerCase();
            String freq = parts[1];

            count += Double.parseDouble(freq);
            hashtags.put(word, freq);
            line = br.readLine();
        }
        br.close();
    }
}
