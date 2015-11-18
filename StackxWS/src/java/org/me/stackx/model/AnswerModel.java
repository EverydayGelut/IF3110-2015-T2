/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.stackx.model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import static java.lang.System.out;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.me.stackx.module.Answer;
import org.me.stackx.module.User;

/**
 *
 * @author natanelia
 */
public class AnswerModel {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/stackx";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
 
    public static int create(User user, int questionId, String content) {
        int r = -1;
        if (user.isValid()) {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
               //STEP 2: Register JDBC driver
               Class.forName("com.mysql.jdbc.Driver");

               //STEP 3: Open a connection
               conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

               //STEP 4: Execute a query
               String sql;
               sql = "INSERT INTO answer (question_id, user_id, content, create_date) VALUES(?, ?, ?, CURRENT_TIMESTAMP)";
               stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
               stmt.setInt(1, questionId);
               stmt.setInt(2, user.getUserId());
               stmt.setString(3, content);
               
               int affectedRows = stmt.executeUpdate();
               if (affectedRows == 0) {
                   r = -1;
               } else {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            r = generatedKeys.getInt(1);
                        }
                        else {
                            r = -1;
                        }
                    }
               }
               stmt.close();
               conn.close();
            } catch(SQLException se) {
               //Handle errors for JDBC
               se.printStackTrace();
            } catch(Exception e) {
               //Handle errors for Class.forName
               e.printStackTrace();
            } finally {
               //finally block used to close resources
               try {
                  if (stmt!=null)
                    stmt.close();
               } catch(SQLException se2) { }// nothing we can do
               try {
                  if (conn!=null) {
                    conn.close();
                  }
               } catch (SQLException se) {
                    se.printStackTrace();
               }//end finally try
            }//end try
        }
        
        return r;
    }
    
    public static int edit(User user, int answerId, String content) {
        int r = -1;
        if (user.isValid()) {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
               //STEP 2: Register JDBC driver
               Class.forName("com.mysql.jdbc.Driver");

               //STEP 3: Open a connection
               conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

               //STEP 4: Execute a query
               String sql;
               sql = "UPDATE answer SET content=? WHERE answer_id=? AND user_id=?";
               stmt = conn.prepareStatement(sql);
               
               stmt.setString(1, content);
               stmt.setInt(2, answerId);
               stmt.setInt(3, user.getUserId());
               
               int affectedRows = stmt.executeUpdate();
               if (affectedRows == 0) {
                   r = -1;
               } else {
                   r = answerId;
               }
               
               stmt.close();
               conn.close();
            } catch(SQLException se) {
               //Handle errors for JDBC
               se.printStackTrace();
            } catch(Exception e) {
               //Handle errors for Class.forName
               e.printStackTrace();
            } finally {
               //finally block used to close resources
               try {
                  if (stmt!=null)
                    stmt.close();
               } catch(SQLException se2) { }// nothing we can do
               try {
                  if (conn!=null) {
                    conn.close();
                  }
               } catch (SQLException se) {
                    se.printStackTrace();
               }//end finally try
            }//end try
        }
        return r;
    }
    
    public static int vote(User user, int answerId, int inc) {
        int r = -1;
        if (user.isValid()) {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                //STEP 2: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");

                //STEP 3: Open a connection
                conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

                //STEP 4: Execute a query
                String sql;
                sql = "SELECT COUNT(user_id) FROM vote_answer WHERE answer_id=? AND user_id=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, answerId);
                stmt.setInt(2, user.getUserId());
                ResultSet rs = stmt.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    sql = "INSERT INTO vote_answer (user_id, answer_id) VALUES (?, ?)";
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, user.getUserId());
                    stmt.setInt(2, answerId);
                    stmt.executeUpdate();
                }
               
                sql = "SELECT COUNT(user_id) FROM vote_answer WHERE answer_id=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, answerId);
                rs = stmt.executeQuery();
                rs.next();
                r = rs.getInt(1);
               
                stmt.close();
                conn.close();
            } catch(SQLException se) {
               //Handle errors for JDBC
               se.printStackTrace();
            } catch(Exception e) {
               //Handle errors for Class.forName
               e.printStackTrace();
            } finally {
               //finally block used to close resources
               try {
                  if (stmt!=null)
                    stmt.close();
               } catch(SQLException se2) { }// nothing we can do
               try {
                  if (conn!=null) {
                    conn.close();
                  }
               } catch (SQLException se) {
                    se.printStackTrace();
               }//end finally try
            }//end try
        }
        return r;
    }
    
    
    public static String delete(User user, int answerId) {
        String r = "ERROR";
        if (user.isValid()) {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
               //STEP 2: Register JDBC driver
               Class.forName("com.mysql.jdbc.Driver");

               //STEP 3: Open a connection
               conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

               //STEP 4: Execute a query
               String sql;
               sql = "DELETE FROM answer WHERE answer_id=? AND user_id=?";
               stmt = conn.prepareStatement(sql);
               
               stmt.setInt(1, answerId);
               stmt.setInt(2, user.getUserId());
               
               int affectedRows = stmt.executeUpdate();
               if (affectedRows == 0) {
                   r = "ERROR";
               } else {
                   r = "SUCCESS";
               }
               
               stmt.close();
               conn.close();
            } catch(SQLException se) {
               //Handle errors for JDBC
               se.printStackTrace();
            } catch(Exception e) {
               //Handle errors for Class.forName
               e.printStackTrace();
            } finally {
               //finally block used to close resources
               try {
                  if (stmt!=null)
                    stmt.close();
               } catch(SQLException se2) { }// nothing we can do
               try {
                  if (conn!=null) {
                    conn.close();
                  }
               } catch (SQLException se) {
                    se.printStackTrace();
               }//end finally try
            }//end try
        }
        return r;
    }
    
    
    public static Answer getById(int id) {
        Connection conn = null;
        Statement stmt = null;
        try {
           //STEP 2: Register JDBC driver
           Class.forName("com.mysql.jdbc.Driver");

           //STEP 3: Open a connection
           conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

           //STEP 4: Execute a query
           stmt = (Statement) conn.createStatement();
           String sql;
           sql = "SELECT answer_id, question_id, user_id, content, vote, create_date FROM answer WHERE answer_id=" + id;
            //STEP 5: Extract data from result set
            try (ResultSet rs = stmt.executeQuery(sql)) {
                //STEP 5: Extract data from result set
                rs.next();
                //Retrieve by column name
                int answerId  = rs.getInt("answer_id");
                int questionId  = rs.getInt("question_id");
                int userId = rs.getInt("user_id");
                String content = rs.getString("content");
                int vote = rs.getInt("vote");
                Timestamp createDate = rs.getTimestamp("create_date");

                return new Answer(answerId, questionId, userId, content, vote, createDate.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
           stmt.close();
           conn.close();
        } catch(SQLException se) {
           //Handle errors for JDBC
           se.printStackTrace();
        } catch(Exception e) {
           //Handle errors for Class.forName
           e.printStackTrace();
        } finally {
           //finally block used to close resources
           try {
              if (stmt!=null)
                 stmt.close();
           } catch(SQLException se2){ }// nothing we can do
           try {
              if (conn!=null)
                 conn.close();
           } catch (SQLException se) {
              se.printStackTrace();
           }//end finally try
        }//end try
        return null;
    }
    
    public static Answer[] getAllFromQuestionId(int questionId) {
        //TODO: request to oauth to get who is the requester
        ArrayList<Answer> answerList = new ArrayList<>();
        Answer[] r = null;
        Connection conn = null;
        Statement stmt = null;
        try {
           //STEP 2: Register JDBC driver
           Class.forName("com.mysql.jdbc.Driver");

           //STEP 3: Open a connection
           conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);

           //STEP 4: Execute a query
           stmt = (Statement) conn.createStatement();
           String sql;
           sql = "SELECT answer_id, user_id, content, vote, create_date FROM answer WHERE question_id=" + questionId;
            //STEP 5: Extract data from result set
            try (ResultSet rs = stmt.executeQuery(sql)) {
                //STEP 5: Extract data from result set
                while (rs.next()) {
                    //Retrieve by column name
                    int answerId  = rs.getInt("answer_id");
                    int userId = rs.getInt("user_id");
                    String content = rs.getString("content");
                    int vote = rs.getInt("vote");
                    Timestamp createDate = rs.getTimestamp("create_date");

                    answerList.add(new Answer(answerId, questionId, userId, content, vote, createDate.toString()));
                }
                r = new Answer[answerList.size()];
                r = answerList.toArray(r);
            } catch (Exception e) {
                e.printStackTrace();
            }
           stmt.close();
           conn.close();
        } catch(SQLException se) {
           //Handle errors for JDBC
           se.printStackTrace();
        } catch(Exception e) {
           //Handle errors for Class.forName
           e.printStackTrace();
        } finally {
           //finally block used to close resources
           try {
              if (stmt!=null)
                 stmt.close();
           } catch(SQLException se2){ }// nothing we can do
           try {
              if (conn!=null)
                 conn.close();
           } catch (SQLException se) {
              se.printStackTrace();
           }//end finally try
        }//end try
        return r;
    }
}
