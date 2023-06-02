package gov.doc.isu.dwarf.resources;

import gov.doc.isu.dwarf.print.TrueTypeFontFactory;
import gov.doc.isu.dwarf.util.AppUtil;

import java.awt.Color;

import com.lowagie.text.Font;

/**
 * Dwarf Constants class.
 * 
 * @author Steven L. Skinner
 */
public class Constants {

    /** These values will be set in ApplicationInitServlet */
    public static String DATASOURCE = "";// value for Datasource
    public static String COOKIE_NAME = "";
    public static String DEFAULT_COOKIE = "";
    public static String APPLICATION_TYPE = "";
    public static String APPLICATION_NAME = "";
    public static String PDF_EXPORT_FOOTER = "";
    public static String GOVERNOR = "";
    public static String DOC_DIRECTOR = "";
    public static String DOC_TEMPLATE = "";
    /*****************************************/

    public static Color PDF_HEADER_BACKGROUND = new Color(255, 255, 255);
    public static Color PDF_COLUMN_BACKGROUND = new Color(220, 220, 220);
    public static Font PDF_headerFont = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 10f, Font.BOLD, new Color(0, 0, 0));
    public static Font PDF_subHeaderFont = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 8f, Font.BOLD, new Color(0, 0, 0));
    public static String LIST_NAME = "";

    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String APPLICATION_USER = "appUser";
    public static final String DEFAULT_DATE = "12/31/7799";
    public static final String DEFAULT_TIME = "00:00";
    public static final String DEFAULT_DATE_TIME = "00/00/0000 00:00";
    public static final java.sql.Date DEFAULT_SQL_DATE_AS_DATE = AppUtil.getSQLDate(DEFAULT_DATE);
    public static final java.util.Date DEFAULT_DATE_AS_DATE = AppUtil.getDate(DEFAULT_DATE);
    public static final String DEFAULTYESIND = "Y";
    public static final String DEFAULTNOIND = "N";
    public static final String DEFAULTYESDESC = "Yes";
    public static final String DEFAULTNODESC = "No";
    public static final String DEFAULT_DROPDOWN_LABEL = "...";
    public static final String VAL = "value";
    public static final String KEY = "key";
    public static final String TYPE = "type";
    public static final String EMPTY_STRING = "";
    public static final String EMPTY_SPACE = " ";
    public static final String ZERO_STRING = "0";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String HYPHEN = "-";
    public static final String UNDER_SCORE = "_";
    public static final String EQUALS = "=";
    public static final String PERCENTAGE = "%";
    public static final String ASTERISK = "*";
    public static final String AT_SYMBOL = "@";
    public static final String STR_OPEN_PARENTHETICAL = "(";
    public static final String STR_CLOSE_PARENTHETICAL = ")";
    public static final String STR_OPEN_BRACE = "{";
    public static final String STR_CLOSE_BRACE = "}";
    public static final String STR_SQR_BRACE_END = "]";
    public static final String STR_SQR_BRACE_START = "[";
    public static final String NULL_AS_STRING = "null";
    public static final String SIMPLE_DATE_LONG_FORMAT = "MM/dd/yyyy";
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    // Values for jsp file mapping in struts.config
    public static final String REFRESH = "Refresh";
    public static final String LIST = "List";
    public static final String DETAIL = "Detail";
    public static final String FAILURE = "Failure";
    public static final String CANCEL = "Cancelled";
}
