package com.apulse.di;

import android.content.Context;
import com.apulse.data.db.APulseDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideAPulseDatabaseFactory implements Factory<APulseDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideAPulseDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public APulseDatabase get() {
    return provideAPulseDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideAPulseDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideAPulseDatabaseFactory(contextProvider);
  }

  public static APulseDatabase provideAPulseDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAPulseDatabase(context));
  }
}
