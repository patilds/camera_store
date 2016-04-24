/*  SubmitOrder.java
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
import java.text.*;
import sdsu.*;
import helpers.*;


public class SubmitOrder extends HttpServlet {
        private ServletContext context=null;
    private RequestDispatcher dispatcher = null;
        private String toDo = "";  
          
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        
        Cookie myCookie=null;
        String cookieValue="";
        String toDo = "/WEB-INF/jsp/order.jsp";
        

        for(Cookie cookie : request.getCookies()){
            if(cookie.getName().equals("jadrn039"))
            {
                myCookie=cookie;
                cookieValue=myCookie.getValue();
            }
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

         String sku, query;
         int qt, dbqt, newqt;
         Calendar cal = Calendar.getInstance();
         cal.add(Calendar.DATE, 1);
         SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd");
         myformat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
         String date = myformat.format(cal.getTime());      
         String skuToDelete="";
         
         for(String item : listitems){

           
            sku=item.split("\\|")[0];
            skuToDelete+=sku+",";
            qt=Integer.parseInt(item.split("\\|")[1]);
            
            query="insert into merchandise_out values('"+sku+"','"+date+"',"+qt+");"; 
            DBHelper.doUpdate(query);   
            
            query="select on_hand_quantity from on_hand where sku='"+sku+"';";
            dbqt=Integer.parseInt(DBHelper.doQuery(query).get(0)[0]);
           
            newqt= dbqt-qt;
            query="update on_hand set last_date_modified='"+date+"', on_hand_quantity="+newqt+" where sku='"+sku+"';";
            DBHelper.doUpdate(query);
         }

          HttpSession session = request.getSession(false);
         session.setAttribute("skus", skuToDelete );
          response.sendRedirect("http://jadran.sdsu.edu/jadrn039/ordersuccess.jsp");
        
        /*context = getServletContext();      
        dispatcher = request.getRequestDispatcher(toDo); 
        dispatcher.forward(request, response); */             
    }    
}