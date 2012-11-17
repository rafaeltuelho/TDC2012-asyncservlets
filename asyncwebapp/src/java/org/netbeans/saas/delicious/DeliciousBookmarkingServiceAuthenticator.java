/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.saas.delicious;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DeliciousBookmarkingServiceAuthenticator
 */
public class DeliciousBookmarkingServiceAuthenticator extends Authenticator {
    
    private static String appUsername;
    private static String appPassword;
    private static boolean useAppUsernamePassword;
    private static String CLASS_NAME = DeliciousBookmarkingServiceAuthenticator.class.getSimpleName();
    private static String ATTR_PREFIX = CLASS_NAME.toLowerCase();
    private static String USERNAME_ATTR = "_username";
    private static String PASSWORD_ATTR = "_password";
    private static String RETURN_URL_ATTR = ATTR_PREFIX + "_return_url";
    private static String LOGIN_URL = CLASS_NAME.replace("Authenticator", "Login");
    private static final String PROP_FILE = ATTR_PREFIX + ".properties";
    private String username;
    private String password;
    
    static {
        try {
            Properties props = new Properties();
            props.load(DeliciousBookmarkingServiceAuthenticator.class.getResourceAsStream(PROP_FILE));
            appUsername = props.getProperty("username");
            appPassword = props.getProperty("password");
            
            if (appUsername != null && appUsername.length() > 0
                    && appPassword != null || appPassword.length() > 0) {
                useAppUsernamePassword = true;
            } else {
                useAppUsernamePassword = false;
            }
            
            System.out.println("useAppUsernamePassword = " + useAppUsernamePassword);
        } catch (IOException ex) {
            Logger.getLogger(DeliciousBookmarkingServiceAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        assert request != null;
        assert response != null;
        
        if (useAppUsernamePassword) {
            Authenticator.setDefault(new DeliciousBookmarkingServiceAuthenticator(appUsername, appPassword));
        } else {
            HttpSession session = request.getSession(true);
            String username = (String) request.getParameter(USERNAME_ATTR);
            String password = (String) request.getParameter(PASSWORD_ATTR);
            
            if (username == null || username.length() == 0
                    || password == null || password.length() == 0) {
                session.setAttribute(RETURN_URL_ATTR, request.getRequestURI());
                response.sendRedirect(request.getContextPath() + "/" + LOGIN_URL);
            } else {
                String returnUrl = (String) session.getAttribute(RETURN_URL_ATTR);
                
                if (returnUrl != null) {
                    session.removeAttribute(RETURN_URL_ATTR);
                    response.sendRedirect(returnUrl);
                }
            }
            Authenticator.setDefault(new DeliciousBookmarkingServiceAuthenticator(username, password));
        }
        
    }
    
    private DeliciousBookmarkingServiceAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password.toCharArray());
    }
}
