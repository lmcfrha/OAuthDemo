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
		PrintWriter out = response.getWriter();
		 

		
		String code=request.getParameter("code");
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
		System.out.println(">>>>>>>>>> Getting a token: "+body); 
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
		while ((inputLine = in.readLine()) != null) 
		out.println(inputLine);
		System.out.println(">>>>>>>>>> Resp Body:"+ inputLine); 
		in.close();
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
