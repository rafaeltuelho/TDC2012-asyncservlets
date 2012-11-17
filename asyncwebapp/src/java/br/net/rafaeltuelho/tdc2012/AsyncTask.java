/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.rafaeltuelho.tdc2012;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.netbeans.saas.RestResponse;
import org.netbeans.saas.delicious.DeliciousBookmarkingService;

/**
 *
 * @author rafaeltuelho
 */
public class AsyncTask implements Runnable {

    private static final Logger LOG = Logger.getLogger(AsyncTask.class.getName());
    private AsyncContext asyncContext;

    public AsyncTask() {
    }

    public AsyncTask(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public void run() {

        long startTime, finalTime = 0;
        startTime = System.currentTimeMillis();

        LOG.info("\n---\n\t\t [" + this.getThreadID() + "]: processando nova task (Async)...");

        // online (Delicious rest service)
        this.processTask();

        // offline (sleep)
//        this.longRunningProcess();

        finalTime = System.currentTimeMillis() - startTime;
        LOG.info("\t\t [" + this.getThreadID() + "]: task finalizada em "
                + finalTime + "ms \n---\n");
    }

    private void processTask() {

        String tag = null;
        String start = null;
        String results = null;
        String fromdt = null;
        String todt = null;
        String meta = null;

        long startTime, finalTime, regCount = 0;
        StringBuffer sb = new StringBuffer();
        PrintWriter out = null;

        try {

            startTime = System.currentTimeMillis();

            HttpServletRequest req =
                    (HttpServletRequest) this.asyncContext.getRequest();
            HttpServletResponse rsp =
                    (HttpServletResponse) this.asyncContext.getResponse();
            
            String username = (String) req.getParameter("_username");

            if (req.isAsyncStarted()) {

                out = rsp.getWriter();

                rsp.setContentType("text/html;charset=UTF-8");
                sb.append("<h3>async thread: [<span class=\"text-info\">" + this.getThreadID() + "</span>] from managed pool</h3>");
                sb.append("<h2>My Bookmarks: http://www.delicious.com/<span class=\"text-info\">" + username + "</span></h2>");
                sb.append("<div>");
                sb.append("<ul>");

                RestResponse result = DeliciousBookmarkingService.allPosts(
                        req, rsp, tag, start, results, fromdt, todt, meta);

                finalTime = System.currentTimeMillis() - startTime;

                if (result.getDataAsObject(
                        delicious.bookmarkingservice.deliciousresponse.Posts.class) instanceof delicious.bookmarkingservice.deliciousresponse.Posts) {

                    delicious.bookmarkingservice.deliciousresponse.Posts resultObj =
                            result.getDataAsObject(
                            delicious.bookmarkingservice.deliciousresponse.Posts.class);

                    regCount = resultObj.getPost().size();
                    for (Iterator it = resultObj.getPost().iterator(); it.hasNext();) {
                        delicious.bookmarkingservice.deliciousresponse.Posts.Post post =
                                (delicious.bookmarkingservice.deliciousresponse.Posts.Post) it.next();

                        //System.out.println("post url: " + post.getHref());

                        sb.append("<li>" + post.getHref() + "</li>");
                    }
                }

                sb.append("</ul>");
                sb.append("</div>");

                sb.append("<p> retornou <b>" + regCount + "</b> registros<br>");
                sb.append(" tempo de processamento <b>" + finalTime + "ms </b></p>");


                sb.append("</body>");
                sb.append("</html>");

                out.println(sb.toString());
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            if (null != out) {
                out.close();
            }

            this.asyncContext.complete();
        }

    }

    private void longRunningProcess() {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(8000);

        StringBuffer sb = new StringBuffer();
        PrintWriter out = null;

        try {
            HttpServletRequest req =
                    (HttpServletRequest) this.asyncContext.getRequest();
            HttpServletResponse rsp =
                    (HttpServletResponse) this.asyncContext.getResponse();

            if (req.isAsyncStarted()) {

                out = rsp.getWriter();

                rsp.setContentType("text/html;charset=UTF-8");

                sb.append("<html>");
                sb.append("<head>");
                sb.append("<title>Servlet DeliciousClientServlet</title>");
                sb.append("</head>");
                sb.append("<body>");

                sb.append("<div>");
                sb.append("<h1>tempo de processamento: " + randomInt + "</h1>");
                sb.append("<h3>processamento realizado pela thread: [" + this.getThreadID() + "]</h3>");
                sb.append("</div>");

                sb.append("</body>");
                sb.append("</html>");

                out.println(sb.toString());
            }

            LOG.info("\t\t [" + this.getThreadID() + "]: Aguardando " + randomInt + "ms ...");
            Thread.sleep(randomInt);

        } catch (InterruptedException ie) {
            LOG.log(Level.WARNING, ie.getMessage(), ie);
        } catch (IOException ioe) {
            LOG.log(Level.WARNING, ioe.getMessage(), ioe);
        } finally {

            if (null != out) {
                out.close();
            }

            this.asyncContext.complete();
        }
    }

    private String getThreadID() {
        String threadId = Thread.currentThread().getName();

        return threadId;
    }
}
