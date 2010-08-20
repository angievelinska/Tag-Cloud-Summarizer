package docMachine.lsa;

import edu.ucla.sspace.lsa.LatentSemanticAnalysis;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ng
 * Date: 19.08.2010
 * Time: 23:05:27
 * To change this template use File | Settings | File Templates.
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
            LatentSemanticAnalysis LSA = lsa.invokeLSA();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @After
    public void tearDown() throws Exception {
        lsa = null;
    }
}
