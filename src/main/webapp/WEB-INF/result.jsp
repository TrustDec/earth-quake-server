<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta charset=utf-8/>
        <title>计算BMI</title> 
    </head>
    <body>
	    <h1>
	    Welcome, and you are 
	    </h1>
        <c:if test="${res=='thin' }">
        	<h1><span style="color:red">thin</span></h1>
        	<h2>Be more careful please.</h2>
        </c:if>
        <c:if test="${res=='fat' }">
        	<h1><span style="color:red">fat</span></h1>
        	<h2>Be more careful please.</h2>
        </c:if>
        <c:if test="${res=='normal' }">
        	<h1><span style="color:red">in good condition</span></h1>
        	<h2>Healthy, exercise more, keep on.</h2>
        </c:if>
        <h2>Thanks for using system again, if you have any suggestion, let me know</h2>
    </body>
</html>