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
import javax.swing.SwingUtilities;

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
        // TODO: add stuff
    }
}
