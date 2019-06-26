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

import org.openflexo.ctamodule.controller.SimulationPerspective;
import org.openflexo.foundation.fml.rt.FMLRTVirtualModelInstance;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.gina.model.FMLFIBBindingFactory;
import org.openflexo.view.FIBModuleView;
import org.openflexo.view.controller.FlexoController;

/**
 * View showing Execution (simulation)
 */
@SuppressWarnings("serial")
public class ExecutionModuleView extends FIBModuleView<FMLRTVirtualModelInstance> {

	public static Resource EXECUTION_MODULE_VIEW_FIB = ResourceLocator.locateResource("Fib/ExecutionPanel.fib");

	private final SimulationPerspective perspective;

	public ExecutionModuleView(FMLRTVirtualModelInstance execution, FlexoController controller, SimulationPerspective perspective) {
		super(execution, controller, EXECUTION_MODULE_VIEW_FIB, controller.getModule().getLocales());
		this.perspective = perspective;

		// FIBBrowserWidget<?, ?> browserView = (FIBBrowserWidget<?, ?>) getFIBView("ElementBrowser");
		// System.out.println("Found browser: " + browserView);
		/*browser = retrieveFIBBrowserNamed((FIBContainer) getFIBComponent(), "ElementBrowser");
		if (browser != null) {
			bindFlexoActionsToBrowser(browser);
		}*/

		setDataObject(execution);

	}

	@Override
	public SimulationPerspective getPerspective() {
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

	@Override
	public void willHide() {

		super.willHide();

		getPerspective().hideSimulationBrowser();
	}

	@Override
	public void willShow() {

		super.willShow();

		FMLRTVirtualModelInstance execution = getRepresentedObject();
		getPerspective().showSimulationBrowser(execution);
	}

}
