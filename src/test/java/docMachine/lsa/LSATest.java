package docMachine.lsa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * User: ng
 * Date: 19.08.2010
 * Time: 23:05:27
 */
public class LSATest {
    LSA lsa;

    @Before
    public void setUp() throws Exception {
        lsa = new LSA();
    }

    @Test
    public void testLSA(){
        try {
            lsa.runLSA();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        lsa = null;
    }
}
