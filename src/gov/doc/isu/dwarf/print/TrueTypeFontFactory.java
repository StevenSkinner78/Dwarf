package gov.doc.isu.dwarf.print;

import java.awt.Color;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

/**
 * This class has default values use in PDF export options.
 * 
 * @author Michael
 * @author Steven Skinner JCCC
 */
public class TrueTypeFontFactory {
    private static Logger log = Logger.getLogger("gov.doc.isu.dwarf.print.TrueTypeFontFactory");

    public static final int ARIAL = 0x0001;
    public static final int BOOK_ANTIQUA = 0x0002;
    public static final int BOOKMAN_OLD_STYLE = 0x0003;
    public static final int CENTURY_GOTHIC = 0x0004;
    public static final int COMIC_SANS_MS = 0x0005;
    public static final int COURIER_NEW = 0x0006;
    public static final int TAHOMA = 0x0007;
    public static final int TIMES_NEW_ROMAN = 0x0008;
    public static final int VERDANA = 0x0010;

    private static final String fontFolder = "c:\\windows\\fonts\\";
    private static final String ARIAL_FONT_PATH = fontFolder + "ARIAL.ttf";
    private static final String BOOK_ANTIQUA_FONT_PATH = fontFolder + "BKANT.ttf";
    private static final String BOOKMAN_OLD_STYLE_FONT_PATH = fontFolder + "BOOKOS.ttf";
    private static final String CENTURY_GOTHIC_FONT_PATH = fontFolder + "GOTHIC.ttf";
    private static final String COMIC_SANS_MS_FONT_PATH = fontFolder + "comic.ttf";
    private static final String COURIER_FONT_PATH = fontFolder + "COUR.ttf";
    private static final String TAHOMA_FONT_PATH = fontFolder + "TAHOMA.ttf";
    private static final String TIMES_FONT_PATH = fontFolder + "TIMES.ttf";
    private static final String VERDANA_FONT_PATH = fontFolder + "verdana.ttf";

    /**
	 * 
	 */
    public TrueTypeFontFactory() {
        super();
    }

    public static Font getFont(int fontName) {
        Font font = null;
        try{
            BaseFont bFont = BaseFont.createFont(getFontPath(fontName), BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            font = new Font(bFont, Font.DEFAULTSIZE);
        }catch(DocumentException de){
            log.error(de.getMessage());
        }catch(IOException ioe){
            log.error(ioe.getMessage());
        }
        return font;
    }

    public static Font getFont(int fontName, float size, int style) {
        Font font = getFont(fontName);
        font.setSize(size);
        font.setStyle(style);
        return font;
    }

    public static Font getFont(int fontName, float size, int style, String htmlValue) {
        Font font = getFont(fontName, size, style);
        if(!StringUtils.isBlank(htmlValue) && htmlValue.length() == 6){
            int col = Integer.parseInt(StringUtils.trim(htmlValue), 16);
            font.setColor(new Color(col));
        }
        return font;
    }

    public static Font getFont(int fontName, float size, int style, Color color) {
        Font font = getFont(fontName, size, style);
        font.setColor(color);
        return font;
    }

    private static String getFontPath(int fontName) {
        String fontPath = "";
        switch(fontName){
            case ARIAL:
                fontPath = ARIAL_FONT_PATH;
                break;
            case BOOK_ANTIQUA:
                fontPath = BOOK_ANTIQUA_FONT_PATH;
                break;
            case BOOKMAN_OLD_STYLE:
                fontPath = BOOKMAN_OLD_STYLE_FONT_PATH;
                break;
            case CENTURY_GOTHIC:
                fontPath = CENTURY_GOTHIC_FONT_PATH;
                break;
            case COMIC_SANS_MS:
                fontPath = COMIC_SANS_MS_FONT_PATH;
                break;
            case TAHOMA:
                fontPath = TAHOMA_FONT_PATH;
                break;
            case TIMES_NEW_ROMAN:
                fontPath = TIMES_FONT_PATH;
                break;
            case VERDANA:
                fontPath = VERDANA_FONT_PATH;
                break;
            default:
                fontPath = COURIER_FONT_PATH;
                break;
        }
        return fontPath;
    }
}
