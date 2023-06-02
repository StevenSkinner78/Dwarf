package gov.doc.isu.dwarf.core;

import static gov.doc.isu.dwarf.resources.Constants.CANCEL;
import static gov.doc.isu.dwarf.resources.Constants.EMPTY_STRING;
import static gov.doc.isu.dwarf.resources.Constants.KEY;
import static gov.doc.isu.dwarf.resources.Constants.LIST;
import static gov.doc.isu.dwarf.resources.Constants.VAL;
import gov.doc.isu.dwarf.model.ColumnModel;
import gov.doc.isu.dwarf.model.CommonModel;
import gov.doc.isu.dwarf.util.AppUtil;
import gov.doc.isu.dwarf.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

/**
 * <p>
 * An abstract Dispatch Action for Dwarf projects. </br> <b>This actions should extended by all Actions using the Dwarf framework.</b>
 * </p>
 * 
 * @author Steven L. Skinner
 */
public class AbstractAction extends DispatchAction {
    private static Logger log = Logger.getLogger("gov.doc.isu.dwarf.core.AbstractAction");
    private String className = "AbstractAction";
    private static final String PAGE_SIZE = "pagesize";
    private static final String DEFAULT_PAGE_SIZE = "15";
    private static String AUTHORIZATION_ERROR_FORWARD = "authError";

    /**
     * Automatically figures out the class name for extending classes
     */
    public AbstractAction() {
        this.className = this.getClass().getName();
    }

    /**
     * Automatically figures out the class name for extending classes
     * 
     * @param className
     *        className
     */
    public AbstractAction(final String className) {
        this.className = className;
    }

    /**
     * Forward to cancel if cancel button is clicked. "Cancelled" forward must be listed in action-mapping.
     * 
     * @param mapping
     *        (REQUIRED)- Information that the controller, RequestProcessor, knows about mapping the request to the instance of this Action class.
     * @param form
     *        (REQUIRED) - A JavaBean associated with mapping. It will have its properties initialized from the request parameters. Subsequently, properties of this bean have been populated
     * @param request
     *        (REQUIRED) - Provides data including parameter name and values, attributes, and an input stream
     * @param response
     *        (OPTIONAL) - Assist in sending a response to the client
     * @return ActionForward - Describes where and how control should be forwarded
     * @throws Exception
     *         if an exception occurred
     */
    protected ActionForward cancelled(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        return mapping.findForward(CANCEL);
    }

    /**
     * Returns true if every field in the list passed in is blank or empty, false if not.
     * 
     * @param list
     *        the search object fields
     * @return true|false value of the check
     */
    public boolean checkFilterSearchObject(final List<ColumnModel> list) {
        log.debug("Entering gov.doc.isu.dwarf.core.AbstractAction - method checkFilterSearchObject()");
        log.debug("Parameters are: list.size()=" + (list == null ? "null" : list.size()));
        boolean isAllBlank = false;
        Integer counter = 0;
        for(int i = 0, j = list.size();i < j;i++){
            if(list.get(i).getColumnValue() instanceof String[]){
                if(!AppUtil.checkForNotDefaultValue((String[]) list.get(i).getColumnValue())){
                    counter++;
                }
            }else{
                if(StringUtil.isNullOrEmpty(String.valueOf(list.get(i).getColumnValue())) || "...".equalsIgnoreCase(String.valueOf(list.get(i).getColumnValue()))){
                    counter++;
                }
            }
        }
        if(counter == list.size()){
            log.debug(" Search boxes were empty.");
            isAllBlank = true;
        }
        log.debug("Exiting gov.doc.isu.dwarf.core.AbstractAction - method checkFilterSearchObject()");
        return isAllBlank;
    }

    /**
     * This method builds a List<ColumnModel> based on selected fields and sets the filterSearchObject on theForm. It will first check of the fieldDisplay Map is empty if not then it will use the map to build the column names.
     * <p>
     * This method might need to be overridden if there is special circumstances. i.e. a multiple select in search row. <b>Example:</b>
     * </p>
     * 
     * <pre>
     * public void setFilterSearchObject(ExampleForm theForm, String[] fields) {
     *     log.debug(&quot;Entering gov.doc.isu.dwarf.example.action.ExampleAction - method setFilterSearchObject()&quot;);
     *     log.debug(&quot;Parameters are: fields=&quot; + StringUtil.collapseArray(fields));
     *     CommonModel common = new CommonModel();
     *     common.setColumnData(new ArrayList&lt;ColumnModel&gt;());
     *     for(int i = 0, j = fields.length;i &lt; j;i++){
     *         ColumnModel col = new ColumnModel();
     *         if(theForm.getFieldDisplay() != null &amp;&amp; !theForm.getFieldDisplay().isEmpty()){
     *             col.put(KEY, theForm.getFieldDisplay().get(fields[i]));
     *         }else{
     *             col.put(KEY, fields[i]);
     *         }
     *         <b>if(fields[i].equalsIgnoreCase(&quot;Site&quot;)){
     *             col.put(VAL, new String[100]);
     *         }else{
     *             col.put(VAL, EMPTY_STRING);
     *         }</b>
     *         common.getColumnData().add(col);
     *     }
     *     log.debug(&quot; filterSearchObject.size=&quot; + (AppUtil.isEmpty(common.getColumnData()) ? &quot;null/empty&quot; : common.getColumnData().size()));
     *     theForm.setFilterSearchObject(common.getColumnData());
     *     log.debug(&quot;Exiting gov.doc.isu.dwarf.example.action.ExampleAction - method setFilterSearchObject()&quot;);
     * }
     * </pre>
     * 
     * @param theForm
     *        form that extends AbstractForm
     * @param fields
     *        String[] of selected fields
     */
    public void setFilterSearchObject(AbstractForm theForm, String[] fields) {
        log.debug("Entering gov.doc.isu.dwarf.core.AbstractAction - method setFilterSearchObject()");
        log.debug("Parameters are: fields=" + StringUtil.collapseArray(fields));
        CommonModel common = new CommonModel();
        common.setColumnData(new ArrayList<ColumnModel>());
        for(int i = 0, j = fields.length;i < j;i++){
            ColumnModel col = new ColumnModel();
            if(theForm.getFieldDisplay() != null && !theForm.getFieldDisplay().isEmpty()){
                col.put(KEY, theForm.getFieldDisplay().get(fields[i]));
            }else{
                col.put(KEY, fields[i]);
            }
            col.put(VAL, EMPTY_STRING);
            common.getColumnData().add(col);
        }
        log.debug(" filterSearchObject.size=" + (AppUtil.isEmpty(common.getColumnData()) ? "null/empty" : common.getColumnData().size()));
        theForm.setFilterSearchObject(common.getColumnData());
        log.debug("Exiting gov.doc.isu.dwarf.core.AbstractAction - method setFilterSearchObject()");
    }

    /**
     * This method loads page size selection into form. Is called from the onChange event handler in PageSizeTag.
     * 
     * @param mapping
     *        (REQUIRED)- Information that the controller, RequestProcessor, knows about mapping the request to the instance of this Action class.
     * @param form
     *        (REQUIRED) - A JavaBean associated with mapping. It will have its properties initialized from the request parameters. Subsequently, properties of this bean have been populated
     * @param request
     *        (REQUIRED) - Provides data including parameter name and values, attributes, and an input stream
     * @param response
     *        (OPTIONAL) - Assist in sending a response to the client
     * @return ActionForward - Describes where and how control should be forwarded
     */
    public ActionForward changePageSize(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        log.debug("Entering gov.doc.isu.dwarf.core.AbstractAction - method changePageSize()");
        AbstractForm theForm = (AbstractForm) form;
        String pagesize = DEFAULT_PAGE_SIZE;
        if(!StringUtil.isNullOrEmpty(request.getParameter(PAGE_SIZE))){
            pagesize = request.getParameter(PAGE_SIZE);
        }
        log.debug(" setting pagesize to=" + pagesize);
        theForm.setPageSize(pagesize);
        log.debug("Exiting gov.doc.isu.dwarf.core.AbstractAction - method changePageSize()");
        return mapping.findForward(LIST);
    }

    /**
     * Refreshes page so that any changes to list is updated for display.
     * 
     * @param mapping
     *        (REQUIRED)- Information that the controller, RequestProcessor, knows about mapping the request to the instance of this Action class.
     * @param form
     *        (REQUIRED) - A JavaBean associated with mapping. It will have its properties initialized from the request parameters. Subsequently, properties of this bean have been populated
     * @param request
     *        (REQUIRED) - Provides data including parameter name and values, attributes, and an input stream
     * @param response
     *        (OPTIONAL) - Assist in sending a response to the client
     * @return ActionForward - Describes where and how control should be forwarded
     */
    public ActionForward refreshList(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) {
        return mapping.findForward(LIST);
    }

    /**
     * This method captures any data that is submitted from any of the list page This is required for enhanced touch list tag library
     * 
     * @param mapping
     *        (REQUIRED)- Information that the controller, RequestProcessor, knows about mapping the request to the instance of this Action class.
     * @param actionForm
     *        (REQUIRED) - A JavaBean associated with mapping. It will have its properties initialized from the request parameters. Subsequently, properties of this bean have been populated
     * @param request
     *        (REQUIRED) - Provides data including parameter name and values, attributes, and an input stream
     * @param response
     *        (OPTIONAL) - Assist in sending a response to the client
     * @return ActionForward - Describes where and how control should be forwarded
     */
    public ActionForward updateListPageData(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
        log.debug("updateListPageData for pagination");
        return new ActionForward(mapping.getInput());
    }

    /**
     * This method creates an action message and returns an 'authError' ActionForward.
     * 
     * @param mapping
     *        (REQUIRED)- Information that the controller, RequestProcessor, knows about mapping the request to the instance of this Action class.
     * @param request
     *        (REQUIRED) - Provides data including parameter name and values, attributes, and an input stream.
     * @param key
     *        (OPTIONAL) - The key of the message which needs to be displayed on the error page.
     * @return ActionForward ("authError")
     */
    public ActionForward createErrorMessage(final ActionMapping mapping, final HttpServletRequest request, final String key) {
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key));
        request.setAttribute("messages", messages);
        saveMessages(request, messages);
        return mapping.findForward(AUTHORIZATION_ERROR_FORWARD);
    }
}
