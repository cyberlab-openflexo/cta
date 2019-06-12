/**
 * 
 * Copyright (c) 2019, Openflexo
 * 
 * This file is part of Flexo-ui, a component of the software infrastructure 
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
import org.openflexo.ctamodule.view.SimulationPerspectiveModuleView;
import org.openflexo.ctamodule.widget.PimCAModelBrowser;
import org.openflexo.ctamodule.widget.SimulationProjectBrowser;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.fml.rt.FMLRTVirtualModelInstance;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.FlexoController;

public class SimulationPerspective extends AbstractCTAPerspective {

	static final Logger logger = Logger.getLogger(SimulationPerspective.class.getPackage().getName());

	private final PimCAModelBrowser pimCAModelBrowser;

	/**
	 * @param controller
	 * @param name
	 */
	public SimulationPerspective(FlexoController controller) {
		super("simulation_perspective", controller);
		pimCAModelBrowser = new PimCAModelBrowser(controller);
	}

	public PimCAModelBrowser getPimCAModelBrowser() {
		return pimCAModelBrowser;
	}

	public void showPimCAModelBrowser(FMLRTVirtualModelInstance pimCAModel) {
		setBottomLeftView(getPimCAModelBrowser());
		getPimCAModelBrowser().setDataObject(pimCAModel);
	}

	public void hidePimCAModelBrowser() {
		setBottomLeftView(null);
	}

	@Override
	public String getWindowTitleforObject(final FlexoObject object, final FlexoController controller) {
		return super.getWindowTitleforObject(object, controller);
	}

	@Override
	public ImageIcon getActiveIcon() {
		return CTAIconLibrary.SIMULATION_PERSPECTIVE_ICON;
	}

	@Override
	public ModuleView<?> createModuleViewForObject(FlexoObject object) {

		if (object instanceof CTAProjectNature) {
			return new SimulationPerspectiveModuleView((CTAProjectNature) object, getController(), this);
		}

		/*if (object instanceof FMLRTVirtualModelInstance) {
			if (((FMLRTVirtualModelInstance) object).hasNature(FMLControlledDiagramVirtualModelInstanceNature.INSTANCE)) {
				FMLRTVirtualModelInstance diagramVMI = (FMLRTVirtualModelInstance) object;
				VirtualModel type = diagramVMI.getVirtualModel();
				if (type != null) {
					if (type.getName().equals(CTACst.PIMCA_DIAGRAM_VM_NAME)) {
						DiagramTechnologyAdapterController diagramTAC = ((CTAController) getController()).getDiagramTAC();
						FMLControlledDiagramEditor editor = new FMLControlledDiagramEditor(diagramVMI, false, getController(),
								diagramTAC.getToolFactory());
						return new PimCADiagramModuleView(editor, this);
					}
				}
			}
		}*/

		// In all other cases...
		return super.createModuleViewForObject(object);

	}

	@Override
	public boolean hasModuleViewForObject(FlexoObject object) {
		/*if (object instanceof FMLRTVirtualModelInstance) {
			// FML-controlled diagram
			if (((FMLRTVirtualModelInstance) object).hasNature(FMLControlledDiagramVirtualModelInstanceNature.INSTANCE)) {
				FMLRTVirtualModelInstance diagramVMI = (FMLRTVirtualModelInstance) object;
				VirtualModel type = diagramVMI.getVirtualModel();
				if (type != null) {
					if (type.getName().equals(CTACst.PIMCA_DIAGRAM_VM_NAME)) {
						return true;
					}
				}
			}
		}*/
		return super.hasModuleViewForObject(object);
	}

	@Override
	public FlexoObject getDefaultObject(FlexoObject proposedObject) {
		return super.getDefaultObject(proposedObject);
	}

	@Override
	public SimulationProjectBrowser makeProjectBrowser() {
		return new SimulationProjectBrowser(getController());
	}

}
