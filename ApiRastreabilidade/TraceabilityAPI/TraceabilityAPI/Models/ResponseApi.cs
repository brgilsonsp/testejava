using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TraceabilityAPI.Models
{
    public class ResponseApi<T>
    {
        public String Message { get; set; }
        public T Data { get; set; }
    }
}
