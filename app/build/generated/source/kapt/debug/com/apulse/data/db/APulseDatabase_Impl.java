package com.apulse.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class APulseDatabase_Impl extends APulseDatabase {
  private volatile SessionDao _sessionDao;

  private volatile NetworkRequestDao _networkRequestDao;

  private volatile RequestHeadersDao _requestHeadersDao;

  private volatile ResponseHeadersDao _responseHeadersDao;

  private volatile RequestBodyDao _requestBodyDao;

  private volatile ResponseBodyDao _responseBodyDao;

  private volatile AppMetadataDao _appMetadataDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `sessions` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, `totalRequests` INTEGER NOT NULL, `totalSize` INTEGER NOT NULL, `tags` TEXT NOT NULL, `metadata` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `network_requests` (`id` TEXT NOT NULL, `sessionId` TEXT NOT NULL, `url` TEXT NOT NULL, `method` TEXT NOT NULL, `protocol` TEXT, `host` TEXT NOT NULL, `path` TEXT NOT NULL, `query` TEXT, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `duration` INTEGER, `statusCode` INTEGER, `statusMessage` TEXT, `requestSize` INTEGER NOT NULL, `responseSize` INTEGER NOT NULL, `mimeType` TEXT, `isBookmarked` INTEGER NOT NULL, `tags` TEXT NOT NULL, `notes` TEXT, `error` TEXT, PRIMARY KEY(`id`), FOREIGN KEY(`sessionId`) REFERENCES `sessions`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_network_requests_sessionId` ON `network_requests` (`sessionId`)");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_network_requests_url` ON `network_requests` (`url`)");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_network_requests_method` ON `network_requests` (`method`)");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_network_requests_statusCode` ON `network_requests` (`statusCode`)");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_network_requests_startTime` ON `network_requests` (`startTime`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `request_headers` (`id` TEXT NOT NULL, `requestId` TEXT NOT NULL, `headers` TEXT NOT NULL, `rawHeaders` TEXT, PRIMARY KEY(`id`), FOREIGN KEY(`requestId`) REFERENCES `network_requests`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_request_headers_requestId` ON `request_headers` (`requestId`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `response_headers` (`id` TEXT NOT NULL, `requestId` TEXT NOT NULL, `headers` TEXT NOT NULL, `rawHeaders` TEXT, PRIMARY KEY(`id`), FOREIGN KEY(`requestId`) REFERENCES `network_requests`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_response_headers_requestId` ON `response_headers` (`requestId`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `request_bodies` (`id` TEXT NOT NULL, `requestId` TEXT NOT NULL, `body` BLOB, `bodyText` TEXT, `contentType` TEXT, `contentEncoding` TEXT, `size` INTEGER NOT NULL, `isRedacted` INTEGER NOT NULL, `filePath` TEXT, PRIMARY KEY(`id`), FOREIGN KEY(`requestId`) REFERENCES `network_requests`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_request_bodies_requestId` ON `request_bodies` (`requestId`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `response_bodies` (`id` TEXT NOT NULL, `requestId` TEXT NOT NULL, `body` BLOB, `bodyText` TEXT, `contentType` TEXT, `contentEncoding` TEXT, `size` INTEGER NOT NULL, `isRedacted` INTEGER NOT NULL, `filePath` TEXT, `isImage` INTEGER NOT NULL, `isJson` INTEGER NOT NULL, `isXml` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`requestId`) REFERENCES `network_requests`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_response_bodies_requestId` ON `response_bodies` (`requestId`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `app_metadata` (`id` TEXT NOT NULL, `requestId` TEXT NOT NULL, `screenName` TEXT, `activityName` TEXT, `fragmentName` TEXT, `userId` TEXT, `userAgent` TEXT, `buildVersion` TEXT, `buildType` TEXT, `deviceInfo` TEXT, `osVersion` TEXT, `appVersion` TEXT, `threadName` TEXT, `customData` TEXT NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`requestId`) REFERENCES `network_requests`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_app_metadata_requestId` ON `app_metadata` (`requestId`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd8eb1143d63f379538135f4a9829b882')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `sessions`");
        _db.execSQL("DROP TABLE IF EXISTS `network_requests`");
        _db.execSQL("DROP TABLE IF EXISTS `request_headers`");
        _db.execSQL("DROP TABLE IF EXISTS `response_headers`");
        _db.execSQL("DROP TABLE IF EXISTS `request_bodies`");
        _db.execSQL("DROP TABLE IF EXISTS `response_bodies`");
        _db.execSQL("DROP TABLE IF EXISTS `app_metadata`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsSessions = new HashMap<String, TableInfo.Column>(10);
        _columnsSessions.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("totalRequests", new TableInfo.Column("totalRequests", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("totalSize", new TableInfo.Column("totalSize", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("tags", new TableInfo.Column("tags", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessions.put("metadata", new TableInfo.Column("metadata", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSessions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSessions = new TableInfo("sessions", _columnsSessions, _foreignKeysSessions, _indicesSessions);
        final TableInfo _existingSessions = TableInfo.read(_db, "sessions");
        if (! _infoSessions.equals(_existingSessions)) {
          return new RoomOpenHelper.ValidationResult(false, "sessions(com.apulse.data.model.Session).\n"
                  + " Expected:\n" + _infoSessions + "\n"
                  + " Found:\n" + _existingSessions);
        }
        final HashMap<String, TableInfo.Column> _columnsNetworkRequests = new HashMap<String, TableInfo.Column>(20);
        _columnsNetworkRequests.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("sessionId", new TableInfo.Column("sessionId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("url", new TableInfo.Column("url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("method", new TableInfo.Column("method", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("protocol", new TableInfo.Column("protocol", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("host", new TableInfo.Column("host", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("path", new TableInfo.Column("path", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("query", new TableInfo.Column("query", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("endTime", new TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("duration", new TableInfo.Column("duration", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("statusCode", new TableInfo.Column("statusCode", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("statusMessage", new TableInfo.Column("statusMessage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("requestSize", new TableInfo.Column("requestSize", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("responseSize", new TableInfo.Column("responseSize", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("mimeType", new TableInfo.Column("mimeType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("isBookmarked", new TableInfo.Column("isBookmarked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("tags", new TableInfo.Column("tags", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetworkRequests.put("error", new TableInfo.Column("error", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNetworkRequests = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysNetworkRequests.add(new TableInfo.ForeignKey("sessions", "CASCADE", "NO ACTION",Arrays.asList("sessionId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesNetworkRequests = new HashSet<TableInfo.Index>(5);
        _indicesNetworkRequests.add(new TableInfo.Index("index_network_requests_sessionId", false, Arrays.asList("sessionId"), Arrays.asList("ASC")));
        _indicesNetworkRequests.add(new TableInfo.Index("index_network_requests_url", false, Arrays.asList("url"), Arrays.asList("ASC")));
        _indicesNetworkRequests.add(new TableInfo.Index("index_network_requests_method", false, Arrays.asList("method"), Arrays.asList("ASC")));
        _indicesNetworkRequests.add(new TableInfo.Index("index_network_requests_statusCode", false, Arrays.asList("statusCode"), Arrays.asList("ASC")));
        _indicesNetworkRequests.add(new TableInfo.Index("index_network_requests_startTime", false, Arrays.asList("startTime"), Arrays.asList("ASC")));
        final TableInfo _infoNetworkRequests = new TableInfo("network_requests", _columnsNetworkRequests, _foreignKeysNetworkRequests, _indicesNetworkRequests);
        final TableInfo _existingNetworkRequests = TableInfo.read(_db, "network_requests");
        if (! _infoNetworkRequests.equals(_existingNetworkRequests)) {
          return new RoomOpenHelper.ValidationResult(false, "network_requests(com.apulse.data.model.NetworkRequest).\n"
                  + " Expected:\n" + _infoNetworkRequests + "\n"
                  + " Found:\n" + _existingNetworkRequests);
        }
        final HashMap<String, TableInfo.Column> _columnsRequestHeaders = new HashMap<String, TableInfo.Column>(4);
        _columnsRequestHeaders.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRequestHeaders.put("requestId", new TableInfo.Column("requestId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRequestHeaders.put("headers", new TableInfo.Column("headers", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRequestHeaders.put("rawHeaders", new TableInfo.Column("rawHeaders", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRequestHeaders = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysRequestHeaders.add(new TableInfo.ForeignKey("network_requests", "CASCADE", "NO ACTION",Arrays.asList("requestId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesRequestHeaders = new HashSet<TableInfo.Index>(1);
        _indicesRequestHeaders.add(new TableInfo.Index("index_request_headers_requestId", false, Arrays.asList("requestId"), Arrays.asList("ASC")));
        final TableInfo _infoRequestHeaders = new TableInfo("request_headers", _columnsRequestHeaders, _foreignKeysRequestHeaders, _indicesRequestHeaders);
        final TableInfo _existingRequestHeaders = TableInfo.read(_db, "request_headers");
        if (! _infoRequestHeaders.equals(_existingRequestHeaders)) {
          return new RoomOpenHelper.ValidationResult(false, "request_headers(com.apulse.data.model.RequestHeaders).\n"
                  + " Expected:\n" + _infoRequestHeaders + "\n"
                  + " Found:\n" + _existingRequestHeaders);
        }
        final HashMap<String, TableInfo.Column> _columnsResponseHeaders = new HashMap<String, TableInfo.Column>(4);
        _columnsResponseHeaders.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseHeaders.put("requestId", new TableInfo.Column("requestId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseHeaders.put("headers", new TableInfo.Column("headers", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseHeaders.put("rawHeaders", new TableInfo.Column("rawHeaders", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysResponseHeaders = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysResponseHeaders.add(new TableInfo.ForeignKey("network_requests", "CASCADE", "NO ACTION",Arrays.asList("requestId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesResponseHeaders = new HashSet<TableInfo.Index>(1);
        _indicesResponseHeaders.add(new TableInfo.Index("index_response_headers_requestId", false, Arrays.asList("requestId"), Arrays.asList("ASC")));
        final TableInfo _infoResponseHeaders = new TableInfo("response_headers", _columnsResponseHeaders, _foreignKeysResponseHeaders, _indicesResponseHeaders);
        final TableInfo _existingResponseHeaders = TableInfo.read(_db, "response_headers");
        if (! _infoResponseHeaders.equals(_existingResponseHeaders)) {
          return new RoomOpenHelper.ValidationResult(false, "response_headers(com.apulse.data.model.ResponseHeaders).\n"
                  + " Expected:\n" + _infoResponseHeaders + "\n"
                  + " Found:\n" + _existingResponseHeaders);
        }
        final HashMap<String, TableInfo.Column> _columnsRequestBodies = new HashMap<String, TableInfo.Column>(9);
        _columnsRequestBodies.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRequestBodies.put("requestId", new TableInfo.Column("requestId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRequestBodies.put("body", new TableInfo.Column("body", "BLOB", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRequestBodies.put("bodyText", new TableInfo.Column("bodyText", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRequestBodies.put("contentType", new TableInfo.Column("contentType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRequestBodies.put("contentEncoding", new TableInfo.Column("contentEncoding", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRequestBodies.put("size", new TableInfo.Column("size", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRequestBodies.put("isRedacted", new TableInfo.Column("isRedacted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRequestBodies.put("filePath", new TableInfo.Column("filePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRequestBodies = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysRequestBodies.add(new TableInfo.ForeignKey("network_requests", "CASCADE", "NO ACTION",Arrays.asList("requestId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesRequestBodies = new HashSet<TableInfo.Index>(1);
        _indicesRequestBodies.add(new TableInfo.Index("index_request_bodies_requestId", false, Arrays.asList("requestId"), Arrays.asList("ASC")));
        final TableInfo _infoRequestBodies = new TableInfo("request_bodies", _columnsRequestBodies, _foreignKeysRequestBodies, _indicesRequestBodies);
        final TableInfo _existingRequestBodies = TableInfo.read(_db, "request_bodies");
        if (! _infoRequestBodies.equals(_existingRequestBodies)) {
          return new RoomOpenHelper.ValidationResult(false, "request_bodies(com.apulse.data.model.RequestBody).\n"
                  + " Expected:\n" + _infoRequestBodies + "\n"
                  + " Found:\n" + _existingRequestBodies);
        }
        final HashMap<String, TableInfo.Column> _columnsResponseBodies = new HashMap<String, TableInfo.Column>(12);
        _columnsResponseBodies.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseBodies.put("requestId", new TableInfo.Column("requestId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseBodies.put("body", new TableInfo.Column("body", "BLOB", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseBodies.put("bodyText", new TableInfo.Column("bodyText", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseBodies.put("contentType", new TableInfo.Column("contentType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseBodies.put("contentEncoding", new TableInfo.Column("contentEncoding", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseBodies.put("size", new TableInfo.Column("size", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseBodies.put("isRedacted", new TableInfo.Column("isRedacted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseBodies.put("filePath", new TableInfo.Column("filePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseBodies.put("isImage", new TableInfo.Column("isImage", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseBodies.put("isJson", new TableInfo.Column("isJson", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResponseBodies.put("isXml", new TableInfo.Column("isXml", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysResponseBodies = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysResponseBodies.add(new TableInfo.ForeignKey("network_requests", "CASCADE", "NO ACTION",Arrays.asList("requestId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesResponseBodies = new HashSet<TableInfo.Index>(1);
        _indicesResponseBodies.add(new TableInfo.Index("index_response_bodies_requestId", false, Arrays.asList("requestId"), Arrays.asList("ASC")));
        final TableInfo _infoResponseBodies = new TableInfo("response_bodies", _columnsResponseBodies, _foreignKeysResponseBodies, _indicesResponseBodies);
        final TableInfo _existingResponseBodies = TableInfo.read(_db, "response_bodies");
        if (! _infoResponseBodies.equals(_existingResponseBodies)) {
          return new RoomOpenHelper.ValidationResult(false, "response_bodies(com.apulse.data.model.ResponseBody).\n"
                  + " Expected:\n" + _infoResponseBodies + "\n"
                  + " Found:\n" + _existingResponseBodies);
        }
        final HashMap<String, TableInfo.Column> _columnsAppMetadata = new HashMap<String, TableInfo.Column>(14);
        _columnsAppMetadata.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("requestId", new TableInfo.Column("requestId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("screenName", new TableInfo.Column("screenName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("activityName", new TableInfo.Column("activityName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("fragmentName", new TableInfo.Column("fragmentName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("userId", new TableInfo.Column("userId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("userAgent", new TableInfo.Column("userAgent", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("buildVersion", new TableInfo.Column("buildVersion", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("buildType", new TableInfo.Column("buildType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("deviceInfo", new TableInfo.Column("deviceInfo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("osVersion", new TableInfo.Column("osVersion", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("appVersion", new TableInfo.Column("appVersion", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("threadName", new TableInfo.Column("threadName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppMetadata.put("customData", new TableInfo.Column("customData", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAppMetadata = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysAppMetadata.add(new TableInfo.ForeignKey("network_requests", "CASCADE", "NO ACTION",Arrays.asList("requestId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesAppMetadata = new HashSet<TableInfo.Index>(1);
        _indicesAppMetadata.add(new TableInfo.Index("index_app_metadata_requestId", false, Arrays.asList("requestId"), Arrays.asList("ASC")));
        final TableInfo _infoAppMetadata = new TableInfo("app_metadata", _columnsAppMetadata, _foreignKeysAppMetadata, _indicesAppMetadata);
        final TableInfo _existingAppMetadata = TableInfo.read(_db, "app_metadata");
        if (! _infoAppMetadata.equals(_existingAppMetadata)) {
          return new RoomOpenHelper.ValidationResult(false, "app_metadata(com.apulse.data.model.AppMetadata).\n"
                  + " Expected:\n" + _infoAppMetadata + "\n"
                  + " Found:\n" + _existingAppMetadata);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d8eb1143d63f379538135f4a9829b882", "4f2e364931d22d2631cda10c29d84af2");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "sessions","network_requests","request_headers","response_headers","request_bodies","response_bodies","app_metadata");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `sessions`");
      _db.execSQL("DELETE FROM `network_requests`");
      _db.execSQL("DELETE FROM `request_headers`");
      _db.execSQL("DELETE FROM `response_headers`");
      _db.execSQL("DELETE FROM `request_bodies`");
      _db.execSQL("DELETE FROM `response_bodies`");
      _db.execSQL("DELETE FROM `app_metadata`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SessionDao.class, SessionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NetworkRequestDao.class, NetworkRequestDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RequestHeadersDao.class, RequestHeadersDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ResponseHeadersDao.class, ResponseHeadersDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RequestBodyDao.class, RequestBodyDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ResponseBodyDao.class, ResponseBodyDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AppMetadataDao.class, AppMetadataDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public SessionDao sessionDao() {
    if (_sessionDao != null) {
      return _sessionDao;
    } else {
      synchronized(this) {
        if(_sessionDao == null) {
          _sessionDao = new SessionDao_Impl(this);
        }
        return _sessionDao;
      }
    }
  }

  @Override
  public NetworkRequestDao networkRequestDao() {
    if (_networkRequestDao != null) {
      return _networkRequestDao;
    } else {
      synchronized(this) {
        if(_networkRequestDao == null) {
          _networkRequestDao = new NetworkRequestDao_Impl(this);
        }
        return _networkRequestDao;
      }
    }
  }

  @Override
  public RequestHeadersDao requestHeadersDao() {
    if (_requestHeadersDao != null) {
      return _requestHeadersDao;
    } else {
      synchronized(this) {
        if(_requestHeadersDao == null) {
          _requestHeadersDao = new RequestHeadersDao_Impl(this);
        }
        return _requestHeadersDao;
      }
    }
  }

  @Override
  public ResponseHeadersDao responseHeadersDao() {
    if (_responseHeadersDao != null) {
      return _responseHeadersDao;
    } else {
      synchronized(this) {
        if(_responseHeadersDao == null) {
          _responseHeadersDao = new ResponseHeadersDao_Impl(this);
        }
        return _responseHeadersDao;
      }
    }
  }

  @Override
  public RequestBodyDao requestBodyDao() {
    if (_requestBodyDao != null) {
      return _requestBodyDao;
    } else {
      synchronized(this) {
        if(_requestBodyDao == null) {
          _requestBodyDao = new RequestBodyDao_Impl(this);
        }
        return _requestBodyDao;
      }
    }
  }

  @Override
  public ResponseBodyDao responseBodyDao() {
    if (_responseBodyDao != null) {
      return _responseBodyDao;
    } else {
      synchronized(this) {
        if(_responseBodyDao == null) {
          _responseBodyDao = new ResponseBodyDao_Impl(this);
        }
        return _responseBodyDao;
      }
    }
  }

  @Override
  public AppMetadataDao appMetadataDao() {
    if (_appMetadataDao != null) {
      return _appMetadataDao;
    } else {
      synchronized(this) {
        if(_appMetadataDao == null) {
          _appMetadataDao = new AppMetadataDao_Impl(this);
        }
        return _appMetadataDao;
      }
    }
  }
}
