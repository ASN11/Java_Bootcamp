package model;

import annotations.OrmColumn;
import annotations.OrmColumnId;
import annotations.OrmEntity;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrmManager {
    DataSource dataSource;

    public OrmManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initializeTables(Class<?>... classes) {
        removeTables(classes);
        try (Connection conn = dataSource.getConnection()) {
            for (Class<?> clazz : classes) {
                OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
                if (ormEntity != null) {
                    createTable(conn, clazz, ormEntity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTables(Class<?>... classes) {
        try (Connection conn = dataSource.getConnection()) {
            for (Class<?> clazz : classes) {
                OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
                if (ormEntity != null) {
                    dropTable(conn, ormEntity.table());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Object entity) {
        try (Connection conn = dataSource.getConnection()) {
            OrmEntity ormEntity = entity.getClass().getAnnotation(OrmEntity.class);
            if (ormEntity != null) {
                String tableName = ormEntity.table();
                Field[] fields = entity.getClass().getDeclaredFields();
                StringBuilder columnNames = new StringBuilder();
                StringBuilder values = new StringBuilder();

                for (Field field : fields) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    if (ormColumn != null) {
                        field.setAccessible(true);
                        String columnName = ormColumn.name();
                        Object value = field.get(entity);
                        columnNames.append(columnName).append(",");
                        values.append("'").append(value).append("',");
                    }
                }

                columnNames.deleteCharAt(columnNames.length() - 1);
                values.deleteCharAt(values.length() - 1);

                String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columnNames, values);
                executeStatement(conn, sql);
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void update(Object entity) {
        try (Connection conn = dataSource.getConnection()) {
            OrmEntity ormEntity = entity.getClass().getAnnotation(OrmEntity.class);
            if (ormEntity != null) {
                String tableName = ormEntity.table();
                Field[] fields = entity.getClass().getDeclaredFields();
                StringBuilder setClause = new StringBuilder();
                StringBuilder whereClause = new StringBuilder();

                for (Field field : fields) {
                    field.setAccessible(true);
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    if (ormColumn != null) {
                        String columnName = ormColumn.name();
                        Object value = field.get(entity);
                        setClause.append(columnName).append(" = '").append(value).append("',");
                    } if (field.getAnnotation(OrmColumnId.class) != null) {
                        whereClause.append("id = ").append(field.get(entity));
                    }
                }

                setClause.deleteCharAt(setClause.length() - 1);

                String sql = String.format("UPDATE %s SET %s WHERE %s", tableName, setClause, whereClause);
                executeStatement(conn, sql);
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public <T> T findById(int id, Class<T> clazz) {
        try (Connection conn = dataSource.getConnection()) {
            OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);
            if (ormEntity != null) {
                String tableName = ormEntity.table();
                Field[] fields = clazz.getDeclaredFields();

                String sql = String.format("SELECT * FROM %s WHERE id = %d", tableName, id);
                ResultSet resultSet = executeQuery(conn, sql);

                if (resultSet.next()) {
                    T result = clazz.newInstance();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                        if (ormColumn != null) {
                            String columnName = ormColumn.name();
                            Object value = resultSet.getObject(columnName);
                            field.set(result, value);
                        } else if (field.getAnnotation(OrmColumnId.class) != null) {
                            Object value = resultSet.getObject("id");
                            field.set(result, value);
                        }
                    }
                    return result;
                }
            }
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createTable(Connection conn, Class<?> clazz, OrmEntity ormEntity) throws SQLException {
        String tableName = ormEntity.table();
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder columns = new StringBuilder();

        for (Field field : fields) {
            OrmColumnId ormColumnId = field.getAnnotation(OrmColumnId.class);
            if (ormColumnId != null) {
                columns.append("id SERIAL PRIMARY KEY, ");
            }
            OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
            if (ormColumn != null) {
                String columnName = ormColumn.name();
                String columnType = getColumnDataType(field.getType(), ormColumn.length());
                columns.append(columnName).append(" ").append(columnType).append(", ");
            }
        }

        columns.deleteCharAt(columns.length() - 2);

        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s)", tableName, columns);
        executeStatement(conn, sql);
    }

    private void dropTable(Connection conn, String tableName) throws SQLException {
        String sql = String.format("DROP TABLE IF EXISTS %s", tableName);
        executeStatement(conn, sql);
    }

    private void executeStatement(Connection conn, String sql) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Executed SQL: " + sql);
        }
    }

    private ResultSet executeQuery(Connection conn, String sql) throws SQLException {
        Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            System.out.println("Executed SQL: " + sql);
            return resultSet;
    }

    private String getColumnDataType(Class<?> fieldType, int length) {
        if (fieldType == String.class) {
            return "VARCHAR(" + length + ")";
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return "INT";
        } else if (fieldType == Double.class || fieldType == double.class) {
            return "DOUBLE";
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return "BOOLEAN";
        } else if (fieldType == Long.class || fieldType == long.class) {
            return "BIGINT";
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType);
        }
    }
}

