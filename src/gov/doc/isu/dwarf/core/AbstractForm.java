package gov.doc.isu.dwarf.core;

import gov.doc.isu.dwarf.model.ColumnModel;
import gov.doc.isu.dwarf.taglib.displaytag.pagination.PaginatedList;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * A Generic form for application.
 *
 * @author Steven L. Skinner
 */
public class AbstractForm extends ValidatorForm {

    private static final long serialVersionUID = 1L;
    private String appName;
    private Collection<?> datalist;
    private Collection<?> sessionList;
    private PaginatedList paginatedList;
    private List<ColumnModel> filterSearchObject;
    private List<ColumnModel> columnInfo;
    private Map<String, String> fieldDisplay;
    private String[] selectedFields;
    private Long createUserRefId;
    private Timestamp createTime;
    private Long updateUserRefId;
    private Timestamp updateTime;
    private String pageSize;
    private Integer offset;
    private boolean restrict = true;
    private boolean administrator;
    private boolean noRecords;
    private String listType;
    private String subType;
    private String goButtonDisable;

    /**
     * @return the appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName
     *        the appName to set
     */
    public void setAppName(final String appName) {
        this.appName = appName;
    }

    /**
     * @return the datalist
     */
    public Collection<?> getDatalist() {
        return datalist;
    }

    /**
     * @param c
     *        the c to set
     */
    public void setDatalist(final Collection<?> c) {
        datalist = c;
    }

    /**
     * @return the sessionList
     */
    public Collection<?> getSessionList() {
        return sessionList;
    }

    /**
     * @param sessionList
     *        the sessionList to set
     */
    public void setSessionList(final Collection<?> sessionList) {
        this.sessionList = sessionList;
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
     * @return the filterSearchObject
     */
    public List<ColumnModel> getFilterSearchObject() {
        return filterSearchObject;
    }

    /**
     * @param filterSearchObject
     *        the filterSearchObject to set
     */
    public void setFilterSearchObject(final List<ColumnModel> filterSearchObject) {
        this.filterSearchObject = filterSearchObject;
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
     * @return the fieldDisplay
     */
    public Map<String, String> getFieldDisplay() {
        return fieldDisplay;
    }

    /**
     * @param fieldDisplay
     *        the fieldDisplay to set
     */
    public void setFieldDisplay(final Map<String, String> fieldDisplay) {
        this.fieldDisplay = fieldDisplay;
    }

    /**
     * @return the selectedFields
     */
    public String[] getSelectedFields() {
        return selectedFields;
    }

    /**
     * @param selectedFields
     *        the selectedFields to set
     */
    public void setSelectedFields(final String[] selectedFields) {
        this.selectedFields = selectedFields;
    }

    /**
     * @return the createUserRefId
     */
    public Long getCreateUserRefId() {
        return createUserRefId;
    }

    /**
     * @param createUserRefId
     *        the createUserRefId to set
     */
    public void setCreateUserRefId(final Long createUserRefId) {
        this.createUserRefId = createUserRefId;
    }

    /**
     * @return the createTime
     */
    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *        the createTime to set
     */
    public void setCreateTime(final Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the updateUserRefId
     */
    public Long getUpdateUserRefId() {
        return updateUserRefId;
    }

    /**
     * @param updateUserRefId
     *        the updateUserRefId to set
     */
    public void setUpdateUserRefId(final Long updateUserRefId) {
        this.updateUserRefId = updateUserRefId;
    }

    /**
     * @return the updateTime
     */
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     *        the updateTime to set
     */
    public void setUpdateTime(final Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the pageSize
     */
    public String getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *        the pageSize to set
     */
    public void setPageSize(final String pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @param i
     *        int
     */
    public void setOffset(final int i) {
        offset = i;
    }

    /**
     * @return the restrict
     */
    public boolean isRestrict() {
        return restrict;
    }

    /**
     * @param restrict
     *        the restrict to set
     */
    public void setRestrict(final boolean restrict) {
        this.restrict = restrict;
    }

    /**
     * @return the administrator
     */
    public boolean isAdministrator() {
        return administrator;
    }

    /**
     * @param administrator
     *        the administrator to set
     */
    public void setAdministrator(final boolean administrator) {
        this.administrator = administrator;
    }

    /**
     * @return the noRecords
     */
    public boolean isNoRecords() {
        return noRecords;
    }

    /**
     * @param noRecords
     *        the noRecords to set
     */
    public void setNoRecords(final boolean noRecords) {
        this.noRecords = noRecords;
    }

    /**
     * @return the listType
     */
    public String getListType() {
        return listType;
    }

    /**
     * @param listType
     *        the listType to set
     */
    public void setListType(String listType) {
        this.listType = listType;
    }

    /**
     * @return the subType
     */
    public String getSubType() {
        return subType;
    }

    /**
     * @param subType
     *        the subType to set
     */
    public void setSubType(String subType) {
        this.subType = subType;
    }

    /**
     * @return the goButtonDisable
     */
    public String getGoButtonDisable() {
        return goButtonDisable;
    }

    /**
     * @param goButtonDisable
     *        the goButtonDisable to set
     */
    public void setGoButtonDisable(String goButtonDisable) {
        this.goButtonDisable = goButtonDisable;
    }

    /** Dereference any interal resources */
    public void semiFlush() {
        offset = 0;
        datalist = null;
        sessionList = null;
        paginatedList = null;
        filterSearchObject = null;
        createUserRefId = null;
        createTime = null;
        updateUserRefId = null;
        updateTime = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        return super.validate(mapping, request);
    }

}
