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
import org.openflexo.ctamodule.model.CTAProjectNature;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoActionFactory;
import org.openflexo.foundation.fml.rt.FMLRTVirtualModelInstance;
import org.openflexo.localization.LocalizedDelegate;

/**
 * @author sylvain
 */
public class StartNewSimulation extends CTAAction<StartNewSimulation, CTAProjectNature, FlexoObject> {

	public static final FlexoActionFactory<StartNewSimulation, CTAProjectNature, FlexoObject> ACTION_TYPE = new FlexoActionFactory<StartNewSimulation, CTAProjectNature, FlexoObject>(
			"start_new_simulation", FlexoActionFactory.defaultGroup, FlexoActionFactory.NORMAL_ACTION_TYPE) {

		@Override
		public StartNewSimulation makeNewAction(final CTAProjectNature focusedObject, final Vector<FlexoObject> globalSelection,
				final FlexoEditor editor) {
			return new StartNewSimulation(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(final CTAProjectNature elementMapping, final Vector<FlexoObject> globalSelection) {
			return true;
		}

		@Override
		public boolean isEnabledForSelection(final CTAProjectNature element, final Vector<FlexoObject> globalSelection) {
			return true;
		}
	};

	static {
		FlexoObjectImpl.addActionForClass(ACTION_TYPE, CTAProjectNature.class);
	}

	private String simulationName;
	private String simulationDescription;

	public StartNewSimulation(final CTAProjectNature focusedObject, final Vector<FlexoObject> globalSelection, final FlexoEditor editor) {
		super(ACTION_TYPE, focusedObject, globalSelection, editor);
	}

	@Override
	public LocalizedDelegate getLocales() {
		if (getServiceManager() instanceof ApplicationContext) {
			return ((ApplicationContext) getServiceManager()).getModuleLoader().getModule(CTA.class).getLoadedModuleInstance().getLocales();
		}
		return super.getLocales();
	}

	private FMLRTVirtualModelInstance newSimulation;

	@Override
	protected void doAction(final Object context) throws FlexoException {

		System.out.println("Create new PimCA simulation...");

		FMLRTVirtualModelInstance ctaView = getFocusedObject().getCTAInstance().getAccessedVirtualModelInstance();

		System.out.println("ctaView=" + ctaView);

		try {
			newSimulation = ctaView.execute("this.startNewSimulation({$name},{$description})", getSimulationName(),
					getSimulationDescription());
		} catch (Exception e) {
			throw new FlexoException(e);
		}

	}

	public String getSimulationName() {
		return simulationName;
	}

	public void setSimulationName(String simulationName) {
		if ((simulationName == null && this.simulationName != null)
				|| (simulationName != null && !simulationName.equals(this.simulationName))) {
			String oldValue = this.simulationName;
			this.simulationName = simulationName;
			getPropertyChangeSupport().firePropertyChange("simulationName", oldValue, simulationName);
		}
	}

	public String getSimulationDescription() {
		return simulationDescription;
	}

	public void setSimulationDescription(String simulationDescription) {
		if ((simulationDescription == null && this.simulationDescription != null)
				|| (simulationDescription != null && !simulationDescription.equals(this.simulationDescription))) {
			String oldValue = this.simulationDescription;
			this.simulationDescription = simulationDescription;
			getPropertyChangeSupport().firePropertyChange("simulationDescription", oldValue, simulationDescription);
		}
	}

	public FMLRTVirtualModelInstance getNewSimulation() {
		return newSimulation;
	}

}
