/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * Default RequestHelperFactory implementation. Returns instaces of {@link DefaultRequestHelper}.
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class DefaultRequestHelperFactory implements RequestHelperFactory {

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.util.RequestHelperFactory#getRequestHelperInstance(javax.servlet.jsp.PageContext)
     */
    public RequestHelper getRequestHelperInstance(PageContext pageContext) {
        return new DefaultRequestHelper((HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
    }

}
