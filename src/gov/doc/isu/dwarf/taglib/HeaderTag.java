package gov.doc.isu.dwarf.taglib;

import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.BORDER;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.CLOSE_TAG;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.CSS_CLASS;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.DOUBLE_QUOTE;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.HREF;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.ID;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.IMG_END;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.IMG_OPEN;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.SPAN_END;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.SPAN_START;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.SRC;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TABLE_END;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TABLE_START;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TD_END;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TD_START;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TITLE;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TR_END;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TR_START;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * This tag builds the screen header for this application which displays the name of the application (e.g., My Web Application) and the toggle link to collapse/expand the FieldBoxTag.
 * <p>
 * <b>This tag can be used in a JSP as follows:</b>
 * 
 * <pre>
 * &lt;dwarf:header screenName=&quot;Listing&quot; addIcon=&quot;true&quot; addView=&quot;myAction.do?method=addView&quot; printIcon=&quot;false&quot; toggle=&quot;true&quot;/&gt
 * </pre>
 * 
 * </p>
 * 
 * @author James Eric Mansfield JEM01#IS
 */

public class HeaderTag extends TagSupport {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger("gov.doc.isu.dwarf.taglib.HeaderTag");
    private static final String NEW_LINE = System.getProperty("line.separator");
    private String formName;
    private String screenName;
    private Boolean addIcon = false;
    private String addView;
    private Boolean printIcon = false;
    private Boolean helpIcon = false;
    private Boolean requiredFields = false;
    private Boolean toggle = false;

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
     * @return the screenName
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     * @param screenName
     *        the screenName to set
     */
    public void setScreenName(final String screenName) {
        this.screenName = screenName;
    }

    /**
     * @return the addIcon
     */
    public Boolean getAddIcon() {
        return addIcon;
    }

    /**
     * @param addIcon
     *        the addIcon to set
     */
    public void setAddIcon(final Boolean addIcon) {
        this.addIcon = addIcon;
    }

    /**
     * @return the addView
     */
    public String getAddView() {
        return addView;
    }

    /**
     * @param addView
     *        the addView to set
     */
    public void setAddView(final String addView) {
        this.addView = addView;
    }

    /**
     * @return the helpIcon
     */
    public Boolean getHelpIcon() {
        return helpIcon;
    }

    /**
     * @param helpIcon
     *        the helpIcon to set
     */
    public void setHelpIcon(final Boolean helpIcon) {
        this.helpIcon = helpIcon;
    }

    /**
     * @return the printIcon
     */
    public Boolean getPrintIcon() {
        return printIcon;
    }

    /**
     * @param printIcon
     *        the printIcon to set
     */
    public void setPrintIcon(final Boolean printIcon) {
        this.printIcon = printIcon;
    }

    /**
     * @return the requiredFields
     */
    public Boolean getRequiredFields() {
        return requiredFields;
    }

    /**
     * @param requiredFields
     *        the requiredFields to set
     */
    public void setRequiredFields(final Boolean requiredFields) {
        this.requiredFields = requiredFields;
    }

    /**
     * @return the toggle
     */
    public Boolean getToggle() {
        return toggle;
    }

    /**
     * @param toggle
     *        the toggle to set
     */
    public void setToggle(final Boolean toggle) {
        this.toggle = toggle;
    }

    /**
     * Constructs HTML &lt;table&gt; element displaying information regarding page.
     * 
     * @return int
     * @throws JspException
     *         if an JspException occurred
     */
    public int doStartTag() throws JspException {
        log.debug("Entering HeaderTag.doStartTag");
        StringBuffer sb = new StringBuffer();
        String contextPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
        try{
            sb.append(TABLE_START).append(CSS_CLASS).append("applicationHeader").append(DOUBLE_QUOTE).append(CLOSE_TAG).append(NEW_LINE);
            sb.append(TR_START).append(TD_START).append(NEW_LINE);
            sb.append(SPAN_START).append(screenName).append(SPAN_END);
            // Show the Add Icon if true and get the anchor's href (addView attribute in the tag)
            if(addIcon){
                log.debug(" addIcon added to header");
                sb.append(" ").append("<a ").append(HREF).append(contextPath).append(addView).append(DOUBLE_QUOTE).append(CLOSE_TAG).append(IMG_OPEN).append(SRC).append(contextPath).append("/jsp/images/icons/add.gif").append(DOUBLE_QUOTE).append(TITLE).append("Add").append(DOUBLE_QUOTE).append(BORDER).append("0").append(DOUBLE_QUOTE).append(IMG_END).append("</a>");
            }
            // Show the Print Icon if true and set the javaScript protocol to the anchor's href
            if(printIcon){
                log.debug(" printIcon added to header");
                sb.append(" ").append("<a ").append(HREF).append("javascript:window.print()").append(DOUBLE_QUOTE).append(CLOSE_TAG).append(IMG_OPEN).append(SRC).append(contextPath).append("/jsp/images/icons/print.gif").append(DOUBLE_QUOTE).append(TITLE).append("Print").append(DOUBLE_QUOTE).append(BORDER).append("0").append(DOUBLE_QUOTE).append(IMG_END).append("</a>");
            }
            // Show the Help Icon if true and set the javaScript protocol to the anchor's href
            if(helpIcon){
                log.debug(" helpIcon added to header");
                sb.append(" ").append("<a ").append(HREF).append(contextPath).append("/jsp/common/ezGuide.pdf").append(DOUBLE_QUOTE).append(" target=").append(DOUBLE_QUOTE).append("blank").append(DOUBLE_QUOTE).append(CLOSE_TAG).append(IMG_OPEN).append(SRC).append(contextPath).append("/jsp/images/icons/help.gif").append(DOUBLE_QUOTE).append(TITLE).append("EZ-Guide").append(DOUBLE_QUOTE).append(BORDER).append("0").append(DOUBLE_QUOTE).append(IMG_END).append("</a>");
            }

            if(requiredFields){
                log.debug(" required fields added to header");
                sb.append(" ").append("<span ").append(CSS_CLASS).append("requiredAsteriskMsg").append(DOUBLE_QUOTE).append(CLOSE_TAG).append("* Required Field").append(SPAN_END);
            }
            sb.append(TD_END).append(NEW_LINE);
            // The toggle attribute below works in conjunction with dwarf:fieldDisplay which contains all the column checkBoxes
            // Show the toggle link if true. The javaScript is external in applicationDisplay.js file
            if(toggle){
                log.debug(" toggle added to header");
                sb.append("<td ").append("align=\"").append("right").append(DOUBLE_QUOTE).append(CLOSE_TAG).append(NEW_LINE);
                sb.append("<span ").append(CSS_CLASS).append("toggleLink").append(DOUBLE_QUOTE).append(ID).append("toggleLink").append(DOUBLE_QUOTE).append(CLOSE_TAG).append("Collapse").append("</span>").append(NEW_LINE);
                sb.append(TD_END);
            }
            sb.append(TR_END).append(TABLE_END);
            pageContext.getOut().print(sb.toString());
        }catch(final Exception e){
            log.error("Exception While constructing the HeaderTag for the Listing Page. e=" + e.getMessage());
        }
        log.debug("Exiting HeaderTag.doStartTag");
        return EVAL_PAGE;
    }
}
