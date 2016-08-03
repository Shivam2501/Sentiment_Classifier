package edu.illinois.cs.cogcomp.sentiment.yelpDictionary;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;

/**
 * Created by shivambharuka on 7/5/16.
 */
public class YelpJsonParser {

    private String text;
    private String stars;
    private static Map<String,WordDictionary> wordCount = new HashMap<>();
    private static List<String> stopwords = new ArrayList<>();

    private YelpJsonParser(String text, String stars) {
        this.text = text;
        this.stars = stars;
        dictionary();
    }

    private void dictionary() {
        for(String word:text.split("\\s+")) {

            String filteredWord = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

            if(filteredWord.trim().equals("") || filteredWord.length()<3 || isNum(filteredWord))
                continue;

            if(stopwords.contains(filteredWord))
                continue;

            if(wordCount.containsKey(filteredWord)){
                WordDictionary key = wordCount.get(filteredWord);
                update_star(key);
            }else{
                WordDictionary key = new WordDictionary(filteredWord);
                update_star(key);
                wordCount.put(filteredWord,key);
            }
        }
    }

    private static boolean isNum(String strNum) {
        try {
            Double.parseDouble(strNum);
        }catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void update_star(WordDictionary key) {
        switch (stars) {
            case "5":
                key.fiveStar++;
                break;
            case "4":
                key.fourStar++;
                break;
            case "3":
                key.threeStar++;
                break;
            case "2":
                key.twoStar++;
                break;
            case "1":
                key.oneStar++;
                break;
        }
    }

    private static void loadStopwords() throws Exception{
        FileInputStream fstream = new FileInputStream("data/stopwords.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();

        while (line != null) {
            if(line.trim().equals(""))   //check for empty string
                continue;
            stopwords.add(line);
            line = br.readLine();
        }

        br.close();
    }

    private static void create_file() throws  Exception {
        PrintWriter out = new PrintWriter(new File("data/map.csv"));

        StringBuilder sb = new StringBuilder();
        String ColumnNamesList = "Word,fiveStar,fourSar,threeStar,twoStar,oneStar,Score1,Score2";
        sb.append(ColumnNamesList).append("\n");

        for (Map.Entry<String, WordDictionary> entry : wordCount.entrySet()) {
            String key = entry.getKey();
            WordDictionary value = entry.getValue();
            if (value.fiveStar + value.fourStar + value.threeStar + value.twoStar + value.oneStar > 15 && value.fiveStar + value.oneStar > 0 && value.fiveStar + value.fourStar + value.twoStar + value.oneStar > 0) {
                Double score1 = (value.fiveStar - value.oneStar) / (double)(value.fiveStar + value.oneStar);
                Double score2 = (value.fiveStar + value.fourStar - value.twoStar - value.oneStar) / (double)(value.fiveStar + value.fourStar + value.twoStar + value.oneStar);
                sb.append(key + "," + value.fiveStar + "," + value.fourStar + "," + value.threeStar + "," + value.twoStar + "," + value.oneStar + "," + score1 + "," + score2 + "\n");
            }
        }
        out.write(sb.toString());
        out.close();
    }

    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();

        //load the stop words
        loadStopwords();

        FileInputStream fstream = new FileInputStream("data/yelp_academic_dataset_review.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String line = br.readLine();
        while (line != null){
            if(line.trim().equals("")) {
                line = br.readLine();
                continue;//empty string cannot be parsed
            }

            YelpJsonParser entry = gson.fromJson(line,YelpJsonParser.class);
            YelpJsonParser index = new YelpJsonParser(entry.text, entry.stars);
            line = br.readLine();
        }
        br.close();
        create_file();
    }
}
