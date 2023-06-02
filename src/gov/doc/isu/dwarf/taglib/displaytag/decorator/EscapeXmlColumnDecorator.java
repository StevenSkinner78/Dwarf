/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.decorator;

import gov.doc.isu.dwarf.taglib.displaytag.properties.MediaTypeEnum;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * This takes the string that is passed in, and escapes html tags and entities. Only operates on "html" or "xml" media.
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class EscapeXmlColumnDecorator implements DisplaytagColumnDecorator {

    /**
     * Instance used for the "escapeXml" tag attribute.
     */
    public static final DisplaytagColumnDecorator INSTANCE = new EscapeXmlColumnDecorator();

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.decorator.DisplaytagColumnDecorator#decorate(Object, PageContext, MediaTypeEnum)
     */
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) {

        if(columnValue == null || (!media.equals(MediaTypeEnum.HTML) && !media.equals(MediaTypeEnum.XML))){
            return columnValue;
        }

        return StringEscapeUtils.escapeXml(columnValue.toString());
    }

}
