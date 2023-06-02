/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.export;

import static gov.doc.isu.dwarf.print.PDFConstants.PDF_COLUMN_BACKGROUND;
import static gov.doc.isu.dwarf.print.PDFConstants.headerFont;
import static gov.doc.isu.dwarf.print.PDFConstants.textFont;
import gov.doc.isu.dwarf.print.Footer;
import gov.doc.isu.dwarf.print.PDFGenerator;
import gov.doc.isu.dwarf.taglib.displaytag.Messages;
import gov.doc.isu.dwarf.taglib.displaytag.exception.BaseNestableJspTagException;
import gov.doc.isu.dwarf.taglib.displaytag.exception.SeverityEnum;
import gov.doc.isu.dwarf.taglib.displaytag.model.Column;
import gov.doc.isu.dwarf.taglib.displaytag.model.ColumnIterator;
import gov.doc.isu.dwarf.taglib.displaytag.model.HeaderCell;
import gov.doc.isu.dwarf.taglib.displaytag.model.Row;
import gov.doc.isu.dwarf.taglib.displaytag.model.RowIterator;
import gov.doc.isu.dwarf.taglib.displaytag.model.TableModel;
import gov.doc.isu.dwarf.util.AppUtil;

import java.awt.Color;
import java.io.OutputStream;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

/**
 * PDF exporter using IText. This class is provided more as an example than as a "production ready" class: users probably will need to write a custom export class with a specific layout.
 * 
 * @author Ivan Markov
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class PdfView implements BinaryExportView {

    private static final String HEADER_TEXT = "pdf.export.header";
    private static final String FOOTER_TEXT = "pdf.export.footer";

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
     * This is the table, added as an Element to the PDF document. It contains all the data, needed to represent the visible table into the PDF
     */
    private Table tablePDF;

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.ExportView#setParameters(TableModel, boolean, boolean, boolean)
     */
    public void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader, boolean decorateValues) {
        this.model = tableModel;
        this.exportFull = exportFullList;
        this.header = includeHeader;
        this.decorated = decorateValues;
    }

    /**
     * Initialize the main info holder table.
     * 
     * @throws BadElementException
     *         for errors during table initialization
     */
    protected void initTable() throws BadElementException {
        tablePDF = new Table(this.model.getNumberOfColumns());
        // commented this line due to the new iText jar
        // replaced it with the line of code below it. SLS00#IS JCCC
        // tablePDF.setDefaultVerticalAlignment(Element.ALIGN_TOP);
        tablePDF.setAlignment(Element.ALIGN_TOP);
        tablePDF.setCellsFitPage(true);
        tablePDF.setWidth(100);

        tablePDF.setPadding(2);
        tablePDF.setSpacing(0);
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BaseExportView#getMimeType()
     * @return "application/pdf"
     */
    public String getMimeType() {
        return "application/pdf"; //$NON-NLS-1$
    }

    /**
     * The overall PDF table generator.
     * 
     * @throws JspException
     *         for errors during value retrieving from the table model
     * @throws BadElementException
     *         IText exception
     */
    protected void generatePDFTable() throws JspException, BadElementException {
        if(this.header){
            generateHeaders();
        }
        tablePDF.endHeaders();
        generateRows();
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BinaryExportView#doExport(OutputStream)
     */
    public void doExport(OutputStream out) throws JspException {
        try{
            // Initialize the table with the appropriate number of columns
            initTable();

            // Initialize the Document and register it with PdfWriter listener and the OutputStream
            Document document = new Document(PageSize.A4.rotate(), 60, 60, 40, 40);
            document.addCreationDate();
            PDFGenerator gen = new PDFGenerator();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            String tableName = "";
            if(this.model.getTableName() != null){
                tableName = this.model.getTableName();
            }
            if(this.model.getOffenderInfo() != null){
                writer.setPageEvent(new Footer(this.model.getOffenderInfo().getOffenderFullName(), String.valueOf(this.model.getOffenderInfo().getOffenderNum()), this.model.getProperties().getExportPdfFooter()));
            }else{
                writer.setPageEvent(new Footer("", "", this.model.getProperties().getExportPdfFooter()));
            }
            generatePDFTable();
            document.open();
            document.add(gen.getDOCHeader());
            document.add(gen.getTableCaption(tableName));
            document.add(this.tablePDF);

            document.close();

        }catch(Exception e){
            throw new PdfGenerationException(e);
        }
    }

    /**
     * Generates the header cells, which persist on every page of the PDF document.
     * 
     * @throws BadElementException
     *         IText exception
     */
    protected void generateHeaders() throws BadElementException {
        Iterator iterator = this.model.getHeaderCellList().iterator();
        while(iterator.hasNext()){
            HeaderCell headerCell = (HeaderCell) iterator.next();

            String columnHeader = headerCell.getTitle();

            if(columnHeader == null){
                columnHeader = StringUtils.capitalize(headerCell.getBeanPropertyName());
            }

            Cell hdrCell = getColumnHeaderCell(columnHeader);
            hdrCell.setHeader(true);
            tablePDF.addCell(hdrCell);

        }
    }

    /**
     * Generates all the row cells.
     * 
     * @throws JspException
     *         for errors during value retrieving from the table model
     * @throws BadElementException
     *         errors while generating content
     */
    protected void generateRows() throws JspException, BadElementException {
        // get the correct iterator (full or partial list according to the exportFull field)
        RowIterator rowIterator = this.model.getRowIterator(this.exportFull);
        // iterator on rows
        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            int rowNum = row.getRowNumber();

            // iterator on columns
            ColumnIterator columnIterator = row.getColumnIterator(this.model.getHeaderCellList());

            while(columnIterator.hasNext()){
                Column column = columnIterator.nextColumn();

                // Get the value to be displayed for the column
                Object value = column.getValue(this.decorated);

                Cell cell = getCell(AppUtil.htmlBreak2TextBreak(ObjectUtils.toString(value)));
                if((rowNum % 2) == 0){
                    cell.setBackgroundColor(new Color(255, 255, 255));
                }else{
                    cell.setBackgroundColor(new Color(238, 238, 238));
                }
                tablePDF.addCell(cell);
            }
        }
    }

    /**
     * Returns a formatted cell for the given value.
     * 
     * @param value
     *        cell value
     * @return Cell
     * @throws BadElementException
     *         errors while generating content
     */
    private Cell getCell(String value) throws BadElementException {
        Cell cell = new Cell(new Chunk(StringUtils.trimToEmpty(value), textFont));
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setLeading(8);
        return cell;
    }

    /**
     * Returns a formatted header cell for the given value.
     * 
     * @param value
     *        cell value
     * @return Cell
     * @throws BadElementException
     *         errors while generating content
     */
    private Cell getColumnHeaderCell(String value) throws BadElementException {
        Cell cell = new Cell(new Chunk(StringUtils.trimToEmpty(value), headerFont));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setLeading(8);
        cell.setBackgroundColor(PDF_COLUMN_BACKGROUND);
        return cell;
    }

    /**
     * Wraps IText-generated exceptions.
     * 
     * @author Fabrizio Giustina
     * @author Steven Skinner JCCC
     */
    static class PdfGenerationException extends BaseNestableJspTagException {

        /**
         * D1597A17A6.
         */
        private static final long serialVersionUID = 899149338534L;

        /**
         * Instantiate a new PdfGenerationException with a fixed message and the given cause.
         * 
         * @param cause
         *        Previous exception
         */
        public PdfGenerationException(Throwable cause) {
            super(PdfView.class, Messages.getString("PdfView.errorexporting"), cause); //$NON-NLS-1$
        }

        /**
         * @see gov.doc.isu.dwarf.taglib.displaytag.exception.BaseNestableJspTagException#getSeverity()
         */
        public SeverityEnum getSeverity() {
            return SeverityEnum.ERROR;
        }
    }
}
