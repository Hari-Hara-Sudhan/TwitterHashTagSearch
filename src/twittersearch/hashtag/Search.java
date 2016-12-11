package twittersearch.hashtag;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final int count = 100;
    static long sinceId = 0;
    static long numberOfTweets = 0;
    static int newtweets = 0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	
	private static boolean checkIfSinceTweetsAreAvaliable(Twitter twitter,String hashtag) {
        Query query = new Query(hashtag);
        query.setCount(count);
        query.setSinceId(sinceId);
        try {
            QueryResult result = twitter.search(query);
            if(result.getTweets()==null || result.getTweets().isEmpty()){
                query = null;
                return false;
            }
        } catch (TwitterException te) {
            System.out.println("Couldn't connect: " + te);
            System.exit(-1);
        }catch (Exception e) {
            System.out.println("Something went wrong: " + e);
            System.exit(-1);
        }
        return true;
    }
    
    private static void getTweets(Query query, Twitter twitter, String mode,String hashtag) {
    	try
    	{
        boolean getTweets=true;
        long maxId = 0;
        long whileCount=0;
        long tid=0;
        Statement st;
	    String myDriver = "org.gjt.mm.mysql.Driver";
	    String myUrl = "jdbc:mysql://localhost/testproject";
	    Class.forName(myDriver);
	    Connection conn = DriverManager.getConnection(myUrl, "root", "root");;
	    
	    String sqlquery = " insert into twitterdata2 (username, screenname, tweets, tweetid, hashtag)"
		        + " values (?, ?, ?, ?, ?)";
	    String sqlquery2 = " select max(tweetid) from twitterdata2 where hashtag='#" +hashtag+ "'" ;
	    st =conn.createStatement();
        ResultSet rs2 = st.executeQuery(sqlquery2);
        if(rs2.first())
        {
          tid =rs2.getLong(1);
          System.out.println("tid is" + tid);
        }
	    
        while (getTweets){
            try {
                QueryResult result = twitter.search(query);
                if(result.getTweets()==null || result.getTweets().isEmpty()){
                    getTweets=false;
                }else{
                    //System.out.println("***********************************************");
                    System.out.println("Gathered " + result.getTweets().size() + " tweets");
                    int forCount=0;
                    for (Status status: result.getTweets()) {
                        if(whileCount == 0 && forCount == 0){
                            sinceId = status.getId();
                            System.out.println("sinceId= "+sinceId);
                        }
                        
                        if(sinceId == tid || status.getId()==tid)
                        	return;
                        //System.out.println("Id= "+status.getId());
                        //System.out.println("@" + status.getUser().getScreenName() + " : "+status.getUser().getName()+"--------"+status.getText());

                        
		                //Status t = (Status) tweets.get(i);
                        String user = status.getUser().getName();
		                String screenname = "@"+status.getUser().getScreenName();
		                String msg = status.getText();
		                long tweetid = status.getId();
		                String hstag= "#"+hashtag;
		              
	                   // create the mysql insert preparedstatement
		               
		                newtweets++;
		               
	                   PreparedStatement preparedStmt =conn.prepareStatement(sqlquery);
	                   preparedStmt.setString (1, user);
	                   preparedStmt.setString (2, screenname);
	                   preparedStmt.setString (3, msg);
	                   preparedStmt.setLong(4, tweetid);
	                   preparedStmt.setString(5, hstag);
	                   // execute the preparedstatement
	                   preparedStmt.execute();
		               
                        if(forCount == result.getTweets().size()-1){
                            maxId = status.getId();
                            //System.out.println("maxId= "+maxId);
                        }
                        //System.out.println("");
                        forCount++;
                    }
                    numberOfTweets=numberOfTweets+result.getTweets().size();
                    query.setMaxId(maxId-1);
                }
            }catch (TwitterException te) {
                System.out.println("Couldn't connect: " + te);
                System.exit(-1);
            }catch (Exception e) {
                System.out.println("Something went wrong: " + e);
                System.exit(-1);
            }
            whileCount++;
        }
        //System.out.println("Total tweets count======="+numberOfTweets);
    	}
    	catch(Exception e)
    	{
    	e.printStackTrace();	
    	}
    }
    
    
    
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    
		response.setIntHeader("Refresh", 120);
		//newtweets=0;
		System.out.println("calling the servlet again");
		
		PrintWriter pr=response.getWriter();
	    //pr.println("Success");
	      // create a mysql database connection
		  String hashtag= request.getParameter("t1");
	      //String tag = hashtag;
	      
	      ConfigurationBuilder cb = new ConfigurationBuilder();
		    cb.setDebugEnabled(true)
		      .setOAuthConsumerKey("8RYfiOpHq7KyB9MQi8hAu6Sy2")
		      .setOAuthConsumerSecret("i5aFSYFxFn3Ol5wnK0BiVbNxJULybAQmBO5xMWvamiRWRXjxnf")
		      .setOAuthAccessToken("282967636-ZZgtysH60iwXyYGjQuLLwgmDNNHX1yuXqV3BwsVr")
		      .setOAuthAccessTokenSecret("DlAGRS5vylRfR3IDj6qkreXcF4hiE8i3879Hi7jxlxhKO");
	    
	      // create a sql date object so we can use seit in our INSERT statement
	      //Calendar calendar = Calendar.getInstance();
	      //java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

	      
	      // the mysql insert statement
	      //String sqlquery = " insert into twitterdata (User_name, Tweets, Hashtag)"
	       // + " values (?, ?, ?)";

	      Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		    Query queryMax = new Query(hashtag);
		    queryMax.setCount(count);
            getTweets(queryMax, twitter, "maxId",hashtag);
            queryMax = null;


            do{
                Query querySince = new Query(hashtag);
                querySince.setCount(count);
                querySince.setSinceId(sinceId);
                getTweets(querySince, twitter, "sinceId",hashtag);
                querySince = null;
            }while(checkIfSinceTweetsAreAvaliable(twitter,hashtag));

            response.setContentType("text/html");
    		PrintWriter out = response.getWriter();
    		String myDriver = "org.gjt.mm.mysql.Driver";
    	    String myUrl = "jdbc:mysql://localhost/testproject";
    	    Statement st;
    	    
    	    try{
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
