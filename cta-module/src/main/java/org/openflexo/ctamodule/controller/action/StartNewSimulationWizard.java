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
import org.openflexo.ctamodule.model.action.StartNewSimulation;
import org.openflexo.gina.annotation.FIBPanel;
import org.openflexo.icon.IconFactory;
import org.openflexo.icon.IconLibrary;
import org.openflexo.toolbox.StringUtils;
import org.openflexo.view.controller.FlexoController;

public class StartNewSimulationWizard extends FlexoActionWizard<StartNewSimulation> {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(StartNewSimulationWizard.class.getPackage().getName());

	private final ConfigureNewSimulation configuration;

	private static final Dimension DIMENSIONS = new Dimension(600, 400);

	public StartNewSimulationWizard(StartNewSimulation action, FlexoController controller) {
		super(action, controller);
		addStep(configuration = new ConfigureNewSimulation());
	}

	@Override
	public Dimension getPreferredSize() {
		return DIMENSIONS;
	}

	@Override
	public String getWizardTitle() {
		return getAction().getLocales().localizedForKey("start_new_simulation");
	}

	@Override
	public Image getDefaultPageImage() {
		return IconFactory.getImageIcon(CTAIconLibrary.BIG_SIMULATION_ICON, IconLibrary.BIG_NEW_MARKER).getImage();
	}

	public ConfigureNewSimulation getConfiguration() {
		return configuration;
	}

	/**
	 * This step is used to configure new pimca diagram
	 * 
	 * @author sylvain
	 *
	 */
	@FIBPanel("Fib/Wizard/ConfigureNewSimulation.fib")
	public class ConfigureNewSimulation extends WizardStep {

		public ApplicationContext getServiceManager() {
			return getController().getApplicationContext();
		}

		public StartNewSimulation getAction() {
			return StartNewSimulationWizard.this.getAction();
		}

		@Override
		public String getTitle() {
			return getAction().getLocales().localizedForKey("configure_new_simulation");
		}

		@Override
		public boolean isValid() {

			if (StringUtils.isEmpty(getSimulationName())) {
				setIssueMessage(getAction().getLocales().localizedForKey("you_should_provide_a_name_for_the_new_simulation"),
						IssueMessageType.ERROR);
				return false;
			}

			if (StringUtils.isEmpty(getSimulationDescription())) {
				setIssueMessage(getAction().getLocales().localizedForKey("it_is_recommanded_to_describe_simulation"),
						IssueMessageType.WARNING);
			}

			return true;

		}

		public String getSimulationName() {
			return getAction().getSimulationName();
		}

		public void setSimulationName(String simulationName) {
			if (!simulationName.equals(getSimulationName())) {
				String oldValue = getSimulationName();
				getAction().setSimulationName(simulationName);
				getPropertyChangeSupport().firePropertyChange("simulationName", oldValue, simulationName);
				checkValidity();
			}
		}

		public String getSimulationDescription() {
			return getAction().getSimulationDescription();
		}

		public void setSimulationDescription(String description) {
			if (!description.equals(getSimulationDescription())) {
				String oldValue = getSimulationDescription();
				getAction().setSimulationDescription(description);
				getPropertyChangeSupport().firePropertyChange("simulationDescription", oldValue, description);
				checkValidity();
			}
		}

	}

}
