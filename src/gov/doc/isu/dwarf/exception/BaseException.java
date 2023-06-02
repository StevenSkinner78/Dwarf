package gov.doc.isu.dwarf.exception;

/**
 * The base Exception for Dwarf Application
 * 
 * @author Steven L. Skinner
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * @param message
     *        message
     * @param ex
     *        ex
     */
    public BaseException(final String message, final Throwable ex) {
        super(message, ex);
    }

    /**
     * @param message
     *        message
     */
    public BaseException(final String message) {
        super(message);
    }
}
