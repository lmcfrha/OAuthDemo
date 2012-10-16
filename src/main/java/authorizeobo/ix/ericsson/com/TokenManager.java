package authorizeobo.ix.ericsson.com;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TokenManager
 */
@WebServlet(
		description = "Receives the authorization code, retrieves the access token", 
		urlPatterns = { 
				"/TokenManager", 
				"/authorized"
		}, 
		initParams = { 
				@WebInitParam(name = "Author", value = "EMC", description = "lmcfrha")
		})
public class TokenManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TokenManager() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// At the end of the Authorization process from ATT, the resource owner is redirected here, with the auth code.

		
		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
		String path = request.getContextPath();
		String query = request.getQueryString();
		String pathinfo =request.getPathInfo();
		if (path != null) System.out.println(">>>>>>>>>> Authorization Server redirected back to the App with Contect Path - "+path); 
		if (query != null) System.out.println(">>>>>>>>>> Authorization Server redirected back to the App with Query String - "+query);  
		if (pathinfo != null) System.out.println(">>>>>>>>>> Authorization Server redirected back to the App with Path Info    - "+pathinfo);  
		
		String access_token=null;
		
		String code=request.getParameter("code");
		if (code !=null) System.out.println(">>>>>>>>>> Authorization Server redirected back to the App with an Authorization token: "+code);  
		String mdn=request.getParameter("mdn");
		String appid=request.getParameter("appid");
		URL oauthURL = new URL("https://api.att.com/oauth/access_token");
		
		HttpsURLConnection connection = (HttpsURLConnection) oauthURL.openConnection();
		connection.setDoOutput(true); 
		connection.setDoInput(true);
		connection.setRequestMethod("POST"); 
		connection.setRequestProperty("Host","api.att.com");
		connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		connection.setRequestProperty("Accept","application/json");
		String body=null;
		if (code == null) {
			body="client_id=3b13b84938eff99458bed9227b559dcc&"+
				"client_secret=614a87f363ca651e&"+
				"grant_type=client_credentials&"+
				"scope="+"SMS";
		} else {
			body="client_id=3b13b84938eff99458bed9227b559dcc&"+
					"client_secret=614a87f363ca651e&"+
					"grant_type=authorization_code&"+
					"code="+code;	
		}
		System.out.println(">>>>>>>>>> App must now exchange the Authorization code (Authorization Grant) for an access token at the Authorization server: \n\r"+body); 
		connection.setRequestProperty("Content-length",String.valueOf(body.length())); 
		//Send request      
		DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
		
		wr.writeBytes (body);      
		wr.flush ();      
		wr.close ();
		
		System.out.println(">>>>>>>>>> Resp Code:"+connection.getResponseCode()); 
		System.out.println(">>>>>>>>>> Resp Message:"+ connection.getResponseMessage()); 

		connection.setReadTimeout(10000);
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
//			out.println(inputLine);
			System.out.println(">>>>>>>>>> Resp Body:"+ inputLine); 
			if (inputLine.contains("access_token")) {
				String[] access_token_AVP = inputLine.split(":");
				access_token = access_token_AVP[1].substring(1, access_token_AVP[1].length()-2); 
			}
		}
		in.close();
//		out.flush();
//		out.close();
		
		if (access_token != null) {
			System.out.println(">>>>>>>>>> The App can now use the access_token in API requests:"+access_token);
			
			// Get location now that we have a token:
			URL locationURL = new URL("https://api.att.com/2/devices/location?requestedAccuracy=1000");
			HttpsURLConnection connection1 = (HttpsURLConnection) locationURL.openConnection();
			connection1.setRequestMethod("GET"); 
			connection1.setRequestProperty("Authorization","Bearer "+access_token);
			connection1.setRequestProperty("Host","api.att.com");
			connection1.setRequestProperty("Accept","application/json");
			connection1.connect();

			System.out.println(">>>>>>>>>> Getting location with the access token"); 
			System.out.println("           "+locationURL.toString());
			System.out.println("           Authorization: Bearer "+access_token);
			System.out.println(">>>>>>>>>> Resp Code:"+connection1.getResponseCode()); 
			System.out.println(">>>>>>>>>> Resp Message:"+ connection1.getResponseMessage()); 

			connection1.setReadTimeout(10000);
			BufferedReader in1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
			String inputLine1;
			String latitude=null;
			String longitude=null;
			while ((inputLine1 = in1.readLine()) != null) {
//				out.println(inputLine1);
				if (inputLine1.contains("latitude")) {
					latitude = inputLine1.split("\"")[3];
				}
				if (inputLine1.contains("longitude")) {
					longitude = inputLine1.split("\"")[3];				
				}				
				System.out.println(">>>>>>>>>> Resp Body:"+ inputLine1);
			}
			if (latitude!=null && longitude!=null) {
				System.out.println(">>>>>>>>>> Now, redirecting to back to eh App JSP page: http://authorizeobo-ericssonsandbox.rhcloud.com/FindMyLocation.jsp?latitude="+latitude+"&longitude="+longitude);
				String url = "http://authorizeobo-ericssonsandbox.rhcloud.com/FindMyLocation.jsp?latitude="+latitude+"&longitude="+longitude;
			    response.sendRedirect(response.encodeRedirectURL(url));
			}				
				
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
