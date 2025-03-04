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

package io.github.atholton.sigmath.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.function.Predicate;

public class FilterKeyListener implements KeyListener, Serializable {
    private Predicate<? super Character> filter;

    public FilterKeyListener(Predicate<? super Character> filter) {
        this.filter = filter;
    }

    public static FilterKeyListener allows(char... characters) {
        return new FilterKeyListener(ch -> {
            for(char ch2 : characters) {
                if(ch == ch2) return true;
            }
            return false;
        });
    }

    public static FilterKeyListener disallows(char... characters) {
        return new FilterKeyListener(ch -> {
            for(char ch2 : characters) {
                if(ch == ch2) return false;
            }
            return true;
        });
    }

    public Predicate<? super Character> getFilter() {
        return filter;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(!filter.test(e.getKeyChar())) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
