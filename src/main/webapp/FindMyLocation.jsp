<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FIND MY LOCATION APPLICATION aka authorizeobo</title>
</head>


<body>
<%
if (request.getParameter("operator")!=null) {
	if (request.getParameter("operator").equals("ATT")) {
		System.out.println(">>>>>>>>>> ");
		System.out.println(">>>>>>>>>> FIND MY LOCATION APP is now redirecting the User Agent to the Authorization server");
		System.out.println(">>>>>>>>>> https://api.att.com/oauth/authorize?client_id=3b13b84938eff99458bed9227b559dcc&scope=TL&redirect_uri=http://authorizeobo-ericssonsandbox.rhcloud.com/authorized");
		System.out.println(">>>>>>>>>>  ");
		response.sendRedirect("https://api.att.com/oauth/authorize?client_id=3b13b84938eff99458bed9227b559dcc&scope=TL&redirect_uri=http://authorizeobo-ericssonsandbox.rhcloud.com/authorized");
	    return;
	}
}
%>
<center><b>AUTHORIZEOBO APPLICATION : finding your location</b><br></center>
<br><div id="carrierSelection">
In OAuth terminology: <br>
- AUTHORIZEOBO is the CLIENT<br>
- The person whose LAT/LONG is being looked up is the RESOURCE OWNER<br>
- AT&T provides the RESOURCE SERVER: it delivers the resource (here the LAT/LONG information of the resource owner) to the client (once authorization of the resource owner has been granted)<br>
- AT&T provides the AUTHORIZATION SERVER: authenticates resource owners and issue grants and access tokens<br><br>
The client (aka application, authorizeobo) does not ask your name/phone number/credentials. Instead, the client redirects you to the authorization server where you, the resource owner, grant access to the protected resource.<br>
The authorization server generates an authorization grant (code) and redirects back to the client with the code. The client then exchanges the authorization code for an access token at the authorization server. <br>
The client then includes the access token in the API request to the resource server, and the resource server will send back the resource (lat/long) of the resource owner. <br>
At no time the client knows whose lat/long it is handling.<br>
Select your operator, click on the "Get Location" button to obtain your phone position<br>
<form action="http://authorizeobo-ericssonsandbox.rhcloud.com/FindMyLocation.jsp" method="GET">
<br>
<INPUT type="radio" name="operator" value="ATT">AT&T<BR>
<INPUT type="radio" name="operator" value="someOperator">OperatorX<BR>
<br>
<INPUT type="submit" name="submit" value="Get Location">
</form><br><br>
REFERENCES:
<table>
<tr>
<td><a href=http://tools.ietf.org/html/rfc5849>OAuth 1.0 RFC5849</a></td>
<td><a href=http://tools.ietf.org/html/rfc6749>OAuth 2.0 RFC6749</a></td>
</tr>
<tr>
<td><a href=http://hueniverse.com>OAuth Information</a></td>
</tr>
<tr>
<td><a href=https://devconnect-api.att.com/docs>ATT developer APIs' Doc</a></td>
<td><a href=http://lprod.code-api-att.com:8080/tl-R2-1-0/TL.jsp>ATT Location sample app</a></td>
<td><a href=https://gist.github.com/3125770>ATT Location sample app code</a></td>
</tr>
</table>
</div>
<div id="map">
<% if (request.getParameter("latitude")!=null && request.getParameter("longitude")!=null) {%>
<iframe width="600" height="400" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="http://maps.google.com/?q=<%=request.getParameter("latitude")%>+<%=request.getParameter("longitude")%>&z=15&output=embed"></iframe>
<%}%>
</div>
</body>
</html>