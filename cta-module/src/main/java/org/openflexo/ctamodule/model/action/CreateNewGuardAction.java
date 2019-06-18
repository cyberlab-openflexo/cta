/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Formose prototype, a component of the software infrastructure 
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

package org.openflexo.ctamodule.model.action;

import java.util.Vector;

import org.openflexo.ApplicationContext;
import org.openflexo.ctamodule.CTA;
import org.openflexo.ctamodule.CTACst;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoActionFactory;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.localization.LocalizedDelegate;

/**
 * @author sylvain
 */
public class CreateNewGuardAction extends CTAAction<CreateNewGuardAction, FlexoConceptInstance, FlexoObject> {

	public static final FlexoActionFactory<CreateNewGuardAction, FlexoConceptInstance, FlexoObject> ACTION_TYPE = new FlexoActionFactory<CreateNewGuardAction, FlexoConceptInstance, FlexoObject>(
			"create_new_guard_action", FlexoActionFactory.defaultGroup, FlexoActionFactory.NORMAL_ACTION_TYPE) {

		@Override
		public CreateNewGuardAction makeNewAction(final FlexoConceptInstance focusedObject, final Vector<FlexoObject> globalSelection,
				final FlexoEditor editor) {
			return new CreateNewGuardAction(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(final FlexoConceptInstance executionUnit, final Vector<FlexoObject> globalSelection) {
			return executionUnit != null && executionUnit.getFlexoConcept() != null
					&& executionUnit.getFlexoConcept().getName().equals(CTACst.TSM_GUARD_ACTION_EXECUTION_UNIT_CONCEPT_NAME);
		}

		@Override
		public boolean isEnabledForSelection(final FlexoConceptInstance element, final Vector<FlexoObject> globalSelection) {
			return isVisibleForSelection(element, globalSelection);
		}
	};

	static {
		FlexoObjectImpl.addActionForClass(ACTION_TYPE, FlexoConceptInstance.class);
	}

	private String actionName;
	private String description;

	private FlexoConceptInstance executionUnit;

	public CreateNewGuardAction(final FlexoConceptInstance focusedObject, final Vector<FlexoObject> globalSelection,
			final FlexoEditor editor) {
		super(ACTION_TYPE, focusedObject, globalSelection, editor);
	}

	@Override
	public LocalizedDelegate getLocales() {
		if (getServiceManager() instanceof ApplicationContext) {
			return ((ApplicationContext) getServiceManager()).getModuleLoader().getModule(CTA.class).getLoadedModuleInstance().getLocales();
		}
		return super.getLocales();
	}

	private FlexoConceptInstance newGuardAction;

	@Override
	protected void doAction(final Object context) throws FlexoException {

		System.out.println("Create new guard_action...");

		FlexoConceptInstance executionUnit = getExecutionUnit();
		System.out.println("executionUnit=" + executionUnit);

		try {
			newGuardAction = executionUnit.execute("this.createNewGuardAction({$name},{$description})", getActionName(), getDescription());
		} catch (Exception e) {
			throw new FlexoException(e);
		}

	}

	public FlexoConceptInstance getExecutionUnit() {
		if (executionUnit == null) {
			return getFocusedObject();
		}
		return executionUnit;
	}

	public void setExecutionUnit(FlexoConceptInstance executionUnit) {
		if ((executionUnit == null && this.executionUnit != null) || (executionUnit != null && !executionUnit.equals(this.executionUnit))) {
			FlexoConceptInstance oldValue = this.executionUnit;
			this.executionUnit = executionUnit;
			getPropertyChangeSupport().firePropertyChange("executionUnit", oldValue, executionUnit);
		}
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		if ((actionName == null && this.actionName != null) || (actionName != null && !actionName.equals(this.actionName))) {
			String oldValue = this.actionName;
			this.actionName = actionName;
			getPropertyChangeSupport().firePropertyChange("actionName", oldValue, actionName);
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if ((description == null && this.description != null) || (description != null && !description.equals(this.description))) {
			String oldValue = this.description;
			this.description = description;
			getPropertyChangeSupport().firePropertyChange("description", oldValue, description);
		}
	}

	public FlexoConceptInstance getNewGuardAction() {
		return newGuardAction;
	}

}
