/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.rafaeltuelho.tdc2012.listeners;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;

/**
 *
 * @author rafaeltuelho
 */
public class ServletAsyncListener implements AsyncListener {

    private static final Logger logger = Logger.getLogger(ServletAsyncListener.class.getName());
    private ThreadPoolExecutor tpe;

    public ServletAsyncListener() {
    }

    
    public ServletAsyncListener(ThreadPoolExecutor tpe) {
        this.tpe = tpe;
    }
    
    public void onComplete(AsyncEvent ae) {
        StringBuffer sb = new StringBuffer();
        
        logger.info("AsyncListener: onComplete for request: "
                + ae.getAsyncContext().getRequest().getParameter("id"));
        
        sb.append("\n[\n");
        sb.append("\t --> Max Pool Size:        " + this.tpe.getMaximumPoolSize() + " <--");
        sb.append("\n\t --> Core Pool Size:       " + this.tpe.getCorePoolSize()    + " <--");
        sb.append("\n\t --> Current Pool Size:    " + this.tpe.getPoolSize()        + " <--");
        sb.append("\n\t --> Task Count:           " + this.tpe.getTaskCount()       + " <--");
        sb.append("\n\t --> *Active Count*:       " + this.tpe.getActiveCount()     + " <--");
        sb.append("\n\t --> Completed Task Count: " + this.tpe.getCompletedTaskCount() + " <--");
        sb.append("\n\t --> Largest Pool Size:    " + this.tpe.getLargestPoolSize()    + " <--");
        sb.append("\n]\n");
        
        logger.info("\n>>>>> AsyncListener: Thread Pool Statistics. <<<<< \n" + 
                sb.toString());
    }

    public void onTimeout(AsyncEvent ae) {
        logger.info("AsyncListener: onTimeout for request: "
                + ae.getAsyncContext().getRequest().getParameter("id"));
    }

    public void onError(AsyncEvent ae) {
        logger.info("AsyncListener: onError for request: "
                + ae.getAsyncContext().getRequest().getParameter("id"));
    }

    public void onStartAsync(AsyncEvent ae) {
        logger.info("AsyncListener: onStartAsync");
    }
}
