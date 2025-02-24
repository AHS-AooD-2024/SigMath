/**
 * Copyright 2025 Sigmath Creators and Contributers 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.atholton.sigmath;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.scilab.forge.jlatexmath.TeXParser;

import io.github.atholton.sigmath.latex.DualTeXField;
import io.github.atholton.sigmath.latex.TeXField;
import io.github.atholton.sigmath.latex.TeXLabel;

/**
 * The main container class for application interfaces.
 */
public class Sigmath extends JFrame implements Runnable {
    public static void main(String[] args) {
        Sigmath sigmath = new Sigmath();
        SwingUtilities.invokeLater(sigmath);
    }

    public Sigmath() {
        super("SigMath");
    }

    @Override
    public void run() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        TeXIcon.defaultSize = 24.0f;
        // TODO: add stuff
        var l = new TeXLabel("$$ \\sqrt{x + 2} \\over y^3 $$", 24.0f);
        add(l);
        var t = new DualTeXField();
        add(t);
        pack();
    }
}
