using BotRastreabilidade.Business;
using BotRastreabilidade.Infra;
using BotRastreabilidade.Models;
using log4net;
using NHibernate;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.Linq;
using System.Diagnostics;

namespace BotRastreabilidade
{
    public class ProcessRastreability
    {
        private readonly ILog logger = LogManager.GetLogger(typeof(ProcessRastreability));

        public ProcessRastreability()
        {
            new ConfigureLog4NetBot().Configure();
        }
        
        public async Task ExecuteProcess()
        {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.Start();

            try
            {
                logger.Info($"Bot iniciou processo");
                List<Call> calls = await GegtCallsToTrackerAsync().ConfigureAwait(false);
                ApiTracelibity apiTracelibity = new ApiTracelibity();
                ISession session = NHibernateHelper.OpenSession();
                Tracking tracking = new Tracking(session);
                foreach (Call call in calls)
                {
                    await this.ManageCall(call, tracking, apiTracelibity);
                }
                session.Close();
            }
            catch (Exception e)
            {
                logger.Error("Erro na execução do processo", e);
            }
            finally
            {
                stopwatch.Stop();
                logger.Info($"Bot finalizado em {stopwatch.ElapsedMilliseconds} ms");
                logger.Info("===============================================================================");
            }
        }

        private async Task ManageCall(Call call, Tracking tracking, ApiTracelibity apiTracelibity)
        {
            try
            {
                tracking.RelatesCallTrackEch(call);
                await MarkCallAsUsedAsync(call, apiTracelibity).ConfigureAwait(false);
            }
            catch (Exception e)
            {
                logger.Error("Erro ao gerenciar a chamada", e);
            }
        }
        
        private async Task<Boolean> MarkCallAsUsedAsync(Call call, ApiTracelibity apiTracelibity)
        {
            Boolean isMark = false;

            if (call.IsRecordedECH || call.IsExceededLimitAttemps)
            {
                ResponseApi<Call> response = await apiTracelibity.AlterCallToUsedAsync(call)
                                                                .ConfigureAwait(false);
                logger.Info($"Resposta API: {response}");
                isMark = true;
            }
            else
            {
                logger.Info($"Call não foi marcada como utilizada {call}");
            }

            return isMark;
        }
        
        private async Task<List<Call>> GegtCallsToTrackerAsync()
        {
            try
            {
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.Start();

                ApiTracelibity consultaApi = new ApiTracelibity();
                ResponseApi<List<Call>> responseApi = await consultaApi.GetListCallAsync()
                                                                        .ConfigureAwait(false);

                stopwatch.Stop();
                logger.Info($"API enviou {responseApi.Data.Count} objetos com a mensagem: {responseApi.Message}, " +
                    $"em {stopwatch.ElapsedMilliseconds} ms");
                return responseApi.Data;
            }
            catch (Exception e)
            {
                logger.Error("Não foi possível obter os dados da API.", e);
                throw e;
            }
        }
    }
}
