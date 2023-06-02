package gov.doc.isu.dwarf.taglib.displaytag.tags.el;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * BeanInfo descriptor for the <code>ELFilterSearchRowTag</code> class. Unevaluated EL expression has to be kept separately from the evaluated value, since the JSP compiler can choose to reuse different tag instances if they received the same original attribute values, and the JSP compiler can choose to not re-call the setter methods.
 * 
 * @author Will Glass-Husain
 */
public class ELFilterSearchRowTagBeanInfo extends SimpleBeanInfo {

    /**
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public PropertyDescriptor[] getPropertyDescriptors() {
        List proplist = new ArrayList();

        try{
            proplist.add(new PropertyDescriptor("id", ELFilterSearchRowTag.class, null, "setId"));
            proplist.add(new PropertyDescriptor("media", ELFilterSearchRowTag.class, null, "setMedia"));
            proplist.add(new PropertyDescriptor("ignoreColumns", ELFilterSearchRowTag.class, null, "setIgnoreColumns"));
            proplist.add(new PropertyDescriptor("formName", ELFilterSearchRowTag.class, null, "setFormName"));
            proplist.add(new PropertyDescriptor("action", ELFilterSearchRowTag.class, null, "setAction"));

        }catch(IntrospectionException ex){
            // ignore, this should never happen
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }

}
