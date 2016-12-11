<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
img{
  width: 75px;
  height: 75px;
}
</style>
<title>Twitter Hash Tag search</title>
</head>
<body>
<br>
<div style="background-color:#00bfff">
<br>
<img src="logo.jpeg" style='position:absolute; top:1; left:1;'><center><h1>Twitter Search</h1></center></center>
<br>
</div>
<form name='f1' action='http://localhost:8080/TwitterHashTagSearch/Search' method='get'>
<br>
<br>
<center><h2><b>Enter the Hashtag to search</b><h2></center> 
<center><input type='text' name='t1'><br></center>
<br>
<br>
<center><input type='submit' name='submit' value='submit'></center>
</form>
</body>
</html>