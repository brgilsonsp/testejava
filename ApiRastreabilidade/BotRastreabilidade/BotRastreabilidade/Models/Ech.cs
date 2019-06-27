using System;
using System.Collections.Generic;
using System.Text;

namespace BotRastreabilidade.Models
{
    public class Ech
    {
        public virtual int id { get; set; }
        public virtual int callid { get; set; }
        public virtual string ucid { get; set; }
        public virtual string ucid_track { get; set; }
        public virtual int callid_track { get; set; }
        public virtual string type_call_track { get; set; }
        public virtual DateTime? dtime_call_track { get; set; }
        public virtual Int16 segment { get; set; }
        public virtual int callid_track_ech { get; set; }

        public override string ToString()
        {
            return $"Ech: [id: {id}, callid: {callid}, ucid: {ucid}, ucid_track: {ucid_track}, " +
                $"callid_track: {callid_track}, type_call_track: {type_call_track}, " +
                $"dtime_call_track: {dtime_call_track}, callid_track_ech: {callid_track_ech}]";
        }

    }
}
