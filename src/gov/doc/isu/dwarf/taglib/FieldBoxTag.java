package gov.doc.isu.dwarf.taglib;

import gov.doc.isu.dwarf.core.AbstractForm;
import gov.doc.isu.dwarf.util.StringUtil;

import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.CLOSE_TAG;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.CSS_CLASS;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.DOUBLE_QUOTE;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.ID;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.LABEL_END;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.LABEL_START;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TABLE_END;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TABLE_START;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TD_END;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TD_START;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TR_END;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TR_START;

import java.util.Iterator;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.html.CheckboxTag;
import org.apache.struts.taglib.html.MultiboxTag;
import org.apache.struts.taglib.html.SubmitTag;

/**
 * This tag builds check boxes based on the information passed in that are associated with the columns to show in display table. Adds all javascript functions that are associated with these checkboxes.
 * <p>
 * For tag to work:
 * <ul>
 * <li>Action Form used in jsp must extend AbstractForm.</li>
 * <li>Action Class must have method called showTable.</li>
 * </ul>
 * <b>This is a example of a showTable method in the action:</b>
 * 
 * <pre>
 * public ActionForward showTable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
 *     log.debug(&quot;Entering action.MyAction - method showTable()&quot;);
 *     ActionForward forward = mapping.findForward(LIST);
 *     try{
 *         MyForm myForm = (MyForm) form;
 *         myForm.setNoRecords(false);
 *         List&lt;TestModel&gt; testList = null;
 *         testList = new ArrayList&lt;TestModel&gt;();
 *         setFilterSearchObject(theForm, theForm.getSelectedFields());
 *         log.debug(&quot;setting search object based on selected fields.&quot;);
 *         CookieUtilities.addCookie(response, myForm.getSelectedFields());
 *         log.debug(&quot;updating cookie value for cookie=&quot; + COOKIE_NAME + &quot;);
 *         testList = TestBean.getTestListBySelectedCol(myForm.getSelectedFields(), myForm.getFieldDisplay());
 *         log.debug(&quot;setting myForm. myForm.size=&quot; + (AppUtil.isEmpty(myForm) ? &quot;null/empty&quot; : myForm.size()));
 *         if(AppUtil.isEmpty(myForm)){
 *             myForm = new ArrayList&lt;TestModel&gt;();
 *             myForm.setNoRecords(true);
 *             TestModel t = new TestModel();
 *             t.setColumnData(AppUtil.getNoRecordsFoundForDisplay(myForm.getFieldDisplay(), myForm.getSelectedFields()));
 *             testList.add(t);
 *         }// end if
 *         myForm.setDatalist(testList);
 *         myForm.setSessionList(testList);
 *         myForm.setColumnInfo(testList.get(0).getColumnData());
 *     }catch(Exception e){
 *         log.error(&quot;Exception caught in in MyAction method showTable.&quot;, e);
 *         forward = mapping.findForward(FAILURE);
 *     }// end catch
 *     log.debug(&quot;Exiting action.MyAction - method showTable()&quot;);
 *     return forward;
 * }// end method
 * </pre>
 * 
 * </p>
 * <p>
 * <b>This tag can be used in a JSP as follows:</b>
 * </p>
 * 
 * <pre>
 *  &lt;dwarf:fieldDisplay formName=&quotmyForm&quot action=&quotmyAction&quot/&gt;
 * </pre>
 * 
 * @author Steven Skinner JCCC
 * @see AbstractForm
 */
public class FieldBoxTag extends MultiboxTag {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger("gov.doc.isu.dwarf.taglib.FieldBoxTag");
    private static final String NEW_LINE = System.getProperty("line.separator");
    /** Ready Method checks value of checkboxes when page loads. Calls function that is built in constructOnClickEvents() */
    private static final String READY_METHOD = NEW_LINE + "<script>$().ready(function() {" + NEW_LINE + "switchApplyAllButton('display', 'selectAll')" + NEW_LINE + "});" + "</script>";
    /** The name of form defined in struts-config */
    private String formName;
    /** The action defined in struts-config */
    private String action;
    /** The class name for label display */
    private String labelClass;

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
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action
     *        the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the labelClass
     */
    public String getLabelClass() {
        return labelClass;
    }

    /**
     * @param labelClass
     *        the labelClass to set
     */
    public void setLabelClass(String labelClass) {
        this.labelClass = labelClass;
    }

    /**
     * Constructs HTML &lt;checkbox&gt; element for each fieldDisplay string/
     * 
     * @return int
     * @throws JspException
     *         if an JspException occurred
     */
    public int doStartTag() throws JspException {
        log.debug("Entering FieldBoxTag.doStartTag");
        StringBuffer sb = new StringBuffer();
        HttpSession session = pageContext.getSession();
        /** Check the form in the request if not then try to find in the session */
        AbstractForm theForm = (AbstractForm) pageContext.getRequest().getAttribute(formName);
        if(theForm == null){
            theForm = (AbstractForm) session.getAttribute(formName);
        }
        try{

            int x = 0;
            sb.append("<div").append(ID).append("selectorDiv").append(DOUBLE_QUOTE).append(CLOSE_TAG).append(NEW_LINE);
            sb.append(TABLE_START).append(CSS_CLASS).append("width100pct").append(DOUBLE_QUOTE).append(CLOSE_TAG).append(NEW_LINE);
            sb.append(TR_START).append(TD_START).append(NEW_LINE);
            sb.append(TABLE_START).append(CSS_CLASS).append("columnSettings").append(DOUBLE_QUOTE).append(CLOSE_TAG);
            sb.append(NEW_LINE);
            sb.append(TR_START).append(NEW_LINE);
            sb.append(TD_START).append(NEW_LINE);
            sb.append(LABEL_START).append(CSS_CLASS).append(labelClass).append(DOUBLE_QUOTE).append(CLOSE_TAG).append("Select All:&nbsp;").append("</label>");
            pageContext.getOut().print(sb.toString());

            CheckboxTag selectAll = new CheckboxTag();
            selectAll.setPageContext(pageContext);
            selectAll.setStyleId("selectAll");
            selectAll.setStyleClass("fieldBox");
            selectAll.setOnclick("selectAllCheckBox('selectAll','false', 'fieldBox')");
            selectAll.doStartTag();
            log.debug(" render select all checkbox.");
            sb = new StringBuffer();
            sb.append(NEW_LINE).append(TD_END).append(NEW_LINE);
            sb.append(TR_END).append(NEW_LINE);
            sb.append(TR_START).append(NEW_LINE);
            pageContext.getOut().print(sb.toString());
            // loop through array of field names and check if it is already selected on form
            log.debug(" building checkboxes for fieldDisplay. The fields are=" + StringUtil.isNull(theForm.getFieldDisplay()));
            Iterator<String> display = theForm.getFieldDisplay().keySet().iterator();
            while(display.hasNext()){
                String string = display.next();
                pageContext.getOut().print(TD_START + NEW_LINE);
                super.setPageContext(pageContext);
                super.setProperty("selectedFields");
                super.setValue(string);
                String id = "display[" + string + "]";
                super.setStyleId(id);
                super.setStyleClass("fieldBox");
                super.setOnclick("switchApplyAllButton('display', 'selectAll')");
                super.doStartTag();
                super.doAfterBody();
                super.doEndTag();
                sb = new StringBuffer();
                sb.append(LABEL_START).append(CSS_CLASS).append(labelClass).append(DOUBLE_QUOTE).append(CLOSE_TAG);
                sb.append(theForm.getFieldDisplay().get(string));
                sb.append(LABEL_END).append(NEW_LINE);
                sb.append(TD_END).append(NEW_LINE);
                x++;
                // only put 5 checkboxes per tr
                if((x % 5) == 0){
                    sb.append(TR_END).append(TR_START).append(NEW_LINE);
                } // end if
                pageContext.getOut().print(sb.toString());
            } // end for loop

            sb = new StringBuffer();
            sb.append(TR_END).append(NEW_LINE);
            sb.append(TR_START).append(NEW_LINE);
            sb.append(TD_START).append(NEW_LINE);
            pageContext.getOut().print(sb.toString());

            SubmitTag button = new SubmitTag();
            button.setPageContext(pageContext);
            button.setStyleClass("button");
            button.setStyleId("columnButton");
            button.setValue("Select Columns");
            button.setTitle("Select Columns");
            button.setProperty("subType");
            button.doStartTag();
            button.doEndTag();
            sb = new StringBuffer();
            sb.append(TD_END).append(NEW_LINE);
            sb.append(TR_END).append(NEW_LINE);
            sb.append(TABLE_END);
            sb.append(TD_END).append(TR_END);
            sb.append(TR_START).append(NEW_LINE);
            sb.append(TD_START).append("<hr").append(CSS_CLASS);
            sb.append("separator").append(DOUBLE_QUOTE).append(CLOSE_TAG);
            sb.append(TD_END).append(TR_END).append(TABLE_END).append("</div>").append("<br/>");
            pageContext.getOut().print(sb.toString());
            if(log.isDebugEnabled()){
                log.debug("constucting javascript functions");
            }
            pageContext.getOut().print(constructOnClickEvents().toString());
            pageContext.getOut().print(READY_METHOD);
        }catch(Exception e){
            log.error("Exception While constructing the FieldBoxTag for the Listing Page e=" + e);
        }
        log.debug("Exiting FieldBoxTag.doStartTag");
        return SKIP_BODY;
    }

    /**
     * {@inheritDoc}
     */
    public int doEndTag() throws JspException {

        return EVAL_PAGE;
    }

    /**
     * builds javascript function for on click event handlers.
     * 
     * @return StringBuffer
     */
    private StringBuffer constructOnClickEvents() {
        log.debug(" entering FieldBoxTag.constructOnClickEvents");
        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE).append("<script type=\"text/javascript\">").append(NEW_LINE);
        // this is for select all checkbox clicked.
        sb.append("function selectAllCheckBox(applyButtonId, listRequired, checkBoxClassName){").append(NEW_LINE);
        sb.append("var checkVal = false;").append(NEW_LINE);
        sb.append("var resultList;").append(NEW_LINE);
        sb.append("if($('#'+applyButtonId).attr('checked')){").append(NEW_LINE);
        sb.append("checkVal = true;").append(NEW_LINE);
        sb.append("}").append(NEW_LINE);
        sb.append("$(\"input[type='checkbox']\").each(function(i){").append(NEW_LINE);
        sb.append("if( (isWhitespace(checkBoxClassName) || $(this).attr('class') == checkBoxClassName)");
        sb.append(" &&  $(this).attr('class') != 'notSelect') {").append(NEW_LINE);
        sb.append("$(this).attr('checked',checkVal);").append(NEW_LINE);
        sb.append("if(listRequired == \"true\" && ($(this).attr('id')  != applyButtonId)){").append(NEW_LINE);
        sb.append("val = $(this).val();").append(NEW_LINE).append("if(resultList == null){").append(NEW_LINE);
        sb.append("resultList = new Array();").append(NEW_LINE).append("}").append(NEW_LINE).append("if(checkVal){").append(NEW_LINE);
        sb.append("if($.inArray(val, resultList) == -1){").append(NEW_LINE).append("resultList[resultList.length]=val;").append(NEW_LINE);
        sb.append("}").append(NEW_LINE).append("} else {").append(NEW_LINE).append("resultList.splice($.inArray(val, resultList), 1);");
        sb.append(NEW_LINE).append("}").append(NEW_LINE).append("}").append(NEW_LINE).append("}").append(NEW_LINE).append("});");
        sb.append(NEW_LINE).append("if(listRequired == \"true\") {").append(NEW_LINE).append("return resultList;").append(NEW_LINE);
        sb.append("}").append(NEW_LINE).append("}").append(NEW_LINE);
        // this checks to see if all boxes are checked/unchecked to assign correct value to "selectAll" checkbox.
        sb.append("function switchApplyAllButton(checkBoxIdPrefix, applyButtonId){").append(NEW_LINE).append("var applyAll = true;");
        sb.append(NEW_LINE).append("$(\"input[id ^='\"+checkBoxIdPrefix+\"']\").each(function(i, element){").append(NEW_LINE);
        sb.append("if(element.checked == false){").append(NEW_LINE).append("applyAll = false;").append(NEW_LINE).append("}").append(NEW_LINE);
        sb.append("});").append(NEW_LINE).append("if($(\"input[id ^='\"+checkBoxIdPrefix+\"']\").length > 0){").append(NEW_LINE);
        sb.append("$('#'+applyButtonId).attr('checked',applyAll);").append(NEW_LINE).append("}").append(NEW_LINE).append("}");
        sb.append(NEW_LINE);
        sb.append("</script>").append(NEW_LINE);
        log.debug(" exiting FieldBoxTag.constructOnClickEvents");
        return sb;
    }
}
