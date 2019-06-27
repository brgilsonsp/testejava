using BotRastreabilidade.Daos;
using BotRastreabilidade.Infra;
using BotRastreabilidade.Models;
using log4net;
using NHibernate;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;

namespace BotRastreabilidade.Business
{
    public class Tracking
    {
        private EchDao Dao;
        private readonly ILog logger = LogManager.GetLogger(typeof(Tracking));

        public Tracking(ISession session)
        {
            Dao = new EchDao(session);
        }

        public void RelatesCallTrackEch(Call call)
        {
            if (call != null && call.IsCallValidToRelate)
            {
                call.IsRecordedECH = Relate(call);
                logger.Info($"Persistencia foi executado com sucesso? {call.IsRecordedECH}");
            }
            else
            {
                throw new Exception($"Call não é um objeto válido para configurar a rastreabilidade no ECH " +
                    $"{call}");
            }
        }
        
        private Boolean Relate(Call call)
        {
            try
            {
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.Start();

                int callIdECH = Dao.GetCallIdInECHByUcidTrack(call.UCIDTrack);
                int returnUpdate = Dao.Update(call, callIdECH);

                stopwatch.Stop();
                logger.Info($"Persistido rastreabilidade no ECH em {stopwatch.ElapsedMilliseconds} ms. " +
                    $"Chamada: {call}");

                return returnUpdate > 0;
            }
            catch (Exception e)
            {
                logger.Error("Erro ao persistir a rastreabilidade.", e);
                return false;
            }
        }
        
    }
}
