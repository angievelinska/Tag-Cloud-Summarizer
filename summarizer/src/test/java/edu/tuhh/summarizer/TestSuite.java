package edu.tuhh.summarizer;

import edu.tuhh.summarizer.common.PropertiesLoaderTest;
import edu.tuhh.summarizer.docmachine.CountDocumentsTest;
import edu.tuhh.summarizer.docmachine.ProcessDocumentsTest;
import edu.tuhh.summarizer.docmachine.connect.ConnectDocMachineTest;
import edu.tuhh.summarizer.docmachine.parser.DocMachineParserTest;
import edu.tuhh.summarizer.identify.TagsIdentificationTest;
import edu.tuhh.summarizer.lsa.*;
import edu.tuhh.summarizer.search.IndexerTest;
import edu.tuhh.summarizer.search.SearcherTest;
import edu.tuhh.summarizer.tagcloud.TagCloudTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author avelinsk
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {       ConnectDocMachineTest.class,
                DocMachineParserTest.class,
                ProcessDocumentsTest.class,
                CountDocumentsTest.class,
                PropertiesLoaderTest.class,
                LSATest.class,
                LatentSemanticAnalysisTest.class,
                QueryTest.class,
                LSAUtilsTest.class,
                SimilarityUtilTest.class,
                TestCompareSvdResults.class,
                TagsIdentificationTest.class,
                TagCloudTest.class,
                IndexerTest.class,
                SearcherTest.class}
)
public class TestSuite {}
