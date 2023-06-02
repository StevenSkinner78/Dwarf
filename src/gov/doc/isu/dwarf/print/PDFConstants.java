package gov.doc.isu.dwarf.print;

import gov.doc.isu.dwarf.resources.Constants;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.lowagie.text.Font;

/**
 * Class: PDFConstants.java<br/>
 * Date: Oct 3, 2006<br/>
 * Description:
 * <p>
 * Interface class for PDF contants
 * </p>
 * <p>
 * Modified by Steven Skinner 05/31/2013
 * </p>
 * 
 * @author Michael R. Dirks
 */
public interface PDFConstants {
    public static Font subHeader = TrueTypeFontFactory.getFont(TrueTypeFontFactory.ARIAL, 6f, Font.NORMAL, "333333");
    public static Font contentSubHeader = TrueTypeFontFactory.getFont(TrueTypeFontFactory.ARIAL, 8f, Font.BOLD, "333333");
    public static Font arial8 = TrueTypeFontFactory.getFont(TrueTypeFontFactory.ARIAL, 8f, Font.NORMAL, "333333");
    public static Font historyOfficer = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TAHOMA, 10f, Font.BOLD, "333333");
    public static Font historyHeader = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TAHOMA, 12f, Font.BOLD, "333333");
    public Font headerFont = Constants.PDF_headerFont;
    public Font subHeaderFont = Constants.PDF_subHeaderFont;
    public static Font wantedHeader = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 28f, Font.BOLD, "333333");
    public static Font wantedSubHeader = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 12f, Font.BOLD, "333333");
    public static Font wantedArial10 = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 12f, Font.BOLD, "333333");
    public static Font tahoma12Normal = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TAHOMA, 12f, Font.NORMAL, "333333");
    public static Font tahoma12Bold = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TAHOMA, 12f, Font.BOLD, "333333");
    public static Font arial10Normal = TrueTypeFontFactory.getFont(TrueTypeFontFactory.ARIAL, 10f, Font.NORMAL, "333333");
    public static Font arial10Bold = TrueTypeFontFactory.getFont(TrueTypeFontFactory.ARIAL, 10f, Font.BOLD, "333333");

    public static Font tahoma = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TAHOMA, 8f, Font.NORMAL);
    public static Font tahomaBold = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TAHOMA, 8f, Font.BOLD);

    public SimpleDateFormat sd = new SimpleDateFormat(Constants.SIMPLE_DATE_LONG_FORMAT);
    public SimpleDateFormat tf = new SimpleDateFormat("hh:mm a");
    public DecimalFormat currency = new DecimalFormat("$###,###.00;($###,###.00)");

    public static String imagePath = "shieldbig.gif";
    public static String noPhoto = "nophoto.gif";
    public static String colorLogo = "colorlogo1.gif";

    public static Color PDF_HEADER_BACKGROUND = Constants.PDF_HEADER_BACKGROUND;
    public static Color PDF_COLUMN_BACKGROUND = Constants.PDF_COLUMN_BACKGROUND;

    public static Font mainHeader = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TAHOMA, 16f, Font.BOLD, "000000");
    public static Font mainSubHeader = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 14f, Font.BOLD, "000000");
    public static Font docHeader = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 16f, Font.BOLD, "000000");
    public static Font nameFont = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 11f, Font.BOLD, "333333");
    public static Font titleFont = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 11f, Font.NORMAL, "333333");
    public static Font addressFont = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 9f, Font.NORMAL, "333333");
    public static Font italicFont = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 10f, Font.ITALIC, "333333");
    public static Font footerFont = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 10f, Font.ITALIC, "333333");
    public static Font textFont = TrueTypeFontFactory.getFont(TrueTypeFontFactory.TIMES_NEW_ROMAN, 8f, Font.NORMAL, "333333");

    public static final String DOC_ADDRESS = "2729 Plaza Drive\nP.O. Box 236\nJefferson City, Missouri 65102\nTelephone: 573-751-2389\nFax: 573-751-4099\nTDD Available";
}
