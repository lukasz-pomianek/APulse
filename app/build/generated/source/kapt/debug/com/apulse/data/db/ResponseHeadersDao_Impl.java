package com.apulse.data.db;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.apulse.data.model.ResponseHeaders;
import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ResponseHeadersDao_Impl implements ResponseHeadersDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ResponseHeaders> __insertionAdapterOfResponseHeaders;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<ResponseHeaders> __deletionAdapterOfResponseHeaders;

  private final EntityDeletionOrUpdateAdapter<ResponseHeaders> __updateAdapterOfResponseHeaders;

  public ResponseHeadersDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfResponseHeaders = new EntityInsertionAdapter<ResponseHeaders>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `response_headers` (`id`,`requestId`,`headers`,`rawHeaders`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ResponseHeaders value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getRequestId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getRequestId());
        }
        final String _tmp = __converters.fromStringMap(value.getHeaders());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        if (value.getRawHeaders() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getRawHeaders());
        }
      }
    };
    this.__deletionAdapterOfResponseHeaders = new EntityDeletionOrUpdateAdapter<ResponseHeaders>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `response_headers` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ResponseHeaders value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfResponseHeaders = new EntityDeletionOrUpdateAdapter<ResponseHeaders>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `response_headers` SET `id` = ?,`requestId` = ?,`headers` = ?,`rawHeaders` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ResponseHeaders value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getRequestId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getRequestId());
        }
        final String _tmp = __converters.fromStringMap(value.getHeaders());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        if (value.getRawHeaders() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getRawHeaders());
        }
        if (value.getId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getId());
        }
      }
    };
  }

  @Override
  public Object insertHeaders(final ResponseHeaders headers,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object deleteHeaders(final ResponseHeaders headers,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object updateHeaders(final ResponseHeaders headers,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object getHeadersForRequest(final String requestId,
      final Continuation<? super ResponseHeaders> $completion) {
    final String _sql = "SELECT * FROM response_headers WHERE requestId = ?";
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
  public Object deleteHeadersForRequest(final String requestId,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
