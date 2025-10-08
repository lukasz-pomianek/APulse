package com.apulse.di;

import com.apulse.data.db.APulseDatabase;
import com.apulse.data.db.ResponseHeadersDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DatabaseModule_ProvideResponseHeadersDaoFactory implements Factory<ResponseHeadersDao> {
  private final Provider<APulseDatabase> databaseProvider;

  public DatabaseModule_ProvideResponseHeadersDaoFactory(
      Provider<APulseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ResponseHeadersDao get() {
    return provideResponseHeadersDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideResponseHeadersDaoFactory create(
      Provider<APulseDatabase> databaseProvider) {
    return new DatabaseModule_ProvideResponseHeadersDaoFactory(databaseProvider);
  }

  public static ResponseHeadersDao provideResponseHeadersDao(APulseDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideResponseHeadersDao(database));
  }
}
