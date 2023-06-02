/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.exception;

/**
 * Exception thrown by column decorators. If a decorator need to throw a checked exception this should be nested in a DecoratorException.
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class DecoratorException extends BaseNestableJspTagException {

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Constructor for DecoratorException.
     * 
     * @param source
     *        Class where the exception is generated
     * @param message
     *        message
     */
    public DecoratorException(Class source, String message) {
        super(source, message);
    }

    /**
     * Constructor for DecoratorException.
     * 
     * @param source
     *        Class where the exception is generated
     * @param message
     *        message
     * @param cause
     *        previous exception
     */
    public DecoratorException(Class source, String message, Throwable cause) {
        super(source, message, cause);
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.exception.BaseNestableJspTagException#getSeverity()
     */
    public SeverityEnum getSeverity() {
        return SeverityEnum.ERROR;
    }

}
