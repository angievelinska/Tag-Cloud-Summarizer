<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="org.mcavallo.opencloud.Cloud" %>
<%@ page import="org.mcavallo.opencloud.Tag" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" type="text/css" href="css/opencloud.css"/>
  <title>Tag Cloud Summarizer</title>
</head>
<body>

<div style="text-align: center;">

  <h3>Tag cloud from search text</h3>

  <p style="font-size: 90%; width: 80%; text-align: justify; color: #555555;">
    To generate a tag cloud enter a search phrase the textarea and click on the "Search" button.
    Click on the "Clear" button to remove all tags in the cloud.
  </p>

</div>

<br/>

<table width="100%">
  <tr>
    <td>
      <form name="cloudform" action="tagcloud.do" method="POST">

        <%
          String searchText = (String) request.getAttribute("searchText");
        %>
        <input type="hidden" name="command" value="">

        <table width="100%">
          <tr>
            <td align="center" valign="top" colspan="2">
              <textarea name="searchText" rows="5" cols="50"><%= searchText %></textarea>
            </td>
          </tr>
          <tr>
            <td align="left" valign="top">
              <input type="button" value="Clear Cloud"
                     onclick="document.cloudform.command.value='CLEAR'; document.cloudform.submit();"/>
            </td>
            <td align="right" valign="top">
              <input type="button" value="Search" onclick="document.cloudform.command.value='SEARCH'; document.cloudform.submit();"/>
            </td>
          </tr>
        </table>

      </form>
    </td>

    <td width="100%">
      <div class="tagcloud" style="margin: auto; width: 80%;">
        <%
          Cloud tagcloud = (Cloud) request.getAttribute("tagcloud");

          List<Tag> tags = tagcloud.tags();

          if (tags.size() > 0) {
            for (Tag tag : tags) {
        %>
        <a class="tag_<%= tag.getWeightInt() %>" href="<%= tag.getLink() %>"><%= tag.getName() %>
        </a>
        <%
          }
        } else {
        %>
        <p style="text-align: center">No tags to display.</p>
        <%
          }
        %>

      </div>
    </td>

  </tr>
  <tr>
    <%@ include file="searchResults.jsp" %>
  </tr>
</table>

<br/>

</body>
</html>