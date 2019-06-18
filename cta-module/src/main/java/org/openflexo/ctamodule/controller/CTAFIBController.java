/**
 * 
 * Copyright (c) 2019, Openflexo
 * 
 * This file is part of Freemodellingeditor, a component of the software infrastructure 
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

package org.openflexo.ctamodule.controller;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.ctamodule.CTAIconLibrary;
import org.openflexo.ctamodule.model.CTAProjectNature;
import org.openflexo.fml.controller.FMLFIBController;
import org.openflexo.foundation.fml.FMLObject;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.gina.controller.FIBController;
import org.openflexo.gina.model.FIBComponent;
import org.openflexo.gina.swing.view.SwingViewFactory;
import org.openflexo.gina.utils.FIBInspector;
import org.openflexo.gina.view.GinaViewFactory;
import org.openflexo.localization.FlexoLocalization;

/**
 * Represents the controller of a FIBComponent in CTA prototype
 * 
 * 
 * @author sylvain
 */
public class CTAFIBController extends FMLFIBController {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CTAFIBController.class.getPackage().getName());

	public CTAFIBController(FIBComponent component, GinaViewFactory<?> viewFactory) {
		super(component, viewFactory);
		// Default parent localizer is the main localizer
		// setParentLocalizer(FlexoLocalization.getMainLocalizer());
	}

	public CTAFIBController(FIBComponent component) {
		super(component, SwingViewFactory.INSTANCE);
		// Default parent localizer is the main localizer
		setParentLocalizer(FlexoLocalization.getMainLocalizer());
	}

	@Override
	public CTAController getFlexoController() {
		return (CTAController) super.getFlexoController();
	}

	public CTAProjectNature getCTANature() {
		if (getFlexoController() != null) {
			return getFlexoController().getCTANature();
		}
		return null;
	}

	@Override
	public ImageIcon retrieveIconForObject(Object object) {
		if (getFlexoController() != null) {
			return getFlexoController().iconForObject(object);
		}
		return super.retrieveIconForObject(object);
	}

	public void newSimulation() {
		System.out.println("New simulation !");
	}

	public void openSimulation(FlexoConceptInstance simulation) {
		System.out.println("Open simulation " + simulation);
	}

	public ImageIcon getProjectIcon() {
		return CTAIconLibrary.BIG_EXECUTION_UNIT_ICON;
	}

	@Override
	public Class<? extends FIBController> getInspectorControllerClass() {
		return CTAFIBController.class;
	}

}
