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

package org.openflexo.ctamodule.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.connie.exception.InvalidBindingException;
import org.openflexo.connie.exception.NullReferenceException;
import org.openflexo.connie.exception.TypeMismatchException;
import org.openflexo.ctamodule.CTACst;
import org.openflexo.ctamodule.CTAIconLibrary;
import org.openflexo.ctamodule.CTAModule;
import org.openflexo.ctamodule.controller.action.CTAControllerActionInitializer;
import org.openflexo.ctamodule.model.CTAProjectNature;
import org.openflexo.ctamodule.view.CTAMainPane;
import org.openflexo.ctamodule.view.CTAWelcomePanelModuleView;
import org.openflexo.ctamodule.view.ConvertToCTAProjectView;
import org.openflexo.ctamodule.view.menu.CTAMenuBar;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.VirtualModel;
import org.openflexo.foundation.fml.rt.FMLRTVirtualModelInstance;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.module.FlexoModule.WelcomePanel;
import org.openflexo.selection.MouseSelectionManager;
import org.openflexo.technologyadapter.diagram.DiagramTechnologyAdapter;
import org.openflexo.technologyadapter.diagram.controller.DiagramTechnologyAdapterController;
import org.openflexo.view.FlexoMainPane;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.ControllerActionInitializer;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;
import org.openflexo.view.menu.FlexoMenuBar;

/**
 * Controller for CTA module
 * 
 * @author yourname
 */
public class CTAController extends FlexoController {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CTAController.class.getPackage().getName());

	private PimCAPerspective pimcaPerspective;
	private ExecutionUnitPerspective executionUnitPerspective;
	private SimulationPerspective simulationPerspective;

	private DiagramTechnologyAdapterController diagramTAC = null;

	/**
	 * Default constructor
	 */
	public CTAController(CTAModule module) {
		super(module);
	}

	@Override
	protected void initializePerspectives() {
		this.addToPerspectives(pimcaPerspective = new PimCAPerspective(this));
		this.addToPerspectives(executionUnitPerspective = new ExecutionUnitPerspective(this));
		this.addToPerspectives(simulationPerspective = new SimulationPerspective(this));
	}

	public PimCAPerspective getPimCAPerspective() {
		return pimcaPerspective;
	}

	public ExecutionUnitPerspective getExecutionUnitPerspective() {
		return executionUnitPerspective;
	}

	public SimulationPerspective getSimulationPerspective() {
		return simulationPerspective;
	}

	@Override
	protected MouseSelectionManager createSelectionManager() {
		return new CTASelectionManager(this);
	}

	@Override
	public ControllerActionInitializer createControllerActionInitializer() {
		return new CTAControllerActionInitializer(this);
	}

	/**
	 * Creates a new instance of MenuBar for the module this controller refers to
	 * 
	 * @return
	 */
	@Override
	protected FlexoMenuBar createNewMenuBar() {
		return new CTAMenuBar(this);
	}

	@Override
	public FlexoObject getDefaultObjectToSelect(FlexoProject<?> project) {
		if (project != null && project.hasNature(CTAProjectNature.class)) {
			return project.getNature(CTAProjectNature.class);
		}
		return project;
	}

	@Override
	protected FlexoMainPane createMainPane() {
		return new CTAMainPane(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModuleView<?> makeWelcomePanel(WelcomePanel<?> welcomePanel, FlexoPerspective perspective) {
		return new CTAWelcomePanelModuleView((WelcomePanel<CTAModule>) welcomePanel, this, perspective);
	}

	@Override
	public ModuleView<?> makeDefaultProjectView(FlexoProject<?> project, FlexoPerspective perspective) {
		return new ConvertToCTAProjectView(project, this, perspective);
	}

	public CTAProjectNature getCTANature() {
		if (getProject() != null) {
			return getProject().getNature(CTAProjectNature.class);
		}
		return null;
	}

	@Override
	protected void updateEditor(final FlexoEditor from, final FlexoEditor to) {
		super.updateEditor(from, to);
		FlexoProject<?> project = (to != null ? to.getProject() : null);
		pimcaPerspective.setProject(project);
	}

	@Override
	public ImageIcon iconForObject(final Object object) {
		if (object instanceof CTAProjectNature) {
			return CTAIconLibrary.CTA_SMALL_ICON;
		}
		if (object instanceof FMLRTVirtualModelInstance) {
			VirtualModel type = ((FMLRTVirtualModelInstance) object).getVirtualModel();
			/*if (type != null) {
				if (type.getName().equals(CTACst.PIMCA_DIAGRAM_VM_NAME)) {
					return DiagramIconLibrary.DIAGRAM_ICON;
				}
			}*/

			return super.iconForObject(object);
		}

		if (object instanceof FlexoConceptInstance) {
			FlexoConcept type = ((FlexoConceptInstance) object).getFlexoConcept();
			if (type != null) {
				if (type.getName().equals(CTACst.MACHINERY_ALLOCATION_CONCEPT_NAME)) {
					try {
						return iconForObject(((FlexoConceptInstance) object).execute("machinery"));
					} catch (TypeMismatchException | NullReferenceException | InvocationTargetException | InvalidBindingException e) {
						e.printStackTrace();
					}
				}
				else if (type.getName().equals(CTACst.TSM_VARIABLE_CONCEPT_NAME)) {
					try {
						return iconForObject(((FlexoConceptInstance) object).execute("property"));
					} catch (TypeMismatchException | NullReferenceException | InvocationTargetException | InvalidBindingException e) {
						e.printStackTrace();
					}
				}
				else if (type.getName().equals(CTACst.TSM_GUARD_ACTION_CONCEPT_NAME)) {
					try {
						return iconForObject(((FlexoConceptInstance) object).execute("actionScheme"));
					} catch (TypeMismatchException | NullReferenceException | InvocationTargetException | InvalidBindingException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return super.iconForObject(object);
	}

	/**
	 * Helper function to ease access to DiagramTAController
	 * 
	 * @return
	 */
	public DiagramTechnologyAdapterController getDiagramTAC() {
		if (diagramTAC == null) {
			DiagramTechnologyAdapter diagramTA = this.getTechnologyAdapter(DiagramTechnologyAdapter.class);
			diagramTAC = (DiagramTechnologyAdapterController) getTechnologyAdapterController(diagramTA);
		}
		return this.diagramTAC;
	}

}
