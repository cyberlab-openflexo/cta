/**
 * 
 * Copyright (c) 2019, Openflexo
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

package org.openflexo.ctamodule;

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.icon.IconLibrary;
import org.openflexo.icon.IconMarker;
import org.openflexo.icon.ImageIconResource;
import org.openflexo.rm.ResourceLocator;

/**
 * Utility class containing all icons used in context of CTAModule
 * 
 * @author yourname
 * 
 */
public class CTAIconLibrary extends IconLibrary {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CTAIconLibrary.class.getPackage().getName());

	// Module icons
	public static final ImageIcon CTA_SMALL_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/CTA/module-cta-16.png"));
	public static final ImageIcon CTA_MEDIUM_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/CTA/module-cta-32.png"));
	public static final ImageIcon CTA_MEDIUM_ICON_WITH_HOVER = new ImageIconResource(
			ResourceLocator.locateResource("Icons/CTA/module-cta-hover-32.png"));
	public static final ImageIcon CTA_BIG_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/CTA/module-cta-hover-64.png"));

	public static final ImageIcon PIMCA_DIAGRAM_BIG_ICON = new ImageIconResource(
			ResourceLocator.locateResource("Icons/CTA/PimcaDiagram_64x64.png"));
	public static final ImageIcon PIMCA_DIAGRAM_ICON = new ImageIconResource(
			ResourceLocator.locateResource("Icons/CTA/PimcaDiagram_16x16.png"));

	public static final ImageIcon PIMCA_PERSPECTIVE_ICON = new ImageIconResource(
			ResourceLocator.locateResource("Icons/CTA/PimcaPerspective_16x16.png"));
	public static final ImageIcon EXECUTION_UNIT_PERSPECTIVE_ICON = new ImageIconResource(
			ResourceLocator.locateResource("Icons/CTA/ExecutionUnitPerspective_16x16.png"));
	public static final ImageIcon SIMULATION_PERSPECTIVE_ICON = new ImageIconResource(
			ResourceLocator.locateResource("Icons/CTA/SimulationPerspective_16x16.png"));

	public static final ImageIcon BIG_EXECUTION_UNIT_ICON = new ImageIconResource(
			ResourceLocator.locateResource("Icons/CTA/ExecutionUnit_64x64.png"));

	public static final ImageIcon BIG_SIMULATION_ICON = new ImageIconResource(
			ResourceLocator.locateResource("Icons/CTA/Simulation_64x64.png"));
	public static final ImageIcon SIMULATION_ICON = new ImageIconResource(ResourceLocator.locateResource("Icons/CTA/Simulation_16x16.png"));

	public static final IconMarker CTA_BIG_MARKER = new IconMarker(CTA_MEDIUM_ICON, 32, 0);

}
