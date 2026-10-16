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

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIDefaults;
import javax.swing.plaf.FontUIResource;

/**
 * PHPForge Dark — tema escuro derivado do FlatLaf Dark com paleta Darcula.
 * A paleta em si vem de {@code PHPForgeLaf.properties} (custom defaults do
 * FlatLaf); aqui entram apenas os ajustes que precisam de objetos Java
 * (fontes embutidas JetBrains Mono / Inter).
 */
public class PHPForgeLaf extends FlatDarkLaf {

    @Override
    public String getName() {
        return "PHPForge Dark"; // NOI18N
    }

    @Override
    public String getDescription() {
        return "PHPForge IDE dark theme (FlatLaf Dark with Darcula palette)"; // NOI18N
    }

    @Override
    public UIDefaults getDefaults() {
        UIDefaults defaults = super.getDefaults();

        FontUIResource uiFont = PHPForgeFonts.getUIFont();
        if (uiFont != null) {
            defaults.put("defaultFont", uiFont); // NOI18N
        }

        FontUIResource editorFont = PHPForgeFonts.getEditorFont();
        if (editorFont != null) {
            defaults.put("TextPane.font", editorFont); // NOI18N
            defaults.put("EditorPane.font", editorFont); // NOI18N
            defaults.put("TextArea.font", editorFont); // NOI18N
            defaults.put("TextField.font", uiFont != null ? uiFont : editorFont); // NOI18N
        }
        return defaults;
    }
}
