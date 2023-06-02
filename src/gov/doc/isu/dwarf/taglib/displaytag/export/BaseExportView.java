/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.export;

import gov.doc.isu.dwarf.resources.Constants;
import gov.doc.isu.dwarf.taglib.displaytag.model.Column;
import gov.doc.isu.dwarf.taglib.displaytag.model.ColumnIterator;
import gov.doc.isu.dwarf.taglib.displaytag.model.HeaderCell;
import gov.doc.isu.dwarf.taglib.displaytag.model.Row;
import gov.doc.isu.dwarf.taglib.displaytag.model.RowIterator;
import gov.doc.isu.dwarf.taglib.displaytag.model.TableModel;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * <p>
 * Base abstract class for simple export views.
 * </p>
 * <p>
 * A class that extends BaseExportView simply need to provide delimiters for rows and columns.
 * </p>
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public abstract class BaseExportView implements TextExportView {

    /**
     * logger.
     */
    private static Logger log = Logger.getLogger(BaseExportView.class);

    /**
     * TableModel to render.
     */
    private TableModel model;

    /**
     * export full list?
     */
    private boolean exportFull;

    /**
     * include header in export?
     */
    private boolean header;

    /**
     * decorate export?
     */
    private boolean decorated;

    /**
     * {@inheritDoc}
     */
    public void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader, boolean decorateValues) {
        this.model = tableModel;
        this.exportFull = exportFullList;
        this.header = includeHeader;
        this.decorated = decorateValues;
    }

    /**
     * String to add before a row.
     * 
     * @return String
     */
    protected String getRowStart() {
        return null;
    }

    /**
     * String to add after a row.
     * 
     * @return String
     */
    protected String getRowEnd() {
        return null;
    }

    /**
     * String to add before a cell.
     * 
     * @return String
     */
    protected String getCellStart() {
        return null;
    }

    /**
     * String to add after a cell.
     * 
     * @return String
     */
    protected abstract String getCellEnd();

    /**
     * String to add to the top of document.
     * 
     * @return String
     */
    protected String getDocumentStart() {
        return null;
    }

    /**
     * String to add to the end of document.
     * 
     * @return String
     */
    protected String getDocumentEnd() {
        return null;
    }

    /**
     * always append cell end string?
     * 
     * @return boolean
     */
    protected abstract boolean getAlwaysAppendCellEnd();

    /**
     * always append row end string?
     * 
     * @return boolean
     */
    protected abstract boolean getAlwaysAppendRowEnd();

    /**
     * can be implemented to escape values for different output.
     * 
     * @param value
     *        original column value
     * @return escaped column value
     */
    protected abstract String escapeColumnValue(Object value);

    /**
     * Write table header.
     * 
     * @return String rendered header
     */
    @SuppressWarnings("rawtypes")
    protected String doHeaders() {

        String rowStart = getRowStart();
        String rowEnd = getRowEnd();
        String cellStart = getCellStart();
        String cellEnd = getCellEnd();
        boolean alwaysAppendCellEnd = getAlwaysAppendCellEnd();

        StringBuffer buffer = new StringBuffer(1000);

        Iterator iterator = this.model.getHeaderCellList().iterator();

        // start row
        if(rowStart != null){
            buffer.append(rowStart);
        }

        while(iterator.hasNext()){
            HeaderCell headerCell = (HeaderCell) iterator.next();

            String columnHeader = headerCell.getTitle();

            if(columnHeader == null){
                columnHeader = StringUtils.capitalize(headerCell.getBeanPropertyName());
            }

            columnHeader = escapeColumnValue(columnHeader);

            if(cellStart != null){
                buffer.append(cellStart);
            }

            if(columnHeader != null){
                buffer.append(columnHeader);
            }

            if(cellEnd != null && (alwaysAppendCellEnd || iterator.hasNext())){
                buffer.append(cellEnd);
            }
        }

        // end row
        if(rowEnd != null){
            buffer.append(rowEnd);
        }

        return buffer.toString();

    }

    /**
     * {@inheritDoc}
     */
    public void doExport(Writer out) throws IOException, JspException {
        if(log.isDebugEnabled()){
            log.debug(getClass().getName());
        }

        String documentStart = getDocumentStart();
        String documentEnd = getDocumentEnd();
        String rowStart = getRowStart();
        String rowEnd = getRowEnd();
        String cellStart = getCellStart();
        String cellEnd = getCellEnd();
        boolean alwaysAppendCellEnd = getAlwaysAppendCellEnd();
        boolean alwaysAppendRowEnd = getAlwaysAppendRowEnd();

        // document start
        write(out, documentStart);

        // write offender name & docId
        if(this.model.getOffenderInfo() != null){
            write(out, doOffenderInfo("Offender Name: " + this.model.getOffenderInfo().getOffenderFullName()));
            write(out, doOffenderInfo("DOC ID: " + String.valueOf(this.model.getOffenderInfo().getOffenderNum())));
            // write empty row
            write(out, doOffenderInfo(Constants.EMPTY_STRING));
        }

        if(this.header){
            write(out, doHeaders());
        }

        // get the correct iterator (full or partial list according to the exportFull field)
        RowIterator rowIterator = this.model.getRowIterator(this.exportFull);

        // iterator on rows
        while(rowIterator.hasNext()){
            Row row = rowIterator.next();

            if(this.model.getTableDecorator() != null){

                String stringStartRow = this.model.getTableDecorator().startRow();
                write(out, stringStartRow);
            }

            // iterator on columns
            ColumnIterator columnIterator = row.getColumnIterator(this.model.getHeaderCellList());

            write(out, rowStart);

            while(columnIterator.hasNext()){
                Column column = columnIterator.nextColumn();

                // Get the value to be displayed for the column
                String value = escapeColumnValue(column.getValue(this.decorated));

                write(out, cellStart);

                write(out, value);

                if(alwaysAppendCellEnd || columnIterator.hasNext()){
                    write(out, cellEnd);
                }

            }
            if(alwaysAppendRowEnd || rowIterator.hasNext()){
                write(out, rowEnd);
            }
        }

        // document end
        write(out, documentEnd);

    }

    /**
     * Write a String, checking for nulls value.
     * 
     * @param out
     *        output writer
     * @param string
     *        String to be written
     * @throws IOException
     *         thrown by out.write
     */
    private void write(Writer out, String string) throws IOException {
        if(string != null){
            out.write(string);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean outputPage() {
        return false;
    }

    /**
     * Get the offender Info
     * 
     * @param str
     *        str
     * @return String
     */
    private String doOffenderInfo(String str) {
        StringBuffer sb = new StringBuffer();

        String rowStart = getRowStart();
        String rowEnd = getRowEnd();
        String cellStart = getCellStart();
        String cellEnd = getCellEnd();

        // start row & cell
        if(rowStart != null && cellStart != null){
            sb.append(rowStart).append(cellStart);
        }

        sb.append(str);

        // end cell
        if(cellEnd != null){
            sb.append(cellEnd);
        }
        // end row
        if(rowEnd != null){
            sb.append(rowEnd);
        }
        return sb.toString();
    }
}
