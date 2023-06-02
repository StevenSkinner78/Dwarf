package gov.doc.isu.dwarf.util;

import static gov.doc.isu.dwarf.resources.Constants.DEFAULTNODESC;
import static gov.doc.isu.dwarf.resources.Constants.DEFAULTNOIND;
import static gov.doc.isu.dwarf.resources.Constants.DEFAULTYESDESC;
import static gov.doc.isu.dwarf.resources.Constants.DEFAULTYESIND;
import static gov.doc.isu.dwarf.resources.Constants.DEFAULT_DATE;
import static gov.doc.isu.dwarf.resources.Constants.DEFAULT_DATE_TIME;
import static gov.doc.isu.dwarf.resources.Constants.DEFAULT_DROPDOWN_LABEL;
import static gov.doc.isu.dwarf.resources.Constants.DEFAULT_SQL_DATE_AS_DATE;
import static gov.doc.isu.dwarf.resources.Constants.DEFAULT_TIME;
import static gov.doc.isu.dwarf.resources.Constants.EMPTY_SPACE;
import static gov.doc.isu.dwarf.resources.Constants.EMPTY_STRING;
import static gov.doc.isu.dwarf.resources.Constants.HYPHEN;
import static gov.doc.isu.dwarf.resources.Constants.KEY;
import static gov.doc.isu.dwarf.resources.Constants.PERCENTAGE;
import static gov.doc.isu.dwarf.resources.Constants.UNDER_SCORE;
import static gov.doc.isu.dwarf.resources.Constants.VAL;
import static gov.doc.isu.dwarf.taglib.displaytag.util.TagConstants.DATE_SORT;
import static gov.doc.isu.dwarf.taglib.displaytag.util.TagConstants.DATE_TIME_SORT;
import gov.doc.isu.dwarf.model.ColumnModel;
import gov.doc.isu.dwarf.model.CommonModel;

import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Utility class for Dwarf Framework.
 * 
 * @author Steven Skinner JCCC
 */
public class AppUtil {
    private static final Logger LOG = Logger.getLogger("gov.doc.isu.dwarf.util.AppUtil");
    private static final String NO_RECORDS_DISPLAY_STRING = "No Records found for your search string.";
    private static final String NO_RECORDS_FOUND_DISPLAY_STRING = "No Records found to display.";
    private static final String SDF = "MM/dd/yyyy";
    private static final String STF = "HH:mm";
    private static final String SDFT = "MM/dd/yyyy HH:mm";
    private static final String SDFT3 = "MM/dd/yyyy h:mm a";
    private static final String FORWARD_SLASH = "/";

    /**
     * Replaces all newlines (\n) or CR-newlines (\r\n) with html <br>
     * 's
     * 
     * @param inString
     *        inString
     * @return String
     */
    public static String htmlBreak2TextBreak(String inString) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil.htmlBreak2TextBreak.");
        LOG.debug("Parameters are: inString=" + String.valueOf(inString));
        inString = swapStrings(inString, "<br/>", "\r\n");
        inString = swapStrings(inString, "<br/>", "\n");
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil.htmlBreak2TextBreak. result=" + String.valueOf(inString));
        return inString;
    }// end method

    /**
     * This method checks if string array is deafult value or if the first element of a 1 element array is <code>DEFAULT_DROPDOWN_LABEL</code>
     * 
     * @param array
     *        the string array to check
     * @return boolean true|false
     */
    public static boolean checkForNotDefaultValue(String[] array) {
        boolean result = true;

        if(!StringUtil.isNullOrEmptyStringArray(array)){
            if(array.length == 1){
                if(array[0] == null || DEFAULT_DROPDOWN_LABEL.equalsIgnoreCase(array[0])){
                    result = false;
                }
            }
            if(array.length == 100 && array[0] == null){
                result = false;
            }
        }
        return result;
    }

    /**
     * Replaces what it finds in the string with something else.
     * 
     * @param inString
     *        inString
     * @param from
     *        from this
     * @param to
     *        to this
     * @return String
     */
    public static String swapStrings(String inString, String from, String to) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil.swapStrings.");
        LOG.debug("Parameters are: inString=" + String.valueOf(inString) + ", from=" + String.valueOf(from) + ", to=" + String.valueOf(to));
        if(inString != null){
            int index = -1;
            index = inString.indexOf(from);
            while(index != -1){
                inString = inString.substring(0, index) + to + inString.substring(index + from.length());
                index = inString.indexOf(from, index + to.length());
            }// end while
        }// end if
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil.swapStrings. result=" + String.valueOf(inString));
        return inString;
    }// end method

    /**
     * Gets the current java.sql.Timestamp
     * 
     * @return java.sql.Timestamp
     */
    public static java.sql.Timestamp getSQLTimestamp() {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getSQLTimestamp.");
        return new java.sql.Timestamp(new java.util.Date().getTime());
    }// end method

    /**
     * Formats the Timestamp, but removes the seconds to match MM/dd/yyyy h:mm a
     * 
     * @return either the formatted Timestamp or null
     */
    public static String getCurrentFormattedDate12HrsTime() {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getFormattedDate12HrsTimeAsString().");
        String result = new SimpleDateFormat(SDFT3).format(System.currentTimeMillis());
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getFormattedDate12HrsTimeAsString(). result=" + String.valueOf(result));
        return result;
    }// end method

    /**
     * Returns the String representation of input date in MM/dd/yyyy format. Returns empty string if date is null.
     * 
     * @param date
     *        java.sql.Date
     * @return date as string
     */
    public static String getDateAsString(final java.sql.Date date) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getDateAsString");
        LOG.debug("Parameters: date=" + (date == null ? "null" : String.valueOf(date)));
        String result = EMPTY_STRING;
        if(date != null && !DEFAULT_SQL_DATE_AS_DATE.equals(date)){
            result = new SimpleDateFormat(SDF).format(date);
        }// end if
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getDateAsString");
        return result;
    }// end methos

    /**
     * Returns the String representation of input date in MM/dd/yyyy format. Returns empty string if date is null.
     * 
     * @param date
     *        java.sql.Date
     * @return date as string
     * @throws ParseException
     */
    public static String getDateAsString(final Date date) throws ParseException {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getDateAsString");
        LOG.debug("Parameters: date=" + (date == null ? "null" : String.valueOf(date)));
        String result = EMPTY_STRING;
        Date dte5 = new Date();
        Date fg = new SimpleDateFormat(SDF).parse("2015-05-13");
        if(!dte5.equals(fg)){

        }// end if
        if(date != null && !getDate(DEFAULT_DATE).equals(date)){
            result = new SimpleDateFormat(SDF).format(date);
        }// end if
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getDateAsString");
        return result;
    }// end method

    /**
     * Parses input date in "MM/dd/yyyy HH:mm" format.
     * 
     * @param dateWithTime
     *        dateWithTime
     * @return Date object for the input date
     */
    public static Date getDateWithTime(String dateWithTime) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getDateWithTime");
        LOG.debug("Parameters: dateWithTime=" + (StringUtil.isNullOrEmpty(dateWithTime) ? "null" : String.valueOf(dateWithTime)));
        Date result = null;
        if(dateWithTime != null && dateWithTime.trim().length() > 0){
            if(dateWithTime.equals(DEFAULT_DATE)){
                dateWithTime = DEFAULT_DATE_TIME;
            }else if(dateWithTime.trim().length() == 10){
                StringBuffer sb = new StringBuffer();
                sb.append(dateWithTime).append(EMPTY_SPACE).append(DEFAULT_TIME);
                dateWithTime = sb.toString();
            }else if(dateWithTime.trim().length() < 10){
                StringBuffer sb = new StringBuffer();
                sb.append(DEFAULT_DATE).append(EMPTY_SPACE).append(getSQLTimeAsString(dateWithTime));
                dateWithTime = sb.toString();
            }// end else
            try{
                result = new SimpleDateFormat(SDFT).parse(dateWithTime);
            }catch(ParseException e){
                LOG.error("getDateWithTime - Exception parsing date: " + dateWithTime);
            }// end try
        }// end if

        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getDateWithTime result=" + String.valueOf(result));
        return result;
    }// end method

    /**
     * Converts the hh:mm a to HH:MM
     *
     * @param v
     *        timesString
     * @return String
     */
    public static String getSQLTimeAsString(String v) {
        LOG.debug("Entering getSQLTimeAsString");
        LOG.trace("Converts the hh:mm a to HH:MM");
        LOG.debug("Entry parameters are: v=" + String.valueOf(v));
        String result = String.valueOf(getSQLTime(v));
        result = StringUtil.blankNull(result);
        LOG.debug("Return value is: result=" + String.valueOf(result));
        LOG.debug("Exiting getSQLTimeAsString");
        return result;
    }// end method

    /**
     * Converts the hh:mm a Time value to java.sql.Time
     *
     * @param v
     *        timesString
     * @return java.sql.Time
     */
    public static Time getSQLTime(String v) {
        LOG.debug("Entering getSQLTime");
        LOG.trace("Converts the hh:mm a Time value to java.sql.Time");
        LOG.debug("Entry parameters are: v=" + String.valueOf(v));
        Time result = null;
        if((v != null) && !v.equals("") && (v.indexOf(":") != -1)){
            StringBuffer sb = new StringBuffer();
            if(v.indexOf("p") != -1){
                int hold = Integer.valueOf(v.substring(0, v.indexOf(":")));
                hold = hold + 12;
                sb.append(hold).append(":").append(v.substring(v.indexOf(":") + 1, v.indexOf(":") + 3));
                v = sb.toString();
            }else if(v.indexOf("a") != -1 && v.substring(0, 2).equalsIgnoreCase("12")){
                sb.append("00").append(":").append(v.substring(v.indexOf(":") + 1, v.indexOf(":") + 3));
                v = sb.toString();
            }// end if/else
            try{
                result = new Time(new SimpleDateFormat(STF).parse(v.trim()).getTime());
            }catch(ParseException e){
                LOG.error("Input String cannot be parsed in to time value.. Returning null");
            }// end catch
        }// end if
        LOG.debug("Return value is: result=" + String.valueOf(result));
        LOG.debug("Exiting getSQLTime");
        return result;
    }// end method

    /**
     * Gets the current date in sql format
     * 
     * @return java.sql.Date
     */
    public static java.sql.Date getSQLDate() {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getSQLDate");
        return new java.sql.Date(System.currentTimeMillis());
    }

    /**
     * Converts string from MM/dd/yyyy to yyyy-MM-dd. Returns null in case of a parse exception.
     * 
     * @param dateStr
     *        dateStr
     * @return java.sql.Date
     */
    public static java.sql.Date getSQLDate(final String dateStr) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getSQLDate");
        LOG.debug("Parameters: dateStr=" + (StringUtil.isNullOrEmpty(dateStr) ? "null" : String.valueOf(dateStr)));
        java.util.Date utilDate = null;
        java.sql.Date result = null;
        try{
            if(dateStr == null || dateStr.equals(EMPTY_STRING)){
                result = DEFAULT_SQL_DATE_AS_DATE;
            }else{
                utilDate = new SimpleDateFormat(SDF).parse(dateStr);
                result = new java.sql.Date(utilDate.getTime());
            }// end if
        }catch(final ParseException e){
            LOG.error("Exception while parsing " + dateStr);
        }// end catch
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getSQLDate result=" + String.valueOf(result));
        return result;
    }

    /**
     * Get the current date
     * 
     * @return date
     */
    public static String getDate() {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getDate");
        return new SimpleDateFormat(SDF).format(new Date());
    }

    /**
     * Parses input date in "MM/dd/yyyy" format.
     * 
     * @param date
     *        date
     * @return Date object for the input date
     */
    public static Date getDate(String date) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getDate");
        LOG.debug("Parameters: date=" + (StringUtil.isNullOrEmpty(date) ? "null" : String.valueOf(date)));
        Date result = null;
        if(date != null && date.trim().length() > 0){
            try{
                result = new SimpleDateFormat(SDF).parse(date);
            }catch(ParseException e){
                LOG.error("getDate- Exception parsing date: " + date);

            }
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getDate result=" + String.valueOf(result));
        return result;
    }

    /**
     * <p>
     * This method will return the system timestamp as a custom formatted string
     * </p>
     * <b>Pattern: </b><code>[MM/dd/yyyy hh:mm:ss.SSS]</code>
     * 
     * @return date - custom formatted system timestamp string
     */
    public static String getSystemTimestampString() {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getSystemTimestampString");
        SimpleDateFormat sd = new SimpleDateFormat("[MM/dd/yyyy hh:mm:ss.SSS]");
        String date = "";
        try{
            Date dt = new Date();
            date = sd.format(dt);
        }catch(Exception e){
            LOG.error("Exception getting system timestamp as a string: " + e.getMessage());
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getSystemTimestampString");
        return date;
    }

    /**
     * Converts single letter indicator to whole indicator description string. Returns null if input indicator is null.
     * 
     * @param ind
     *        ind
     * @return <String>description of indicator<String>
     */
    public static String getYesNoIndicatorDescription(final String ind) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getYesNoIndicatorDescription");
        LOG.debug("Parameters: ind=" + (StringUtil.isNullOrEmpty(ind) ? "null" : String.valueOf(ind)));
        String desc = StringUtil.trim(ind);
        if(DEFAULTYESIND.equalsIgnoreCase(ind)){
            desc = DEFAULTYESDESC;
        }else if(DEFAULTNOIND.equalsIgnoreCase(ind)){
            desc = DEFAULTNODESC;
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getDate result=" + String.valueOf(desc));
        return desc;
    }

    /**
     * This method converts and replaces "_" with " "(empty space).
     * 
     * @param columnName
     *        column name
     * @return string
     */
    public static String getFormattedColumnNameWithOutUnderScore(final String columnName) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getFormattedColumnNameWithOutUnderScore");
        LOG.debug("Parameters: columnName=" + (columnName == null ? "null" : String.valueOf(columnName)));
        String returnString = columnName.trim();
        if(returnString.indexOf(UNDER_SCORE) > 0){
            returnString = columnName.replaceAll(UNDER_SCORE, EMPTY_SPACE);
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getFormattedColumnNameWithOutUnderScore result=" + String.valueOf(returnString));
        return returnString;
    }

    /**
     * This method converts the string representation of a column name without "_" and adds "_" to empty spaces.
     * 
     * @param columnName
     *        the unformatted column name
     * @return columnName the formatted column name
     */
    public static String getFormattedColumnNameWithUnderScore(final String columnName) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getFormattedColumnNameWithUnderScore");
        LOG.debug("Parameters: columnName=" + (StringUtil.isNullOrEmpty(columnName) ? "null" : String.valueOf(columnName)));
        String returnString = columnName.trim();
        if(returnString.indexOf(EMPTY_SPACE) > 0){
            returnString = columnName.replaceAll(EMPTY_SPACE, UNDER_SCORE);
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getFormattedColumnNameWithUnderScore result=" + String.valueOf(returnString));
        return returnString;
    }

    /**
     * This method sets the columnData list on the CommonModel based on the list of fields passed in.
     * 
     * @param fields
     *        list of string fields
     * @return returnObject CommonModel
     */
    public static CommonModel setFilterObject(final ArrayList<String> fields) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method setFilterObject");
        final CommonModel returnObject = new CommonModel();
        returnObject.setColumnData(new ArrayList<ColumnModel>());
        for(int i = 0, j = fields.size();i < j;i++){
            final ColumnModel col = new ColumnModel();
            col.put(KEY, fields.get(i));
            col.put(VAL, EMPTY_STRING);
            returnObject.getColumnData().add(col);
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method setFilterObject");
        return returnObject;
    }

    /**
     * This method compares the filter object list to the object list. Returns true of they match.
     * 
     * @param filterObj
     *        filterObj
     * @param objList
     *        objList
     * @return <boolean> true if match
     * @throws ParseException
     *         ParseException
     */
    public static boolean filter(final List<ColumnModel> filterObj, final List<ColumnModel> objList) throws ParseException {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method filter");
        LOG.debug("Parameters: filterObj=" + (filterObj == null ? "null" : filterObj.toString()) + " ,objList=" + (objList == null ? "null" : objList.toString()));
        boolean matches = false;
        String fldName, filterFldName, fldType;
        Object fldValue, filterFldValue;
        boolean isSelect = false;
        boolean multiple = false;
        for(int i = 0;i < filterObj.size();i++){
            filterFldName = filterObj.get(i).get(KEY).toString();
            fldName = objList.get(i).get(KEY).toString();
            isSelect = objList.get(i).get("isSelect") != null && Boolean.valueOf(objList.get(i).get("isSelect").toString()) ? true : false;
            multiple = objList.get(i).get("multiple") != null && !StringUtil.isNullOrEmpty(objList.get(i).get("multiple").toString()) ? true : false;
            if(filterFldName.trim().equalsIgnoreCase(fldName.trim())){
                LOG.debug("Field Name to check = " + filterFldName);
                if(!multiple){
                    filterFldValue = (String) filterObj.get(i).get(VAL);
                }else{
                    filterFldValue = (String[]) filterObj.get(i).get(VAL);
                }
                LOG.debug("Value of field in search object = " + filterFldValue);
                if((multiple && (AppUtil.checkForNotDefaultValue((String[]) filterFldValue)) && !StringUtil.isNullOrEmptyStringArray((String[]) filterFldValue)) || (!multiple && !StringUtil.isNullOrEmpty((String) filterFldValue)) && (!multiple && !"...".equalsIgnoreCase((String) filterFldValue))){
                    fldType = objList.get(i).getType();
                    fldValue = String.valueOf(objList.get(i).get(VAL));
                    if(fldName.endsWith("Phone") && ((String) filterFldValue).length() == 13){
                        fldValue = StringUtil.phoneToDB((String) fldValue);
                        filterFldValue = StringUtil.phoneToDB((String) filterFldValue);
                    }
                    LOG.debug("Value of field to compare = " + fldValue);
                    if(DATE_SORT.equals(fldType) || DATE_TIME_SORT.equals(fldType)){
                        // compare field value against filter field value
                        if(!StringUtil.isNullOrEmpty((String) fldValue) && compareDates((String) fldValue, (String) filterFldValue)){
                            matches = true;
                        }else{
                            return false;
                        }
                    }else if(isSelect){
                        if(multiple){
                            if(!StringUtil.isNullOrEmpty((String) fldValue) && StringUtil.arrayContains((String) fldValue, (String[]) filterFldValue)){
                                matches = true;
                            }else{
                                return false;
                            }
                        }else{
                            if(((String) fldValue).equalsIgnoreCase((String) filterFldValue)){
                                matches = true;
                            }else{
                                return false;
                            }
                        }
                    }else if(((String) fldValue).toLowerCase().trim().contains(((String) filterFldValue).toLowerCase().trim())){
                        matches = true;
                    }else{
                        return false;
                    }// end if/else
                }else{
                    LOG.debug("Search field value is empty");
                }// end else
            }// end if
        }// end for
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method filter");
        return matches;
    }

    /**
     * Used to compare two String dates to see if the month, year, month/year, or month/day/year values match. This method parsed the String objects to retrieve dates for comparison.
     * 
     * @param value1
     *        The value to be matched.
     * @param value2
     *        User input trying to match.
     * @return true if values match; false otherwise
     */
    public static boolean compareDates(String value1, String value2) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method compareDates. Incoming parameters: value1=" + String.valueOf(value1) + ", value2=" + String.valueOf(value2));
        boolean matches = false;
        if(value1 == null || value2 == null){
            LOG.debug("Incoming parameter value is null. Returning false.");
            return matches;
        }// end if
        try{
            SimpleDateFormat format = null;
            ParsePosition pos = new ParsePosition(0);
            Calendar fldTime = Calendar.getInstance(), filterFldTime = Calendar.getInstance();
            LOG.debug("Running switch statement for value: " + value2.length());
            switch(value2.length()){
                case 2:// check MM format
                    LOG.debug("Parsing value " + String.valueOf(value1) + " to month format.");
                    format = new SimpleDateFormat("MM");
                    fldTime.setTimeInMillis(format.parse(value1, pos).getTime());
                    filterFldTime.setTimeInMillis(format.parse(value2).getTime());
                    matches = (fldTime.get(Calendar.MONTH) == filterFldTime.get(Calendar.MONTH));
                    LOG.debug("Month values " + ((!matches) ? "do not match." : "match"));
                    break;
                case 4:// check CCYY format
                    LOG.debug("Parsing value " + String.valueOf(value1) + " to year format.");
                    format = new SimpleDateFormat("yyyy");
                    pos.setIndex(6);
                    fldTime.setTimeInMillis(format.parse(value1, pos).getTime());
                    filterFldTime.setTimeInMillis(format.parse(value2).getTime());
                    matches = (fldTime.get(Calendar.YEAR) == filterFldTime.get(Calendar.YEAR));
                    LOG.debug("Year values " + ((!matches) ? "do not match." : "match"));
                    break;
                case 7:// check MM/CCYY format
                    LOG.debug("Parsing value " + String.valueOf(value1) + " to month/year format.");
                    format = new SimpleDateFormat("MM/yyyy");
                    // substring the month/year from the value (day interferes with comparison)
                    String val = value1.substring(0, value1.indexOf(FORWARD_SLASH) + 1) + value1.substring(value1.lastIndexOf(FORWARD_SLASH) + 1);
                    fldTime.setTimeInMillis(format.parse(val, pos).getTime());
                    filterFldTime.setTimeInMillis(format.parse(value2).getTime());
                    matches = (fldTime.get(Calendar.MONTH) == filterFldTime.get(Calendar.MONTH)) && (fldTime.get(Calendar.YEAR) == filterFldTime.get(Calendar.YEAR));
                    LOG.debug("Month/year values " + ((!matches) ? "do not match." : "match"));
                    break;
                case 10:// check MM/DD/CCYY format
                    LOG.debug("Parsing value " + String.valueOf(value1) + " to month/day/year format.");
                    format = new SimpleDateFormat("MM/dd/yyyy");
                    fldTime.setTimeInMillis(format.parse(value1, pos).getTime());
                    filterFldTime.setTimeInMillis(format.parse(value2).getTime());
                    matches = (fldTime.get(Calendar.MONTH) == filterFldTime.get(Calendar.MONTH)) && (fldTime.get(Calendar.DAY_OF_MONTH) == filterFldTime.get(Calendar.DAY_OF_MONTH)) && (fldTime.get(Calendar.YEAR) == filterFldTime.get(Calendar.YEAR));
                    LOG.debug("Month/day/year values " + ((!matches) ? "do not match." : "match"));
                    break;
                default:
                    matches = false;
            }// end switch
        }catch(ParseException e){
            LOG.error("ParseException while comparing dates. Compare values are: value=" + String.valueOf(value1) + ", filter value=" + String.valueOf(value2));
        }// end try/catch
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method compareDates. Return value is: " + String.valueOf(matches));
        return matches;
    }// end compareDates

    /**
     * This method replaces the wild-character with percentage.
     * 
     * @param searchString
     *        Search String.
     * @return modified Search string.
     */
    public static String replaceWildChar(String searchString) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method replaceWildChar");
        LOG.debug("Parameters: searchString=" + (StringUtil.isNullOrEmpty(searchString) ? "null" : String.valueOf(searchString)));
        String result = searchString;
        if(!StringUtil.isNullOrEmpty(searchString)){
            StringBuilder sb = new StringBuilder();
            int len = searchString.length();
            if("*".equals(searchString)){
                sb.append(EMPTY_STRING);
            }else if('*' == searchString.charAt(0) && '*' == searchString.charAt(len - 1)){ // * at both ends
                sb.append(PERCENTAGE).append(searchString.substring(1, searchString.length() - 1)).append(PERCENTAGE);
            }else if('*' == searchString.charAt(0) && '*' != searchString.charAt(len - 1)){ // * only in
                sb.append(PERCENTAGE).append(searchString.substring(1, searchString.length()));
            }else if('*' != searchString.charAt(0) && '*' == searchString.charAt(len - 1)){ // * only at the
                sb.append(searchString.substring(0, searchString.length() - 1)).append(PERCENTAGE);
            }else{ // No * at all. We are not handling for * in middle of the
                   // search string.
                sb.append(searchString);
            }
            result = sb.toString().trim().toUpperCase();
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method replaceWildChar result=" + String.valueOf(result));
        return result;
    }

    /**
     * This method builds a blank object to display in data table. This is a temporary fix to keep the filter object input boxes in table when no records match.
     * 
     * @param fieldNames
     *        fieldNames
     * @return <List>columnVO
     */
    public static List<ColumnModel> getNoRecordsFound(String[] fieldNames) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getNoRecordsFound");
        List<ColumnModel> f = new ArrayList<ColumnModel>();
        for(int x = 0, j = fieldNames.length;x < j;x++){
            if(x < fieldNames.length){
                ColumnModel fd = new ColumnModel();
                fd.put(KEY, fieldNames[x]);
                if(x == 0){
                    fd.put(VAL, NO_RECORDS_DISPLAY_STRING);
                }else{
                    fd.put(VAL, EMPTY_STRING);
                }
                f.add(fd);
            }
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getNoRecordsFound");
        return f;
    }

    /**
     * This method builds a blank object to display in data table.
     * 
     * @param fieldNames
     *        fieldNames
     * @return <List>columnVO
     */
    public static List<ColumnModel> getNoRecordsFoundToDisplay(String[] fieldNames) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getNoRecordsFoundToDisplay");
        List<ColumnModel> f = new ArrayList<ColumnModel>();
        for(int x = 0, j = fieldNames.length;x < j;x++){
            if(x < fieldNames.length){
                ColumnModel fd = new ColumnModel();
                fd.put(KEY, fieldNames[x]);
                if(x == 0){
                    fd.put(VAL, NO_RECORDS_FOUND_DISPLAY_STRING);
                }else{
                    fd.put(VAL, EMPTY_STRING);
                }
                f.add(fd);
            }
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getNoRecordsFoundToDisplay");
        return f;
    }

    /**
     * This method builds a blank object to display in data table. This is a temporary fix to keep the filter object input boxes in table when no records match for Authorized Users.
     * 
     * @return <List>columnVO
     */
    public static List<ColumnModel> getAuthUserNoRecordsFound() {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getAuthUserNoRecordsFound");
        List<ColumnModel> f = new ArrayList<ColumnModel>();
        ColumnModel fd = new ColumnModel();
        fd.put(KEY, "Last Name");
        fd.put(VAL, NO_RECORDS_DISPLAY_STRING);
        f.add(fd);
        fd = new ColumnModel();
        fd.put(KEY, "First Name");
        fd.put(VAL, EMPTY_STRING);
        f.add(fd);
        fd = new ColumnModel();
        fd.put(KEY, "User Id");
        fd.put(VAL, EMPTY_STRING);
        f.add(fd);
        fd = new ColumnModel();
        fd.put(KEY, "User Authority");
        fd.put(VAL, EMPTY_STRING);
        f.add(fd);
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getAuthUserNoRecordsFound");
        return f;
    }

    /**
     * This method builds the cookie string with "-" seperating column names. If column name is more that one word then a "_" is added in between names. This method is needed because cookie values cant have "," nor empty spaces.
     * 
     * @param selectedColumns
     *        selectedColumns
     * @return dash seperated string
     */
    public static String buildCookieString(String[] selectedColumns) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method buildCookieString");
        String[] holdArray = new String[selectedColumns.length];

        for(int x = 0;x < selectedColumns.length;x++){
            holdArray[x] = getFormattedColumnNameWithUnderScore(selectedColumns[x]);
        }
        String result = StringUtil.flattenArray(holdArray, HYPHEN);
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method buildCookieString");
        return result;
    }

    /**
     * this method returns an array of strings without the under score in name for display.
     * 
     * @param selected
     *        selected
     * @return array of strings
     */
    public static String[] formatArrayFromCookieForDisplay(String[] selected) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method formatArrayFromCookieForDisplay");
        String[] returnArray = new String[selected.length];

        for(int x = 0;x < selected.length;x++){
            returnArray[x] = getFormattedColumnNameWithOutUnderScore(selected[x]);
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method formatArrayFromCookieForDisplay");
        return returnArray;
    }

    /**
     * To check for null or zero long
     * 
     * @param value
     *        value
     * @return boolean
     */
    public static boolean isNotNullAndZero(Long value) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method isNotNullAndZero");
        return (value != null && value > 0L) ? true : false;
    }

    /**
     * To check for null or empty collection
     * 
     * @param col
     *        col
     * @return boolean
     */
    public static boolean isEmpty(Collection<?> col) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method isEmpty()");
        boolean result = col == null || col.size() == 0 ? true : false;
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method isEmpty() result=" + String.valueOf(result));
        return result;
    }

    /**
     * To check for null or empty Map
     * 
     * @param col
     *        col
     * @return boolean
     */
    public static boolean isEmpty(Map<?, ?> col) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method isEmpty()");
        return col == null || col.size() == 0 ? true : false;
    }

    /**
     * Convert string to integer
     * 
     * @param inString
     *        the string to convert
     * @return parsed int of inString
     */
    public static Integer stringToInt(String inString) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method stringToInt.");
        LOG.debug("Parameters: inString=" + (StringUtil.isNullOrEmpty(inString) ? "null" : String.valueOf(inString)));
        Integer returnInt = 0;
        if(!StringUtil.isNullOrEmpty(inString)){
            returnInt = Integer.parseInt(inString);
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method stringToInt. result=" + String.valueOf(returnInt));
        return returnInt;
    }

    /**
     * Convert integer to string
     * 
     * @param inInt
     *        the integer to covert
     * @return string value of inInt
     */
    public static String intToString(int inInt) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method intToString.");
        LOG.debug("Parameters: inInt=" + String.valueOf(inInt));
        String returnString = EMPTY_STRING;
        if(isNotNullAndZero(Long.valueOf(inInt))){
            returnString = String.valueOf(inInt);
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method intToString. returnString=" + String.valueOf(returnString));
        return returnString;
    }

    /**
     * @param inLong
     *        inLong
     * @return integer
     */
    public static Integer longToInt(Long inLong) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method longToInt.");
        LOG.debug("Parameters: inLong=" + String.valueOf(inLong));
        int result = 0;
        if(!StringUtil.isNullOrEmpty(inLong.toString())){
            result = Integer.parseInt(inLong.toString());
        }
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method longToInt. result=" + String.valueOf(result));
        return result;
    }

    /**
     * Checks to see that value is between min and max
     * 
     * @param value
     *        Value to check
     * @param min
     *        Minimum value
     * @param max
     *        Maximun value
     * @return true if value >= min and value <= max; false otherwise
     */
    public static boolean validateRange(int value, int min, int max) {
        return (value >= min && value <= max);
    }// end validateRange

    /**
     * Validate that the input value is of type java.util.Date Method follows the MM/dd/yyyy pattern for validation
     * 
     * @param value
     *        String object to check
     * @return true if java.util.Date; false otherwise
     */
    public static boolean validateDate(String value) {
        boolean isValid = false;
        SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
        sf.setLenient(false);
        try{
            sf.parse(value);
            isValid = true;
        }catch(Exception e){
            isValid = false;
        }// try/catch
        return isValid;
    }// validateDate

    /**
     * Validate that the input for Integer field is valid and within the range.
     * 
     * @param value
     *        String object to check
     * @param min
     *        Minimum value
     * @param max
     *        Maximun value
     * @return true if valid value; false otherwise
     */
    public static boolean validateInt(String value, int min, int max) {
        if(value == null){
            return false;
        }// end if
        if(!validateInt(value)){
            return false;
        }// end if
        int n = Integer.valueOf(value).intValue();
        return validateRange(n, min, max);
    }// validateInt

    /**
     * Validate that the input for year is valid.
     * 
     * @param value
     *        String object to check
     * @return true if valid year; false otherwise
     */
    public static boolean validateYear(String value) {
        if(value == null){
            return false;
        }// end if
        if(!validateInt(value)){
            return false;
        }// end if
        int n = Integer.valueOf(value).intValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        return validateRange(n, 1900, year);
    }// validateYear

    /**
     * Validate that the input value is of type int
     * 
     * @param value
     *        String object to check
     * @return true if int; false otherwise
     */
    public static boolean validateInt(String value) {
        boolean isValid = false;
        try{
            Integer.parseInt(value);
            isValid = true;
        }catch(Exception e){
            isValid = false;
        }// end try/catch
        return isValid;
    }// end validateInt

    /**
     * This method builds a blank object to display in data table. This is a temporary fix to keep the filter object input boxes in table when no records match.
     * 
     * @param displayNames
     *        The Map of display names
     * @param fieldNames
     *        The array of field name values
     * @return <List>columnVO
     */
    public static List<ColumnModel> getNoRecordsFoundForSearch(Map<String, String> displayNames, String[] fieldNames) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getNoRecordsFoundForSearch");
        LOG.debug("Parameters: displayNames.size=" + (displayNames == null ? "null" : displayNames.size()) + " ,objList.fieldNames=" + (fieldNames == null ? "null" : fieldNames.length));
        List<ColumnModel> f = new ArrayList<ColumnModel>();
        for(int x = 0, y = fieldNames.length;x < y;x++){
            ColumnModel fd = new ColumnModel();
            fd.setColumnName(displayNames == null ? fieldNames[x] : displayNames.get(fieldNames[x]));
            if(x == 0){
                fd.put(VAL, NO_RECORDS_DISPLAY_STRING);
            }else{
                fd.put(VAL, EMPTY_STRING);
            }// end else
            f.add(fd);
        }// end for
        LOG.debug("Exiting gov.doc.isu.dwarf.exampleutil.AppUtil - method getNoRecordsFoundForSearch");
        return f;
    }// end method

    /**
     * This method builds a blank object to display in data table. This is a temporary fix to keep the filter object input boxes in table when no records return from query.
     * 
     * @param displayNames
     *        The Map of display names
     * @param fieldNames
     *        The array of field name values
     * @return <List>columnVO
     */
    public static List<ColumnModel> getNoRecordsFoundForDisplay(Map<String, String> displayNames, String[] fieldNames) {
        LOG.debug("Entering gov.doc.isu.dwarf.util.AppUtil - method getNoRecordsFoundForDisplay");
        LOG.debug("Parameters: displayNames.size=" + (displayNames == null ? "null" : displayNames.size()) + " ,objList.fieldNames=" + (fieldNames == null ? "null" : fieldNames.length));
        List<ColumnModel> f = new ArrayList<ColumnModel>();
        for(int x = 0, y = fieldNames.length;x < y;x++){
            ColumnModel fd = new ColumnModel();
            fd.setColumnName(displayNames == null ? fieldNames[x] : displayNames.get(fieldNames[x]));
            if(x == 0){
                fd.put(VAL, NO_RECORDS_FOUND_DISPLAY_STRING);
                fd.put("disabled", "disabled");
            }else{
                fd.put(VAL, EMPTY_STRING);
            }// end else
            f.add(fd);
        }// end for
        LOG.debug("Exiting gov.doc.isu.dwarf.util.AppUtil - method getNoRecordsFoundForDisplay");
        return f;
    }// end method
}
