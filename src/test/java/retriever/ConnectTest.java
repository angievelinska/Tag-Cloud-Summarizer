package retriever;

import com.coremedia.cap.Cap;
import com.coremedia.cap.common.CapConnection;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.content.ContentType;
import com.coremedia.cotopaxi.common.CapConnectionImpl;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: avelinsk
 * Date: 28.04.2010
 * Time: 18:44:43
 * To change this template use File | Settings | File Templates.
 */
public class ConnectTest {

// TODO: to be cleaned up
    public static void connectSimple(){

            String url = "http://localhost:44441/coremedia/ior";
            CapConnection con = Cap.connect(url, "admin", "admin");
            ContentRepository repository = con.getContentRepository();
            try {
                Content root = repository.getRoot();
                ContentType folderType = repository.getFolderContentType();
                folderType.create(root, "hello");
            } finally {
                con.close();
            }
        }
//TODO: to be cleaned up
    public static void openConnection(){
        // Configure the connection parameters
        String username="admin";
        String password="admin";
        String domain=null;
        // if iorUrl is null, use the IOR URL from capclient.properties
        // String iorUrl=null;
        String iorUrl="http://localhost:44441/coremedia/ior";

        // Build up the connection
        CapConnection connection=null;
        Map params = new HashMap();
        params.put(Cap.USER, username);
        params.put(Cap.DOMAIN, domain);
        params.put(Cap.PASSWORD, password);
        if (iorUrl != null) params.put(Cap.CONTENT_SERVER_URL, iorUrl);
        System.out.println("*** Connecting as "+username+(domain != null ? "@"+domain : "")+(iorUrl != null ? " to "+iorUrl : "")+" ***");
        connection = Cap.connect(params);
        System.out.println("*** Connected: "+connection+" ***");

        // Define some variables
        ContentRepository cr = connection.getContentRepository();
        com.coremedia.cap.content.Content root = cr.getRoot();
        com.coremedia.cap.content.authorization.AccessControl ac = cr.getAccessControl();
        com.coremedia.cap.content.publication.PublicationService pu = cr.getPublicationService();
        com.coremedia.cap.content.query.QueryService qs = cr.getQueryService();
        com.coremedia.cap.user.UserRepository ur = connection.getUserRepository();
        com.coremedia.cap.workflow.WorkflowRepository wr = connection.getWorkflowRepository();
        if (wr != null){
            com.coremedia.cap.workflow.WorklistService wl = wr.getWorklistService();
        }
        com.coremedia.cap.struct.StructRepository sr = ((CapConnectionImpl)connection).getStructRepository();

        // Disable annoying "evicted" messages
        java.util.logging.Logger rootLogger = java.util.logging.LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(java.util.logging.Level.WARNING);
    }

}
