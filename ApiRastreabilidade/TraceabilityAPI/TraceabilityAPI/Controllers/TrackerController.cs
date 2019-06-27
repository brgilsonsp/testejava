using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using TraceabilityAPI.DAOs;
using TraceabilityAPI.Infra;
using TraceabilityAPI.Models;

namespace TraceabilityAPI.Controllers
{
    [Produces("application/json")]
    [Route("api/Tracker")]
    public class TrackerController : ControllerBase
    {
        private CallDao dao;
        private readonly ILogger<TrackerController> _logger;

        public TrackerController(ILogger<TrackerController> logger)
        {
            this._logger = logger;
            NHibernate.ISession session = NHibernateHelper.OpenSession();
            dao = new CallDao(session);
        }

        
        [HttpGet]
        public ActionResult StarterApi()
        {
            string message = "For save object Call, use resource api/Tracker with POST method and send " +
                "one valid object Call in the body. For mark the object Call as used, use " +
                "resource api/Tracker/CallUsed with PUT method and send the object Call in the body. " +
                "For request one list with all objects Call with not recorded in ech table, " +
                "use resouce api/Tracker/NotRecordedEch with GET method." +
                "For request one list with limit objects Call with not recorded in ech table, " +
                "use resouce api/Tracker/NotRecordedEch/{NUMBER_LIMIT}, where {NUMBER_LIMT} is sent the max objects " +
                "will you need, with GET method.";
            ResponseApi<String> response = new ResponseApi<string>
            {
                Data = message
            };
            return Ok(response);
        }

        [HttpGet("NotRecordedEch")]
        public ActionResult FindAllNotRecordedEch()
        {
            ResponseApi<IList<Call>> response = new ResponseApi<IList<Call>>();
            try
            {
                response.Data = this.dao.FindAllNotRecordedEch();
                response.Message = "Sucesso";
                this._logger.LogInformation($"Found {response.Data.Count} elements");
                return Ok(response);
            }
            catch (Exception e)
            {
                response.Message = $"Sorry, the API throws error";
                this._logger.LogError(response.Message, e);
                return StatusCode(StatusCodes.Status500InternalServerError, response);
            }
        }

        [HttpGet("NotRecordedEch/{limit}")]
        public ActionResult FindNotRecordedLimited(int limit)
        {
            ResponseApi<IList<Call>> response = new ResponseApi<IList<Call>>();
            try
            {
                response.Data = this.dao.FindNotRecordedEchLimited(limit);
                response.Message = "Sucesso";
                this._logger.LogInformation($"Found {response.Data.Count} elements");
                return Ok(response);
            }
            catch (Exception e)
            {
                response.Message = $"Sorry, the API throws error";
                this._logger.LogError(response.Message, e);
                return StatusCode(StatusCodes.Status500InternalServerError, response);
            }
        }

        [HttpPost]
        public ActionResult TrackerCall([FromBody] Call call)
        {
            ResponseApi<Call> response = new ResponseApi<Call>();
            response.Data = call;
            try
            {
                if (this.IsValidTrackerCall(call))
                {
                    call.DateSaved = DateTime.Now.ToString();
                    dao.Save(call);
                    response.Message = "Call persisted success";
                    this._logger.LogInformation(response.Message);
                    return Ok(response);
                }
                else
                {
                    response.Message = "Call not valid";
                    this._logger.LogInformation(response.Message);
                    return BadRequest(response);
                }
            }
            catch (Exception e)
            {
                response.Message = "Sorry, the API throws one error, the object it was not persisted";
                this._logger.LogError(response.Message, e);
                return StatusCode(StatusCodes.Status500InternalServerError, response);
            }
        }

        [HttpPut("CallTracked")]
        public ActionResult CallUsed([FromBody] Call call)
        {
            call.IsRecordedECH = true;
            call.Observation = "Tracked";
            call.DateTracked = DateTime.Now.ToString();
            return this.UpdateCall(call);
        }

        [HttpPut("CallNotTracked")]
        public ActionResult UpdateCallNotUsed([FromBody] Call call)
        {
            call.IsRecordedECH = true;
            call.Observation = "Untracked";
            call.DateTracked = DateTime.Now.ToString();
            return this.UpdateCall(call);
        }

        [HttpPut("reprocess/useadm")]
        public ActionResult Reprocess()
        {
            ResponseApi<String> response = new ResponseApi<String>();
            try
            {
                this.dao.ReprocessAll();
                response.Message = "Reprocessing has been completed";
                response.Data = response.Message;
                return Ok(response);
            }
            catch (Exception e)
            {
                response.Message = "Sorry, the API throws one error, the reprocessing has not been completed";
                response.Data = response.Message;
                this._logger.LogError(response.Message, e);
                return StatusCode(StatusCodes.Status500InternalServerError, response);
            }
        }

        public ActionResult UpdateCall(Call call)
        {
            ResponseApi<Call> response = new ResponseApi<Call>();
            response.Data = call;
            try
            {
                if (call.Id > 0)
                {
                    dao.Update(call, call.Id);
                    response.Message = "Call update success";
                    response.Data = call;

                    this._logger.LogInformation(response.Message);
                    return Ok(response);
                }

                response.Message = "Call not valid";
                this._logger.LogInformation(response.Message);
                return BadRequest(response);
            }
            catch (Exception e)
            {
                response.Message = "Sorry, the API throws one error, the object it was not persisted";
                this._logger.LogError(response.Message, e);
                return StatusCode(StatusCodes.Status500InternalServerError, response);
            }
        }
        
        private Boolean IsValidTrackerCall(Call call)
        {
            return call != null &&
                !String.IsNullOrWhiteSpace(call.UCIDOrigin) &&
                !String.IsNullOrWhiteSpace(call.UCIDTrack);
        }
    }
}