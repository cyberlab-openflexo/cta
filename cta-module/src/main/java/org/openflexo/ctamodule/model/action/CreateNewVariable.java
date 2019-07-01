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

import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import org.openflexo.ApplicationContext;
import org.openflexo.connie.DataBinding;
import org.openflexo.connie.exception.InvalidBindingException;
import org.openflexo.connie.exception.NullReferenceException;
import org.openflexo.connie.exception.TypeMismatchException;
import org.openflexo.connie.type.PrimitiveType;
import org.openflexo.ctamodule.CTA;
import org.openflexo.ctamodule.CTACst;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoActionFactory;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.action.CreateInspectorEntry;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.localization.LocalizedDelegate;

/**
 * @author sylvain
 */
public class CreateNewVariable extends CTAAction<CreateNewVariable, FlexoConceptInstance, FlexoObject> {

	public static final FlexoActionFactory<CreateNewVariable, FlexoConceptInstance, FlexoObject> ACTION_TYPE = new FlexoActionFactory<CreateNewVariable, FlexoConceptInstance, FlexoObject>(
			"create_new_variable", FlexoActionFactory.defaultGroup, FlexoActionFactory.NORMAL_ACTION_TYPE) {

		@Override
		public CreateNewVariable makeNewAction(final FlexoConceptInstance focusedObject, final Vector<FlexoObject> globalSelection,
				final FlexoEditor editor) {
			return new CreateNewVariable(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(final FlexoConceptInstance executionUnit, final Vector<FlexoObject> globalSelection) {
			return executionUnit != null && executionUnit.getFlexoConcept() != null
					&& executionUnit.getFlexoConcept().getName().contains(CTACst.TSM_EXECUTION_UNIT_DEFINITION_CONCEPT_NAME);
		}

		@Override
		public boolean isEnabledForSelection(final FlexoConceptInstance element, final Vector<FlexoObject> globalSelection) {
			return isVisibleForSelection(element, globalSelection);
		}
	};

	static {
		FlexoObjectImpl.addActionForClass(ACTION_TYPE, FlexoConceptInstance.class);
	}

	private String variableName;
	private PrimitiveType primitiveType;
	private String description;

	private FlexoConceptInstance executionUnitDefinition;

	public CreateNewVariable(final FlexoConceptInstance focusedObject, final Vector<FlexoObject> globalSelection,
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

	private FlexoConceptInstance newVariable;

	@Override
	protected void doAction(final Object context) throws FlexoException {

		System.out.println("Create new variable...");

		FlexoConceptInstance executionUnitDefinition = getExecutionUnitDefinition();
		System.out.println("executionUnitDefinition=" + executionUnitDefinition);

		try {
			newVariable = executionUnitDefinition.execute("this.createNewVariable({$name},{$primitiveType},{$description})",
					getVariableName(), getPrimitiveType(), getDescription());
		} catch (Exception e) {
			throw new FlexoException(e);
		}

		try {
			FlexoConcept supportConcept = executionUnitDefinition.execute("supportConcept");
			CreateInspectorEntry createInspectorEntry = CreateInspectorEntry.actionType.makeNewEmbeddedAction(supportConcept.getInspector(),
					null, this);
			createInspectorEntry.setEntryName(getVariableName());
			createInspectorEntry.setEntryType(getPrimitiveType().getType());
			createInspectorEntry.setData(new DataBinding<>(getVariableName()));
			createInspectorEntry.setIndex(supportConcept.getInspector().getEntries().size() - 1);
			createInspectorEntry.doAction();
		} catch (TypeMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullReferenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public FlexoConceptInstance getExecutionUnitDefinition() {
		if (executionUnitDefinition == null) {
			return getFocusedObject();
		}
		return executionUnitDefinition;
	}

	public void setExecutionUnitDefinition(FlexoConceptInstance executionUnitDefinition) {
		if ((executionUnitDefinition == null && this.executionUnitDefinition != null)
				|| (executionUnitDefinition != null && !executionUnitDefinition.equals(this.executionUnitDefinition))) {
			FlexoConceptInstance oldValue = this.executionUnitDefinition;
			this.executionUnitDefinition = executionUnitDefinition;
			getPropertyChangeSupport().firePropertyChange("executionUnitDefinition", oldValue, executionUnitDefinition);
		}
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		if ((variableName == null && this.variableName != null) || (variableName != null && !variableName.equals(this.variableName))) {
			String oldValue = this.variableName;
			this.variableName = variableName;
			getPropertyChangeSupport().firePropertyChange("variableName", oldValue, variableName);
		}
	}

	public PrimitiveType getPrimitiveType() {
		return primitiveType;
	}

	public void setPrimitiveType(PrimitiveType primitiveType) {
		if ((primitiveType == null && this.primitiveType != null) || (primitiveType != null && !primitiveType.equals(this.primitiveType))) {
			PrimitiveType oldValue = this.primitiveType;
			this.primitiveType = primitiveType;
			getPropertyChangeSupport().firePropertyChange("primitiveType", oldValue, primitiveType);
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

	public FlexoConceptInstance getNewVariable() {
		return newVariable;
	}

}
