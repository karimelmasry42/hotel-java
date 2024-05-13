<%@ page import="apa.karim.apaproject.models.RememberGuest" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html" %>
<% RememberGuest.remember(request, session); %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/style.css?<%= new Date() %>">
    <title>Hotel | <%= request.getParameter("title")%>
    </title>
</head>
<body>
