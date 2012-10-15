<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FIND MY LOCATION APPLICATION AKA authorizeobo</title>
</head>


<body>
<%
if (request.getParameter("operator")!=null) {
	if (request.getParameter("operator").equals("ATT")) {
		response.sendRedirect("https://api.att.com/oauth/authorize?client_id=3b13b84938eff99458bed9227b559dcc&scope=TL&redirect_uri=http://authorizeobo-ericssonsandbox.rhcloud.com/authorized");
	    return;
	}
}
%>
<b>AUTHORIZEOBO APPLICATION: finding your location</b><br>
<br><div id="carrierSelection">
In OAuth terminology: <br>
- AUTHORIZEOBO is the CLIENT<br>
- The person whose LAT/LONG is being looked up is the RESOURCE OWNER<br>
- AT&T is the RESOURCE SERVER: it delivers the resource (here the LAT/LONG information of the resource owner) to the client (once authorization of the resource owner has been granted)<br>
<br>
The client (aka application, authorizaobo) does not ask your name or phone number. This is between you and the authorization server. What the application gets is an authorization token that it exchanges for an access token at the authorization server. <br>
The access token is included in the API request to the resource server, and the resource server will send back the lat/long of the resource owner. <br>
At no time the client knows whose lat/long it is. <br>
Select your operator button, click on the Get Location Button. and you'll get your phone lat/long (for now, I only have an OAuth relationship with AT&T, so this application is limited to AT&T subscriber):<br>
<form action="http://authorizeobo-ericssonsandbox.rhcloud.com/FindMyLocation.jsp" method="GET">
<br>
<INPUT type="radio" name="operator" value="ATT">AT&T<BR>
<INPUT type="radio" name="operator" value="someOperator">OperatorX<BR>
<br>
<INPUT type="submit" name="submit" value="Get Location">
</form><br><br>
<a href=https://devconnect-api.att.com/docs>ATT developer APIs' Doc</a><br>
<a href=http://lprod.code-api-att.com:8080/tl-R2-1-0/TL.jsp>ATT Location sample app</a><br>
<a href=https://gist.github.com/3125770>ATT Location sample app code</a>
</div>
<div id="map">
<% if (request.getParameter("latitude")!=null && request.getParameter("longitude")!=null) {%>
<iframe width="600" height="400" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="http://maps.google.com/?q=<%=request.getParameter("latitude")%>+<%=request.getParameter("longitude")%>&z=15&output=embed"></iframe>
<%}%>
</div>
</body>
</html>