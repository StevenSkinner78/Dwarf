package gov.doc.isu.dwarf.taglib.displaytag.tags.el;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * BeanInfo descriptor for the <code>ELSubRowTag</code> class. Unevaluated EL expression has to be kept separately from the evaluated value, since the JSP compiler can choose to reuse different tag instances if they received the same original attribute values, and the JSP compiler can choose to not re-call the setter methods.
 * 
 * @author Will Glass-Husain
 */
public class ELSubRowTagBeanInfo extends SimpleBeanInfo {

    /**
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public PropertyDescriptor[] getPropertyDescriptors() {
        List proplist = new ArrayList();

        try{
            proplist.add(new PropertyDescriptor("id", ELSubRowTag.class, null, "setId"));
            proplist.add(new PropertyDescriptor("evenClass", ELSubRowTag.class, null, "setEvenClass"));
            proplist.add(new PropertyDescriptor("media", ELSubRowTag.class, null, "setMedia"));
            proplist.add(new PropertyDescriptor("oddClass", ELSubRowTag.class, null, "setOddClass"));
            proplist.add(new PropertyDescriptor("positionBelow", ELSubRowTag.class, null, "setPositionBelow"));
            proplist.add(new PropertyDescriptor("frequency", ELSubRowTag.class, null, "setFrequency"));
            proplist.add(new PropertyDescriptor("style", ELSubRowTag.class, null, "setStyle"));

            // deprecated attributes
            proplist.add(new PropertyDescriptor("background", ELSubRowTag.class, null, "setBackground"));
            proplist.add(new PropertyDescriptor("bgcolor", ELSubRowTag.class, null, "setBgcolor"));
            proplist.add(new PropertyDescriptor("height", ELSubRowTag.class, null, "setHeight"));

        }catch(IntrospectionException ex){
            // ignore, this should never happen
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }

}
