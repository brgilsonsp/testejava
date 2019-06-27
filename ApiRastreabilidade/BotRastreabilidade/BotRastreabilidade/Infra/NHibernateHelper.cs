using NHibernate;
using NHibernate.Cfg;
using System;
using System.Collections.Generic;
using System.Reflection;
using System.Text;

namespace BotRastreabilidade.Infra
{
    public class NHibernateHelper
    {
        private static readonly ISessionFactory sessionFactory = NHibernateHelper.BuildSessionFactory();

        private static ISessionFactory BuildSessionFactory()
        {
            Configuration configuration = NHibernateHelper.GetConfiguration();
            return configuration.BuildSessionFactory();
        }

        public static ISession OpenSession()
        {
            return NHibernateHelper.sessionFactory.OpenSession();
        }

        public static Configuration GetConfiguration()
        {
            Configuration configuration = new Configuration();
            configuration.Configure();
            configuration.AddAssembly(Assembly.GetExecutingAssembly());
            return configuration;
        }
    }
}
