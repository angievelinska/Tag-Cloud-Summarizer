package docMachine.connect;

import com.coremedia.cap.Cap;
import com.coremedia.cap.common.CapConnection;
import com.coremedia.cap.common.CapException;
import com.coremedia.cap.common.ConnectionNotOpenException;
import com.coremedia.cap.common.ServiceUnavailableException;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.content.ContentType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: avelinsk
 * Date: 28.04.2010
 * Time: 18:21:10
 * To change this template use File | Settings | File Templates.
 */
public class ConnectDocMachine {
    private CapConnection connection;
    private String username, password, domain, iorUrl;
    private Map params;
    private ContentRepository repository;
    private Content root;
    private ContentType folderType;
    private final static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(ConnectDocMachine.class);
   
    private void initParams(){
        this.username = "admin";
        this.password = "admin";
        this.domain = null;
        this.iorUrl = "http://localhost:44441/coremedia/ior";
        params = new HashMap();
        params.put(Cap.USER,this.username);
        params.put(Cap.DOMAIN, this.domain);
        params.put(Cap.PASSWORD,this.password);
        params.put(Cap.CONTENT_SERVER_URL, this.iorUrl);
        params.put(Cap.WORKFLOW_SERVER_URL, this.iorUrl);

    }

    public ContentRepository openConnection(){
        try{
           this.initParams();
           log.info("*** Connecting to DocMachine ***");
           this.connection = Cap.connect(params);
           this.repository = this.connection.getContentRepository();
           this.root = this.repository.getRoot();
           this.folderType = this.repository.getFolderContentType();
           log.info("*** Connected to "+this.connection);


        } catch (ServiceUnavailableException serv){
           log.error(serv);
            
        } catch(ConnectionNotOpenException con){
            log.error(con);

        }  catch (CapException ce){
           log.error(ce);
        }

        return this.repository;
    }


    public void closeConnection(){
        if(this.connection!=null && this.connection.isOpen()){
            this.connection.close();
        }
    }


/**
 *      docMachine.connect to DocMachine
 */
    public static void main(String[] args){
        ConnectDocMachine ct = new ConnectDocMachine();
        
        try{
            ct.openConnection();
        }  finally {
            ct.closeConnection();
        }
    }

}
