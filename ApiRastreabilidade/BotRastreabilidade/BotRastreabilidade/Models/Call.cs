using BotRastreabilidade.Infra;
using Microsoft.Extensions.Configuration;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace BotRastreabilidade.Models
{
    public class Call
    {
        [JsonIgnore]
        private readonly IConfigurationRoot _configurationRoot;

        [JsonIgnore]
        private readonly int _daysAttemps;

        public Call()
        {
            this._configurationRoot = ReadConfiguration.BuildConfiguration();
            string daysAttempsString = this._configurationRoot["LimitDaysAttemps"];
            this._daysAttemps = 4;
            Int32.TryParse(daysAttempsString, out this._daysAttemps);
        }

        public Int64 Id { get; set; }
        public String UCIDOrigin { get; set; }
        public int CallIdOrigin { get; set; }
        public string UCIDTrack { get; set; }
        public int CallIdTrack { get; set; }
        public string DateCallTrack { get; set; }
        public String TypeCallTrack { get; set; }
        public Boolean IsRecordedECH { get; set; }

        [JsonIgnore]
        public int CallidTrackEch { get; set; }

        [JsonIgnore]
        public Boolean IsCallValidToRelate
        {
            get
            {
                return !this.IsRecordedECH && !String.IsNullOrWhiteSpace(this.UCIDOrigin);
            }
        }

        [JsonIgnore]
        public Boolean IsExceededLimitAttemps
        {
            get { return DifferenceBetweenDataCallAndToday >= this._daysAttemps; }
        }

        [JsonIgnore]
        private int DifferenceBetweenDataCallAndToday
        {
            get{
                DateTime dateCall = DateTime.Parse(this.DateCallTrack);
                TimeSpan dateDifference = DateTime.Now.Subtract(dateCall);
                return dateDifference.Days;
            }
            
        }

        public override string ToString()
        {
            return $"Call: [Id: {Id}, UCIDOrigin: {UCIDOrigin}, CallIdOrigin: {CallIdOrigin}, UCIDTrack: {UCIDTrack}, " +
                $"CallIdTrack: {CallIdTrack}, DateCallTrack: {DateCallTrack}, TypeCallTrack: {TypeCallTrack}, " +
                $"IsRecordedECH: {IsRecordedECH}, CallidTrackEch: {CallidTrackEch}]";
        }
    }
}
