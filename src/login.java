

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {

    private static final String url = "jdbc:mysql://bhuviedbi.cwyiughlbpf0.us-west-2.rds.amazonaws.com:3306/bhuviedb";
    private static final String user = "bhuvie93";
    private static final String pass = "#############";

    Connection con;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("usrname");
        String passwd=request.getParameter("passwd");
        Cookie cookie=new Cookie("username",username);

        try {


            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = con.prepareStatement("select * from bhuviedb.users ");
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                if(rs.getString("username").equals(username)&&rs.getString("password").equals(passwd))
                {
                    response.addCookie(cookie);
                    PrintWriter pw=response.getWriter().append("<script language=\"javascript\">"+"alert(" + "\"Login Successful\"" + ");"+"window.location.replace(\"\\index.html\");"+"</script>");
                    pw.close();
                }
            }
            PrintWriter pw=response.getWriter().append("<script language=\"javascript\">"+"alert(" + "\"Login failed\"" + ");"+"window.location.replace(\"\\login.html\");"+"</script>");
            pw.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
