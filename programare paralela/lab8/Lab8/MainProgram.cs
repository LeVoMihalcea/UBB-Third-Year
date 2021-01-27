using System;
using System.Threading;
using Lab8.model;
using MPI;
using Environment = MPI.Environment;

namespace Lab8
{
    internal static class MainProgram
    {
        private static void Main(string[] args)
        {
            using (new Environment(ref args))
            {
                var dsm = new Dsm();

                switch (Communicator.world.Rank)
                {
                    case 0:
                    {
                        var thread = new Thread(Listener.Listen);
                        thread.Start(dsm);

                        var exit = false;

                        dsm.SubscribeTo("a");
                        dsm.SubscribeTo("b");
                        dsm.SubscribeTo("c");

                        while (!exit)
                        {
                            Console.WriteLine(
                                "1. Set a variable\n" + "2. Compare and exchange variable\n" + "0. Exit\n");

                            int.TryParse(Console.ReadLine(), out var answer);

                            switch (answer)
                            {
                                case 0:
                                    Dsm.Close();
                                    exit = true;
                                    break;
                                case 1:
                                {
                                    Console.WriteLine("Choose a variable between a/b/c\n");
                                    var var = Console.ReadLine();

                                    Console.WriteLine("Insert new value for variable " + var + ":\n");
                                    int.TryParse(Console.ReadLine(), out var val);

                                    dsm.UpdateVar(var, val);
                                    Listener.WriteVars(dsm);
                                    break;
                                }
                                case 2:
                                {
                                    Console.WriteLine("var to check (a, b, c) = ");
                                    var var = Console.ReadLine();

                                    Console.WriteLine("val to check (int) = ");
                                    int.TryParse(Console.ReadLine(), out var val);

                                    Console.WriteLine("val to check (int) = ");
                                    int.TryParse(Console.ReadLine(), out var newVal);

                                    dsm.CheckAndReplace(var, val, newVal);
                                    break;
                                }
                            }
                        }

                        break;
                    }
                    case 1:
                    {
                        var thread = new Thread(Listener.Listen);
                        thread.Start(dsm);

                        dsm.SubscribeTo("a");
                        dsm.SubscribeTo("b");
                        dsm.SubscribeTo("c");

                        thread.Join();
                        break;
                    }
                    case 2:
                    {
                        var thread = new Thread(Listener.Listen);
                        thread.Start(dsm);

                        dsm.SubscribeTo("a");
                        dsm.SubscribeTo("b");
                        dsm.SubscribeTo("c");

                        thread.Join();
                        break;
                    }
                }
            }
        }
    }
}