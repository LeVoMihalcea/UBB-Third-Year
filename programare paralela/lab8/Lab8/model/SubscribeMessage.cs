using System;

namespace Lab8.model
{
    [Serializable]
    internal class SubscribeMessage
    {
        public string Var { get; set; }
        public int Rank { get; set; }

        public SubscribeMessage(string var, int rank)
        {
            Var = var;
            Rank = rank;
        }
    }
}