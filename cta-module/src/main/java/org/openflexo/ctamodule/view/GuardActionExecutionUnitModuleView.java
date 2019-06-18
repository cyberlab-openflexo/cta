/*
 * (c) Copyright 2013- Openflexo
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.openflexo.ctamodule.view;

import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.gina.model.FMLFIBBindingFactory;
import org.openflexo.view.FIBModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

/**
 * View showing GuardActionExecutionUnit
 */
@SuppressWarnings("serial")
public class GuardActionExecutionUnitModuleView extends FIBModuleView<FlexoConceptInstance> {

	public static Resource CTA_PROJECT_MODULE_VIEW_FIB = ResourceLocator.locateResource("Fib/GuardActionExecutionUnitPanel.fib");

	private final FlexoPerspective perspective;

	public GuardActionExecutionUnitModuleView(FlexoConceptInstance executionUnit, FlexoController controller,
			FlexoPerspective perspective) {
		super(executionUnit, controller, CTA_PROJECT_MODULE_VIEW_FIB, controller.getModule().getLocales());
		this.perspective = perspective;

		// FIBBrowserWidget<?, ?> browserView = (FIBBrowserWidget<?, ?>) getFIBView("ElementBrowser");
		// System.out.println("Found browser: " + browserView);
		/*browser = retrieveFIBBrowserNamed((FIBContainer) getFIBComponent(), "ElementBrowser");
		if (browser != null) {
			bindFlexoActionsToBrowser(browser);
		}*/

		setDataObject(executionUnit);

	}

	@Override
	public FlexoPerspective getPerspective() {
		return perspective;
	}

	@Override
	public FlexoConceptInstance getDataObject() {
		return (FlexoConceptInstance) super.getDataObject();
	}

	@Override
	public void initializeFIBComponent() {

		super.initializeFIBComponent();

		getFIBComponent().setBindingFactory(new FMLFIBBindingFactory(getDataObject().getVirtualModelInstance().getVirtualModel()));

	}

	/*public static class GuardActionExecutionUnitModuleViewFIBController extends CTAFIBController {
		public GuardActionExecutionUnitModuleViewFIBController(FIBComponent component, GinaViewFactory<?> viewFactory) {
			super(component, viewFactory);
		}
	
		public ImageIcon getProjectIcon() {
			return CTAIconLibrary.BIG_EXECUTION_UNIT_ICON;
		}
	
		public void openVMI(FMLRTVirtualModelInstance vmi) {
			getFlexoController().selectAndFocusObject(vmi);
		}
	}*/

}
