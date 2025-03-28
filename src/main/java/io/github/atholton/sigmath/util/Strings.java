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

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Strings {
    private Strings() {
        throw new AssertionError("Do not instantiate class " + Strings.class);
    }

    public static String backspaceUntilMatches(String str, Pattern pattern) {
        Matcher m = pattern.matcher(str);
        int i = str.length();
        while (m.find()) {
            i = m.start();
        }

        return str.substring(0, i);
    }

    public static String backspaceUntilMatches(String str, String match) {
        int i = str.lastIndexOf(match);
        if(i <= 0) return "";
        return str.substring(0, i);
    }

    public static String backspaceUntilMatches(String str, String match, int from) {
        int i = str.lastIndexOf(match, from);
        if(i <= 0) return "";
        return str.substring(0, i);
    }

    public static String insertString(String src, String ins, int position) {
        return src.substring(0, position) + ins + src.substring(position);
    }

    /**
     * Replaces a substring that contains something afterwards within a string.
     * <p>
     * The opening and closing indicators will also be replaced with some given
     * replacement for each, which is the main difference between this function
     * and {@link String#replace(CharSequence, CharSequence)}.
     * 
     * @param str The string to work on.
     * @param old The substring to replace.
     * @param replacement The substring that replaces {@code old}.
     * @param open The opening delimiter.
     * @param openReplace The replacement of the opening delimiter.
     * @param close The closing delimiter.
     * @param closeReplace The replacement of the closing delimiter.
     * @return The processed string.
     */
    public static String replaceWithInsides(
        String str, String old, String replacement,
        String open, String openReplace,
        String close, String closeReplace
    ) {
        return replaceWithInsides(str, old, replacement, open, openReplace, close, closeReplace, 0, str.length());
    }

    /**
     * Replaces a substring that contains something afterwards within a string.
     * <p>
     * The opening and closing indicators will also be replaced with some given
     * replacement for each, which is the main difference between this function
     * and {@link String#replace(CharSequence, CharSequence)}.
     * 
     * @param str The string to work on.
     * @param old The substring to replace.
     * @param replacement The substring that replaces {@code old}.
     * @param open The opening delimiter.
     * @param openReplace The replacement of the opening delimiter.
     * @param close The closing delimiter.
     * @param closeReplace The replacement of the closing delimiter.
     * @param startIndex The start index, inclusive.
     * @param endIndex The end index, exclusive.
     * @return The processed string.
     */
    public static String replaceWithInsides(
        String str, String old, String replacement, 
        String open, String openReplace, 
        String close, String closeReplace,
        int startIndex, int endIndex
    ) {
        StringBuilder sb = new StringBuilder();
        replaceWithInsides(
            str, old, replacement, 
            open, openReplace, 
            close, closeReplace, 
            startIndex, endIndex, 
            sb);
        return sb.toString();
    }

    private static void replaceWithInsides(
        String str, String old, String replacement, 
        String open, String openReplace, 
        String close, String closeReplace,
        int startIndex, int endIndex,
        StringBuilder sb
    ) {
        if(startIndex >= endIndex) {
            return;
        }
        int i = str.indexOf(old, startIndex);
        int oldI = startIndex;

        // last will be used to add trailing content
        int last = oldI;

        // no instances of old, return original string from start.
        if(i < 0 || i >= endIndex) {
            String s = str.substring(startIndex, endIndex);
            sb.append(s);
        } else{
            while(i >= 0 && i < endIndex) {
                // adding everything outside of our concern
                String oi = str.substring(oldI, i);
                sb.append(oi)
                    .append(replacement); // then add the replacement
                
                // we have to search from the *end* of the opener
                int j0 = i + old.length();
                int j = str.indexOf(open, j0);

                boolean noOpenFound = j < 0;
                if(noOpenFound){
                    // no open found, so we add one
                    j = j0;
                }
        
                // add all content between found old and open
                sb.append(str.substring(j0, j))
                    .append(openReplace);
        
                int k0, k, l;
                k0 = j + open.length();
        
                k = str.indexOf(close, k0);
                l = str.indexOf(open, k0);
                
                // while there is an opener for the found closer,
                // find another
                while(l < k && l != -1) {
                    k = str.indexOf(close, k + 1);
                    l = str.indexOf(open, l + 1);
                }
        
                // no corrosponding closer found
                boolean noCloseFound = k < 0;
                if(noCloseFound) {
                    if(noOpenFound) {
                        k = j; // place close immediately after open
                    } else {
                        k = endIndex; // place close at the end
                    }
                }
        
        
                // sb.append(str.substring(k0, k))
                //     .append(closeReplace);
                // add inside content
                replaceWithInsides(
                    str, old, replacement, 
                    open, openReplace, 
                    close, closeReplace,
                    k0, k,
                    sb
                );
                sb.append(closeReplace);

                // search from the end of all replacements
                i = str.indexOf(old, k);
                if(noCloseFound) {
                    last = k; // we added a close, so no need to add close.length()
                } else {
                    // still doing a min check just in case.
                    last = Math.min(k + close.length(), endIndex);
                }
                oldI = last;
                // System.out.println("i=" + i);
                // System.out.println("sb=" + sb);
            }

            // this adds everything after the last replacement
            sb.append(str.substring(last, endIndex));
        }
    }


    public static String replaceBeforeWithInsides(
        String str, String old, String replacement, 
        String open, String openReplace, 
        String close, String closeReplace
    ) {
        return replaceBeforeWithInsides(
            str, old, replacement, 
            open, openReplace, 
            close, closeReplace,
            0, str.length()
        );
    }

    public static String replaceBeforeWithInsides(
        String str, String old, String replacement, 
        String open, String openReplace, 
        String close, String closeReplace,
        int startIndex, int endIndex
    ) {
        // StringBuilder sb = new StringBuilder();
        // replaceBeforeWithInsides(
        //     str, old, replacement, 
        //     open, openReplace, 
        //     close, closeReplace, 
        //     startIndex, endIndex, 
        //     sb);
        // return sb.toString();

        // This is really, really, oh so very dumb.
        // replace with proper solution ASAP
        // Reasons why this is bad:
        // 1. FOUR. SEPERATE. REVERSE CALLS.
        //   - This allocates an absurd amount of garbage memory, as
        //      each reversal already innefficiently requires two extra 
        //      strings worth of memory, and we do it four to seven times, 
        //      in addition to the extra memory needed by the replace in 
        //      the first place
        // 2. Debugging is going to be a nightmare
        //   - If there are bugs still left in here, print debugging is
        //      going to be really annoying.
        // 3. My feelings are hurt.
        return reversed(replaceWithInsides(
            reversed(str), reversed(old), reversed(replacement), 
            reversed(close), reversed(closeReplace), 
            reversed(open), reversed(openReplace), 
            startIndex, endIndex
        ));
    }

    // TODO: needs to be finished
    private static void replaceBeforeWithInsides(
        String str, String old, String replacement, 
        String open, String openReplace, 
        String close, String closeReplace,
        int startIndex, int endIndex,
        StringBuilder sb
    ) {
        if(startIndex >= endIndex) {
            return;
        }
        int i = str.lastIndexOf(old, endIndex);
        int oldI = endIndex;

        // last will be used to add trailing content
        int last = oldI;

        // no instances of old, return original string from start.
        if(i < startIndex) {
            String s = str.substring(startIndex, endIndex);
            sb.append(s);
        } else{
            while(i >= startIndex && i < endIndex) {
                // adding everything outside of our concern
                String oi = str.substring(oldI, i);
                sb.append(oi)
                    .append(replacement); // then add the replacement
                
                int j0 = i;
                int j = str.lastIndexOf(close, j0);

                boolean noOpenFound = j < 0;
                if(noOpenFound){
                    // no open found, so we add one
                    j = j0;
                }
        
                // add all content between found old and open
                sb.append(str.substring(j0, j))
                    .append(openReplace);
        
                int k0, k, l;
                k0 = j - open.length();
        
                k = str.indexOf(close, k0);
                l = str.indexOf(open, k0);
                
                // while there is an opener for the found closer,
                // find another
                while(l > k && l != -1) {
                    k = str.indexOf(close, k + 1);
                    l = str.indexOf(open, l + 1);
                }
        
                // no corrosponding closer found
                boolean noCloseFound = k < 0;
                if(noCloseFound) {
                    if(noOpenFound) {
                        k = j; // place close immediately after open
                    } else {
                        k = endIndex; // place close at the end
                    }
                }
        
        
                // sb.append(str.substring(k0, k))
                //     .append(closeReplace);
                // add inside content
                replaceWithInsides(
                    str, old, replacement, 
                    open, openReplace, 
                    close, closeReplace,
                    k0, k,
                    sb
                );
                sb.append(closeReplace);

                // search from the end of all replacements
                i = str.indexOf(old, k);
                if(noCloseFound) {
                    last = k; // we added a close, so no need to add close.length()
                } else {
                    // still doing a min check just in case.
                    last = Math.min(k + close.length(), endIndex);
                }
                oldI = last;
                // System.out.println("i=" + i);
                // System.out.println("sb=" + sb);
            }

            // this adds everything after the last replacement
            sb.append(str.substring(last, endIndex));
        }
    }
    
    public static String replaceWithBothSides (
        String str, String old, String replacement, 
        String open, String openReplace, 
        String close, String closeReplace
    ) {
        return replaceBeforeWithInsides(
            str, old, replacement, 
            open, openReplace, 
            close, closeReplace,
            0, str.length()
        );
    }
    public static String replaceWithBothSides (
        String str, String old, String replacement, 
        String open, String openReplace, 
        String close, String closeReplace,
        int startIndex, int endIndex
    ) {
        String first = replaceWithInsides(
            str, old, replacement, 
            open, openReplace, 
            close, closeReplace,
            startIndex, endIndex
        );
        String second = replaceBeforeWithInsides(
            first, old, replacement, 
            open, openReplace, 
            close, closeReplace,
            startIndex, endIndex
        );
        return second;
    }

    /**
     * Replaces a substring that contains something afterwards within a string.
     * <p>
     * The opening and closing indicators will also be replaced with some given
     * replacement for each, which is the main difference between this function
     * and {@link String#replace(CharSequence, CharSequence)}.
     * 
     * @param str The string to work on.
     * @param old The substring to replace.
     * @param replacement The substring that replaces {@code old}.
     * @param open The opening delimiter.
     * @param openReplace The replacement of the opening delimiter.
     * @param close The closing delimiter.
     * @param closeReplace The replacement of the closing delimiter.
     * @param grabUntil When to stop including characters for missing open/close
     * @return The processed string.
     */
    public static String replaceWithInsides(
        String str, String old, String replacement,
        String open, String openReplace,
        String close, String closeReplace,
        String grabUntil
    ) {
        return replaceWithInsides(str, old, replacement, open, openReplace, close, closeReplace, grabUntil, 0, str.length());
    }

    /**
     * Replaces a substring that contains something afterwards within a string.
     * <p>
     * The opening and closing indicators will also be replaced with some given
     * replacement for each, which is the main difference between this function
     * and {@link String#replace(CharSequence, CharSequence)}.
     * 
     * @param str The string to work on.
     * @param old The substring to replace.
     * @param replacement The substring that replaces {@code old}.
     * @param open The opening delimiter.
     * @param openReplace The replacement of the opening delimiter.
     * @param close The closing delimiter.
     * @param closeReplace The replacement of the closing delimiter.
     * @param grabUntil When to stop including characters for missing open/close
     * @param startIndex The start index, inclusive.
     * @param endIndex The end index, exclusive.
     * @return The processed string.
     */
    public static String replaceWithInsides(
        String str, String old, String replacement, 
        String open, String openReplace, 
        String close, String closeReplace,
        String grabUntil,
        int startIndex, int endIndex
    ) {
        StringBuilder sb = new StringBuilder();
        replaceWithInsides(
            str, old, replacement, 
            open, openReplace, 
            close, closeReplace,
            grabUntil,
            startIndex, endIndex, 
            sb);
        return sb.toString();
    }

    private static void replaceWithInsides(
        String str, String old, String replacement, 
        String open, String openReplace, 
        String close, String closeReplace,
        String grabUntil,
        int startIndex, int endIndex,
        StringBuilder sb
    ) {
        if(startIndex >= endIndex) {
            return;
        }
        int i = str.indexOf(old, startIndex);
        int oldI = startIndex;

        // last will be used to add trailing content
        int last = oldI;

        // no instances of old, return original string from start.
        if(i < 0 || i >= endIndex) {
            String s = str.substring(startIndex, endIndex);
            sb.append(s);
        } else{
            while(i >= 0 && i < endIndex) {
                // adding everything outside of our concern
                String oi = str.substring(oldI, i);
                sb.append(oi)
                    .append(replacement); // then add the replacement
                
                // we have to search from the *end* of the opener
                int j0 = i + old.length();
                int j = str.indexOf(open, j0);

                boolean noOpenFound = j < 0;
                if(noOpenFound){
                    // no open found, so we add one
                    j = j0;
                }
        
                // add all content between found old and open
                sb.append(str.substring(j0, j))
                    .append(openReplace);
        
                int k0, k, l;
                k0 = j + open.length();
        
                k = str.indexOf(close, k0);
                l = str.indexOf(open, k0);
                
                // while there is an opener for the found closer,
                // find another
                while(l < k && l != -1) {
                    k = str.indexOf(close, k + 1);
                    l = str.indexOf(open, l + 1);
                }
        
                // no corrosponding closer found
                boolean noCloseFound = k < 0;
                if(noCloseFound) {
                    k0 -= open.length();
                    if(noOpenFound) {
                        // try to grab characters
                        int j2 = str.indexOf(grabUntil, j);
                        if(j2 != -1) {
                            k = j2; // place close at the grabUntil
                        } else {
                            k = endIndex;
                        }
                    } else {
                        k = endIndex; // place close at the end
                    }
                }
        
        
                // sb.append(str.substring(k0, k))
                //     .append(closeReplace);
                // add inside content
                replaceWithInsides(
                    str, old, replacement, 
                    open, openReplace, 
                    close, closeReplace,
                    grabUntil,
                    k0, k,
                    sb
                );
                sb.append(closeReplace);

                // search from the end of all replacements
                i = str.indexOf(old, k);
                if(noCloseFound) {
                    last = k; // we added a close, so no need to add close.length()
                } else {
                    // still doing a min check just in case.
                    last = Math.min(k + close.length(), endIndex);
                }
                oldI = last;
                // System.out.println("i=" + i);
                // System.out.println("sb=" + sb);
            }

            // this adds everything after the last replacement
            sb.append(str.substring(last, endIndex));
        }
    }


    public static String replaceBeforeWithInsides(
        String str, String old, String replacement, 
        String open, String openReplace, 
        String close, String closeReplace,
        String grabUntil
    ) {
        return replaceBeforeWithInsides(
            str, old, replacement, 
            open, openReplace, 
            close, closeReplace,
            grabUntil,
            0, str.length()
        );
    }

    public static String replaceBeforeWithInsides(
        String str, String old, String replacement, 
        String open, String openReplace, 
        String close, String closeReplace,
        String grabUntil,
        int startIndex, int endIndex
    ) {
        // StringBuilder sb = new StringBuilder();
        // replaceBeforeWithInsides(
        //     str, old, replacement, 
        //     open, openReplace, 
        //     close, closeReplace, 
        //     startIndex, endIndex, 
        //     sb);
        // return sb.toString();

        // This is really, really, oh so very dumb.
        // replace with proper solution ASAP
        // Reasons why this is bad:
        // 1. FOUR. SEPERATE. REVERSE CALLS.
        //   - This allocates an absurd amount of garbage memory, as
        //      each reversal already innefficiently requires two extra 
        //      strings worth of memory, and we do it four to seven times, 
        //      in addition to the extra memory needed by the replace in 
        //      the first place
        // 2. Debugging is going to be a nightmare
        //   - If there are bugs still left in here, print debugging is
        //      going to be really annoying.
        // 3. My feelings are hurt.
        return reversed(replaceWithInsides(
            reversed(str), reversed(old), reversed(replacement), 
            reversed(close), reversed(closeReplace), 
            reversed(open), reversed(openReplace), 
            reversed(grabUntil),
            startIndex, endIndex
        ));
    }

    public static String reversed(String str) {
        if(str.length() < 2) return str; // mild optimiztion
        char[] data = str.toCharArray();
        reverse(data);
        return new String(data);
    }

    private static char[] reverse(char[] arr) {
        final int n = arr.length;
        for(int i = 0; i < n / 2; i++) {
            int j = n - i - 1;
            char temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }
}
