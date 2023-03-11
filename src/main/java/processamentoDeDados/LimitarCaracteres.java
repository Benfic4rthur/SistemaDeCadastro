package processamentoDeDados;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitarCaracteres extends PlainDocument {
    private int maxCaracteres;

    public LimitarCaracteres(int maxCaracteres) {
        super();
        this.maxCaracteres = maxCaracteres;
    }

    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null)
            return;

        String texto = getText(0, getLength()) + str;

        if (texto.length() <= maxCaracteres) {
            super.insertString(offs, str, a);
        }
    }
}
