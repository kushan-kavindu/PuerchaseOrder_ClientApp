package com;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/PurchaseAPI")
public class PurchaseAPI extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	Purchase purchaseObj;   
    
    public PurchaseAPI() 
    {
        super();
        purchaseObj = new Purchase();
        
    }

	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String output = purchaseObj.insertPurchase(
				request.getParameter("purchaseCode"),
				request.getParameter("purchaseType"),
				request.getParameter("TotalPrice"),
				request.getParameter("purchaseDesc"),
				request.getParameter("purchaseDate"));
		response.getWriter().write(output);
	}

	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Map<String, String> paras = getParasMap(request);
		
		String output = purchaseObj.updatePurchase(paras.get("hidPurchaseIDSave").toString(),
													paras.get("purchaseCode"),
													paras.get("purchaseType"),
													paras.get("TotalPrice"),
													paras.get("purchaseDesc"),
													paras.get("purchaseDate"));
		response.getWriter().write(output);
		
	}

	
	
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Map<String, String> paras = getParasMap(request);
		
		String output = purchaseObj.deletePurchase(paras.get("purchaseID").toString());
		
		response.getWriter().write(output);
		
	}
	
	
	// Convert request parameters to a Map
		private static Map<String,String> getParasMap(HttpServletRequest request)
		{
			Map<String, String> map = new HashMap<String, String>();
			
			try
			{
				Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
				String queryString = scanner.hasNext() ?
									scanner.useDelimiter("\\A").next() : "";
				scanner.close();
				
				String[] params = queryString.split("&");
				for (String param : params)
				{
					String[] p = param.split("=");
					map.put(p[0], java.net.URLDecoder.decode(p[1], StandardCharsets.UTF_8.name()));
				}
			}
			catch (Exception e)
			{
			}
			return map;
		}

}
