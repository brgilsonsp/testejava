using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TraceabilityAPI.Models
{
    public class Call
    {
        public virtual Int64 Id { get; set; }
        public virtual String UCIDOrigin { get; set; }
        public virtual String CallIdOrigin { get; set; }

        public virtual string UCIDTrack { get; set; }
        public virtual string CallIdTrack { get; set; }
        public virtual String DateCallTrack { get; set; }
        public virtual String TypeCallTrack { get; set; }
        public virtual String Observation { get; set; }

        public virtual Boolean IsRecordedECH { get; set; }

        public virtual String DateSaved { get; set; }
        public virtual String DateTracked { get; set; }

    }
}
