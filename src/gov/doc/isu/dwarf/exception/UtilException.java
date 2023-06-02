package gov.doc.isu.dwarf.exception;

/**
 * Utility Exception class
 * 
 * @author Steven L. Skinner
 */
public class UtilException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message
     *        message
     * @param ex
     *        throwable exception
     */
    public UtilException(final String message, final Throwable ex) {
        super(message, ex);
    }

    /**
     * @param message
     *        message
     */
    public UtilException(final String message) {
        super(message);
    }
}
