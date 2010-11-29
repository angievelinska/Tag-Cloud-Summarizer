package edu.tuhh.tagcloud.servlet;

import edu.tuhh.tagcloud.Summarizer;
import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author avelinsk
 */
public class TagCloudServlet extends HttpServlet {

  public void doGet(HttpServletRequest requeset, HttpServletResponse response)
          throws IOException, ServletException {
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {

    String command = request.getParameter("command");
    String searchQuery = request.getParameter("text");

    Summarizer summarizer = new Summarizer();
    List<String> links; // = summarizer.getSearchResults(searchQuery);
    List<Tag> tags = new ArrayList<Tag>(); // = summarizer.getTags(searchQuery, 20);


    this.log("parameters command and text passed. command: " + command);

    Cloud cloud = new Cloud();

    //String content;
    //cloud.setMaxTagsToDisplay(30);

    // We want four different levels so set the maximum weight value to 4.0.
    //cloud.setMaxWeight(4.0);

    // Sets the default url to assign to tag.
    // The format specifier %s will be substituted by the tag name
    //cloud.setDefaultLink("https://documentation.coremedia.com/servlet/content/247402?language=en&include=false&version=5.2&book=%s");

    if (searchQuery != null) {
      links = summarizer.getSearchResults(searchQuery);
      tags = summarizer.getTags(searchQuery, 20);
    } else {
      searchQuery = "";
    }
    if (command != null) {
      if (command.equals("CLEAR")) {
        cloud.clear();
      } else if (command.equals("SEARCH")) {
        cloud.addTags(tags);
        searchQuery = "";
      }
    }
    System.out.println("Classpath:"+System.getProperty ("java.class.path"));
    request.setAttribute("tagcloud", cloud);
    request.setAttribute("text", searchQuery);
    RequestDispatcher view = request.getRequestDispatcher("tagcloud.jsp");
    view.forward(request, response);

  }

}
