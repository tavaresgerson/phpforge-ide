/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.phpforge.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.plaf.FontUIResource;

/**
 * Carrega as fontes embutidas do PHPForge IDE (arquivos dentro deste JAR):
 *
 * <ul>
 * <li><b>JetBrains Mono</b> — fonte do editor (fallbacks: DejaVu Sans Mono,
 * Liberation Mono, Monospaced);</li>
 * <li><b>Inter</b> — fonte da UI (fallback: a fonte padrão do LAF).</li>
 * </ul>
 */
final class PHPForgeFonts {

    private static final Logger LOG = Logger.getLogger(PHPForgeFonts.class.getName());

    private static final String MONO_RESOURCE = "/org/netbeans/modules/phpforge/ui/fonts/JetBrainsMono-Medium.ttf"; // NOI18N
    private static final String UI_RESOURCE = "/org/netbeans/modules/phpforge/ui/fonts/Inter-Regular.ttf"; // NOI18N
    private static final String[] MONO_FALLBACKS = {"DejaVu Sans Mono", "Liberation Mono", "Monospaced"}; // NOI18N

    private static FontUIResource editorFont;
    private static FontUIResource uiFont;

    private PHPForgeFonts() {
    }

    static synchronized void register() {
        if (editorFont != null) {
            return;
        }
        Font mono = load(MONO_RESOURCE);
        if (mono != null) {
            editorFont = new FontUIResource(mono.deriveFont(Font.PLAIN, 13f));
        } else {
            for (String fallback : MONO_FALLBACKS) {
                Font f = new Font(fallback, Font.PLAIN, 13);
                if (!"Dialog".equals(f.getFamily())) { // NOI18N
                    editorFont = new FontUIResource(f);
                    break;
                }
            }
        }

        Font inter = load(UI_RESOURCE);
        if (inter != null) {
            uiFont = new FontUIResource(inter.deriveFont(Font.PLAIN, 13f));
        }
    }

    private static Font load(String resource) {
        try (InputStream in = PHPForgeFonts.class.getResourceAsStream(resource)) {
            if (in == null) {
                LOG.log(Level.WARNING, "Font resource not found: {0}", resource); // NOI18N
                return null;
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, in);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font;
        } catch (FontFormatException | IOException ex) {
            LOG.log(Level.WARNING, "Cannot load font " + resource, ex); // NOI18N
            return null;
        }
    }

    static synchronized FontUIResource getEditorFont() {
        return editorFont;
    }

    static synchronized FontUIResource getUIFont() {
        return uiFont;
    }
}
