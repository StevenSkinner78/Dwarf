package gov.doc.isu.dwarf.taglib.displaytag.tags;

import gov.doc.isu.dwarf.model.ColumnModel;
import gov.doc.isu.dwarf.taglib.displaytag.exception.InvalidTagAttributeValueException;
import gov.doc.isu.dwarf.taglib.displaytag.exception.TagStructureException;
import gov.doc.isu.dwarf.taglib.displaytag.model.FilterSearchRow;
import gov.doc.isu.dwarf.taglib.displaytag.properties.MediaTypeEnum;
import gov.doc.isu.dwarf.taglib.displaytag.util.HtmlAttributeMap;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

/**
 * @author sls00#is
 */
public class FilterSearchRowTag extends BodyTagSupport {

    /**
     * logger.
     */
    private static Logger log = Logger.getLogger(FilterSearchRowTag.class);

    /**
     * html pass-through attributes for cells.
     */
    private HtmlAttributeMap attributeMap = new HtmlAttributeMap();

    private String buttonClass;
    private boolean buttonLast;
    private int buttonColSpan;
    private String buttonTitle;
    private boolean showInstructions;
    /**
     * The media supported attribute.
     */
    private List supportedMedia;

    /**
     * if true, the row is below the main row
     */
    boolean positionBelow = false;

    /**
     * id for the sub row
     */
    private String id;

    /**
     * Should be set to "always", "first", or "last"
     */
    String frequency = "first";

    /**
     * ColumnModel to ignore for filter box.
     */
    private String ignoreColumns;

    /**
     * 
     */
    private String formName;

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
     * 
     */
    private String url;

    private List<ColumnModel> columnInfo;
    private boolean useColumnInfo;

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
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *        the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * setter for the "position" tag attribute.
     * 
     * @param value
     *        attribute value
     */
    public void setPositionBelow(boolean value) {
        this.positionBelow = false;
    }

    /**
     * setter for the "frequency" tag attribute.
     * 
     * @param value
     *        attribute value
     */
    public void setFrequency(String value) {
        this.frequency = "first";
    }

    /**
     * Is this row configured for the media type?
     * 
     * @param mediaType
     *        the currentMedia type
     * @return true if the row should be displayed for this request
     */
    public boolean availableForMedia(MediaTypeEnum mediaType) {
        if(supportedMedia == null){
            return true;
        }else{
            return this.supportedMedia.contains(mediaType);
        }
    }

    /**
     * Tag setter.
     * 
     * @param media
     *        the space delimited list of supported types
     * @throws InvalidTagAttributeValueException
     *         if an unknown media name is set
     */
    public void setMedia(String media) throws InvalidTagAttributeValueException {
        if(StringUtils.isBlank(media) || media.toLowerCase().indexOf("all") > -1){
            this.supportedMedia = null;
            return;
        }
        this.supportedMedia = new ArrayList();
        String[] values = StringUtils.split(media);
        for(int i = 0;i < values.length;i++){
            String value = values[i];
            if(!StringUtils.isBlank(value)){
                MediaTypeEnum type = MediaTypeEnum.fromName(value.toLowerCase());
                if(type == null){
                    throw new InvalidTagAttributeValueException(getClass(), "media", value);
                }
                this.supportedMedia.add(type);
            }
        }
    }

    /**
     * Passes attribute information up to the parent TableTag.
     * <p>
     * When we hit the end of the tag, we simply let our parent (which better be a TableTag) know what the user wants to do with this row. We do that by simple registering this tag with the parent. This tag's only job is to hold the configuration information to describe this particular row. The TableTag does all the work.
     * </p>
     * 
     * @return int
     * @throws JspException
     *         if this tag is being used outside of a &lt;display:list...&gt; tag.
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException {
        MediaTypeEnum currentMediaType = (MediaTypeEnum) this.pageContext.findAttribute(TableTag.PAGE_ATTRIBUTE_MEDIA);
        if(currentMediaType != null && !availableForMedia(currentMediaType)){
            if(log.isDebugEnabled()){
                log.debug("skipping row body, currentMediaType=" + currentMediaType);
            }
            return SKIP_BODY;
        }

        TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

        String content;
        if(this.bodyContent != null){
            content = this.bodyContent.getString();
            String lowercase_value = content.toLowerCase();
            // TODO check on this Commented code when time is available.
            // if ( (lowercase_value.indexOf("<td") != -1) || (lowercase_value.indexOf("</td>") != -1) ||
            // (lowercase_value.indexOf("<th") != -1) || (lowercase_value.indexOf("</th>") != -1)
            // )
            // {
            FilterSearchRow row = new FilterSearchRow(content);
            row.setPositionBelow(positionBelow);
            row.setFrequency(frequency);
            row.setButtonClass(buttonClass);
            row.setHtmlAttributes(attributeMap);
            row.setId(id);
            row.setIgnoreColumns(ignoreColumns);
            row.setFormName(formName);
            row.setUrl(url);
            row.setColumnInfo(columnInfo);
            row.setUseColumnInfo(useColumnInfo);
            row.setButtonLast(buttonLast);
            row.setButtonColSpan(buttonColSpan);
            row.setShowInstructions(showInstructions);
            row.setButtonTitle(buttonTitle);
            tableTag.addSearchRow(row);
            // }
        }

        return super.doEndTag();
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release() {
        super.release();
        this.attributeMap.clear();
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
        if(tableTag == null){
            throw new TagStructureException(getClass(), "searchRow", "table");
        }

        // If the list is empty, do not execute the body; may result in NPE
        if(tableTag.isEmpty()){
            return SKIP_BODY;
        }else{
            MediaTypeEnum currentMediaType = (MediaTypeEnum) this.pageContext.findAttribute(TableTag.PAGE_ATTRIBUTE_MEDIA);
            if(!availableForMedia(currentMediaType)){
                return SKIP_BODY;
            }
            return super.doStartTag();
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                //
                .append("bodyContent", this.bodyContent).append("attributeMap", this.attributeMap).toString();
    }

    public String getIgnoreColumns() {
        return ignoreColumns;
    }

    public void setIgnoreColumns(String ignoreColumns) {
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
    public void setFormName(String formName) {
        this.formName = formName;
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
     * @param showInstructions the showInstructions to set
     */
    public void setShowInstructions(boolean showInstructions) {
        this.showInstructions = showInstructions;
    }

    /**
     * @return the buttonTitle
     */
    public String getButtonTitle() {
        return buttonTitle;
    }

    /**
     * @param buttonTitle the buttonTitle to set
     */
    public void setButtonTitle(String buttonTitle) {
        this.buttonTitle = buttonTitle;
    }

}
