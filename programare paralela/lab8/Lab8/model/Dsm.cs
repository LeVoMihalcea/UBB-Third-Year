using System.Collections.Generic;
using MPI;

namespace Lab8.model
{
    internal class Dsm
    {
        public int a = 1, b = 2, c = 3;
        public readonly Dictionary<string, List<int>> subscribers = new Dictionary<string, List<int>>();

        public Dsm()
        {
            subscribers.Add("a", new List<int>());
            subscribers.Add("b", new List<int>());
            subscribers.Add("c", new List<int>());
        }

        public void UpdateVar(string var, int val)
        {
            SetVar(var, val);
            var updateMsg = new UpdateMessage(var, val);
            var msg = new Message(updateMsg);

            SendToSubscribers(var, msg);
        }

        public static void Close()
        {
            SendAll(new Message(true));
        }

        private static void SendAll(Message message)
        {
            for (var i = 0; i < Communicator.world.Size; i++)
            {
                if (Communicator.world.Rank == i) continue;
                Communicator.world.Send(message, i, 0);
            }
        }

        public void SetVar(string var, int val)
        {
            switch (var)
            {
                case "a":
                    a = val;
                    break;
                case "b":
                    b = val;
                    break;
                case "c":
                    c = val;
                    break;
            }
        }

        public void SubscribeTo(string var)
        {
            subscribers[var].Add(Communicator.world.Rank);

            SendAll(new Message(new SubscribeMessage(var, Communicator.world.Rank)));
        }

        public void SubscribeOther(string var, int rank)
        {
            subscribers[var].Add(rank);
        }

        private void SendToSubscribers(string var, Message message)
        {
            for (var i = 0; i < Communicator.world.Size; i++)
            {
                if (Communicator.world.Rank == i) continue;
                if (!IsSubscribedTo(var, i)) continue;

                Communicator.world.Send(message, i, 0);
            }
        }

        private bool IsSubscribedTo(string var, int rank)
        {
            if (subscribers[var].Contains(rank))
            {
                return true;
            }

            return false;
        }

        internal void CheckAndReplace(string var, int val, int newVal)
        {
            switch (var)
            {
                case "a":
                {
                    if (a == val)
                    {
                        UpdateVar("a", newVal);
                    }

                    break;
                }
                case "b":
                {
                    if (b == val)
                    {
                        UpdateVar("b", newVal);
                    }

                    break;
                }
                case "c":
                {
                    if (a == val)
                    {
                        UpdateVar("c", newVal);
                    }

                    break;
                }
            }
        }
    }
}