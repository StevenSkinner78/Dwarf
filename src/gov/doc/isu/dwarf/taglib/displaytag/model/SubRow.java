package gov.doc.isu.dwarf.taglib.displaytag.model;

import gov.doc.isu.dwarf.taglib.displaytag.util.HtmlAttributeMap;
import gov.doc.isu.dwarf.taglib.displaytag.util.MultipleHtmlAttribute;
import gov.doc.isu.dwarf.taglib.displaytag.util.TagConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Holds informations for a manually specified row which goes either above or below each main row.
 * 
 * @author Will Glass-Husain
 * @author Steven Skinner JCCC
 */
public class SubRow {
    public static final int DISPLAY_ALWAYS = 0;
    public static final int DISPLAY_FIRST = 1;
    public static final int DISPLAY_LAST = 2;

    /**
     * sub row Id this is added to TR of the sub row
     */
    private String id;

    /**
     * true if below the main row, false if above the main row
     */
    private boolean positionBelow;

    /**
     * row content
     */
    private String row_content;

    /**
     * How often is this row displayed
     */
    private int frequency;

    /**
     * Row this column belongs to.
     */
    private Row row;

    /**
     * Map containing the html tag attributes for cells (tr).
     */
    private HtmlAttributeMap htmlAttributes;

    /**
     * Class name for the odd rows
     */
    private MultipleHtmlAttribute oddClass;

    /**
     * Class name for the even rows
     */
    private MultipleHtmlAttribute evenClass;

    /**
     * Constructor for SubRow.
     * 
     * @param content
     *        the content of the sub row
     */
    public SubRow(String content) {
        this.row_content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * getter for the row content
     * 
     * @return String object holding values for the current row.
     */
    public String getRowContent() {
        return this.row_content;
    }

    /**
     * Check whether the subrow is above or below the main row.
     * 
     * @return boolean true|false
     */
    public boolean isPositionBelow() {
        return this.positionBelow;
    }

    /**
     * Setter for the row this subrow belongs to.
     * 
     * @param row
     *        row
     */
    protected void setParentRow(Row row) {
        this.row = row;
    }

    /**
     * Getter for the row this subrow belongs to.
     * 
     * @return Row
     */
    protected Row getParentRow() {
        return this.row;
    }

    /**
     * Writes the open &lt;tr> tag.
     * 
     * @return String &lt;tr> tag with the appropriate css class attribute
     */
    public String getOpenTag() {
        // use the default CSS unless an oddClass or evenClass property are specified
        TableModel tablem = this.row.getParentTable();
        String toggle = tablem.getToggle();
        String css = "";// tablem.getProperties().getCssRow(this.row.getRowNumber());

        if(row.getRowNumber() % 2 == 0 && (evenClass != null))
            css = evenClass.toString();
        else if(!(row.getRowNumber() % 2 == 0) && (oddClass != null)) css = oddClass.toString();

        StringBuffer buffer = new StringBuffer();
        buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_ROW);

        if(this.htmlAttributes.size() > 0) buffer.append(this.htmlAttributes);

        // Adding the class attribute
        buffer.append(" ").append(TagConstants.ATTRIBUTE_CLASS);
        buffer.append("=\"");
        buffer.append(css);
        if("collapsed".equals(toggle)){
            buffer.append(" hidden");
        }
        buffer.append("\"");

        if(StringUtils.isNotBlank(getId())){
            buffer.append(" ").append(TagConstants.ATTRIBUTE_ID);
            buffer.append("=\"");
            buffer.append(getId());
            buffer.append("\"");
        }

        buffer.append(TagConstants.TAG_CLOSE);

        /** Construct TD */
        buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_COLUMN);
        buffer.append(" ").append(TagConstants.ATTRIBUTE_COLSPAN);
        buffer.append("=\"");
        buffer.append(tablem.getNumberOfColumns());
        buffer.append("\"");
        buffer.append(TagConstants.TAG_CLOSE);

        return buffer.toString();

    }

    /**
     * writes the &lt;/tr> tag.
     * 
     * @return String &lt;/tr> tag
     */
    public String getCloseTag() {
        return TagConstants.TAG_TD_CLOSE + TagConstants.TAG_TR_CLOSE;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("belowMainRow", this.positionBelow).append("rowContent", this.row_content).toString();
    }

    /**
     * @return Returns the evenClass.
     */
    public MultipleHtmlAttribute getEvenClass() {
        return evenClass;
    }

    /**
     * @param evenClass
     *        The evenClass to set.
     */
    public void setEvenClass(MultipleHtmlAttribute evenClass) {
        this.evenClass = evenClass;
    }

    /**
     * @return Returns the htmlAttributes.
     */
    public HtmlAttributeMap getHtmlAttributes() {
        return htmlAttributes;
    }

    /**
     * @param htmlAttributes
     *        The htmlAttributes to set.
     */
    public void setHtmlAttributes(HtmlAttributeMap htmlAttributes) {
        this.htmlAttributes = htmlAttributes;
    }

    /**
     * @return Returns the oddClass.
     */
    public MultipleHtmlAttribute getOddClass() {
        return oddClass;
    }

    /**
     * @param oddClass
     *        The oddClass to set.
     */
    public void setOddClass(MultipleHtmlAttribute oddClass) {
        this.oddClass = oddClass;
    }

    /**
     * @param positionBelow
     *        The positionBelow to set.
     */
    public void setPositionBelow(boolean positionBelow) {
        this.positionBelow = positionBelow;
    }

    public void setFrequency(String s) {
        if(s.equals("always"))
            frequency = DISPLAY_ALWAYS;
        else if(s.equals("first"))
            frequency = DISPLAY_FIRST;
        else if(s.equals("last"))
            frequency = DISPLAY_LAST;
        else throw new IllegalArgumentException("Attribute 'frequency' must be set equal to 'always', 'first', or 'last'.");
    }

    public int getFrequency() {
        return frequency;
    }
}
