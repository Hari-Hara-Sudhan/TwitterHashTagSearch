<%@ page import="java.util.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
p {
 display: inline-block;
  font-family: "Helvetica Neue", Roboto, "Segoe UI", Calibri, sans-serif;
  font-size: 12px;
  font-weight: bold;
  line-height: 16px;
  border-color: #eee #ddd #bbb;
  border-radius: 5px;
  border-style: solid;
  border-width: 1px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.15);
  margin: 10px 5px;
  padding: 0 16px 16px 16px;
  max-width: 500px;
}
tweet{
font-size: 16px;
  font-weight: normal;
  line-height: 20px;
  }
aut
{
  color: inherit;
  font-weight: normal;
  text-decoration: none;
  outline: 0 none;
}</style>
<title>Matched tweets</title>
</head>
<body>
<%
//Statement st;
//String myDriver = "org.gjt.mm.mysql.Driver";
//String myUrl = "jdbc:mysql://localhost/testproject";
//Class.forName(myDriver);
//Connection conn = DriverManager.getConnection(myUrl, "root", "root");;
//String sqlquery2 = " select distinct hashtag from twitterdata2" ;
//st =conn.createStatement();
//ResultSet rs2 = st.executeQuery(sqlquery2);
//while(rs2.next())
//{
//rs2.getString(1);
//}
%>

<!--
<div style="background-color:#00bfff">
<br>
<center><h1 style="color:ffffff">#HashTag</h1></center>
<br>
<br>
</div>
-->
<%
int count = 0;
if(request.getAttribute("piList") != null){
ArrayList al = (ArrayList) request.getAttribute("piList");
ArrayList hs = (ArrayList) al.get(0);
//System.out.println(hs.get(5));%>
<div style="background-color:#00bfff">
<br>
<img src="logo.jpeg" style='position:absolute; top:1; left:1;' width='100' height='100'><center><h1><%=hs.get(5).toString()%></h1></center>
<br>
</div>
<table>
<tr>
<td>
<%Iterator itr = al.iterator();
while(itr.hasNext())
	{
	count ++;
	ArrayList pList = (ArrayList) itr.next();
%>
<p>
<tweet><%=pList.get(3).toString()%>
</tweet>
<br>
<br>
<aut>
-<%=pList.get(1).toString()%>
(<%=pList.get(2).toString()%>)
</aut>
</p>
<br>
<br>
<%	}
}%>
</td>
<td></td><td></td>
<td valign='top' align='right'><h3>RECENT SEARCHES<h3><br>
<%Statement st;
String myDriver = "org.gjt.mm.mysql.Driver";
String myUrl = "jdbc:mysql://localhost/testproject";
Class.forName(myDriver);
Connection conn = DriverManager.getConnection(myUrl, "root", "root");;
String sqlquery2 = " select distinct hashtag from twitterdata2" ;
st =conn.createStatement();
ResultSet rs2 = st.executeQuery(sqlquery2);
while(rs2.next())
{
%>
<a href='http://localhost:8080/TwitterHashTagSearch/searchrecent' method='get' name='t1'><%=rs2.getString(1)%></a><br>
<%}
%></td>
</tr>
</table>
<%if( count == 0){
%>
No record found
<%
}
%>	
</body>
</html>