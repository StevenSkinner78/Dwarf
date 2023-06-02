package gov.doc.isu.dwarf.util;

import static gov.doc.isu.dwarf.resources.Constants.COMMA;
import static gov.doc.isu.dwarf.resources.Constants.EMPTY_SPACE;
import static gov.doc.isu.dwarf.resources.Constants.EMPTY_STRING;
import static gov.doc.isu.dwarf.resources.Constants.EMPTY_STRING_ARRAY;
import static gov.doc.isu.dwarf.resources.Constants.HYPHEN;
import static gov.doc.isu.dwarf.resources.Constants.NULL_AS_STRING;
import static gov.doc.isu.dwarf.resources.Constants.STR_CLOSE_PARENTHETICAL;
import static gov.doc.isu.dwarf.resources.Constants.STR_OPEN_PARENTHETICAL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * String Utility class for Dwarf Framework.
 * 
 * @author Steven Skinner JCCC
 */
public final class StringUtil {
    // log
    private static Logger log = Logger.getLogger("gov.doc.isu.dwarf.util.StringUtil");

    /**
     * Default Constructor
     */
    private StringUtil() {

    }

    /**
     * Used to return the word 'null' if the Object passed in equals null. This method called only from overridden toString() in object classes and forms.
     * 
     * @param o
     *        The Object to check if is null.
     * @return String The literal 'null' if is null, otherwise the value of the parameter.
     */
    public static String isNull(final Object o) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method isNull()");
        log.debug("Parameters are: object=" + String.valueOf(o));
        String result = (o == null ? "null" : String.valueOf(o));
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method isNull()");
        return result;
    }

    /***
     * This method return true if the given string is null or empty else returns false
     * 
     * @param str
     *        str
     * @return boolean
     */
    public static boolean isNullOrEmpty(final String str) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method isNullOrEmpty()");
        log.debug("Parameters are: string=" + String.valueOf(str));
        boolean result = (str == null || str.trim().equals(EMPTY_STRING)) ? true : false;
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method isNullOrEmpty()");
        return result;
    }

    /**
     * This method return true if the given string array is null or empty else returns false
     * 
     * @param strArray
     *        strArray
     * @return boolean
     */
    public static boolean isNullOrEmptyStringArray(final String[] strArray) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method isNullOrEmptyStringArray()");
        log.debug("Parameters are: stringArray=" + collapseArray(strArray));
        boolean result = (strArray == null || strArray.length == 0) ? true : false;
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method isNullOrEmptyStringArray()");
        return result;
    }

    /**
     * Returns a blank string if the input string is null
     * 
     * @param inString
     *        inString
     * @return String inString if not null, else blank string
     */
    public static String blankNull(final String inString) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method blankNull()");
        log.debug("Parameters are: string=" + String.valueOf(inString));
        String result = (inString == null || NULL_AS_STRING.equals(inString)) ? EMPTY_STRING : inString.trim();
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method blankNull()");
        return result;
    }

    /**
     * Returns a blank string if the input object is null. Otherwise return the String value
     * 
     * @param obj
     *        inString
     * @return String inString if not null, else blank string
     */
    public static String blankForNull(final Object obj) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method blankForNull()");
        log.debug("Parameters are: object=" + String.valueOf(obj));
        String result = (obj == null ? EMPTY_STRING : obj.toString());
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method blankForNull()");
        return result;
    }

    /**
     * Returns a Double of value 0.0 if the input Double is null
     * 
     * @param inDbl
     *        inDbl
     * @return Double inDbl if not null, else new Double of value 0.0
     * @deprecated This method is not being used.
     */
    public static Double zeroNull(final Double inDbl) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method zeroNull()");
        log.debug("Parameters are: inDouble=" + String.valueOf(inDbl));
        Double result = (inDbl == null) ? new Double(0.0) : inDbl;
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method zeroNull()");
        return result;
    }

    /**
     * Validates that SSN is correct length ans is numeric
     * 
     * @param inString
     *        inString
     * @return boolean
     */
    public static boolean validateSSN(String inString) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method validateSSN()");
        boolean valid = false;
        inString = StringUtils.replace(inString, HYPHEN, EMPTY_STRING);
        if(inString.length() == 9 && StringUtils.isNumeric(inString)){
            valid = true;
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method validateSSN()");
        return valid;
    }

    /**
     * Formats the ssn into the format xxx-xx-xxxx
     * 
     * @param inSSN
     *        String object to format
     * @return String in the format xxx-xx-xxxx
     */
    public static String formatSSN(String inSSN) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method formatSSN()");
        String result = EMPTY_STRING;
        if(!isNullOrEmpty(inSSN)){
            if(inSSN.length() != 9){
                if(!StringUtils.isBlank(inSSN)){
                    inSSN = StringUtils.leftPad(inSSN, 9, '0');
                }// end if
            }// end if
            StringBuffer sb = new StringBuffer(inSSN);
            sb.insert(3, HYPHEN);
            sb.insert(6, HYPHEN);
            result = sb.toString();
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method formatSSN()");
        return result;
    }

    /**
     * Changes the last four digits of ssn to xxxx for secutiry.
     * 
     * @param inSSN
     *        inSSN
     * @return String
     */
    public static String maskSSN(String inSSN) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method maskSSN()");
        String result = EMPTY_STRING;
        if(!isNullOrEmpty(inSSN)){
            inSSN = ssnToDB(inSSN);
            if(inSSN.length() < 9){
                inSSN = StringUtils.leftPad(inSSN, 9, '0');
            }// end if
            StringBuffer sb = new StringBuffer(inSSN);
            sb.replace(0, 5, "XXXXX");
            result = formatSSN(sb.toString());
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method maskSSN()");
        return result;
    }

    /**
     * Formats the phone number in the format (xxx)xxx-xxxx
     * 
     * @param phoneNum
     *        String object to format
     * @return String in the format (xxx)xxx-xxxx
     */
    public static String formatPhone(String phoneNum) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method formatPhone()");
        log.debug("Parameters are: phoneNum=" + String.valueOf(phoneNum));
        StringBuffer newPhone = new StringBuffer();
        String updatedPhoneNum = null;
        if(!isNullOrEmpty(phoneNum)){
            if(phoneNum.length() < 10){
                phoneNum = StringUtils.leftPad(phoneNum, 10, '0');
            }// end if
            newPhone.append(STR_OPEN_PARENTHETICAL).append(phoneNum.substring(0, 3).trim()).append(STR_CLOSE_PARENTHETICAL).append(phoneNum.substring(3, 6).trim()).append(HYPHEN).append(phoneNum.substring(6, 10).trim());
            updatedPhoneNum = newPhone.toString();
        }else{
            updatedPhoneNum = phoneNum;
        }// end else
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method formatPhone()");
        return updatedPhoneNum;
    }

    /**
     * Removes "()" and "-" from phone number to save to database
     * 
     * @param inPhone
     *        formatted phone number
     * @return database ready phone number
     */
    public static String phoneToDB(String inPhone) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method phoneToDB()");
        log.debug("Parameters are: phoneNum=" + String.valueOf(inPhone));
        if(!isNullOrEmpty(inPhone) && (inPhone.indexOf(STR_OPEN_PARENTHETICAL) == 0) && (inPhone.indexOf(STR_CLOSE_PARENTHETICAL) > 0)){
            inPhone = StringUtils.replace(inPhone, STR_OPEN_PARENTHETICAL, EMPTY_STRING);
            inPhone = StringUtils.replace(inPhone, STR_CLOSE_PARENTHETICAL, EMPTY_STRING);
            inPhone = StringUtils.replace(inPhone, HYPHEN, EMPTY_STRING);
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method phoneToDB()");
        return inPhone;
    }

    /**
     * Removes "-" from ssn to save to database
     * 
     * @param inSSN
     *        ssn with dashes
     * @return ssn without dashes
     */
    public static String ssnToDB(String inSSN) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method ssnToDB()");
        if(!isNullOrEmpty(inSSN) && (inSSN.indexOf(HYPHEN) > 0)){
            inSSN = StringUtils.replace(inSSN, HYPHEN, EMPTY_STRING);
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method ssnToDB()");
        return inSSN;
    }

    /**
     * This method takes a string and escapes and apostrophes in the string
     * 
     * @param sValue
     *        The string to decode.
     * @return The decoded string.
     */
    public static String escapeApostrophe(final String sValue) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method escapeApostrophe()");
        log.debug("Parameters are: value=" + String.valueOf(sValue));
        String result = null;
        if(!StringUtils.isBlank(sValue)){
            StringBuffer sb = new StringBuffer(sValue);
            for(int i = 0;i < sb.length();i++){
                char c = sb.charAt(i);
                if(c == '\''){
                    sb.replace(i, ++i, "''");
                }// end for
            }// end if
            result = sb.toString();
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method escapeApostrophe()");
        return result;
    }

    /**
     * Collapses a List of String objects into a comma separated row.
     * <p>
     * The list containing the String objects will be converted into a string of elements separated by a comma.
     * 
     * @param inList
     *        containing the elements
     * @param enquote
     *        boolean value determining if single quotes need to be around each String object
     * @return String object similar to a .csv row
     */
    public static String collapseList(final Collection<?> inList, final boolean enquote) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method collapseList()");
        log.debug("Parameters are: list.size()=" + (inList == null || inList.isEmpty() ? "null" : inList.size()));
        String result = EMPTY_STRING;
        if(!AppUtil.isEmpty(inList)){
            StringBuffer sb = new StringBuffer();
            if(enquote){
                sb.append("'");
            }// end if
            for(Iterator<?> iter = inList.iterator();iter.hasNext();){
                String element = String.valueOf(iter.next());
                sb.append(element);
                if(iter.hasNext()){
                    if(enquote){
                        sb.append("', '");
                    }else{
                        sb.append(", ");
                    }// end else
                }// end if
            }// end for
            if(enquote){
                sb.append("'");
            }// end if
            result = sb.toString();
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method collapseList()");
        return result;
    }// end method

    /**
     * Collapses a List of objects into a comma separated row.
     * <p>
     * The list containing the objects will be converted into a string of elements separated by a comma.
     * 
     * @param inList
     *        containing the elements
     * @return String object similar to a .csv row
     */
    public static String collapseObjectList(final Collection<?> inList) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method collapseObjectList()");
        log.debug("Parameters are: list.size()=" + (inList == null || inList.isEmpty() ? "null" : inList.size()));
        String result = EMPTY_STRING;
        if(!AppUtil.isEmpty(inList)){
            StringBuffer sb = new StringBuffer();
            for(Iterator<?> iter = inList.iterator();iter.hasNext();){
                String element = String.valueOf(iter.next());
                sb.append(element);
                if(iter.hasNext()){
                    sb.append(", ");
                }// end if
            }// end for
            result = sb.toString();
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method collapseObjectList()");
        return result;
    }// end method

    /**
     * Collapses a List of String objects into a comma separated row.
     * <p>
     * The list containing the String objects will be converted into a string of elements separated by a comma.
     * 
     * @param inList
     *        containing the elements
     * @return String object similar to a .csv row
     */
    public static String collapseList(final Collection<?> inList) {
        return collapseList(inList, false);
    }

    /**
     * Validate if a String has '*' character
     * 
     * @param s
     *        s
     * @return boolean
     */
    public static boolean isMask(final String s) {
        return StringUtils.contains(s, "*");
    }

    /**
     * Replace '*' to '%' for SQL search
     * 
     * @param s
     *        s
     * @return string
     */
    public static String setMask(final String s) {
        return StringUtils.replace(s, "*", "%");
    }

    /**
     * Converts newlines (\n) to the html break (&lt;br&gt;) tag
     * 
     * @param s
     *        String to be converted
     * @return Converted string with newlines replaced with breaks
     */
    public static String nl2br(final String s) {
        return StringUtils.replace(s, "\n", "<br>");
    }

    /**
     * Returns a boolean value indicating whether or not checkString matches the pattern. The pattern can include single characters, a range of characters enclosed in brackets, a question mark (which matches exactly one character), or an asterisk (which matches zero or more characters). If you're matching a character range, it can be either single characters, like [abc], or a range of characters, like [a-c], or a combination, like [a-clmnx-z]. For example, the pattern 'b[aiu]t' would match 'bat', 'bit', and 'but', and the pattern 'a[1-9]' would match a1, a2, a3, a4, a5, a6, a7, a8, and a9. This should all work much (exactly?) like file matching in DOS. For example, a pattern of '*.txt' should match all strings ending in '.txt', '*.*' should match all strings with a '.' in them, '*.???' should match strings with a three letter extension, and so on. Also, please note that the pattern check IS case-sensitive. If you don't want it to be, you should convert the checkString and the pattern
     * to lower-case as you're passing them.
     * 
     * @param checkString
     *        checkString
     * @param pattern
     *        pattern
     * @return boolean
     */
    public static boolean isMatch(final String checkString, final String pattern) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method isMatch()");
        log.debug("Parameters are: checkString=" + String.valueOf(checkString) + " ,pattern=" + String.valueOf(pattern));
        char patternChar;
        int patternPos = 0;
        char lastPatternChar;
        char thisChar;
        int i, j;

        for(i = 0;i < checkString.length();i++){
            // if we're at the end of the pattern but not the end
            // of the string, return false
            if(patternPos >= pattern.length()){
                return false;
            }// end if

            // grab the characters we'll be looking at
            patternChar = pattern.charAt(patternPos);
            thisChar = checkString.charAt(i);

            switch(patternChar){
                // check for '*', which is zero or more characters
                case '*':
                    // if this is the last thing we're matching,
                    // we have a match
                    if(patternPos >= (pattern.length() - 1)){
                        return true;
                    }// end if

                    // otherwise, do a recursive search
                    for(j = i;j < checkString.length();j++){
                        if(isMatch(checkString.substring(j), pattern.substring(patternPos + 1))){
                            return true;
                        }// end if
                    }// end for

                    // if we never returned from that, there is no match
                    return false;

                // check for '?', which is a single character
                case '?':
                    // do nothing, just advance the patternPos at the end
                    break;

                // check for '[', which indicates a range of characters
                case '[':
                    // if there's nothing after the bracket, we have
                    // a syntax problem
                    if(patternPos >= (pattern.length() - 1)){
                        return false;
                    }// end if

                    lastPatternChar = '\u0000';
                    for(j = patternPos + 1;j < pattern.length();j++){
                        patternChar = pattern.charAt(j);
                        if(patternChar == ']'){
                            // no match found
                            return false;
                        }else if(patternChar == '-'){
                            // we're matching a range of characters
                            j++;
                            if(j == pattern.length()){
                                return false; // bad syntax
                            }// end if

                            patternChar = pattern.charAt(j);
                            if(patternChar == ']'){
                                return false; // bad syntax
                            }else{
                                if((thisChar >= lastPatternChar) && (thisChar <= patternChar)){
                                    break; // found a match
                                }// end if
                            }// end else
                        }else if(thisChar == patternChar){
                            // if we got here, we're doing an exact match
                            break;
                        }// end else if

                        lastPatternChar = patternChar;
                    }// end for

                    // if we broke out of the loop, advance to the end bracket
                    patternPos = j;
                    for(j = patternPos;j < pattern.length();j++){
                        if(pattern.charAt(j) == ']'){
                            break;
                        }// end if
                    }// end for
                    patternPos = j;
                    break;

                default:
                    // the default condition is to do an exact character match
                    if(thisChar != patternChar){
                        return false;
                    }// end if

            }// end switch

            // advance the patternPos before we loop again
            patternPos++;

        }// end for

        // if there's still something in the pattern string, check to
        // see if it's one or more '*' characters. If that's all it is,
        // just advance to the end
        for(j = patternPos;j < pattern.length();j++){
            if(pattern.charAt(j) != '*'){
                break;
            }// end if
        }// end for
        patternPos = j;

        // at the end of all this, if we're at the end of the pattern
        // then we have a good match
        // if (patternPos == pattern.length()) {
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method isMatch()");
        return true;

    }// end method

    /**
     * Splits a String object into smaller strings of length strLength
     * 
     * @param inStr
     *        The String object to be split
     * @param strLength
     *        The length of each chunk to be split to
     * @return Collection of this String into strLength chunks
     */
    public static Collection<String> split(final String inStr, final Integer strLength) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method split()");
        log.debug("Parameters are: inStr=" + String.valueOf(inStr) + " ,strLength=" + String.valueOf(strLength));
        Integer startMult = 0;
        Integer endMult = 1;
        Vector<String> splitString = new Vector<String>();

        if(inStr.length() <= strLength){
            splitString.add(inStr);
        }else{
            int start = startMult * strLength;
            int end = endMult * strLength;
            while(end <= inStr.length()){
                log.debug("String from " + start + " to " + end + ": " + inStr.substring(start, end));
                splitString.add(inStr.substring(start, end));
                start = ( ++startMult) * strLength;
                end = ( ++endMult) * strLength;
            }// end while
            if(start < inStr.length()){
                log.debug("Last part of substring: " + inStr.substring(start, inStr.length()));
                splitString.add(inStr.substring(start, inStr.length()));
            }// end if

        }// end else
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method split()");
        return splitString;
    }// end method

    /**
     * Joins an array of strings into one long String object.
     * <p>
     * <strong>NOTE:</strong>The Collection passed in must contain elements of type <code>String</code>.
     * 
     * @param list
     *        Collection containing the Strings to be joined
     * @return String representation of the list.
     */
    public static String join(final Collection<?> list) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method join()");
        log.debug("Parameters are: list.size()=" + (AppUtil.isEmpty(list) ? "null" : list.size()));
        StringBuffer sb = new StringBuffer();
        for(Iterator<?> iter = list.iterator();iter.hasNext();){
            String element = (String) iter.next();
            sb.append(element);
        }// end for
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method join()");
        return sb.toString();
    }// end method

    /**
     * Creates a collection object from an array
     * 
     * @param array
     *        The array to put into the Collection
     * @return Collection object containing the String elements present in the array
     */
    public static Collection<String> createCollection(final String[] array) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method createCollection()");
        log.debug("Parameters are: array.length=" + (array == null ? "null" : array.length));
        Vector<String> v = new Vector<String>(20, 20);
        for(int i = 0;i < array.length;i++){
            v.add(array[i]);
        }// end for
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method createCollection()");
        return v;
    }

    /**
     * Validates the value to match a pattern specified by expr
     * 
     * @param value
     *        String object to validate
     * @param expr
     *        Regular expression to match value against
     * @return true if value matches expr; false otherwise
     */
    public static boolean matchPattern(final String value, final String expr) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method matchPattern()");
        log.debug("Parameters are: value=" + String.valueOf(value) + " ,expr=" + String.valueOf(expr));
        boolean match = false;
        if(validateRequired(expr)){
            Pattern regex = Pattern.compile(expr);
            Matcher matches = regex.matcher(value);
            match = matches.matches();
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method matchPattern()");
        return match;
    }

    /**
     * Validates the value to ensure that the value is not null and not blank
     * 
     * @param value
     *        String object to check
     * @return true is value is present; false otherwise
     */
    public static boolean validateRequired(final String value) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method validateRequired()");
        log.debug("Parameters are: value=" + String.valueOf(value));
        boolean isValid = false;
        if(!StringUtils.isBlank(value)){
            isValid = true;
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method validateRequired()");
        return isValid;
    }

    /**
     * Converts an array of Strings to a comma separated line
     * 
     * @param str
     *        array of Strings to convert
     * @return String object
     */
    public static String collapseArray(final String[] str) {
        return collapseArray(str, false);
    }

    /**
     * Converts an array of Strings to a comma separated line
     * 
     * @param str
     *        array of Strings to convert
     * @param enquote
     *        if true: each String will be encapsulated in single quotes
     * @return String object
     */
    public static String collapseArray(final String[] str, final boolean enquote) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method collapseArray()");
        log.debug("Parameters are: array.length=" + (str == null ? "null" : str.length) + " ,enquote=" + String.valueOf(enquote));
        String retStr = EMPTY_STRING;
        if(str != null && str.length != 0){
            StringBuffer sb = new StringBuffer();
            if(enquote){
                sb.append("'");
            }// end if
            for(int i = 0;i < str.length;i++){
                sb.append(str[i]);
                if(i + 1 < str.length){
                    if(enquote){
                        sb.append("','");
                    }else{
                        sb.append(",");
                    }// end else
                }// end if
            }// end for
            if(enquote){
                sb.append("'");
            }// end if
            retStr = sb.toString();
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method collapseArray()");
        return retStr;
    }// end method

    /**
     * Converts an array of Strings to one String with each object separated by delim
     * 
     * @param str
     *        array of Strings to convert
     * @param delim
     *        the delimiter used to separate each String object
     * @return String object with delimiters
     */
    public static String flattenArray(final String[] str, final String delim) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method flattenArray()");
        log.debug("Parameters are: array.length=" + (str == null ? "null" : str.length) + " ,delim=" + String.valueOf(delim));
        StringBuffer sb = new StringBuffer(str.length);
        for(int i = 0;i < str.length;i++){
            sb.append(StringUtils.trim(str[i])).append(delim);
        }// end for

        String retStr = sb.toString();
        if(retStr.lastIndexOf(delim) != -1){
            retStr = retStr.substring(0, retStr.lastIndexOf(delim));
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method flattenArray()");
        return retStr;
    }

    /**
     * Checks if an array contains a value.
     * 
     * @param checkString
     *        the value searching for
     * @param arrayToCheck
     *        the array to check
     * @return Booelan
     */
    public static boolean arrayContains(final String checkString, final String[] arrayToCheck) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method arrayContains()");
        log.debug("Parameters are: checkString=" + String.valueOf(checkString) + " ,array.length=" + (arrayToCheck == null ? "null" : arrayToCheck.length));
        boolean result = false;
        for(int i = 0, j = arrayToCheck.length;i < j;i++){
            if(arrayToCheck[i].equalsIgnoreCase(checkString)){
                result = true;
                break;
            }// end if
        }// end for
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method arrayContains()");
        return result;
    }

    /**
     * Jul 14, 2005 &lt;The detailed purpose of this method> This method is used to trim the sting value before assigning. Use this method in VOs to trim the String objects in setter methods.
     * 
     * @param string
     *        string
     * @return String
     */
    public static String trim(final String string) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method trim()");
        log.debug("Parameters are: string=" + String.valueOf(string));
        String result = (string != null ? string.trim() : null);
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method trim()");
        return result;
    }

    /**
     * Builds a string value of city, state zip to save in one column in database.
     * 
     * @param city
     *        city name
     * @param state
     *        state
     * @param zip
     *        zip code
     * @return string
     */
    public static String cityStateZipToPersist(final String city, final String state, final String zip) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method cityStateZipToPersist()");
        log.debug("Parameters are: city=" + String.valueOf(city) + " ,state=" + String.valueOf(state) + " ,zip=" + String.valueOf(zip));
        String returnString = EMPTY_STRING;
        StringBuffer sb = null;

        if(!isNullOrEmpty(city)){
            sb = new StringBuffer();
            sb.append(city.trim());
            sb.append(COMMA).append(EMPTY_SPACE).append((isNullOrEmpty(state) ? "" : state.toUpperCase().trim()));
            sb.append(EMPTY_SPACE).append((isNullOrEmpty(zip) ? "" : zip.trim()));
        }// end if
        if(sb != null){
            returnString = sb.toString();
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method cityStateZipToPersist()");
        return returnString;
    }

    /**
     * Builds an array of city, state zip for edit display on screen.
     * 
     * @param inString
     *        a String array
     * @return String[]
     */
    public static String[] cityStateZipToBusiness(final String inString) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method cityStateZipToBusiness()");
        log.debug("Parameters are: inString=" + String.valueOf(inString));
        String[] addressArray = {null, null, null};
        if(!isNullOrEmpty(inString)){
            Integer wholeString = inString.trim().length();
            Integer cityEnd = inString.indexOf(COMMA);
            Integer stateStart = cityEnd + 2;
            Integer stateEnd = stateStart + 2;
            Integer zipStart = stateEnd + 1;

            addressArray[0] = inString.substring(0, cityEnd);
            addressArray[1] = inString.substring(stateStart, stateEnd);
            addressArray[2] = inString.substring(zipStart, wholeString);
        }// end if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method cityStateZipToBusiness()");
        return addressArray;
    }

    /**
     * Gets full name as string
     * 
     * @param firstName
     *        firstName
     * @param middleName
     *        middleName
     * @param lastName
     *        lastName
     * @param suffixName
     *        suffixName
     * @return Full Name
     */
    public static String getFullName(final String firstName, final String middleName, final String lastName, final String suffixName) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method getFullName()");
        log.debug("Parameters are: firstName=" + String.valueOf(firstName) + " ,middleName=" + String.valueOf(middleName) + " ,lastName=" + String.valueOf(lastName) + " ,suffixName=" + String.valueOf(suffixName));
        StringBuffer sb = new StringBuffer();
        sb.append(blankNull(lastName));

        if(!StringUtil.isNullOrEmpty(blankNull(firstName))){
            sb.append(COMMA).append(EMPTY_SPACE).append(firstName.trim());
        }// end if
        if(!StringUtil.isNullOrEmpty(blankNull(middleName))){
            sb.append(EMPTY_SPACE).append(middleName.trim());
        }// end if
        if(!StringUtil.isNullOrEmpty(blankNull(suffixName))){
            sb.append(EMPTY_SPACE).append(suffixName.trim());
        }// end if
        String result = sb.toString().toUpperCase();
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method getFullName()");
        return result;
    }

    /**
     * Gets the description of the User Authority
     * 
     * @param auth
     *        auth code
     * @return auth as Description
     */
    public static String getAuthorityDesc(String auth) {
        log.debug("Entering gov.doc.isu.dwarf.util.StringUtil - method getAuthorityDesc()");
        log.debug("Parameters are: authorityCode=" + String.valueOf(auth));
        if("VIEW".equalsIgnoreCase(auth)){
            auth = "View";
        }else if("USER".equalsIgnoreCase(auth)){
            auth = "User";
        }else if("ADMN".equalsIgnoreCase(auth)){
            auth = "Administrator";
        }// end else if
        log.debug("Exiting gov.doc.isu.dwarf.util.StringUtil - method getAuthorityDesc()");
        return auth;
    }

    /* The following methods were copied from the commons API StringUtils class. */
    /**
     * This method is used to test if the parameter {@code str} is {@code null} or blank.
     *
     * @param str
     *        The {@code String} to test.
     * @return A {@code boolean} flag indicating if the parameter is {@code null} or blank.
     */
    public static boolean isBlank(String str) {
        if((str == null) || ((str.length()) == 0)){
            return true;
        }// end if
        for(int i = 0, j = str.length();i < j; ++i){
            if(!(Character.isWhitespace(str.charAt(i)))){
                return false;
            }// end if
        }// end for
        return true;
    }// end isBlank

    /**
     * This method is used to test if the parameter {@code str} is not {@code null} or blank.
     *
     * @param str
     *        The {@code String} to test.
     * @return A {@code boolean} flag indicating if the parameter is not {@code null} or blank.
     */
    public static boolean isNotBlank(String str) {
        return (!(isBlank(str)));
    }// end isNotBlank

    /**
     * This method is used to split a {@code String} into a {@code String[]}.
     *
     * @param str
     *        The {@code String} to be split.
     * @return The split {@code String}.
     */
    public static String[] split(String str) {
        if(str == null){
            return null;
        }// end if
        int len = str.length();
        if(len == 0){
            return EMPTY_STRING_ARRAY;
        }// end if
        List<String> list = new ArrayList<String>();
        int sizePlus1 = 1;
        int i = 0;
        int start = 0;
        boolean match = false;
        boolean lastMatch = false;
        while(i < len){
            if(Character.isWhitespace(str.charAt(i))){
                if(match){
                    lastMatch = true;
                    if(sizePlus1++ == -1){
                        i = len;
                        lastMatch = false;
                    }// end if
                    list.add(str.substring(start, i));
                    match = false;
                }// end if
                i++;
                start = i;
            }else{
                lastMatch = false;
                match = true;
                i++;
            }// end if/else
        }// end while
        if((match) || (lastMatch)){
            list.add(str.substring(start, i));
        }// end if
        return list.toArray(new String[list.size()]);
    }// end split
}
