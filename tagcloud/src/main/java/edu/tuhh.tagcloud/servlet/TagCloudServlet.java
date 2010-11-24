package edu.tuhh.tagcloud.servlet;

import org.mcavallo.opencloud.Cloud;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    String text = request.getParameter("text");

    this.log("parameters command and text passed. command: "+command);

    Cloud cloud = new Cloud();
    String content;
    cloud.setMaxTagsToDisplay(30);

    // We want four different levels so set the maximum weight value to 4.0.
    cloud.setMaxWeight(4.0);

    // Sets the default url to assign to tag.
    // The format specifier %s will be substituted by the tag name
    cloud.setDefaultLink("https://documentation.coremedia.com/servlet/content/247402?language=en&include=false&version=5.2&book=%s");

    if (text != null) {
      content = text;
    } else {
      content ="";
    }  
    if (command != null) {
            if (command.equals("CLEAR")) {
                    cloud.clear();
            } else if (command.equals("SEARCH")) {
                    cloud.addText(content);
                    text = "";
            }
    }

    request.setAttribute("tagcloud", cloud);
    request.setAttribute("text", text);
    RequestDispatcher view = request.getRequestDispatcher("tagcloud.jsp");
    view.forward(request, response);

  }

}
