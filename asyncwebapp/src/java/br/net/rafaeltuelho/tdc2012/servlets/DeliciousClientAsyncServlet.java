/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.rafaeltuelho.tdc2012.servlets;

import br.net.rafaeltuelho.tdc2012.AsyncTask;
import br.net.rafaeltuelho.tdc2012.listeners.ServletAsyncListener;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author rafaeltuelho
 */
@WebServlet(name = "DeliciousClientAsyncServlet",
urlPatterns = {"/DeliciousClientAsyncServlet"},
asyncSupported = true)
public class DeliciousClientAsyncServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DeliciousClientAsyncServlet.class.getName());

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
        
        long startTime = System.currentTimeMillis();
        long finalTime = 0;
        String reqID = request.getParameter("_reqID");
        
        LOG.info("\n---\n\t[" + this.getThreadID() + "]: processando nova requisicao...");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>Servlet DeliciousClientServlet</title>");
        
        response.getWriter().println("<link href=\"css/bootstrap.css\" rel=\"stylesheet\" type=\"text/css\" />");

        response.getWriter().println("</head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<h1>Http Servlet Thread: [<span class=\"text-info\">" + this.getThreadID() + "</span>]");
        response.getWriter().println("<h2>   requisicao [<span class=\"text-info\">"+ reqID +"</span>]</h1>");
        response.getWriter().flush();

        try {

            AsyncContext asyncCtx = request.startAsync();
            ThreadPoolExecutor executor =
                    (ThreadPoolExecutor) request.getServletContext().
                        getAttribute("threadPoolExecutor");
            
            asyncCtx.setTimeout(-1L);
            asyncCtx.addListener(new ServletAsyncListener(executor));

            LOG.info("\t[" + this.getThreadID() + "]: delegando o processamento para o Executor (Async).");

            //delegate long running process to an "async" thread
            executor.execute(new AsyncTask(asyncCtx));

            finalTime = System.currentTimeMillis() - startTime;
        } finally {
            LOG.info("\t[" + this.getThreadID() + "]: retornando para o pool Http (gastou " +
                     finalTime + "ms)! \n---\n");
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

    private String getThreadID() {
        String threadId = Thread.currentThread().getName();

        return threadId;
    }
}
