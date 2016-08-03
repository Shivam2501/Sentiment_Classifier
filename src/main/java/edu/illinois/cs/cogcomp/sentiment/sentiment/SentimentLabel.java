// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000B49CC2E4E2A4D2945580E4DCB29CCC5021E398949A93A1E29F9C5A02E924A86A28D8EA245B2005159615E924A8E5A7A245986A5B24D200CE1733D3F3000000

package edu.illinois.cs.cogcomp.sentiment.sentiment;

import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.infer.*;
import edu.illinois.cs.cogcomp.lbjava.io.IOUtilities;
import edu.illinois.cs.cogcomp.lbjava.learn.*;
import edu.illinois.cs.cogcomp.lbjava.parse.*;
import edu.illinois.cs.cogcomp.sentiment.Document;
import edu.illinois.cs.cogcomp.sentiment.sentiment.Reader;
import java.util.List;


public class SentimentLabel extends Classifier
{
  public SentimentLabel()
  {
    containingPackage = "edu.illinois.cs.cogcomp.sentiment.sentiment";
    name = "SentimentLabel";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.sentiment.Document"; }
  public String getOutputType() { return "discrete"; }


  public FeatureVector classify(Object __example)
  {
    return new FeatureVector(featureValue(__example));
  }

  public Feature featureValue(Object __example)
  {
    String result = discreteValue(__example);
    return new DiscretePrimitiveStringFeature(containingPackage, name, "", result, valueIndexOf(result), (short) allowableValues().length);
  }

  public String discreteValue(Object __example)
  {
    if (!(__example instanceof Document))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'SentimentLabel(Document)' defined on line 34 of SentimentClassifier.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    Document d = (Document) __example;

    return "" + (d.getLabel());
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof Document[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'SentimentLabel(Document)' defined on line 34 of SentimentClassifier.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "SentimentLabel".hashCode(); }
  public boolean equals(Object o) { return o instanceof SentimentLabel; }
}

