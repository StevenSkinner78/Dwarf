package gov.doc.isu.dwarf.taglib.displaytag.decorator;

import gov.doc.isu.dwarf.resources.Constants;
import gov.doc.isu.dwarf.taglib.displaytag.exception.DecoratorException;
import gov.doc.isu.dwarf.taglib.displaytag.properties.MediaTypeEnum;

import java.sql.Date;
import java.util.Locale;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.time.FastDateFormat;

/**
 * Column Decorator for the Dwarf Application.
 * 
 * @author Steven L. Skinner
 */
public class DataUtilityColumnDecorator implements DisplaytagColumnDecorator {

    private FastDateFormat dateFormat = FastDateFormat.getInstance("MM/dd/yyyy", Locale.ENGLISH);

    /**
     * {@inheritDoc}
     */
    public Object decorate(Object columnValue, final PageContext pageContext, final MediaTypeEnum media) throws DecoratorException {

        if(columnValue instanceof Date){
            if(!columnValue.equals(Constants.DEFAULT_SQL_DATE_AS_DATE)){
                columnValue = dateFormat.format(columnValue);
            }else{
                columnValue = null;
            }
        }
        return columnValue;

    }

}
