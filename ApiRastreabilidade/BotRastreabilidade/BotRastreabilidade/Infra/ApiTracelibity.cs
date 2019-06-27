using BotRastreabilidade.Models;
using log4net;
using Microsoft.Extensions.Configuration;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Net.Http;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace BotRastreabilidade.Infra
{
    public class ApiTracelibity
    {

        private static readonly ILog _logger = LogManager.GetLogger(typeof(ApiTracelibity));

        private static readonly HttpClient httpClient = new HttpClient();
        private static readonly IConfigurationRoot configuration = ReadConfiguration.BuildConfiguration();
        
        private static readonly string _resourceUseCall = configuration["AddressApiTracker:PathUpdateUseCall"];
        private static readonly string _resourceNotUseCall = configuration["AddressApiTracker:PathUpdateNotUseCall"];
        private static readonly string _resourceGetNotRecorded = configuration["AddressApiTracker:PathGetNotRecorded"];
        private static readonly int _limitRecordsAPI = GetLimitRecordsAPI(configuration);
        private static readonly int _limitCancellationToken = GetLimitCancellationToken(configuration);
        

#if DEBUG
        private static readonly string _url = configuration["AddressApiTracker:UrlDev"];
#else
        private static readonly string _url = configuration["AddressApiTracker:UrlProd"];
#endif


        public async Task<ResponseApi<List<Call>>> GetListCallAsync()
        {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.Start();
            CancellationTokenSource cancellationToken = new CancellationTokenSource();
            cancellationToken.CancelAfter(_limitCancellationToken);
            string path = $"{_url}{_resourceGetNotRecorded}/{_limitRecordsAPI}";

            try
            {
                HttpResponseMessage response = await httpClient.GetAsync(path, cancellationToken.Token)
                                                            .ConfigureAwait(false);
                string responseString = await response.Content.ReadAsStringAsync().ConfigureAwait(false);
                ResponseApi<List<Call>> listCalls = JsonConvert
                                                    .DeserializeObject<ResponseApi<List<Call>>>(responseString);

                stopwatch.Stop();
                _logger.Info($"Obtido lista de chamdas rastreáveis no recurso {path} em " +
                    $"{stopwatch.ElapsedMilliseconds} ms");

                return listCalls;
            }
            catch (Exception e)
            {
                String messageError = $"Error ao obter a lista de chamadas rastreáveis no recurso {path}";
                stopwatch.Stop();
                _logger.Error(messageError, e);
                throw new Exception(messageError, e);
            }
        }

        public async Task<ResponseApi<Call>> AlterCallToUsedAsync(Call call)
        {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.Start();

            try
            {
                string callJson = JsonConvert.SerializeObject(call);
                HttpContent content = new StringContent(callJson, Encoding.UTF8, "application/json");
                string path = BuildUri(call);
                CancellationTokenSource cancellationToken = BuildCancellationToken();

                HttpResponseMessage response = await httpClient.PutAsync(path, content, cancellationToken.Token)
                                                                .ConfigureAwait(false);
                ResponseApi<Call> responseBody = await BuildResponseAsync(response).ConfigureAwait(false);
                
                stopwatch.Stop();
                _logger.Info($"Atualizado a Call {call} no recurso {path} em {stopwatch.ElapsedMilliseconds} ms");

                return responseBody;
            }
            catch(Exception e)
            {
                String messageError = $"Error ao atualizar a Call {call}";
                stopwatch.Stop();
                _logger.Error(messageError, e);
                throw new Exception(messageError, e);
            }
        }

        private static String BuildUri(Call call)
        {
            string resource = call.IsRecordedECH ? _resourceUseCall : _resourceNotUseCall;
            return $"{_url}{resource}";
        }

        private static CancellationTokenSource BuildCancellationToken()
        {
            CancellationTokenSource cancellationToken = new CancellationTokenSource();
            cancellationToken.CancelAfter(_limitCancellationToken);
            return cancellationToken;
        }

        private async Task<ResponseApi<Call>> BuildResponseAsync(HttpResponseMessage response)
        {
            if (response.StatusCode == System.Net.HttpStatusCode.OK)
            {
                string responseString = await response.Content.ReadAsStringAsync().ConfigureAwait(false);
                return JsonConvert.DeserializeObject<ResponseApi<Call>>(responseString);
            }
            return new ResponseApi<Call>
            {
                Message = response.StatusCode.ToString(),
                Data = new Call()
            };
        }

        private static int GetLimitRecordsAPI(IConfigurationRoot configuration)
        {
            String limitString = configuration["LimitRecordsAPI"];
            int limitFound = 10;
            Int32.TryParse(limitString, out limitFound);
            return limitFound;
            //return String.IsNullOrWhiteSpace(limitString) ? 10 : Int32.Parse(limitString);
        }

        private static int GetLimitCancellationToken(IConfigurationRoot configuration)
        {
            String limitString = configuration["LimitTimeCancelationToken"];
            int limitLong = 10000;
            int.TryParse(limitString, out limitLong);

            return limitLong;
        }
    }
}
