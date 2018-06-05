package control;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class getApkVersion extends HttpServlet {


    private String version;
    @Override
    public void init(ServletConfig config) throws ServletException {
        version = config.getInitParameter("ver");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        JSONObject object = new JSONObject();
        object.put("ver",version);
        String outputStr = object.toString();
        System.out.println("output:"+outputStr);
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            writer.write(outputStr);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(writer != null){
                writer.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
