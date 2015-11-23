

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServlet;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
import org.json.simple.parser.*;
/**
 *
 * @author Edwin
 */
public class Authentication extends HttpServlet {
   
   //attr
    private String user_id;
    private String name; 
    private String email;
    private String create_time;
    private String is_valid;
    Date date;
    
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3306/stackx";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "";
   
   public void getUser(String token){
        Connection conn = null;
        Statement stmt = null;
        try{
           //STEP 2: Register JDBC driver
           Class.forName("com.mysql.jdbc.Driver");

           //STEP 3: Open a connection
           conn = DriverManager.getConnection(DB_URL,USER,PASS);

           //STEP 4: Execute a query
           stmt = conn.createStatement();
           String sql;
           sql = "SELECT user.user_id, name, email, create_time FROM user_token INNER JOIN user ON user.user_id=user_token.user_id WHERE access_token ='" + token + "'";
           ResultSet rs = stmt.executeQuery(sql);
           
           if(!rs.next()){
               name = "";
               email = "";
               user_id = "";
               create_time= "";
               is_valid ="0";
           }
           else{
               name = rs.getString("name");
               email = rs.getString("email");
               user_id = rs.getInt("user_id") + "";
               create_time = rs.getString("create_time");
               is_valid= "1";
               
               SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
               date = (Date) formatter.parse("create_time");
               
               
           }
           
           //closing database
           rs.close();
           stmt.close();
           conn.close();
        }
         catch(SQLException se){
           //Handle errors for JDBC
           se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }finally{
           //finally block used to close resources
           try{
              if(stmt!=null)
                 stmt.close();
           }catch(SQLException se2){
           }// nothing we can do
           try{
              if(conn!=null)
                 conn.close();
           }catch(SQLException se){
              se.printStackTrace();
           }//end finally try
        }//end try
    }
    
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String jsoninput = request.getParameter("input_token");
        //parsing json input format
        JSONParser parser = new JSONParser();
        String token;
        try {
            Object obj = parser.parse(jsoninput);
            JSONObject input = (JSONObject) obj;
            token = (String) input.get("token");
            getUser(token);
            
        } catch (ParseException ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //out.println(date);
            //JSON output format          
            JSONObject output = new JSONObject();
            output.put("user_id", user_id);
            output.put("name", name);
            output.put("email", email);
            output.put("create_time", create_time);
            output.put("is_valid", is_valid);
            out.println(output);
            
        }
        
    }
    
}
