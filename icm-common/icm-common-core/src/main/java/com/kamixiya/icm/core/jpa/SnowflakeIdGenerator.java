package com.kamixiya.icm.core.jpa;

import com.kamixiya.icm.core.idgenerator.Snowflake;
import com.kamixiya.icm.core.rest.SpringUtil;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 * Hibernate的Snowflake算法的ID生成器
 * Hibernate初始化时将会自动创建生成器
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
public class SnowflakeIdGenerator implements IdentifierGenerator, Configurable {
    private String entityName;

    private Snowflake snowflake;

    public SnowflakeIdGenerator() {
    }
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) {
        if (snowflake == null) {
            this.snowflake = SpringUtil.getBean("snowflake");
            if (this.snowflake == null) {
                throw new HibernateException("Cannot create SnowflakeIdGenerator!");
            }
        }
        Serializable id = session.getEntityPersister(this.entityName, o).getIdentifier(o, session);
        if (id != null) { // 有ID就无需再生成
            return id;
        } else {
            return snowflake.nextId();
        }
    }
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        this.entityName = params.getProperty("entity_name");
        if (this.entityName == null) {
            throw new MappingException("no entity name");
        }
    }
}
