<%@ page import="javax.swing.text.html.HTML" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Simple jsp page</title></head>
  <body>
    <div>
    <% for (HTML.Tag tag : cloud.tags()) { %>
    <a href="<%= tag.getLink() %>" style="font-size: <%= tag.getWeight() %>px;"%gt;<%= tag.getName() %></a>
    <% } %>
  </div>
  </body>
</html>