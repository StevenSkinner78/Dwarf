package gov.doc.isu.dwarf.taglib;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.OptionsTag;
import org.apache.struts.taglib.html.SelectTag;
import org.apache.struts.util.IteratorAdapter;
import org.apache.struts.util.MessageResources;

/**
 * Tag for creating multiple &lt;select&gt; options from a collection. The associated values displayed to the user may optionally be specified by a second collection, or will be the same as the values themselves. Each collection may be an array of objects, a Collection, an Enumeration, an Iterator, or a Map.
 * <p>
 * Tag for creating multiple options from a collection.<br/>
 * The associated values displayed to the user may optionally be specified by a second collection, or will be the same as the values themselves. Each collection may be an array of objects, a Collection, an Enumeration, an Iterator, or a Map.<br/>
 * This tag to be used in building of FilterSearchRow if the search column should be a drop down box.<br/>
 * <b>This tag can never be used in jsp.</b>
 * </p>
 * 
 * @author Steven Skinner JCCC
 */
public class DwarfOptionsTag extends OptionsTag {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * The message resources for this package.
     */
    private static MessageResources messages = MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

    /** This will be used to pass the options back to FilterSearchRow. */
    private static StringBuffer stringBuffer;

    /**
     * @return the stringBuffer
     */
    public StringBuffer getStringBuffer() {
        return stringBuffer;
    }

    /**
     * @param stringBuffer
     *        the stringBuffer to set
     */
    public void setStringBuffer(StringBuffer stringBuffer) {
        DwarfOptionsTag.stringBuffer = stringBuffer;
    }

    /**
     * The name of the collection containing beans that have properties to provide both the values and the labels (identified by the <code>property</code> and <code>labelProperty</code> attributes).
     */
    protected String collection = null;

    /**
     * @return the collection
     */
    public String getCollection() {
        return collection;
    }

    /**
     * @param collection
     *        the collection to set
     */
    public void setCollection(String collection) {
        this.collection = collection;
    }

    /**
     * Should the label values be filtered for HTML sensitive characters?
     */
    protected boolean filter = true;

    /**
     * @return the filter
     */
    public boolean isFilter() {
        return filter;
    }

    /**
     * @param filter
     *        the filter to set
     */
    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    /**
     * The name of the bean containing the labels collection.
     */
    protected String labelName = null;

    /**
     * The bean property containing the labels collection.
     */
    protected String labelProperty = null;

    /**
     * The name of the bean containing the values collection.
     */
    protected String name = null;

    /**
     * The name of the property to use to build the values collection.
     */
    protected String property = null;

    /**
     * The style associated with this tag.
     */
    private String style = null;

    /**
     * The named style class associated with this tag.
     */
    private String styleClass = null;

    /**
     * @return the labelName
     */
    public String getLabelName() {
        return labelName;
    }

    /**
     * @param labelName
     *        the labelName to set
     */
    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    /**
     * @return the labelProperty
     */
    public String getLabelProperty() {
        return labelProperty;
    }

    /**
     * @param labelProperty
     *        the labelProperty to set
     */
    public void setLabelProperty(String labelProperty) {
        this.labelProperty = labelProperty;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *        the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @param property
     *        the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style
     *        the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @return the styleClass
     */
    public String getStyleClass() {
        return styleClass;
    }

    /**
     * @param styleClass
     *        the styleClass to set
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * Process the start of this tag.
     * 
     * @return SKIP_BODY
     * @exception JspException
     *            if a JSP exception has occurred
     */

    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    /**
     * Process the end of this tag.
     * 
     * @return EVAL_PAGE
     * @exception JspException
     *            if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
        stringBuffer = new StringBuffer();
        // Acquire the select tag we are associated with
        SelectTag selectTag = (SelectTag) pageContext.getAttribute(Constants.SELECT_KEY);
        if(selectTag == null){
            throw new JspException(messages.getMessage("optionsTag.select"));
        }
        StringBuffer sb = new StringBuffer();

        // If a collection was specified, use that mode to render options
        if(collection != null){
            Iterator<?> collIterator = getIterator(collection, null);
            while(collIterator.hasNext()){

                Object bean = collIterator.next();
                Object value = null;
                Object label = null;

                try{
                    value = PropertyUtils.getProperty(bean, property);
                    if(value == null){
                        value = "";
                    }
                }catch(IllegalAccessException e){
                    throw new JspException(messages.getMessage("getter.access", property, collection));
                }catch(InvocationTargetException e){
                    Throwable t = e.getTargetException();
                    throw new JspException(messages.getMessage("getter.result", property, t.toString()));
                }catch(NoSuchMethodException e){
                    throw new JspException(messages.getMessage("getter.method", property, collection));
                }

                try{
                    if(labelProperty != null){
                        label = PropertyUtils.getProperty(bean, labelProperty);
                    }else{
                        label = value;
                    }

                    if(label == null){
                        label = "";
                    }
                }catch(IllegalAccessException e){
                    throw new JspException(messages.getMessage("getter.access", labelProperty, collection));
                }catch(InvocationTargetException e){
                    Throwable t = e.getTargetException();
                    throw new JspException(messages.getMessage("getter.result", labelProperty, t.toString()));
                }catch(NoSuchMethodException e){
                    throw new JspException(messages.getMessage("getter.method", labelProperty, collection));
                }

                String stringValue = value.toString();
                addOption(sb, stringValue, label.toString(), selectTag.isMatched(stringValue));

            }

        }

        // Otherwise, use the separate iterators mode to render options
        else{

            // Construct iterators for the values and labels collections
            Iterator<?> valuesIterator = getIterator(name, property);
            Iterator<?> labelsIterator = null;
            if((labelName != null) || (labelProperty != null)){
                labelsIterator = getIterator(labelName, labelProperty);
            }

            // Render the options tags for each element of the values coll.
            while(valuesIterator.hasNext()){
                Object valueObject = valuesIterator.next();
                if(valueObject == null){
                    valueObject = "";
                }
                String value = valueObject.toString();
                String label = value;
                if((labelsIterator != null) && labelsIterator.hasNext()){
                    Object labelObject = labelsIterator.next();
                    if(labelObject == null){
                        labelObject = "";
                    }
                    label = labelObject.toString();
                }
                addOption(sb, value, label, selectTag.isMatched(value));
            }
        }

        // TagUtils.getInstance().write(pageContext, sb.toString());
        stringBuffer.append(sb.toString());
        return EVAL_PAGE;

    }

    /**
     * Release any acquired resources.
     */
    public void release() {

        super.release();
        collection = null;
        filter = true;
        labelName = null;
        labelProperty = null;
        name = null;
        property = null;
        style = null;
        styleClass = null;
    }

    // ------------------------------------------------------ Protected Methods

    /**
     * Add an option element to the specified StringBuffer based on the specified parameters.
     * <p>
     * Note that this tag specifically does not support the <code>styleId</code> tag attribute, which causes the HTML <code>id</code> attribute to be emitted. This is because the HTML specification states that all "id" attributes in a document have to be unique. This tag will likely generate more than one <code>option</code> element element, but it cannot use the same <code>id</code> value. It's conceivable some sort of mechanism to supply an array of <code>id</code> values could be devised, but that doesn't seem to be worth the trouble.
     * 
     * @param sb
     *        StringBuffer accumulating our results
     * @param value
     *        Value to be returned to the server for this option
     * @param label
     *        Value to be shown to the user for this option
     * @param matched
     *        Should this value be marked as selected?
     */
    protected void addOption(StringBuffer sb, String value, String label, boolean matched) {

        sb.append("<option value=\"");
        if(filter){
            sb.append(TagUtils.getInstance().filter(value));
        }else{
            sb.append(value);
        }
        sb.append("\"");
        if(matched){
            sb.append(" selected=\"selected\"");
        }
        if(style != null){
            sb.append(" style=\"");
            sb.append(style);
            sb.append("\"");
        }
        if(styleClass != null){
            sb.append(" class=\"");
            sb.append(styleClass);
            sb.append("\"");
        }

        sb.append(">");

        if(filter){
            sb.append(TagUtils.getInstance().filter(label));
        }else{
            sb.append(label);
        }

        sb.append("</option>\r\n");

    }

    /**
     * Return an iterator for the option labels or values, based on our configured properties.
     * 
     * @param name
     *        Name of the bean attribute (if any)
     * @param property
     *        Name of the bean property (if any)
     * @return Iterator
     * @exception JspException
     *            if an error occurs
     */
    protected Iterator<?> getIterator(String name, String property) throws JspException {

        // Identify the bean containing our collection
        String beanName = name;
        if(beanName == null){
            beanName = Constants.BEAN_KEY;
        }

        Object bean = TagUtils.getInstance().lookup(pageContext, beanName, null);
        if(bean == null){
            throw new JspException(messages.getMessage("getter.bean", beanName));
        }

        // Identify the collection itself
        Object collection = bean;
        if(property != null){
            try{
                collection = PropertyUtils.getProperty(bean, property);
                if(collection == null){
                    throw new JspException(messages.getMessage("getter.property", property));
                }
            }catch(IllegalAccessException e){
                throw new JspException(messages.getMessage("getter.access", property, name));
            }catch(InvocationTargetException e){
                Throwable t = e.getTargetException();
                throw new JspException(messages.getMessage("getter.result", property, t.toString()));
            }catch(NoSuchMethodException e){
                throw new JspException(messages.getMessage("getter.method", property, name));
            }
        }

        // Construct and return an appropriate iterator
        if(collection.getClass().isArray()){
            collection = Arrays.asList((Object[]) collection);
        }

        if(collection instanceof Collection){
            return (((Collection<?>) collection).iterator());

        }else if(collection instanceof Iterator){
            return ((Iterator<?>) collection);

        }else if(collection instanceof Map){
            return (((Map<?, ?>) collection).entrySet().iterator());

        }else if(collection instanceof Enumeration){
            return new IteratorAdapter((Enumeration<?>) collection);

        }else{
            throw new JspException(messages.getMessage("optionsTag.iterator", collection.toString()));
        }
    }

}
