// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000D2CC1BA0380401481E7599620778826DE9964256025A5B87BAC611F0E6754888FEEEA89A6A8FF3622BC89949E18E689F335F927417FA43EAF5A5411D3AD21B3EDC2A8DEA6872265339ED0D70C4923C1B956B4570B96F69590FF8E21C541EDE348611AF72B307CE1D043FA41070E831F7C5B02988000000

package edu.illinois.cs.cogcomp.lbjava.sentiment;

import edu.illinois.cs.cogcomp.lbjava.Document;
import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.infer.*;
import edu.illinois.cs.cogcomp.lbjava.io.IOUtilities;
import edu.illinois.cs.cogcomp.lbjava.learn.*;
import edu.illinois.cs.cogcomp.lbjava.parse.*;
import edu.illinois.cs.cogcomp.lbjava.sentiment.Reader;
import java.util.List;


/** Bag of Words feature */
public class BagOfWords extends Classifier
{
  public BagOfWords()
  {
    containingPackage = "edu.illinois.cs.cogcomp.lbjava.sentiment";
    name = "BagOfWords";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.lbjava.Document"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof Document))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'BagOfWords(Document)' defined on line 10 of SentimentClassifier.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    Document d = (Document) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    List words = d.getWords();
    for (int i = 0; i < words.size(); i++)
    {
      __id = "" + (words.get(i));
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    }
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof Document[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'BagOfWords(Document)' defined on line 10 of SentimentClassifier.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "BagOfWords".hashCode(); }
  public boolean equals(Object o) { return o instanceof BagOfWords; }
}

