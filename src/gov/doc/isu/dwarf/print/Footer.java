package gov.doc.isu.dwarf.print;

import gov.doc.isu.dwarf.resources.Constants;
import gov.doc.isu.dwarf.util.StringUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Footer.java for genrating footer on PDF. Footer has message, date, and page number.
 * 
 * @author Steven L. Skinner
 */
public class Footer extends PdfPageEventHelper {
    private static Logger log = Logger.getLogger("gov.doc.isu.dwarf.print.Footer");
    protected PdfPTable footer;
    protected String footerText = null;
    protected BaseFont helv = null;
    protected BaseFont helvBold = null;
    protected String ofndrNameText = null;
    protected String docIdText = null;

    /**
     * Constructor for footer
     * 
     * @param footerValue
     *        footerValue
     */
    public Footer(final String offenderName, final String offenderNum, final String footerValue) {
        try{

            if(!StringUtil.isNullOrEmpty(offenderName)){
                ofndrNameText = offenderName;
            }
            if(!StringUtil.isNullOrEmpty(offenderNum)){
                docIdText = offenderNum;
            }
            footer = new PdfPTable(1);
            footer.setTotalWidth(600);
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
            helvBold = BaseFont.createFont("Helvetica-Bold", BaseFont.WINANSI, false);
            footer.addCell(new Phrase(new Chunk(footerValue, new Font(helv, 8)).setAction(new PdfAction(PdfAction.FIRSTPAGE))));
        }catch(final Exception e){
            log.debug("An error occured genarating footer for PDF: ");
            log.debug(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void onEndPage(final PdfWriter writer, final Document document) {
        final PdfContentByte cb = writer.getDirectContent();
        final String pageNumText = "Page:  " + writer.getPageNumber();
        final float pageNumTextSize = helv.getWidthPoint(pageNumText, 12);
        final float textBase = document.bottom() - 25;

        cb.beginText();
        cb.setFontAndSize(helv, 10);
        cb.setTextMatrix(document.left(), textBase);
        final DateFormat dateFormat = new SimpleDateFormat(Constants.SIMPLE_DATE_LONG_FORMAT);
        final Date date = new Date();
        cb.showText(dateFormat.format(date));

        if(docIdText != null && ofndrNameText != null){
            // Set Fonts to helvBold for DOC ID & Offender Name labels and add to cb.
            cb.setFontAndSize(helvBold, 10);
            float labelBase = document.bottom() - 10;
            float docIdLabelSize = helvBold.getWidthPoint("DOC ID:  ", 10);
            cb.setTextMatrix(document.left(), labelBase);
            cb.showText("DOC ID:  ");

            float ofndrNameLabelSize = helvBold.getWidthPoint("Offender Name:  ", 10);
            cb.setTextMatrix(document.left() + docIdLabelSize + 220, labelBase);
            cb.showText("Offender Name:  ");

            // Set Fonts to helv for DOC ID & Offender Name Text and add to cb.
            cb.setFontAndSize(helv, 10);
            cb.setTextMatrix(document.left() + docIdLabelSize, labelBase);
            cb.showText(docIdText);

            cb.setTextMatrix(document.left() + docIdLabelSize + 220 + ofndrNameLabelSize, labelBase);
            cb.showText(ofndrNameText);

            cb.setTextMatrix(document.right() - pageNumTextSize, labelBase);
            cb.showText(pageNumText);
        }else{
            cb.setTextMatrix(document.right() - pageNumTextSize, textBase);
            cb.showText(pageNumText);
        }

        cb.endText();
        footer.writeSelectedRows(0, -1, (document.right() - document.left() - 580) / 2 + document.leftMargin(), document.bottom() - 15, cb);
    }
}
