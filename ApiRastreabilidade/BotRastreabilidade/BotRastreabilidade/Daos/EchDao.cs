using BotRastreabilidade.Models;
using NHibernate;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Text;

namespace BotRastreabilidade.Daos
{
    public class EchDao
    {
        private ISession Session;
        private static readonly int SEGMENT = 1;

        public EchDao(ISession session)
        {
            Session = session;
        }
        
        public int GetCallIdInECHByUcidTrack(String ucidTrack)
        {
            
            string queryString = "select e.callid from Ech e where e.ucid = ?";
            ITransaction transaction = Session.BeginTransaction();
            IQuery query = Session.CreateQuery(queryString);
            query.SetParameter<string>(0, ucidTrack);
            int callId = query.SetMaxResults(1).UniqueResult<int>();
            transaction.Commit();
            transaction.Dispose();

            if (callId <= 0)
                throw new Exception($"Não foi localizado o CALLID no ECH para o UCIDTRACK {ucidTrack}");

            return callId;
            
        }
        
        public int Update(Call call, int ucidTrackECH)
        {
            
            string queryString = "update Ech e set e.ucid_track = ?, e.callid_track = ?, e.type_call_track = ?, " +
                "dtime_call_track = ?, e.callid_track_ech = ? where e.ucid = ? and e.segment = ?";
            ITransaction transaction = Session.BeginTransaction();
            IQuery query = Session.CreateQuery(queryString);
            query.SetParameter<string>(0, call.UCIDTrack);
            query.SetParameter<int>(1, call.CallIdTrack);
            query.SetParameter<string>(2, call.TypeCallTrack);
            query.SetParameter(3, Convert.ToDateTime(call.DateCallTrack));
            query.SetParameter<int>(4, ucidTrackECH);
            query.SetParameter<string>(5, call.UCIDOrigin);
            query.SetParameter<int>(6, EchDao.SEGMENT);
            int returUpdate = query.ExecuteUpdate();
            transaction.Commit();

            return returUpdate;
        }
    }
}
