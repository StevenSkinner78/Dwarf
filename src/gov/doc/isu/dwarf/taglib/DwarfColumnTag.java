package gov.doc.isu.dwarf.taglib;

import gov.doc.isu.dwarf.model.ColumnModel;
import gov.doc.isu.dwarf.taglib.displaytag.tags.ColumnTag;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

/**
 * This tag extends gov.doc.isu.dwarf.taglib.displaytag.tags.ColumnTag and sets all the default values that are associated with Dwarf application. This tag makes the associated jsp easier to maintain.
 * <p>
 * For tag to work:
 * <ul>
 * <li>Tag must be nested in &lt;dwarf:dataTable&gt; tag.</li>
 * <li>Action Form used in jsp must extend AbstractForm.</li>
 * </ul>
 * <b>This is a example of a list method in the action:</b>
 * 
 * <pre>
 * public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
 *         log.debug("Entering action.MyAction - method list&quot;);
 *         ActionForward forward = mapping.findForward(LIST);
 *         try{
 *             MyForm theForm = (MyForm) form;
 *             if(theForm.getFilterSearchObject() != null && !checkFilterSearchObject(theForm.getFilterSearchObject())){
 *                 theForm.setSessionList(TestBean.getListBySelectedCol(theForm.getSelectedFields(), theForm.getFieldDisplay()));
 *                 return search(mapping, theForm, request, response);
 *             }
 *             List&lt;TestModel&gt; testList = new ArrayList&lt;TestModel&gt;();
 *             theForm.setNoRecords(false);
 *             theForm.setGoButtonDisable(&quot;&quot;);
 *             if(theForm.getFieldDisplay() == null || theForm.getFieldDisplay().isEmpty()){
 *                 // this is the column names for the object. Loaded in a String array of checkboxes.
 *                 theForm.setFieldDisplay(VolunteerBean.getColumnNames());
 *                 log.debug(&quot;setting field display of checkboxes. size= &quot; + (AppUtil.isEmpty(theForm.getFieldDisplay()) ? &quot;null/empty&quot; : theForm.getFieldDisplay().size()));
 *             }// end if
 *              // get the staff list with the selected columns set in the columnData list
 *              // look for cookie of previous selected columns
 *             String cookieVal = CookieUtilities.getCookieValue(request, COOKIE_NAME, DEFAULT_COOKIE);
 *             log.debug(&quot;checking for existing cookie=&quot; + COOKIE_NAME);
 *             // set the selected fields
 *             theForm.setSelectedFields(cookieVal.split(HYPHEN));
 *             log.debug(&quot;setting fields display in listing. selectedFields.size=&quot; + (StringUtil.isNullOrEmpty(cookieVal) ? &quot;&quot; : cookieVal.split(HYPHEN).length));
 *             // set the searchFilter object
 *             setFilterSearchObject(theForm, theForm.getSelectedFields());
 *             log.debug(&quot;setting search object based on selected fields.&quot;);
 *             testList = TestBean.getListBySelectedCol(cookieVal.split(HYPHEN), theForm.getFieldDisplay());
 *             if(AppUtil.isEmpty(testList)){
 *                 testList = new ArrayList&lt;TestModel&gt;();
 *                 theForm.setNoRecords(true);
 *                 theForm.setGoButtonDisable(&quot;disabled&quot;);
 *                 TestModel t = new TestModel();
 *                 t.setColumnData(AppUtil.getNoRecordsFoundForDisplay(theForm.getFieldDisplay(), theForm.getSelectedFields()));
 *                 testList.add(t);
 *             }// end if
 *             theForm.setColumnInfo(testList.get(0).getColumnData());
 *             log.debug(&quot;setting stafflist. testList.size=&quot; + (AppUtil.isEmpty(testList) ? &quot;null/empty&quot; : testList.size()));
 *             // datalist used for display table
 *             <b>theForm.setDatalist(testList);</b>
 * 
 *             // sessionlist used for future search
 *             if(theForm.isNoRecords()){
 *                 testList = new ArrayList&lt;TestModel&gt;();
 *             }
 *             theForm.setSessionList(testList);
 * 
 *             if(StringUtil.isNullOrEmpty(theForm.getPageSize())){
 *                 theForm.setPageSize(DEFAULT_PAGE_SIZE);
 *             }// end if
 *             getReferenceData(theForm, request);
 *         }catch(Exception e){
 *             log.error(&quot;Exception caught in MyAction method list&quot;);
 *             forward = mapping.findForward(FAILURE);
 *         }// end catch
 *         log.debug(&quot;Exiting action.MyAction - method list()&quot;);
 *         return forward;
 *     }// end method
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
 *  <b>&lt;dwarf:columndata/&gt;</b>
 * &lt;/dwarf:dataTable&gt;
 * </pre>
 * 
 * </p>
 * 
 * @author Steven L. Skinner JCCC
 */
public class DwarfColumnTag extends ColumnTag {

    /**
     * 
     */
    private static final long serialVersionUID = 1141102110628568675L;

    private static Logger log = Logger.getLogger("gov.doc.isu.dwarf.taglib.DwarfColumnTag");

    /** This is the list of {@link ColumnModel} that will be used to load table data */

    /**
     * When the tag starts, we just initialize some of our variables, and do a little bit of error checking to make sure that the user is not trying to give us parameters that we don't expect.
     * 
     * @return int
     * @throws JspException
     *         if an JspException occurred
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        log.debug("Entering DwarfColumnTag.doStartTag");
        final String contextPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
        final DwarfTableTag t = (DwarfTableTag) findAncestorWithClass(this, DwarfTableTag.class);

        try{
            if(t.isNoRecords()){
                t.setExport(false);
                t.setProperty("paging.banner.one_item_found", "<span class=\"pagebanner\">Please Search Again.</span>");
                t.setProperty("paging.banner.page.link", "");
                t.setProperty("paging.banner.page.selected", "");
                doNoRecordsTable(t, contextPath);
            }else{
                doRecordsTable(t, contextPath);
            }// end else
        }catch(final JspException e){
            log.error("JspException occurred in DwarfColumnTag.doStartTag. e=" + e.getMessage());
            throw e;
        }catch(final Exception e){
            log.error("Exception occurred in DwarfColumnTag.doStartTag. e=" + e.getMessage());
        }// end catch
        log.debug("Exiting DwarfColumnTag.doStartTag");
        return super.doAfterBody();
    }// end method

    /**
     * {@inheritDoc}
     */
    public int doEndTag() {
        return EVAL_PAGE;
    }

    /**
     * This method loads the columns with data from the table tag
     * 
     * @param t
     *        the table tag with parent information
     * @param contextPath
     *        the context path for application
     * @throws JspException
     *         if an exception occurred
     */
    public void doRecordsTable(final DwarfTableTag t, final String contextPath) throws JspException {
        log.debug(" entering DwarfColumnTag.doRecordsTable");
        ColumnModel currentObject = null;
        try{
            super.release();
            super.setParent(t);
            // column data
            ArrayList<ColumnModel> columnList = (ArrayList<ColumnModel>) t.getTheCurrentRowColumns();
            Iterator<ColumnModel> iter = columnList.iterator();
            Integer i = 0;
            while(iter.hasNext()){
                currentObject = iter.next();
                super.release();
                super.setParent(t);
                super.setProperty("columnData[" + i + "].value");
                if(currentObject.getColumnName() != null){
                    super.setTitle(currentObject.getColumnName());
                }// end if
                if(currentObject.getMaxLength() < 0){
                    super.setMaxLength(20);
                }else{
                    super.setMaxLength(currentObject.getMaxLength());
                }// end else
                if(currentObject.get("media") != null){
                    super.setMedia((String) currentObject.get("media"));
                }// end if
                if(currentObject.get("style") != null){
                    super.setStyle((String) currentObject.get("style"));
                }// end if
                if(currentObject.get("sortProperty") != null){
                    super.setSortProperty((String) currentObject.get("sortProperty"));
                }// end if
                if(currentObject.get("paramId") != null){
                    super.setParamId((String) currentObject.get("paramId"));
                }
                if(currentObject.get("paramProperty") != null){
                    super.setParamProperty((String) currentObject.get("paramProperty"));
                }
                if(currentObject.get("url") != null){
                    super.setUrl((String) currentObject.get("url"));
                }
                if(currentObject.get("class") != null){
                    super.setClass((String) currentObject.get("class"));
                }
                if(currentObject.get("headerClass") != null){
                    super.setHeaderClass((String) currentObject.get("headerClass"));
                }
                super.setSortable(currentObject.getSortable());
                super.setSortType(currentObject.getType());
                super.setFormat(currentObject.getFormat());
                super.setDecorator(currentObject.getDecorator());
                super.doStartTag();
                super.doEndTag();
                i++;
            }// end while
        }catch(final JspException e){
            log.error(" JspException occurred in DwarfColumnTag.doRecordsTable. columnObject=" + String.valueOf(currentObject) + ". e=" + e.getMessage());
            throw e;
        }// end catch
        log.debug(" exiting DwarfColumnTag.doRecordsTable");
    }

    /**
     * This method loads the columns with with no data when there is none from table tag
     * 
     * @param t
     *        the table tag with parent information
     * @param contextPath
     *        the context path for application
     * @throws JspException
     *         if an exception occurred
     */
    public void doNoRecordsTable(final DwarfTableTag t, final String contextPath) throws JspException {
        log.debug(" entering DwarfColumnTag.doNoRecordsTable");
        try{
            super.release();
            super.setParent(t);
            ArrayList<ColumnModel> columnList = (ArrayList<ColumnModel>) t.getTheCurrentRowColumns();
            for(int i = 0, j = columnList.size();i < j;i++){
                super.setProperty("columnData[" + i + "].value");
                super.setTitle(columnList.get(i).get("key").toString());
                super.setSortable(false);
                super.doStartTag();
                super.doEndTag();
            }// end for
        }catch(final JspException e){
            log.error(" JspException occurred in DwarfColumnTag.doNoRecordsTable. e=" + e.getMessage());
            throw e;
        }// end catch
        log.debug(" exiting DwarfColumnTag.doNoRecordsTable");
    }
}
