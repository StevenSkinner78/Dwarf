package gov.doc.isu.dwarf.taglib.displaytag.tags;

import gov.doc.isu.dwarf.taglib.displaytag.exception.InvalidTagAttributeValueException;
import gov.doc.isu.dwarf.taglib.displaytag.exception.TagStructureException;
import gov.doc.isu.dwarf.taglib.displaytag.model.SubRow;
import gov.doc.isu.dwarf.taglib.displaytag.properties.MediaTypeEnum;
import gov.doc.isu.dwarf.taglib.displaytag.util.HtmlAttributeMap;
import gov.doc.isu.dwarf.taglib.displaytag.util.MultipleHtmlAttribute;
import gov.doc.isu.dwarf.taglib.displaytag.util.TagConstants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

public class SubRowTag extends BodyTagSupport {

    /**
     * logger.
     */
    private static Logger log = Logger.getLogger(SubRowTag.class);

    /**
     * html pass-through attributes for cells.
     */
    private HtmlAttributeMap attributeMap = new HtmlAttributeMap();

    /**
     * The media supported attribute.
     */
    private List supportedMedia;

    /**
     * if true, the row is below the main row
     */
    boolean positionBelow = true;

    /**
     * id for the sub row
     */
    private String id;

    /**
     * Should be set to "always", "first", or "last"
     */
    String frequency = "always";

    /**
     * Class name for the odd rows (may be multiple classes)
     */
    private MultipleHtmlAttribute oddClass;

    /**
     * Class name for the even rows (may be multiple classes)
     */
    private MultipleHtmlAttribute evenClass;

    /**
     * setter for the "background" tag attribute.
     * 
     * @param value
     *        attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setBackground(String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_BACKGROUND, value);
    }

    /**
     * setter for the "bgcolor" tag attribute.
     * 
     * @param value
     *        attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setBgcolor(String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_BGCOLOR, value);
    }

    /**
     * setter for the "height" tag attribute.
     * 
     * @param value
     *        attribute value
     */
    public void setHeight(String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_HEIGHT, value);
    }

    /**
     * setter for the "style" tag attribute.
     * 
     * @param value
     *        attribute value
     */
    public void setStyle(String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_STYLE, value);
    }

    /**
     * setter for the "position" tag attribute.
     * 
     * @param value
     *        attribute value
     */
    public void setPositionBelow(boolean value) {
        this.positionBelow = value;
    }

    /**
     * setter for the "id" tag attribute.
     * 
     * @param id
     *        id value
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * setter for the "frequency" tag attribute.
     * 
     * @param value
     *        attribute value
     */
    public void setFrequency(String value) {
        value = value.toLowerCase();
        if(!value.equals("always") && !value.equals("first") && !value.equals("last")) throw new IllegalArgumentException("Attribute 'frequency' must be set equal to 'always', 'first', or 'last'.");

        this.frequency = value;
    }

    /**
     * setter for the "oddClass" tag attribute.
     * 
     * @param value
     *        attribute value
     */
    public void setOddClass(String value) {
        oddClass = new MultipleHtmlAttribute(value);
    }

    /**
     * setter for the "evenClass" tag attribute.
     * 
     * @param value
     *        attribute value
     */
    public void setEvenClass(String value) {
        evenClass = new MultipleHtmlAttribute(value);
    }

    /**
     * Adds a css class to the class attribute (html class suports multiple values).
     * 
     * @param value
     *        attribute value
     */
    public void addEvenClass(String value) {
        if(evenClass == null){
            evenClass = new MultipleHtmlAttribute(value);
        }else{
            evenClass.addAttributeValue(value);
        }
    }

    /**
     * Adds a css class to the class attribute (html class suports multiple values).
     * 
     * @param value
     *        attribute value
     */
    public void addOddClass(String value) {
        if(oddClass == null){
            oddClass = new MultipleHtmlAttribute(value);
        }else{
            oddClass.addAttributeValue(value);
        }
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
            SubRow row = new SubRow(content);
            row.setPositionBelow(positionBelow);
            row.setFrequency(frequency);
            row.setEvenClass(evenClass);
            row.setOddClass(oddClass);
            row.setHtmlAttributes(attributeMap);
            row.setId(id);
            tableTag.addSubRow(row);
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
            throw new TagStructureException(getClass(), "subRow", "table");
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
}
