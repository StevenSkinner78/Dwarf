package gov.doc.isu.dwarf.taglib.displaytag.decorator;

/**
 * Table Decorator for the Dwarf Framework. Will add the row number to the id.
 * 
 * @author Steven L. Skinner
 */
public class DataUtilityTableDecorator extends TableDecorator {

    /**
     * {@inheritDoc}
     */
    public String addRowId() {
        return "rowNum" + getListIndex();
    }
}
