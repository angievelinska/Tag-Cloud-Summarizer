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

    Cloud cloud = new Cloud();

    this.log("parameters command and text passed. command: " + command + " ; text: " + searchQuery);


    if (command != null) {
      if (command.equals("CLEAR")) {
        cloud.clear();

      } else if (command.equals("SEARCH")) {
        if (searchQuery != null) {
          cloud = new Cloud();
          links = summarizer.getSearchResults(searchQuery);
          tags = summarizer.getTags(searchQuery, 20);
          cloud.addTags(tags);
        }
        searchQuery = "";
      }
    }
    request.setAttribute("tagcloud", cloud);
    request.setAttribute("text", searchQuery);
    RequestDispatcher view = request.getRequestDispatcher("tagcloud.jsp");
    view.forward(request, response);

  }
}