using System;
using System.Collections.Generic;
using lab4.methods;

namespace lab4
{
    internal class Program
    {
        private static readonly List<string> HOSTS = new List<string> {
            "filelist.io",
            "youtube.com",
            "google.com", 
        };
        public static void Main(string[] args)
        {
            DirectCallbacks.run(HOSTS);
            TaskMechanism.run(HOSTS);
            AsyncTaskMechanism.run(HOSTS);
        }
    }
    
    
}