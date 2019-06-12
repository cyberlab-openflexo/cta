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

import javax.swing.ImageIcon;

import org.openflexo.ctamodule.CTAIconLibrary;
import org.openflexo.ctamodule.controller.CTAFIBController;
import org.openflexo.ctamodule.model.CTAProjectNature;
import org.openflexo.foundation.fml.rt.FMLRTVirtualModelInstance;
import org.openflexo.gina.model.FIBComponent;
import org.openflexo.gina.view.GinaViewFactory;
import org.openflexo.icon.IconFactory;
import org.openflexo.icon.IconLibrary;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.gina.model.FMLFIBBindingFactory;
import org.openflexo.view.FIBModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

/**
 * Main view for CTA module
 */
@SuppressWarnings("serial")
public class CTAProjectNatureModuleView extends FIBModuleView<CTAProjectNature> {

	public static Resource CTA_PROJECT_MODULE_VIEW_FIB = ResourceLocator.locateResource("Fib/CTAProjectNaturePanel.fib");

	private final FlexoPerspective perspective;

	public CTAProjectNatureModuleView(CTAProjectNature nature, FlexoController controller, FlexoPerspective perspective) {
		super(nature, controller, CTA_PROJECT_MODULE_VIEW_FIB, controller.getModule().getLocales());
		this.perspective = perspective;

		// FIBBrowserWidget<?, ?> browserView = (FIBBrowserWidget<?, ?>) getFIBView("ElementBrowser");
		// System.out.println("Found browser: " + browserView);
		/*browser = retrieveFIBBrowserNamed((FIBContainer) getFIBComponent(), "ElementBrowser");
		if (browser != null) {
			bindFlexoActionsToBrowser(browser);
		}*/

		setDataObject(nature);

	}

	@Override
	public FlexoPerspective getPerspective() {
		return perspective;
	}

	@Override
	public CTAProjectNature getDataObject() {
		return (CTAProjectNature) super.getDataObject();
	}

	@Override
	public void initializeFIBComponent() {

		super.initializeFIBComponent();

		getFIBComponent().setBindingFactory(new FMLFIBBindingFactory(getDataObject().getCTAViewPoint()));

	}

	public static class CTAProjectNatureModuleViewFIBController extends CTAFIBController {
		public CTAProjectNatureModuleViewFIBController(FIBComponent component, GinaViewFactory<?> viewFactory) {
			super(component, viewFactory);
		}

		public ImageIcon getProjectIcon() {
			return IconFactory.getImageIcon(IconLibrary.OPENFLEXO_NOTEXT_64, CTAIconLibrary.CTA_BIG_MARKER);
		}

		public void openVMI(FMLRTVirtualModelInstance vmi) {
			getFlexoController().selectAndFocusObject(vmi);
		}
	}

}
