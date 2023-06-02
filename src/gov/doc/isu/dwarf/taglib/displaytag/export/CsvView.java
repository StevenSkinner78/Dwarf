/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.export;

import gov.doc.isu.dwarf.taglib.displaytag.model.TableModel;

import org.apache.commons.lang.StringUtils;

/**
 * Export view for comma separated value exporting.
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class CsvView extends BaseExportView {

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BaseExportView#setParameters(TableModel, boolean, boolean, boolean)
     */
    public void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader, boolean decorateValues) {
        super.setParameters(tableModel, exportFullList, includeHeader, decorateValues);
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
        return ","; //$NON-NLS-1$
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BaseExportView#getAlwaysAppendCellEnd()
     */
    protected boolean getAlwaysAppendCellEnd() {
        return false;
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BaseExportView#getAlwaysAppendRowEnd()
     */
    protected boolean getAlwaysAppendRowEnd() {
        return true;
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.ExportView#getMimeType()
     */
    public String getMimeType() {
        return "text/csv"; //$NON-NLS-1$
    }

    /**
     * Escaping for csv format.
     * <ul>
     * <li>Quotes inside quoted strings are escaped with a /</li>
     * <li>Fields containings newlines or , are surrounded by "</li>
     * </ul>
     * Note this is the standard CVS format and it's not handled well by excel.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.export.BaseExportView#escapeColumnValue(java.lang.Object)
     */
    protected String escapeColumnValue(Object value) {
        String stringValue = StringUtils.trim(value.toString());
        if(!StringUtils.containsNone(stringValue, new char[]{'\n', ','})){
            return "\"" + //$NON-NLS-1$
                    StringUtils.replace(stringValue, "\"", "\\\"") + "\""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }

        return stringValue;
    }

}
