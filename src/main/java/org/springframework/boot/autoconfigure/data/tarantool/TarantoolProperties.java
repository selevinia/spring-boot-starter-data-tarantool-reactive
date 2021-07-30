package org.springframework.boot.autoconfigure.data.tarantool;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

/**
 * Configuration properties for Tarantool.
 *
 * @author Tatiana Blinova
 */
@ConfigurationProperties("spring.data.tarantool")
public class TarantoolProperties {

    /**
     * Comma-separated list of Tarantool nodes (host:port) to connect to
     */
    private List<String> nodes = List.of("localhost:3301");

    /**
     * Tarantool user name
     */
    private String userName = "guest";

    /**
     * Tarantool user password
     */
    private String password = "";

    /**
     * Number of connections used for sending requests to the server
     */
    private int connections = 1;

    /**
     * Timeout for connecting to the Tarantool server.
     * If a duration suffix is not specified, milliseconds will be used
     */
    private Duration connectTimeout = Duration.ofMillis(1000);

    /**
     * Timeout for reading the responses from Tarantool server.
     * If a duration suffix is not specified, milliseconds will be used
     */
    private Duration readTimeout = Duration.ofMillis(1000);

    /**
     * Timeout for receiving a response from the Tarantool server.
     * If a duration suffix is not specified, milliseconds will be used
     */
    private Duration requestTimeout = Duration.ofMillis(2000);

    /**
     * Fully qualified name of the FieldNamingStrategy to use
     */
    private Class<?> fieldNamingStrategy;

    /**
     * Enable tarantool cluster mode
     */
    private boolean cluster = true;

    /**
     * Enable Tarantool CRUD module usage
     */
    private boolean crud = true;

    /**
     * Proxy operations configuration
     */
    private ProxyOperations proxyOperations;

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnections() {
        return connections;
    }

    public void setConnections(int connections) {
        this.connections = connections;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Duration getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(Duration requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public Class<?> getFieldNamingStrategy() {
        return fieldNamingStrategy;
    }

    public void setFieldNamingStrategy(Class<?> fieldNamingStrategy) {
        this.fieldNamingStrategy = fieldNamingStrategy;
    }

    public boolean isCluster() {
        return cluster;
    }

    public void setCluster(boolean cluster) {
        this.cluster = cluster;
    }

    public boolean isCrud() {
        return crud;
    }

    public void setCrud(boolean crud) {
        this.crud = crud;
    }

    public ProxyOperations getProxyOperations() {
        return proxyOperations;
    }

    public void setProxyOperations(ProxyOperations proxyOperations) {
        this.proxyOperations = proxyOperations;
    }

    public static class ProxyOperations {

        /**
         * API function name for getting the spaces and indexes schema
         */
        private String getSchemaFunctionName;

        /**
         * API function name for performing the delete operation
         */
        private String deleteFunctionName;

        /**
         * API function name for performing the insert operation
         */
        private String insertFunctionName;

        /**
         * API function name for performing the replace operation
         */
        private String replaceFunctionName;

        /**
         * API function name for performing the update operation
         */
        private String updateFunctionName;

        /**
         * API function name for performing the upsert operation
         */
        private String upsertFunctionName;

        /**
         * API function name for performing the select operation
         */
        private String selectFunctionName;

        public String getGetSchemaFunctionName() {
            return getSchemaFunctionName;
        }

        public void setGetSchemaFunctionName(String getSchemaFunctionName) {
            this.getSchemaFunctionName = getSchemaFunctionName;
        }

        public String getDeleteFunctionName() {
            return deleteFunctionName;
        }

        public void setDeleteFunctionName(String deleteFunctionName) {
            this.deleteFunctionName = deleteFunctionName;
        }

        public String getInsertFunctionName() {
            return insertFunctionName;
        }

        public void setInsertFunctionName(String insertFunctionName) {
            this.insertFunctionName = insertFunctionName;
        }

        public String getReplaceFunctionName() {
            return replaceFunctionName;
        }

        public void setReplaceFunctionName(String replaceFunctionName) {
            this.replaceFunctionName = replaceFunctionName;
        }

        public String getUpdateFunctionName() {
            return updateFunctionName;
        }

        public void setUpdateFunctionName(String updateFunctionName) {
            this.updateFunctionName = updateFunctionName;
        }

        public String getUpsertFunctionName() {
            return upsertFunctionName;
        }

        public void setUpsertFunctionName(String upsertFunctionName) {
            this.upsertFunctionName = upsertFunctionName;
        }

        public String getSelectFunctionName() {
            return selectFunctionName;
        }

        public void setSelectFunctionName(String selectFunctionName) {
            this.selectFunctionName = selectFunctionName;
        }
    }
}
