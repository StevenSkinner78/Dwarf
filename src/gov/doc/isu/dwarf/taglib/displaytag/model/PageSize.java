package gov.doc.isu.dwarf.taglib.displaytag.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.html.OptionTag;
import org.apache.struts.taglib.html.SelectTag;

import gov.doc.isu.dwarf.core.AbstractForm;
import gov.doc.isu.dwarf.util.StringUtil;

/**
 * This tag builds a drop down box of values to change the page size of list that is displayed on the screen.<br/>
 * The options for selecting are 5,10,15,20,25,50,100,200.
 * <p>
 * For tag to work:
 * <ul>
 * <li>Action Form used in jsp must extend gov.doc.isu.dwarf.core.AbstractForm.</li>
 * <li>Action Class must extend gov.doc.isu.dwarf.core.AbstractAction which has method changePageSize() which will reload the list with the correct number of records to display on screen.</li>
 * </uL>
 * </p>
 * <p>
 * <b>This tag can be used in a JSP as follows:</b>
 * </p>
 * 
 * <pre>
 * &lt;dwarf:pageSize formName=&quot;theForm&quot; action=&quot;/myAction&quot; type=&quot;example&quot;/&gt;
 * </pre>
 * 
 * @author Steven Skinner JCCC
 */
public class PageSize {
    private static Logger log = Logger.getLogger("gov.doc.isu.dwarf.taglib.displaytag.model.PageSize");
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String LABEL_START = "<label class=\"";
    private static final String DOUBLE_QUOTE = "\"";
    private static final String LABEL_END = ">";
    private static final String LABEL_CLOSE = "</label>";
    private static final String DIV_FORM_GROUP = "<div class=\"form-group\">";
    private static final String DIV_CLOSE = "</div>";
    /** This the id of the Action Form defined on jsp. */
    private String formName;
    /** This is the action associated with Action Form that is defined in struts-config. */
    private String action;
    /** This can be used to differentiate between screens that might use same Action Form */
    private String type;
    /** This can be used to define a different method then changePageSize() if additional work is needed to update list */
    private String method;

    private String styleClass;

    private String labelOne;

    private String labelTwo;

    private String labelOneClass;

    private String labelTwoClass;

    /**
     * Output destination.
     */
    private JspWriter out;

    private TableModel model;

    private boolean useAll;

    /**
     * 
     */
    public PageSize(JspWriter out, TableModel model) {
        super();
        this.out = out;
        this.model = model;
    }

    /**
     * @param formName
     * @param action
     * @param type
     * @param method
     * @param styleClass
     * @param labelOne
     * @param labelTwo
     * @param labelOneStyle
     * @param labelTwoStyle
     * @param out
     * @param model
     */
    public PageSize(String formName, String action, String type, String method, String styleClass, String labelOne, String labelOneStyle, String labelTwo, String labelTwoStyle, JspWriter out, TableModel model) {
        super();
        this.formName = formName;
        this.action = action;
        this.type = type;
        this.method = method;
        this.styleClass = styleClass;
        this.labelOne = labelOne;
        this.labelTwo = labelTwo;
        this.labelOneClass = labelOneStyle;
        this.labelTwoClass = labelTwoStyle;
        this.out = out;
        this.model = model;
    }

    /**
     * @return the out
     */
    public JspWriter getOut() {
        return out;
    }

    /**
     * @return the model
     */
    public TableModel getModel() {
        return model;
    }

    /**
     * @param model
     *        the model to set
     */
    public void setModel(TableModel model) {
        this.model = model;
    }

    /**
     * @param out
     *        the out to set
     */
    public void setOut(JspWriter out) {
        this.out = out;
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
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action
     *        the action to set
     */
    public void setAction(final String action) {
        this.action = action;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *        the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method
     *        the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the styleClass
     */
    public String getStyleClass() {
        return styleClass;
    }

    /**
     * @param styleClass
     *        the styleClass to set
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * @return the labelOne
     */
    public String getLabelOne() {
        return labelOne;
    }

    /**
     * @param labelOne
     *        the labelOne to set
     */
    public void setLabelOne(String labelOne) {
        this.labelOne = labelOne;
    }

    /**
     * @return the labelTwo
     */
    public String getLabelTwo() {
        return labelTwo;
    }

    /**
     * @param labelTwo
     *        the labelTwo to set
     */
    public void setLabelTwo(String labelTwo) {
        this.labelTwo = labelTwo;
    }

    /**
     * @return the labelOneClass
     */
    public String getLabelOneClass() {
        return labelOneClass;
    }

    /**
     * @param labelOneClass
     *        the labelOneClass to set
     */
    public void setLabelOneClass(String labelOneClass) {
        this.labelOneClass = labelOneClass;
    }

    /**
     * @return the labelTwoClass
     */
    public String getLabelTwoClass() {
        return labelTwoClass;
    }

    /**
     * @param labelTwoClass
     *        the labelTwoClass to set
     */
    public void setLabelTwoClass(String labelTwoClass) {
        this.labelTwoClass = labelTwoClass;
    }

    /**
     * @return the useAll
     */
    public boolean isUseAll() {
        return useAll;
    }

    /**
     * @param useAll
     *        the useAll to set
     */
    public void setUseAll(boolean useAll) {
        this.useAll = useAll;
    }

    /**
     * {@inheritDoc}
     */
    public void doStartTag() {
        log.debug("Entering PageSize - method doStartTag()");
        PageContext context = model.getPageContext();
        HttpSession session = context.getSession();
        AbstractForm theForm = (AbstractForm) context.getRequest().getAttribute(formName);
        if(theForm == null){
            theForm = (AbstractForm) session.getAttribute(formName);
        }
        String contextPath = ((HttpServletRequest) context.getRequest()).getContextPath();
        StringBuilder sb = new StringBuilder();
        sb.append("refreshPage('");
        sb.append(contextPath);
        sb.append(action);
        sb.append(".do?method=");
        sb.append((StringUtil.isNullOrEmpty(method)) ? "changePageSize" : method);
        sb.append("&screen=");
        sb.append((StringUtil.isNullOrEmpty(type)) ? "" : type);
        sb.append("')");
        if(theForm == null){
            theForm = (AbstractForm) session.getAttribute(formName);
        }
        try{
            log.debug(" start building drop down for page size.");
            if(!StringUtil.isNullOrEmpty(labelOne)){
                StringBuilder sb1 = new StringBuilder();
                sb1.append(DIV_FORM_GROUP);
                sb1.append(LABEL_START);
                if(!StringUtil.isNullOrEmpty(labelOneClass)){
                    sb1.append(labelOneClass);
                }
                sb1.append(DOUBLE_QUOTE).append(LABEL_END);
                sb1.append(labelOne);
                sb1.append(LABEL_CLOSE);
                sb1.append(DIV_CLOSE);
                out.write(sb1.toString());
            }
            out.write(DIV_FORM_GROUP);
            final SelectTag select = new SelectTag();
            select.setPageContext(context);
            select.setProperty("pagesize");
            select.setStyleId("pagesize");
            select.setOnchange(sb.toString());
            select.setValue(theForm.getPageSize());
            select.setStyleClass(styleClass);
            select.doStartTag();
            for(int x = 1;x < 9;x++){
                out.write(NEW_LINE);
                final OptionTag optionTag = new OptionTag();
                String key = "";
                if((x % 6) == 0){
                    key = String.valueOf(50);
                    optionTag.setParent(select);
                    optionTag.setValue(key);
                    log.debug("  add option=" + String.valueOf(optionTag.getValue()));
                }else if((x % 7) == 0){
                    key = String.valueOf(100);
                    optionTag.setParent(select);
                    optionTag.setValue(key);
                    log.debug("  add option=" + String.valueOf(optionTag.getValue()));
                }else if((x % 8) == 0){
                    key = String.valueOf(200);
                    optionTag.setParent(select);
                    if(useAll){
                        optionTag.setValue("0");
                        optionTag.setKey("label.dropdown.all");
                    }else{
                        optionTag.setValue(key);
                    }
                    log.debug("  add option=" + String.valueOf(optionTag.getValue()));
                }else{
                    key = String.valueOf(x * 5);
                    optionTag.setParent(select);
                    optionTag.setValue(key);
                    log.debug("  add option=" + String.valueOf(optionTag.getValue()));
                }// end else
                optionTag.setPageContext(context);
                optionTag.doStartTag();
                optionTag.doEndTag();
            }// end for
            select.doAfterBody();
            select.doEndTag();
            out.write(DIV_CLOSE);
            if(!StringUtil.isNullOrEmpty(labelTwo)){
                StringBuilder sb2 = new StringBuilder();
                sb2.append(DIV_FORM_GROUP);
                sb2.append(LABEL_START);
                if(!StringUtil.isNullOrEmpty(labelTwoClass)){
                    sb2.append(labelTwoClass);
                }
                sb2.append(DOUBLE_QUOTE).append(LABEL_END);
                sb2.append(labelTwo);
                sb2.append(LABEL_CLOSE);
                sb2.append(DIV_CLOSE);
                out.write(sb2.toString());
            }
            log.debug(" end building drop down for page size.");
        }catch(

        final Exception e){
            log.error("Exception occurred while  constructing the PageSize for the Listing Page ", e);
        }// end catch
        log.debug("Exiting PageSize - method doStartTag()");
    }// end method
}// end class
