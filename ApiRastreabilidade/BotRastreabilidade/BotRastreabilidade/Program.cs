using BotRastreabilidade.Business;
using BotRastreabilidade.Infra;
using BotRastreabilidade.Models;
using log4net;
using NHibernate;
using System;
using System.Collections.Generic;
using System.IO;
using System.Reflection;
using System.Threading;
using System.Threading.Tasks;
using System.Xml;
using System.Linq;

namespace BotRastreabilidade
{
    class Program
    {

        static void Main(string[] args)
        {
            Version version = Assembly.GetExecutingAssembly().GetName().Version;
            System.Console.WriteLine($"Versão {version} - Atualizando tabela ECH. Aguarde...");

            ProcessRastreability process = new ProcessRastreability();
            process.ExecuteProcess().Wait();
        }
        
    }
}