/**
    This file is part of JkernelMachines.

    JkernelMachines is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    JkernelMachines is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with JkernelMachines.  If not, see <http://www.gnu.org/licenses/>.

    Copyright David Picard - 2012

*/
package fr.lip6.jkernelmachines.test.kernel.typed;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.lip6.jkernelmachines.kernel.typed.DoubleGaussChi2;

/**
 * @author picard
 *
 */
public class DoubleGaussChi2Test {
	
	DoubleGaussChi2 gausschi2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gausschi2 = new DoubleGaussChi2();
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.kernel.typed.DoubleGaussChi2#setGamma(double)}.
	 */
	@Test
	public final void testSetGamma() {
		gausschi2.setGamma(1.0);
		assertEquals(1.0, gausschi2.getGamma(), 1e-15);
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.kernel.typed.DoubleGaussChi2#DoubleGaussChi2(double)}.
	 */
	@Test
	public final void testDoubleGaussChi2Double() {
		gausschi2 = new DoubleGaussChi2(1.0);
		assertEquals(1.0, gausschi2.getGamma(), 1e-15);
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.kernel.typed.DoubleGaussChi2#valueOf(double[], double[])}.
	 */
	@Test
	public final void testValueOfDoubleArrayDoubleArray() {
		double[] x1 = {1.0, 0.0};
		double[] x2 = {0.0, 1.0};
		
		assertEquals(1.0, gausschi2.valueOf(x1, x1), 1e-15);
		gausschi2.setGamma(1000);
		assertEquals(0.0, gausschi2.valueOf(x1, x2), 1e-15);
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.kernel.typed.DoubleGaussChi2#valueOf(double[])}.
	 */
	@Test
	public final void testValueOfDoubleArray() {
		double[] x1 = { 1.0, 0.0 };
		assertEquals(1.0, gausschi2.valueOf(x1), 1e-15);
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.kernel.typed.DoubleGaussChi2#distanceValueOf(double[], double[])}.
	 */
	@Test
	public final void testDistanceValueOfDoubleArrayDoubleArray() {
		double[] x1 = {1.0, 0.0};
		double[] x2 = {0.0, 1.0};
		
		assertEquals(0.0, gausschi2.distanceValueOf(x1, x1), 1e-15);
		assertEquals(2.0, gausschi2.distanceValueOf(x1, x2), 1e-15);
	}

}
