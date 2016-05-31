
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;
import sdsu.*;
import helpers.*;


public class GetProducts extends HttpServlet {
        private ServletContext context=null;
    private RequestDispatcher dispatcher = null;
        private String toDo = "";  
          
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        
       
        HttpSession session = request.getSession(false);
        String responseString="";
        
        
            String sku, colseparator="|", rowseparator="||";

            String query="select sku,vendor.name, category.name, mfdid, retail from products, vendor, category where products.vendorid=vendor.id and products.catid=category.id order by sku;";
            Vector<String []> result = DBHelper.doQuery(query);            
           
             for(String[] row : result)
             {

                sku=row[0];   
                for(String column: row)
                {
                    responseString+=column+colseparator;
                }

            query="select on_hand_quantity from on_hand where sku='"+sku+"';";
            result= DBHelper.doQuery(query);
            if(result.size()>0)
            {
               //int qt= Integer.parseInt(result.get(0)[0]);
                responseString+=result.get(0)[0];
         }                  
         
         else
         {
            responseString+="null";
         }
         responseString+=rowseparator;
     }
         responseString = responseString.substring(0, responseString.length()-2);
         response.setContentType("text/plain");  
         response.setCharacterEncoding("UTF-8"); 
         response.getWriter().write(responseString);              
    }    
}



