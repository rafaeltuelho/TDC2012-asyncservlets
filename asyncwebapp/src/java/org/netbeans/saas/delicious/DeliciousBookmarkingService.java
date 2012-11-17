/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.saas.delicious;

import java.io.IOException;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DeliciousBookmarkingService Service
 *
 * @author rafaeltuelho
 */
public class DeliciousBookmarkingService {

    /**
     * Creates a new instance of DeliciousBookmarkingService
     */
    public DeliciousBookmarkingService() {
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable th) {
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param tag
     * @param start
     * @param results
     * @param fromdt
     * @param todt
     * @param meta
     * @return an instance of RestResponse
     */
    public static RestResponse allPosts(HttpServletRequest request, HttpServletResponse response, String tag, String start, String results, String fromdt, String todt, String meta) throws IOException {
        DeliciousBookmarkingServiceAuthenticator.login(request, response);
        
        String[][] httpHeaders = new String[][]{{"Content-Type", "application/x-www-form-urlencoded"}};
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{{"tag", tag}, {"start", start}, {"results", results}, {"fromdt", fromdt}, {"todt", todt}, {"meta", meta}};
        RestConnection conn = new RestConnection("http://api.del.icio.us/v1/posts/all", pathParams, queryParams);
        sleep(1000);
        return conn.get(httpHeaders);
    }
}
