using System;
using System.Collections.Generic;
using System.IO;
using System.Reflection;
using System.Text;
using System.Xml;

namespace BotRastreabilidade.Infra
{
    public class ConfigureLog4NetBot
    {
        public void Configure()
        {
            Version version = Assembly.GetExecutingAssembly().GetName().Version;
            string versionBot = $"{version.Major}.{version.Minor}.{version.Build}.{version.Revision}";

            XmlDocument log4netConfig = new XmlDocument();

            log4netConfig.Load(File.OpenRead("log4net.config"));
            var repo = log4net.LogManager.CreateRepository(Assembly.GetEntryAssembly(),
                       typeof(log4net.Repository.Hierarchy.Hierarchy));
            log4net.GlobalContext.Properties["version"] = versionBot;
            log4net.Config.XmlConfigurator.Configure(repo, log4netConfig["log4net"]);

        }
    }
}
