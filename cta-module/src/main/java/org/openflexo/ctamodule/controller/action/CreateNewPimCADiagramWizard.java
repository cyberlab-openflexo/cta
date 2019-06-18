/**
 * 
 * Copyright (c) 2014, Openflexo
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

package org.openflexo.ctamodule.controller.action;

import java.awt.Dimension;
import java.awt.Image;
import java.util.logging.Logger;

import org.openflexo.ApplicationContext;
import org.openflexo.components.wizard.FlexoActionWizard;
import org.openflexo.components.wizard.WizardStep;
import org.openflexo.ctamodule.CTAIconLibrary;
import org.openflexo.ctamodule.model.action.CreateNewPimCADiagram;
import org.openflexo.gina.annotation.FIBPanel;
import org.openflexo.icon.IconFactory;
import org.openflexo.icon.IconLibrary;
import org.openflexo.toolbox.StringUtils;
import org.openflexo.view.controller.FlexoController;

public class CreateNewPimCADiagramWizard extends FlexoActionWizard<CreateNewPimCADiagram> {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CreateNewPimCADiagramWizard.class.getPackage().getName());

	private final ConfigureNewPimcaDiagram configuration;

	private static final Dimension DIMENSIONS = new Dimension(600, 400);

	public CreateNewPimCADiagramWizard(CreateNewPimCADiagram action, FlexoController controller) {
		super(action, controller);
		addStep(configuration = new ConfigureNewPimcaDiagram());
	}

	@Override
	public Dimension getPreferredSize() {
		return DIMENSIONS;
	}

	@Override
	public String getWizardTitle() {
		return getAction().getLocales().localizedForKey("create_new_pimca_diagram");
	}

	@Override
	public Image getDefaultPageImage() {
		return IconFactory.getImageIcon(CTAIconLibrary.PIMCA_DIAGRAM_BIG_ICON, IconLibrary.NEW_32_32).getImage();
	}

	public ConfigureNewPimcaDiagram getConfiguration() {
		return configuration;
	}

	/**
	 * This step is used to configure new pimca diagram
	 * 
	 * @author sylvain
	 *
	 */
	@FIBPanel("Fib/Wizard/ConfigureNewVariable.fib")
	public class ConfigureNewPimcaDiagram extends WizardStep {

		public ApplicationContext getServiceManager() {
			return getController().getApplicationContext();
		}

		public CreateNewPimCADiagram getAction() {
			return CreateNewPimCADiagramWizard.this.getAction();
		}

		@Override
		public String getTitle() {
			return getAction().getLocales().localizedForKey("configure_new_pimca_diagram");
		}

		@Override
		public boolean isValid() {

			if (StringUtils.isEmpty(getDiagramTitle())) {
				setIssueMessage(getAction().getLocales().localizedForKey("you_should_provide_a_title_for_the_new_pimca_diagram"),
						IssueMessageType.ERROR);
				return false;
			}

			if (StringUtils.isEmpty(getDiagramDescription())) {
				setIssueMessage(getAction().getLocales().localizedForKey("it_is_recommanded_to_describe_new_pimca_diagram"),
						IssueMessageType.WARNING);
			}

			return true;

		}

		public String getDiagramTitle() {
			return getAction().getDiagramTitle();
		}

		public void setDiagramTitle(String diagramTitle) {
			if (!diagramTitle.equals(getDiagramTitle())) {
				String oldValue = getDiagramTitle();
				getAction().setDiagramTitle(diagramTitle);
				getPropertyChangeSupport().firePropertyChange("diagramTitle", oldValue, diagramTitle);
				getPropertyChangeSupport().firePropertyChange("diagramName", oldValue, getDiagramName());
				checkValidity();
			}
		}

		public String getDiagramName() {
			return getAction().getDiagramName();
		}

		public void setDiagramName(String diagramName) {
			if (!diagramName.equals(getDiagramName())) {
				String oldValue = getDiagramName();
				getAction().setDiagramName(diagramName);
				getPropertyChangeSupport().firePropertyChange("diagramName", oldValue, diagramName);
				checkValidity();
			}
		}

		public String getDiagramDescription() {
			return getAction().getDiagramDescription();
		}

		public void setDiagramDescription(String description) {
			if (!description.equals(getDiagramDescription())) {
				String oldValue = getDiagramDescription();
				getAction().setDiagramDescription(description);
				getPropertyChangeSupport().firePropertyChange("diagramDescription", oldValue, description);
				checkValidity();
			}
		}

	}

}
