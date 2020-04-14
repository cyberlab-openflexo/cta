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
import org.openflexo.ctamodule.model.action.AllocateExecutionUnit;
import org.openflexo.ctamodule.model.action.AllocateExecutionUnit.RefinementChoice;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.gina.annotation.FIBPanel;
import org.openflexo.icon.IconFactory;
import org.openflexo.icon.IconLibrary;
import org.openflexo.toolbox.StringUtils;
import org.openflexo.view.controller.FlexoController;

public class AllocateExecutionUnitWizard extends FlexoActionWizard<AllocateExecutionUnit> {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(AllocateExecutionUnitWizard.class.getPackage().getName());

	private final ConfigureAllocateExecutionUnit configuration;

	private static final Dimension DIMENSIONS = new Dimension(600, 400);

	public AllocateExecutionUnitWizard(AllocateExecutionUnit action, FlexoController controller) {
		super(action, controller);
		addStep(configuration = new ConfigureAllocateExecutionUnit());
	}

	@Override
	public Dimension getPreferredSize() {
		return DIMENSIONS;
	}

	@Override
	public String getWizardTitle() {
		return getAction().getLocales().localizedForKey("allocate_new_execution_unit");
	}

	@Override
	public Image getDefaultPageImage() {
		return IconFactory.getImageIcon(CTAIconLibrary.BIG_EXECUTION_UNIT_ICON, IconLibrary.BIG_NEW_MARKER).getImage();
	}

	public ConfigureAllocateExecutionUnit getConfiguration() {
		return configuration;
	}

	/**
	 * This step is used to configure new allocation
	 * 
	 * @author sylvain
	 *
	 */
	@FIBPanel("Fib/Wizard/ConfigureAllocateExecutionUnit.fib")
	public class ConfigureAllocateExecutionUnit extends WizardStep {

		public ApplicationContext getServiceManager() {
			return getController().getApplicationContext();
		}

		public AllocateExecutionUnit getAction() {
			return AllocateExecutionUnitWizard.this.getAction();
		}

		@Override
		public String getTitle() {
			return getAction().getLocales().localizedForKey("configure_execution_unit_allocation");
		}

		@Override
		public boolean isValid() {

			if (StringUtils.isEmpty(getExecutionUnitName())) {
				setIssueMessage(getAction().getLocales().localizedForKey("you_should_provide_a_name_for_the_new_execution_unit_definition"),
						IssueMessageType.ERROR);
				return false;
			}

			return true;

		}

		public String getExecutionUnitName() {
			return getAction().getExecutionUnitName();
		}

		public void setExecutionUnitName(String executionUnitName) {
			if (!executionUnitName.equals(getExecutionUnitName())) {
				String oldValue = getExecutionUnitName();
				getAction().setExecutionUnitName(executionUnitName);
				getPropertyChangeSupport().firePropertyChange("executionUnitName", oldValue, executionUnitName);
				checkValidity();
			}
		}

		public RefinementChoice getRefinementChoice() {
			return getAction().getRefinementChoice();
		}

		public void setRefinementChoice(RefinementChoice refinementChoice) {
			if (!refinementChoice.equals(getExecutionUnitName())) {
				String oldValue = getExecutionUnitName();
				getAction().setRefinementChoice(refinementChoice);
				getPropertyChangeSupport().firePropertyChange("refinementChoice", oldValue, refinementChoice);
				checkValidity();
			}
		}

		public FlexoConceptInstance getRefinedExecutionUnitDefinition() {
			return getAction().getRefinedExecutionUnitDefinition();
		}

		public void setRefinedExecutionUnitDefinition(FlexoConceptInstance executionUnitDefinition) {
			if (!executionUnitDefinition.equals(getRefinedExecutionUnitDefinition())) {
				FlexoConceptInstance oldValue = getRefinedExecutionUnitDefinition();
				getAction().setRefinedExecutionUnitDefinition(executionUnitDefinition);
				getPropertyChangeSupport().firePropertyChange("refinedExecutionUnitDefinition", oldValue, executionUnitDefinition);
				checkValidity();
			}
		}

		
		
	}

}
