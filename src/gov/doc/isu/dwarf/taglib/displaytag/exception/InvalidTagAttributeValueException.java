/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.exception;

import gov.doc.isu.dwarf.taglib.displaytag.Messages;

/**
 * Exception thrown when an invalid value is given for a tag attribute.
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class InvalidTagAttributeValueException extends BaseNestableJspTagException {

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Constructor for InvalidTagAttributeValueException.
     * 
     * @param source
     *        Class where the exception is generated
     * @param attributeName
     *        String attribute name
     * @param attributeValue
     *        attribute value (invalid)
     */
    public InvalidTagAttributeValueException(Class source, String attributeName, Object attributeValue) {
        super(source, Messages.getString("InvalidTagAttributeValueException.msg", //$NON-NLS-1$
                new Object[]{attributeName, attributeValue}));
    }

    /**
     * @return SeverityEnum.ERROR
     * @see gov.doc.isu.dwarf.taglib.displaytag.exception.BaseNestableJspTagException#getSeverity()
     * @see gov.doc.isu.dwarf.taglib.displaytag.exception.SeverityEnum
     */
    public SeverityEnum getSeverity() {
        return SeverityEnum.ERROR;
    }

}
