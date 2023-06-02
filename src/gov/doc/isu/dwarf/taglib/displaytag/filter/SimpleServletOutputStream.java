/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.filter;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * A simple implementation of ServletOutputStream which wraps a ByteArrayOutputStream.
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class SimpleServletOutputStream extends ServletOutputStream {

    /**
     * My outputWriter stream, a buffer.
     */
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    /**
     * {@inheritDoc}
     */
    public void write(int b) {
        this.outputStream.write(b);
    }

    /**
     * Get the contents of the outputStream.
     * 
     * @return contents of the outputStream
     */
    public String toString() {
        return this.outputStream.toString();
    }

    /**
     * Reset the wrapped ByteArrayOutputStream.
     */
    public void reset() {
        outputStream.reset();
    }

    @Override
    public boolean isReady() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setWriteListener(WriteListener arg0) {
        // TODO Auto-generated method stub
        
    }
}
