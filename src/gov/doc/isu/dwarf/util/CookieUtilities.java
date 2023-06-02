package gov.doc.isu.dwarf.util;

import static gov.doc.isu.dwarf.resources.Constants.COOKIE_NAME;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * This is a Utility Class to get and add cookies. Has two static methods
 * 
 * @author Steven Skinner JCCC
 */
public class CookieUtilities {
    private static final Logger LOG = Logger.getLogger("gov.doc.isu.dwarf.util.CookieUtilities");
    private static final int SECONDS_PER_WEEK = 60 * 60 * 24 * 7;

    /**
     * This method tries to find the value of cookieName. If no cookie matches then return default value.
     * 
     * @param request
     *        the request object
     * @param cookieName
     *        cookieName looking for
     * @param defaultValue
     *        default value to return
     * @return value returned
     * @throws Exception
     *         if an exception occurred
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String defaultValue) throws Exception {
        LOG.debug("Entering gov.doc.isu.dwarf.util.CookieUtilities - method getCookieValue().");
        LOG.debug("Parameters: request=" + (request == null ? "null" : request.toString()) + " ,cookieName=" + (cookieName == null ? "null" : String.valueOf(cookieName) + " ,defaultValue=" + (defaultValue == null ? "null" : String.valueOf(defaultValue))));
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(int i = 0, j = cookies.length;i < j;i++){
                if(cookieName.equals(cookies[i].getName())){
                    return cookies[i].getValue();
                }
            }
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.CookieUtilities - method getCookieValue().");
        return defaultValue;
    }

    /**
     * This method tries to find and return the cookie that has the given name. If no cookie found then return null.
     * 
     * @param request
     *        the request object
     * @param cookieName
     *        the given cookie name
     * @return cookie
     * @throws Exception
     *         if an exception occurred
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) throws Exception {
        LOG.debug("Entering gov.doc.isu.dwarf.util.CookieUtilities - method getCookie().");
        LOG.debug("Parameters: request=" + (request == null ? "null" : request.toString()) + " ,cookieName=" + String.valueOf(cookieName));
        if(request != null && !StringUtil.isNullOrEmpty(cookieName)){
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for(int i = 0, j = cookies.length;i < j;i++){
                    if(cookieName.equals(cookies[i].getName())){
                        return cookies[i];
                    }
                }
            }
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.CookieUtilities - method getCookie().");
        return null;
    }

    /**
     * This method adds a cookie value for default cookie to the response
     * 
     * @param response
     *        HttpServletResponse
     * @param valArray
     *        String[]
     * @throws Exception
     *         if an exception occurred
     */
    public static void addCookie(HttpServletResponse response, String[] valArray) throws Exception {
        LOG.debug("Entering gov.doc.isu.dwarf.util.CookieUtilities - method addCookie().");
        LOG.debug("Parameters: response=" + (response == null ? "null" : response.toString()) + " ,valArray=" + (valArray == null ? "null" : StringUtil.collapseArray(valArray)));
        if(response != null && !StringUtil.isNullOrEmptyStringArray(valArray)){
            String cookieValue = AppUtil.buildCookieString(valArray);
            Cookie cookie = new Cookie(COOKIE_NAME, cookieValue);
            cookie.setMaxAge(SECONDS_PER_WEEK);
            response.addCookie(cookie);
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.CookieUtilities - method addCookie().");
    }

    /**
     * This method adds a cookie value with cookie name passed in to the response
     * 
     * @param cookieName
     *        cookieName HttpServletResponse
     * @param response
     *        HttpServletResponse
     * @param valArray
     *        String[]
     * @throws Exception
     *         if an exception occurred
     */
    public static void addCookie(String cookieName, HttpServletResponse response, String[] valArray) throws Exception {
        LOG.debug("Entering gov.doc.isu.dwarf.util.CookieUtilities - method addCookie().");
        LOG.debug("Parameters: cookieName=" + String.valueOf(cookieName) + " ,valArray=" + (valArray == null ? "null" : StringUtil.collapseArray(valArray)));
        if(response != null && !StringUtil.isNullOrEmpty(cookieName) && !StringUtil.isNullOrEmptyStringArray(valArray)){
            String cookieValue = AppUtil.buildCookieString(valArray);
            Cookie cookie = new Cookie(cookieName, cookieValue);
            cookie.setMaxAge(SECONDS_PER_WEEK);
            response.addCookie(cookie);
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.CookieUtilities - method addCookie().");
    }
}
