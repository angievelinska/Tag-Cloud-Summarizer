<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="org.mcavallo.opencloud.*" %>
<%@ page import="java.util.*" %>
<%@ page import="edu.tuhh.servlet.TagCloudServlet" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/opencloud.css"/>
<title>Tag Cloud Summarizer</title>
</head>
<body>

<div style="text-align: center;">

<h3>Tag cloud from text</h3>

<p style="font-size: 90%; width: 80%; text-align: justify; color: #555555;">
To generate a tag cloud enter a search phrase the textarea and click on the "Search" button.
Click on the "Clear" button to remove all tags in the cloud.
</p>

</div>

<br />

<table width="100%">
<tr>
<td>
<form name="tagcloud" action="index.jsp" method="post">

<input type="hidden" name="command" value="">

	<table width="100%">
	<tr>
	<td align="center" valign="top" colspan="2">
	<textarea name="text" rows="5" cols="50"><%= TagCloudServlet.text %></textarea>
	</td>
	</tr>
	<tr>
	<td align="left" valign="top">
	<input type="button" value="Clear Cloud" onclick="document.tagcloud.command.value='CLEAR'; document.tagcloud.submit();" />
	</td>
	<td align="right" valign="top">
	<input type="button" value="Search" onclick="document.tagcloud.command.value='SEARCH'; document.tagcloud.submit();" />
	</td>
	</tr>
	</table>

</form>
</td>

<td width="100%">
<div class="tagcloud" style="margin: auto; width: 80%;">
<%
// Gets output tags
List<Tag> tags = TagCloudServlet.cloud.tags();

if (tags.size() > 0) {
	for (Tag tag : tags) {
%>
	<a class="tag_<%= tag.getWeightInt() %>" href="<%= tag.getLink() %>"><%= tag.getName() %></a>
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
</table>

<br />

</body>
</html>