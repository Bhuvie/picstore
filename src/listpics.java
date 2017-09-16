import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by Bhuvie on 4/15/2017.
 */
@WebServlet("/listpics")
public class listpics extends HttpServlet {
    private static final String url = "jdbc:mysql://bhuviedbi.cwyiughlbpf0.us-west-2.rds.amazonaws.com:3306/bhuviedb";
    private static final String user = "bhuvie93";
    private static final String pass = "################";

    Connection con;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {


            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = con.prepareStatement("select fileid,filename,filesize,comments,username from bhuviedb.picdb ");
            ResultSet rs;
            rs = ps.executeQuery();
            String arr="<div class='row' style="+'"'+"padding-bottom:10px"+'"'+"><h4><div class='col-sm-2'>FileID</div><div class='col-sm-2'>FileName</div><div class='col-sm-2'>File Size</div><div class='col-sm-4'>Comments</div><div class='col-sm-2'>Posted By</div></h4></div>";
            while (rs.next()) {
                    arr = arr + "<div class='row' style="+'"'+"padding-bottom:10px"+'"'+"><div class='col-sm-2'>"+rs.getString("fileid")+"</div><div class='col-sm-2'>"+rs.getString("filename")+"</div><div class='col-sm-2'>"+rs.getString("filesize")+"</div><div class='col-sm-4'>"+rs.getString("comments")+"</div><div class='col-sm-2'>"+rs.getString("username")+"</div></div>";
                }
            PrintWriter pw = response.getWriter().append(arr);
            pw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
