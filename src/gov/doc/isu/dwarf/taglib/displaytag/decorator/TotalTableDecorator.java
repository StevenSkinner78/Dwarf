/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.decorator;

import gov.doc.isu.dwarf.taglib.displaytag.exception.DecoratorException;
import gov.doc.isu.dwarf.taglib.displaytag.model.HeaderCell;
import gov.doc.isu.dwarf.taglib.displaytag.model.TableModel;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * A table decorator which adds rows with totals (for column with the "total" attribute set) and subtotals (grouping by the column with a group="1" attribute).
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class TotalTableDecorator extends TableDecorator {

    /**
     * Logger.
     */
    private static Logger log = Logger.getLogger(TotalTableDecorator.class);

    /**
     * total amount.
     */
    private Map<String, Double> grandTotals = new HashMap<String, Double>();

    /**
     * total amount for current group.
     */
    private Map<String, Double> subTotals = new HashMap<String, Double>();

    /**
     * Previous values needed for grouping.
     */
    private Map<String, Object> previousValues = new HashMap<String, Object>();

    /**
     * Name of the property used for grouping.
     */
    private String groupPropertyName;

    /**
     * Label used for subtotals. Default: "{0} total".
     */
    private String subtotalLabel = "{0} subtotal";

    /**
     * Label used for totals. Default: "Total".
     */
    private String totalLabel = "Total";

    /**
     * Setter for <code>subtotalLabel</code>.
     * 
     * @param subtotalLabel
     *        The subtotalLabel to set.
     */
    public void setSubtotalLabel(String subtotalLabel) {
        this.subtotalLabel = subtotalLabel;
    }

    /**
     * Setter for <code>totalLabel</code>.
     * 
     * @param totalLabel
     *        The totalLabel to set.
     */
    public void setTotalLabel(String totalLabel) {
        this.totalLabel = totalLabel;
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.decorator.Decorator#init(PageContext, Object, TableModel)
     */
    public void init(PageContext context, Object decorated, TableModel tableModel) {
        super.init(context, decorated, tableModel);

        // reset
        groupPropertyName = null;
        grandTotals.clear();
        subTotals.clear();
        previousValues.clear();

        for(Iterator<?> it = tableModel.getHeaderCellList().iterator();it.hasNext();){
            HeaderCell cell = (HeaderCell) it.next();
            if(cell.getGroup() == 1){
                groupPropertyName = cell.getBeanPropertyName();
            }
        }
    }

    public String startRow() {
        String subtotalRow = null;

        if(groupPropertyName != null){
            Object groupedPropertyValue = evaluate(groupPropertyName);
            Object previousGroupedPropertyValue = previousValues.get(groupPropertyName);
            // subtotals
            if(previousGroupedPropertyValue != null && !ObjectUtils.equals(previousGroupedPropertyValue, groupedPropertyValue)){
                subtotalRow = createTotalRow(false);
            }
            previousValues.put(groupPropertyName, groupedPropertyValue);
        }

        for(Iterator<?> it = tableModel.getHeaderCellList().iterator();it.hasNext();){
            HeaderCell cell = (HeaderCell) it.next();
            if(cell.isTotaled()){
                String totalPropertyName = cell.getBeanPropertyName();
                Number amount = (Number) evaluate(totalPropertyName);

                Number previousSubTotal = (Number) subTotals.get(totalPropertyName);
                Number previousGrandTotals = (Number) grandTotals.get(totalPropertyName);

                subTotals.put(totalPropertyName, new Double((previousSubTotal != null ? previousSubTotal.doubleValue() : 0) + (amount != null ? amount.doubleValue() : 0)));

                grandTotals.put(totalPropertyName, new Double((previousGrandTotals != null ? previousGrandTotals.doubleValue() : 0) + (amount != null ? amount.doubleValue() : 0)));
            }
        }

        return subtotalRow;
    }

    /**
     * After every row completes we evaluate to see if we should be drawing a new total line and summing the results from the previous group.
     * 
     * @return String
     */
    public final String finishRow() {
        StringBuffer buffer = new StringBuffer(1000);

        // Grand totals...
        if(getViewIndex() == ((List<?>) getDecoratedObject()).size() - 1){
            if(groupPropertyName != null){
                buffer.append(createTotalRow(false));
            }
            buffer.append(createTotalRow(true));
        }
        return buffer.toString();

    }

    protected String createTotalRow(boolean grandTotal) {
        StringBuffer buffer = new StringBuffer(1000);
        buffer.append("\n<tr class=\"total\">"); //$NON-NLS-1$

        List<?> headerCells = tableModel.getHeaderCellList();

        for(Iterator<?> it = headerCells.iterator();it.hasNext();){
            HeaderCell cell = (HeaderCell) it.next();
            String cssClass = ObjectUtils.toString(cell.getHtmlAttributes().get("class"));

            buffer.append("<td"); //$NON-NLS-1$
            if(StringUtils.isNotEmpty(cssClass)){
                buffer.append(" class=\""); //$NON-NLS-1$
                buffer.append(cssClass);
                buffer.append("\""); //$NON-NLS-1$
            }
            buffer.append(">"); //$NON-NLS-1$

            if(cell.isTotaled()){
                String totalPropertyName = cell.getBeanPropertyName();
                Object total = grandTotal ? grandTotals.get(totalPropertyName) : subTotals.get(totalPropertyName);

                DisplaytagColumnDecorator[] decorators = cell.getColumnDecorators();
                for(int j = 0;j < decorators.length;j++){
                    try{
                        total = decorators[j].decorate(total, this.getPageContext(), tableModel.getMedia());
                    }catch(DecoratorException e){
                        log.warn(e.getMessage(), e);
                        // ignore, use undecorated value for totals
                    }
                }
                buffer.append(total);
            }else if(groupPropertyName != null && groupPropertyName.equals(cell.getBeanPropertyName())){
                buffer.append(grandTotal ? totalLabel : MessageFormat.format(subtotalLabel, new Object[]{previousValues.get(groupPropertyName)}));
            }

            buffer.append("</td>"); //$NON-NLS-1$

        }

        buffer.append("</tr>"); //$NON-NLS-1$

        // reset subtotal
        this.subTotals.clear();

        return buffer.toString();
    }

}
