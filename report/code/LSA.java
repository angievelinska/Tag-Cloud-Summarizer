public class LSA {
  private static Logger log = Logger.getLogger(LSA.class);

  public void runLSA() {
      Properties props = new PropertiesLoader().loadProperties();
      IteratorFactory.setProperties(lsaprops);
      int noOfThreads = Runtime.getRuntime().availableProcessors();

      long start = System.currentTimeMillis();
      LatentSemanticAnalysis sspace = null;
      try {
        // initialize the semantic space
        sspace = new LatentSemanticAnalysis();
        Iterator<Document> iter = new OneLinePerDocumentIterator(props.getProperty("docFile"));

        // dimensionality reduction and SVD
        processDocumentsAndSpace(sspace, iter, noOfThreads, props);

        // save the constructed term space - after SVD these are U * Sigma matrices.
        File output = initOutputFile(props, "termSpace.sspace");
        SemanticSpaceIO.save(sspace, output, SemanticSpaceIO.SSpaceFormat.TEXT);
        log.info("Semantic space is saved after SVD reduction.");
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
      saveDocumentSpace(sspace, props);

      long end = System.currentTimeMillis();
      log.info("LSA took " + (end - start) + "ms to index the document collection.");
    }

  protected void processDocumentsAndSpace(SemanticSpace space,
                                          Iterator<Document> iter,
                                          int noOfThreads,
                                          Properties props)
          throws IOException, InterruptedException {

    parseDocsMultiThreaded(space, iter, noOfThreads);
    space.processSpace(props);
  }

  protected void parseDocsMultiThreaded(final SemanticSpace space,
                                        final Iterator<Document> iter,
                                        int noThreads)
          throws IOException, InterruptedException {
    Collection<Thread> threads = new LinkedList<Thread>();
    for (int i = 0; i < noThreads; ++i) {
      Thread t = new Thread() {
        public void run() {
          log.info("Process document: " + iter.next().toString());
          while (iter.hasNext()) {
            Document document = iter.next();
            try {
              space.processDocument(document.reader());
            } catch (Throwable t) {
              t.printStackTrace();
            }
          }
        }
      };
      threads.add(t);
    }

    for (Thread t : threads)
      t.start();

    for (Thread t : threads)
      t.join();
  }

  /** .... */
}