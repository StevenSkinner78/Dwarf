/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.filter;

import gov.doc.isu.dwarf.taglib.displaytag.tags.TableTagParameters;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * J2ee 1.3 implementation of BufferedResponseWrapper. Need to extend HttpServletResponseWrapper for Weblogic compatibility.
 * 
 * @author rapruitt
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class BufferedResponseWrapper13Impl extends HttpServletResponseWrapper implements BufferedResponseWrapper {

    /**
     * logger.
     */
    private static Logger log = Logger.getLogger(BufferedResponseWrapper13Impl.class);

    /**
     * The buffered response.
     */
    private CharArrayWriter outputWriter;

    /**
     * The outputWriter stream.
     */
    private SimpleServletOutputStream servletOutputStream;

    /**
     * The contentType.
     */
    private String contentType;

    /**
     * If state is set, allow getOutputStream() to return the "real" output stream, elsewhere returns a internal buffer.
     */
    private boolean state;

    /**
     * Writer has been requested.
     */
    private boolean outRequested;

    /**
     * @param httpServletResponse
     *        the response to wrap
     */
    public BufferedResponseWrapper13Impl(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        this.outputWriter = new CharArrayWriter();
        this.servletOutputStream = new SimpleServletOutputStream();
    }

    /**
     * {@inheritDoc}
     */
    public String getContentType() {
        return this.contentType;
    }

    /**
     * The content type is NOT set on the wrapped response. You must set it manually. Overrides any previously set value.
     * 
     * @param theContentType
     *        the content type.
     */
    public void setContentType(String theContentType) {
        if(state){
            log.debug("Allowing content type");

            if(this.contentType != null && this.contentType.indexOf("charset") > -1){// content type has been set before and it specified charset

                // so copy the charset
                String charset = this.contentType.substring(this.contentType.indexOf("charset"));
                if(log.isDebugEnabled()){
                    log.debug("Adding charset: [" + charset + "]");
                }

                getResponse().setContentType(StringUtils.substringBefore(theContentType, "charset") + '=' + charset);
            }else{
                getResponse().setContentType(theContentType);
            }

        }
        this.contentType = theContentType;
    }

    /**
     * {@inheritDoc}
     */
    public PrintWriter getWriter() throws IOException {

        if(state && !outRequested){
            log.debug("getWriter() returned");

            // ok, exporting in progress, discard old data and go on streaming
            this.servletOutputStream.reset();
            this.outputWriter.reset();
            this.outRequested = true;
            return ((HttpServletResponse) getResponse()).getWriter();
        }

        return new PrintWriter(this.outputWriter);
    }

    /**
     * Flush the buffer, not the response.
     * 
     * @throws IOException
     *         if encountered when flushing
     */
    public void flushBuffer() throws IOException {
        if(outputWriter != null){
            this.outputWriter.flush();
            this.servletOutputStream.outputStream.reset();
        }
    }

    /**
     * {@inheritDoc}
     */
    public ServletOutputStream getOutputStream() throws IOException {
        if(state && !outRequested){
            log.debug("getOutputStream() returned");

            // ok, exporting in progress, discard old data and go on streaming
            this.servletOutputStream.reset();
            this.outputWriter.reset();
            this.outRequested = true;
            return ((HttpServletResponse) getResponse()).getOutputStream();
        }
        return this.servletOutputStream;
    }

    /**
     * {@inheritDoc}
     */
    public void addHeader(String name, String value) {
        // if the "magic parameter" is set, a table tag is going to call getOutputStream()
        if(TableTagParameters.PARAMETER_EXPORTING.equals(name)){
            log.debug("Magic header received, real response is now accessible");
            state = true;
        }else{
            if(!ArrayUtils.contains(FILTERED_HEADERS, StringUtils.lowerCase(name))){
                ((HttpServletResponse) getResponse()).addHeader(name, value);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isOutRequested() {
        return this.outRequested;
    }

    /**
     * {@inheritDoc}
     */
    public String getContentAsString() {
        return this.outputWriter.toString() + this.servletOutputStream.toString();
    }

    /**
     * {@inheritDoc}
     */
    public void setDateHeader(String name, long date) {
        if(!ArrayUtils.contains(FILTERED_HEADERS, StringUtils.lowerCase(name))){
            ((HttpServletResponse) getResponse()).setDateHeader(name, date);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void addDateHeader(String name, long date) {
        if(!ArrayUtils.contains(FILTERED_HEADERS, StringUtils.lowerCase(name))){
            ((HttpServletResponse) getResponse()).addDateHeader(name, date);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setHeader(String name, String value) {
        if(!ArrayUtils.contains(FILTERED_HEADERS, StringUtils.lowerCase(name))){
            ((HttpServletResponse) getResponse()).setHeader(name, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setIntHeader(String name, int value) {
        if(!ArrayUtils.contains(FILTERED_HEADERS, StringUtils.lowerCase(name))){
            ((HttpServletResponse) getResponse()).setIntHeader(name, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void addIntHeader(String name, int value) {
        if(!ArrayUtils.contains(FILTERED_HEADERS, StringUtils.lowerCase(name))){
            ((HttpServletResponse) getResponse()).addIntHeader(name, value);
        }
    }

}
