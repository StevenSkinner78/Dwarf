/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.util;

import gov.doc.isu.dwarf.taglib.displaytag.util.Href;
import gov.doc.isu.dwarf.taglib.displaytag.util.TagConstants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * DwarfHref is a class representing an URI (the href parameter of an &lt;a&gt; tag). Provides methods to insert new parameters. It doesn't support multiple parameter values
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class DwarfHref implements Href {

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Base url for the href.
     */
    private String url;

    /**
     * Url parameters.
     */
    private Map<String, Object> parameters;

    /**
     * Anchor (to be added at the end of URL).
     */
    private String anchor;
    private static final String DV_HREF_SUBMIT_LIST_PAGE_DATA_START = "javascript:submitListPageData('";
    private static final String DV_HREF_SUBMIT_LIST_PAGE_DATA_END = "');";
    private static final String DV_HREF_SUBMIT_LIST_PAGE_DATA_METHOD_NAME = "method=updateListPageData";

    /**
     * Construct a new Href parsing a URL. Parameters are stripped from the base url and saved in the parameters map.
     * 
     * @param baseUrl
     *        String
     */
    public DwarfHref(String baseUrl) {
        this.parameters = new HashMap<String, Object>();
        setFullUrl(baseUrl);
    }

    /**
     * @see gov.doc.isu.dwarf.taglib.displaytag.util.Href#setFullUrl(java.lang.String)
     */
    public void setFullUrl(String baseUrl) {
        this.url = null;
        this.anchor = null;
        String noAnchorUrl;
        int anchorposition = baseUrl.indexOf('#');

        // extract anchor from url
        if(anchorposition != -1){
            noAnchorUrl = baseUrl.substring(0, anchorposition);
            this.anchor = baseUrl.substring(anchorposition + 1);
        }else{
            noAnchorUrl = baseUrl;
        }

        if(noAnchorUrl.indexOf('?') == -1){
            // simple url, no parameters
            this.url = noAnchorUrl;
            return;
        }

        // the Url already has parameters, put them in the parameter Map
        StringTokenizer tokenizer = new StringTokenizer(noAnchorUrl, "?"); //$NON-NLS-1$

        if(baseUrl.startsWith("?")) //$NON-NLS-1$
        {
            // support fake URI's which are just parameters to use with the current uri
            url = TagConstants.EMPTY_STRING;
        }else{
            // base url (before "?")
            url = tokenizer.nextToken();
        }

        if(!tokenizer.hasMoreTokens()){
            return;
        }

        // process parameters
        StringTokenizer paramTokenizer = new StringTokenizer(tokenizer.nextToken(), "&"); //$NON-NLS-1$

        // split parameters (key=value)
        while(paramTokenizer.hasMoreTokens()){
            // split key and value ...
            String[] keyValue = StringUtils.split(paramTokenizer.nextToken().replaceAll("amp;", ""), '=');

            // encode name/value to prevent css
            String escapedKey = StringEscapeUtils.escapeHtml(keyValue[0]);
            String escapedValue = keyValue.length > 1 ? StringEscapeUtils.escapeHtml(keyValue[1]) : TagConstants.EMPTY_STRING;

            if(!this.parameters.containsKey(escapedKey)){
                // ... and add it to the map
                this.parameters.put(escapedKey, escapedValue);
            }else{
                // additional value for an existing parameter
                Object previousValue = this.parameters.get(escapedKey);
                if(previousValue != null && previousValue.getClass().isArray()){
                    Object[] previousArray = (Object[]) previousValue;
                    Object[] newArray = new Object[previousArray.length + 1];

                    int j;

                    for(j = 0;j < previousArray.length;j++){
                        newArray[j] = previousArray[j];
                    }

                    newArray[j] = escapedValue;
                    this.parameters.put(escapedKey, newArray);
                }else{
                    this.parameters.put(escapedKey, new Object[]{previousValue, escapedValue});
                }
            }
        }
    }

    /**
     * Adds a parameter to the href.
     * 
     * @param name
     *        String
     * @param value
     *        Object
     * @return this Href instance, useful for concatenation.
     */
    public Href addParameter(String name, Object value) {
        this.parameters.put(name, ObjectUtils.toString(value, null));
        return this;
    }

    /**
     * Removes a parameter from the href.
     * 
     * @param name
     *        String
     */
    public void removeParameter(String name) {
        // warning, param names are escaped
        this.parameters.remove(StringEscapeUtils.escapeHtml(name));
    }

    /**
     * Adds an int parameter to the href.
     * 
     * @param name
     *        String
     * @param value
     *        int
     * @return this Href instance, useful for concatenation.
     */
    public Href addParameter(String name, int value) {
        this.parameters.put(name, new Integer(value));
        return this;
    }

    /**
     * Getter for the map containing link parameters. The returned map is always a copy and not the original instance.
     * 
     * @return parameter Map (copy)
     */
    public Map<String, Object> getParameterMap() {
        Map<String, Object> copyMap = new HashMap<String, Object>(this.parameters.size());
        copyMap.putAll(this.parameters);
        return copyMap;
    }

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before added. Any parameter already present in the href object is removed.
     * 
     * @param parametersMap
     *        Map containing parameters
     */
    public void setParameterMap(Map<String, Object> parametersMap) {
        // create a new HashMap
        this.parameters = new HashMap<String, Object>(parametersMap.size());

        // copy the parameters
        addParameterMap(parametersMap);
    }

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before added. Parameters in the original href are kept and not overridden.
     * 
     * @param parametersMap
     *        Map containing parameters
     */
    public void addParameterMap(Map<String, Object> parametersMap) {
        // handle nulls
        if(parametersMap == null){
            return;
        }

        // copy value, escaping html
        Iterator<?> mapIterator = parametersMap.entrySet().iterator();
        while(mapIterator.hasNext()){
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) mapIterator.next();
            String key = StringEscapeUtils.escapeHtml((String) entry.getKey());

            // don't overwrite parameters
            if(!this.parameters.containsKey(key)){
                Object value = entry.getValue();

                if(value != null){
                    if(value.getClass().isArray()){
                        String[] values = (String[]) value;
                        for(int i = 0;i < values.length;i++){
                            values[i] = StringEscapeUtils.escapeHtml(values[i]);
                        }
                    }else{
                        value = StringEscapeUtils.escapeHtml(value.toString());
                    }
                }

                this.parameters.put(key, value);
            }
        }
    }

    /**
     * Getter for the base url (without parameters).
     * 
     * @return String
     */
    public String getBaseUrl() {
        return this.url;
    }

    /**
     * Returns the URI anchor.
     * 
     * @return anchor or <code>null</code> if no anchor has been set.
     */
    public String getAnchor() {
        return this.anchor;
    }

    /**
     * Setter for the URI anchor.
     * 
     * @param name
     *        string to be used as anchor name (without #).
     */
    public void setAnchor(String name) {
        this.anchor = name;
    }

    /**
     * toString: output the full url with parameters.
     * 
     * @return String
     */
    public String toString() {

        StringBuffer buffer = new StringBuffer(30);

        buffer.append(this.url);

        if(this.parameters.size() > 0){
            buffer.append('?');
            Set<?> parameterSet = this.parameters.entrySet();

            Iterator<?> iterator = parameterSet.iterator();

            while(iterator.hasNext()){
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iterator.next();

                Object key = entry.getKey();
                Object value = entry.getValue();

                if(value == null){
                    buffer.append(key).append('='); // no value
                }else if(value.getClass().isArray()){
                    Object[] values = (Object[]) value;
                    for(int i = 0;i < values.length;i++){
                        if(i > 0){
                            buffer.append("&");
                        }

                        buffer.append(key).append('=').append(values[i]);
                    }
                }else{
                    buffer.append(key).append('=').append(value);
                }

                if(iterator.hasNext()){
                    buffer.append(TagConstants.AMPERSAND);
                }
            }
        }

        if(this.anchor != null){
            buffer.append('#');
            buffer.append(this.anchor);
        }

        return checkAndUpdateUrl(buffer.toString());
    }

    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        final DwarfHref href;
        try{
            href = (DwarfHref) super.clone();
        }catch(CloneNotSupportedException e){
            throw new UnhandledException(e);
        }

        href.parameters = new HashMap<String, Object>(this.parameters);
        return href;
    }

    /**
     * This method checks the url if it contains "method=updateLastPageData", then it updates the url to call submitListPageData method of std_lib.js to retain updated form values on pagination.
     * 
     * @param url
     *        (String)
     * @return String (updated url String)
     */
    private String checkAndUpdateUrl(String url) {
        if(StringUtils.contains(url, DV_HREF_SUBMIT_LIST_PAGE_DATA_METHOD_NAME)){
            StringBuffer sb = new StringBuffer();
            sb.append(DV_HREF_SUBMIT_LIST_PAGE_DATA_START).append(url).append(DV_HREF_SUBMIT_LIST_PAGE_DATA_END);
            url = sb.toString();
        }
        return url;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if(!(object instanceof DwarfHref)){
            return false;
        }
        DwarfHref rhs = (DwarfHref) object;
        return new EqualsBuilder().append(this.parameters, rhs.parameters).append(this.url, rhs.url).append(this.anchor, rhs.anchor).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(1313733113, -431360889).append(this.parameters).append(this.url).append(this.anchor).toHashCode();
    }
}
