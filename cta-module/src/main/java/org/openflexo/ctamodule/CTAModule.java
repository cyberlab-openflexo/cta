/**
 * 
 * Copyright (c) 2013-2019, Openflexo
 * Copyright (c) 2011-2012, AgileBirds
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

package org.openflexo.ctamodule;

import java.util.logging.Logger;

import org.openflexo.ApplicationContext;
import org.openflexo.ctamodule.controller.CTAController;
import org.openflexo.module.FlexoModule;
import org.openflexo.view.controller.FlexoController;

/**
 * CTA module
 * 
 * @author yourname
 */
public class CTAModule extends FlexoModule<CTAModule> {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CTAModule.class.getPackage().getName());

	public static final String CTA_MODULE_SHORT_NAME = "CTA";
	public static final String CTA_MODULE_NAME = "Cyber Threat Analysis";

	/*private JDianaInteractiveEditor<?> screenshotController;
	private JDrawingView<?> screenshot = null;
	private boolean drawWorkingArea;
	private FlexoObject screenshotObject;*/

	public CTAModule(ApplicationContext applicationContext) throws Exception {
		super(applicationContext);
	}

	@Override
	public String getLocalizationDirectory() {
		return "FlexoLocalization/CTAModule";
	}

	@Override
	protected FlexoController createControllerForModule() {
		return new CTAController(this);
	}

	@Override
	public CTA getModule() {
		return getApplicationContext().getModuleLoader().getModule(CTA.class);
	}

	public CTAController getCTAController() {
		return (CTAController) getFlexoController();
	}

	@Override
	public CTAPreferences getPreferences() {
		return (CTAPreferences) super.getPreferences();
	}

	@Override
	public boolean close() {
		if (getApplicationContext().getResourceManager().getUnsavedResources().size() == 0) {
			return super.close();
		}
		else {
			if (getCTAController().reviewModifiedResources()) {
				return super.close();
			}
			else {
				return false;
			}
		}
	}

}
