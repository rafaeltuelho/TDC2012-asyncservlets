/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.rafaeltuelho.tdc2012.listeners;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author rafaeltuelho
 */
@WebListener()
public class ServletContextListener implements javax.servlet.ServletContextListener {
    
    private static final Logger LOG = Logger.getLogger(ServletContextListener.class.getName());

    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("init Servlet Context");
        
        // Executor Framework (JavaSE 5)
        //    definido com uma fila de execução com capacidade de 100 threads.
        Executor executor = new ThreadPoolExecutor(10, 100, 60000L, 
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100));
        
        sce.getServletContext().setAttribute("threadPoolExecutor", executor);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
        ThreadPoolExecutor executor = (ThreadPoolExecutor)sce.getServletContext().getAttribute("threadPoolExecutor");
        
        LOG.info(" stopping the ThreadPool Executor for this ServletContext...");
        
        executor.shutdownNow();
        
        LOG.info("Servlet Context destroyed");
    }
}
