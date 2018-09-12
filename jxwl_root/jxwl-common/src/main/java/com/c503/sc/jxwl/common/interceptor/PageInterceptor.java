/*
 * 文件名：PageInterceptor.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年6月4日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.StringUtils;

import com.c503.sc.base.entity.Page;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉 通过拦截StatementHandler的prepare方法，重写sql语句实现物理分页。 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2015年5月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Intercepts(value = {@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor implements Interceptor {
    
    /** 日志记录器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(PageInterceptor.class);
    
    /** 对象工程，默认创建类的对象，负责创建对象实体类 */
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY =
        new DefaultObjectFactory();
    
    /** 对象包装工厂 */
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY =
        new DefaultObjectWrapperFactory();
    
    /** 默认数据库类型 */
    private static String defaultDialect = "mysql";
    
    /** 需要拦截的ID(正则匹配) */
    private static String defaultPageSqlId = ".*Params$";
    
    /** 数据库类型(默认为mysql) */
    private static String dialect = "";
    
    /**
     * 〈一句话功能简述〉设置数据库类型方言
     * 〈功能详细描述〉
     * 
     * @param dialect 数据库类型方言
     * @see [类、类#方法、类#成员]
     */
    public static void setDialect(String dialect) {
        setDialectValue(dialect);
    }
    
    /**
     * 〈一句话功能简述〉设置数据库类型方言
     * 〈功能详细描述〉
     * 
     * @param dialect 数据库类型方言
     * @see [类、类#方法、类#成员]
     */
    public static void setDialectValue(String dialect) {
        PageInterceptor.dialect = dialect;
    }
    
    /**
     * {@inheritDoc}
     *
     */
    public Object intercept(Invocation invocation)
        throws Throwable {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START, invocation);
        StatementHandler statementHandler =
            (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler =
            MetaObject.forObject(statementHandler,
                DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY);
        
        // 分离代理
        separateProxy(metaStatementHandler);
        // 读取mybaits配置文件，获取数据库方言
        setDialect(metaStatementHandler);
        
        MappedStatement mappedStatement =
            (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        // 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的MappedStatement的sql
        BoundSql boundSql =
            (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        String sql = boundSql.getSql();
        if (mappedStatement.getId().matches(defaultPageSqlId)) {
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null) {
                throw new NullPointerException("parameterObject is null!");
            }
            else {
                Page page = null;
                Object obj =
                    metaStatementHandler.getValue("delegate.boundSql.parameterObject.page");
                if (parameterObject instanceof Page) {
                    // 参数就是Page实体
                    page = (Page) parameterObject;
                }
                else if (obj instanceof Page) {
                    page = (Page) obj;
                }
                if (null != page) {
                    
                    // 记录原始sql信息
                    LOGGER.info(0, sql);
                    // 根据对应方言重写分页sql
                    String pageSql = buildPageSql(sql, page);
                    // 记录原始重写的sql信息
                    LOGGER.info(0, pageSql);
                    // 重新设置分页
                    metaStatementHandler.setValue("delegate.boundSql.parameterObject.page",
                        page);
                    metaStatementHandler.setValue("delegate.boundSql.sql",
                        pageSql);
                    // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
                    metaStatementHandler.setValue("delegate.rowBounds.offset",
                        RowBounds.NO_ROW_OFFSET);
                    metaStatementHandler.setValue("delegate.rowBounds.limit",
                        RowBounds.NO_ROW_LIMIT);
                    Connection connection =
                        (Connection) invocation.getArgs()[0];
                    // 重设分页对象的总页数等
                    resetPage(sql, connection, mappedStatement, boundSql, page);
                }
            }
        }
        // 记录程序执行方法结束调试日志
        LOGGER.debug(SystemContants.DEBUG_END, invocation);
        // 将执行权交给下一个拦截器
        return invocation.proceed();
    }
    
    /**
     * 〈一句话功能简述〉分离代理 〈功能详细描述〉
     * 
     * @param metaStatementHandler 代理对象
     * @see [类、类#方法、类#成员]
     */
    private void separateProxy(MetaObject metaStatementHandler) {
        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler =
                MetaObject.forObject(object,
                    DEFAULT_OBJECT_FACTORY,
                    DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        // 分离最后一个代理对象的目标类
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler =
                MetaObject.forObject(object,
                    DEFAULT_OBJECT_FACTORY,
                    DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        
    }
    
    /**
     * 〈一句话功能简述〉mysql的分页语句 〈功能详细描述〉
     * 
     * @param sql 需分页的SQL
     * @param page 分页对象
     * @return MySQL分页语句
     * @see [类、类#方法、类#成员]
     */
    public StringBuilder buildPageSqlForMysql(String sql, Page page) {
        StringBuilder pageSql = new StringBuilder();
        String beginrow =
            String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        pageSql.append(sql);
        pageSql.append(" limit " + beginrow + "," + page.getPageSize());
        return pageSql;
    }
    
    /**
     * @param sql 需分页的SQL
     * @param page 分页对象
     * @return postgresql分页语句
     */
    public StringBuilder buildPageSqlForPostgre(String sql, Page page) {
        
        StringBuilder pageSql = new StringBuilder();
        String beginrow =
            String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        pageSql.append(sql);
        pageSql.append(" limit " + page.getPageSize() + " offset " + beginrow);
        return pageSql;
    }
    
    /**
     * 〈一句话功能简述〉参考hibernate的组装oracle的分页 〈功能详细描述〉
     * 
     * @param sql 需分页的SQL
     * @param page 分页对象
     * @return MySQL分页语句
     * @see [类、类#方法、类#成员]
     */
    public StringBuilder buildPageSqlForOracle(String sql, Page page) {
        StringBuilder pageSql = new StringBuilder();
        String beginrow =
            String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        String endrow =
            String.valueOf(page.getCurrentPage() * page.getPageSize());
        
        pageSql.append("SELECT * FROM ( SELECT temp.*, ROWNUM row_id FROM ( ");
        pageSql.append(sql);
        pageSql.append(" ) temp) WHERE row_id <= ").append(endrow);
        pageSql.append("AND row_id > ").append(beginrow);
        return pageSql;
    }
    
    /**
     * {@inheritDoc}
     *
     */
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        else {
            return target;
        }
    }
    
    /**
     * {@inheritDoc}
     *
     */
    public void setProperties(Properties properties) {
        setDialectValue(properties.getProperty("dialect"));
        if (StringUtils.isEmpty(dialect)) {
            // 记录操作异常信息
            LOGGER.error(0,
                null,
                "Property dialect is not setted,use default 'mysql' ");
            setDialectValue(defaultDialect);
        }
    }
    
    /**
     * 〈一句话功能简述〉读取mybaits配置文件，获取数据库方言并复制给dialect 〈功能详细描述〉
     * 
     * @param metaStatementHandler 元数据处理器
     * @see [类、类#方法、类#成员]
     */
    private void setDialect(MetaObject metaStatementHandler) {
        Configuration configuration =
            (Configuration) metaStatementHandler.getValue("delegate.configuration");
        setDialectValue(configuration.getVariables().getProperty("dialect"));
        if (StringUtils.isEmpty(dialect)) {
            // 记录操作异常信息
            LOGGER.error(0,
                null,
                "Property dialect is not setted,use default 'mysql' ");
            setDialectValue(defaultDialect);
        }
        
    }
    
    /**
     * 〈一句话功能简述〉从数据库里查询总的记录数并计算总页数，回写进分页参数Page,这样调用者就可用通过 分页参数 Page获得相关信息
     * 〈功能详细描述〉
     * 
     * @param sql 需要查询的sql语句
     * @param connection 数据库连接
     * @param mappedStatement mybatis配置文件中的一个SQL节点
     * @param boundSql 表示动态生成的SQL语句以及相应的参数信息
     * @param page 分页对象
     * @return 总条数
     * @see [类、类#方法、类#成员]
     */
    private int resetPage(String sql, Connection connection,
        MappedStatement mappedStatement, BoundSql boundSql, Page page) {
        /**
         * 记录统计 == oracle 加 as 报错(SQL command not properly ended)
         * */
        // 记录总记录数
        String countSql = "SELECT COUNT(0) FROM (" + sql + ") total";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        // 总记录数
        int totalCount = 0;
        try {
            // 通过链接打开已编译的 SQL语句实例对象
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS =
                new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(),
                    boundSql.getParameterObject());
            setParameters(countStmt,
                mappedStatement,
                countBS,
                boundSql.getParameterObject());
            // 执行sql
            rs = countStmt.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            // 设置分页对象
            setPage(totalCount, page);
        }
        catch (SQLException e) {
            // 记录操作异常信息
            LOGGER.error(0, e, "Ignore this exception");
        }
        finally {
            try {
                if (null != rs) {
                    rs.close();
                }
            }
            catch (SQLException e) {
                LOGGER.error(0, e, "Ignore this exception");
            }
            try {
                if (null != countStmt) {
                    countStmt.close();
                }
            }
            catch (SQLException e) {
                LOGGER.error(0, e, "Ignore this exception");
            }
        }
        return totalCount;
    }
    
    /**
     * 〈一句话功能简述〉设置分页对象信息 〈功能详细描述〉根据总条数、当前页面与每页条数
     * 
     * @param totalCount 总条数
     * @param page 分页对象
     * @see [类、类#方法、类#成员]
     */
    private void setPage(int totalCount, Page page) {
        page.setTotalCount(totalCount);
        int totalPage =
            totalCount / page.getPageSize()
                + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
        page.setTotalPage(totalPage);
        // 计算上一页、下一页
        int prePage = 1;
        if (page.getCurrentPage() > 1) {
            prePage = page.getCurrentPage() - 1;
        }
        page.setPrePage(prePage);
        int nextPage = 1;
        if (page.getCurrentPage() >= totalPage) {
            nextPage = totalPage;
        }
        else if (page.getCurrentPage() <= 0) {
            page.setCurrentPage(1);
        }
        else {
            nextPage = page.getCurrentPage() + 1;
        }
        page.setNextPage(nextPage);
    }
    
    /**
     * 〈一句话功能简述〉对SQL参数(?)设值 〈功能详细描述〉
     * 
     * @param ps 预处理对象
     * @param mappedStatement mybatis配置文件中的一个SQL节点
     * @param boundSql 表示动态生成的SQL语句以及相应的参数信息
     * @param parameterObject 参数对象
     * @throws SQLException SQL异常
     * @see [类、类#方法、类#成员]
     */
    private void setParameters(PreparedStatement ps,
        MappedStatement mappedStatement, BoundSql boundSql,
        Object parameterObject)
        throws SQLException {
        ParameterHandler parameterHandler =
            new DefaultParameterHandler(mappedStatement, parameterObject,
                boundSql);
        parameterHandler.setParameters(ps);
    }
    
    /**
     * 〈一句话功能简述〉根据数据库类型，生成特定的分页sql 〈功能详细描述〉
     * 
     * @param sql SQL语句
     * @param page 分页对象
     * @return 分装好的sql
     * @see [类、类#方法、类#成员]
     */
    private String buildPageSql(String sql, Page page) {
        StringBuilder pageSql = new StringBuilder();
        if ("mysql".equals(dialect)) {
            pageSql = buildPageSqlForMysql(sql, page);
        }
        if ("oracle".equals(dialect)) {
            pageSql = buildPageSqlForOracle(sql, page);
        }
        if ("postgresql".equals(dialect)) {
            pageSql = buildPageSqlForPostgre(sql, page);
        }
        return pageSql.toString();
    }
}
