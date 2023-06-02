package gov.doc.isu.dwarf.model;

import java.sql.Date;

/**
 * The Model object for application codes.
 * 
 * @author Steven L. Skinner
 */
public class CodeModel extends CommonModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2195717190558364943L;
    private String code;
    private String description;
    private Long sortSeqNumber;
    private Date startDate;
    private Date stopDate;

    /**
     * 
     */
    public CodeModel() {

    }

    /**
     * Class Constructor
     * 
     * @param code
     *        the ref id of code
     * @param description
     *        the description of code
     */
    public CodeModel(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *        the code to set
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *        the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return the sortSeqNumber
     */
    public Long getSortSeqNumber() {
        return sortSeqNumber;
    }

    /**
     * @param sortSeqNumber
     *        the sortSeqNumber to set
     */
    public void setSortSeqNumber(final Long sortSeqNumber) {
        this.sortSeqNumber = sortSeqNumber;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     *        the startDate to set
     */
    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the stopDate
     */
    public Date getStopDate() {
        return stopDate;
    }

    /**
     * @param stopDate
     *        the stopDate to set
     */
    public void setStopDate(final Date stopDate) {
        this.stopDate = stopDate;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "CodeModel [code=" + String.valueOf(this.code) + " ,description=" + String.valueOf(this.description) + "]";
    }
}
