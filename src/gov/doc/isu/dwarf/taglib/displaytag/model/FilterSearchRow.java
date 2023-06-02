package gov.doc.isu.dwarf.taglib.displaytag.model;

import gov.doc.isu.dwarf.core.AbstractForm;
import gov.doc.isu.dwarf.model.ColumnModel;
import gov.doc.isu.dwarf.taglib.DwarfOptionsTag;
import gov.doc.isu.dwarf.taglib.DwarfSelectTag;
import gov.doc.isu.dwarf.taglib.displaytag.util.HtmlAttributeMap;
import gov.doc.isu.dwarf.taglib.displaytag.util.MultipleHtmlAttribute;
import gov.doc.isu.dwarf.taglib.displaytag.util.TagConstants;
import gov.doc.isu.dwarf.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.apache.struts.taglib.html.TextTag;

/**
 * Holds informations for a manually specified search row which goes above main row.
 * 
 * @author Will Glass-Husain
 * @author Steven Skinner JCCC
 */
@SuppressWarnings("serial")
public class FilterSearchRow extends TextTag {
    private static Logger log = Logger.getLogger("gov.doc.isu.dwarf.taglib.displaytag.model.FilterSearchRow");
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

    private String buttonClass;
    private String buttonTitle;

    private boolean buttonLast;
    private int buttonColSpan;
    private boolean showInstructions;
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
     * ColumnModel that should not have input box
     */
    private String ignoreColumns;

    private String formName;

    private String url;

    private List<ColumnModel> columnInfo;

    private boolean useColumnInfo;

    /**
     * @return the buttonClass
     */
    public String getButtonClass() {
        return buttonClass;
    }

    /**
     * @param buttonClass
     *        the buttonClass to set
     */
    public void setButtonClass(String buttonClass) {
        this.buttonClass = buttonClass;
    }

    /**
     * Constructor for row
     * 
     * @param content
     *        content
     */
    public FilterSearchRow(final String content) {
        this.row_content = content;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *        the id to set
     */
    public void setId(final String id) {
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
     * @return boolen
     */
    public boolean isPositionBelow() {
        return this.positionBelow;
    }

    /**
     * Setter for the row this subrow belongs to.
     * 
     * @param row
     *        Row
     */
    protected void setParentRow(final Row row) {
        this.row = row;
    }

    /**
     * Getter for the row this subrow belongs to.
     * 
     * @return TableModel
     */
    protected Row getParentRow() {
        return this.row;
    }

    /**
     * @return the columnInfo
     */
    public List<ColumnModel> getColumnInfo() {
        return columnInfo;
    }

    /**
     * @param columnInfo
     *        the columnInfo to set
     */
    public void setColumnInfo(List<ColumnModel> columnInfo) {
        this.columnInfo = columnInfo;
    }

    /**
     * @return the useColumnInfo
     */
    public boolean isUseColumnInfo() {
        return useColumnInfo;
    }

    /**
     * @param useColumnInfo
     *        the useColumnInfo to set
     */
    public void setUseColumnInfo(boolean useColumnInfo) {
        this.useColumnInfo = useColumnInfo;
    }

    /**
     * @return the buttonTitle
     */
    public String getButtonTitle() {
        return buttonTitle;
    }

    /**
     * @param buttonTitle
     *        the buttonTitle to set
     */
    public void setButtonTitle(String buttonTitle) {
        this.buttonTitle = buttonTitle;
    }

    /**
     * Writes the open &lt;tr> tag.
     * 
     * @return String &lt;tr> tag with the appropriate css class attribute
     */
    public String getOpenTag() {
        log.debug("Entering gov.doc.isu.dwarf.taglib.displaytag.model.FilterSearchRow -  method getOpenTag()");
        // use the default CSS unless an oddClass or evenClass property are
        // specified
        StringBuffer buffer = new StringBuffer();
        try{
            TableModel tablem = this.row.getParentTable();
            PageContext context = tablem.getPageContext();
            HttpSession session = context.getSession();
            AbstractForm theForm = (AbstractForm) context.getRequest().getAttribute(formName);
            if(theForm == null){
                theForm = (AbstractForm) session.getAttribute(formName);
            }
            ArrayList<ColumnModel> columnVOList = (ArrayList<ColumnModel>) theForm.getFilterSearchObject();
            ArrayList<ColumnModel> columnInfoList = null;
            if(useColumnInfo){
                columnInfoList = (ArrayList<ColumnModel>) columnInfo;
            }
            Integer numOfCol = tablem.getNumberOfColumns();

            if(showInstructions){
                buffer.append(TagConstants.TAG_TR_OPEN);
                buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_COLUMN);
                buffer.append(" ").append(TagConstants.ATTRIBUTE_COLSPAN);
                buffer.append("=\"");
                buffer.append(numOfCol);
                buffer.append("\"");
                buffer.append(TagConstants.TAG_CLOSE);
                buffer.append("<i>Search records in a column(s) that contain a string of character(s).</i>");
                buffer.append(TagConstants.TAG_TD_CLOSE);
                buffer.append(TagConstants.TAG_TR_CLOSE);
            }
            buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_ROW);

            if(this.htmlAttributes.size() > 0){
                buffer.append(this.htmlAttributes);
            }

            buffer.append(TagConstants.TAG_CLOSE);

            /** Construct TD */

            String[] ignoreColArray = null;
            ArrayList<Integer> ignoreList = new ArrayList<Integer>();

            if(ignoreColumns != null && ignoreColumns.indexOf(",") > 0){
                ignoreColArray = ignoreColumns.split(",");
                for(int i = 0, j = ignoreColArray.length;i < j;i++){
                    ignoreList.add(Integer.parseInt(ignoreColArray[i]));
                }
            }else if(ignoreColumns != null){
                ignoreList.add(Integer.parseInt(ignoreColumns));
            }

            // build td for "Go" button to process filter/search
            StringBuilder sb2 = new StringBuilder();
            sb2.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_COLUMN);
            sb2.append(TagConstants.EMPTY_SPACE).append(TagConstants.ATTRIBUTE_COLSPAN);
            sb2.append("=\"");
            if(buttonColSpan == 0){
                buttonColSpan = ignoreList.size();
            }
            sb2.append(buttonColSpan).append("\"").append(TagConstants.EMPTY_SPACE);
            sb2.append("style=\"").append("text-align:center;vertical-align: middle;").append("\"");
            sb2.append(TagConstants.TAG_CLOSE);
            sb2.append("<input name=\"subType\"").append(TagConstants.EMPTY_SPACE);
            sb2.append(" data-toggle=\"data-toggle\" type=\"").append("submit").append("\"").append(TagConstants.EMPTY_SPACE);
            sb2.append("title=\"").append("Process Search Filter").append("\"").append(TagConstants.EMPTY_SPACE);
            if(null != buttonClass){
                sb2.append("class=\"").append(buttonClass).append("\"").append(TagConstants.EMPTY_SPACE);
            }
            sb2.append("value=\"");
            if(buttonTitle == null){
                buttonTitle = "Go";
            }
            sb2.append(buttonTitle).append("\"").append(TagConstants.EMPTY_SPACE);
            sb2.append("id=\"").append("goButton").append("\"");
            sb2.append("/>");
            sb2.append(TagConstants.TAG_TD_CLOSE);
            // ////////////////////////////
            if(!buttonLast){
                buffer.append(sb2.toString());
            }else{
                if(buttonColSpan != ignoreList.size()){
                    int diff = ignoreList.size() - buttonColSpan;
                    for(int h = 0;h < diff;h++){
                        buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_COLUMN);
                        buffer.append(TagConstants.EMPTY_SPACE).append(TagConstants.ATTRIBUTE_COLSPAN);
                        buffer.append("=\"").append(1).append("\"");
                        buffer.append(TagConstants.TAG_CLOSE);
                        buffer.append(TagConstants.TAG_TD_CLOSE);
                    }
                }
            }

            // loop through columns and add a input box to every
            for(int y = 0, l = columnVOList.size();y < l;y++){
                buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_COLUMN);
                buffer.append(TagConstants.EMPTY_SPACE).append(TagConstants.ATTRIBUTE_COLSPAN);
                buffer.append("=\"").append(1).append("\"");
                buffer.append(TagConstants.TAG_CLOSE);
                if(!useColumnInfo || (useColumnInfo && (columnInfoList.get(y).get("isSelect") == null || !Boolean.valueOf(columnInfoList.get(y).get("isSelect").toString())))){
                    this.type = "search";
                    this.setPageContext(context);
                    this.setProperty("filterSearchObject[" + y + "].columnValue");
                    this.setValue(columnVOList.get(y).getColumnValue().toString());
                    this.setStyleClass("enableWithRestrict");
                    if(useColumnInfo){
                        if(columnInfoList.get(y).get("styleClass") != null && !StringUtil.isNullOrEmpty(columnInfoList.get(y).get("styleClass").toString())){
                            this.setStyleClass(columnInfoList.get(y).get("styleClass").toString());
                        }
                        if(columnInfoList.get(y).get("boxSize") != null && !StringUtil.isNullOrEmpty(columnInfoList.get(y).get("boxSize").toString())){
                            this.setSize(columnInfoList.get(y).get("boxSize").toString());
                        }
                        if(columnInfoList.get(y).getMaxLength() != null && !StringUtil.isNullOrEmpty(columnInfoList.get(y).getMaxLength().toString())){
                            String leng = String.valueOf(columnInfoList.get(y).getMaxLength() + 10);
                            this.setMaxlength(leng);
                        }
                        if(columnInfoList.get(y).get("errorStyleClass") != null && !StringUtil.isNullOrEmpty(columnInfoList.get(y).get("styleClass").toString())){
                            this.setErrorStyleClass(columnInfoList.get(y).get("errorStyleClass").toString());
                        }else{
                            this.setErrorStyleClass("error");
                        }
                    }
                    this.setErrorKey("org.apache.struts.action.ERROR");
                    buffer.append(this.renderInputElement());
                }else{
                    this.type = "select";
                    DwarfSelectTag tag = new DwarfSelectTag();
                    tag.setPageContext(context);
                    tag.setProperty("filterSearchObject[" + y + "].columnValue");
                    tag.setStyleClass("enableWithRestrict");

                    if(useColumnInfo){
                        if(columnInfoList.get(y).get("styleClass") != null && !StringUtil.isNullOrEmpty(columnInfoList.get(y).get("styleClass").toString())){
                            tag.setStyleClass(columnInfoList.get(y).get("styleClass").toString());
                        }
                        if(columnInfoList.get(y).get("boxSize") != null && !StringUtil.isNullOrEmpty(columnInfoList.get(y).get("boxSize").toString())){
                            tag.setSize(columnInfoList.get(y).get("boxSize").toString());
                        }
                        if(columnInfoList.get(y).get("multiple") != null && !StringUtil.isNullOrEmpty(columnInfoList.get(y).get("multiple").toString())){
                            tag.setMultiple("multiple");
                            tag.setProperty(columnVOList.get(y).getColumnName().toString().toLowerCase() + "SelectedValues");
                            tag.setValue(null);
                            if(columnInfoList.get(y).get("useJavascript") != null && Boolean.valueOf(columnInfoList.get(y).get("useJavascript").toString())){
                                tag.setOnfocus("javascript: selectFocusEvent(this);");
                                tag.setOnmouseover("javascript: selectFocusEvent(this);");
                                tag.setOnblur("javascript: selectBlurEvent(this);");
                                tag.setOnmouseout("javascript: selectBlurEvent(this);");
                                tag.setStyle("height:20px");
                            }
                        }else{
                            tag.setValue(columnVOList.get(y).getColumnValue().toString());
                        }
                        if(columnInfoList.get(y).get("id") != null && !StringUtil.isNullOrEmpty(columnInfoList.get(y).get("id").toString())){
                            tag.setStyleId(columnInfoList.get(y).get("id").toString());
                        }
                    }else{
                        tag.setValue(columnVOList.get(y).getColumnValue().toString());
                    }
                    tag.doStartTag();
                    buffer.append(tag.getStringBuffer().toString());
                    DwarfOptionsTag optionTag = new DwarfOptionsTag();
                    optionTag.setPageContext(context);
                    optionTag.setParent(tag);
                    optionTag.setCollection(columnInfoList.get(y).get("listName").toString());
                    optionTag.setLabelProperty("description");
                    optionTag.setProperty("description");
                    optionTag.doStartTag();
                    optionTag.doEndTag();
                    buffer.append(optionTag.getStringBuffer().toString());
                    tag.doAfterBody();
                    tag.doEndTag();
                    buffer.append(tag.getStringBuffer().toString());
                }
                buffer.append(TagConstants.TAG_TD_CLOSE);
            }
            if(buttonLast){
                buffer.append(sb2.toString());
            }
            // buffer.append(TagConstants.TAG_TR_CLOSE);
        }catch(Exception e){
            log.error("Exception occurred in gov.doc.isu.dwarf.taglib.displaytag.model.FilterSearchRow -  method getOpenTag. FilterSearchRow=" + toString(), e);
        }
        log.debug("Exiting gov.doc.isu.dwarf.taglib.displaytag.model.FilterSearchRow -  method getOpenTag()");
        return buffer.toString();

    }

    /**
     * writes the &lt;/tr> tag.
     * 
     * @return String &lt;/tr> tag
     */
    public String getCloseTag() {
        return TagConstants.TAG_TR_CLOSE;
    }

    /**
     * {@inheritDoc}
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
    public void setEvenClass(final MultipleHtmlAttribute evenClass) {
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
    public void setHtmlAttributes(final HtmlAttributeMap htmlAttributes) {
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
    public void setOddClass(final MultipleHtmlAttribute oddClass) {
        this.oddClass = oddClass;
    }

    /**
     * @param positionBelow
     *        The positionBelow to set.
     */
    public void setPositionBelow(final boolean positionBelow) {
        this.positionBelow = positionBelow;
    }

    /**
     * @param s
     *        The frequency to set
     */
    public void setFrequency(final String s) {
        frequency = DISPLAY_FIRST;
    }

    /**
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * @return the ignoreColumns
     */
    public String getIgnoreColumns() {
        return ignoreColumns;
    }

    /**
     * @param ignoreColumns
     *        the ignoreColumns to set
     */
    public void setIgnoreColumns(final String ignoreColumns) {
        this.ignoreColumns = ignoreColumns;
    }

    /**
     * @return the formName
     */
    public String getFormName() {
        return formName;
    }

    /**
     * @param formName
     *        the formName to set
     */
    public void setFormName(final String formName) {
        this.formName = formName;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *        the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * @return the buttonLast
     */
    public boolean isButtonLast() {
        return buttonLast;
    }

    /**
     * @param buttonLast
     *        the buttonLast to set
     */
    public void setButtonLast(boolean buttonLast) {
        this.buttonLast = buttonLast;
    }

    /**
     * @return the buttonColSpan
     */
    public int getButtonColSpan() {
        return buttonColSpan;
    }

    /**
     * @param buttonColSpan
     *        the buttonColSpan to set
     */
    public void setButtonColSpan(int buttonColSpan) {
        this.buttonColSpan = buttonColSpan;
    }

    /**
     * @return the showInstructions
     */
    public boolean isShowInstructions() {
        return showInstructions;
    }

    /**
     * @param showInstructions
     *        the showInstructions to set
     */
    public void setShowInstructions(boolean showInstructions) {
        this.showInstructions = showInstructions;
    }

}
