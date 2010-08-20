package docMachine.documents;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Map;

public class DocumentsTest extends TestCase {

    public DocumentsTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testValueOf(){
        assertTrue(Documents.valueOf() instanceof Map);
        assertTrue(Documents.valueOf().entrySet().size() > 0);

    }

    public static Test suite() {
        return new TestSuite(DocumentsTest.class);
    }
}
