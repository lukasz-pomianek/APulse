package com.apulse.data.db;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.apulse.data.model.NetworkRequest;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;
import kotlinx.datetime.Instant;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class NetworkRequestDao_Impl implements NetworkRequestDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NetworkRequest> __insertionAdapterOfNetworkRequest;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<NetworkRequest> __deletionAdapterOfNetworkRequest;

  private final EntityDeletionOrUpdateAdapter<NetworkRequest> __updateAdapterOfNetworkRequest;

  public NetworkRequestDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNetworkRequest = new EntityInsertionAdapter<NetworkRequest>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `network_requests` (`id`,`sessionId`,`url`,`method`,`protocol`,`host`,`path`,`query`,`startTime`,`endTime`,`duration`,`statusCode`,`statusMessage`,`requestSize`,`responseSize`,`mimeType`,`isBookmarked`,`tags`,`notes`,`error`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NetworkRequest value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getSessionId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getSessionId());
        }
        if (value.getUrl() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUrl());
        }
        if (value.getMethod() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMethod());
        }
        if (value.getProtocol() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getProtocol());
        }
        if (value.getHost() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getHost());
        }
        if (value.getPath() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPath());
        }
        if (value.getQuery() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getQuery());
        }
        final Long _tmp = __converters.fromInstant(value.getStartTime());
        if (_tmp == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindLong(9, _tmp);
        }
        final Long _tmp_1 = __converters.fromInstant(value.getEndTime());
        if (_tmp_1 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindLong(10, _tmp_1);
        }
        if (value.getDuration() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, value.getDuration());
        }
        if (value.getStatusCode() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindLong(12, value.getStatusCode());
        }
        if (value.getStatusMessage() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getStatusMessage());
        }
        stmt.bindLong(14, value.getRequestSize());
        stmt.bindLong(15, value.getResponseSize());
        if (value.getMimeType() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getMimeType());
        }
        final int _tmp_2 = value.isBookmarked() ? 1 : 0;
        stmt.bindLong(17, _tmp_2);
        final String _tmp_3 = __converters.fromStringList(value.getTags());
        if (_tmp_3 == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, _tmp_3);
        }
        if (value.getNotes() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getNotes());
        }
        if (value.getError() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getError());
        }
      }
    };
    this.__deletionAdapterOfNetworkRequest = new EntityDeletionOrUpdateAdapter<NetworkRequest>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `network_requests` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NetworkRequest value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfNetworkRequest = new EntityDeletionOrUpdateAdapter<NetworkRequest>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `network_requests` SET `id` = ?,`sessionId` = ?,`url` = ?,`method` = ?,`protocol` = ?,`host` = ?,`path` = ?,`query` = ?,`startTime` = ?,`endTime` = ?,`duration` = ?,`statusCode` = ?,`statusMessage` = ?,`requestSize` = ?,`responseSize` = ?,`mimeType` = ?,`isBookmarked` = ?,`tags` = ?,`notes` = ?,`error` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NetworkRequest value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getSessionId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getSessionId());
        }
        if (value.getUrl() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUrl());
        }
        if (value.getMethod() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMethod());
        }
        if (value.getProtocol() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getProtocol());
        }
        if (value.getHost() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getHost());
        }
        if (value.getPath() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPath());
        }
        if (value.getQuery() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getQuery());
        }
        final Long _tmp = __converters.fromInstant(value.getStartTime());
        if (_tmp == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindLong(9, _tmp);
        }
        final Long _tmp_1 = __converters.fromInstant(value.getEndTime());
        if (_tmp_1 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindLong(10, _tmp_1);
        }
        if (value.getDuration() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindLong(11, value.getDuration());
        }
        if (value.getStatusCode() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindLong(12, value.getStatusCode());
        }
        if (value.getStatusMessage() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getStatusMessage());
        }
        stmt.bindLong(14, value.getRequestSize());
        stmt.bindLong(15, value.getResponseSize());
        if (value.getMimeType() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getMimeType());
        }
        final int _tmp_2 = value.isBookmarked() ? 1 : 0;
        stmt.bindLong(17, _tmp_2);
        final String _tmp_3 = __converters.fromStringList(value.getTags());
        if (_tmp_3 == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, _tmp_3);
        }
        if (value.getNotes() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getNotes());
        }
        if (value.getError() == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, value.getError());
        }
        if (value.getId() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getId());
        }
      }
    };
  }

  @Override
  public Object insertRequest(final NetworkRequest request,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object deleteRequest(final NetworkRequest request,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object updateRequest(final NetworkRequest request,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Flow<List<NetworkRequest>> getAllRequests() {
    final String _sql = "SELECT * FROM network_requests ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"network_requests"}, new Callable<List<NetworkRequest>>() {
      @Override
      public List<NetworkRequest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfHost = CursorUtil.getColumnIndexOrThrow(_cursor, "host");
          final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
          final int _cursorIndexOfQuery = CursorUtil.getColumnIndexOrThrow(_cursor, "query");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfStatusCode = CursorUtil.getColumnIndexOrThrow(_cursor, "statusCode");
          final int _cursorIndexOfStatusMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "statusMessage");
          final int _cursorIndexOfRequestSize = CursorUtil.getColumnIndexOrThrow(_cursor, "requestSize");
          final int _cursorIndexOfResponseSize = CursorUtil.getColumnIndexOrThrow(_cursor, "responseSize");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final List<NetworkRequest> _result = new ArrayList<NetworkRequest>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NetworkRequest _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpHost;
            if (_cursor.isNull(_cursorIndexOfHost)) {
              _tmpHost = null;
            } else {
              _tmpHost = _cursor.getString(_cursorIndexOfHost);
            }
            final String _tmpPath;
            if (_cursor.isNull(_cursorIndexOfPath)) {
              _tmpPath = null;
            } else {
              _tmpPath = _cursor.getString(_cursorIndexOfPath);
            }
            final String _tmpQuery;
            if (_cursor.isNull(_cursorIndexOfQuery)) {
              _tmpQuery = null;
            } else {
              _tmpQuery = _cursor.getString(_cursorIndexOfQuery);
            }
            final Instant _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toInstant(_tmp);
            final Instant _tmpEndTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toInstant(_tmp_1);
            final Long _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            }
            final Integer _tmpStatusCode;
            if (_cursor.isNull(_cursorIndexOfStatusCode)) {
              _tmpStatusCode = null;
            } else {
              _tmpStatusCode = _cursor.getInt(_cursorIndexOfStatusCode);
            }
            final String _tmpStatusMessage;
            if (_cursor.isNull(_cursorIndexOfStatusMessage)) {
              _tmpStatusMessage = null;
            } else {
              _tmpStatusMessage = _cursor.getString(_cursorIndexOfStatusMessage);
            }
            final long _tmpRequestSize;
            _tmpRequestSize = _cursor.getLong(_cursorIndexOfRequestSize);
            final long _tmpResponseSize;
            _tmpResponseSize = _cursor.getLong(_cursorIndexOfResponseSize);
            final String _tmpMimeType;
            if (_cursor.isNull(_cursorIndexOfMimeType)) {
              _tmpMimeType = null;
            } else {
              _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_2 != 0;
            final List<String> _tmpTags;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            }
            _tmpTags = __converters.toStringList(_tmp_3);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            _item = new NetworkRequest(_tmpId,_tmpSessionId,_tmpUrl,_tmpMethod,_tmpProtocol,_tmpHost,_tmpPath,_tmpQuery,_tmpStartTime,_tmpEndTime,_tmpDuration,_tmpStatusCode,_tmpStatusMessage,_tmpRequestSize,_tmpResponseSize,_tmpMimeType,_tmpIsBookmarked,_tmpTags,_tmpNotes,_tmpError);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<NetworkRequest>> getRequestsForSession(final String sessionId) {
    final String _sql = "SELECT * FROM network_requests WHERE sessionId = ? ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (sessionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sessionId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"network_requests"}, new Callable<List<NetworkRequest>>() {
      @Override
      public List<NetworkRequest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfHost = CursorUtil.getColumnIndexOrThrow(_cursor, "host");
          final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
          final int _cursorIndexOfQuery = CursorUtil.getColumnIndexOrThrow(_cursor, "query");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfStatusCode = CursorUtil.getColumnIndexOrThrow(_cursor, "statusCode");
          final int _cursorIndexOfStatusMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "statusMessage");
          final int _cursorIndexOfRequestSize = CursorUtil.getColumnIndexOrThrow(_cursor, "requestSize");
          final int _cursorIndexOfResponseSize = CursorUtil.getColumnIndexOrThrow(_cursor, "responseSize");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final List<NetworkRequest> _result = new ArrayList<NetworkRequest>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NetworkRequest _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpHost;
            if (_cursor.isNull(_cursorIndexOfHost)) {
              _tmpHost = null;
            } else {
              _tmpHost = _cursor.getString(_cursorIndexOfHost);
            }
            final String _tmpPath;
            if (_cursor.isNull(_cursorIndexOfPath)) {
              _tmpPath = null;
            } else {
              _tmpPath = _cursor.getString(_cursorIndexOfPath);
            }
            final String _tmpQuery;
            if (_cursor.isNull(_cursorIndexOfQuery)) {
              _tmpQuery = null;
            } else {
              _tmpQuery = _cursor.getString(_cursorIndexOfQuery);
            }
            final Instant _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toInstant(_tmp);
            final Instant _tmpEndTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toInstant(_tmp_1);
            final Long _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            }
            final Integer _tmpStatusCode;
            if (_cursor.isNull(_cursorIndexOfStatusCode)) {
              _tmpStatusCode = null;
            } else {
              _tmpStatusCode = _cursor.getInt(_cursorIndexOfStatusCode);
            }
            final String _tmpStatusMessage;
            if (_cursor.isNull(_cursorIndexOfStatusMessage)) {
              _tmpStatusMessage = null;
            } else {
              _tmpStatusMessage = _cursor.getString(_cursorIndexOfStatusMessage);
            }
            final long _tmpRequestSize;
            _tmpRequestSize = _cursor.getLong(_cursorIndexOfRequestSize);
            final long _tmpResponseSize;
            _tmpResponseSize = _cursor.getLong(_cursorIndexOfResponseSize);
            final String _tmpMimeType;
            if (_cursor.isNull(_cursorIndexOfMimeType)) {
              _tmpMimeType = null;
            } else {
              _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_2 != 0;
            final List<String> _tmpTags;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            }
            _tmpTags = __converters.toStringList(_tmp_3);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            _item = new NetworkRequest(_tmpId,_tmpSessionId,_tmpUrl,_tmpMethod,_tmpProtocol,_tmpHost,_tmpPath,_tmpQuery,_tmpStartTime,_tmpEndTime,_tmpDuration,_tmpStatusCode,_tmpStatusMessage,_tmpRequestSize,_tmpResponseSize,_tmpMimeType,_tmpIsBookmarked,_tmpTags,_tmpNotes,_tmpError);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getRequest(final String requestId,
      final Continuation<? super NetworkRequest> $completion) {
    final String _sql = "SELECT * FROM network_requests WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (requestId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, requestId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Object _result;
      if(_cursor.moveToFirst()) {
        _result = new Object();
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Flow<List<NetworkRequest>> searchRequestsInSession(final String sessionId,
      final String searchQuery) {
    final String _sql = "\n"
            + "        SELECT * FROM network_requests \n"
            + "        WHERE sessionId = ? \n"
            + "        AND (url LIKE '%' || ? || '%' OR host LIKE '%' || ? || '%')\n"
            + "        ORDER BY startTime DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (sessionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sessionId);
    }
    _argIndex = 2;
    if (searchQuery == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, searchQuery);
    }
    _argIndex = 3;
    if (searchQuery == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, searchQuery);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"network_requests"}, new Callable<List<NetworkRequest>>() {
      @Override
      public List<NetworkRequest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfHost = CursorUtil.getColumnIndexOrThrow(_cursor, "host");
          final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
          final int _cursorIndexOfQuery = CursorUtil.getColumnIndexOrThrow(_cursor, "query");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfStatusCode = CursorUtil.getColumnIndexOrThrow(_cursor, "statusCode");
          final int _cursorIndexOfStatusMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "statusMessage");
          final int _cursorIndexOfRequestSize = CursorUtil.getColumnIndexOrThrow(_cursor, "requestSize");
          final int _cursorIndexOfResponseSize = CursorUtil.getColumnIndexOrThrow(_cursor, "responseSize");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final List<NetworkRequest> _result = new ArrayList<NetworkRequest>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NetworkRequest _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpHost;
            if (_cursor.isNull(_cursorIndexOfHost)) {
              _tmpHost = null;
            } else {
              _tmpHost = _cursor.getString(_cursorIndexOfHost);
            }
            final String _tmpPath;
            if (_cursor.isNull(_cursorIndexOfPath)) {
              _tmpPath = null;
            } else {
              _tmpPath = _cursor.getString(_cursorIndexOfPath);
            }
            final String _tmpQuery;
            if (_cursor.isNull(_cursorIndexOfQuery)) {
              _tmpQuery = null;
            } else {
              _tmpQuery = _cursor.getString(_cursorIndexOfQuery);
            }
            final Instant _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toInstant(_tmp);
            final Instant _tmpEndTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toInstant(_tmp_1);
            final Long _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            }
            final Integer _tmpStatusCode;
            if (_cursor.isNull(_cursorIndexOfStatusCode)) {
              _tmpStatusCode = null;
            } else {
              _tmpStatusCode = _cursor.getInt(_cursorIndexOfStatusCode);
            }
            final String _tmpStatusMessage;
            if (_cursor.isNull(_cursorIndexOfStatusMessage)) {
              _tmpStatusMessage = null;
            } else {
              _tmpStatusMessage = _cursor.getString(_cursorIndexOfStatusMessage);
            }
            final long _tmpRequestSize;
            _tmpRequestSize = _cursor.getLong(_cursorIndexOfRequestSize);
            final long _tmpResponseSize;
            _tmpResponseSize = _cursor.getLong(_cursorIndexOfResponseSize);
            final String _tmpMimeType;
            if (_cursor.isNull(_cursorIndexOfMimeType)) {
              _tmpMimeType = null;
            } else {
              _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_2 != 0;
            final List<String> _tmpTags;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            }
            _tmpTags = __converters.toStringList(_tmp_3);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            _item = new NetworkRequest(_tmpId,_tmpSessionId,_tmpUrl,_tmpMethod,_tmpProtocol,_tmpHost,_tmpPath,_tmpQuery,_tmpStartTime,_tmpEndTime,_tmpDuration,_tmpStatusCode,_tmpStatusMessage,_tmpRequestSize,_tmpResponseSize,_tmpMimeType,_tmpIsBookmarked,_tmpTags,_tmpNotes,_tmpError);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<NetworkRequest>> getRequestsByStatusRange(final String sessionId,
      final int minStatus, final int maxStatus) {
    final String _sql = "\n"
            + "        SELECT * FROM network_requests \n"
            + "        WHERE sessionId = ? \n"
            + "        AND statusCode BETWEEN ? AND ?\n"
            + "        ORDER BY startTime DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (sessionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sessionId);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, minStatus);
    _argIndex = 3;
    _statement.bindLong(_argIndex, maxStatus);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"network_requests"}, new Callable<List<NetworkRequest>>() {
      @Override
      public List<NetworkRequest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfHost = CursorUtil.getColumnIndexOrThrow(_cursor, "host");
          final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
          final int _cursorIndexOfQuery = CursorUtil.getColumnIndexOrThrow(_cursor, "query");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfStatusCode = CursorUtil.getColumnIndexOrThrow(_cursor, "statusCode");
          final int _cursorIndexOfStatusMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "statusMessage");
          final int _cursorIndexOfRequestSize = CursorUtil.getColumnIndexOrThrow(_cursor, "requestSize");
          final int _cursorIndexOfResponseSize = CursorUtil.getColumnIndexOrThrow(_cursor, "responseSize");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final List<NetworkRequest> _result = new ArrayList<NetworkRequest>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NetworkRequest _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpHost;
            if (_cursor.isNull(_cursorIndexOfHost)) {
              _tmpHost = null;
            } else {
              _tmpHost = _cursor.getString(_cursorIndexOfHost);
            }
            final String _tmpPath;
            if (_cursor.isNull(_cursorIndexOfPath)) {
              _tmpPath = null;
            } else {
              _tmpPath = _cursor.getString(_cursorIndexOfPath);
            }
            final String _tmpQuery;
            if (_cursor.isNull(_cursorIndexOfQuery)) {
              _tmpQuery = null;
            } else {
              _tmpQuery = _cursor.getString(_cursorIndexOfQuery);
            }
            final Instant _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toInstant(_tmp);
            final Instant _tmpEndTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toInstant(_tmp_1);
            final Long _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            }
            final Integer _tmpStatusCode;
            if (_cursor.isNull(_cursorIndexOfStatusCode)) {
              _tmpStatusCode = null;
            } else {
              _tmpStatusCode = _cursor.getInt(_cursorIndexOfStatusCode);
            }
            final String _tmpStatusMessage;
            if (_cursor.isNull(_cursorIndexOfStatusMessage)) {
              _tmpStatusMessage = null;
            } else {
              _tmpStatusMessage = _cursor.getString(_cursorIndexOfStatusMessage);
            }
            final long _tmpRequestSize;
            _tmpRequestSize = _cursor.getLong(_cursorIndexOfRequestSize);
            final long _tmpResponseSize;
            _tmpResponseSize = _cursor.getLong(_cursorIndexOfResponseSize);
            final String _tmpMimeType;
            if (_cursor.isNull(_cursorIndexOfMimeType)) {
              _tmpMimeType = null;
            } else {
              _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_2 != 0;
            final List<String> _tmpTags;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            }
            _tmpTags = __converters.toStringList(_tmp_3);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            _item = new NetworkRequest(_tmpId,_tmpSessionId,_tmpUrl,_tmpMethod,_tmpProtocol,_tmpHost,_tmpPath,_tmpQuery,_tmpStartTime,_tmpEndTime,_tmpDuration,_tmpStatusCode,_tmpStatusMessage,_tmpRequestSize,_tmpResponseSize,_tmpMimeType,_tmpIsBookmarked,_tmpTags,_tmpNotes,_tmpError);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<NetworkRequest>> getRequestsByHost(final String sessionId, final String host) {
    final String _sql = "SELECT * FROM network_requests WHERE sessionId = ? AND host = ? ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (sessionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sessionId);
    }
    _argIndex = 2;
    if (host == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, host);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"network_requests"}, new Callable<List<NetworkRequest>>() {
      @Override
      public List<NetworkRequest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfHost = CursorUtil.getColumnIndexOrThrow(_cursor, "host");
          final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
          final int _cursorIndexOfQuery = CursorUtil.getColumnIndexOrThrow(_cursor, "query");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfStatusCode = CursorUtil.getColumnIndexOrThrow(_cursor, "statusCode");
          final int _cursorIndexOfStatusMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "statusMessage");
          final int _cursorIndexOfRequestSize = CursorUtil.getColumnIndexOrThrow(_cursor, "requestSize");
          final int _cursorIndexOfResponseSize = CursorUtil.getColumnIndexOrThrow(_cursor, "responseSize");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final List<NetworkRequest> _result = new ArrayList<NetworkRequest>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NetworkRequest _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpHost;
            if (_cursor.isNull(_cursorIndexOfHost)) {
              _tmpHost = null;
            } else {
              _tmpHost = _cursor.getString(_cursorIndexOfHost);
            }
            final String _tmpPath;
            if (_cursor.isNull(_cursorIndexOfPath)) {
              _tmpPath = null;
            } else {
              _tmpPath = _cursor.getString(_cursorIndexOfPath);
            }
            final String _tmpQuery;
            if (_cursor.isNull(_cursorIndexOfQuery)) {
              _tmpQuery = null;
            } else {
              _tmpQuery = _cursor.getString(_cursorIndexOfQuery);
            }
            final Instant _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toInstant(_tmp);
            final Instant _tmpEndTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toInstant(_tmp_1);
            final Long _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            }
            final Integer _tmpStatusCode;
            if (_cursor.isNull(_cursorIndexOfStatusCode)) {
              _tmpStatusCode = null;
            } else {
              _tmpStatusCode = _cursor.getInt(_cursorIndexOfStatusCode);
            }
            final String _tmpStatusMessage;
            if (_cursor.isNull(_cursorIndexOfStatusMessage)) {
              _tmpStatusMessage = null;
            } else {
              _tmpStatusMessage = _cursor.getString(_cursorIndexOfStatusMessage);
            }
            final long _tmpRequestSize;
            _tmpRequestSize = _cursor.getLong(_cursorIndexOfRequestSize);
            final long _tmpResponseSize;
            _tmpResponseSize = _cursor.getLong(_cursorIndexOfResponseSize);
            final String _tmpMimeType;
            if (_cursor.isNull(_cursorIndexOfMimeType)) {
              _tmpMimeType = null;
            } else {
              _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_2 != 0;
            final List<String> _tmpTags;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            }
            _tmpTags = __converters.toStringList(_tmp_3);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            _item = new NetworkRequest(_tmpId,_tmpSessionId,_tmpUrl,_tmpMethod,_tmpProtocol,_tmpHost,_tmpPath,_tmpQuery,_tmpStartTime,_tmpEndTime,_tmpDuration,_tmpStatusCode,_tmpStatusMessage,_tmpRequestSize,_tmpResponseSize,_tmpMimeType,_tmpIsBookmarked,_tmpTags,_tmpNotes,_tmpError);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<NetworkRequest>> getBookmarkedRequests() {
    final String _sql = "SELECT * FROM network_requests WHERE isBookmarked = 1 ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"network_requests"}, new Callable<List<NetworkRequest>>() {
      @Override
      public List<NetworkRequest> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfProtocol = CursorUtil.getColumnIndexOrThrow(_cursor, "protocol");
          final int _cursorIndexOfHost = CursorUtil.getColumnIndexOrThrow(_cursor, "host");
          final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
          final int _cursorIndexOfQuery = CursorUtil.getColumnIndexOrThrow(_cursor, "query");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfStatusCode = CursorUtil.getColumnIndexOrThrow(_cursor, "statusCode");
          final int _cursorIndexOfStatusMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "statusMessage");
          final int _cursorIndexOfRequestSize = CursorUtil.getColumnIndexOrThrow(_cursor, "requestSize");
          final int _cursorIndexOfResponseSize = CursorUtil.getColumnIndexOrThrow(_cursor, "responseSize");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfError = CursorUtil.getColumnIndexOrThrow(_cursor, "error");
          final List<NetworkRequest> _result = new ArrayList<NetworkRequest>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NetworkRequest _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final String _tmpMethod;
            if (_cursor.isNull(_cursorIndexOfMethod)) {
              _tmpMethod = null;
            } else {
              _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
            }
            final String _tmpProtocol;
            if (_cursor.isNull(_cursorIndexOfProtocol)) {
              _tmpProtocol = null;
            } else {
              _tmpProtocol = _cursor.getString(_cursorIndexOfProtocol);
            }
            final String _tmpHost;
            if (_cursor.isNull(_cursorIndexOfHost)) {
              _tmpHost = null;
            } else {
              _tmpHost = _cursor.getString(_cursorIndexOfHost);
            }
            final String _tmpPath;
            if (_cursor.isNull(_cursorIndexOfPath)) {
              _tmpPath = null;
            } else {
              _tmpPath = _cursor.getString(_cursorIndexOfPath);
            }
            final String _tmpQuery;
            if (_cursor.isNull(_cursorIndexOfQuery)) {
              _tmpQuery = null;
            } else {
              _tmpQuery = _cursor.getString(_cursorIndexOfQuery);
            }
            final Instant _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toInstant(_tmp);
            final Instant _tmpEndTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toInstant(_tmp_1);
            final Long _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            }
            final Integer _tmpStatusCode;
            if (_cursor.isNull(_cursorIndexOfStatusCode)) {
              _tmpStatusCode = null;
            } else {
              _tmpStatusCode = _cursor.getInt(_cursorIndexOfStatusCode);
            }
            final String _tmpStatusMessage;
            if (_cursor.isNull(_cursorIndexOfStatusMessage)) {
              _tmpStatusMessage = null;
            } else {
              _tmpStatusMessage = _cursor.getString(_cursorIndexOfStatusMessage);
            }
            final long _tmpRequestSize;
            _tmpRequestSize = _cursor.getLong(_cursorIndexOfRequestSize);
            final long _tmpResponseSize;
            _tmpResponseSize = _cursor.getLong(_cursorIndexOfResponseSize);
            final String _tmpMimeType;
            if (_cursor.isNull(_cursorIndexOfMimeType)) {
              _tmpMimeType = null;
            } else {
              _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            }
            final boolean _tmpIsBookmarked;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp_2 != 0;
            final List<String> _tmpTags;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfTags);
            }
            _tmpTags = __converters.toStringList(_tmp_3);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpError;
            if (_cursor.isNull(_cursorIndexOfError)) {
              _tmpError = null;
            } else {
              _tmpError = _cursor.getString(_cursorIndexOfError);
            }
            _item = new NetworkRequest(_tmpId,_tmpSessionId,_tmpUrl,_tmpMethod,_tmpProtocol,_tmpHost,_tmpPath,_tmpQuery,_tmpStartTime,_tmpEndTime,_tmpDuration,_tmpStatusCode,_tmpStatusMessage,_tmpRequestSize,_tmpResponseSize,_tmpMimeType,_tmpIsBookmarked,_tmpTags,_tmpNotes,_tmpError);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getRequestCountForSession(final String sessionId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM network_requests WHERE sessionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (sessionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sessionId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Object _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Object getTotalSizeForSession(final String sessionId,
      final Continuation<? super Long> $completion) {
    final String _sql = "SELECT SUM(requestSize + responseSize) FROM network_requests WHERE sessionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (sessionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sessionId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Object _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Flow<List<String>> getHostsForSession(final String sessionId) {
    final String _sql = "SELECT DISTINCT host FROM network_requests WHERE sessionId = ? ORDER BY host";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (sessionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sessionId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"network_requests"}, new Callable<List<String>>() {
      @Override
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<String>> getMethodsForSession(final String sessionId) {
    final String _sql = "SELECT DISTINCT method FROM network_requests WHERE sessionId = ? ORDER BY method";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (sessionId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sessionId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[]{"network_requests"}, new Callable<List<String>>() {
      @Override
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object deleteRequestsForSession(final String sessionId,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object deleteOldRequests(final Instant cutoffTime,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object updateBookmarkStatus(final String requestId, final boolean isBookmarked,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object updateTags(final String requestId, final List<String> tags,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object updateNotes(final String requestId, final String notes,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
