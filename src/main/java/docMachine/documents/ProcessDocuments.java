package docMachine.documents;

import com.coremedia.cap.content.Content;
import com.coremedia.xml.Markup;
import com.coremedia.xml.PlaintextSerializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Locale;

/**
 * 
 * User: avelinsk
 * Date: 26.05.2010
 *
 */
public class ProcessDocuments extends AbstractDocuments{
  private final static org.apache.commons.logging.Log log =
          org.apache.commons.logging.LogFactory.getLog(ProcessDocuments.class);
  private static final String path = "output/";
  private static final String extension = ".txt"; //".html";

  
  @Override
  public void processText(Content article){
     StringBuffer buf = new StringBuffer();
     String id = "";
     Markup markup = null;

     for (Content textElement : article.getLinks("Articles")){

      if (textElement.getType().getName().equals("Text")){
        List<Content> languages = textElement.getLinks("Language");

        if (languages.size()>0 &&
                languages.get(0).getString("Name").equalsIgnoreCase("en")){
          markup = textElement.getMarkup("Content");
          id = textElement.getId();
          serialize(id, markup);
        }
      }
    }
  }


  /**
   * TODO: decapitalize words, remove punctuation
   */
   private void serialize(String id, Markup markup){
     StringBuffer sb = new  StringBuffer();
     sb.append(id.substring(id.lastIndexOf("/")+1));
     sb.append(extension);

     File outputPath = new File(path) ;
     if(!outputPath.exists()){
       outputPath.mkdir();
     }

     File outputFile = new File(sb.toString());
     if (!outputFile.exists()){
       outputFile = new File(outputPath,sb.toString());
     }

     try {
       BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
       // TODO: to test this
       String plainText = asPlainText(markup);
       writer.write(plainText);
       writer.close();

     } catch (IOException ex){
       log.error(ex);
     }

     log.info("*** File created: "+sb.toString());
   }

  private String asPlainText(Markup markup){
    if (markup ==  null) return "";

    StringWriter writer = new StringWriter();
    PlaintextSerializer serializer = new PlaintextSerializer(writer);
    markup.writeOn(serializer);

    return writer.toString();
  }

}