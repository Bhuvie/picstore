import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Bhuvie on 4/15/2017.
 */
@WebServlet("/commentpic")
public class commentpic extends HttpServlet {
    private static final String url = "jdbc:mysql://bhuviedbi.cwyiughlbpf0.us-west-2.rds.amazonaws.com:3306/bhuviedb";
    private static final String user = "bhuvie93";
    private static final String pass = "##########";
    Connection con;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie cookie = null;
        Cookie[] cookies = null;
        // Get an array of Cookies associated with this domain
        cookies = request.getCookies();
        String username="";
        for (int i = 0; i < cookies.length; i++){
            cookie = cookies[i];
            if((cookie.getName( )).compareTo("username") == 0 )
            {
                username=cookie.getValue();
            }}
        String fileName=request.getParameter("cfilename");
        String comments=request.getParameter("tacomment");
        String paramName=request.getParameter("fnid");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps;
            ResultSet rs;

            String query="update bhuviedb.picdb set comments=CONCAT(IFNULL(comments,''),?)"+" where "+paramName+"=?";
            ps = con.prepareStatement(query);
            ps.setString(1,comments+" - Posted by "+username+". ");
            ps.setString(2,fileName);


            ps.executeUpdate();
            ps.close();
            con.close();
            PrintWriter pw = response.getWriter().append("Pic Commented Successfully");
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
