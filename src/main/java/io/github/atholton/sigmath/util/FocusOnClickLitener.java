package io.github.atholton.sigmath.util;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class FocusOnClickLitener implements MouseListener {
    private Component comp;

    public FocusOnClickLitener(Component comp) {
        this.comp = Objects.requireNonNull(comp);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        comp.requestFocusInWindow(FocusEvent.Cause.MOUSE_EVENT);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
