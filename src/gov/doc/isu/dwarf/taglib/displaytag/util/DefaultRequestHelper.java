/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.util;

import gov.doc.isu.dwarf.taglib.displaytag.Messages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.log4j.Logger;

/**
 * Default RequestHelper implementation.
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 * @see gov.doc.isu.dwarf.taglib.displaytag.util.Href
 * @see gov.doc.isu.dwarf.taglib.displaytag.util.RequestHelper
 */
public class DefaultRequestHelper implements RequestHelper {

    /**
     * logger.
     */
    private static Logger log = Logger.getLogger(DefaultRequestHelper.class);

    /**
     * original HttpServletRequest.
     */
    private HttpServletRequest request;

    /**
     * original HttpServletResponse.
     */
    private HttpServletResponse response;

    /**
     * Construct a new RequestHelper for the given request.
     * 
     * @param servletRequest
     *        HttpServletRequest needed to generate the base href
     * @param servletResponse
     *        HttpServletResponse needed to encode generated urls
     */
    public DefaultRequestHelper(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        this.request = servletRequest;
        this.response = servletResponse;
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.util.RequestHelper#getHref()
     */
    public Href getHref() {
        String requestURI = this.request.getRequestURI();
        // call encodeURL to preserve session id when cookies are disabled
        Href href = new DefaultHref(this.response.encodeURL(requestURI));
        href.setParameterMap(getParameterMap());
        return href;
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.util.RequestHelper#getParameter(java.lang.String)
     */
    public String getParameter(String key) {
        // actually simply return the parameter, this behaviour could be changed
        return this.request.getParameter(key);
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.util.RequestHelper#getIntParameter(java.lang.String)
     */
    public Integer getIntParameter(String key) {
        String value = this.request.getParameter(key);

        if(value != null){
            try{
                return new Integer(value);
            }catch(NumberFormatException e){
                // It's ok to ignore, simply return null
                log.debug(Messages.getString("RequestHelper.invalidparameter", //$NON-NLS-1$
                        new Object[]{key, value}));
            }
        }

        return null;
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.util.RequestHelper#getParameterMap()
     */
    public Map getParameterMap() {

        Map map = new HashMap();

        // get the parameters names
        Enumeration parametersName = this.request.getParameterNames();

        while(parametersName.hasMoreElements()){
            // ... get the value
            String paramName = (String) parametersName.nextElement();

            request.getParameter(paramName);
            // put key/value in the map
            String[] originalValues = (String[]) ObjectUtils.defaultIfNull(this.request.getParameterValues(paramName), new String[0]);
            String[] values = new String[originalValues.length];

            for(int i = 0;i < values.length;i++){
                try{
                    values[i] = URLEncoder.encode(StringUtils.defaultString(originalValues[i]), StringUtils.defaultString(response.getCharacterEncoding(), "UTF8")); //$NON-NLS-1$
                }catch(UnsupportedEncodingException e){
                    throw new UnhandledException(e);
                }
            }
            map.put(paramName, values);

        }

        // return the Map
        return map;
    }

}
