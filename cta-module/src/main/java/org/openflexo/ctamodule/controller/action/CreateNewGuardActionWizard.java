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
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import org.openflexo.ApplicationContext;
import org.openflexo.components.wizard.FlexoActionWizard;
import org.openflexo.components.wizard.WizardStep;
import org.openflexo.connie.exception.InvalidBindingException;
import org.openflexo.connie.exception.NullReferenceException;
import org.openflexo.connie.exception.TypeMismatchException;
import org.openflexo.ctamodule.model.action.CreateNewGuardAction;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.gina.annotation.FIBPanel;
import org.openflexo.icon.FMLIconLibrary;
import org.openflexo.icon.IconFactory;
import org.openflexo.icon.IconLibrary;
import org.openflexo.toolbox.StringUtils;
import org.openflexo.view.controller.FlexoController;

public class CreateNewGuardActionWizard extends FlexoActionWizard<CreateNewGuardAction> {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CreateNewGuardActionWizard.class.getPackage().getName());

	private final ConfigureNewGuardAction configuration;

	private static final Dimension DIMENSIONS = new Dimension(600, 400);

	public CreateNewGuardActionWizard(CreateNewGuardAction action, FlexoController controller) {
		super(action, controller);
		addStep(configuration = new ConfigureNewGuardAction());
	}

	@Override
	public Dimension getPreferredSize() {
		return DIMENSIONS;
	}

	@Override
	public String getWizardTitle() {
		return getAction().getLocales().localizedForKey("create_new_guard_action");
	}

	@Override
	public Image getDefaultPageImage() {
		return IconFactory.getImageIcon(FMLIconLibrary.FLEXO_BEHAVIOUR_BIG_ICON, IconLibrary.NEW_32_32).getImage();
	}

	public ConfigureNewGuardAction getConfiguration() {
		return configuration;
	}

	/**
	 * This step is used to configure new pimca diagram
	 * 
	 * @author sylvain
	 *
	 */
	@FIBPanel("Fib/Wizard/ConfigureNewGuardAction.fib")
	public class ConfigureNewGuardAction extends WizardStep {

		public ApplicationContext getServiceManager() {
			return getController().getApplicationContext();
		}

		public CreateNewGuardAction getAction() {
			return CreateNewGuardActionWizard.this.getAction();
		}

		@Override
		public String getTitle() {
			return getAction().getLocales().localizedForKey("configure_new_guard_action");
		}

		@Override
		public boolean isValid() {

			if (StringUtils.isEmpty(getActionName())) {
				setIssueMessage(getAction().getLocales().localizedForKey("you_should_provide_a_name_for_the_new_action"),
						IssueMessageType.ERROR);
				return false;
			}

			try {
				FlexoConcept concept = getAction().getFocusedObject().execute("supportConcept");
				if (concept.getDeclaredFlexoBehaviour(getActionName() + "()") != null) {
					setIssueMessage(getAction().getLocales().localizedForKey("an_action_with_that_name_already_exists"),
							IssueMessageType.ERROR);
					return false;
				}
			} catch (TypeMismatchException | NullReferenceException | InvocationTargetException | InvalidBindingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (StringUtils.isEmpty(getDescription())) {
				setIssueMessage(getAction().getLocales().localizedForKey("you_can_provide_a_description_for_the_new_guard_action"),
						IssueMessageType.WARNING);
				return true;
			}

			return true;

		}

		public String getActionName() {
			return getAction().getActionName();
		}

		public void setActionName(String actionName) {
			if (!actionName.equals(getActionName())) {
				String oldValue = getActionName();
				getAction().setActionName(actionName);
				getPropertyChangeSupport().firePropertyChange("actionName", oldValue, getActionName());
				checkValidity();
			}
		}

		public String getDescription() {
			return getAction().getDescription();
		}

		public void setDescription(String description) {
			if (!description.equals(getDescription())) {
				String oldValue = getDescription();
				getAction().setDescription(description);
				getPropertyChangeSupport().firePropertyChange("description", oldValue, getDescription());
				checkValidity();
			}
		}

	}

}
