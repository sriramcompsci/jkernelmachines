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

    Copyright David Picard - 2010

*/
package fr.lip6.jkernelmachines.test.transductive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import fr.lip6.jkernelmachines.classifier.DoublePegasosSVM;
import fr.lip6.jkernelmachines.classifier.transductive.S3VMLightPegasos;
import fr.lip6.jkernelmachines.kernel.typed.DoubleLinear;
import fr.lip6.jkernelmachines.type.TrainingSample;

/**
 * Simple example of transductive linear classifiers.
 * @author picard
 *
 */
@Deprecated
public class TestLinearTransductiveSVM {

	public static void main(String[] args)
	{
		int dimension = 10;
		int nbPosTrain = 20;
		int nbNegTrain = 100;
		int nbPosTest = 200;
		int nbNegTest = 1000;
		double maxcv = 50;
		
		DoubleLinear k = new DoubleLinear();
		
		Random ran = new Random(System.currentTimeMillis());
		double posstart = 0.8;
		double negstart = -0.8;
		
		double pegerr = 0;
		double pegstd = 0;
		double tpegerr = 0;
		double tpegstd = 0;
		for(int cv = 0 ; cv < maxcv; cv++)
		{
			ArrayList<TrainingSample<double[]>> train = new ArrayList<TrainingSample<double[]>>();
			//1. generate positive train samples
			for(int i = 0 ; i < nbPosTrain; i++)
			{
				double[] t = new double[dimension];
				for(int x = 0 ; x < dimension; x++)
				{
					t[x] = posstart + ran.nextGaussian();
				}

				train.add(new TrainingSample<double[]>(t, 1));
			}
			//2. generate negative train samples
			for(int i = 0 ; i < nbNegTrain; i++)
			{
				double[] t = new double[dimension];
				for(int x = 0 ; x < dimension; x++)
				{
					t[x] = negstart + ran.nextGaussian();
				}

				train.add(new TrainingSample<double[]>(t, -1));
			}

			ArrayList<TrainingSample<double[]>> test = new ArrayList<TrainingSample<double[]>>();
			//4. generate positive test samples
			for(int i = 0 ; i < nbPosTest; i++)
			{
				double[] t = new double[dimension];
				for(int x = 0 ; x < dimension; x++)
				{
					t[x] = posstart + ran.nextGaussian();
				}

				test.add(new TrainingSample<double[]>(t, 1));
			}
			//5. generate negative test samples
			for(int i = 0 ; i < nbNegTest; i++)
			{
				double[] t = new double[dimension];
				for(int x = 0 ; x < dimension; x++)
				{
					t[x] = negstart + ran.nextGaussian();
				}

				test.add(new TrainingSample<double[]>(t, -1));
			}

			int T = 10*train.size()/10;
			double t0 = 1.e2;
			double lambda = 1e-2;
//			double lambdat = 1e-1;
			boolean bias = false;
			int K = 10;
			
			//3.1 train pegasos
			DoublePegasosSVM peg = new DoublePegasosSVM();
			peg.setLambda(lambda);
			peg.setK(K);
			peg.setT(T);
			peg.setT0(t0);
			peg.setBias(bias);
			peg.train(train);


			//3.2 train transductive pegasos
//			DoubleGaussL2 kernel = new DoubleGaussL2();
//			kernel.setGamma(0.01);
			S3VMLightPegasos tpeg = new S3VMLightPegasos();
			tpeg.setLambda(lambda);
			tpeg.setK(K);
			tpeg.setT(T);
			tpeg.setT0(t0);
			tpeg.setBias(bias);
//			tpeg.setVerbosityLevel(3);
			tpeg.setNumplus(200);
			tpeg.train(train, test);
			

			//6. test svm
			int nbErr = 0;
			int tpegErr = 0;
			for(TrainingSample<double[]> t : test)
			{
				int y = t.label;
				double value = peg.valueOf(t.sample);
				if(y*value < 0)
					nbErr++;
				double pegVal = tpeg.valueOf(t.sample);
				if(y*pegVal < 0)
				{
					tpegErr++;
//					System.out.println("y : "+y+" value : "+value+" nbErr : "+nbErr+" pegVal : "+pegVal+" pegErr : "+tpegErr);
				}

//				System.out.println("y : "+y+" value : "+value+" nbErr : "+nbErr+" pegVal : "+pegVal+" pegErr : "+tpegErr);


			}

			pegerr += nbErr;
			pegstd += nbErr*nbErr;
			tpegerr += tpegErr;
			tpegstd += tpegErr*tpegErr;

			//7.1 compute w for pegasos
			double w[] = peg.getW();
			System.out.println("peg : w : "+Arrays.toString(w));
			System.out.println("peg : bias : "+peg.getB());
			System.out.println("peg : ||w|| : "+k.valueOf(w, w));

			//7.2 w from transductive pegasos
			System.out.println("tpeg : w : "+Arrays.toString(tpeg.getW()));
			System.out.println("tpeg : bias : "+tpeg.getB());
			System.out.println("tpeg : ||w|| : "+k.valueOf(tpeg.getW(), tpeg.getW()));

			//8. comparing smo and peg
			System.out.println("< peg, tpeg > : "+(k.valueOf(w, tpeg.getW())/Math.sqrt(k.valueOf(w, w)*k.valueOf(tpeg.getW(), tpeg.getW()))));
		}
		
		//final stats
		System.out.println();
		pegstd = Math.sqrt(pegstd/maxcv - (pegerr/maxcv)*(pegerr/maxcv));
		tpegstd = Math.sqrt(tpegstd/maxcv - (tpegerr/maxcv)*(tpegerr/maxcv));
		System.out.println("mean : peg = "+(pegerr/maxcv)+" (+/- "+pegstd+") tpeg = "+(tpegerr/maxcv)+" (+/- "+tpegstd+")");
	}
	
}
