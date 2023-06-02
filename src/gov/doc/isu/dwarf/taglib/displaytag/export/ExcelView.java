/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.export;

import gov.doc.isu.dwarf.taglib.displaytag.model.TableModel;

import org.apache.commons.lang.StringUtils;

/**
 * Export view for excel exporting.
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class ExcelView extends BaseExportView {

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BaseExportView#setParameters(TableModel, boolean, boolean, boolean)
     */
    public void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader, boolean decorateValues) {
        super.setParameters(tableModel, exportFullList, includeHeader, decorateValues);
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.ExportView#getMimeType()
     * @return "application/vnd.ms-excel"
     */
    public String getMimeType() {
        return "application/vnd.ms-excel"; //$NON-NLS-1$
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BaseExportView#getRowEnd()
     */
    protected String getRowEnd() {
        return "\n"; //$NON-NLS-1$
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BaseExportView#getCellEnd()
     */
    protected String getCellEnd() {
        return "\t"; //$NON-NLS-1$
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BaseExportView#getAlwaysAppendCellEnd()
     * @return false
     */
    protected boolean getAlwaysAppendCellEnd() {
        return false;
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BaseExportView#getAlwaysAppendRowEnd()
     * @return false
     */
    protected boolean getAlwaysAppendRowEnd() {
        return false;
    }

    /**
     * Escaping for excel format.
     * <ul>
     * <li>Quotes inside quoted strings are escaped with a double quote</li>
     * <li>Fields are surrounded by " (should be optional, but sometimes you get a "Sylk error" without those)</li>
     * </ul>
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BaseExportView#escapeColumnValue(java.lang.Object)
     */
    protected String escapeColumnValue(Object value) {
        if(value != null){
            // quotes around fields are needed to avoid occasional "Sylk format invalid" messages from excel
            return "\"" //$NON-NLS-1$
                    + StringUtils.replace(StringUtils.trim(value.toString()), "\"", "\"\"") //$NON-NLS-1$ //$NON-NLS-2$ 
                    + "\""; //$NON-NLS-1$ 
        }

        return null;
    }

}
