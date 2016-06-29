package edu.illinois.cs.cogcomp.sentiment;

import edu.illinois.cs.cogcomp.lbjava.classify.TestDiscrete;
import edu.illinois.cs.cogcomp.lbjava.learn.BatchTrainer;
import edu.illinois.cs.cogcomp.sentiment.sentiment.Reader;
import edu.illinois.cs.cogcomp.sentiment.sentiment.SentimentClassifier;
import edu.illinois.cs.cogcomp.sentiment.sentiment.SentimentLabel;

/**
 * Created by shivambharuka on 6/23/16.
 */
public class Trainer {
    private String modelName = "model/sentimentModel";

    private void train() {
        SentimentClassifier classifier = new SentimentClassifier();
        //Reader trainReader = new Reader("data/train.tsv");
        Reader trainReader = new Reader("data/PL-dataset/train-pos.txt","data/PL-dataset/train-neg.txt");
        BatchTrainer trainer = new BatchTrainer(classifier, trainReader, 2000);
        trainer.train(5);
        classifier.save();
    }

    private void test() {
        SentimentClassifier classifier = new SentimentClassifier();
        //Reader testReader = new Reader("data/test.tsv");
        Reader testReader = new Reader("data/PL-dataset/test-pos.txt","data/PL-dataset/test-neg.txt");
        int outputGranularity = 2000;
        TestDiscrete.testDiscrete(new TestDiscrete(), classifier, new SentimentLabel(), testReader, true, outputGranularity);
    }

    public static void main(String[] args) {
        Trainer trainer = new Trainer();
        trainer.train();
        trainer.test();
    }
}
