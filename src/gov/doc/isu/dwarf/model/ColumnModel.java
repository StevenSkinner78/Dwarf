package gov.doc.isu.dwarf.model;

import gov.doc.isu.dwarf.resources.Constants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This object is mainly used for displaying data in jsp data table. Uses a hash map with key and value.
 * 
 * @author Steven Skinner JCCC
 */
public class ColumnModel extends HashMap<String, Object> {

    /**
     * 
     */
    private static final long serialVersionUID = -2605004430148570082L;

    /**
     * Default constructor
     */
    public ColumnModel() {
        super();
        clear();
        setColumnName("");
        setColumnValue(new Object());
        setType("");
        setFormat("");
        setDecorator("");
        setSortable(false);
        setMaxLength(20);
        setColumnNumber(0L);
    }

    /**
     * Constructor
     * 
     * @param columnName
     *        the name of the column
     * @param columnValue
     *        the value of the column
     */
    public ColumnModel(String columnName, Object columnValue, Long columnNumber) {
        super();
        clear();
        setColumnName(columnName);
        setColumnValue(columnValue);
        setType("");
        setFormat("");
        setDecorator("");
        setSortable(false);
        setMaxLength(20);
        setColumnNumber(columnNumber);
    }

    /**
     * Constructor
     * 
     * @param columnName
     *        the name of the column
     * @param columnValue
     *        the value of the column
     * @param sortable
     *        is the column sortable
     */
    public ColumnModel(String columnName, Object columnValue, boolean sortable, Long columnNumber) {
        super();
        clear();
        setColumnName(columnName);
        setColumnValue(columnValue);
        setType("");
        setFormat("");
        setDecorator("");
        setSortable(sortable);
        setMaxLength(20);
        setColumnNumber(columnNumber);
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return (String) this.get("key");
    }

    /**
     * @param columnName
     *        the columnName to set
     */
    public void setColumnName(final String columnName) {
        this.put("key", columnName);
    }

    /**
     * @return the columnValue
     */
    public Object getColumnValue() {
        return this.get("value");
    }

    /**
     * @param columnValue
     *        the columnValue to set
     */
    public void setColumnValue(final Object columnValue) {
        this.put("value", columnValue);
    }

    /**
     * @return the columnNumber
     */
    public Long getColumnNumber() {
        return (Long) this.get("columnNumber");
    }

    /**
     * @param columnNumber
     *        the columnNumber to set
     */
    public void setColumnNumber(final Long columnNumber) {
        this.put("columnNumber", columnNumber);
    }

    /**
     * @return the type
     */
    public String getType() {
        return (String) this.get("type");
    }

    /**
     * @param type
     *        the type to set
     */
    public void setType(final String type) {
        this.put("type", type);
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return (String) this.get("format");
    }

    /**
     * @param format
     *        the format to set
     */
    public void setFormat(final String format) {
        this.put("format", format);
    }

    /**
     * @return the sortable
     */
    public boolean getSortable() {
        return (Boolean) this.get("sortable");
    }

    /**
     * @param sortable
     *        the sortable to set
     */
    public void setSortable(boolean sortable) {
        this.put("sortable", sortable);
    }

    /**
     * @return the maxLength
     */
    public Integer getMaxLength() {
        return (Integer) this.get("maxLength");
    }

    /**
     * @param maxLength
     *        the maxLength to set
     */
    public void setMaxLength(Integer maxLength) {
        this.put("maxLength", maxLength);
    }

    /**
     * @return the decorator
     */
    public String getDecorator() {
        return (String) this.get("decorator");
    }

    /**
     * @param decorator
     *        the decorator to set
     */
    public void setDecorator(String decorator) {
        this.put("decorator", decorator);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        // fast exit when no attribute are present
        if(size() == 0){
            return Constants.EMPTY_STRING;
        }
        final StringBuffer sb = new StringBuffer();

        final Iterator<?> it = entrySet().iterator();
        // iterates on attributes
        sb.append(Constants.STR_OPEN_BRACE);
        while(it.hasNext()){
            final Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();

            // append a new atribute
            sb.append(entry.getKey());
            sb.append(Constants.EQUALS);
            sb.append(entry.getValue());
            if(it.hasNext()){
                sb.append(", ").append(Constants.NEW_LINE);
            }
        }
        sb.append(Constants.STR_CLOSE_BRACE);
        // return
        return sb.toString();
    }
}
