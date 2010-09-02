<%@ page import="org.mcavallo.opencloud.Cloud" %>
<%@ page import="org.mcavallo.opencloud.Tag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Simple jsp page</title></head>
  <body>
    <div>
    <% for (Tag tag : c.tags()) { %>
    <a href="<%= tag.getLink() %>" style="font-size: <%= tag.getWeight() %>px;"%gt;<%= tag.getName() %></a>
    <% } %>
  </div>
  </body>
</html>
