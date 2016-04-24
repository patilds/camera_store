$(document).ready( function() {
    var cart = new shopping_cart("jadrn039");
    
    $('#addButton').on('click', function() {
        cart.add($('#sku').val(), $('#qty').val());
        updateDisplay();
        });
        
    $('#deleteButton').on('click', function() {
        cart.delete($('#sku').val());
        updateDisplay();
        });   
        
    $('#quantityButton').on('click', function() {
        cart.setQuantity($('#sku').val(), $('#qty').val());
        updateDisplay();
        });                
        
        
    function updateDisplay() {
        var cartArray = cart.getCartArray();
        var toWrite = "<table>";
        toWrite += "<tr><th>SKU</th><th>Quantity</th></tr>";
        for(i=0; i < cartArray.length; i++) {
            toWrite += "<tr>";
            toWrite += "<td>"+cartArray[i][0]+"</td>";
            toWrite += "<td>"+cartArray[i][1]+"</td>"; 
            toWrite += "</tr>";
            }
        toWrite += "</table>"; 
        $('#cart').html(toWrite); 
        $('#count').text(cart.size());     
        } 
        
    $( "#dialog-modal" ).dialog({
            height: 350,
            width: 500,
            modal: true,
            autoOpen: false
    });
    
    $('#order_button').bind('click', function($e) {       
            $("#dialog-modal").dialog('open');
            });                 
    });
