<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
</head>
<body>
    <center>
        <h2>Error while detecting label</h2>
        <a href="/">Retour à la page d''accueil</a>
     </center>
    <s:actionerror/>
    <p>
        <s:property value="%{exception.message}"/>
    </p>
     <hr/>
     <h3>Technical Details</h3>
     <p>
         <s:property value="%{exceptionStack}"/>
     </p>
</body>
</html>