package com.apulse.data.db;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.apulse.data.model.RequestBody;
import java.lang.Class;
import java.lang.Long;
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
public final class RequestBodyDao_Impl implements RequestBodyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RequestBody> __insertionAdapterOfRequestBody;

  private final EntityDeletionOrUpdateAdapter<RequestBody> __deletionAdapterOfRequestBody;

  private final EntityDeletionOrUpdateAdapter<RequestBody> __updateAdapterOfRequestBody;

  public RequestBodyDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRequestBody = new EntityInsertionAdapter<RequestBody>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `request_bodies` (`id`,`requestId`,`body`,`bodyText`,`contentType`,`contentEncoding`,`size`,`isRedacted`,`filePath`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RequestBody value) {
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
        if (value.getBody() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindBlob(3, value.getBody());
        }
        if (value.getBodyText() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getBodyText());
        }
        if (value.getContentType() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getContentType());
        }
        if (value.getContentEncoding() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getContentEncoding());
        }
        stmt.bindLong(7, value.getSize());
        final int _tmp = value.isRedacted() ? 1 : 0;
        stmt.bindLong(8, _tmp);
        if (value.getFilePath() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getFilePath());
        }
      }
    };
    this.__deletionAdapterOfRequestBody = new EntityDeletionOrUpdateAdapter<RequestBody>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `request_bodies` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RequestBody value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfRequestBody = new EntityDeletionOrUpdateAdapter<RequestBody>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `request_bodies` SET `id` = ?,`requestId` = ?,`body` = ?,`bodyText` = ?,`contentType` = ?,`contentEncoding` = ?,`size` = ?,`isRedacted` = ?,`filePath` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RequestBody value) {
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
        if (value.getBody() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindBlob(3, value.getBody());
        }
        if (value.getBodyText() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getBodyText());
        }
        if (value.getContentType() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getContentType());
        }
        if (value.getContentEncoding() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getContentEncoding());
        }
        stmt.bindLong(7, value.getSize());
        final int _tmp = value.isRedacted() ? 1 : 0;
        stmt.bindLong(8, _tmp);
        if (value.getFilePath() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getFilePath());
        }
        if (value.getId() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getId());
        }
      }
    };
  }

  @Override
  public Object insertBody(final RequestBody body, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object deleteBody(final RequestBody body, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object updateBody(final RequestBody body, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object getBodyForRequest(final String requestId,
      final Continuation<? super RequestBody> $completion) {
    final String _sql = "SELECT * FROM request_bodies WHERE requestId = ?";
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
  public Object getTotalBodySize(final Continuation<? super Long> $completion) {
    final String _sql = "SELECT SUM(size) FROM request_bodies";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    int _argIndex = 1;
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
  public Object deleteBodyForRequest(final String requestId,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object deleteLargeBodies(final long maxSize,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
