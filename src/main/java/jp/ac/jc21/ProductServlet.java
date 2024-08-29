package jp.ac.jc21;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet(urlPatterns = { "/item" })
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final String dbServer = "192.168.54.231";
	final String dbPort = "3306";
	final String dbName = "test2023";
	final String user = "test2023";
	final String pass = "test2023";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String url = "jdbc:mysql://"+dbServer+"/"+dbName;
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append("<h2>Connect to : ").append(url).append("</h2>");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection  conn = DriverManager.getConnection(url, user, pass);
			
			String sql ="SELECT MAKER_CODE,MAKER_NAME FROM MAKER";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			ResultSet rs = statement.executeQuery();
			
			ArrayList<String[]> result = new ArrayList<>();
			while(rs.next()==true) {
				String[] s = new String[2];
				s[0]=rs.getString("MAKER_NAME");
				s[1]=rs.getString("MAKER_CODE");
				result.add(s);
			}
			
			request.setAttribute("result", result);
			
			String MAKER_CODE = request.getParameter("MAKER_CODE");
			if(MAKER_CODE == null) {
				// ↓全件表示したい
				//３つ目をメーカーネーム
				String sql1 ="SELECT A.PRODUCT_CODE, A.PRODUCT_NAME, B.MAKER_NAME FROM PRODUCT A INNER JOIN MAKER B ON A.MAKER_CODE = B.MAKER_CODE";
				
				PreparedStatement statement1 = conn.prepareStatement(sql1);
				
				ResultSet rs1 = statement1.executeQuery();
				
				ArrayList<String[]> result1 = new ArrayList<>();
				while(rs1.next()==true) {
					String[] s1 = new String[3];
					s1[0]=rs1.getString("A.PRODUCT_CODE");
					s1[1]=rs1.getString("A.PRODUCT_NAME");
					s1[2]=rs1.getString("B.MAKER_NAME");
					result1.add(s1);
				}
				
				request.setAttribute("result1", result1);
			} else {
				
				// ↓絞り込みしたい
				
				String sql2 ="SELECT PRODUCT_CODE, PRODUCT_NAME, MAKER_CODE FROM PRODUCT" + " Where MAKER_CODE = ?";
				
				PreparedStatement statement2 = conn.prepareStatement(sql2);

				statement2.setString(1,MAKER_CODE);
				ResultSet rs2 = statement2.executeQuery();
				
				ArrayList<String[]> result2 = new ArrayList<>();
				while(rs2.next()==true) {
					String[] s2 = new String[3];
					s2[0]=rs2.getString("PRODUCT_CODE");
					s2[1]=rs2.getString("PRODUCT_NAME");
					s2[2]=rs2.getString("MAKER_CODE");
					result2.add(s2);
				}
				
				request.setAttribute("result1", result2);
			}
			


			
			
			RequestDispatcher rd =
					request.getRequestDispatcher("/WEB-INF/jsp/product.jsp");
			rd.forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
}
