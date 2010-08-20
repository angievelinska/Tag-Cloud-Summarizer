package docMachine.connect;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.xml.Markup;
import com.coremedia.xml.MarkupFactory;
import com.coremedia.xml.PlaintextSerializer;
import documents.Documents;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * User: avelinsk
 * Date: 26.05.2010
 */
public class ProcessDocument {
    private static final String rootDir =  "/Books/CMS_ONLINE/5.2/UserManual";
    private static final String path = "output/";
    private static final String extension = ".html";
    private final static org.apache.commons.logging.Log log =  org.apache.commons.logging.LogFactory.getLog(ProcessDocument.class);
    private StringBuffer buf;


    /**
     * Walks document tree and appends text from the sections to a buffer.
     * 
     * @param rootElement
     */
    private void walkDocumentTree(Content rootElement){
        for (Content element: rootElement.getChildren()){
            if (element.getType().getName().equals("Text")){

                java.util.List<Content> language = element.getLinks("Language");
                for (Content lang: language){
                    if (lang.getString("Name").equalsIgnoreCase("en")){

                        String str = element.getMarkup("Content").toString();
                        //String  str = element.getString("Content");
                        //buf.append(str.substring(str.indexOf("[")));
                        buf.append(element.getMarkup("Content").toString());
                        log.info("text "+element.getName()+" with language "+lang.getString("Name"));
                        log.info("element type: " + element.getType().toString());
                    }
                }
            }
            walkDocumentTree(element);
        }
    }

    /**
     * Serialize buffer content into an html file.
     * 
     * @param id
     * @param buf
     */
    private void serializeText(String id, String buf){
        StringBuffer sb = new  StringBuffer(path);
        sb.append(id.substring(id.lastIndexOf("/")+1));
        sb.append(extension);

        log.info("*** File created: "+sb.toString());

        try {

            FileOutputStream fi = new FileOutputStream(sb.toString());
            ObjectOutputStream oi = new ObjectOutputStream(fi);
            oi.writeObject(buf);
            oi.close();

        } catch (IOException ex){
            log.error(ex);
        }
    }

    /**
     * Retrieves Documents class sections and serializes them.
     *
     * @param repository
     */
    public void callTextSerializer(ContentRepository repository){
       Content secRoot = repository.getRoot();

       Map<String,String> sections = Documents.valueOf();
       Iterator iter = sections.entrySet().iterator();
       while (iter.hasNext()){
           Map.Entry entry = (Map.Entry) iter.next();
           String secName = (String) entry.getValue();

           Content secContent = (Content)secRoot.getChild(secName);

           buf = new StringBuffer();
           this.walkDocumentTree(secContent);
           this.serializeText(secContent.getId(),buf.toString());
       }
    }

        public void testCallTextSerializer(){
            ProcessDocument pd = new ProcessDocument();
            ConnectDocMachine cdm = new ConnectDocMachine();
            pd.callTextSerializer(cdm.openConnection());
        }


    /**
     * retrieve and serialize as html files all texts from User Manual 5.2
     * 
     * @param args
     */
    public static void main(String[]args){
        ProcessDocument pd = new ProcessDocument();
        ConnectDocMachine cd = new ConnectDocMachine();

        try {
            //pd.documentWalker(cd.openConnection());
            //pd.retrieveContent(cd.openConnection());
            pd.callTextSerializer(cd.openConnection());

        }   finally {
            cd.closeConnection();
        }
    }
    
    

    /**
     *
     * Storage for old code below .......
     *
     *
     * 
     */

     public Content retrieveContent(ContentRepository repository){
        Content root = repository.getRoot();
        Content article = (Content)root
                .getChild("/Books/CMS_ONLINE/5.2/UserManual/10_0010-A-Introduction/20_0020-en-Structure-section/_content/0020-en-StructureFrom4.2.0_text_from5.0");
        log.info(article.getString("Content"));
        log.info(article.getId());
        log.info(article.getName());

        String content = article.getString("Content");
        String id = article.getId();
        StringBuffer sb = new  StringBuffer(path);
        sb.append(id.substring(id.lastIndexOf("/")+1));
        sb.append(extension);

        log.info("*** File created: "+sb.toString());
        
        try {

            FileOutputStream fi = new FileOutputStream(sb.toString());
            ObjectOutputStream oi = new ObjectOutputStream(fi);
            oi.writeObject(content);
            oi.close();

        } catch (IOException ex){
            log.error(ex);
        }

        return article;
    }

    /**
     * Use case for project: User Manual 5.2
     * Start search for Texts from /Books/CMS_ONLINE/5.2/UserManual down, recursively
     *
     * @param repository
     */
    private void documentWalker(ContentRepository repository) {
        Content root = repository.getRoot();
        Content rootContent = (Content)root.getChild(rootDir);
        if (!rootContent.isFolder()){
            log.error("Wrong root path for document. Check DocMachine repository.");
            throw new IllegalArgumentException("Wrong root path for document.");
            }
        this.walkDocument(rootContent);
    }

        /**
     * Helper method to strip html tags off Content text.
     *
     * @param con
     * @return
     */
    private String stripTags(Content con){
        Markup markup = MarkupFactory.fromString(con.getString("Content"));
        StringWriter writer = new StringWriter();
        PlaintextSerializer serializer = new PlaintextSerializer(writer);
        markup.writeOn(serializer);
        String value = writer.toString();
        log.info("*** Content: "+value);

        return value;
    }

        /**
     * Writes the text content into an html file.
     * Filename is Content ID.
     *
     * @param con
     */
    private void serializeContent(Content con){
        String content = con.getString("Content");
        String id = con.getId();
        StringBuffer sb = new  StringBuffer(path);
        sb.append(id.substring(id.lastIndexOf("/")+1));
        sb.append(extension);

        log.info("*** File created: "+sb.toString());

        try {

            FileOutputStream fi = new FileOutputStream(sb.toString());
            ObjectOutputStream oi = new ObjectOutputStream(fi);
            oi.writeObject(content);
            oi.close();

        } catch (IOException ex){
            log.error(ex);
        }
    }

        /**
     * serialize texts from a document as separate html file per text element
     *
     * @param rootElement
     */
    private void walkDocument(Content rootElement){

        for (Content element: rootElement.getChildren()){
            if (element.getType().getName().equals("Text")){

                java.util.List<Content> language = element.getLinks("Language");
                for (Content lang: language){
                    if (lang.getString("Name").equalsIgnoreCase("en")){
                        this.serializeContent(element);
                        //log.info("text "+element.getName()+" with language "+lang.getString("Name"));
                    }
                }
            }
            walkDocument(element);
        }
    }
}