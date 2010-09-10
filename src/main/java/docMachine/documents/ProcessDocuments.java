package docMachine.documents;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import docMachine.connect.DocumentList;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * User: avelinsk
 * Date: 26.05.2010
 *
 * TODO: refactor
 */
public class ProcessDocuments extends AbstractDocuments{
  private static final String path = "output/";
  private static final String extension = ".html";
  private final static org.apache.commons.logging.Log log =  org.apache.commons.logging.LogFactory.getLog(
          ProcessDocuments.class);
  private StringBuffer buf;

  @Override
  public void getTextsForSection(Content rootElement) {

      if (rootElement.getType().getName().equals("Section")){

          for (Content article: rootElement.getLinks("Articles")){
              if (article.getType().getName().equals("MLArticle")){

                serializeText(article);
              }
              if (rootElement.getLinks("Sections").size()>0){
                for (Content section:  rootElement.getLinks("Sections")){

                    getTextsForSection(section);
                }
              }
          }
      } else if (rootElement.getType().getName().equals("MLArticle")){
          serializeText(rootElement);
      }
  }

   private void serializeText(Content article){

     for (Content text : article.getLinks("Articles")){

      if (text.getType().getName().equals("Text")){
        List<Content> languages = text.getLinks("Language");

        if (languages.size()>0 && languages.get(0).getString("Name").equalsIgnoreCase("en")){
          String content = text.getMarkup("Content").asXml();
          buf.append(content);
          log.info("Appended text to buffer: "+ content);
        }
      }
    }
   }

    /**
     * Serializes buffer content as an html file.
     *
     * @param id
     * @param buf
     */
    private void serializeToHTML(String id, String buf){
      StringBuffer sb = new  StringBuffer(path);
      sb.append(id.substring(id.lastIndexOf("/")+1));
      sb.append(extension);

      log.info("*** File created: "+sb.toString());

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
    }

    /**
     * Retrieves document sections and serializes them.
     *
     * TODO: refactor callTextSerializer()
     *
     * @param repository
     */
    public void callTextSerializer(ContentRepository repository){
     Content secRoot = repository.getRoot();

     Map<String,String> sections = DocumentList.valueOf();
     Iterator iter = sections.entrySet().iterator();
     while (iter.hasNext()){
       Map.Entry entry = (Map.Entry) iter.next();
       String secName = (String) entry.getValue();
       Content secContent = (Content)secRoot.getChild(secName);

       buf = new StringBuffer();
       this.getTextsForSection(secContent);
       this.serializeToHTML(secContent.getId(),buf.toString());
       }
    }

}