package control;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class downloadServlet extends HttpServlet {


    private String contentType;
    private String enc = "UTF-8";
    private String fileRoot;
    private String fileName;
    @Override
    public void init(ServletConfig config) throws ServletException {
        contentType = config.getInitParameter("contentType");
        fileRoot = config.getInitParameter("fileRoot");
        fileName = config.getInitParameter("fileName");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filePath = fileRoot+ File.separator+fileName;
        File downloadFile = new File(filePath);

        if(downloadFile.exists()){
            resp.setContentType(contentType);
            Long length = downloadFile.length();
            resp.setContentLength(length.intValue());
            fileName = URLEncoder.encode(downloadFile.getName(),enc);

            resp.addHeader("Content-Disposition","attachment;filename=" + fileName);

            ServletOutputStream outputStream = resp.getOutputStream();
            FileInputStream inputStream = new FileInputStream(downloadFile);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            int size = 0;
            byte[] b = new byte[4096];
            while((size = bufferedInputStream.read(b)) != -1){
                outputStream.write(b,0,size);
            }
            outputStream.flush();
            outputStream.close();
            bufferedInputStream.close();
        }else {
            Writer writer = resp.getWriter();
            writer.write("error ! no this file!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
