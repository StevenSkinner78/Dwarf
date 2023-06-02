package gov.doc.isu.dwarf.taglib;

import gov.doc.isu.dwarf.core.AbstractForm;
import gov.doc.isu.dwarf.taglib.displaytag.pagination.PaginatedList;
import gov.doc.isu.dwarf.taglib.displaytag.tags.TableTag;
import gov.doc.isu.dwarf.util.StringUtil;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

/**
 * This tag extends gov.doc.isu.dwarf.taglib.displaytag.tags.TableTag and sets all the default values that are associated with Dwarf application. This tag makes the associated jsp easier to maintain.
 * <p>
 * For tag to work:
 * <ul>
 * <li>Action Form used in jsp must extend AbstractForm.</li>
 * </ul>
 * <b>This is a example of a list method in the action:</b>
 * 
 * <pre>
 * public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
 *     log.debug(&quot;Entering action.MyAction - method list&quot;);
 *     ActionForward forward = mapping.findForward(LIST);
 *     try{
 *         MyForm theForm = (MyForm) form;
 *         if(theForm.getFilterSearchObject() != null &amp;&amp; !checkFilterSearchObject(theForm.getFilterSearchObject())){
 *             theForm.setSessionList(TestBean.getListBySelectedCol(theForm.getSelectedFields(), theForm.getFieldDisplay()));
 *             return search(mapping, theForm, request, response);
 *         }
 *         List&lt;TestModel&gt; testList = new ArrayList&lt;TestModel&gt;();
 *         theForm.setNoRecords(false);
 *         theForm.setGoButtonDisable(&quot;&quot;);
 *         if(theForm.getFieldDisplay() == null || theForm.getFieldDisplay().isEmpty()){
 *             // this is the column names for the object. Loaded in a String array of checkboxes.
 *             theForm.setFieldDisplay(VolunteerBean.getColumnNames());
 *             log.debug(&quot;setting field display of checkboxes. size= &quot; + (AppUtil.isEmpty(theForm.getFieldDisplay()) ? &quot;null/empty&quot; : theForm.getFieldDisplay().size()));
 *         }// end if
 *          // get the staff list with the selected columns set in the columnData list
 *          // look for cookie of previous selected columns
 *         String cookieVal = CookieUtilities.getCookieValue(request, COOKIE_NAME, DEFAULT_COOKIE);
 *         log.debug(&quot;checking for existing cookie=&quot; + COOKIE_NAME);
 *         // set the selected fields
 *         theForm.setSelectedFields(cookieVal.split(HYPHEN));
 *         log.debug(&quot;setting fields display in listing. selectedFields.size=&quot; + (StringUtil.isNullOrEmpty(cookieVal) ? &quot;&quot; : cookieVal.split(HYPHEN).length));
 *         // set the searchFilter object
 *         setFilterSearchObject(theForm, theForm.getSelectedFields());
 *         log.debug(&quot;setting search object based on selected fields.&quot;);
 *         testList = TestBean.getListBySelectedCol(cookieVal.split(HYPHEN), theForm.getFieldDisplay());
 *         if(AppUtil.isEmpty(testList)){
 *             testList = new ArrayList&lt;TestModel&gt;();
 *             theForm.setNoRecords(true);
 *             theForm.setGoButtonDisable(&quot;disabled&quot;);
 *             TestModel t = new TestModel();
 *             t.setColumnData(AppUtil.getNoRecordsFoundForDisplay(theForm.getFieldDisplay(), theForm.getSelectedFields()));
 *             testList.add(t);
 *         }// end if
 *         theForm.setColumnInfo(testList.get(0).getColumnData());
 *         log.debug(&quot;setting stafflist. testList.size=&quot; + (AppUtil.isEmpty(testList) ? &quot;null/empty&quot; : testList.size()));
 *         // datalist used for display table
 *         theForm.setDatalist(testList);
 * 
 *         // sessionlist used for future search
 *         if(theForm.isNoRecords()){
 *             testList = new ArrayList&lt;TestModel&gt;();
 *         }
 *         theForm.setSessionList(testList);
 * 
 *         if(StringUtil.isNullOrEmpty(theForm.getPageSize())){
 *             theForm.setPageSize(DEFAULT_PAGE_SIZE);
 *         }// end if
 *         getReferenceData(theForm, request);
 *     }catch(Exception e){
 *         log.error(&quot;Exception caught in MyAction method list&quot;);
 *         forward = mapping.findForward(FAILURE);
 *     }// end catch
 *     log.debug(&quot;Exiting action.MyAction - method list()&quot;);
 *     return forward;
 * }// end method
 * </pre>
 * 
 * </p>
 * <p>
 * <b>This tag can be used in a JSP as follows:</b>
 * 
 * <pre>
 * &lt;dwarf:dataTable uid=&quot;myListing&quot; toggle=&quot;collapsed&quot; toggleAll=&quot;collapsed&quot; sort=&quot;list&quot; formName=&quot;theForm&quot; requestURI=&quot;/myAction.do?method=updateListPageData&quot; export=&quot;true&quot; exportColOffset=&quot;2&quot;&gt;
 *  &lt;display:column media=&quot;html&quot; style=&quot;width: 10px; text-align:center;&quot;&gt;
 *      &lt;a id=&quot;editView&quot; class=&quot;enableWithRestrict&quot; href=&quot;&lt;%=request.getContextPath()%&gt;/volunteerDetailAction.do?method=editView&refId=${myListing.refId}&quot;&gt;
 *          &lt;img title=&quot;view&quot; border=&quot;0&quot; src=&quot;&lt;%=request.getContextPath()%&gt;/jsp/images/icons/view.gif&quot; id=&quot;view&quot; /&gt;
 *      &lt;/a&gt;
 *  &lt;/display:column&gt;
 *  &lt;display:column media=&quot;html&quot; style=&quot;width: 10px; text-align:center;&quot; paramId=&quot;refId&quot; paramProperty=&quot;refId&quot; url=&quot;/myAction.do?method=delete&quot;&gt;
 *      &lt;img title=&quot;delete&quot; border=&quot;0&quot; src=&quot;&lt;%=request.getContextPath()%&gt;/jsp/images/icons/trash.gif&quot; onclick=&quot;return deleteConfirmation();&quot; id=&quot;deleteIcon&quot; /&gt;
 *  &lt;/display:column&gt;
 *                
 *  &lt;dwarf:columndata columnData=&quot;${myListing.columnData}&quot;  noRecords=&quot;${theForm.noRecords}&quot; sortable=&quot;true&quot; /&gt;
 * &lt;/dwarf:dataTable&gt;
 * </pre>
 * 
 * </p>
 * 
 * @author Steven L. Skinner JCCC
 */
public class DwarfTableTag extends TableTag {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger("gov.doc.isu.dwarf.taglib.DwarfTableTag");
    private String formName;
    /**
     * Request uri.
     */
    private String requestURI;
    /**
     * Name of parameter which should not be forwarded during sorting or pagination.
     */
    private String excludedParams;

    /**
     * add export links.
     */
    private boolean export;
    /**
     * is a offender page.
     */
    private boolean offenderPage;
    /**
     * the table name
     */
    private String tableName;
    /**
     * toggle row
     */
    private String toggle;
    /**
     * toggle all
     */
    private String toggleAll;
    /**
     * "page" (sort a single page) or "list" (sort the full list)
     */
    private String sort;
    /**
     * "style class name html attribute
     */
    private String className;
    /**
     * "style" html attribute.
     */
    private String style;
    /**
     * cellspacing html attribute.
     */
    private String cellspacing;
    /**
     * cellpadding html attribute.
     */
    private String cellpadding;

    /**
     * Unique table id.
     */
    private String uid;
    /**
     * Preserve the current page and sort.
     */
    private boolean keepStatus;
    /**
     * Clear the current page and sort status.
     */
    private boolean clearStatus;
    /**
     * The paginated list containing the external pagination and sort parameters The presence of this paginated list is what determines if external pagination and sorting is used or not.
     */
    private PaginatedList paginatedList;
    /**
     * Uses to offset the number of columns primarily for exports to media other than HTML.
     */
    private int exportColOffset;

    /**
     * @return the cellspacing
     */
    public String getCellspacing() {
        return cellspacing;
    }

    /**
     * @param cellspacing
     *        the cellspacing to set
     */
    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    /**
     * @return the cellpadding
     */
    public String getCellpadding() {
        return cellpadding;
    }

    /**
     * @param cellpadding
     *        the cellpadding to set
     */
    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    /**
     * @return the requestURI
     */
    public String getRequestURI() {
        return requestURI;
    }

    /**
     * @param requestURI
     *        the requestURI to set
     */
    public void setRequestURI(final String requestURI) {
        this.requestURI = requestURI;
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
     * @return the excludedParams
     */
    public String getExcludedParams() {
        return excludedParams;
    }

    /**
     * @param excludedParams
     *        the excludedParams to set
     */
    public void setExcludedParams(final String excludedParams) {
        this.excludedParams = excludedParams;
    }

    /**
     * @return the export
     */
    public boolean isExport() {
        return export;
    }

    /**
     * @param export
     *        the export to set
     */
    public void setExport(final boolean export) {
        this.export = export;
    }

    /**
     * @return the offenderPage
     */
    public boolean isOffenderPage() {
        return offenderPage;
    }

    /**
     * @param offenderPage
     *        the offenderPage to set
     */
    public void setOffenderPage(final boolean offenderPage) {
        this.offenderPage = offenderPage;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName
     *        the tableName to set
     */
    public void setTableName(final String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the toggle
     */
    public String getToggle() {
        return toggle;
    }

    /**
     * @param toggle
     *        the toggle to set
     */
    public void setToggle(final String toggle) {
        this.toggle = toggle;
    }

    /**
     * @return the toggleAll
     */
    public String getToggleAll() {
        return toggleAll;
    }

    /**
     * @param toggleAll
     *        the toggleAll to set
     */
    public void setToggleAll(final String toggleAll) {
        this.toggleAll = toggleAll;
    }

    /**
     * @return the sort
     */
    public String getSort() {
        return sort;
    }

    /**
     * @param sort
     *        the sort to set
     */
    public void setSort(final String sort) {
        this.sort = sort;
    }

    /**
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid
     *        the uid to set
     */
    public void setUid(final String uid) {
        this.uid = uid;
    }

    /**
     * @return the keepStatus
     */
    public boolean isKeepStatus() {
        return keepStatus;
    }

    /**
     * @param keepStatus
     *        the keepStatus to set
     */
    public void setKeepStatus(final boolean keepStatus) {
        this.keepStatus = keepStatus;
    }

    /**
     * @return the clearStatus
     */
    public boolean isClearStatus() {
        return clearStatus;
    }

    /**
     * @param clearStatus
     *        the clearStatus to set
     */
    public void setClearStatus(final boolean clearStatus) {
        this.clearStatus = clearStatus;
    }

    /**
     * @return the paginatedList
     */
    public PaginatedList getPaginatedList() {
        return paginatedList;
    }

    /**
     * @param paginatedList
     *        the paginatedList to set
     */
    public void setPaginatedList(PaginatedList paginatedList) {
        this.paginatedList = paginatedList;
    }

    /**
     * @return the exportColOffset
     */
    public int getExportColOffset() {
        return exportColOffset;
    }

    /**
     * @param exportColOffset
     *        the exportColOffset to set
     */
    public void setExportColOffset(int exportColOffset) {
        this.exportColOffset = exportColOffset;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className
     *        the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style
     *        the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * When the tag starts, we just initialize some of our variables, and do a little bit of error checking to make sure that the user is not trying to give us parameters that we don't expect.
     * 
     * @return int
     * @throws JspException
     *         if an JspException occurred
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        log.debug("entering DwarfTableTag.doStartTag");
        final HttpSession session = pageContext.getSession();
        /** Check the form in the request if not then try to find in the session */
        AbstractForm theForm = (AbstractForm) pageContext.getRequest().getAttribute(formName);

        if(theForm == null){
            log.debug("the form name passed in was null. Setting formName to session form.");
            theForm = (AbstractForm) session.getAttribute(formName);
        }
        if(theForm.getPaginatedList() != null){
            super.setName(theForm.getPaginatedList());
        }else{
            super.setName(theForm.getDatalist());
        }
        super.setNoRecords(theForm.isNoRecords());
        super.setUid(uid);
        super.setSort(sort);
        super.setPagesize(Integer.parseInt(theForm.getPageSize()));
        super.setRequestURI(requestURI);
        super.setExcludedParams(excludedParams);
        super.setExport(export);
        super.setCellpadding(StringUtil.isNullOrEmpty(cellpadding) ? "" : cellpadding);
        super.setCellspacing(StringUtil.isNullOrEmpty(cellspacing) ? "" : cellspacing);
        super.setClass(StringUtil.isNullOrEmpty(className) ? "" : className);
        super.setStyle(StringUtil.isNullOrEmpty(style) ? "" : style);
        super.setOffenderPage(offenderPage);
        super.setTableName(tableName);
        super.setToggle(toggle);
        super.setToggleAll(toggleAll);
        super.setKeepStatus(keepStatus);
        super.setClearStatus(clearStatus);
        super.setExportColOffset(exportColOffset);
        log.debug("Exiting DwarfTableTag.doStartTag");
        return super.doStartTag();
    }

    /**
     * {@inheritDoc}
     */
    public int doAfterBody() {
        return super.doAfterBody();
    }

    /**
     * {@inheritDoc}
     */
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }
}
