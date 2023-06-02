/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.decorator;

import gov.doc.isu.dwarf.taglib.displaytag.Messages;
import gov.doc.isu.dwarf.taglib.displaytag.properties.MediaTypeEnum;

import java.text.MessageFormat;
import java.util.Locale;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

/**
 * A decorator that simply formats input Objects using a <code>java.text.messageFormat</code>. By design, this implementations handle MessageFormat errors by returning the unformatted value and logging the exception.
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class MessageFormatColumnDecorator implements DisplaytagColumnDecorator {

    /**
     * Logger.
     */
    private static Logger log = Logger.getLogger(MessageFormatColumnDecorator.class);

    /**
     * Pre-compiled messageFormat.
     */
    private MessageFormat format;

    /**
     * Instantiates a new MessageFormatColumnDecorator with a given pattern and locale.
     * 
     * @param pattern
     *        see <code>java.text.messageFormat</code>
     * @param locale
     *        current locale
     */
    public MessageFormatColumnDecorator(String pattern, Locale locale) {
        this.format = new MessageFormat(pattern, locale);
    }

    /**
     * {@inheritDoc}
     */
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) {
        try{
            return this.format.format(new Object[]{columnValue});
        }catch(IllegalArgumentException e){
            String p = pageContext.getPage().toString();
            String r = pageContext.getRequest().toString();
            log.error(Messages.getString("MessageFormatColumnDecorator.invalidArgument", new Object[]{this.format.toPattern(), columnValue != null ? columnValue.getClass().getName() : "null"}) + System.getProperty("line.separator") + "PAGE: " + p + System.getProperty("line.separator") + "REQUEST: " + r); //$NON-NLS-1$

            return columnValue;
        }
    }
}
