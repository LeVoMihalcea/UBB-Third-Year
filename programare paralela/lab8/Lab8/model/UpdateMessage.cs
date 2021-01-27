using System;

namespace Lab8.model
{
    [Serializable]
    internal class UpdateMessage
    {
        public string Var { get; set; }
        public int Val { get; set; }

        public UpdateMessage(string var, int val)
        {
            Var = var;
            Val = val;
        }
    }
}