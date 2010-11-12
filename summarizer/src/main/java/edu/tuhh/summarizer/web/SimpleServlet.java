package edu.tuhh.summarizer.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class SimpleServlet extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response){

  }

  public void doGet(HttpServletRequest request, HttpServletResponse response){

    try{
      ServletOutputStream out = response.getOutputStream();
      response.setContentType("text/html");
      write(out, "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML//EN\">");
      write(out, "<html>");
      write(out, "<head><title>Maven by Example</title></head>");
      write(out, "<body>");
      write(out, "  <h1>Hello World.</h1>");
      write(out, "</body>");
      write(out, "</html>");

      out.flush();
      out.close();
      
    } catch(IOException ex){
      ex.printStackTrace();
    }
  }

  public void write(OutputStream out, String write) throws IOException{
    out.write(write.getBytes());
    out.write("\n".getBytes());
  }
}