package gov.doc.isu.dwarf.print;

import gov.doc.isu.dwarf.resources.Constants;

import java.awt.Color;

import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;

/**
 * Class: PDFGenerator.java<br/>
 * Date: Oct 3, 2006<br/>
 * Description:
 * <p>
 * Abstract class for generating PDF documents
 * </p>
 * <p>
 * Modified by Steven Skinner 05/31/2013
 * </p>
 * 
 * @author Michael R. Dirks
 */
public class PDFGenerator implements PDFConstants {
    private static Logger log = Logger.getLogger(PDFGenerator.class);

    /**
     * Thes method gets the DOC header for the PDF
     * 
     * @return PdfPTable
     * @throws Exception
     *         Exception
     */
    public PdfPTable getDOCHeader() throws Exception {
        // Set up the table to display the data
        final PdfPTable datatable = new PdfPTable(1);
        // final int[] headerwidths = {25, 50, 25}; // percentage
        // datatable.setWidths(headerwidths);
        datatable.setWidthPercentage(100); // percentage
        datatable.getDefaultCell().setPadding(0);
        datatable.getDefaultCell().setBorder(0);
        datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        datatable.getDefaultCell().setLeading(2, 1);

        // p.add(new Chunk(Constants.GOVERNOR + "\n", nameFont));
        // p.add(new Chunk("Governor\n\n", titleFont));
        // p.add(new Chunk(Constants.DOC_DIRECTOR + "\n", nameFont));
        // p.add(new Chunk("Director\n\n", titleFont));
        // datatable.addCell(p);
        //
        // datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        // datatable.getDefaultCell().setLeading(2, 1);
        // p = new Paragraph();
        // p.add(new Chunk(DOC_ADDRESS + "\n\n", addressFont));
        // datatable.addCell(p);

        datatable.getDefaultCell().setColspan(3);
        datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        // datatable.getDefaultCell().setLeading(1, 1);
        datatable.addCell(new Paragraph(new Chunk(getDOCImage(colorLogo), 0, 0)));
        Paragraph p = new Paragraph();
        p.add(new Chunk("State of Missouri\n", mainSubHeader));
        p.add(new Chunk("DEPARTMENT OF CORRECTIONS\n", docHeader));
        p.add(new Chunk("Ad Excelleum Conamur - \"We Strive Towards Excellence\"\n", italicFont));
        datatable.addCell(p);
        return datatable;
    }

    /**
     * Gets the DOC Image
     * 
     * @param photoPath
     *        the path of the photo
     * @return Image image
     */
    protected Image getDOCImage(final String photoPath) {
        Image img = null;
        try{
            final java.awt.Image awtImg = new ImageIcon(PDFGenerator.class.getResource(photoPath)).getImage();
            img = Image.getInstance(awtImg, null);
            img.scalePercent(46);
        }catch(final Exception e){
            log.error("Exception loading image: " + e.getMessage(), e);
        }
        return img;
    }

    /**
     * This method gets the header for the table
     * 
     * @param tableName
     *        The name of the table
     * @return PdfPTable as a caption
     * @throws Exception
     *         Exception
     */
    public PdfPTable getTableCaption(String tableName) throws Exception {
        // Set up the table to display the data
        final PdfPTable datatable = new PdfPTable(1);
        datatable.setWidthPercentage(100); // percentage
        datatable.setSpacingBefore(3.0f);
        datatable.setSpacingAfter(2.0f);
        datatable.getDefaultCell().setPadding(2.0f);
        datatable.getDefaultCell().setBorder(Rectangle.BOX);
        datatable.getDefaultCell().setBorderColor(Color.BLACK);
        datatable.getDefaultCell().setBorderWidth(1.0f);
        datatable.getDefaultCell().setBackgroundColor(PDF_HEADER_BACKGROUND);
        datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        datatable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        datatable.getDefaultCell().setLeading(2, 1);

        final Paragraph p = new Paragraph();
        p.add(new Chunk(StringUtils.upperCase(Constants.APPLICATION_NAME) + "\n", Constants.PDF_headerFont));
        p.add(new Chunk(StringUtils.upperCase(tableName) + "\n", Constants.PDF_subHeaderFont));
        datatable.addCell(p);

        return datatable;
    }

}
