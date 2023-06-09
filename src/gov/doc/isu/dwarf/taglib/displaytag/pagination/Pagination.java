/**
 * Licensed under the Artistic License; you may not use this file except in compliance with the License. You may obtain a copy of the License at http://displaytag.sourceforge.net/license.html THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gov.doc.isu.dwarf.taglib.displaytag.pagination;

import gov.doc.isu.dwarf.taglib.displaytag.properties.TableProperties;
import gov.doc.isu.dwarf.taglib.displaytag.util.Href;
import gov.doc.isu.dwarf.taglib.displaytag.util.TagConstants;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;

/**
 * Helper class for generation of paging banners.
 * 
 * @author Fabrizio Giustina
 * @author Steven Skinner JCCC
 */
public class Pagination {

    /**
     * logger.
     */
    private static Logger log = Logger.getLogger(Pagination.class);

    private static final String DV_HREF_SUBMIT_LIST_PAGE_DATA_START = "javascript:submitListPageData('";
    private static final String DV_HREF_SUBMIT_LIST_PAGE_DATA_END = "');";
    private static final String DV_HREF_SUBMIT_LIST_PAGE_DATA_METHOD_NAME = "method=updateListPageData";
    private static final String DV_HREF_JS_METHOD_PLACE_HOLDER = "&amp;jsmethod=buildPathWithParams";

    /**
     * Base href for urls.
     */
    private Href href;

    /**
     * page parameter name.
     */
    private String pageParam;

    /**
     * first page.
     */
    private Integer firstPage;

    /**
     * last page.
     */
    private Integer lastPage;

    /**
     * previous page.
     */
    private Integer previousPage;

    /**
     * next page.
     */
    private Integer nextPage;

    /**
     * current page.
     */
    private Integer currentPage;

    /**
     * List containg NumberedPage objects.
     * 
     * @see gov.doc.isu.dwarf.taglib.displaytag.pagination.NumberedPage
     */
    private List pages = new ArrayList();

    /**
     * Table properties, needed fot locale.
     */
    private TableProperties properties;

    /**
     * Constructor for Pagination.
     * 
     * @param baseHref
     *        Href used for links
     * @param pageParameter
     *        name for the page parameter
     */
    public Pagination(Href baseHref, String pageParameter, TableProperties properties) {
        this.href = baseHref;
        this.pageParam = pageParameter;
        this.properties = properties;
    }

    /**
     * Adds a page.
     * 
     * @param number
     *        int page number
     * @param isSelected
     *        is the page selected?
     */
    public void addPage(int number, boolean isSelected) {
        if(log.isDebugEnabled()){
            log.debug("adding page " + number);
        }
        this.pages.add(new NumberedPage(number, isSelected));
    }

    /**
     * first page selected?
     * 
     * @return boolean
     */
    public boolean isFirst() {
        return this.firstPage == null;
    }

    /**
     * last page selected?
     * 
     * @return boolean
     */
    public boolean isLast() {
        return this.lastPage == null;
    }

    /**
     * only one page?
     * 
     * @return boolean
     */
    public boolean isOnePage() {
        return (this.pages == null) || this.pages.size() <= 1;
    }

    /**
     * Gets the number of the first page.
     * 
     * @return Integer number of the first page
     */
    public Integer getFirst() {
        return this.firstPage;
    }

    /**
     * Sets the number of the first page.
     * 
     * @param first
     *        Integer number of the first page
     */
    public void setFirst(Integer first) {
        this.firstPage = first;
    }

    /**
     * Gets the number of the last page.
     * 
     * @return Integer number of the last page
     */
    public Integer getLast() {
        return this.lastPage;
    }

    /**
     * Sets the number of the last page.
     * 
     * @param last
     *        Integer number of the last page
     */
    public void setLast(Integer last) {
        this.lastPage = last;
    }

    /**
     * Gets the number of the previous page.
     * 
     * @return Integer number of the previous page
     */
    public Integer getPrevious() {
        return this.previousPage;
    }

    /**
     * Sets the number of the previous page.
     * 
     * @param previous
     *        Integer number of the previous page
     */
    public void setPrevious(Integer previous) {
        this.previousPage = previous;
    }

    /**
     * Gets the number of the next page.
     * 
     * @return Integer number of the next page
     */
    public Integer getNext() {
        return this.nextPage;
    }

    /**
     * Sets the number of the next page.
     * 
     * @param next
     *        Integer number of the next page
     */
    public void setNext(Integer next) {
        this.nextPage = next;
    }

    /**
     * Sets the number of the current page.
     * 
     * @param current
     *        number of the current page
     */
    public void setCurrent(Integer current) {
        this.currentPage = current;
    }

    /**
     * Returns the appropriate banner for the pagination.
     * 
     * @param numberedPageFormat
     *        String to be used for a not selected page
     * @param numberedPageSelectedFormat
     *        String to be used for a selected page
     * @param numberedPageSeparator
     *        separator beetween pages
     * @param fullBanner
     *        String basic banner
     * @return String formatted banner whith pages
     */
    public String getFormattedBanner(String numberedPageFormat, String numberedPageSelectedFormat, String numberedPageSeparator, String fullBanner) {
        StringBuffer buffer = new StringBuffer(100);

        // numbered page list
        Iterator pageIterator = this.pages.iterator();

        while(pageIterator.hasNext()){

            // get NumberedPage from iterator
            NumberedPage page = (NumberedPage) pageIterator.next();

            Integer pageNumber = new Integer(page.getNumber());

            String urlString = ((Href) this.href.clone()).addParameter(this.pageParam, pageNumber).toString();

            // Check if urlString contains method=updateLastPageData and then update.
            urlString = checkAndUpdateUrl(urlString);

            // needed for MessageFormat : page number/url
            Object[] pageObjects = {pageNumber, urlString};

            // selected page need a different formatter
            if(page.getSelected()){
                buffer.append(new MessageFormat(numberedPageSelectedFormat, properties.getLocale()).format(pageObjects));
            }else{
                buffer.append(new MessageFormat(numberedPageFormat, properties.getLocale()).format(pageObjects));
            }

            // next? add page separator
            if(pageIterator.hasNext()){
                buffer.append(numberedPageSeparator);
            }
        }

        // String for numbered pages
        String numberedPageString = buffer.toString();
        // Object array
        // {0} full String for numbered pages
        // {1} first page url
        // {2} previous page url
        // {3} next page url
        // {4} last page url
        // {5} current page
        // {6} total pages
        Object[] pageObjects = {numberedPageString, checkAndUpdateUrl(((Href) this.href.clone()).addParameter(this.pageParam, getFirst()).toString()), checkAndUpdateUrl(((Href) this.href.clone()).addParameter(this.pageParam, getPrevious()).toString()), checkAndUpdateUrl(((Href) this.href.clone()).addParameter(this.pageParam, getNext()).toString()), checkAndUpdateUrl(((Href) this.href.clone()).addParameter(this.pageParam, getLast()).toString()), this.currentPage, this.isLast() ? this.currentPage : this.lastPage}; // this.lastPage is null if the last page is displayed

        // return the full banner
        return MessageFormat.format(fullBanner, pageObjects);
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
        }else if(StringUtils.contains(url, DV_HREF_JS_METHOD_PLACE_HOLDER)){
            StringBuffer buffer = new StringBuffer(30);
            url = url.replace(DV_HREF_JS_METHOD_PLACE_HOLDER, "");
            buffer.append(url);

            if(this.properties.getAddParams() != null && this.properties.getAddParams().size() > 0){
                Set<?> parameterSet = this.properties.getAddParams().entrySet();

                Iterator<?> iterator = parameterSet.iterator();

                while(iterator.hasNext()){
                    Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iterator.next();

                    Object key = entry.getKey();
                    Object value = entry.getValue();

                    buffer.append(TagConstants.AMPERSAND);

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

                }
            }
            url = buffer.toString();
        }
        return url;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE) //
                .append("firstPage", this.firstPage) //$NON-NLS-1$
                .append("lastPage", this.lastPage) //$NON-NLS-1$
                .append("currentPage", this.currentPage) //$NON-NLS-1$
                .append("nextPage", this.nextPage) //$NON-NLS-1$
                .append("previousPage", this.previousPage) //$NON-NLS-1$
                .append("pages", this.pages) //$NON-NLS-1$
                .append("href", this.href) //$NON-NLS-1$
                .append("pageParam", this.pageParam) //$NON-NLS-1$
                .toString();
    }
}
