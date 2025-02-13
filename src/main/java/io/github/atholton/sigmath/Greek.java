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

/**
 * A container class for the Greek letters as characters and strings.
 */
public final class Greek {
    private Greek() {
        throw new AssertionError("Do not instantiate class " + Greek.class);
    }

    /**
     * The Greek alphabet, without the end-word variant of sigma ({@code ς}), alternating so
     * that each pair of letters appears as uppercase-lower case.
     * An English example would be: {@code AaBbCcDdEeFf...}.
     */
    public static final String ALTERNATING_ALPHABET = "ΑαΒβΓγΔδΕεΖζΗηΘθΙιΚκΛλΜμΝνΞξΟοΠπΡρΣσΤτΥυΦφΧχΨψΩω";

    /**
     * The uppercae Greek alphabet.
     */
    public static final String ALPHABET = "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ";

    /**
     * The lowercase Greek alphabet, without the end-word variant of sigma (ς).
     */
    public static final String alphabet = "αβγδεζηθικλμνξοπρστυφχψω";

    /**
     * Uppercase Greek Alpha
     */
    public static final char ALPHA = 'Α';

    /**
     * Lowercase Greek Alpha
     */
    public static final char alpha   = 'α';

    /**
     * Uppercase Greek Beta
     */
    public static final char BETA    = 'Β';
    
    /**
     * Lowercase Greek Beta
     */
    public static final char beta    = 'β';

    /**
     * Uppercase Greek Gamma
     */
    public static final char GAMMA   = 'Γ';

    /**
     * Lowercase Greek Gamma
     */
    public static final char gamma   = 'γ';

    /**
     * Uppercase Greek Delta
     */
    public static final char DELTA   = 'Δ';

    /**
     * Lowercase Greek Delta
     */
    public static final char delta   = 'δ';

    /**
     * Uppercase Greek Epsilon
     */
    public static final char EPSILON = 'Ε';

    /**
     * Lowercase Greek Epsilon
     */
    public static final char epsilon = 'ε';

    /**
     * Uppercase Greek Zeta
     */
    public static final char ZETA    = 'Ζ';

    /**
     * Lowercase Greek Zeta
     */
    public static final char zeta    = 'ζ';

    /**
     * Uppercase Greek Eta
     */
    public static final char ETA     = 'Η';

    /**
     * Lowercase Greek Eta
     */
    public static final char eta     = 'η';

    /**
     * Uppercase Greek Theta
     */
    public static final char THETA   = 'Θ';

    /**
     * Lowercase Greek Theta
     */
    public static final char theta   = 'θ';

    /**
     * Uppercase Greek Iota
     */
    public static final char IOTA    = 'Ι';

    /**
     * Lowercase Greek Iota
     */
    public static final char iota    = 'ι';

    /**
     * Uppercase Greek Kappa
     */
    public static final char KAPPA   = 'Κ';

    /**
     * Lowercase Greek Kappa
     */
    public static final char kappa   = 'κ';

    /**
     * Uppercase Greek Lambda
     */
    public static final char LAMBDA  = 'Λ';

    /**
     * Lowercase Greek Lambda
     */
    public static final char lambda  = 'λ';

    /**
     * Uppercase Greek Mu
     */
    public static final char MU      = 'Μ';

    /**
     * Lowercase Greek Mu
     */
    public static final char mu      = 'μ';

    /**
     * Uppercase Greek Nu
     */
    public static final char NU      = 'Ν';

    /**
     * Lowercase Greek Nu
     */
    public static final char nu      = 'ν';

    /**
     * Uppercase Greek Xi
     */
    public static final char XI      = 'Ξ';

    /**
     * Lowercase Greek Xi
     */
    public static final char xi      = 'ξ';

    /**
     * Uppercase Greek Omicron
     */
    public static final char OMICRON = 'Ο';

    /**
     * Lowercase Greek Omicron
     */
    public static final char omicron = 'ο';

    /**
     * Uppercase Greek Pi
     */
    public static final char PI      = 'Π';

    /**
     * Lowercase Greek Pi
     */
    public static final char pi      = 'π';

    /**
     * Uppercase Greek Rho
     */
    public static final char RHO     = 'Ρ';

    /**
     * Lowercase Greek Rho
     */
    public static final char rho     = 'ρ';
    
    /**
     * Uppercase Greek Sigma
     */
    public static final char SIGMA   = 'Σ';
    
    /**
     * Lowercase Greek Sigma
     */
    public static final char sigma   = 'σ';
    
    /**
     * End-Word varient of Lowercase Greek Sigma
     */
    public static final char sigma2  = 'ς';
    
    /**
     * Uppercase Greek Tau
     */
    public static final char TAU     = 'Τ';
    
    /**
     * Lowercase Greek Tau
     */
    public static final char tau     = 'τ';
    
    /**
     * Uppercase Greek Upsilon
     */
    public static final char UPSILON = 'Υ';
    
    /**
     * Lowercase Greek Upsilon
     */
    public static final char upsilon = 'υ';
    
    /**
     * Uppercase Greek Phi
     */
    public static final char PHI     = 'Φ';
    
    /**
     * Lowercase Greek Phi
     */
    public static final char phi     = 'φ';
    
    /**
     * Uppercase Greek Chi
     */
    public static final char CHI     = 'Χ';
    
    /**
     * Lowercase Greek Chi
     */
    public static final char chi     = 'χ';
    
    /**
     * Uppercase Greek Psi
     */
    public static final char PSI     = 'Ψ';
    
    /**
     * Lowercase Greek Psi
     */
    public static final char psi     = 'ψ';
    
    /**
     * Uppercase Greek Omega
     */
    public static final char OMEGA   = 'Ω';
    
    /**
     * Lowercase Greek Omega
     */
    public static final char omega   = 'ω';
}
