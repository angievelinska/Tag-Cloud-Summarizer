package docMachine.documents;

import com.coremedia.cap.content.Content;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: avelinsk
 * Date: 02.08.2010
 */
public class Counter {
    private final static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Counter.class);
    public int counter = 0;

    public void countDocuments(Content rootElement){
         int counter=0;

         log.info("name of element:"+rootElement.getName());
         log.info("root element type is:"+rootElement.getType().getName());

        if (rootElement.getType().getName().equals("Folder_")){
            
        }
          if (rootElement.getType().getName().equals("Section")){

            for (Content article: rootElement.getLinks("Articles")){

                 log.info("element type is:..."+article.getType().getName());
                 log.info("name of element:"+article.getName());
                 if (article.getType().getName().equals("MLArticle")){
                     counter++;
                 }

            }

            if (rootElement.getLinks("Sections").size()>0){
                for (Content section:rootElement.getLinks("Sections")){
                    countDocuments(section);
                }
            }

        } else if (rootElement.getType().getName().equals("MLArticle")){
              log.info("element type is: "+rootElement.getType().getName());
              log.info("element name is: "+rootElement.getName());
              counter++;
          }

    }

    public int getCount(){
        log.info("Number of Texts in 5.2: "+counter);
        return counter;
    }

}
