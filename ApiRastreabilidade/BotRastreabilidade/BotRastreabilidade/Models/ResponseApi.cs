using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace BotRastreabilidade.Models
{
    public class ResponseApi<T>
    {
        [JsonProperty("data")]
        public T Data { get; set; }
        [JsonProperty("message")]
        public String Message { get; set; }

        public override string ToString()
        {
            return $"ResponseTest: [Message: {Message}, Data: {string.Join(",", Data)}]";
        }
    }
}
