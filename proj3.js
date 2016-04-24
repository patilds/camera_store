/*Thamatam, Karthik    Account:  jadrn039
CS645, Spring 2015
Project #3 */

$(document).ready(function () {

    //get sku,mfid,price,status
    $.get('http://jadran.sdsu.edu/jadrn039/servlet/GetProducts', populateProducts);
    $("#search").keypress(searchProduct);  

});


function populateProducts(response) {
    var rows = response.split('||');
    var i;
    $("ul#items").empty();
    for (i = 0; i < rows.length; i++) {
        var cols = rows[i].split('|');
        var sku = cols[0];
        var vendor = cols[1];
        var category = cols[2];
        var mfdid = cols[3];
        var price = cols[4];
        var status;
        
        if(cols[5]=="null"){
            status="Coming Soon";
        }
        else if(parseInt(cols[5])>0){
            status="In Stock";
        }
        else{
            status="More on the way";
        }

        
        var item = "<li id=" + sku + "> <a><img /> <h4>" + vendor + " " + mfdid + " </h4> <p>" + price + " </p> <p>" + status + " </p> </a> </li>";
        $("ul#items").append(item);
        document.querySelector("li#" + sku + " a img").src = "http://jadran.sdsu.edu/~jadrn039/proj1/file_upload/_tk_images/" + sku.toLowerCase() + ".jpg";
        document.querySelector("li#" + sku + " a").href = "/jadrn039/servlet/GetProductDetail?sku="+sku;
    }
}

function searchProduct(event) {

    var key = event.keyCode || event.charCode;
    if (key == 13) {
        var keyword = document.getElementById("search").value;
        if (keyword == null || keyword == "") {
            return;
        }
        $.get('http://jadran.sdsu.edu/jadrn039/servlet/SearchProducts?keyword='+keyword, populateProducts);
    }
}