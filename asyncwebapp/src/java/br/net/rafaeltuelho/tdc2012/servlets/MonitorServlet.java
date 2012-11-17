/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.rafaeltuelho.tdc2012.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadPoolExecutor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rafaeltuelho
 */
@WebServlet(name = "MonitorServlet", urlPatterns = {"/MonitorServlet"})
public class MonitorServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        StringBuffer sb = new StringBuffer();
        try {
            ThreadPoolExecutor tpe =
                    (ThreadPoolExecutor) request.getServletContext().
                    getAttribute("threadPoolExecutor");

            sb.append("");
            sb.append("<thead>");
            sb.append("    <tr>");
            sb.append("        <th>Atributo</th>");
            sb.append("        <th>Valor atual</th>");
            sb.append("    </tr>");
            sb.append("</thead>");
            sb.append("<tbody>");
            sb.append("    <tr>");
            sb.append("        <td>Max Pool Size</td>");
            sb.append("        <td>" + tpe.getMaximumPoolSize() + "</td>");
            sb.append("    </tr>");
            sb.append("    <tr>");
            sb.append("        <td>Core Pool Size</td>");
            sb.append("         <td>" + tpe.getCorePoolSize() + "</td>");
            sb.append("     </tr>");
            sb.append("     <tr>");
            sb.append("         <td>Current Pool Size</td>");
            sb.append("         <td>" + tpe.getPoolSize() + "</td>");
            sb.append("     </tr>");
            sb.append("     <tr>");
            sb.append("         <td>Task Count</td>");
            sb.append("         <td>" + tpe.getTaskCount() + "</td>");
            sb.append("     </tr>");
            sb.append("     <tr>");
            sb.append("         <td>Active Count</td>");
            sb.append("         <td>" + tpe.getActiveCount() + "</td>");
            sb.append("     </tr>");
            sb.append("     <tr>");
            sb.append("         <td>Completed Task Count</td>");
            sb.append("         <td>" + tpe.getCompletedTaskCount() + "</td>");
            sb.append("     </tr>");
            sb.append("     <tr>");
            sb.append("         <td>Largest Pool Size</td>");
            sb.append("         <td>"+tpe.getLargestPoolSize()+"</td>");
            sb.append("     </tr>");
            sb.append("     <tr>");
            sb.append("         <td>Thread Queue remaining Capacity</td>");
            sb.append("         <td>"+tpe.getQueue().remainingCapacity()+"</td>");
            sb.append("     </tr>");
            sb.append("     <tr>");
            sb.append("         <td>Thread Queue Size</td>");
            sb.append("         <td>"+tpe.getQueue().size()+"</td>");
            sb.append("     </tr>");
            sb.append(" </tbody>");

            out.println(sb.toString());
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
}
