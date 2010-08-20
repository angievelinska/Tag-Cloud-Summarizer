package docMachine.connect;

import com.coremedia.cap.content.ContentRepository;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ConnectDocMachineTest extends TestCase {
    ConnectDocMachine conn;

    public ConnectDocMachineTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
        conn = new ConnectDocMachine();
        //conn.openConnection();
    }

    public void tearDown() throws Exception {
        super.tearDown();
        conn.closeConnection();
    }


    public void testOpenConnection(){

        assertTrue (conn.openConnection() instanceof ContentRepository);
    }


    public static Test suite() {
        return new TestSuite(ConnectDocMachineTest.class);
    }
}
