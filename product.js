var skumap = {};
var cart;
$(document).ready(function () {

    $.get('http://jadran.sdsu.edu/jadrn039/servlet/GetProducts', mapskuToProduct);
    cart = new shopping_cart("jadrn039");
    document.getElementById("addNotify").style.display = 'none';
    
    $('#addButton').on('click', function () {
       
        var sku=$('#sku').html();
        var tempcart = cart.getCartArray();
        var cartqt;

        for(i=0; i<tempcart.length; i++){
            if(tempcart[i][0]==sku){
                    cartqt=tempcart[i][1];
                    break;
            }
        }

        var qt = parseInt($("#qty").val());

        var product = getProductFromSKU(sku);

        if (isNaN(qt)) {
            qt = 1;
        }

        if(parseInt(product.qt) < qt + parseInt(parseInt(cartqt))){
            alert("Sorry, you have requested more quantity than available");
            return;
        }

        cart.add(product.sku, qt);
        updateDisplay();
        document.getElementById("addNotify").style.display = 'inline';
        setTimeout(function () {
            $('#addNotify').fadeOut('slow');
        }, 2000);
    });

   
    $("#shoppingcart").dialog({
        height: 600,
        width: 800,
        modal: true,
        autoOpen: false,
        closeOnEscape: true
    }).prev(".ui-dialog-titlebar").css("background","#173447");


    $("#checkoutdialog").dialog({
        height: 820,
        width: 800,
        modal: true,
        autoOpen: false,
        closeOnEscape: true
    }).prev(".ui-dialog-titlebar").css("background","#173447");


    $('#cart').bind('click', function ($e) {
        $("#shoppingcart").dialog('open');
    });

       
    $('#checkout').bind('click', function ($e) {
        $("#checkoutdialog").dialog('open');
    });    
   

    $('#order_button').bind('click', function ($e) {
        $("#dialog-modal").dialog('open');
    });


    $("#sameadrs").change(function () {
        if (this.checked) {
            copyshippingaddress();
        }
        else {
            clearShippingAddress();
        }
    });

    var status=document.getElementById("status").innerHTML.trim();
    if (status == "Coming Soon" || status=="More on the way")
    {        
        document.getElementById("addButton").style.visibility ='hidden';
        document.getElementById("qtLabel").style.display = 'none'; 
        document.getElementById("qty").style.display = 'none';
        document.getElementById("status").style.color="#e82727";
    }       

});

function copyshippingaddress() {
    document.getElementById("shipadrs").value = document.getElementById("billadrs").value;
    document.getElementById("shipcity").value = document.getElementById("billcity").value;
    document.getElementById("shipstate").value = document.getElementById("billstate").value;
    document.getElementById("shipzip").value = document.getElementById("billzip").value;
}

function clearShippingAddress() {
    document.getElementById("shipadrs").value = "";
    document.getElementById("shipcity").value = "";
    document.getElementById("shipstate").value = "";
    document.getElementById("shipzip").value = "";
}


function updateDisplay() {
    var cartArray = cart.getCartArray();
    var totalPriceElement = document.getElementById("totalPrice");
    // var toWrite;
    $("#carttable").empty();
    var table = document.getElementById("carttable");
    var row, name, qt, price, removebtn;
    var totalprice=0,priceval=0,quantity=0;

    if (cartArray.length > 0) {

        row = table.insertRow(-1);
        name = row.insertCell(0);
        qt = row.insertCell(1);
        price= row.insertCell(2);
        name.innerHTML = "Item";
        qt.innerHTML = "Quantity";
        price.innerHTML="Price";
        var product;

        for (i = 0; i < cartArray.length; i++) {

            row = table.insertRow(-1);
            row.id = "remove" + (i + 1);
            name = row.insertCell(0);
            qt = row.insertCell(1);
            price=row.insertCell(2);
            removebtn = row.insertCell(3);
            product = getProductFromSKU(cartArray[i][0]);
            name.innerHTML ="<img src='http://jadran.sdsu.edu/~jadrn039/proj1/file_upload/_tk_images/"+cartArray[i][0].toLowerCase()+"' />" + product.vendor +" " + product.id;
            qt.innerHTML = "<input type='number' class='changeqt' min='1' max='"+product.qt+"' id='change"+product.sku+"' value='"+cartArray[i][1]+"'/>";
            price.innerHTML="<span id='price"+product.sku+"'> "+product.price+"</span>";
            removebtn.innerHTML = "<input type='button' class='removeFromCart' name='"+(i+1)+"' id='remove" + product.sku + "' value='Remove' />"
            document.getElementById("remove" + product.sku).onclick = removeItemFromCart;
            document.getElementById("change"+product.sku).onchange= updateQuantity;
            priceval = parseFloat(product.price.replace("$",""));
            quantity = parseInt(cartArray[i][1])
            totalprice += (priceval * quantity);
        }

        totalprice = totalprice.toFixed(2);
        document.getElementById("checkout").style.display="block";
        totalPriceElement.innerHTML= "$"+totalprice;
        document.getElementById("hiddenPrice").value=totalprice;
        totalPriceElement.style.display="inline";
        document.getElementById("totalPriceLabel").style.display="inline";
       
    }
    else {
        row = table.insertRow(-1);
        name = row.insertCell(0);
        name.innerHTML = "No items in cart";
        document.getElementById("hiddenPrice").value="";
        totalPriceElement.value="";
        totalPriceElement.style.display="none";
        document.getElementById("totalPriceLabel").style.display="none";
        document.getElementById("checkout").style.display="none";
    }
   
    $('#count').text(cart.size());
}


function updateQuantity(e){
    var sku=e.target.id;
    sku=sku.substr(-7);
    var newqt=e.target.value;
    cart.setQuantity(sku,newqt);
    updateDisplay();
}


function removeItemFromCart(e) {
    var pos = e.target.name;
    var sku = e.target.id;
    sku = sku.substr(-7);
    cart.delete(sku);
    var table = document.getElementById("carttable");
    table.deleteRow(pos);
    updateDisplay();
}

function mapskuToProduct(response) {

    var rows = response.split('||');
    var i;    
    
    for (i = 0; i < rows.length; i++) {
        var cols = rows[i].split('|');
        var product = new Object();
        product.sku=cols[0];
        product.vendor=cols[1];
        product.id=cols[3];
        product.price=cols[4];  
        product.qt=cols[5];         
        skumap[cols[0]] = product;
       
    }
    updateDisplay();
}

function getProductFromSKU(sku) {
    if (sku in skumap) {
        return skumap[sku];
    }
}