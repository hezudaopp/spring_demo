package spittr.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import spittr.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 273cn on 16/12/17.
 */
public class JdbcBaseRepository<T> {
    private JdbcTemplate jdbcTemplate;

    private Class<T> clazz;

    private String tableName;

    @Autowired
    public JdbcBaseRepository(JdbcTemplate jdbcTemplate, Class<T> clazz) {
        this.jdbcTemplate = jdbcTemplate;
        this.clazz = clazz;
        this.tableName = StringUtil.camelToUnderline(clazz.getSimpleName());
    }

    /**
     * Auto increment field should be named with "id".
     * @param object
     * @return
     */
    public long insertAndReturnId(T object) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(tableName);
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> fieldMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = StringUtil.camelToUnderline(field.getName());
            boolean accessFlag = field.isAccessible();
            field.setAccessible(true);
            try {
                fieldMap.put(fieldName, field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return -1;
            }
            field.setAccessible(accessFlag);
        }
        return jdbcInsert.executeAndReturnKey(fieldMap).longValue();
    }

    /**
     * Update by id, (id is primary key of a table).
     * @param object
     * @return affected rows
     */
    public int updateById(T object) {
        StringBuilder updateSql = new StringBuilder();
        List<Object> argList = new ArrayList();
        updateSql.append("UPDATE " + tableName + " SET ");
        Field[] fields = clazz.getDeclaredFields();
        Object id = null;
        for (Field field : fields) {
            String fieldName = StringUtil.camelToUnderline(field.getName());
            boolean accessFlag = field.isAccessible();
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return -1;
            }
            if ("id".equals(fieldName)) {
                id = value;
            } else {
                updateSql.append("`" + fieldName + "` = ?, ");
                argList.add(value);
            }
            field.setAccessible(accessFlag);
        }
        // id or update fields are empty.
        if (id == null || argList.isEmpty()) return -1;
        // remove last comma
        updateSql.setLength(updateSql.length() - 2);
        updateSql.append(" ");
        updateSql.append("WHERE `id` = ?;");
        argList.add(id);
        return jdbcTemplate.update(updateSql.toString(), argList.toArray());
    }

    /**
     * All count of table
     * @return long size of table
     */
    public long count() {
        return jdbcTemplate.queryForObject("select count(1) from " + tableName, Long.class);
    }

    public T findOne(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM " + tableName + " where `id` = ?", new BaseRowMapper(), id);
    }

    private final class BaseRowMapper implements RowMapper<T> {
        public T mapRow(ResultSet rs, int rowNum) throws SQLException {
            T instance = null;
            try {
                instance = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    String attName = field.getName();
                    String fieldName = StringUtil.camelToUnderline(attName);
                    String setMethodName = "set" + Character.toUpperCase(attName.charAt(0)) + attName.substring(1);
                    Method setMethod = clazz.getMethod(setMethodName, field.getType());
                    Type fieldType = field.getType();
                    String typeName = fieldType.getTypeName();
                    typeName = typeName.substring(typeName.lastIndexOf(".")+1);
                    if (Character.isLowerCase(typeName.charAt(0))) {
                        typeName = Character.toUpperCase(typeName.charAt(0)) + typeName.substring(1);
                    }
                    Method rsGetMethod = rs.getClass().getMethod("get" + typeName, String.class);
                    setMethod.invoke(instance, rsGetMethod.invoke(rs, fieldName));
                }
            } catch (InstantiationException|IllegalAccessException|NoSuchMethodException|InvocationTargetException e) {
                e.printStackTrace();
            }
            return instance;
        }
    }
}
