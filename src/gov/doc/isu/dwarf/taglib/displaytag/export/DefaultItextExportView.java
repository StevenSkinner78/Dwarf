/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.export;

import gov.doc.isu.dwarf.taglib.displaytag.Messages;
import gov.doc.isu.dwarf.taglib.displaytag.exception.BaseNestableJspTagException;
import gov.doc.isu.dwarf.taglib.displaytag.exception.SeverityEnum;
import gov.doc.isu.dwarf.taglib.displaytag.model.TableModel;
import gov.doc.isu.dwarf.taglib.displaytag.render.ItextTableWriter;

import java.io.OutputStream;

import javax.servlet.jsp.JspException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

/**
 * Exporter using iText: subclasses export to any of the iText document types, such as PDF and RTF.
 * 
 * @author Jorge L. Barroso
 * @author Steven Skinner JCCC
 */
public abstract class DefaultItextExportView implements BinaryExportView {

    /**
     * TableModel to render.
     */
    private TableModel model;

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.ExportView#setParameters(TableModel, boolean, boolean, boolean)
     */
    public void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader, boolean decorateValues) {
        this.model = tableModel;
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BaseExportView#getMimeType() Meant to be overwritten by subclasses.
     * @return null
     */
    public String getMimeType() {
        return null;
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BinaryExportView#doExport(OutputStream)
     */
    public void doExport(OutputStream out) throws JspException {
        try{
            Document document = new Document(PageSize.A4.rotate(), 60, 60, 40, 40);
            this.initItextWriter(document, out);
            document.open();
            Table table = new Table(this.model.getNumberOfColumns());
            ItextTableWriter writer = new ItextTableWriter(table, document);
            writer.writeTable(this.model, "-1");
            document.add(table);
            document.close();
        }catch(Exception e){
            throw new ItextGenerationException(e);
        }
    }

    /**
     * Initializes the iText writer used by export view to write iText document, such as PDF or RTF iText writer.
     * 
     * @param document
     *        The iText document to be written.
     * @param out
     *        The output stream to which the document is written.
     * @throws DocumentException
     *         If something goes wrong during initialization.
     */
    protected abstract void initItextWriter(Document document, OutputStream out) throws DocumentException;

    /**
     * Wraps iText-generated exceptions.
     * 
     * @author Fabrizio Giustina
     * @author Steven Skinner JCCC
     */
    static class ItextGenerationException extends BaseNestableJspTagException {

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
        public ItextGenerationException(Throwable cause) {
            super(DefaultItextExportView.class, Messages.getString("DefaultItextExportView.errorexporting"), cause); //$NON-NLS-1$
            this.initCause(cause);
        }

        /**
         * @see gov.doc.isu.dwarf.taglib.displaytag.exception.BaseNestableJspTagException#getSeverity()
         */
        public SeverityEnum getSeverity() {
            return SeverityEnum.ERROR;
        }
    }
}
