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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.openflexo.ApplicationContext;
import org.openflexo.connie.exception.InvalidBindingException;
import org.openflexo.connie.exception.NullReferenceException;
import org.openflexo.connie.exception.TypeMismatchException;
import org.openflexo.ctamodule.CTA;
import org.openflexo.ctamodule.CTACst;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoActionFactory;
import org.openflexo.foundation.fml.rt.FMLRTVirtualModelInstance;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.localization.LocalizedDelegate;
import org.openflexo.toolbox.JavaUtils;
import org.openflexo.toolbox.PropertyChangedSupportDefaultImplementation;
import org.openflexo.toolbox.StringUtils;

/**
 * @author sylvain
 */
public class AllocateExecutionUnit extends CTAAction<AllocateExecutionUnit, FlexoConceptInstance, FlexoObject> {

	public static final FlexoActionFactory<AllocateExecutionUnit, FlexoConceptInstance, FlexoObject> ACTION_TYPE = new FlexoActionFactory<AllocateExecutionUnit, FlexoConceptInstance, FlexoObject>(
			"allocate_execution_unit", FlexoActionFactory.defaultGroup, FlexoActionFactory.NORMAL_ACTION_TYPE) {

		@Override
		public AllocateExecutionUnit makeNewAction(final FlexoConceptInstance focusedObject, final Vector<FlexoObject> globalSelection,
				final FlexoEditor editor) {
			return new AllocateExecutionUnit(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(final FlexoConceptInstance elementMapping, final Vector<FlexoObject> globalSelection) {
			return elementMapping != null && elementMapping.getFlexoConcept() != null
					&& elementMapping.getFlexoConcept().getName().equals(CTACst.MACHINERY_ALLOCATION_CONCEPT_NAME);
		}

		@Override
		public boolean isEnabledForSelection(final FlexoConceptInstance element, final Vector<FlexoObject> globalSelection) {
			return isVisibleForSelection(element, globalSelection);
		}
	};

	static {
		FlexoObjectImpl.addActionForClass(ACTION_TYPE, FlexoConceptInstance.class);
	}

	public enum RefinementChoice {
		NewExecutionUnit, RefineExecutionUnit
	}

	private RefinementChoice refinementChoice = RefinementChoice.NewExecutionUnit;
	private String executionUnitName;
	private FlexoConceptInstance refinedExecutionUnitDefinition;

	private FlexoConceptInstance machineryAllocation;

	public AllocateExecutionUnit(final FlexoConceptInstance focusedObject, final Vector<FlexoObject> globalSelection,
			final FlexoEditor editor) {
		super(ACTION_TYPE, focusedObject, globalSelection, editor);
		if (focusedObject != null) {
			updateConnectionEntries();
		}
	}

	@Override
	public LocalizedDelegate getLocales() {
		if (getServiceManager() instanceof ApplicationContext) {
			return ((ApplicationContext) getServiceManager()).getModuleLoader().getModule(CTA.class).getLoadedModuleInstance().getLocales();
		}
		return super.getLocales();
	}

	private FMLRTVirtualModelInstance newExecutionUnitDefinition;

	@Override
	protected void doAction(final Object context) throws FlexoException {

		System.out.println("Allocate execution unit...");

		FlexoConceptInstance machineryAllocation = getMachineryAllocation();
		System.out.println("machineryAllocation=" + machineryAllocation);

		try {
			FlexoConceptInstance executionUnitDefinition = getMachineryAllocation()
					.execute("this.allocateNewGuardActionExecutionUnit({$executionUnitName})", getExecutionUnitName());
			for (MachineryConnectionEntry entry : getConnectionEntries()) {
				FlexoConceptInstance machineryConnection = getMachineryAllocation().execute(
						"this.createMachineryConnection({$referenceName},{$relation},{$reference})", entry.getConnectionName(),
						entry.getRelation(), entry.getReference());
				System.out.println("machineryConnection=" + machineryConnection);
			}
			// newExecutionUnitDefinition =
			// systemNode.execute("this.createNewDiagram({$name},{$title},{$description})",
			// getExecutionUnitName(), getDiagramTitle(),
			// getDiagramDescription());
		} catch (Exception e) {
			throw new FlexoException(e);
		}

	}

	public FlexoConceptInstance getMachineryAllocation() {
		if (machineryAllocation == null) {
			return getFocusedObject();
		}
		return machineryAllocation;
	}

	public void setMachineryAllocation(FlexoConceptInstance machineryAllocation) {
		if ((machineryAllocation == null && this.machineryAllocation != null)
				|| (machineryAllocation != null && !machineryAllocation.equals(this.machineryAllocation))) {
			FlexoConceptInstance oldValue = this.machineryAllocation;
			this.machineryAllocation = machineryAllocation;
			getPropertyChangeSupport().firePropertyChange("machineryAllocation", oldValue, machineryAllocation);
		}
	}

	public String getExecutionUnitName() {
		if (StringUtils.isEmpty(executionUnitName)) {
			if (getMachineryAllocation() != null) {
				try {
					return getMachineryAllocation().execute("this.machinery.name");
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
		}
		return executionUnitName;
	}

	public void setExecutionUnitName(String executionUnitName) {
		if ((executionUnitName == null && this.executionUnitName != null)
				|| (executionUnitName != null && !executionUnitName.equals(this.executionUnitName))) {
			String oldValue = this.executionUnitName;
			this.executionUnitName = executionUnitName;
			getPropertyChangeSupport().firePropertyChange("executionUnitName", oldValue, executionUnitName);
		}
	}

	public RefinementChoice getRefinementChoice() {
		return refinementChoice;
	}

	public void setRefinementChoice(RefinementChoice refinementChoice) {
		if (refinementChoice != this.refinementChoice) {
			RefinementChoice oldValue = this.refinementChoice;
			this.refinementChoice = refinementChoice;
			getPropertyChangeSupport().firePropertyChange("refinementChoice", oldValue, refinementChoice);
		}
	}

	public FlexoConceptInstance getRefinedExecutionUnitDefinition() {
		return refinedExecutionUnitDefinition;
	}

	public void setRefinedExecutionUnitDefinition(FlexoConceptInstance refinedExecutionUnitDefinition) {
		if ((refinedExecutionUnitDefinition == null && this.refinedExecutionUnitDefinition != null)
				|| (refinedExecutionUnitDefinition != null
						&& !refinedExecutionUnitDefinition.equals(this.refinedExecutionUnitDefinition))) {
			FlexoConceptInstance oldValue = this.refinedExecutionUnitDefinition;
			this.refinedExecutionUnitDefinition = refinedExecutionUnitDefinition;
			getPropertyChangeSupport().firePropertyChange("refinedExecutionUnitDefinition", oldValue, refinedExecutionUnitDefinition);
		}
	}

	public FMLRTVirtualModelInstance getNewExecutionUnitDefinition() {
		return newExecutionUnitDefinition;
	}

	public FMLRTVirtualModelInstance getTSMVirtualModelInstance() {
		try {
			return getFocusedObject().execute("tsm");
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
		return null;
	}

	public boolean isAcceptableRefinedExecutionUnitDefinition(FlexoConceptInstance executionUnitDefinition) {
		System.out.println("Acceptable: " + executionUnitDefinition);
		return true;
	}

	private List<MachineryConnectionEntry> connectionEntries;

	public List<MachineryConnectionEntry> getConnectionEntries() {
		return connectionEntries;
	}

	private void updateConnectionEntries() {
		connectionEntries = new ArrayList<>();
		if (getMachineryAllocation() != null) {
			try {
				FlexoConceptInstance machinery = getMachineryAllocation().execute("this.machinery");
				List<FlexoConceptInstance> swapRelations = machinery.execute("this.swapRelations");
				FlexoConceptInstance systemNode = getMachineryAllocation().execute("this.container");
				for (FlexoConceptInstance swapRelation : swapRelations) {
					FlexoConceptInstance sourceMachinery = swapRelation.execute("this.source");
					FlexoConceptInstance targetMachinery = swapRelation.execute("this.target");
					FlexoConceptInstance oppositeMachinery;
					if (sourceMachinery == machinery) {
						oppositeMachinery = targetMachinery;
					}
					else {
						oppositeMachinery = sourceMachinery;
					}
					FlexoConceptInstance oppositeMachineryAllocation = systemNode.execute("this.getMachineryAllocation({$machinery})",
							oppositeMachinery);
					System.out.println("Entree avec ");
					System.out.println("swapRelation=" + swapRelation);
					System.out.println("oppositeMachinery=" + oppositeMachinery);
					System.out.println("oppositeMachineryAllocation=" + oppositeMachineryAllocation);
					String connectionName = JavaUtils.getVariableName(oppositeMachinery.toString());
					MachineryConnectionEntry entry = new MachineryConnectionEntry(connectionName, oppositeMachineryAllocation,
							swapRelation);
					connectionEntries.add(entry);
				}
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
	}

	public static class MachineryConnectionEntry extends PropertyChangedSupportDefaultImplementation {

		private Boolean selectIt;

		private String connectionName;
		private FlexoConceptInstance reference; // Instance of CTA MachineryAllocation
		private FlexoConceptInstance relation; // Instance of PimCA Relation

		public MachineryConnectionEntry(String connectionName, FlexoConceptInstance reference, FlexoConceptInstance relation) {
			super();
			this.connectionName = connectionName;
			this.reference = reference;
			this.relation = relation;
			this.selectIt = true;
		}

		public void delete() {
			reference = null;
			relation = null;
		}

		public String getConnectionName() {
			return connectionName;
		}

		public void setConnectionName(String connectionName) {
			if ((connectionName == null && this.connectionName != null)
					|| (connectionName != null && !connectionName.equals(this.connectionName))) {
				String oldValue = this.connectionName;
				this.connectionName = connectionName;
				getPropertyChangeSupport().firePropertyChange("connectionName", oldValue, connectionName);
			}
		}

		public FlexoConceptInstance getReference() {
			return reference;
		}

		public void setReference(FlexoConceptInstance reference) {
			if ((reference == null && this.reference != null) || (reference != null && !reference.equals(this.reference))) {
				FlexoConceptInstance oldValue = this.reference;
				this.reference = reference;
				getPropertyChangeSupport().firePropertyChange("reference", oldValue, reference);
			}
		}

		public FlexoConceptInstance getRelation() {
			return relation;
		}

		public void setRelation(FlexoConceptInstance relation) {
			if ((relation == null && this.relation != null) || (relation != null && !relation.equals(this.relation))) {
				FlexoConceptInstance oldValue = this.relation;
				this.relation = relation;
				getPropertyChangeSupport().firePropertyChange("relation", oldValue, relation);
			}
		}

		public Boolean getSelectIt() {
			return selectIt;
		}

		public void setSelectIt(Boolean selectIt) {
			if (selectIt != this.selectIt) {
				this.selectIt = selectIt;
				getPropertyChangeSupport().firePropertyChange("selectIt", (Boolean) !selectIt, selectIt);
			}
		}

	}

}
