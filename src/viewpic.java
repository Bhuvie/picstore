import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;

/**
 * Created by Bhuvie on 4/18/2017.
 */
@WebServlet("/viewpic")
public class viewpic extends HttpServlet {
    private static final String url = "jdbc:mysql://bhuviedbi.cwyiughlbpf0.us-west-2.rds.amazonaws.com:3306/bhuviedb";
    private static final String user = "bhuvie93";
    private static final String pass = "############";

    Connection con;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        String paramName=request.getParameter("fnid2");
        String filename=request.getParameter("cfilename1");
        try {


            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps;
            ResultSet rs;


            String query = "SELECT * from bhuviedb.picdb where "+paramName+"=?;";
            ps = con.prepareStatement(query);
            ps.setString(1,filename);
            rs = ps.executeQuery();
            InputStream inputStream=new InputStream() {
                @Override
                public int read() throws IOException {
                    return 0;
                }
            };
            String comments="",fileName="";
            while(rs.next())
            {
                fileName=rs.getString("filename");
                inputStream = rs.getBlob("picblob").getBinaryStream();
                comments=rs.getString("comments");
            }

            //rs.last();System.out.println("Rows:"+rs.getRow());rs.beforeFirst();
            String prefix = fileName;
            String suffix = "";
            if (fileName.contains("."))
            {
                prefix = fileName.substring(0, fileName.lastIndexOf('.'));
                suffix = fileName.substring(fileName.lastIndexOf('.'));
            }
            File tempFile=File.createTempFile(prefix, suffix);

            tempFile.deleteOnExit();
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(inputStream, out);

            ps.close();

            con.close();
            PrintWriter pw = response.getWriter().append(tempFile.getPath()+":"+comments);
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
