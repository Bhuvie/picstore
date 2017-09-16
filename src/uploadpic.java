import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by Bhuvie on 4/15/2017.
 */
@WebServlet("/uploadpic")
@MultipartConfig(location="/", fileSizeThreshold=1024*1024,
        maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class uploadpic extends javax.servlet.http.HttpServlet {
    private static final String url = "jdbc:mysql://bhuviedbi.cwyiughlbpf0.us-west-2.rds.amazonaws.com:3306/bhuviedb";
    private static final String user = "bhuvie93";
    private static final String pass = "#############";
    Connection con;
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

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
        Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);
        String fileSize=Long.toString(filePart.getSize());
        InputStream fileContent = filePart.getInputStream();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = con.prepareStatement("INSERT INTO bhuviedb.picdb (filename, filesize,picblob,username) values (?,?,?,?)");
            ps.setString(1,fileName);
            ps.setString(2,fileSize);
            ps.setBlob(3,fileContent);
            ps.setString(4,username);
             ps.executeUpdate();


            PrintWriter pw = response.getWriter().append("Picture Uploaded Successfully");
            pw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String s= cd.substring(cd.indexOf('=') + 1).trim()
                        .replace("\"", "");
                String[] sa =s.split("\\\\");
                return sa[sa.length-1];
            }
        }
        return null;
    }
}
