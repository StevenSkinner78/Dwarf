package gov.doc.isu.dwarf.taglib;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.SelectTag;
import org.apache.struts.util.MessageResources;

/**
 * Custom tag that represents an HTML select element, associated with a bean property specified by our attributes. <br/>
 * This tag to be used in building of FilterSearchRow if the search column should be a drop down box.<br/>
 * <b>This tag can never be used in jsp.</b>
 * 
 * @author Steven Skinner JCCC
 */
public class DwarfSelectTag extends SelectTag {

    // ----------------------------------------------------- Instance Variables

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
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
        DwarfSelectTag.stringBuffer = stringBuffer;
    }

    /**
     * The actual values we will match against, calculated in doStartTag().
     */
    protected String match[] = null;

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages = MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

    /**
     * Should multiple selections be allowed. Any non-null value will trigger rendering this.
     */
    protected String multiple = null;

    public String getMultiple() {
        return (this.multiple);
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    /**
     * The name of the bean containing our underlying property.
     */
    protected String name = Constants.BEAN_KEY;

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The property name we are associated with.
     */
    protected String property = null;

    /**
     * The saved body content of this tag.
     */
    protected String saveBody = null;

    /**
     * How many available options should be displayed when this element is rendered?
     */
    protected String size = null;

    public String getSize() {
        return (this.size);
    }

    public void setSize(String size) {
        this.size = size;
    }

    /**
     * The value to compare with for marking an option selected.
     */
    protected String value = null;

    // ------------------------------------------------------------- Properties

    /**
     * Does the specified value match one of those we are looking for?
     * 
     * @param value
     *        Value to be compared.
     */
    public boolean isMatched(String value) {
        if((this.match == null) || (value == null)){
            return false;
        }

        for(int i = 0;i < this.match.length;i++){
            if(value.equals(this.match[i])) return true;
        }

        return false;
    }

    /**
     * Return the property name.
     */
    public String getProperty() {

        return (this.property);

    }

    /**
     * Set the property name.
     * 
     * @param property
     *        The new property name
     */
    public void setProperty(String property) {

        this.property = property;

    }

    /**
     * Return the comparison value.
     */
    public String getValue() {

        return (this.value);

    }

    /**
     * Set the comparison value.
     * 
     * @param value
     *        The new comparison value
     */
    public void setValue(String value) {

        this.value = value;

    }

    // --------------------------------------------------------- Public Methods

    /**
     * Render the beginning of this select tag.
     * <p>
     * Support for indexed property since Struts 1.1
     * 
     * @exception JspException
     *            if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

        // TagUtils.getInstance().write(pageContext, renderSelectStartElement());
        stringBuffer = new StringBuffer();
        stringBuffer.append(renderSelectStartElement());
        // Store this tag itself as a page attribute
        pageContext.setAttribute(Constants.SELECT_KEY, this);

        this.calculateMatchValues();

        return (EVAL_BODY_BUFFERED);
    }

    /**
     * Create an appropriate select start element based on our parameters.
     * 
     * @throws JspException
     * @since Struts 1.1
     */
    protected String renderSelectStartElement() throws JspException {
        StringBuffer results = new StringBuffer("<select");

        prepareAttribute(results, "name", prepareName());
        prepareAttribute(results, "accesskey", getAccesskey());
        if(multiple != null){
            results.append(" multiple=\"multiple\"");
        }
        prepareAttribute(results, "size", getSize());
        prepareAttribute(results, "tabindex", getTabindex());
        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        prepareOtherAttributes(results);
        results.append(">");

        return results.toString();
    }

    /**
     * Calculate the match values we will actually be using.
     * 
     * @throws JspException
     */
    private void calculateMatchValues() throws JspException {
        if(this.value != null){
            this.match = new String[1];
            this.match[0] = this.value;

        }else{
            Object bean = TagUtils.getInstance().lookup(pageContext, name, null);
            if(bean == null){
                JspException e = new JspException(messages.getMessage("getter.bean", name));

                TagUtils.getInstance().saveException(pageContext, e);
                throw e;
            }

            try{
                this.match = BeanUtils.getArrayProperty(bean, property);
                if(this.match == null){
                    this.match = new String[0];
                }

            }catch(IllegalAccessException e){
                TagUtils.getInstance().saveException(pageContext, e);
                throw new JspException(messages.getMessage("getter.access", property, name));

            }catch(InvocationTargetException e){
                Throwable t = e.getTargetException();
                TagUtils.getInstance().saveException(pageContext, t);
                throw new JspException(messages.getMessage("getter.result", property, t.toString()));

            }catch(NoSuchMethodException e){
                TagUtils.getInstance().saveException(pageContext, e);
                throw new JspException(messages.getMessage("getter.method", property, name));
            }
        }
    }

    /**
     * Save any body content of this tag, which will generally be the option(s) representing the values displayed to the user.
     * 
     * @exception JspException
     *            if a JSP exception has occurred
     */
    public int doAfterBody() throws JspException {

        if(bodyContent != null){
            String value = bodyContent.getString();
            if(value == null){
                value = "";
            }

            this.saveBody = value.trim();
        }

        return (SKIP_BODY);
    }

    /**
     * Render the end of this form.
     * 
     * @exception JspException
     *            if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {

        stringBuffer = new StringBuffer();
        // Remove the page scope attributes we created
        pageContext.removeAttribute(Constants.SELECT_KEY);
        // Render a tag representing the end of our current form
        StringBuffer results = new StringBuffer();
        if(saveBody != null){
            results.append(saveBody);
            saveBody = null;
        }
        results.append("</select>");

        stringBuffer.append(results.toString());
        // TagUtils.getInstance().write(pageContext, results.toString());

        return (EVAL_PAGE);
    }

    /**
     * Prepare the name element
     * 
     * @return The element name.
     */
    protected String prepareName() throws JspException {

        if(property == null){
            return null;
        }

        // * @since Struts 1.1
        if(indexed){
            StringBuffer results = new StringBuffer();
            prepareIndex(results, name);
            results.append(property);
            return results.toString();
        }

        return property;

    }

    /**
     * Release any acquired resources.
     */
    public void release() {

        super.release();
        match = null;
        multiple = null;
        name = Constants.BEAN_KEY;
        property = null;
        saveBody = null;
        size = null;
        value = null;

    }
}
