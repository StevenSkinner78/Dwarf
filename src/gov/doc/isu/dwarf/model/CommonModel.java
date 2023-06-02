package gov.doc.isu.dwarf.model;

import gov.doc.isu.dwarf.resources.Constants;
import gov.doc.isu.dwarf.util.AppUtil;
import gov.doc.isu.dwarf.util.StringUtil;

import java.sql.Timestamp;
import java.util.List;

/**
 * Purpose: CommonModel used to separate the commonly used member variables.
 * 
 * @author Steven L. Skinner
 */
public class CommonModel implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Long createUserRefId;
    private Timestamp createTime;
    private Long updateUserRefId;
    private Timestamp updateTime;
    private String deleteIndicator;
    private List<ColumnModel> columnData; // This is for display in dataTable
    // Used for session attributes in export
    private String offenderFullName;
    private Long offenderNum;

    /**
     * Default Constructor
     */
    public CommonModel() {

    }

    /**
     * constructor with create and update parameters
     * 
     * @param createUserRefId
     *        createUserRefId
     * @param createTime
     *        createTime
     * @param updateUserRefId
     *        updateUserRefId
     * @param updateTime
     *        updateTime
     */
    public CommonModel(final Long createUserRefId, final Timestamp createTime, final Long updateUserRefId, final Timestamp updateTime) {
        this.setCreateUserRefId(createUserRefId);
        this.setCreateTime(createTime);
        this.setUpdateUserRefId(updateUserRefId);
        this.setUpdateTime(updateTime);
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
     * @return the columnData
     */
    public List<ColumnModel> getColumnData() {
        return columnData;
    }

    /**
     * @param columnData
     *        the columnData to set
     */
    public void setColumnData(final List<ColumnModel> columnData) {
        this.columnData = columnData;
    }

    /**
     * @return the offenderFullName
     */
    public String getOffenderFullName() {
        return offenderFullName;
    }

    /**
     * @param offenderFullName
     *        the offenderFullName to set
     */
    public void setOffenderFullName(final String offenderFullName) {
        this.offenderFullName = offenderFullName;
    }

    /**
     * @return the offenderNum
     */
    public Long getOffenderNum() {
        return offenderNum;
    }

    /**
     * @param offenderNum
     *        the offenderNum to set
     */
    public void setOffenderNum(final Long offenderNum) {
        this.offenderNum = offenderNum;
    }

    public String getDeleteIndicator() {
        return deleteIndicator;
    }

    public void setDeleteIndicator(final String deleteIndicator) {
        this.deleteIndicator = deleteIndicator;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(" columnData=");
        sb.append(AppUtil.isEmpty(columnData) ? "empty" : StringUtil.collapseList(columnData));
        sb.append(Constants.NEW_LINE);
        sb.append("   CommonModel [");
        sb.append("Create User Ref Id=").append(String.valueOf(createUserRefId)).append(Constants.NEW_LINE);
        sb.append(", Create Time=").append(String.valueOf(createTime)).append(Constants.NEW_LINE);
        sb.append(", Update User Ref Id=").append(String.valueOf(updateUserRefId)).append(Constants.NEW_LINE);
        sb.append(", Update Time").append(String.valueOf(updateTime)).append(Constants.NEW_LINE);
        sb.append(", Delete Indicator").append(String.valueOf(deleteIndicator));
        sb.append("]End CommonModel").append(Constants.NEW_LINE);
        return sb.toString();
    }
}
