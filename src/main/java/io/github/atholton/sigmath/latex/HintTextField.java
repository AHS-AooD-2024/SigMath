package io.github.atholton.sigmath.latex;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Objects;

import javax.swing.JTextField;

public class HintTextField extends JTextField{
    private boolean isHint = false;
    private String hint;
    
    private Font mainFont = new Font("SansSerif", Font.BOLD, 20);
    private Font hintFont = new Font("SansSerif", Font.BOLD, 20); 

    public HintTextField(final String hint) {  
        this.hint = hint;
        setToHint();  
    
        this.addFocusListener(new FocusAdapter() {
            @Override  
            public void focusGained(FocusEvent e) {  
                if (isHint()) {  
                    clearHint();  
                } else {  
                    setText(getText());  
                    setFont(mainFont);  
                }  
            }
        
            @Override  
            public void focusLost(FocusEvent e) {  
                if (getText().length() == 0) {  
                    setToHint();  
                } else {  
                    setText(getText());  
                    setFont(mainFont);  
                    setForeground(Color.BLACK);  
                }  
            }  
        });
    }

    public boolean isHint() {
        return isHint;
    }

    public void setToHint() {
        invalidate();
        isHint = true;
        setText(hint);  
        setFont(hintFont);  
        setForeground(Color.GRAY);
        validate();
    }
    
    private void clearHint() {
        invalidate();
        isHint = false;
        setText("");  
        setFont(mainFont);
        setForeground(Color.BLACK);
        validate();
    }  

    @Override
    public void setFont(Font f) {
        setMainFont(f);
        setHintFont(f);
        super.setFont(f);
    }

    public Font getMainFont() {
        return mainFont;
    }

    public void setMainFont(Font font) {
        Font old = this.mainFont;
        this.mainFont = font;

        firePropertyChange("mainFont", old, mainFont);
        if(!isHint() && !Objects.equals(mainFont, old)) {
            setFont(mainFont);
        }
    }
    
    public Font getHintFont() {
        return hintFont;
    }

    public void setHintFont(Font font) {
        Font old = this.hintFont;
        this.hintFont = font;

        firePropertyChange("hintFont", old, hintFont);
        if(isHint() && !Objects.equals(hintFont, old)) {
            setFont(hintFont);
        }
    }
     
    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        String old = this.hint;
        this.hint = hint;

        firePropertyChange("hint", old, this.hint);

        if(isHint()) {
            setToHint();
        }
    }
} 

