using System;
using MPI;

namespace Lab7
{
    internal static partial class Program
    {
        private static void Main(string[] args)
        {
            using (new MPI.Environment(ref args))
            {
                if (Communicator.world.Rank == 0)
                {
                    var polynomialLength = 7;
                    var polynomial1 = Polynomial.Random(polynomialLength);

                    polynomialLength = 7;
                    var polynomial2 = Polynomial.Random(polynomialLength);

                    Console.WriteLine(polynomial1);
                    Console.WriteLine(polynomial2);
                    
                    Console.WriteLine("\n");
                    MpiMultiplicationMaster(polynomial1, polynomial2);
                    Console.WriteLine("\n");
                    MpiKaratsubaMaster(polynomial1, polynomial2);
                    Console.WriteLine("\n");
                }
                else
                {
                    MpiMultiplicationWorker();
                    MpiKaratsubaWorker();
                }
            }
        }
    }
}