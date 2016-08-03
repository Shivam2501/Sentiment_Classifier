// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000053CC1BA02C0401481E75996207740385B71B3B4141C748BB139D2298CDE6A152FEEE604BA92EFF8922A9B2D8D0EE7B748BC297D983B1A444F70C70751538EB8CC32EC82DD04BDD5C487E25114C9A878362F9EF70B359737722D6B1DF3493B2FFDCF02844C90657562C68DEB091891C5128000000

package edu.illinois.cs.cogcomp.sentiment.sentiment;

import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.infer.*;
import edu.illinois.cs.cogcomp.lbjava.io.IOUtilities;
import edu.illinois.cs.cogcomp.lbjava.learn.*;
import edu.illinois.cs.cogcomp.lbjava.parse.*;
import edu.illinois.cs.cogcomp.sentiment.Document;
import edu.illinois.cs.cogcomp.sentiment.sentiment.Reader;
import java.util.List;


/** POS feature */
public class POS extends Classifier
{
  public POS()
  {
    containingPackage = "edu.illinois.cs.cogcomp.sentiment.sentiment";
    name = "POS";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.sentiment.Document"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof Document))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'POS(Document)' defined on line 17 of SentimentClassifier.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    Document d = (Document) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    List speech = d.getPOS();
    for (int i = 0; i < speech.size(); i++)
    {
      __id = "" + (speech.get(i));
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
      System.err.println("Classifier 'POS(Document)' defined on line 17 of SentimentClassifier.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "POS".hashCode(); }
  public boolean equals(Object o) { return o instanceof POS; }
}

