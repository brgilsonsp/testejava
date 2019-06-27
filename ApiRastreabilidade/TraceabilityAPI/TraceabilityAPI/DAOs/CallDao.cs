using NHibernate;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TraceabilityAPI.Models;

namespace TraceabilityAPI.DAOs
{
    public class CallDao
    {
        private readonly ISession Session;

        public CallDao(ISession session)
        {
            Session = session;
        }

        public void Save(Call call)
        {
            ITransaction transaction = Session.BeginTransaction();
            Session.Save(call);
            transaction.Commit();
        }

        public void Update(Call call, Int64 id)
        {
            ITransaction transaction = Session.BeginTransaction();
            Session.Update(call, id);
            transaction.Commit();
        }

        public void SaveAsync(Call call)
        {
            ITransaction transaction = Session.BeginTransaction();
            Session.SaveOrUpdateAsync(call).ContinueWith(item => transaction.Commit());            
        }

        public IList<Call> FindAllCallByIsRecordedEch(bool isRecordedECH)
        {
            string queryString = "from Call c where c.IsRecordedECH = ?";
            IQuery query = Session.CreateQuery(queryString);
            IQuery result = query.SetParameter<Boolean>(0, isRecordedECH);
            return result.List<Call>();
        }

        public IList<Call> FindAllNotRecordedEch()
        {
            return this.FindAllCallByIsRecordedEch(false);
        }

        public IList<Call> FindNotRecordedEchLimited(int limit)
        {
            IEnumerable<Call> limitListCall = FindAllNotRecordedEch().Take(limit);
            return limitListCall.ToList<Call>();
        }

        public void ReprocessAll()
        {
            List<Call> allCalls = this.FindAllCallByIsRecordedEch(true).ToList();
            allCalls.ForEach(item => {
                item.Observation = null;
                item.IsRecordedECH = false;
                item.DateTracked = null;
                this.SaveAsync(item);
            });
        }

        public Call FindById(Int64 id)
        {
            return Session.Get<Call>(id);
        }
    }
}
