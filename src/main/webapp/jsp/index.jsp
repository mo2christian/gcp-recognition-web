<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome Page</title>
    <script type="text/javascript" src="/static/js/image-compressor.common.js"></script>
    <script type="text/javascript" src="/static/js/image-compressor.esm.js"></script>
    <script type="text/javascript" src="/static/js/image-compressor.min.js"></script>
    <script type="text/javascript" src="/static/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="/static/js/app.js"></script>
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
    <center>
        <h2>Welcome page </h2><h4>Version <s:property value="version"/></h4>
        <s:form action="detect" method="post" enctype="multipart/form-data">
            <s:file id="file" name="input.image" label="Image" accept="image/jpeg,image/gif,image/png"/>
            <s:select name="input.target" list="langues" listKey="code" listValue="text" label="Labels language" />
            <s:submit/>
        </s:form>
        <br/><br/><br/><br/>
        <s:if test="%{output != null}">
            <table>
                <tr>
                    <td>
                        <image src="<s:property value='output.url'/>" alt="Smiley face" height="250" width="250"/>
                    </td>
                    <td>
                    </td>
                    <td>
                        <label>Cette image contient</label><br/>
                        <s:iterator value="output.words">
                          <li><s:property /></li>
                        </s:iterator>
                    </td>
                </tr>
            </table>
        </s:if>
    </center>
</body>
</html>