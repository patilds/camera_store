import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;
import java.util.*;
import sdsu.*;
import helpers.*;


public class GetProductDetail extends HttpServlet {
        private ServletContext context=null;
    private RequestDispatcher dispatcher = null;
        private String toDo = "";  
          
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        
        String toDo = "/WEB-INF/jsp/product.jsp";
         String sku = request.getParameter("sku");
         String query="select sku,vendor.name, category.name, mfdid, description, features, retail from products, vendor, category where sku='"+sku+"' and products.vendorid=vendor.id and products.catid=category.id order by sku;";
         
         Vector<String[]> result= DBHelper.doQuery(query);
        
         if(result.isEmpty())
         {
           return;
           //response.sendRedirect("http://jadran.sdsu.edu/jadrn039/proj3.html");
         }
         
            String[] array = result.get(0);
          List<String> list= new ArrayList<String>(Arrays.asList(array));

         query="select on_hand_quantity from on_hand where sku='"+sku+"';";
          result= DBHelper.doQuery(query);
           
            if(!result.isEmpty())
            {
               int qt= Integer.parseInt(result.get(0)[0]);
                if(qt>0)
                {
                    list.add("In Stock");
                }
                 else
                {
                     list.add("More on the way");
                }            
            }
            else
            {
                list.add("Coming Soon");
            } 
        


        request.setAttribute("product", list); 
        context = getServletContext();      
        dispatcher = request.getRequestDispatcher(toDo); 
        dispatcher.forward(request, response);              
    }    
}
