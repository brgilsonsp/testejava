using NHibernate;
using NHibernate.Cfg;
using NHibernate.Tool.hbm2ddl;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;

namespace TraceabilityAPI.Infra
{
    public class NHibernateHelper
    {
        private static readonly ISessionFactory FACTORY = NHibernateHelper.BuildSessionFactory();

        private static ISessionFactory BuildSessionFactory()
        {
            Configuration cfg = NHibernateHelper.GetConfiguration();
            return cfg.BuildSessionFactory();
        }

        public static ISession OpenSession()
        {
            return NHibernateHelper.FACTORY.OpenSession();
        }

        public static Configuration GetConfiguration()
        {
            Configuration cfg = new Configuration();
            cfg.Configure();
            cfg.AddAssembly(Assembly.GetExecutingAssembly());

            return cfg;
        }

        public static void BuildTables()
        {
            Configuration cfg = NHibernateHelper.GetConfiguration();
            new SchemaExport(cfg).Create(true, true);
        }
    }
}
