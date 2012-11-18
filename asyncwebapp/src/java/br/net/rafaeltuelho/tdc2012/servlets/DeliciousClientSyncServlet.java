/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.rafaeltuelho.tdc2012.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.netbeans.saas.RestResponse;
import org.netbeans.saas.delicious.DeliciousBookmarkingService;

/**
 *
 * @author rafaeltuelho
 */
@WebServlet(name = "DeliciousClientSyncServlet", urlPatterns = {"/DeliciousClientSyncServlet"})
public class DeliciousClientSyncServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DeliciousClientSyncServlet.class.getName());

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("text/html;charset=UTF-8");

        String result = "";
        long startTime, finalTime = 0;
        startTime = System.currentTimeMillis();
        String reqID = request.getParameter("_reqID");
        
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet DeliciousClientServlet</title>");
        out.println("<link href=\"css/bootstrap.css\" rel=\"stylesheet\" type=\"text/css\" />");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Http Servlet Thread: [<span class=\"text-info\">" + this.getThreadID() + "</span>]");
        out.println("<h2>   requisicao [<span class=\"text-info\">"+ reqID +"</span>]</h1>");
        out.flush();
        
        try {

            //invoke the remote service synchrounously
            result = this.getDeliciousBookmarks(request, response);
            
            finalTime = System.currentTimeMillis() - startTime;

            out.println("<div>");

            out.println(result);

            out.println("</div>");
            out.println("<p> tempo de processamento: <b>" + finalTime + "ms </b>");

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String getDeliciousBookmarks(HttpServletRequest request, HttpServletResponse response) {
        String tag = null;
        String start = null;
        String results = null;
        String fromdt = null;
        String todt = null;
        String meta = null;

        String username = (String) request.getParameter("_username");
        StringBuffer sb = new StringBuffer();
        int postsCount = 0;
        sb.append("<h2>My Bookmarks: http://www.delicious.com/<span class=\"text-info\">" + username + "</span></h2>");
        sb.append("<ul>");

        try {

            RestResponse result = DeliciousBookmarkingService.allPosts(
                    request, response, tag, start, results, fromdt, todt, meta);

            if (result.getDataAsObject(
                    delicious.bookmarkingservice.deliciousresponse.Posts.class) instanceof delicious.bookmarkingservice.deliciousresponse.Posts) {

                delicious.bookmarkingservice.deliciousresponse.Posts resultObj =
                        result.getDataAsObject(
                        delicious.bookmarkingservice.deliciousresponse.Posts.class);

                postsCount = resultObj.getPost().size();
                for (Iterator it = resultObj.getPost().iterator(); it.hasNext();) {
                    delicious.bookmarkingservice.deliciousresponse.Posts.Post post =
                            (delicious.bookmarkingservice.deliciousresponse.Posts.Post) it.next();

                    //System.out.println("post url: " + post.getHref());

                    sb.append("<li>" + post.getHref() + "</li>");
                }

            }
            //TODO - Uncomment the print Statement below to print result.
            //out.println("The SaasService returned: "+result.getDataAsString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        sb.append("</ul>");
        sb.append("<p>retornou <b>" + postsCount + "</b> registros</p>");


        return sb.toString();
    }

    private String getThreadID() {
        String threadId = Thread.currentThread().getName();

        return threadId;
    }
}
