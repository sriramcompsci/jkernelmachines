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

    Copyright David Picard - 2013

*/
package fr.lip6.jkernelmachines.test.classifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.lip6.jkernelmachines.classifier.DoublePegasosSVM;
import fr.lip6.jkernelmachines.type.TrainingSample;
import fr.lip6.jkernelmachines.util.generators.GaussianGenerator;

/**
 * @author picard
 *
 */
public class DoublePegasosSVMTest {

	
	List<TrainingSample<double[]>> train;
	DoublePegasosSVM svm;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		GaussianGenerator g = new GaussianGenerator(10, 5.0f, 1.0);
		train = g.generateList(10);
		
		svm = new DoublePegasosSVM();
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.classifier.DoublePegasosSVM#train(java.util.List)}.
	 */
	@Test
	public final void testTrainListOfTrainingSampleOfdouble() {
		svm.train(train);
		for(TrainingSample<double[]> t : train) {
			double v = t.label * svm.valueOf(t.sample);
			assertTrue(v > 0);
		}
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.classifier.DoublePegasosSVM#setT(int)}.
	 */
	@Test
	public final void testSetT() {
		svm.setT(10000);
		assertEquals(10000, svm.getT());
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.classifier.DoublePegasosSVM#setK(int)}.
	 */
	@Test
	public final void testSetK() {
		svm.setK(10);
		assertEquals(10, svm.getK());
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.classifier.DoublePegasosSVM#setLambda(double)}.
	 */
	@Test
	public final void testSetLambda() {
		svm.setLambda(1e-3);
		assertEquals(1e-3, svm.getLambda(), 1e-15);
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.classifier.DoublePegasosSVM#setB(double)}.
	 */
	@Test
	public final void testSetB() {
		svm.setB(1.0);
		assertEquals(1.0, svm.getB(), 1e-15);
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.classifier.DoublePegasosSVM#setBias(boolean)}.
	 */
	@Test
	public final void testSetBias() {
		svm.setBias(true);
		assertTrue(svm.isBias());
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.classifier.DoublePegasosSVM#setT0(double)}.
	 */
	@Test
	public final void testSetT0() {
		svm.setT0(1.0);
		assertEquals(1.0, svm.getT0(), 1e-15);
	}

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.classifier.DoublePegasosSVM#setC(double)}.
	 */
	@Test
	public final void testSetC() {
		svm.setC(1.0);
		assertEquals(1.0, svm.getC(), 1e-15);
	}

}
