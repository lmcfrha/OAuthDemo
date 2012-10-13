<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FIND MY LOCATION APPLICATION AKA authorizeobo</title>
</head>
<body>
AUTHORIZEOBO APPLICATION: it's about finding your LAT/LONG.

In OAuth terminology: 
- AUTHORIZEOBO is the CLIENT
- The person whose LAT/LONG is being looked up is the RESOURCE OWNER
- AT&T is the RESOURCE SERVER: it delivers the resource (here the LAT/LONG information of the resource owner)to the client (once authorization of the resource owner has been granted)

Look, I don't even ask you your name of phone number, just click your operator button and you'll get your phone lat/long (for now, I only have an OAuth relationship with AT&T, so this application is limited to AT&T subscriber):
<a ref="https://api.att.com/oauth/authorize?client_id=3b13b84938eff99458bed9227b559dcc&scope=TL&redirect_uri=http://authorizeobo-ericssonsandbox.rhcloud.com/authorized">
AT&T Subscriber, click here</a>
<BUTTON name="Operator">OperatorX Subscriber, click here</button>
</FORM>


</body>
</html>