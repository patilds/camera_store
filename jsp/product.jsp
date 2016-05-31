<%@page import="java.util.*"%>


<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
          "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Teekay's Camera Store</title>
    <link rel="stylesheet" type="text/css" href="/jadrn039/product.css" />
    <link type="text/css" rel="stylesheet" href="/jquery/jquery-ui-1.11.2/jquery-ui.css" />
    <script type="text/javascript" src="/jquery/jquery.js"></script>
    <script type="text/javascript" src="/jquery/jQueryUI.js"></script>
    <script type="text/javascript" src="/jadrn039/product.js"></script>
    <script type="text/javascript" src="/jadrn039/shopping_cart.js"></script>
   
</head>
<body>

    <div id="header">
        <span id="home"><a href="http://jadran.sdsu.edu/jadrn039/proj3.html">Home</a></span>
        <h1><a href="http://jadran.sdsu.edu/jadrn039/proj3.html"><img id="logo" src="/jadrn039/images/logo.png" /></a></h1>
       
         <div id="cart">
            <a href="#"><img id="carticon" src="/jadrn039/images/carticon.png" />View Cart</a>
             <span id="count">0</span> item(s)             
        </div>

        <div id="shoppingcart" title="Shopping Cart">
            <table id="carttable"></table>

            <span id="totalPriceLabel">Total Price:</span><span id="totalPrice"></span>
            <input type="button" id="checkout" value="Checkout Now" />
        </div>
        
        <div id="checkoutdialog" title="Checkout">
            <form id="checkoutform" method="post" action="/jadrn039/servlet/OrderSummary">

                <div class="label">Billing Address </div>
                <div>
                    <label>Address:</label><input type="text" id="billadrs" required=required autofocus />
                </div>
                <div>
                    <label>City:</label> <input type="text" id="billcity" required=required />
                </div>
                <div>
                    <label>State:</label> <input type="text" id="billstate" required=required />
                </div>
                <div>
                    <label>Zip:</label> <input type="text" id="billzip" required=required />
                </div>

                <div class="label">Payment Details</div>
                <div>
                    <label>Name:</label> <input type="text" name="name" id="name" required=required />
                </div>
                <div>
                    <label>Credit card number:</label><input type="text" name="cardnum" id="cardnum" required=required />
                </div>
                <div>
                    <label>Security code:</label><input type="text" name="cvv" id="cvv" required=required />
                </div>
                <div>
                    <label>Expiration Month:</label><input type="text" maxlength="2" name="month" id="month" placeholder="MM" required=required />  Year <input type="text" maxlength="2" name="year" id="year" placeholder="YY" required=required />
                </div>

                <div class="label">Shipping Address</div> &nbsp; <input type="checkbox" id="sameadrs" />Same as Billing address
                <div>
                    <label>Address:</label><input type="text" name="shipadrs" id="shipadrs" required=required />
                </div>
                <div>
                    <label>City:</label> <input type="text" name="shipcity" id="shipcity" required=required />
                </div>
                <div>
                    <label>State:</label> <input type="text" name="shipstate" id="shipstate" required=required />
                </div>
                <div>
                    <label>Zip:</label> <input type="text" name="shipzip" id="shipzip" required=required />
                </div>

                <div id="buttonHolder">
                    <input type="reset" id="reset" value="Reset" />
                    <input type="submit" id="submit" value="Continue" />
                </div>

                <input name="hiddenPrice" id="hiddenPrice" hidden=hidden /> 
            </form>
        </div>
    </div>

    <%  List<String> list=(ArrayList<String>)request.getAttribute("product");  %>
    
        <div id="product">        
            
            <img src="http://jadran.sdsu.edu/~jadrn039/proj1/file_upload/_tk_images/<%= list.get(0).toLowerCase()%>" />
        </div>

            <div id="addcartdiv"> 
               <label for="qty" id="qtLabel">Quantity:</label> <input type="number" min="1" id="qty" value="1" required/><br /><br />               
                <input type="button" name="addtocart" id="addButton" value="Add To Cart" /> <br />
             </div>
               <div id="addNotify">Product added to cart</div>
                
                                        <div id="prodDetails">
                                            <h2><%= list.get(1) %> <%= list.get(3) %></h2>
                                            <p id="status"> <%=list.get(7) %></p>
                                            <p id="desc"> <b>Description:</b> <%= list.get(4) %></p>
                                            <p id="features"> <b>Features:</b> <%= list.get(5) %></p>
                                            <p id="price"> <b>Price:</b> <%= list.get(6) %></p>
                                            <p hidden=hidden id="sku"><%= list.get(0)%></p>
                                        </div>
        
</body>

</html>
