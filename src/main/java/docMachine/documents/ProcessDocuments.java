package docMachine.documents;

import com.coremedia.cap.content.Content;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * 
 * User: avelinsk
 * Date: 26.05.2010
 *
 * TODO: refactor
 */
public class ProcessDocuments extends AbstractDocuments{
  private final static org.apache.commons.logging.Log log =
          org.apache.commons.logging.LogFactory.getLog(ProcessDocuments.class);
  private static final String path = "output/";
  private static final String extension = ".html";

  
  @Override
  public void processText(Content article){
     StringBuffer buf = new StringBuffer();
     String id = "";

     for (Content textElement : article.getLinks("Articles")){

      if (textElement.getType().getName().equals("Text")){
        List<Content> languages = textElement.getLinks("Language");

        if (languages.size()>0 &&
                languages.get(0).getString("Name").equalsIgnoreCase("en")){
          String content = textElement.getMarkup("Content").asXml();
          buf.append(content);
          id = textElement.getId();

          log.info("Appended textElement to buffer: "+ content);
        }
      }
    }

    serialize(id, buf.toString());
  }


   private void serialize(String id, String buf){
     StringBuffer sb = new  StringBuffer(path);
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
       Writer writer = new BufferedWriter(new FileWriter(outputFile));
       writer.write(buf.toString());
       writer.close();

     } catch (IOException ex){
       log.error(ex);
     }

     log.info("*** File created: "+sb.toString());
   }
/*


    *//**
     * Retrieves document sections and serializes them.
     *
     * @param repository
     *//*
    public void callTextSerializer(ContentRepository repository){
     Content secRoot = repository.getRoot();
     // map of section id, section name
     // secName            secContent
     Map<String,String> sections = DocumentList.valueOf();
     Iterator iter = sections.entrySet().iterator();
     while (iter.hasNext()){
       Map.Entry entry = (Map.Entry) iter.next();
       String secName = (String) entry.getValue();
       Content secContent = (Content)secRoot.getChild(secName);

       buf = new StringBuffer();
       this.getTextsInSection(secContent);
       this.serialize(secContent.getId(),buf.toString());
       }
    }*/
}