using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Lab7
{
    public static partial class PolynomialOperations
    {
        public static Polynomial MpiMultiply(Polynomial polynomial1, Polynomial polynomial2, int begin, int end)
        {
            var maxDegree = Math.Max(polynomial1.Degree, polynomial2.Degree);
            var result = new Polynomial(maxDegree * 2);

            for (var i = begin; i < end; i++)
            for (var j = 0; j <= polynomial2.Degree; j++)
                result.Coefficients[i + j] += polynomial1.Coefficients[i] * polynomial2.Coefficients[j];

            return result;
        }

        private static int[] AsynchronousKaratsubaMultiplyRecursive(IReadOnlyList<int> coefficients1,
            IReadOnlyList<int> coefficients2)
        {
            var product = new int[2 * coefficients1.Count];

            if (coefficients1.Count == 1)
            {
                product[0] = coefficients1[0] * coefficients2[0];
                return product;
            }

            var halfArraySize = coefficients1.Count / 2;

            var coefficients1Low = new int[halfArraySize];
            var coefficients1High = new int[halfArraySize];
            var coefficients2Low = new int[halfArraySize];
            var coefficients2High = new int[halfArraySize];

            var coefficients1LowHigh = new int[halfArraySize];
            var coefficients2LowHigh = new int[halfArraySize];

            for (var halfSizeIndex = 0; halfSizeIndex < halfArraySize; halfSizeIndex++)
            {
                coefficients1Low[halfSizeIndex] = coefficients1[halfSizeIndex];
                coefficients1High[halfSizeIndex] = coefficients1[halfSizeIndex + halfArraySize];
                coefficients1LowHigh[halfSizeIndex] =
                    coefficients1Low[halfSizeIndex] + coefficients1High[halfSizeIndex];

                coefficients2Low[halfSizeIndex] = coefficients2[halfSizeIndex];
                coefficients2High[halfSizeIndex] = coefficients2[halfSizeIndex + halfArraySize];
                coefficients2LowHigh[halfSizeIndex] =
                    coefficients2Low[halfSizeIndex] + coefficients2High[halfSizeIndex];
            }

            var t1 = Task<int[]>.Factory.StartNew(() =>
                AsynchronousKaratsubaMultiplyRecursive(coefficients1Low, coefficients2Low));

            var t2 = Task<int[]>.Factory.StartNew(() =>
                AsynchronousKaratsubaMultiplyRecursive(coefficients1High, coefficients2High));

            var t3 = Task<int[]>.Factory.StartNew(() =>
                AsynchronousKaratsubaMultiplyRecursive(coefficients1LowHigh, coefficients2LowHigh));

            var productLow = t1.Result;
            var productHigh = t2.Result;
            var productLowHigh = t3.Result;

            var productMiddle = new int[coefficients1.Count];
            for (var halfSizeIndex = 0; halfSizeIndex < coefficients1.Count; halfSizeIndex++)
                productMiddle[halfSizeIndex] = productLowHigh[halfSizeIndex] - productLow[halfSizeIndex] -
                                               productHigh[halfSizeIndex];

            for (int halfSizeIndex = 0, middleOffset = coefficients1.Count / 2;
                halfSizeIndex < coefficients1.Count;
                ++halfSizeIndex)
            {
                product[halfSizeIndex] += productLow[halfSizeIndex];
                product[halfSizeIndex + coefficients1.Count] += productHigh[halfSizeIndex];
                product[halfSizeIndex + middleOffset] += productMiddle[halfSizeIndex];
            }

            return product;
        }
    }
}