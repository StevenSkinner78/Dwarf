package gov.doc.isu.dwarf.taglib;

import gov.doc.isu.dwarf.core.AbstractForm;
import gov.doc.isu.dwarf.taglib.displaytag.tags.FilterSearchRowTag;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

/**
 * This tag extends gov.doc.isu.dwarf.taglib.displaytag.tags.FilterSearchRowTag and sets all the default values that are associated with Dwarf application. This tag makes the associated jsp easier to maintain.
 * <p>
 * For tag to work:
 * <ul>
 * <li>Tag must be nested in &lt;dwarf:dataTable&gt; tag.</li>
 * <li>Action Form used in jsp must extend AbstractForm.</li>
 * <li>Action Class must have method called search.</li>
 * </ul>
 * <b>This is a example of a search method in the action:</b>
 * 
 * <pre>
 * public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
 *     log.debug(&quot;Entering action.MyAction - method search()&quot;);
 *     ActionForward forward = mapping.findForward(LIST);
 *     ActionMessages errors = new ActionMessages();
 *     try{
 *         MyForm theForm = (MyForm) form;
 *         errors = theForm.validateSearchFilters(getResources(request));
 *         if(!errors.isEmpty()){
 *             this.saveErrors(request, errors);
 *             return forward;
 *         }// end if
 *         theForm.setNoRecords(false);
 *         theForm.setGoButtonDisable(&quot;&quot;);
 *         ArrayList&lt;TestModel&gt; testList = new ArrayList&lt;TestModel&gt;();
 *         List&lt;ColumnModel&gt; fieldList = theForm.getFilterSearchObject();
 *         Iterator&lt;ColumnModel&gt; it = fieldList.iterator();
 *         if(!AppUtil.isEmpty(fieldList) || checkFilterSearchObject(fieldList)){
 *             List&lt;TestModel&gt; list = (ArrayList&lt;TestModel&gt;) theForm.getSessionList();
 *             for(int i = 0, j = list.size();i &lt; j;i++){
 *                 if(AppUtil.filter(fieldList, list.get(i).getColumnData())){
 *                     testList.add(list.get(i));
 *                 }// end if
 *             }// end for
 *             theForm.setFilterSearchObject(fieldList);
 *         }// end if
 *         log.debug(&quot;setting testList based on search object. testList.size=&quot; + (AppUtil.isEmpty(testList) ? &quot;null/empty&quot; : testList.size()));
 *         if(AppUtil.isEmpty(testList)){
 *             // check to see if session list is empty
 *             if(AppUtil.isEmpty(theForm.getSessionList())){
 *                 theForm.setNoRecords(true);
 *                 theForm.setGoButtonDisable(&quot;disabled&quot;);
 *                 testList = (ArrayList&lt;TestModel&gt;) theForm.getDatalist();
 *                 // check to see if all input boxes are empty. If so the reset list to full list.
 *             }else if(checkFilterSearchObject(fieldList)){
 *                 testList = (ArrayList&lt;TestModel&gt;) theForm.getSessionList();
 *             }else{
 *                 theForm.setNoRecords(true);
 *                 TestModel t = new TestModel();
 *                 t.setColumnData(AppUtil.getNoRecordsFoundForSearch(theForm.getFieldDisplay(), theForm.getSelectedFields()));
 *                 testList.add(t);
 *             }// end else
 *         }// end if
 *         theForm.setDatalist(testList);
 *     }catch(Exception e){
 *         log.error(&quot;Exception caught in MyAction method search.&quot;, e);
 *         forward = mapping.findForward(FAILURE);
 *     }// end catch
 *     log.debug(&quot;Exiting action.MyAction - method search()&quot;);
 *     return forward;
 * }// end method
 * </pre>
 * </p>
 * <p>
 * <b>This tag can be used in a JSP as follows:</b>
 * 
 * <pre>
 *    &lt;dwarf:dataTable uid=&quot;myListing&quot; toggle=&quot;collapsed&quot; toggleAll=&quot;collapsed&quot; sort=&quot;list&quot; formName=&quot;theForm&quot; requestURI=&quot;/myAction.do?method=updateListPageData&quot; export=&quot;true&quot; exportColOffset=&quot;2&quot;&gt;
 *     &lt;display:column media=&quot;html&quot; style=&quot;width: 10px; text-align:center;&quot;&gt;
 *         &lt;a id=&quot;editView&quot; class=&quot;enableWithRestrict&quot; href=&quot;&lt;%=request.getContextPath()%&gt;/volunteerDetailAction.do?method=editView&refId=${myListing.refId}&quot;&gt;
 *             &lt;img title=&quot;view&quot; border=&quot;0&quot; src=&quot;&lt;%=request.getContextPath()%&gt;/jsp/images/icons/view.gif&quot; id=&quot;view&quot; /&gt;
 *         &lt;/a&gt;
 *     &lt;/display:column&gt;
 *     &lt;display:column media=&quot;html&quot; style=&quot;width: 10px; text-align:center;&quot; paramId=&quot;refId&quot; paramProperty=&quot;refId&quot; url=&quot;/myAction.do?method=delete&quot;&gt;
 *         &lt;img title=&quot;delete&quot; border=&quot;0&quot; src=&quot;&lt;%=request.getContextPath()%&gt;/jsp/images/icons/trash.gif&quot; onclick=&quot;return deleteConfirmation();&quot; id=&quot;deleteIcon&quot; /&gt;
 *     &lt;/display:column&gt;<b>
 *      &lt;dwarf:searchrow url=&quot;myAction.do?method=search&quot; useColumnInfo=&quot;true&quot; ignoreColumns=&quot;0,1&quot;&gt;
 *      &lt;/dwarf:searchrow&gt;</b>           
 *     &lt;dwarf:columndata/&gt;
 *    &lt;/dwarf:dataTable&gt;
 * </pre>
 * </p>
 * 
 * @author Steven L. Skinner JCCC
 */
public class DwarfSearchTag extends FilterSearchRowTag {
    /**
     * 
     */
    private static final long serialVersionUID = 6947980271653962569L;

    private static Logger log = Logger.getLogger("gov.doc.isu.dwarf.taglib.DwarfSearchTag");

    private String url;
    private String ignoreColumns;
    private String buttonClass;
    private boolean useColumnInfo;
    private boolean buttonLast;
    private int buttonColSpan;
    private boolean showInstructions;

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
     * @return the ignoreColumns
     */
    public String getIgnoreColumns() {
        return ignoreColumns;
    }

    /**
     * @param ignoreColumns
     *        the ignoreColumns to set
     */
    public void setIgnoreColumns(String ignoreColumns) {
        this.ignoreColumns = ignoreColumns;
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

    /**
     * Sets values so that none have to be passed in from the jsp. ignoreColumns to 0,1. form name to that of the ancestor DwarfTableTag.getFormName. action to that of the ancestor DwarfTableTag.getRequestURI
     * 
     * @return int
     * @throws JspException
     *         if an JspException occurred
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        log.debug("Entering DwarfSearchTag.doStartTag");
        try{
            DwarfTableTag t = (DwarfTableTag) findAncestorWithClass(this, DwarfTableTag.class);
            log.debug(" setting inherited values.");
            super.setIgnoreColumns(ignoreColumns);
            super.setFormName(t.getFormName());
            super.setUrl(url);
            super.setButtonClass(buttonClass);
            super.setUseColumnInfo(useColumnInfo);
            super.setButtonLast(buttonLast);
            super.setButtonColSpan(buttonColSpan);
            super.setShowInstructions(showInstructions);
            if(useColumnInfo){
                AbstractForm theForm = (AbstractForm) pageContext.getRequest().getAttribute(t.getFormName());

                if(theForm == null){
                    log.debug("the form name passed in was null. Setting formName to session form.");
                    theForm = (AbstractForm) pageContext.getSession().getAttribute(t.getFormName());
                }
                super.setColumnInfo(theForm.getColumnInfo());
            }
            log.debug("Exiting DwarfSearchTag.doStartTag");
            return super.doStartTag();
        }catch(JspException e){
            log.error("JspException occurred in DwarfSearchTag.doStartTag. e=" + e.getMessage());
            throw e;
        }

    }

    /**
     * {@inheritDoc}
     */
    public int doEndTag() throws JspException {

        return super.doEndTag();
    }

}
