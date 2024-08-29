<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JK3B08</title>
</head>
<% 
	ArrayList<String[]> result =
		(ArrayList<String[]>) request.getAttribute("result");
%>

<body>
<FORM METHOD="GET" ACTION="./item">
<SELECT NAME="MAKER_CODE">

<% for (String[] ss : result) { %>
		<OPTION VALUE="<%= ss[1] %>">
		<%= ss[0] %>
		</OPTION>
<% } %>


</SELECT>
<INPUT TYPE="SUBMIT" VALUE="絞り込む"/>

<%
	ArrayList<String[]> result1 =
		(ArrayList<String[]>) request.getAttribute("result1");
%>

<table>

<% for (String[] ss1 : result1) { %>
	<tr>
		<th><%= ss1[0] %></th>
		<th><%= ss1[1] %></th>
		<th><%= ss1[2] %></th>
	<tr>
<% } %>

</table>

</FORM>
</body>
</html>