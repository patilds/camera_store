<%@ page import="java.util.*, java.lang.* " %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
          "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
    <title>Teekay's Camera Store</title>
    <link rel="stylesheet"type="text/css" href="/jadrn039/order.css" />
    <script type="text/javascript" src="/jadrn039/order.js"> </script>
       
</head>
<body> 

 <div id="header">
        <span id="home"><a href="http://jadran.sdsu.edu/jadrn039/proj3.html">Home</a></span>
        <h1><a href="http://jadran.sdsu.edu/jadrn039/proj3.html"><img id="logo" src="/jadrn039/images/logo.png" /></a></h1>
    </div>


    <h1>Order Summary</h1>

    <div id="summary">
<table id="summarytable">

<tr> 
    <td> Item </td>
    <td> Quantity </td>
    <td>Price</td>
</tr>

     <%   List<String> items=(ArrayList<String>)request.getAttribute("summary");
    	  for(String item : items){
				out.print("<tr>");
				out.print("<td> <img src='http://jadran.sdsu.edu/~jadrn039/proj1/file_upload/_tk_images/"+item.split(",")[0].toLowerCase()+"'/>"+ item.split(",")[1].split(";")[0]+" </td>");
				out.print("<td> "+item.split(",")[2]+ " </td> ");
                out.print("<td> "+item.split(",")[1].split(";")[1]+ " </td>");
				out.print("</tr>");
    	 }    

      %>     
</table>
      
    </div>
    <div id="totalprice">
     
        <%   
          Float price = Float.valueOf(request.getAttribute("price").toString());
        %>

        <div> <label>Price of Items: </label> <span>$ <%=  price %></span> </div>
        <div> <label>Shipping Cost:</label> <span>$ 5</span> </div>

        <% double tax = (8.0/100) * (price + 5); %>

        <div> <label>Sales Tax:</label> <span>$  <%= String.format("%.2f", tax )  %> </span> </div>
        <div> <label>Total Price:</label> <span>$ <%= String.format("%.2f",price+tax+5) %></span> </div>
    
    </div>

    <div id="adrsSummary">
        <p> <b> Shipping Address:</b></p>
    	<p>Name: <%= request.getAttribute("name") %> </p>
    	<p>Address: <%= request.getAttribute("address") %> </p>
    	<p>City: <%=  request.getAttribute("city") %> </p>
    	<p>State: <%=  request.getAttribute("state") %> </p>
    	<p>Zip: <%= request.getAttribute("zip") %> </p>    	
</div>

    <form method="post" action="/jadrn039/servlet/SubmitOrder">

        <div id="buttonHolder">
            <input type="button" id="cancel" value="Cancel" />
            <input type="submit" id="submit" value="Place Order" />
        </div>

    </form>
</body>

</html>
