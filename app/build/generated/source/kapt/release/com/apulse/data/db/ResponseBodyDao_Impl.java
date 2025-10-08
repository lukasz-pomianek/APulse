package com.apulse.data.db;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.apulse.data.model.ResponseBody;
import java.lang.Class;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ResponseBodyDao_Impl implements ResponseBodyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ResponseBody> __insertionAdapterOfResponseBody;

  private final EntityDeletionOrUpdateAdapter<ResponseBody> __deletionAdapterOfResponseBody;

  private final EntityDeletionOrUpdateAdapter<ResponseBody> __updateAdapterOfResponseBody;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLargeBodies;

  public ResponseBodyDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfResponseBody = new EntityInsertionAdapter<ResponseBody>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `response_bodies` (`id`,`requestId`,`body`,`bodyText`,`contentType`,`contentEncoding`,`size`,`isRedacted`,`filePath`,`isImage`,`isJson`,`isXml`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ResponseBody value) {
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
        final int _tmp_1 = value.isImage() ? 1 : 0;
        stmt.bindLong(10, _tmp_1);
        final int _tmp_2 = value.isJson() ? 1 : 0;
        stmt.bindLong(11, _tmp_2);
        final int _tmp_3 = value.isXml() ? 1 : 0;
        stmt.bindLong(12, _tmp_3);
      }
    };
    this.__deletionAdapterOfResponseBody = new EntityDeletionOrUpdateAdapter<ResponseBody>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `response_bodies` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ResponseBody value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfResponseBody = new EntityDeletionOrUpdateAdapter<ResponseBody>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `response_bodies` SET `id` = ?,`requestId` = ?,`body` = ?,`bodyText` = ?,`contentType` = ?,`contentEncoding` = ?,`size` = ?,`isRedacted` = ?,`filePath` = ?,`isImage` = ?,`isJson` = ?,`isXml` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ResponseBody value) {
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
        final int _tmp_1 = value.isImage() ? 1 : 0;
        stmt.bindLong(10, _tmp_1);
        final int _tmp_2 = value.isJson() ? 1 : 0;
        stmt.bindLong(11, _tmp_2);
        final int _tmp_3 = value.isXml() ? 1 : 0;
        stmt.bindLong(12, _tmp_3);
        if (value.getId() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteLargeBodies = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM response_bodies WHERE size > ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertBody(final ResponseBody body, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object deleteBody(final ResponseBody body, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object updateBody(final ResponseBody body, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public int deleteLargeBodies(final long maxSize) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteLargeBodies.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, maxSize);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteLargeBodies.release(_stmt);
    }
  }

  @Override
  public ResponseBody getBodyForRequest(final String requestId) {
    final String _sql = "SELECT * FROM response_bodies WHERE requestId = ?";
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
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "requestId");
      final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
      final int _cursorIndexOfBodyText = CursorUtil.getColumnIndexOrThrow(_cursor, "bodyText");
      final int _cursorIndexOfContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "contentType");
      final int _cursorIndexOfContentEncoding = CursorUtil.getColumnIndexOrThrow(_cursor, "contentEncoding");
      final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
      final int _cursorIndexOfIsRedacted = CursorUtil.getColumnIndexOrThrow(_cursor, "isRedacted");
      final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
      final int _cursorIndexOfIsImage = CursorUtil.getColumnIndexOrThrow(_cursor, "isImage");
      final int _cursorIndexOfIsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "isJson");
      final int _cursorIndexOfIsXml = CursorUtil.getColumnIndexOrThrow(_cursor, "isXml");
      final ResponseBody _result;
      if(_cursor.moveToFirst()) {
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        final String _tmpRequestId;
        if (_cursor.isNull(_cursorIndexOfRequestId)) {
          _tmpRequestId = null;
        } else {
          _tmpRequestId = _cursor.getString(_cursorIndexOfRequestId);
        }
        final byte[] _tmpBody;
        if (_cursor.isNull(_cursorIndexOfBody)) {
          _tmpBody = null;
        } else {
          _tmpBody = _cursor.getBlob(_cursorIndexOfBody);
        }
        final String _tmpBodyText;
        if (_cursor.isNull(_cursorIndexOfBodyText)) {
          _tmpBodyText = null;
        } else {
          _tmpBodyText = _cursor.getString(_cursorIndexOfBodyText);
        }
        final String _tmpContentType;
        if (_cursor.isNull(_cursorIndexOfContentType)) {
          _tmpContentType = null;
        } else {
          _tmpContentType = _cursor.getString(_cursorIndexOfContentType);
        }
        final String _tmpContentEncoding;
        if (_cursor.isNull(_cursorIndexOfContentEncoding)) {
          _tmpContentEncoding = null;
        } else {
          _tmpContentEncoding = _cursor.getString(_cursorIndexOfContentEncoding);
        }
        final long _tmpSize;
        _tmpSize = _cursor.getLong(_cursorIndexOfSize);
        final boolean _tmpIsRedacted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsRedacted);
        _tmpIsRedacted = _tmp != 0;
        final String _tmpFilePath;
        if (_cursor.isNull(_cursorIndexOfFilePath)) {
          _tmpFilePath = null;
        } else {
          _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
        }
        final boolean _tmpIsImage;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsImage);
        _tmpIsImage = _tmp_1 != 0;
        final boolean _tmpIsJson;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfIsJson);
        _tmpIsJson = _tmp_2 != 0;
        final boolean _tmpIsXml;
        final int _tmp_3;
        _tmp_3 = _cursor.getInt(_cursorIndexOfIsXml);
        _tmpIsXml = _tmp_3 != 0;
        _result = new ResponseBody(_tmpId,_tmpRequestId,_tmpBody,_tmpBodyText,_tmpContentType,_tmpContentEncoding,_tmpSize,_tmpIsRedacted,_tmpFilePath,_tmpIsImage,_tmpIsJson,_tmpIsXml);
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
  public Long getTotalBodySize() {
    final String _sql = "SELECT SUM(size) FROM response_bodies";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Long _result;
      if(_cursor.moveToFirst()) {
        final Long _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(0);
        }
        _result = _tmp;
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
  public List<ResponseBody> getImageBodies() {
    final String _sql = "SELECT * FROM response_bodies WHERE isImage = 1 ORDER BY requestId";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "requestId");
      final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
      final int _cursorIndexOfBodyText = CursorUtil.getColumnIndexOrThrow(_cursor, "bodyText");
      final int _cursorIndexOfContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "contentType");
      final int _cursorIndexOfContentEncoding = CursorUtil.getColumnIndexOrThrow(_cursor, "contentEncoding");
      final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
      final int _cursorIndexOfIsRedacted = CursorUtil.getColumnIndexOrThrow(_cursor, "isRedacted");
      final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
      final int _cursorIndexOfIsImage = CursorUtil.getColumnIndexOrThrow(_cursor, "isImage");
      final int _cursorIndexOfIsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "isJson");
      final int _cursorIndexOfIsXml = CursorUtil.getColumnIndexOrThrow(_cursor, "isXml");
      final List<ResponseBody> _result = new ArrayList<ResponseBody>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ResponseBody _item;
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        final String _tmpRequestId;
        if (_cursor.isNull(_cursorIndexOfRequestId)) {
          _tmpRequestId = null;
        } else {
          _tmpRequestId = _cursor.getString(_cursorIndexOfRequestId);
        }
        final byte[] _tmpBody;
        if (_cursor.isNull(_cursorIndexOfBody)) {
          _tmpBody = null;
        } else {
          _tmpBody = _cursor.getBlob(_cursorIndexOfBody);
        }
        final String _tmpBodyText;
        if (_cursor.isNull(_cursorIndexOfBodyText)) {
          _tmpBodyText = null;
        } else {
          _tmpBodyText = _cursor.getString(_cursorIndexOfBodyText);
        }
        final String _tmpContentType;
        if (_cursor.isNull(_cursorIndexOfContentType)) {
          _tmpContentType = null;
        } else {
          _tmpContentType = _cursor.getString(_cursorIndexOfContentType);
        }
        final String _tmpContentEncoding;
        if (_cursor.isNull(_cursorIndexOfContentEncoding)) {
          _tmpContentEncoding = null;
        } else {
          _tmpContentEncoding = _cursor.getString(_cursorIndexOfContentEncoding);
        }
        final long _tmpSize;
        _tmpSize = _cursor.getLong(_cursorIndexOfSize);
        final boolean _tmpIsRedacted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsRedacted);
        _tmpIsRedacted = _tmp != 0;
        final String _tmpFilePath;
        if (_cursor.isNull(_cursorIndexOfFilePath)) {
          _tmpFilePath = null;
        } else {
          _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
        }
        final boolean _tmpIsImage;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsImage);
        _tmpIsImage = _tmp_1 != 0;
        final boolean _tmpIsJson;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfIsJson);
        _tmpIsJson = _tmp_2 != 0;
        final boolean _tmpIsXml;
        final int _tmp_3;
        _tmp_3 = _cursor.getInt(_cursorIndexOfIsXml);
        _tmpIsXml = _tmp_3 != 0;
        _item = new ResponseBody(_tmpId,_tmpRequestId,_tmpBody,_tmpBodyText,_tmpContentType,_tmpContentEncoding,_tmpSize,_tmpIsRedacted,_tmpFilePath,_tmpIsImage,_tmpIsJson,_tmpIsXml);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ResponseBody> getJsonBodies() {
    final String _sql = "SELECT * FROM response_bodies WHERE isJson = 1 ORDER BY requestId";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfRequestId = CursorUtil.getColumnIndexOrThrow(_cursor, "requestId");
      final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "body");
      final int _cursorIndexOfBodyText = CursorUtil.getColumnIndexOrThrow(_cursor, "bodyText");
      final int _cursorIndexOfContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "contentType");
      final int _cursorIndexOfContentEncoding = CursorUtil.getColumnIndexOrThrow(_cursor, "contentEncoding");
      final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
      final int _cursorIndexOfIsRedacted = CursorUtil.getColumnIndexOrThrow(_cursor, "isRedacted");
      final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
      final int _cursorIndexOfIsImage = CursorUtil.getColumnIndexOrThrow(_cursor, "isImage");
      final int _cursorIndexOfIsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "isJson");
      final int _cursorIndexOfIsXml = CursorUtil.getColumnIndexOrThrow(_cursor, "isXml");
      final List<ResponseBody> _result = new ArrayList<ResponseBody>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ResponseBody _item;
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        final String _tmpRequestId;
        if (_cursor.isNull(_cursorIndexOfRequestId)) {
          _tmpRequestId = null;
        } else {
          _tmpRequestId = _cursor.getString(_cursorIndexOfRequestId);
        }
        final byte[] _tmpBody;
        if (_cursor.isNull(_cursorIndexOfBody)) {
          _tmpBody = null;
        } else {
          _tmpBody = _cursor.getBlob(_cursorIndexOfBody);
        }
        final String _tmpBodyText;
        if (_cursor.isNull(_cursorIndexOfBodyText)) {
          _tmpBodyText = null;
        } else {
          _tmpBodyText = _cursor.getString(_cursorIndexOfBodyText);
        }
        final String _tmpContentType;
        if (_cursor.isNull(_cursorIndexOfContentType)) {
          _tmpContentType = null;
        } else {
          _tmpContentType = _cursor.getString(_cursorIndexOfContentType);
        }
        final String _tmpContentEncoding;
        if (_cursor.isNull(_cursorIndexOfContentEncoding)) {
          _tmpContentEncoding = null;
        } else {
          _tmpContentEncoding = _cursor.getString(_cursorIndexOfContentEncoding);
        }
        final long _tmpSize;
        _tmpSize = _cursor.getLong(_cursorIndexOfSize);
        final boolean _tmpIsRedacted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsRedacted);
        _tmpIsRedacted = _tmp != 0;
        final String _tmpFilePath;
        if (_cursor.isNull(_cursorIndexOfFilePath)) {
          _tmpFilePath = null;
        } else {
          _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
        }
        final boolean _tmpIsImage;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsImage);
        _tmpIsImage = _tmp_1 != 0;
        final boolean _tmpIsJson;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfIsJson);
        _tmpIsJson = _tmp_2 != 0;
        final boolean _tmpIsXml;
        final int _tmp_3;
        _tmp_3 = _cursor.getInt(_cursorIndexOfIsXml);
        _tmpIsXml = _tmp_3 != 0;
        _item = new ResponseBody(_tmpId,_tmpRequestId,_tmpBody,_tmpBodyText,_tmpContentType,_tmpContentEncoding,_tmpSize,_tmpIsRedacted,_tmpFilePath,_tmpIsImage,_tmpIsJson,_tmpIsXml);
        _result.add(_item);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
