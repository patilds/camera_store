/*  SearchProducts.java
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
import sdsu.*;
import helpers.*;


public class SearchProducts extends HttpServlet {
        private ServletContext context=null;
    private RequestDispatcher dispatcher = null;
        private String toDo = "";  
          
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        
       
        String keyword=request.getParameter("keyword");
        HttpSession session = request.getSession(false);
        String responseString="", colseparator="|", rowseparator="||", sku;

        String query="select sku,vendor.name, category.name, mfdid, retail from products, vendor, category where vendor.name like '%"+keyword+"%' and products.vendorid=vendor.id and products.catid=category.id order by sku;";
        Vector<String []> result = DBHelper.doQuery(query);
        
        if(result.isEmpty())
        {
        query="select sku,vendor.name, category.name, mfdid, retail from products, vendor, category where mfdid like '%"+keyword+"%' and products.vendorid=vendor.id and products.catid=category.id order by sku;";
        result = DBHelper.doQuery(query);            
        }   

        if(result.isEmpty()) 
        {
          query="select sku,vendor.name, category.name, mfdid, retail from products, vendor, category where category like '%"+keyword+"%' and products.vendorid=vendor.id and products.catid=category.id order by sku;";
         result = DBHelper.doQuery(query);
        }        
           
          if(!result.isEmpty())
          {
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
               int qt= Integer.parseInt(result.get(0)[0]);
                if(qt>0)
                {
                    responseString+="in stock";
                }
                 else
                {
                     responseString+="more on the way";
                }            
         }
         else
         {
            responseString+="coming soon";
         }
         responseString+=rowseparator;
     }
        responseString = responseString.substring(0, responseString.length()-2);
                     
    }

      response.setContentType("text/plain");  
         response.setCharacterEncoding("UTF-8"); 
         response.getWriter().write(responseString); 

    }   
}



