package gov.doc.isu.dwarf.taglib.displaytag.tags.el;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

/**
 * Adds an extra row above or below the main row.
 * 
 * @author Will Glass-Husain
 */
public class ELSubRowTag extends gov.doc.isu.dwarf.taglib.displaytag.tags.SubRowTag {

    /**
     * logger.
     */
    private static Logger log = Logger.getLogger(ELSubRowTag.class);

    /**
     * Expression for the "evenClass" tag attribute.
     */
    private String evenClassExpr;

    /**
     * Expression for the "positionBelow" tag attribute.
     */
    private String positionBelowExpr;

    /**
     * Expression for the "frequency" tag attribute.
     */
    private String frequencyExpr;

    /**
     * Expression for the "oddClass" tag attribute.
     */
    private String oddClassExpr;

    /**
     * Expression for the "media" tag attribute.
     */
    private String mediaExpr;

    /**
     * Expression for the "style" tag attribute.
     */
    private String styleExpr;

    /**
     * Expression for the "oddClass" tag attribute.
     */
    public void setOddClass(String value) {
        oddClassExpr = value;
    }

    /**
     * Expression for the "evenClass" tag attribute.
     */
    public void setEvenClass(String value) {
        evenClassExpr = value;
    }

    /**
     * Expression for the "media" tag attribute.
     */
    public void setMedia(String value) {
        mediaExpr = value;
    }

    /**
     * Expression for the "position" tag attribute.
     */
    public void setPositionBelow(String value) {
        positionBelowExpr = value;
    }

    /**
     * Expression for the "frequency" tag attribute.
     */
    public void setFrequency(String value) {
        frequencyExpr = value;
    }

    /**
     * Expression for the "style" tag attribute.
     */
    public void setStyle(String value) {
        styleExpr = value;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        evaluateExpressions();

        return super.doStartTag();
    }

    /**
     * Evaluates the expressions for all the given attributes and pass results up to the parent tag.
     * 
     * @throws JspException
     *         for exceptions occurred during evaluation.
     */
    private void evaluateExpressions() throws JspException {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if(oddClassExpr != null){
            super.setOddClass(eval.evalString("oddClass", oddClassExpr));
        }
        if(evenClassExpr != null){
            super.setEvenClass(eval.evalString("evenClass", evenClassExpr));
        }
        if(mediaExpr != null){
            super.setMedia(eval.evalString("media", mediaExpr));
        }
        if(positionBelowExpr != null){
            super.setPositionBelow(eval.evalBoolean("positionBelow", positionBelowExpr));
        }
        if(frequencyExpr != null){
            super.setFrequency(eval.evalString("frequency", frequencyExpr));
        }
        if(styleExpr != null){
            super.setStyle(eval.evalString("style", styleExpr));
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release() {
        super.release();
        this.evenClassExpr = null;
        this.oddClassExpr = null;
        this.mediaExpr = null;
        this.positionBelowExpr = null;
        this.frequencyExpr = null;
        this.styleExpr = null;
    }

}
