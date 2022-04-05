package org.mybatis.jpetstore.conf;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.cdi.SessionFactoryProvider;

public class MyProducers {

	@Produces
	@ApplicationScoped
	@SessionFactoryProvider
	public SqlSessionFactory produceFactory() {
		String resource = "mybatis-config.xml";
		SqlSessionFactory sqlSessionFactory = null;
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			try (SqlSession session = sqlSessionFactory.openSession()) {
			      Connection conn = session.getConnection();
			      Reader readerSchema = Resources.getResourceAsReader("database/jpetstore-hsqldb-schema.sql");
			      ScriptRunner runnerSchema = new ScriptRunner(conn);
			      runnerSchema.setLogWriter(null);
			      runnerSchema.runScript(readerSchema);
			      readerSchema.close();
			      
			      Reader readerDataload = Resources.getResourceAsReader("database/jpetstore-hsqldb-dataload.sql");
			      ScriptRunner runnerDataload = new ScriptRunner(conn);
			      runnerDataload.setLogWriter(null);
			      runnerDataload.runScript(readerDataload);
			      readerDataload.close();
			      
			    }
			    catch (Exception ex) {
			      ex.printStackTrace();
			    }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sqlSessionFactory;
	}
}
