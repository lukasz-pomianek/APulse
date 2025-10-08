package com.apulse.data.db;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.apulse.data.model.AppMetadata;
import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppMetadataDao_Impl implements AppMetadataDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AppMetadata> __insertionAdapterOfAppMetadata;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<AppMetadata> __deletionAdapterOfAppMetadata;

  private final EntityDeletionOrUpdateAdapter<AppMetadata> __updateAdapterOfAppMetadata;

  public AppMetadataDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAppMetadata = new EntityInsertionAdapter<AppMetadata>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `app_metadata` (`id`,`requestId`,`screenName`,`activityName`,`fragmentName`,`userId`,`userAgent`,`buildVersion`,`buildType`,`deviceInfo`,`osVersion`,`appVersion`,`threadName`,`customData`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AppMetadata value) {
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
        if (value.getScreenName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getScreenName());
        }
        if (value.getActivityName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getActivityName());
        }
        if (value.getFragmentName() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getFragmentName());
        }
        if (value.getUserId() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getUserId());
        }
        if (value.getUserAgent() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getUserAgent());
        }
        if (value.getBuildVersion() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getBuildVersion());
        }
        if (value.getBuildType() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getBuildType());
        }
        if (value.getDeviceInfo() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getDeviceInfo());
        }
        if (value.getOsVersion() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getOsVersion());
        }
        if (value.getAppVersion() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getAppVersion());
        }
        if (value.getThreadName() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getThreadName());
        }
        final String _tmp = __converters.fromStringMap(value.getCustomData());
        if (_tmp == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, _tmp);
        }
      }
    };
    this.__deletionAdapterOfAppMetadata = new EntityDeletionOrUpdateAdapter<AppMetadata>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `app_metadata` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AppMetadata value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfAppMetadata = new EntityDeletionOrUpdateAdapter<AppMetadata>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `app_metadata` SET `id` = ?,`requestId` = ?,`screenName` = ?,`activityName` = ?,`fragmentName` = ?,`userId` = ?,`userAgent` = ?,`buildVersion` = ?,`buildType` = ?,`deviceInfo` = ?,`osVersion` = ?,`appVersion` = ?,`threadName` = ?,`customData` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AppMetadata value) {
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
        if (value.getScreenName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getScreenName());
        }
        if (value.getActivityName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getActivityName());
        }
        if (value.getFragmentName() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getFragmentName());
        }
        if (value.getUserId() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getUserId());
        }
        if (value.getUserAgent() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getUserAgent());
        }
        if (value.getBuildVersion() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getBuildVersion());
        }
        if (value.getBuildType() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getBuildType());
        }
        if (value.getDeviceInfo() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getDeviceInfo());
        }
        if (value.getOsVersion() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getOsVersion());
        }
        if (value.getAppVersion() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getAppVersion());
        }
        if (value.getThreadName() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getThreadName());
        }
        final String _tmp = __converters.fromStringMap(value.getCustomData());
        if (_tmp == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, _tmp);
        }
        if (value.getId() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getId());
        }
      }
    };
  }

  @Override
  public Object insertMetadata(final AppMetadata metadata,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object deleteMetadata(final AppMetadata metadata,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object updateMetadata(final AppMetadata metadata,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public AppMetadata getMetadataForRequest(final String requestId) {
    final String _sql = "SELECT * FROM app_metadata WHERE requestId = ?";
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
      final int _cursorIndexOfScreenName = CursorUtil.getColumnIndexOrThrow(_cursor, "screenName");
      final int _cursorIndexOfActivityName = CursorUtil.getColumnIndexOrThrow(_cursor, "activityName");
      final int _cursorIndexOfFragmentName = CursorUtil.getColumnIndexOrThrow(_cursor, "fragmentName");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfUserAgent = CursorUtil.getColumnIndexOrThrow(_cursor, "userAgent");
      final int _cursorIndexOfBuildVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "buildVersion");
      final int _cursorIndexOfBuildType = CursorUtil.getColumnIndexOrThrow(_cursor, "buildType");
      final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceInfo");
      final int _cursorIndexOfOsVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "osVersion");
      final int _cursorIndexOfAppVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "appVersion");
      final int _cursorIndexOfThreadName = CursorUtil.getColumnIndexOrThrow(_cursor, "threadName");
      final int _cursorIndexOfCustomData = CursorUtil.getColumnIndexOrThrow(_cursor, "customData");
      final AppMetadata _result;
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
        final String _tmpScreenName;
        if (_cursor.isNull(_cursorIndexOfScreenName)) {
          _tmpScreenName = null;
        } else {
          _tmpScreenName = _cursor.getString(_cursorIndexOfScreenName);
        }
        final String _tmpActivityName;
        if (_cursor.isNull(_cursorIndexOfActivityName)) {
          _tmpActivityName = null;
        } else {
          _tmpActivityName = _cursor.getString(_cursorIndexOfActivityName);
        }
        final String _tmpFragmentName;
        if (_cursor.isNull(_cursorIndexOfFragmentName)) {
          _tmpFragmentName = null;
        } else {
          _tmpFragmentName = _cursor.getString(_cursorIndexOfFragmentName);
        }
        final String _tmpUserId;
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _tmpUserId = null;
        } else {
          _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
        }
        final String _tmpUserAgent;
        if (_cursor.isNull(_cursorIndexOfUserAgent)) {
          _tmpUserAgent = null;
        } else {
          _tmpUserAgent = _cursor.getString(_cursorIndexOfUserAgent);
        }
        final String _tmpBuildVersion;
        if (_cursor.isNull(_cursorIndexOfBuildVersion)) {
          _tmpBuildVersion = null;
        } else {
          _tmpBuildVersion = _cursor.getString(_cursorIndexOfBuildVersion);
        }
        final String _tmpBuildType;
        if (_cursor.isNull(_cursorIndexOfBuildType)) {
          _tmpBuildType = null;
        } else {
          _tmpBuildType = _cursor.getString(_cursorIndexOfBuildType);
        }
        final String _tmpDeviceInfo;
        if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
          _tmpDeviceInfo = null;
        } else {
          _tmpDeviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
        }
        final String _tmpOsVersion;
        if (_cursor.isNull(_cursorIndexOfOsVersion)) {
          _tmpOsVersion = null;
        } else {
          _tmpOsVersion = _cursor.getString(_cursorIndexOfOsVersion);
        }
        final String _tmpAppVersion;
        if (_cursor.isNull(_cursorIndexOfAppVersion)) {
          _tmpAppVersion = null;
        } else {
          _tmpAppVersion = _cursor.getString(_cursorIndexOfAppVersion);
        }
        final String _tmpThreadName;
        if (_cursor.isNull(_cursorIndexOfThreadName)) {
          _tmpThreadName = null;
        } else {
          _tmpThreadName = _cursor.getString(_cursorIndexOfThreadName);
        }
        final Map<String, String> _tmpCustomData;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfCustomData)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfCustomData);
        }
        _tmpCustomData = __converters.toStringMap(_tmp);
        _result = new AppMetadata(_tmpId,_tmpRequestId,_tmpScreenName,_tmpActivityName,_tmpFragmentName,_tmpUserId,_tmpUserAgent,_tmpBuildVersion,_tmpBuildType,_tmpDeviceInfo,_tmpOsVersion,_tmpAppVersion,_tmpThreadName,_tmpCustomData);
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
  public List<String> getDistinctScreenNames() {
    final String _sql = "SELECT DISTINCT screenName FROM app_metadata WHERE screenName IS NOT NULL ORDER BY screenName";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
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
      _statement.release();
    }
  }

  @Override
  public List<String> getDistinctUserIds() {
    final String _sql = "SELECT DISTINCT userId FROM app_metadata WHERE userId IS NOT NULL ORDER BY userId";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
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
      _statement.release();
    }
  }

  @Override
  public Object deleteMetadataForRequest(final String requestId,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
