

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class searchrecent
 */
@WebServlet("/searchrecent")
public class searchrecent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final int count = 100;
    static long sinceId = 0;
    static long numberOfTweets = 0;
    static int newtweets = 0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchrecent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		try{
			
    		PrintWriter out = response.getWriter();
    		String myDriver = "org.gjt.mm.mysql.Driver";
    	    String myUrl = "jdbc:mysql://localhost/testproject";
    	    Statement st;
    	    
	    	Class.forName(myDriver);
		    Connection conn = DriverManager.getConnection(myUrl, "root", "root");
		    System.out.println("connected");
		    String pid = request.getParameter("t1");
		    
		    ArrayList<String> al = null;
		    ArrayList<ArrayList<String>> pid_list = new ArrayList<ArrayList<String>>();
		    String query = "select * from twitterdata2 where hashtag='#" + pid + "' order by tweetid desc; ";
		    String query2 = "select count(*) from twitterdata where hashtag='#" + pid + "' ";
		    
		    //System.out.println("query " + query);
		    st = conn.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    //System.out.println(rs.getRow());
		    
		    while(rs.next())
		    {
		    	al = new ArrayList<String>();
		    	
		    	al.add(rs.getString(1));
		    	al.add(rs.getString(2));
		    	al.add(rs.getString(3));
		    	al.add(rs.getString(4));
		    	al.add(rs.getString(5));
		    	al.add(rs.getString(6));
		    	
		    	pid_list.add(al);
		    }
		    if(newtweets>0)
		    {
		    	System.out.println("page will be refreshed");
		    	System.out.println("number of new tweets is " + newtweets);
		        request.setAttribute("piList", pid_list);
		        RequestDispatcher view = request.getRequestDispatcher("/searchresults.jsp");
		        view.forward(request, response);
		    }
		    conn.close();
		    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }


	}
}
