/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.render;

import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.DOUBLE_QUOTE;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.IMG_END;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.IMG_OPEN;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.SRC;
import static gov.doc.isu.dwarf.taglib.displaytag.util.DwarfTagConstants.TITLE;
import static gov.doc.isu.dwarf.taglib.displaytag.util.TagConstants.TAG_DIV_CLOSE;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import gov.doc.isu.dwarf.taglib.displaytag.exception.DecoratorException;
import gov.doc.isu.dwarf.taglib.displaytag.exception.ObjectLookupException;
import gov.doc.isu.dwarf.taglib.displaytag.exception.WrappedRuntimeException;
import gov.doc.isu.dwarf.taglib.displaytag.model.Column;
import gov.doc.isu.dwarf.taglib.displaytag.model.FilterSearchRow;
import gov.doc.isu.dwarf.taglib.displaytag.model.HeaderCell;
import gov.doc.isu.dwarf.taglib.displaytag.model.PageSize;
import gov.doc.isu.dwarf.taglib.displaytag.model.Row;
import gov.doc.isu.dwarf.taglib.displaytag.model.SubRow;
import gov.doc.isu.dwarf.taglib.displaytag.model.TableModel;
import gov.doc.isu.dwarf.taglib.displaytag.pagination.PaginatedList;
import gov.doc.isu.dwarf.taglib.displaytag.pagination.SmartListHelper;
import gov.doc.isu.dwarf.taglib.displaytag.properties.MediaTypeEnum;
import gov.doc.isu.dwarf.taglib.displaytag.properties.SortOrderEnum;
import gov.doc.isu.dwarf.taglib.displaytag.properties.TableProperties;
import gov.doc.isu.dwarf.taglib.displaytag.tags.CaptionTag;
import gov.doc.isu.dwarf.taglib.displaytag.tags.TableTagParameters;
import gov.doc.isu.dwarf.taglib.displaytag.util.Anchor;
import gov.doc.isu.dwarf.taglib.displaytag.util.Href;
import gov.doc.isu.dwarf.taglib.displaytag.util.HtmlAttributeMap;
import gov.doc.isu.dwarf.taglib.displaytag.util.ParamEncoder;
import gov.doc.isu.dwarf.taglib.displaytag.util.PostHref;
import gov.doc.isu.dwarf.taglib.displaytag.util.TagConstants;
import gov.doc.isu.dwarf.util.DwarfHref;
import gov.doc.isu.dwarf.util.DwarfJavascriptHref;

/**
 * A table writer that formats a table in HTML and writes it to a JSP page.
 * 
 * @author Fabrizio Giustina
 * @author Jorge L. Barroso
 * @author Steven Skinner JCCC
 * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate
 * @since 1.1
 */
public class HtmlTableWriter extends TableWriterAdapter {

    /**
     * Logger.
     */
    private static Logger log = Logger.getLogger(HtmlTableWriter.class);
    /**
     * <code>TableModel</code>
     */
    private TableModel tableModel;

    /**
     * <code>TableProperties</code>
     */
    private TableProperties properties;

    /**
     * Output destination.
     */
    private JspWriter out;

    /**
     * The param encoder used to generate unique parameter names. Initialized at the first use of encodeParameter().
     */
    private ParamEncoder paramEncoder;

    /**
     * base href used for links.
     */
    private Href baseHref;

    /**
     * add export links.
     */
    private boolean export;

    private CaptionTag captionTag;

    /**
     * The paginated list containing the external pagination and sort parameters The presence of this paginated list is what determines if external pagination and sorting is used or not.
     */
    private PaginatedList paginatedList;

    /**
     * Used by various functions when the person wants to do paging.
     */
    private SmartListHelper listHelper;

    /**
     * page size.
     */
    private int pagesize;

    private HtmlAttributeMap attributeMap;

    /**
     * Unique table id.
     */
    private String uid;

    /**
     * This table writer uses a <code>TableModel</code> and a <code>JspWriter</code> to do its work.
     * 
     * @param tableModel
     * @param tableProperties
     * @param baseHref
     * @param export
     * @param out
     * @param captionTag
     * @param paginatedList
     * @param listHelper
     * @param pagesize
     * @param attributeMap
     * @param uid
     */
    public HtmlTableWriter(TableModel tableModel, TableProperties tableProperties, Href baseHref, boolean export, JspWriter out, CaptionTag captionTag, PaginatedList paginatedList, SmartListHelper listHelper, int pagesize, HtmlAttributeMap attributeMap, String uid) {
        this.tableModel = tableModel;
        this.properties = tableProperties;
        this.baseHref = baseHref;
        this.export = export;
        this.out = out;
        this.captionTag = captionTag;
        this.paginatedList = paginatedList;
        this.listHelper = listHelper;
        this.pagesize = pagesize;
        this.attributeMap = attributeMap;
        this.uid = uid;
    }// end constructor

    /**
     * Writes a banner containing search result and paging navigation above an HTML table to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeTopBanner(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeTopBanner(TableModel model) {
        if(this.tableModel.getForm() != null){

            String js = "<script type=\"text/javascript\">\n" + "function displaytagform(formname, fields){\n" + "    var objfrm = document.forms[formname];\n" + "    for (j=fields.length-1;j>=0;j--){var f= objfrm.elements[fields[j].f];if (f){f.value=fields[j].v};}\n" + "    objfrm.submit();\n" + "}\n" + "</script>";
            writeFormFields();
            write(js);
        }// end if
        if(model.getProperties().getShowPageSizeDropDown()){
            write(this.properties.getSearchRowWrapper());
        }// end if
        if((this.paginatedList == null && this.pagesize != 0 && this.listHelper != null) || (this.paginatedList != null)){
            if(!model.getProperties().getShowPageSizeDropDown()){
                write(this.properties.getSearchRowWrapper());
            }// end if
            write(this.listHelper.getSearchResultsSummary());
            if(!model.getProperties().getShowPageSizeDropDown()){
                write(TAG_DIV_CLOSE);
            }// end if
        }// end if
        if(this.paginatedList == null && this.pagesize == 0 && this.listHelper == null){
            Object[] objs;
            String message;
            int size = model.getRowListFull() != null ? model.getRowListFull().size() : 0;
            objs = new Object[]{new Integer(size), this.properties.getPagingItemsName(), this.properties.getPagingItemsName()};
            message = this.properties.getPagingFoundAllItems();
            write(MessageFormat.format(message, objs));
        }// end if
        if(model.getProperties().getShowPageSizeDropDown()){
            write(this.properties.getPageSizeWrapper());
            PageSize tag = new PageSize(this.properties.getPageSizeFormName(), this.properties.getPageSizeAction(), this.properties.getPageSizeType(), this.properties.getPageSizeMethod(), this.properties.getPageSizeStyle(), this.properties.getPageSizeLabelOne(), this.properties.getPageSizeLabelOneStyle(), this.properties.getPageSizeLabelTwo(), this.properties.getPageSizeLabelTwoStyle(), out, model);
            tag.setUseAll(Boolean.valueOf(this.properties.getPageSizeUseAll()));
            tag.doStartTag();
            write(TAG_DIV_CLOSE + TAG_DIV_CLOSE);
        }// end if
        if(model.getProperties().getShowPageSizeDropDown()){
            write(TAG_DIV_CLOSE);
        }// end if

    }// end writeTopBanner

    /**
     * Writes the Expand All|Collapse All banner over table
     */
    protected void writeExpandCollapseAllBanner(TableModel model) {
        if(model.getToggleAll() != null){
            this.write(MessageFormat.format(this.properties.getExpandCollapseBanner(), new Object[]{"'" + model.getId() + "'"}));// Messag"<div class='right bold'><a href='#' class='enableWithRestrict' onclick='expandAll(\"" + model.getId() + "\")'>Expand All</a> |<a href='#' class='enableWithRestrict' onclick='collapseAll(\"" + model.getId() + "\")'> Collapse All</a></div>");
        }// end if
    }// end writeExpandCollapseAllBanner

    /**
     * Writes an HTML table's opening tags to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeTableOpener(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeTableOpener(TableModel model) {
        this.write(getOpenTag());
    }// end writeTableOpener

    private void writeFormFields() {
        Map parameters = baseHref.getParameterMap();

        ParamEncoder pe = new ParamEncoder(this.tableModel.getId());

        addIfMissing(parameters, pe.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
        addIfMissing(parameters, pe.encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        addIfMissing(parameters, pe.encodeParameterName(TableTagParameters.PARAMETER_SORT));

        for(Iterator it = parameters.keySet().iterator();it.hasNext();){
            String key = (String) it.next();
            Object value = parameters.get(key);

            if(value != null & value.getClass().isArray()){
                Object[] arr = (Object[]) value;
                for(int j = 0;j < arr.length;j++){
                    writeField(key, arr[j]);
                }// end for
            }else{
                writeField(key, value);
            }// end if/else
        }// end for
    }// end writeFormFields

    /**
     * @param key
     * @param value
     */
    private void writeField(String key, Object value) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<input type=\"hidden\" name=\"");
        buffer.append(esc(key));
        buffer.append("\" value=\"");
        buffer.append(value);
        buffer.append("\"/>");

        write(buffer.toString());
    }// end writeField

    private String esc(Object value) {
        String valueEscaped = StringUtils.replace(ObjectUtils.toString(value), "\"", "\\\"");
        return valueEscaped;
    }// end esc

    /**
     * Adds an element to the given map if empty (use an empty string as value)
     * 
     * @param parameters
     *        Map of parameters
     * @param key
     *        param key
     */
    private void addIfMissing(Map parameters, String key) {
        if(!parameters.containsKey(key)){
            parameters.put(key, StringUtils.EMPTY);
        }// end if
    }// end addIfMissing

    /**
     * Writes an HTML table's caption to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeCaption(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeCaption(TableModel model) {
        this.write(captionTag.getOpenTag() + model.getCaption() + captionTag.getCloseTag());
    }// end writeCaption

    /**
     * Writes an HTML table's footer to a JSP page; HTML requires tfoot to appear before tbody.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writePreBodyFooter(TableModel)
     */
    protected void writePreBodyFooter(TableModel model) {
        this.write(TagConstants.TAG_TFOOTER_OPEN);
        this.write(model.getFooter());
        this.write(TagConstants.TAG_TFOOTER_CLOSE);
    }// end writePreBodyFooter

    /**
     * Writes the start of an HTML table's body to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeTableBodyOpener(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeTableBodyOpener(TableModel model) {
        this.write(TagConstants.TAG_TBODY_OPEN);

    }// end writeTableBodyOpener

    /**
     * Writes the end of an HTML table's body to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeTableBodyCloser(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeTableBodyCloser(TableModel model) {
        this.write(TagConstants.TAG_TBODY_CLOSE);
    }// end writeTableBodyCloser

    /**
     * Writes the closing structure of an HTML table to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeTableCloser(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeTableCloser(TableModel model) {
        this.write(TagConstants.TAG_OPENCLOSING);
        this.write(TagConstants.TABLE_TAG_NAME);
        this.write(TagConstants.TAG_CLOSE);
    }// end writeTableCloser

    /**
     * Writes a banner containing search result, paging navigation, and export links below an HTML table to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeBottomBanner(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeBottomBanner(TableModel model) {
        writeNavigationAndExportLinks();
    }// end writeBottomBanner

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeDecoratedTableFinish(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeDecoratedTableFinish(TableModel model) {
        model.getTableDecorator().finish();
    }// end writeDecoratedTableFinish

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeDecoratedRowStart(TableModel model) {
        this.write(model.getTableDecorator().startRow());
    }// end writeDecoratedRowStart

    /**
     * Writes an HTML table's row-opening tag to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeRowOpener(gov.doc.isu.dwarf.taglib.displaytag.model.Row)
     */
    protected void writeRowOpener(Row row) {
        this.write(row.getOpenTag());
    }// end writeRowOpener

    /**
     * Writes an HTML table's column-opening tag to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeColumnOpener(gov.doc.isu.dwarf.taglib.displaytag.model.Column)
     */
    protected void writeColumnOpener(Column column) throws ObjectLookupException, DecoratorException {
        this.write(column.getOpenTag());
    }// end writeColumnOpener

    /**
     * Writes an HTML table's column-closing tag to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeColumnCloser(gov.doc.isu.dwarf.taglib.displaytag.model.Column)
     */
    protected void writeColumnCloser(Column column) {
        this.write(column.getCloseTag());
    }// end writeColumnCloser

    /******* Enhancement :: Search Row :: Start *******/
    /**
     * Writes an HTML table's row-opening tag to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeSearchRowOpener(FilterSearchRow)
     */
    protected void writeSearchRowOpener(FilterSearchRow row) {
        this.write(row.getOpenTag());
    }// end writeSearchRowOpener

    /**
     * Writes to a JSP page an HTML table row that has no columns.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeSearchRowWithNoColumns(String)
     */
    protected void writeSearchRowWithNoColumns(String rowValue) {
        this.write(rowValue);
    }// end writeSearchRowWithNoColumns

    /**
     * Writes an HTML table's row-closing tag to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeSearchRowCloser(FilterSearchRow)
     */
    protected void writeSearchRowCloser(FilterSearchRow row) {
        this.write(row.getCloseTag());
    }// end writeSearchRowCloser

    /******* Enhancement :: Search Row :: End *******/

    /******* Enhancement :: Sub Row :: Start *******/
    /**
     * Writes an HTML table's row-opening tag to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeSubRowOpener(gov.doc.isu.dwarf.taglib.displaytag.model.SubRow)
     */
    protected void writeSubRowOpener(SubRow row) {
        this.write(row.getOpenTag());
    }// end writeSubRowOpener

    /**
     * Writes to a JSP page an HTML table row that has no columns.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeSubRowWithNoColumns(java.lang.String)
     */
    protected void writeSubRowWithNoColumns(String rowValue) {
        this.write(rowValue);
    }// end writeSubRowWithNoColumns

    /**
     * Writes an HTML table's row-closing tag to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeSubRowCloser(gov.doc.isu.dwarf.taglib.displaytag.model.SubRow)
     */
    protected void writeSubRowCloser(SubRow row) {
        this.write(row.getCloseTag());
    }// end writeSubRowCloser

    /******* Enhancement :: Sub Row :: End *******/

    /**
     * Writes to a JSP page an HTML table row that has no columns.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeRowWithNoColumns(java.lang.String)
     */
    protected void writeRowWithNoColumns(String rowValue) {
        this.write(TagConstants.TAG_TD_OPEN);
        this.write(rowValue);
        this.write(TagConstants.TAG_TD_CLOSE);
    }// end writeRowWithNoColumns

    /**
     * Writes an HTML table's row-closing tag to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeRowCloser(gov.doc.isu.dwarf.taglib.displaytag.model.Row)
     */
    protected void writeRowCloser(Row row) {
        this.write(row.getCloseTag());
    }// end writeRowCloser

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeDecoratedRowFinish(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeDecoratedRowFinish(TableModel model) {
        this.write(model.getTableDecorator().finishRow());
    }// end writeDecoratedRowFinish

    /**
     * Writes an HTML message to a JSP page explaining that the table model contains no data.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeEmptyListMessage(java.lang.String)
     */
    protected void writeEmptyListMessage(String emptyListMessage) {
        this.write(emptyListMessage);
    }// end writeEmptyListMessage

    /**
     * Writes a HTML table column value to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeColumnValue(Object, Column)
     */
    protected void writeColumnValue(Object value, Column column) {
        this.write(value);
    }// end writeColumnValue

    /**
     * Writes an HTML message to a JSP page explaining that the row contains no data.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeEmptyListRowMessage(java.lang.String)
     */
    protected void writeEmptyListRowMessage(String message) {
        this.write(message);
    }// end writeEmptyListRowMessage

    /**
     * Writes an HTML table's column header to a JSP page.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeTableHeader(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeTableHeader(TableModel model) {
        if(log.isDebugEnabled()){
            log.debug("[" + tableModel.getId() + "] getTableHeader called");
        }// end if

        // open thead
        write(TagConstants.TAG_THEAD_OPEN);

        // open tr
        write(TagConstants.TAG_TR_OPEN);

        // no columns?
        if(this.tableModel.isEmpty()){
            write(TagConstants.TAG_TH_OPEN);
            write(TagConstants.TAG_TH_CLOSE);
        }// end if

        // iterator on columns for header
        Iterator iterator = this.tableModel.getHeaderCellList().iterator();

        while(iterator.hasNext()){
            // get the header cell
            HeaderCell headerCell = (HeaderCell) iterator.next();

            if(headerCell.getSortable()){
                String cssSortable = this.properties.getCssSortable();
                headerCell.addHeaderClass(cssSortable);
            }// end if

            // if sorted add styles
            if(headerCell.isAlreadySorted()){
                // sorted css class
                headerCell.addHeaderClass(this.properties.getCssSorted());

                // sort order css class
                headerCell.addHeaderClass(this.properties.getCssOrder(this.tableModel.isSortOrderAscending()));
            }// end if

            // append th with html attributes
            write(headerCell.getHeaderOpenTag());

            // title
            String header = headerCell.getTitle();

            // column is sortable, create link
            if(headerCell.getSortable()){
                StringBuffer spacedTitle = new StringBuffer();
                spacedTitle.append(header);
                // if sorted add styles
                if(headerCell.isAlreadySorted()){
                    if(this.tableModel.isSortOrderAscending()){
                        spacedTitle.append(IMG_OPEN).append(SRC).append(model.getContextPath()).append(this.properties.getPaginationSortAscImagePath()).append(DOUBLE_QUOTE).append(TITLE).append("Sorted Ascending").append(DOUBLE_QUOTE).append(IMG_END);
                    }else{
                        spacedTitle.append(IMG_OPEN).append(SRC).append(model.getContextPath()).append(this.properties.getPaginationSortDescImagePath()).append(DOUBLE_QUOTE).append(TITLE).append("Sorted Decending").append(DOUBLE_QUOTE).append(IMG_END);
                    }
                }else{
                    spacedTitle.append(IMG_OPEN).append(SRC).append(model.getContextPath()).append(this.properties.getPaginationSortImagePath()).append(DOUBLE_QUOTE).append(TITLE).append("Sortable").append(DOUBLE_QUOTE).append(IMG_END);
                }
                Href href = getSortingHref(headerCell);
                if(href.toString().contains("method=updateListPageData")){
                    href = new DwarfHref(href.toString());
                }// end if
                if(href.toString().contains("jsmethod=buildPathWithParams")){
                    href = new DwarfJavascriptHref(href.toString(), this.properties.getAddParams());
                }// end if
                 // creates the link for sorting
                Anchor anchor = new Anchor(href, spacedTitle.toString());
                // this added so that sort is not disabled when user doesn't have edit capabilities. SLS00#IS
                anchor.setClass("enableWithRestrict");
                // append to buffer
                header = anchor.toString();
            }// end if

            write(header);
            write(headerCell.getHeaderCloseTag());
        }// end while

        // close tr
        write(TagConstants.TAG_TR_CLOSE);

        // close thead
        write(TagConstants.TAG_THEAD_CLOSE);

        if(log.isDebugEnabled()){
            log.debug("[" + tableModel.getId() + "] getTableHeader end");
        }// end if
    }// end writeTableHeader

    /**
     * Generates the link to be added to a column header for sorting.
     * 
     * @param headerCell
     *        header cell the link should be added to
     * @return Href for sorting
     */
    private Href getSortingHref(HeaderCell headerCell) {
        // costruct Href from base href, preserving parameters
        Href href = (Href) this.baseHref.clone();

        if(this.tableModel.getForm() != null){
            href = new PostHref(href, tableModel.getForm());
        }// end if

        if(this.paginatedList == null){
            // add column number as link parameter
            if(!this.tableModel.isLocalSort() && (headerCell.getSortName() != null)){
                href.addParameter(encodeParameter(TableTagParameters.PARAMETER_SORT), headerCell.getSortName());
                href.addParameter(encodeParameter(TableTagParameters.PARAMETER_SORTUSINGNAME), "1");
            }else{
                href.addParameter(encodeParameter(TableTagParameters.PARAMETER_SORT), headerCell.getColumnNumber());
            }// end if/else

            boolean nowOrderAscending = true;

            if(headerCell.getDefaultSortOrder() != null){
                boolean sortAscending = SortOrderEnum.ASCENDING.equals(headerCell.getDefaultSortOrder());
                nowOrderAscending = headerCell.isAlreadySorted() ? !this.tableModel.isSortOrderAscending() : sortAscending;
            }else{
                nowOrderAscending = !(headerCell.isAlreadySorted() && this.tableModel.isSortOrderAscending());
            }// end if/else

            int sortOrderParam = nowOrderAscending ? SortOrderEnum.ASCENDING.getCode() : SortOrderEnum.DESCENDING.getCode();
            href.addParameter(encodeParameter(TableTagParameters.PARAMETER_ORDER), sortOrderParam);

            // If user want to sort the full table I need to reset the page number.
            // or if we aren't sorting locally we need to reset the page as well.
            if(this.tableModel.isSortFullTable() || !this.tableModel.isLocalSort()){
                href.addParameter(encodeParameter(TableTagParameters.PARAMETER_PAGE), 1);
            }// end if
        }else{
            if(properties.getPaginationSkipPageNumberInSort()){
                href.removeParameter(properties.getPaginationPageNumberParam());
            }// end if

            String sortProperty = headerCell.getSortProperty();
            if(sortProperty == null){
                sortProperty = headerCell.getBeanPropertyName();
            }// end if

            href.addParameter(properties.getPaginationSortParam(), sortProperty);
            String dirParam;
            if(headerCell.isAlreadySorted()){
                dirParam = tableModel.isSortOrderAscending() ? properties.getPaginationDescValue() : properties.getPaginationAscValue();
            }else{
                dirParam = properties.getPaginationAscValue();
            }// end if/else
            href.addParameter(properties.getPaginationSortDirectionParam(), dirParam);
            if(paginatedList.getSearchId() != null){
                href.addParameter(properties.getPaginationSearchIdParam(), paginatedList.getSearchId());
            }// end if
        }// end if/else

        return href;
    }// end getSortingHref

    /**
     * encode a parameter name to be unique in the page using ParamEncoder.
     * 
     * @param parameterName
     *        parameter name to encode
     * @return String encoded parameter name
     */
    private String encodeParameter(String parameterName) {
        // paramEncoder has been already instantiated?
        if(this.paramEncoder == null){
            // use the id attribute to get the unique identifier
            this.paramEncoder = new ParamEncoder(this.tableModel.getId());
        }// end if

        return this.paramEncoder.encodeParameterName(parameterName);
    }// end encodeParameter

    /**
     * Generates table footer with links for export commands.
     */
    public void writeNavigationAndExportLinks() {
        // Put the page stuff there if it needs to be there...
        if(this.properties.getAddPagingBannerBottom()){
            writeSearchResultAndNavigation();
        }// end if

        // add export links (only if the table is not empty)
        if(this.export && this.tableModel.getRowListPage().size() != 0){
            writeExportLinks();
        }// end if
    }// end writeNavigationAndExportLinks

    /**
     * generates the search result and navigation bar.
     */
    public void writeSearchResultAndNavigation() {
        if((this.paginatedList == null && this.pagesize != 0 && this.listHelper != null) || (this.paginatedList != null)){
            // create a new href
            Href navigationHref = (Href) this.baseHref.clone();

            if(tableModel.getForm() != null){
                navigationHref = new PostHref(navigationHref, tableModel.getForm());
            }// end if

            // write(this.listHelper.getSearchResultsSummary());

            String pageParameter;
            if(paginatedList == null){
                pageParameter = encodeParameter(TableTagParameters.PARAMETER_PAGE);
            }else{
                pageParameter = properties.getPaginationPageNumberParam();
                if((paginatedList.getSearchId() != null) && (!navigationHref.getParameterMap().containsKey(properties.getPaginationSearchIdParam()))){
                    navigationHref.addParameter(properties.getPaginationSearchIdParam(), paginatedList.getSearchId());
                }// end if
            }// end if/else
            write(this.listHelper.getPageNavigationBar(navigationHref, pageParameter));
        }// end if
    }// end writeSearchResultAndNavigation

    /**
     * Writes the formatted export links section.
     */
    private void writeExportLinks() {
        // Figure out what formats they want to export, make up a little string
        Href exportHref = (Href) this.baseHref.clone();

        StringBuffer buffer = new StringBuffer(200);
        Iterator iterator = MediaTypeEnum.iterator();

        while(iterator.hasNext()){
            MediaTypeEnum currentExportType = (MediaTypeEnum) iterator.next();

            if(this.properties.getAddExport(currentExportType)){

                if(buffer.length() > 0){
                    buffer.append(this.properties.getExportBannerSeparator());
                }// end if

                exportHref.addParameter(encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE), currentExportType.getCode());

                // export marker
                exportHref.addParameter(TableTagParameters.PARAMETER_EXPORTING, "1");

                Anchor anchor = new Anchor(exportHref, this.properties.getExportLabel(currentExportType));
                buffer.append(anchor.toString());
            }// end if
        }// end while

        String[] exportOptions = {buffer.toString()};
        write(MessageFormat.format(this.properties.getExportBanner(), exportOptions));
    }// end writeExportLinks

    /**
     * create the open tag containing all the attributes.
     * 
     * @return open tag string: <code>%lt;table attribute="value" ... ></code>
     */
    public String getOpenTag() {

        if(this.uid != null && attributeMap.get(TagConstants.ATTRIBUTE_ID) == null){
            // we need to clone the attribute map in order to "fix" the html id when using only the "uid" attribute
            Map localAttributeMap = (Map) attributeMap.clone();
            localAttributeMap.put(TagConstants.ATTRIBUTE_ID, this.uid);

            StringBuffer buffer = new StringBuffer();
            buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TABLE_TAG_NAME);
            buffer.append(localAttributeMap);
            buffer.append(TagConstants.TAG_CLOSE);

            return buffer.toString();

        }// end if

        // fast, no clone
        StringBuffer buffer = new StringBuffer();

        buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TABLE_TAG_NAME);
        buffer.append(attributeMap);
        buffer.append(TagConstants.TAG_CLOSE);

        return buffer.toString();
    }// end getOpenTag

    /**
     * Utility method.
     * 
     * @param string
     *        String
     */
    public void write(String string) {
        if(string != null){
            try{
                out.write(string);
            }catch(IOException e){
                throw new WrappedRuntimeException(getClass(), e);
            }// end try/catch
        }// end if

    }// end write

    public void writeTable(TableModel model, String id) throws JspException {
        super.writeTable(model, id);
    }// end writeTable

    /**
     * Utility method.
     * 
     * @param string
     *        String
     */
    public void write(Object string) {
        if(string != null){
            try{
                out.write(string.toString());
            }catch(IOException e){
                throw new WrappedRuntimeException(getClass(), e);
            }// end try/catch
        }// end if

    }// end write

}// end class
