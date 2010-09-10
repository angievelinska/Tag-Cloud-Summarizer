package docMachine.connect;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains a static list with sections from documents in DocMachine.
 * to be removed.
 */
public class DocumentList {
     static Map<String,String> docs;
    

     protected DocumentList(){
         super();

         docs = new HashMap<String,String>();
         
         /****** Workflow Developer Manual 5.2 *******/
         // Overview and Navigation
         docs.put("coremedia:///cap/content/242240","/Books/CMS_ONLINE/5.2/WorkflowDeveloperManual/20_20_OverviewAndNavigation/20_ContentCreation/40_Workflow/0010-Workflow_text");

         // Content Modelling
         docs.put("coremedia:///cap/content/242218","/Books/CMS_ONLINE/5.2/WorkflowDeveloperManual/20_20_OverviewAndNavigation/10_ContentModeling/ContentModeling");

         // Content Management - only the MLArticle
         docs.put("coremedia:///cap/content/242224","/Books/CMS_ONLINE/5.2/WorkflowDeveloperManual/20_20_OverviewAndNavigation/20_ContentCreation/0010-ContentManagement_text");

         // Content Management/CoreMedia Editor
         docs.put("coremedia:///cap/content/242226","/Books/CMS_ONLINE/5.2/WorkflowDeveloperManual/20_20_OverviewAndNavigation/20_ContentCreation/10_Editor/Editor");

         // Content Management/Custom Clients
         docs.put("coremedia:///cap/content/242230","/Books/CMS_ONLINE/5.2/WorkflowDeveloperManual/20_20_OverviewAndNavigation/20_ContentCreation/20_CustomClients/CustomClients");

         // Content Management/Importer
         docs.put("coremedia:///cap/content/242234","/Books/CMS_ONLINE/5.2/WorkflowDeveloperManual/20_20_OverviewAndNavigation/20_ContentCreation/30_Importer/Importer");

         // Content Management/Workflow
         docs.put("coremedia:///cap/content/242238","/Books/CMS_ONLINE/5.2/WorkflowDeveloperManual/20_20_OverviewAndNavigation/20_ContentCreation/40_Workflow/Workflow");

         // Content Management/Content Delivery
         docs.put("coremedia:///cap/content/242242","/Books/CMS_ONLINE/5.2/WorkflowDeveloperManual/20_20_OverviewAndNavigation/30_ContentDelivery/ContentDelivery");

         /******  User Manual 5.2 ******/
         // Editor User Manual - Publication of DocumentList and Folders
         docs.put("coremedia:///cap/content/254966","/Books/CMS_ONLINE/5.2/UserManual/20_0020-B-BasicKnowledge/20_0040-en-Resources-section/50_0010-en-Publication/0010-en-Publication");

         /******  Unified API Developer Manual 5.2  ******/
         // Unified API Overview
         docs.put("coremedia:///cap/content/241654","/Books/CMS_ONLINE/5.2/UnifiedAPIDeveloperManual/30_Overview/Overview");
         
         // Sessions section
         docs.put("coremedia:///cap/content/241796","/Books/CMS_ONLINE/5.2/UnifiedAPIDeveloperManual/50_Common Concepts/100_Sessions/Sessions");
       }

  public static Map<String,String> valueOf(){
        new DocumentList();
        return docs;

    }

}