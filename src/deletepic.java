import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by Bhuvie on 4/16/2017.
 */
@WebServlet("/deletepic")
public class deletepic extends HttpServlet {
    private static final String url = "jdbc:mysql://bhuviedbi.cwyiughlbpf0.us-west-2.rds.amazonaws.com:3306/bhuviedb";
    private static final String user = "bhuvie93";
    private static final String pass = "###########";

    Connection con;
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        Cookie cookie = null;
        Cookie[] cookies = null;
        // Get an array of Cookies associated with this domain
        cookies = request.getCookies();
        String usernam="";
        for (int i = 0; i < cookies.length; i++){
            cookie = cookies[i];
            if((cookie.getName( )).compareTo("username") == 0 )
            {
                usernam=cookie.getValue();
            }}
        String paramName=request.getParameter("fnid1");
        String filename=request.getParameter("cfilename1");
        try {


            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps;
            ResultSet rs;


            String query = "delete from bhuviedb.picdb where "+paramName+"=? AND username='"+usernam+"';";
            ps = con.prepareStatement(query);
            ps.setString(1,filename);
            int noofrowsaffected = ps.executeUpdate();

            //rs.last();System.out.println("Rows:"+rs.getRow());rs.beforeFirst();


            ps.close();

            con.close();
            PrintWriter pw = response.getWriter().append("Pic Deleted Successfully");
            pw.close();

        }catch (SQLException e) {
            e.printStackTrace();
            PrintWriter pw = response.getWriter().append("Only owners are allowed to delete the file");
            pw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }


    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
