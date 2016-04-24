/*  OrderSummary.java
    Karthik Thamatam   
    Account:  jadrn039 
    CS645 
    Project #3
    Spring 2015
 */

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;
import java.util.*;
import sdsu.*;
import helpers.*;


public class OrderSummary extends HttpServlet {
        private ServletContext context=null;
    private RequestDispatcher dispatcher = null;
        private String toDo = "";  
          
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        
        Cookie myCookie;
        String cookieValue="";
        String toDo = "/WEB-INF/jsp/order.jsp";

        String name=request.getParameter("name");
        String address= request.getParameter("shipadrs");
        String city = request.getParameter("shipcity");
        String state = request.getParameter("shipstate");
        String zip = request.getParameter("shipzip");
        String price= request.getParameter("hiddenPrice");

        request.setAttribute("name",name);
        request.setAttribute("address",address);
        request.setAttribute("city",city);
        request.setAttribute("state", state);
        request.setAttribute("zip",zip);
        request.setAttribute("price",price);

        for(Cookie cookie : request.getCookies()){
            if(cookie.getName().equals("jadrn039"))
            {
                myCookie=cookie;
                cookieValue=myCookie.getValue();
            }
        }

        if(cookieValue.equals("")){
            return;
        }  
          
       String[] items = cookieValue.split("\\|\\|"); 
       List<String> listitems;      

         if(items.length==0){

            listitems=new ArrayList<String>();
            listitems.add(cookieValue);
         }
         else
         {
            listitems=new ArrayList<String>(Arrays.asList(items));
         }

         String sku, product, qt, query;
         List<String> summaryList = new ArrayList<String>();
         Vector<String[]> result;

         for(String item : listitems){

           //System.out.println("jadrn039 items check:"+item);
            sku = item.split("\\|")[0];
            qt = item.split("\\|")[1];
            query = "select vendor.name, mfdid, retail from products, vendor where sku='"+sku+"' and products.vendorid=vendor.id;";
            result = DBHelper.doQuery(query);
            product = result.get(0)[0] +" "+ result.get(0)[1]+";"+result.get(0)[2];
            summaryList.add(sku +","+ product+","+qt);            
         }
        
          request.setAttribute("summary", summaryList);        
        
        context = getServletContext();      
        dispatcher = request.getRequestDispatcher(toDo); 
        dispatcher.forward(request, response);              
    }    
}