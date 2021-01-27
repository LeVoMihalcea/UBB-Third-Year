using System;
using System.Collections.Generic;
using MPI;

namespace Lab7
{
    internal static partial class Program
    {
        private static void MpiMultiplicationMaster(Polynomial polynomial1, Polynomial polynomial2)
        {
            var start = DateTime.Now;

            var n = Communicator.world.Size;
            var end = 0;
            var length = (polynomial1.Degree + 1) / (n - 1);

            for (var i = 1; i < n; i++)
            {
                var begin = end;
                end = end + length;
                if (i == n - 1)
                    end = polynomial1.Degree + 1;

                Communicator.world.Send(polynomial1, i, 0);
                Communicator.world.Send(polynomial2, i, 0);
                Communicator.world.Send(begin, i, 0);
                Communicator.world.Send(end, i, 0);
            }

            var results = new Polynomial[n - 1];

            for (var i = 1; i < n; i++)
            {
                results[i - 1] = Communicator.world.Receive<Polynomial>(i, 0);
            }

            var result = ComputeFinalResult(results);

            double time = (DateTime.Now - start).Milliseconds;
            Console.WriteLine("\n Multiplication result : " + result + "\n" + "TIME: " + time + " milliseconds");
        }

        private static void MpiMultiplicationWorker()
        {
            var polynomial1 = Communicator.world.Receive<Polynomial>(0, 0);
            var polynomial2 = Communicator.world.Receive<Polynomial>(0, 0);

            var begin = Communicator.world.Receive<int>(0, 0);
            var end = Communicator.world.Receive<int>(0, 0);

            var result = PolynomialOperations.MpiMultiply(polynomial1, polynomial2, begin, end);

            Communicator.world.Send(result, 0, 0);
        }

        private static Polynomial ComputeFinalResult(IReadOnlyList<Polynomial> results)
        {
            var result = new Polynomial(results[0].Degree);

            for (var i = 0; i <= result.Degree; i++)
            {
                foreach (var t in results)
                {
                    result.Coefficients[i] += t.Coefficients[i];
                }
            }

            return result;
        }
    }
}