using System;
using System.Collections.Generic;
using lab4.methods;

namespace lab4
{
    internal class Program
    {
        // adding 3 hosts, each returning a response in different format:
        private static readonly List<string> HOSTS = new List<string> {
            // - gzip form (compressed)
            "filelist.io",
            // - empty body (just signals that the page has moved and the HTTPS protocol should be used from now on)
            "youtube.com",
            // - plain text
            "google.com", 
        };
        public static void Main(string[] args)
        {
            DirectCallbacks.run(HOSTS);
            //TaskMechanism.run(HOSTS);
            //AsyncTaskMechanism.run(HOSTS);
        }
    }
    
    
}