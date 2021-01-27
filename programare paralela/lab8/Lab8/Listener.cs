using System;
using System.Linq;
using Lab8.model;
using MPI;

namespace Lab8
{
    internal static class Listener 
    {
        public static void Listen(object obj)
        {
            var dsm = (Dsm) obj;

            while (true)
            {
                Console.WriteLine("Rank " + Communicator.world.Rank + " waiting ");
                var msg = Communicator.world.Receive<Message>(Communicator.anySource, Communicator.anyTag);

                if (msg.exit) break;

                if (msg.UpdateMessage != null)
                {
                    Console.WriteLine("Rank " + Communicator.world.Rank + " received : " + msg.UpdateMessage.Var +
                                      " -> " + msg.UpdateMessage.Val);
                    dsm.SetVar(msg.UpdateMessage.Var, msg.UpdateMessage.Val);
                    WriteVars(dsm);
                }

                if (msg.SubscribeMessage != null)
                {
                    Console.WriteLine("Rank " + Communicator.world.Rank + " received: " + msg.SubscribeMessage.Rank +
                                      " sub to " + msg.SubscribeMessage.Var);
                    dsm.SubscribeOther(msg.SubscribeMessage.Var, msg.SubscribeMessage.Rank);
                    WriteSubs(dsm);
                }

            }
        }

        internal static void WriteVars(Dsm dsm)
        {
            var vars = "Rank " + Communicator.world.Rank + " a= " + dsm.a + " b= " + dsm.b + " c= " + dsm.c;
            Console.WriteLine(vars);
        }

        private static void WriteSubs(Dsm dsm)
        {
            var subs = "Rank " + Communicator.world.Rank + " Known subs: ";
            foreach (var var in dsm.subscribers.Keys)
            {
                subs += var + ": [ ";
                subs = dsm.subscribers[var].Aggregate(subs, (current, rank) => current + (rank + " "));
                subs += "] ";
            }
            Console.WriteLine(subs);
        }
    }
}
