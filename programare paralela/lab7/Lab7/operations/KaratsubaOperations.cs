using System.Linq;
using MPI;

namespace Lab7
{
    public static partial class PolynomialOperations
    {
        public static Polynomial AsynchronousKaratsubaMultiply(Polynomial p1, Polynomial p2)
        {
            var result = new Polynomial(p1.Degree + p2.Degree)
            {
                Coefficients = AsynchronousKaratsubaMultiplyRecursive(p1.Coefficients, p2.Coefficients)
            };

            return result;
        }

        public static void MpiKaratsubaMultiply()
        {
            var from = Communicator.world.Receive<int>(Communicator.anySource, 0);
            var coefficients1 = Communicator.world.Receive<int[]>(Communicator.anySource, 0);
            var coefficients2 = Communicator.world.Receive<int[]>(Communicator.anySource, 0);
            var sendTo = Communicator.world.Receive<int[]>(Communicator.anySource, 0);

            var product = new int[2 * coefficients1.Length];

            if (coefficients1.Length == 1)
            {
                product[0] = coefficients1[0] * coefficients2[0];

                Communicator.world.Send<int[]>(product, from, 0);
                return;
            }

            var halfArraySize = coefficients1.Length / 2;

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

            int[] productLow, productHigh, productLowHigh;

            switch (sendTo.Length)
            {
                case 0:
                    productLow = AsynchronousKaratsubaMultiplyRecursive(coefficients1Low, coefficients2Low);
                    productHigh = AsynchronousKaratsubaMultiplyRecursive(coefficients1High, coefficients2High);
                    productLowHigh = AsynchronousKaratsubaMultiplyRecursive(coefficients1LowHigh, coefficients2LowHigh);
                    break;
                case 1:
                    Communicator.world.Send<int>(Communicator.world.Rank, sendTo[0], 0);
                    Communicator.world.Send<int[]>(coefficients1Low, sendTo[0], 0);
                    Communicator.world.Send<int[]>(coefficients2Low, sendTo[0], 0);
                    Communicator.world.Send<int[]>(new int[0], sendTo[0], 0);

                    productHigh = AsynchronousKaratsubaMultiplyRecursive(coefficients1High, coefficients2High);
                    productLowHigh = AsynchronousKaratsubaMultiplyRecursive(coefficients1LowHigh, coefficients2LowHigh);

                    productLow = Communicator.world.Receive<int[]>(sendTo[0], 0);
                    break;
                case 2:
                    Communicator.world.Send<int>(Communicator.world.Rank, sendTo[0], 0);
                    Communicator.world.Send<int[]>(coefficients1Low, sendTo[0], 0);
                    Communicator.world.Send<int[]>(coefficients2Low, sendTo[0], 0);
                    Communicator.world.Send<int[]>(new int[0], sendTo[0], 0);

                    Communicator.world.Send<int>(Communicator.world.Rank, sendTo[1], 0);
                    Communicator.world.Send<int[]>(coefficients1High, sendTo[1], 0);
                    Communicator.world.Send<int[]>(coefficients2High, sendTo[1], 0);
                    Communicator.world.Send<int[]>(new int[0], sendTo[1], 0);

                    productLowHigh = AsynchronousKaratsubaMultiplyRecursive(coefficients1LowHigh, coefficients2LowHigh);

                    productLow = Communicator.world.Receive<int[]>(sendTo[0], 0);
                    productHigh = Communicator.world.Receive<int[]>(sendTo[1], 0);
                    break;
                case 3:
                    Communicator.world.Send<int>(Communicator.world.Rank, sendTo[0], 0);
                    Communicator.world.Send<int[]>(coefficients1Low, sendTo[0], 0);
                    Communicator.world.Send<int[]>(coefficients2Low, sendTo[0], 0);
                    Communicator.world.Send<int[]>(new int[0], sendTo[0], 0);


                    Communicator.world.Send<int>(Communicator.world.Rank, sendTo[1], 0);
                    Communicator.world.Send<int[]>(coefficients1High, sendTo[1], 0);
                    Communicator.world.Send<int[]>(coefficients2High, sendTo[1], 0);
                    Communicator.world.Send<int[]>(new int[0], sendTo[1], 0);

                    Communicator.world.Send<int>(Communicator.world.Rank, sendTo[2], 0);
                    Communicator.world.Send<int[]>(coefficients1LowHigh, sendTo[2], 0);
                    Communicator.world.Send<int[]>(coefficients2LowHigh, sendTo[2], 0);
                    Communicator.world.Send<int[]>(new int[0], sendTo[2], 0);

                    productLow = Communicator.world.Receive<int[]>(sendTo[0], 0);
                    productHigh = Communicator.world.Receive<int[]>(sendTo[1], 0);
                    productLowHigh = Communicator.world.Receive<int[]>(sendTo[2], 0);
                    break;
                default:
                {
                    var auxSendTo = sendTo.Skip(3).ToArray();
                    var auxLength = auxSendTo.Length / 3;

                    Communicator.world.Send<int>(Communicator.world.Rank, sendTo[0], 0);
                    Communicator.world.Send<int[]>(coefficients1Low, sendTo[0], 0);
                    Communicator.world.Send<int[]>(coefficients2Low, sendTo[0], 0);
                    Communicator.world.Send<int[]>(auxSendTo.Take(auxLength).ToArray(), sendTo[0], 0);

                    Communicator.world.Send<int>(Communicator.world.Rank, sendTo[1], 0);
                    Communicator.world.Send<int[]>(coefficients1High, sendTo[1], 0);
                    Communicator.world.Send<int[]>(coefficients2High, sendTo[1], 0);
                    Communicator.world.Send<int[]>(auxSendTo.Skip(auxLength).Take(auxLength).ToArray(), sendTo[1], 0);

                    Communicator.world.Send<int>(Communicator.world.Rank, sendTo[2], 0);
                    Communicator.world.Send<int[]>(coefficients1LowHigh, sendTo[2], 0);
                    Communicator.world.Send<int[]>(coefficients2LowHigh, sendTo[2], 0);
                    Communicator.world.Send<int[]>(auxSendTo.Skip(2 * auxLength).ToArray(), sendTo[2], 0);

                    productLow = Communicator.world.Receive<int[]>(sendTo[0], 0);
                    productHigh = Communicator.world.Receive<int[]>(sendTo[1], 0);
                    productLowHigh = Communicator.world.Receive<int[]>(sendTo[2], 0);
                    break;
                }
            }

            var productMiddle = new int[coefficients1.Length];
            for (var halfSizeIndex = 0; halfSizeIndex < coefficients1.Length; halfSizeIndex++)
            {
                productMiddle[halfSizeIndex] = productLowHigh[halfSizeIndex] - productLow[halfSizeIndex] -
                                               productHigh[halfSizeIndex];
            }

            for (int halfSizeIndex = 0, middleOffset = coefficients1.Length / 2;
                halfSizeIndex < coefficients1.Length;
                ++halfSizeIndex)
            {
                product[halfSizeIndex] += productLow[halfSizeIndex];
                product[halfSizeIndex + coefficients1.Length] += productHigh[halfSizeIndex];
                product[halfSizeIndex + middleOffset] += productMiddle[halfSizeIndex];
            }

            Communicator.world.Send<int[]>(product, @from, 0);
        }
    }
}