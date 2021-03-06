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
package fr.lip6.jkernelmachines.test.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import fr.lip6.jkernelmachines.io.LibSVMImporter;
import fr.lip6.jkernelmachines.type.TrainingSample;

/**
 * @author picard
 *
 */
public class LibSvmImporterTest {

	

	/**
	 * Test method for {@link fr.lip6.jkernelmachines.io.LibSVMImporter#importFromFile(java.lang.String)}.
	 */
	@Test
	public final void testImportFromFile() {
		try {
			List<TrainingSample<double[]>> l = LibSVMImporter.importFromFile("resources/ionosphere_scale");
			assertEquals(351, l.size());
			assertEquals(34, l.get(0).sample.length);
			
		} catch (IOException e) {
			fail("Exception thrown: "+e.getMessage());
		}
		
		
	}

}
