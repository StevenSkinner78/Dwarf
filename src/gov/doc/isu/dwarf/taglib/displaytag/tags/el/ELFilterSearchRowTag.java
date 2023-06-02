package gov.doc.isu.dwarf.taglib.displaytag.tags.el;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

/**
 * Adds an extra row above or below the main row.
 * 
 * @author Will Glass-Husain
 */
public class ELFilterSearchRowTag extends gov.doc.isu.dwarf.taglib.displaytag.tags.FilterSearchRowTag {

    /**
     * logger.
     */
    private static Logger log = Logger.getLogger(ELFilterSearchRowTag.class);

    /**
     * Expression for the "media" tag attribute.
     */
    private String mediaExpr;

    /**
     * Expression for the "ignoreColumns" tag attribute.
     */
    private String ignoreColumnsExpr;

    private String formNameExpr;

    private String urlExpr;

    /**
     * Expression for the "media" tag attribute.
     */
    public void setMedia(String value) {
        mediaExpr = value;
    }

    /**
     * Expression for the "ignoreColumns" tag attribute.
     */
    public void setIgnoreColumns(String value) {
        ignoreColumnsExpr = value;
    }

    /**
     * Expression for the "formName" tag attribute.
     */
    public void setFormName(String value) {
        formNameExpr = value;
    }

    /**
     * Expression for the "url" tag attribute.
     */
    public void setUrl(String value) {
        urlExpr = value;
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

        if(mediaExpr != null){
            super.setMedia(eval.evalString("media", mediaExpr));
        }

        if(ignoreColumnsExpr != null){
            super.setIgnoreColumns(eval.evalString("ignoreColumns", ignoreColumnsExpr));
        }
        if(formNameExpr != null){
            super.setFormName(eval.evalString("formName", formNameExpr));
        }
        if(urlExpr != null){
            super.setUrl(eval.evalString("url", urlExpr));
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release() {
        super.release();
        this.mediaExpr = null;
        this.ignoreColumnsExpr = null;
        this.formNameExpr = null;
        this.urlExpr = null;
    }

}
