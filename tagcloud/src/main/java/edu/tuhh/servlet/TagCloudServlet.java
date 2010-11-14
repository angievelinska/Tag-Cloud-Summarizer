package edu.tuhh.servlet;

import org.mcavallo.opencloud.Cloud;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author avelinsk
 */
public class TagCloudServlet extends HttpServlet {
  public static Cloud cloud;
  public static String text = "";
  public static String command = "";

  public TagCloudServlet(){
    cloud = new Cloud();
    text = "";
    command = "";
  }

  public void doPost(HttpServletRequest requeset, HttpServletResponse response)
          throws IOException, ServletException {

  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {

    	cloud = new Cloud();

        // Sets the number of tag to display in the cloud
        cloud.setMaxTagsToDisplay(30);

        // We want four different levels so set the maximum weight value to 4.0.
        cloud.setMaxWeight(4.0);

        // Sets the default url to assign to tag.
        // The format specifier %s will be substituted by the tag name
        cloud.setDefaultLink("https://documentation.coremedia.com/servlet/content/247402?language=en&include=false&version=5.2&book=%s");

        command = request.getParameter("command");
        text = request.getParameter("text");

        if (text == null) {
                text = "";
        }
        if (command != null) {
                if (command.equals("CLEAR")) {
                        cloud.clear();
                } else if (command.equals("SEARCH")) {
                        cloud.addText(text);
                        text = "";
                }
        }


  }
}
