package gov.doc.isu.dwarf.exception;

/**
 * DAO Exception
 * 
 * @author AAF00#IS, Steven L. Skinner
 */
public class DAOException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message
     *        message
     * @param ex
     *        ex
     */
    public DAOException(final String message, final Throwable ex) {
        super(message, ex);
    }

    /**
     * @param message
     *        message
     */
    public DAOException(final String message) {
        super(message);
    }

}
