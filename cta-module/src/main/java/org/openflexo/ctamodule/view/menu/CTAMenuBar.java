/**
 * 
 * Copyright (c) 2019, Openflexo
 * 
 * This file is part of Flexovieweditor, a component of the software infrastructure 
 * developed at Openflexo.
 * 
 * 
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either 
 * version 1.1 of the License, or any later version ), which is available at 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any 
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * 
 * You can redistribute it and/or modify under the terms of either of these licenses
 * 
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 *
 *          Additional permission under GNU GPL version 3 section 7
 *
 *          If you modify this Program, or any covered work, by linking or 
 *          combining it with software containing parts covered by the terms 
 *          of EPL 1.0, the licensors of this Program grant you additional permission
 *          to convey the resulting work. * 
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.openflexo.org/license.html for details.
 * 
 * 
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 * 
 */

package org.openflexo.ctamodule.view.menu;

import org.openflexo.ctamodule.CTA;
import org.openflexo.ctamodule.controller.CTAController;
import org.openflexo.module.Module;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.menu.EditMenu;
import org.openflexo.view.menu.FileMenu;
import org.openflexo.view.menu.FlexoMenuBar;
import org.openflexo.view.menu.ToolsMenu;
import org.openflexo.view.menu.WindowMenu;

/**
 * Class representing menus related to this module
 * 
 * @author yourname
 */
@SuppressWarnings("serial")
public class CTAMenuBar extends FlexoMenuBar {

	private CTAFileMenu fileMenu;
	private CTAEditMenu editMenu;
	private CTAToolsMenu toolsMenu;

	public CTAMenuBar(CTAController controller) {
		super(controller, controller.getApplicationContext().getModuleLoader().getModule(CTA.class));
	}

	/**
	 * Build if required and return WKF 'File' menu. This method overrides the default one defined on superclass
	 * 
	 * @param controller
	 * @return a CTAFileMenu instance
	 */
	@Override
	public FileMenu getFileMenu(FlexoController controller) {
		if (fileMenu == null) {
			fileMenu = new CTAFileMenu((CTAController) controller);
		}
		return fileMenu;
	}

	/**
	 * Build if required and return WKF 'Edit' menu. This method overrides the default one defined on superclass
	 * 
	 * @param controller
	 * @return a CTAEditMenu instance
	 */
	@Override
	public EditMenu getEditMenu(FlexoController controller) {
		if (editMenu == null) {
			editMenu = new CTAEditMenu((CTAController) controller);
		}
		return editMenu;
	}

	/**
	 * Build if required and return WKF 'Window' menu. This method overrides the default one defined on superclass
	 * 
	 * @param controller
	 * @return a CTAWindowMenu instance
	 */
	@Override
	public WindowMenu getWindowMenu(FlexoController controller, Module<?> module) {
		if (windowMenu == null) {
			windowMenu = new CTAWindowMenu((CTAController) controller);
		}
		return windowMenu;
	}

	/**
	 * Build if required and return WKF 'Tools' menu. This method overrides the default one defined on superclass
	 * 
	 * @param controller
	 * @return a CTAToolsMenu instance
	 */
	public ToolsMenu getToolsMenu(FlexoController controller, Module<?> module) {
		if (toolsMenu == null) {
			toolsMenu = new CTAToolsMenu((CTAController) controller);
		}
		return toolsMenu;
	}

}
