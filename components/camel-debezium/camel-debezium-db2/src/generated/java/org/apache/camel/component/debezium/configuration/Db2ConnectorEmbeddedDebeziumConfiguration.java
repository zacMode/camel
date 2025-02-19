package org.apache.camel.component.debezium.configuration;

import io.debezium.config.Configuration;
import io.debezium.connector.db2.Db2Connector;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

@UriParams
public class Db2ConnectorEmbeddedDebeziumConfiguration
        extends
            EmbeddedDebeziumConfiguration {

    private static final String LABEL_NAME = "consumer,db2";
    @UriParam(label = LABEL_NAME, defaultValue = "8192")
    private int maxQueueSize = 8192;
    @UriParam(label = LABEL_NAME)
    private String databaseHistoryKafkaTopic;
    @UriParam(label = LABEL_NAME)
    private String columnBlacklist;
    @UriParam(label = LABEL_NAME, defaultValue = "0ms", javaType = "java.time.Duration")
    private long snapshotDelayMs = 0;
    @UriParam(label = LABEL_NAME, defaultValue = "100")
    private int databaseHistoryKafkaRecoveryAttempts = 100;
    @UriParam(label = LABEL_NAME)
    private String tableBlacklist;
    @UriParam(label = LABEL_NAME)
    private String tableWhitelist;
    @UriParam(label = LABEL_NAME, defaultValue = "precise")
    private String decimalHandlingMode = "precise";
    @UriParam(label = LABEL_NAME, defaultValue = "100ms", javaType = "java.time.Duration")
    private int databaseHistoryKafkaRecoveryPollIntervalMs = 100;
    @UriParam(label = LABEL_NAME, defaultValue = "500ms", javaType = "java.time.Duration")
    private long pollIntervalMs = 500;
    @UriParam(label = LABEL_NAME, defaultValue = "__debezium-heartbeat")
    private String heartbeatTopicsPrefix = "__debezium-heartbeat";
    @UriParam(label = LABEL_NAME, defaultValue = "true")
    private boolean tableIgnoreBuiltin = true;
    @UriParam(label = LABEL_NAME)
    private int snapshotFetchSize;
    @UriParam(label = LABEL_NAME)
    private String databaseHistoryFileFilename;
    @UriParam(label = LABEL_NAME)
    private String databaseUser;
    @UriParam(label = LABEL_NAME)
    private String databaseDbname;
    @UriParam(label = LABEL_NAME)
    private String snapshotSelectStatementOverrides;
    @UriParam(label = LABEL_NAME)
    private String databaseHistoryKafkaBootstrapServers;
    @UriParam(label = LABEL_NAME, defaultValue = "adaptive")
    private String timePrecisionMode = "adaptive";
    @UriParam(label = LABEL_NAME)
    @Metadata(required = true)
    private String databaseServerName;
    @UriParam(label = LABEL_NAME, defaultValue = "0ms", javaType = "java.time.Duration")
    private int heartbeatIntervalMs = 0;
    @UriParam(label = LABEL_NAME, defaultValue = "v2")
    private String sourceStructVersion = "v2";
    @UriParam(label = LABEL_NAME, defaultValue = "fail")
    private String eventProcessingFailureHandlingMode = "fail";
    @UriParam(label = LABEL_NAME, defaultValue = "50000")
    private int databasePort = 50000;
    @UriParam(label = LABEL_NAME)
    private String columnExcludeList;
    @UriParam(label = LABEL_NAME)
    private String tableExcludeList;
    @UriParam(label = LABEL_NAME)
    private String databaseHostname;
    @UriParam(label = LABEL_NAME)
    @Metadata(required = true)
    private String databasePassword;
    @UriParam(label = LABEL_NAME)
    private String tableIncludeList;
    @UriParam(label = LABEL_NAME, defaultValue = "2048")
    private int maxBatchSize = 2048;
    @UriParam(label = LABEL_NAME, defaultValue = "initial")
    private String snapshotMode = "initial";
    @UriParam(label = LABEL_NAME, defaultValue = "class io.debezium.relational.history.KafkaDatabaseHistory")
    private String databaseHistory = "io.debezium.relational.history.KafkaDatabaseHistory";

    /**
     * Maximum size of the queue for change events read from the database log
     * but not yet recorded or forwarded. Defaults to 8192, and should always be
     * larger than the maximum batch size.
     */
    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public int getMaxQueueSize() {
        return maxQueueSize;
    }

    /**
     * The name of the topic for the database schema history
     */
    public void setDatabaseHistoryKafkaTopic(String databaseHistoryKafkaTopic) {
        this.databaseHistoryKafkaTopic = databaseHistoryKafkaTopic;
    }

    public String getDatabaseHistoryKafkaTopic() {
        return databaseHistoryKafkaTopic;
    }

    /**
     * Regular expressions matching columns to exclude from change events
     * (deprecated, use "column.exclude.list" instead)
     */
    public void setColumnBlacklist(String columnBlacklist) {
        this.columnBlacklist = columnBlacklist;
    }

    public String getColumnBlacklist() {
        return columnBlacklist;
    }

    /**
     * A delay period before a snapshot will begin, given in milliseconds.
     * Defaults to 0 ms.
     */
    public void setSnapshotDelayMs(long snapshotDelayMs) {
        this.snapshotDelayMs = snapshotDelayMs;
    }

    public long getSnapshotDelayMs() {
        return snapshotDelayMs;
    }

    /**
     * The number of attempts in a row that no data are returned from Kafka
     * before recover completes. The maximum amount of time to wait after
     * receiving no data is (recovery.attempts) x (recovery.poll.interval.ms).
     */
    public void setDatabaseHistoryKafkaRecoveryAttempts(
            int databaseHistoryKafkaRecoveryAttempts) {
        this.databaseHistoryKafkaRecoveryAttempts = databaseHistoryKafkaRecoveryAttempts;
    }

    public int getDatabaseHistoryKafkaRecoveryAttempts() {
        return databaseHistoryKafkaRecoveryAttempts;
    }

    /**
     * A comma-separated list of regular expressions that match the
     * fully-qualified names of tables to be excluded from monitoring
     * (deprecated, use "table.exclude.list" instead)
     */
    public void setTableBlacklist(String tableBlacklist) {
        this.tableBlacklist = tableBlacklist;
    }

    public String getTableBlacklist() {
        return tableBlacklist;
    }

    /**
     * The tables for which changes are to be captured (deprecated, use
     * "table.include.list" instead)
     */
    public void setTableWhitelist(String tableWhitelist) {
        this.tableWhitelist = tableWhitelist;
    }

    public String getTableWhitelist() {
        return tableWhitelist;
    }

    /**
     * Specify how DECIMAL and NUMERIC columns should be represented in change
     * events, including:'precise' (the default) uses java.math.BigDecimal to
     * represent values, which are encoded in the change events using a binary
     * representation and Kafka Connect's
     * 'org.apache.kafka.connect.data.Decimal' type; 'string' uses string to
     * represent values; 'double' represents values using Java's 'double', which
     * may not offer the precision but will be far easier to use in consumers.
     */
    public void setDecimalHandlingMode(String decimalHandlingMode) {
        this.decimalHandlingMode = decimalHandlingMode;
    }

    public String getDecimalHandlingMode() {
        return decimalHandlingMode;
    }

    /**
     * The number of milliseconds to wait while polling for persisted data
     * during recovery.
     */
    public void setDatabaseHistoryKafkaRecoveryPollIntervalMs(
            int databaseHistoryKafkaRecoveryPollIntervalMs) {
        this.databaseHistoryKafkaRecoveryPollIntervalMs = databaseHistoryKafkaRecoveryPollIntervalMs;
    }

    public int getDatabaseHistoryKafkaRecoveryPollIntervalMs() {
        return databaseHistoryKafkaRecoveryPollIntervalMs;
    }

    /**
     * Time to wait for new change events to appear after receiving no events,
     * given in milliseconds. Defaults to 500 ms.
     */
    public void setPollIntervalMs(long pollIntervalMs) {
        this.pollIntervalMs = pollIntervalMs;
    }

    public long getPollIntervalMs() {
        return pollIntervalMs;
    }

    /**
     * The prefix that is used to name heartbeat topics.Defaults to
     * __debezium-heartbeat.
     */
    public void setHeartbeatTopicsPrefix(String heartbeatTopicsPrefix) {
        this.heartbeatTopicsPrefix = heartbeatTopicsPrefix;
    }

    public String getHeartbeatTopicsPrefix() {
        return heartbeatTopicsPrefix;
    }

    /**
     * Flag specifying whether built-in tables should be ignored.
     */
    public void setTableIgnoreBuiltin(boolean tableIgnoreBuiltin) {
        this.tableIgnoreBuiltin = tableIgnoreBuiltin;
    }

    public boolean isTableIgnoreBuiltin() {
        return tableIgnoreBuiltin;
    }

    /**
     * The maximum number of records that should be loaded into memory while
     * performing a snapshot
     */
    public void setSnapshotFetchSize(int snapshotFetchSize) {
        this.snapshotFetchSize = snapshotFetchSize;
    }

    public int getSnapshotFetchSize() {
        return snapshotFetchSize;
    }

    /**
     * The path to the file that will be used to record the database history
     */
    public void setDatabaseHistoryFileFilename(
            String databaseHistoryFileFilename) {
        this.databaseHistoryFileFilename = databaseHistoryFileFilename;
    }

    public String getDatabaseHistoryFileFilename() {
        return databaseHistoryFileFilename;
    }

    /**
     * Name of the database user to be used when connecting to the database.
     */
    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    /**
     * The name of the database from which the connector should capture changes
     */
    public void setDatabaseDbname(String databaseDbname) {
        this.databaseDbname = databaseDbname;
    }

    public String getDatabaseDbname() {
        return databaseDbname;
    }

    /**
     *  This property contains a comma-separated list of fully-qualified tables
     * (DB_NAME.TABLE_NAME) or (SCHEMA_NAME.TABLE_NAME), depending on
     * thespecific connectors. Select statements for the individual tables are
     * specified in further configuration properties, one for each table,
     * identified by the id
     * 'snapshot.select.statement.overrides.[DB_NAME].[TABLE_NAME]' or
     * 'snapshot.select.statement.overrides.[SCHEMA_NAME].[TABLE_NAME]',
     * respectively. The value of those properties is the select statement to
     * use when retrieving data from the specific table during snapshotting. A
     * possible use case for large append-only tables is setting a specific
     * point where to start (resume) snapshotting, in case a previous
     * snapshotting was interrupted.
     */
    public void setSnapshotSelectStatementOverrides(
            String snapshotSelectStatementOverrides) {
        this.snapshotSelectStatementOverrides = snapshotSelectStatementOverrides;
    }

    public String getSnapshotSelectStatementOverrides() {
        return snapshotSelectStatementOverrides;
    }

    /**
     * A list of host/port pairs that the connector will use for establishing
     * the initial connection to the Kafka cluster for retrieving database
     * schema history previously stored by the connector. This should point to
     * the same Kafka cluster used by the Kafka Connect process.
     */
    public void setDatabaseHistoryKafkaBootstrapServers(
            String databaseHistoryKafkaBootstrapServers) {
        this.databaseHistoryKafkaBootstrapServers = databaseHistoryKafkaBootstrapServers;
    }

    public String getDatabaseHistoryKafkaBootstrapServers() {
        return databaseHistoryKafkaBootstrapServers;
    }

    /**
     * Time, date, and timestamps can be represented with different kinds of
     * precisions, including:'adaptive' (the default) bases the precision of
     * time, date, and timestamp values on the database column's precision;
     * 'adaptive_time_microseconds' like 'adaptive' mode, but TIME fields always
     * use microseconds precision;'connect' always represents time, date, and
     * timestamp values using Kafka Connect's built-in representations for Time,
     * Date, and Timestamp, which uses millisecond precision regardless of the
     * database columns' precision .
     */
    public void setTimePrecisionMode(String timePrecisionMode) {
        this.timePrecisionMode = timePrecisionMode;
    }

    public String getTimePrecisionMode() {
        return timePrecisionMode;
    }

    /**
     * Unique name that identifies the database server and all recorded offsets,
     * and that is used as a prefix for all schemas and topics. Each distinct
     * installation should have a separate namespace and be monitored by at most
     * one Debezium connector.
     */
    public void setDatabaseServerName(String databaseServerName) {
        this.databaseServerName = databaseServerName;
    }

    public String getDatabaseServerName() {
        return databaseServerName;
    }

    /**
     * Length of an interval in milli-seconds in in which the connector
     * periodically sends heartbeat messages to a heartbeat topic. Use 0 to
     * disable heartbeat messages. Disabled by default.
     */
    public void setHeartbeatIntervalMs(int heartbeatIntervalMs) {
        this.heartbeatIntervalMs = heartbeatIntervalMs;
    }

    public int getHeartbeatIntervalMs() {
        return heartbeatIntervalMs;
    }

    /**
     * A version of the format of the publicly visible source part in the
     * message
     */
    public void setSourceStructVersion(String sourceStructVersion) {
        this.sourceStructVersion = sourceStructVersion;
    }

    public String getSourceStructVersion() {
        return sourceStructVersion;
    }

    /**
     * Specify how failures during processing of events (i.e. when encountering
     * a corrupted event) should be handled, including:'fail' (the default) an
     * exception indicating the problematic event and its position is raised,
     * causing the connector to be stopped; 'warn' the problematic event and its
     * position will be logged and the event will be skipped;'ignore' the
     * problematic event will be skipped.
     */
    public void setEventProcessingFailureHandlingMode(
            String eventProcessingFailureHandlingMode) {
        this.eventProcessingFailureHandlingMode = eventProcessingFailureHandlingMode;
    }

    public String getEventProcessingFailureHandlingMode() {
        return eventProcessingFailureHandlingMode;
    }

    /**
     * Port of the database server.
     */
    public void setDatabasePort(int databasePort) {
        this.databasePort = databasePort;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    /**
     * Regular expressions matching columns to exclude from change events
     */
    public void setColumnExcludeList(String columnExcludeList) {
        this.columnExcludeList = columnExcludeList;
    }

    public String getColumnExcludeList() {
        return columnExcludeList;
    }

    /**
     * A comma-separated list of regular expressions that match the
     * fully-qualified names of tables to be excluded from monitoring
     */
    public void setTableExcludeList(String tableExcludeList) {
        this.tableExcludeList = tableExcludeList;
    }

    public String getTableExcludeList() {
        return tableExcludeList;
    }

    /**
     * Resolvable hostname or IP address of the database server.
     */
    public void setDatabaseHostname(String databaseHostname) {
        this.databaseHostname = databaseHostname;
    }

    public String getDatabaseHostname() {
        return databaseHostname;
    }

    /**
     * Password of the database user to be used when connecting to the database.
     */
    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    /**
     * The tables for which changes are to be captured
     */
    public void setTableIncludeList(String tableIncludeList) {
        this.tableIncludeList = tableIncludeList;
    }

    public String getTableIncludeList() {
        return tableIncludeList;
    }

    /**
     * Maximum size of each batch of source records. Defaults to 2048.
     */
    public void setMaxBatchSize(int maxBatchSize) {
        this.maxBatchSize = maxBatchSize;
    }

    public int getMaxBatchSize() {
        return maxBatchSize;
    }

    /**
     * The criteria for running a snapshot upon startup of the connector.
     * Options include: 'initial' (the default) to specify the connector should
     * run a snapshot only when no offsets are available for the logical server
     * name; 'schema_only' to specify the connector should run a snapshot of the
     * schema when no offsets are available for the logical server name. 
     */
    public void setSnapshotMode(String snapshotMode) {
        this.snapshotMode = snapshotMode;
    }

    public String getSnapshotMode() {
        return snapshotMode;
    }

    /**
     * The name of the DatabaseHistory class that should be used to store and
     * recover database schema changes. The configuration properties for the
     * history are prefixed with the 'database.history.' string.
     */
    public void setDatabaseHistory(String databaseHistory) {
        this.databaseHistory = databaseHistory;
    }

    public String getDatabaseHistory() {
        return databaseHistory;
    }

    @Override
    protected Configuration createConnectorConfiguration() {
        final Configuration.Builder configBuilder = Configuration.create();
        
        addPropertyIfNotNull(configBuilder, "max.queue.size", maxQueueSize);
        addPropertyIfNotNull(configBuilder, "database.history.kafka.topic", databaseHistoryKafkaTopic);
        addPropertyIfNotNull(configBuilder, "column.blacklist", columnBlacklist);
        addPropertyIfNotNull(configBuilder, "snapshot.delay.ms", snapshotDelayMs);
        addPropertyIfNotNull(configBuilder, "database.history.kafka.recovery.attempts", databaseHistoryKafkaRecoveryAttempts);
        addPropertyIfNotNull(configBuilder, "table.blacklist", tableBlacklist);
        addPropertyIfNotNull(configBuilder, "table.whitelist", tableWhitelist);
        addPropertyIfNotNull(configBuilder, "decimal.handling.mode", decimalHandlingMode);
        addPropertyIfNotNull(configBuilder, "database.history.kafka.recovery.poll.interval.ms", databaseHistoryKafkaRecoveryPollIntervalMs);
        addPropertyIfNotNull(configBuilder, "poll.interval.ms", pollIntervalMs);
        addPropertyIfNotNull(configBuilder, "heartbeat.topics.prefix", heartbeatTopicsPrefix);
        addPropertyIfNotNull(configBuilder, "table.ignore.builtin", tableIgnoreBuiltin);
        addPropertyIfNotNull(configBuilder, "snapshot.fetch.size", snapshotFetchSize);
        addPropertyIfNotNull(configBuilder, "database.history.file.filename", databaseHistoryFileFilename);
        addPropertyIfNotNull(configBuilder, "database.user", databaseUser);
        addPropertyIfNotNull(configBuilder, "database.dbname", databaseDbname);
        addPropertyIfNotNull(configBuilder, "snapshot.select.statement.overrides", snapshotSelectStatementOverrides);
        addPropertyIfNotNull(configBuilder, "database.history.kafka.bootstrap.servers", databaseHistoryKafkaBootstrapServers);
        addPropertyIfNotNull(configBuilder, "time.precision.mode", timePrecisionMode);
        addPropertyIfNotNull(configBuilder, "database.server.name", databaseServerName);
        addPropertyIfNotNull(configBuilder, "heartbeat.interval.ms", heartbeatIntervalMs);
        addPropertyIfNotNull(configBuilder, "source.struct.version", sourceStructVersion);
        addPropertyIfNotNull(configBuilder, "event.processing.failure.handling.mode", eventProcessingFailureHandlingMode);
        addPropertyIfNotNull(configBuilder, "database.port", databasePort);
        addPropertyIfNotNull(configBuilder, "column.exclude.list", columnExcludeList);
        addPropertyIfNotNull(configBuilder, "table.exclude.list", tableExcludeList);
        addPropertyIfNotNull(configBuilder, "database.hostname", databaseHostname);
        addPropertyIfNotNull(configBuilder, "database.password", databasePassword);
        addPropertyIfNotNull(configBuilder, "table.include.list", tableIncludeList);
        addPropertyIfNotNull(configBuilder, "max.batch.size", maxBatchSize);
        addPropertyIfNotNull(configBuilder, "snapshot.mode", snapshotMode);
        addPropertyIfNotNull(configBuilder, "database.history", databaseHistory);
        
        return configBuilder.build();
    }

    @Override
    protected Class configureConnectorClass() {
        return Db2Connector.class;
    }

    @Override
    protected ConfigurationValidation validateConnectorConfiguration() {
        if (isFieldValueNotSet(databaseServerName)) {
        	return ConfigurationValidation.notValid("Required field 'databaseServerName' must be set.");
        }
        if (isFieldValueNotSet(databasePassword)) {
        	return ConfigurationValidation.notValid("Required field 'databasePassword' must be set.");
        }
        return ConfigurationValidation.valid();
    }

    @Override
    public String getConnectorDatabaseType() {
        return "db2";
    }
}