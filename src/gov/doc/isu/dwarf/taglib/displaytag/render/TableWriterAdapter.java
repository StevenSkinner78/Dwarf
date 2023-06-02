/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.render;

import gov.doc.isu.dwarf.taglib.displaytag.model.Column;
import gov.doc.isu.dwarf.taglib.displaytag.model.FilterSearchRow;
import gov.doc.isu.dwarf.taglib.displaytag.model.Row;
import gov.doc.isu.dwarf.taglib.displaytag.model.SubRow;
import gov.doc.isu.dwarf.taglib.displaytag.model.TableModel;

/**
 * Convenience abstract adapter for constructing a table view; contains only stub implementations. This class exists as a convenience for creating table-writer objects. Extend this class to create a table writer and override the methods of interest. This class also protects subclasses from future additions to TableWriterTemplate they may not be interested in.
 * 
 * @author Jorge L. Barroso
 * @author Steven Skinner JCCC
 */
public abstract class TableWriterAdapter extends TableWriterTemplate {

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeEmptyListMessage(java.lang.String)
     */
    protected void writeEmptyListMessage(String emptyListMessage) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeTopBanner(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeTopBanner(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeExpandCollapseAllBanner(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeExpandCollapseAllBanner(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeTableOpener(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeTableOpener(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeCaption(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeCaption(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeTableHeader(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeTableHeader(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writePreBodyFooter(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writePreBodyFooter(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeTableBodyOpener(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeTableBodyOpener(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeTableBodyCloser(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeTableBodyCloser(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writePostBodyFooter(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writePostBodyFooter(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeTableCloser(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeTableCloser(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeBottomBanner(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeBottomBanner(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeDecoratedTableFinish(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeDecoratedTableFinish(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeDecoratedRowStart(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeRowOpener(gov.doc.isu.dwarf.taglib.displaytag.model.Row)
     */
    protected void writeRowOpener(Row row) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeColumnOpener(gov.doc.isu.dwarf.taglib.displaytag.model.Column)
     */
    protected void writeColumnOpener(Column column) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeColumnValue(Object,gov.doc.isu.dwarf.taglib.displaytag.model.Column)
     */
    protected void writeColumnValue(Object value, Column column) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeColumnCloser(gov.doc.isu.dwarf.taglib.displaytag.model.Column)
     */
    protected void writeColumnCloser(Column column) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeRowWithNoColumns(java.lang.String)
     */
    protected void writeRowWithNoColumns(String string) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeRowCloser(gov.doc.isu.dwarf.taglib.displaytag.model.Row)
     */
    protected void writeRowCloser(Row row) throws Exception {}

    /******* Enhancement :: Search Row :: Start *******/
    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeSearchRowCloser(gov.doc.isu.dwarf.taglib.displaytag.model.FilterSearchRow)
     */
    protected void writeSearchRowCloser(FilterSearchRow row) {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeSearchRowOpener(gov.doc.isu.dwarf.taglib.displaytag.model.FilterSearchRow)
     */
    protected void writeSearchRowOpener(FilterSearchRow row) {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeSearchRowWithNoColumns(java.lang.String)
     */
    protected void writeSearchRowWithNoColumns(String subRowContent) {}

    /******* Enhancement :: Search Row :: End *******/

    /******* Enhancement :: Sub Row :: Start *******/
    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeSubRowCloser(gov.doc.isu.dwarf.taglib.displaytag.model.SubRow)
     */
    protected void writeSubRowCloser(SubRow row) {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeSubRowOpener(gov.doc.isu.dwarf.taglib.displaytag.model.SubRow)
     */
    protected void writeSubRowOpener(SubRow row) {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeSubRowWithNoColumns(java.lang.String)
     */
    protected void writeSubRowWithNoColumns(String subRowContent) {}

    /******* Enhancement :: Sub Row :: Start *******/

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeDecoratedRowFinish(gov.doc.isu.dwarf.taglib.displaytag.model.TableModel)
     */
    protected void writeDecoratedRowFinish(TableModel model) throws Exception {}

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.render.TableWriterTemplate#writeEmptyListRowMessage(java.lang.String)
     */
    protected void writeEmptyListRowMessage(String message) throws Exception {}
}
