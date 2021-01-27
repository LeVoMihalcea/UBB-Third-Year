using System;

namespace Lab8.model
{
    [Serializable]
    internal class Message
    {
        public UpdateMessage UpdateMessage { get; set; }
        public SubscribeMessage SubscribeMessage { get; set; }

        public bool exit;

        public Message(UpdateMessage updateMessage)
        {
            UpdateMessage = updateMessage;
        }

        public Message(SubscribeMessage subscribeMessage)
        {
            SubscribeMessage = subscribeMessage;
        }

        public Message(bool exit)
        {
            this.exit = exit;
        }
    }
}