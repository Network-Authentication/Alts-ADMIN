package net.dev.alts.database;

import com.zaxxer.hikari.*;
import net.dev.alts.utils.*;

import java.sql.*;

public class MySQLCore extends DataBaseCore
{
    private static String driverName = "com.mysql.jdbc.Driver";
    private String username;
    private String password;
    private Connection connection;
    private String url;
    private HikariDataSource ds;
    public MySQLCore(String host, int port, String dbname, String username, String password)
    {
        url = ("jdbc:mysql://" + host + ":" + port + "/" + dbname);
        this.username = username;
        this.password = password;
        //创建连接池参数
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        config.addDataSourceProperty("connectionTimeout", "10000"); // 连接超时：1秒
        config.addDataSourceProperty("idleTimeout", "60000"); // 空闲超时：60秒
        config.addDataSourceProperty("maximumPoolSize", "20"); // 最大连接数：10


        try {
            ds = new HikariDataSource(config);//使用参数创建连接池
        }catch (Exception e){
            LogUtils.writeLog(e.getMessage());
            System.exit(0);
        }
//        try
//        {
//            Class.forName(driverName).newInstance();
//        }
//        catch (Exception e)
//        {
//            System.out.println("数据库初始化失败 请检查驱动 " + driverName + " 是否存在!");
//        }
    }
    public HikariConfig getDs(){//外部获取连接池
        return ds;
    }
    public Connection getDbCon() throws SQLException {//外部获取连接
        return ds.getConnection();
    }

    @Override
    public boolean createTables(String tableName, KeyValue fields, String conditions)
            throws SQLException
    {
        String sql = "CREATE TABLE IF NOT EXISTS `" + tableName + "` ( "
                + fields.toCreateString()
                + (conditions == null ? ""
                : new StringBuilder(" , ").append(conditions).toString())
                + " ) ENGINE = MyISAM DEFAULT CHARSET=GBK;";
        return execute(sql);
    }

    @Override
    public Connection getConnection()
    {
        try
        {
            if ((connection != null) && (!connection.isClosed()))
            {
                return connection;
            }
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (SQLException e)
        {
            LogUtils.writeLog(e.getMessage());
            System.exit(0);
        }
        return null;
    }
}
