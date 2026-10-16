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

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.spi.dashboard.DashboardDisplayer;
import org.netbeans.spi.dashboard.DashboardWidget;
import org.netbeans.spi.dashboard.WidgetElement;
import org.openide.awt.Actions;
import org.openide.util.NbBundle;

/**
 * Tela de boas-vindas do PHPForge IDE: ações rápidas PHP-centric.
 */
@NbBundle.Messages({
    "LBL_WelcomeTitle=Bem-vindo ao PHPForge IDE",
    "LBL_WelcomeTagline=~/ forge your code",
    "LBL_NewPhpProject=Novo Projeto PHP...",
    "LBL_CloneGit=Clonar do Git...",
    "LBL_OpenFolder=Abrir Projeto ou Pasta..."
})
public class GettingStartedWidget implements DashboardWidget {

    private final List<WidgetElement> elements;

    public GettingStartedWidget() {
        List<WidgetElement> l = new ArrayList<>();
        l.add(WidgetElement.subheading(Bundle.LBL_WelcomeTagline()));
        l.add(WidgetElement.actionNoIcon(delegate(Bundle.LBL_NewPhpProject(),
                "Project", "org.netbeans.modules.project.ui.NewProject"))); // NOI18N
        l.add(WidgetElement.actionNoIcon(delegate(Bundle.LBL_CloneGit(),
                "Git", "org.netbeans.modules.git.ui.clone.CloneAction"))); // NOI18N
        l.add(WidgetElement.actionNoIcon(delegate(Bundle.LBL_OpenFolder(),
                "Project", "org.netbeans.modules.project.ui.OpenProject"))); // NOI18N
        elements = List.copyOf(l);
    }

    private static Action delegate(String name, String category, String id) {
        Action original = Actions.forID(category, id);
        return new AbstractAction(name) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (original != null) {
                    original.actionPerformed(e);
                }
            }
        };
    }

    @Override
    public String title(DashboardDisplayer.Panel panel) {
        return Bundle.LBL_WelcomeTitle();
    }

    @Override
    public List<WidgetElement> elements(DashboardDisplayer.Panel panel) {
        return elements;
    }
}
