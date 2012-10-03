package authorizeobo.ix.ericsson.com;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ConsentManager
 */
@WebServlet(
		description = "Handles the consent request from the resource owner", 
		urlPatterns = { 
				"/ConsentManager", 
				"/initiateConsent"
		}, 
		initParams = { 
				@WebInitParam(name = "Author", value = "lmcfrha", description = "Junior Senior - Senior Junior")
		})
public class ConsentManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsentManager() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "https://api.att.com/oauth/authorize?client_id=3b13b84938eff99458bed9227b559dcc&scope=TL&redirect_uri=http://authorizeobo-ericssonsandbox.rhcloud.com/authorized?mdn="
		        +request.getParameter("mdn")
		        +"&applicationid="
		        +request.getParameter("applicationId");
	    if (url != null)
	    {
	      response.sendRedirect(url);
	    }
	    else
	    {
	      PrintWriter printWriter = response.getWriter();

	      printWriter.println("No redirection.");
	    }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
