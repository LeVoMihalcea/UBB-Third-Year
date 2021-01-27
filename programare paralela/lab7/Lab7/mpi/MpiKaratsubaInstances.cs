using System;
using System.Linq;
using MPI;

namespace Lab7
{
    internal static partial class Program
    {
        private static void MpiKaratsubaMaster(Polynomial polynomial1, Polynomial polynomial2)
        {
            var start = DateTime.Now;

            var result = new Polynomial(polynomial1.Degree * 2);
            if (Communicator.world.Size == 1)
            {
                result = PolynomialOperations.AsynchronousKaratsubaMultiply(polynomial1, polynomial2);
            }
            else
            {
                Communicator.world.Send<int>(0, 1, 0);
                Communicator.world.Send<int[]>(polynomial1.Coefficients, 1, 0);
                Communicator.world.Send<int[]>(polynomial2.Coefficients, 1, 0);
                if (Communicator.world.Size == 2)
                    Communicator.world.Send<int[]>(new int[0], 1, 0);
                else
                {
                    var enumerable = Enumerable.Range(2, Communicator.world.Size - 2);
                    Communicator.world.Send<int[]>(enumerable.ToArray(), 1, 0);
                }

                var coefficients = Communicator.world.Receive<int[]>(1, 0);
                result.Coefficients = coefficients;
            }

            double time = (DateTime.Now - start).Milliseconds;
            Console.WriteLine("\nKaratsuba result: " + result + "\n" + "TIME: " + time + " milliseconds");
            
        }

        private static void MpiKaratsubaWorker()
        {
            PolynomialOperations.MpiKaratsubaMultiply();
        }
    }
}