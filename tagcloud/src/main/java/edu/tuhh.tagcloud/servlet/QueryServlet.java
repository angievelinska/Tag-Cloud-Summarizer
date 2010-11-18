package edu.tuhh.tagcloud.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author avelinsk
 */
public class QueryServlet extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response){

  }

  public void doGet(HttpServletRequest request, HttpServletResponse response){
    String query = request.getParameter("query");

    try{
      ServletOutputStream out = response.getOutputStream();
     /* write(out, SummarizerUtils.getTagCloud(query));*/
      out.flush();
      out.close();
      
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  public void write(OutputStream out, String write) throws IOException{
    out.write(write.getBytes());
    out.write("\n".getBytes());
  }
}