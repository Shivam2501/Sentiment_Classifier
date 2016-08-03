package edu.illinois.cs.cogcomp.sentiment;

import edu.illinois.cs.cogcomp.lbjava.classify.TestDiscrete;
import edu.illinois.cs.cogcomp.lbjava.learn.BatchTrainer;
import edu.illinois.cs.cogcomp.sentiment.sentiment.*;

import java.io.IOException;

/**
 * Created by shivambharuka on 6/23/16.
 */
public class Trainer {
    private String modelName = "model/sentimentModel";

    private void train() throws IOException {
        SentimentClassifier classifier = new SentimentClassifier();
        //PhraseReader trainReader = new PhraseReader("data/train.tsv");
        //Reader trainReader = new Reader("data/PL-dataset/train-pos.txt","data/PL-dataset/train-neg.txt");
        TwitterReader trainReader = new TwitterReader("data/Twitter/positive_tweets_train.json","data/Twitter/negative_tweets_train.json");
        BatchTrainer trainer = new BatchTrainer(classifier, trainReader, 2000);
        trainer.train(5);
        classifier.save();
    }

    private void test() throws IOException {
        SentimentClassifier classifier = new SentimentClassifier();
        //PhraseReader testReader = new PhraseReader("data/test.tsv");
        //Reader testReader = new Reader("data/PL-dataset/test-pos.txt","data/PL-dataset/test-neg.txt");
        TwitterReader testReader = new TwitterReader("data/Twitter/positive_tweets_test.json","data/Twitter/negative_tweets_test.json");
        int outputGranularity = 2000;
        TestDiscrete.testDiscrete(new TestDiscrete(), classifier, new SentimentLabel(), testReader, true, outputGranularity);
    }

    public static void main(String[] args) throws IOException {
        Trainer trainer = new Trainer();
        trainer.train();
        trainer.test();
    }
}
