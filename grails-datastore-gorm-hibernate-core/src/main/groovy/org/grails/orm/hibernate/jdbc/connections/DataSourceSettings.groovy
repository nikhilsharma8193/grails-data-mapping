package org.grails.orm.hibernate.jdbc.connections

import groovy.transform.AutoClone
import groovy.transform.CompileStatic
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import org.grails.datastore.mapping.core.connections.ConnectionSourceSettings

/**
 * DataSource settings
 *
 * @author Graeme Rocher
 */
@Builder(builderStrategy = SimpleStrategy, prefix = '')
@AutoClone
class DataSourceSettings extends ConnectionSourceSettings {
    /**
     * The data source URL
     */
    String url

    /**
     * The driver class name
     */
    String driverClassName
    /**
     * The username
     */
    String username

    /**
     * The password
     */
    String password

    /**
     * The JNDI name
     */
    String jndiName
    /**
     * Whether the data source is pooled
     */
    boolean pooled = true

    /**
     * Whether the connection is readonly
     */
    boolean readOnly = false
    /**
     * The dialect to use
     */
    Class dialect = null
    /**
     * Whether to log SQL
     */
    boolean logSql = false

    /**
     * Whether to format the SQL
     */
    boolean formatSql = false

    /**
     * The default value for `hibernate.hbm2ddl.auto`
     */
    String dbCreate = "none"

    /**
     * The data source properties
     */
    Map properties = [:]
    /**
     * Convert to Hibernate properties
     *
     * @return The hibernate properties
     */
    @CompileStatic
    Properties toHibernateProperties() {
        Properties props = new Properties()
        props.put("hibernate.hbm2ddl.auto", dbCreate)
        props.put("hibernate.show_sql", String.valueOf(logSql))
        props.put("hibernate.format_sql", String.valueOf(formatSql))
        if(dialect != null) {
            props.put("hibernate.dialect", dialect.name)
        }
        return props
    }

    /**
     * @return Convert to datasource properties
     */
    @CompileStatic
    Map<String,String> toProperties() {
        Map<String,String> properties = new LinkedHashMap<>()
        properties.putAll(this.properties)
        properties.put("url", url)
        if(driverClassName) {
            properties.put("driverClassName", driverClassName)
        }
        if(username) {
            properties.put("username", username)
        }
        if(password) {
            properties.put("username", password)
        }
        if(readOnly) {
            properties.put("defaultReadOnly", String.valueOf(readOnly))
        }
        return properties
    }
}