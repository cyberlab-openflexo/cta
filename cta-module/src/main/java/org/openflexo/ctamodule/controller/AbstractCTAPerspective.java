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
import javax.swing.JPanel;

import org.openflexo.ctamodule.CTAIconLibrary;
import org.openflexo.ctamodule.model.CTAProjectNature;
import org.openflexo.ctamodule.view.CTAProjectNatureModuleView;
import org.openflexo.ctamodule.widget.CTAProjectBrowser;
import org.openflexo.ctamodule.widget.GenericProjectBrowser;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.fml.rt.FMLRTVirtualModelInstance;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.module.FlexoModule.WelcomePanel;
import org.openflexo.technologyadapter.diagram.controller.DiagramTechnologyAdapterController;
import org.openflexo.technologyadapter.diagram.controller.diagrameditor.FMLControlledDiagramEditor;
import org.openflexo.technologyadapter.diagram.controller.diagrameditor.FMLControlledDiagramModuleView;
import org.openflexo.technologyadapter.diagram.fml.FMLControlledDiagramVirtualModelInstanceNature;
import org.openflexo.technologyadapter.gina.fml.FMLControlledFIBFlexoConceptInstanceNature;
import org.openflexo.technologyadapter.gina.fml.FMLControlledFIBVirtualModelInstanceNature;
import org.openflexo.technologyadapter.gina.view.FMLControlledFIBFlexoConceptInstanceModuleView;
import org.openflexo.technologyadapter.gina.view.FMLControlledFIBVirtualModelInstanceModuleView;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.NaturePerspective;

public abstract class AbstractCTAPerspective extends NaturePerspective<CTAProjectNature> {

	static final Logger logger = Logger.getLogger(AbstractCTAPerspective.class.getPackage().getName());

	private CTAProjectBrowser browser;
	private GenericProjectBrowser genericBrowser;

	/**
	 * @param controller
	 * @param name
	 */
	public AbstractCTAPerspective(String name, FlexoController controller) {
		super(name, controller);

		// browser = new CTAProjectBrowser(controller);

		// setTopLeftView(browser);

		if (controller.getProject() != null) {
			setProject(controller.getProject());
		}
	}

	@Override
	public Class<CTAProjectNature> getNatureClass() {
		return CTAProjectNature.class;
	}

	@Override
	public void willShow() {
		super.willShow();
		updateBrowser(getController().getProject(), false);
	}

	public void setProject(final FlexoProject<?> project) {

		updateBrowser(project, true);
	}

	public void updateBrowser(final FlexoProject<?> project, boolean rebuildBrowser) {

		System.out.println("updating browser " + browser + " with " + project);

		if (project != null) {
			if (project.hasNature(CTAProjectNature.class)) {
				if (browser == null || rebuildBrowser || browser.getDataObject().getProject() != project) {
					browser = new CTAProjectBrowser(getController());
				}
				if (browser != null) {
					browser.setRootObject(project.getNature(CTAProjectNature.class));
				}
				setTopLeftView(browser);
			}
			else {
				if (genericBrowser == null || rebuildBrowser || genericBrowser.getDataObject() != project) {
					genericBrowser = new GenericProjectBrowser(getController());
				}
				if (genericBrowser != null) {
					genericBrowser.setRootObject(project);
				}
				setTopLeftView(genericBrowser);
			}
		}
		else {
			setTopLeftView(new JPanel());
		}
	}

	public CTAProjectBrowser getBrowser() {
		return browser;
	}

	public GenericProjectBrowser getGenericBrowser() {
		return genericBrowser;
	}

	@Override
	public String getWindowTitleforObject(final FlexoObject object, final FlexoController controller) {
		if (object instanceof WelcomePanel) {
			return "Welcome";
		}
		else if (object instanceof FlexoProject) {
			return ((FlexoProject<?>) object).getName();
		}
		else if (object instanceof CTAProjectNature) {
			if (((CTAProjectNature) object).getOwner() != null) {
				return ((CTAProjectNature) object).getOwner().getName();
			}
			return null;
		}
		else if (object instanceof FMLRTVirtualModelInstance) {
			return ((FMLRTVirtualModelInstance) object).getTitle();
		}
		else if (object instanceof FlexoConceptInstance) {
			return ((FlexoConceptInstance) object).getStringRepresentation();
		}
		else {
			return "Object has no title";
		}
	}

	@Override
	public ImageIcon getActiveIcon() {
		return CTAIconLibrary.CTA_SMALL_ICON;
	}

	@Override
	public ModuleView<?> createModuleViewForObject(FlexoObject object) {

		if (object instanceof CTAProjectNature) {
			return new CTAProjectNatureModuleView((CTAProjectNature) object, getController(), this);
		}

		if (object instanceof FMLRTVirtualModelInstance) {
			if (((FMLRTVirtualModelInstance) object).hasNature(FMLControlledFIBVirtualModelInstanceNature.INSTANCE)) {
				return new FMLControlledFIBVirtualModelInstanceModuleView((FMLRTVirtualModelInstance) object, getController(), this,
						getController().getModuleLocales());
			}
			if (((FMLRTVirtualModelInstance) object).hasNature(FMLControlledDiagramVirtualModelInstanceNature.INSTANCE)) {
				FMLRTVirtualModelInstance diagramVMI = (FMLRTVirtualModelInstance) object;
				DiagramTechnologyAdapterController diagramTAC = ((CTAController) getController()).getDiagramTAC();
				FMLControlledDiagramEditor editor = new FMLControlledDiagramEditor(diagramVMI, false, getController(),
						diagramTAC.getToolFactory());
				return new FMLControlledDiagramModuleView(editor, this);
			}
		}

		if (object instanceof FlexoConceptInstance) {
			if (((FlexoConceptInstance) object).hasNature(FMLControlledFIBFlexoConceptInstanceNature.INSTANCE)) {
				return new FMLControlledFIBFlexoConceptInstanceModuleView((FlexoConceptInstance) object, getController(), this,
						getController().getModuleLocales());
			}
		}

		// In all other cases...
		return super.createModuleViewForObject(object);

	}

	@Override
	public boolean hasModuleViewForObject(FlexoObject object) {
		if (object instanceof WelcomePanel) {
			return true;
		}
		if (object instanceof FlexoProject) {
			return true;
		}
		if (object instanceof CTAProjectNature) {
			return true;
		}
		if (object instanceof FMLRTVirtualModelInstance) {
			// FML-controlled FIB
			if (((FMLRTVirtualModelInstance) object).hasNature(FMLControlledFIBVirtualModelInstanceNature.INSTANCE)) {
				return true;
			}
			// FML-controlled diagram
			if (((FMLRTVirtualModelInstance) object).hasNature(FMLControlledDiagramVirtualModelInstanceNature.INSTANCE)) {
				return true;
			}
			return false;
		}
		if (object instanceof FlexoConceptInstance) {
			if (((FlexoConceptInstance) object).hasNature(FMLControlledFIBFlexoConceptInstanceNature.INSTANCE)) {
				return true;
			}
		}
		return super.hasModuleViewForObject(object);
	}

	@Override
	public FlexoObject getDefaultObject(FlexoObject proposedObject) {
		if (proposedObject instanceof FlexoProject && ((FlexoProject<?>) proposedObject).hasNature(CTAProjectNature.class)) {
			return ((FlexoProject<?>) proposedObject).getNature(CTAProjectNature.class);
		}

		return super.getDefaultObject(proposedObject);
	}

}
