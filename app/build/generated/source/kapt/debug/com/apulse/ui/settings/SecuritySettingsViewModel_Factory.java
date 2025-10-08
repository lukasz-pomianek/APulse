package com.apulse.ui.settings;

import com.apulse.redaction.DataEncryptionService;
import com.apulse.redaction.RedactionEngine;
import com.apulse.redaction.SecurityPolicyManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SecuritySettingsViewModel_Factory implements Factory<SecuritySettingsViewModel> {
  private final Provider<SecurityPolicyManager> securityPolicyManagerProvider;

  private final Provider<RedactionEngine> redactionEngineProvider;

  private final Provider<DataEncryptionService> dataEncryptionServiceProvider;

  public SecuritySettingsViewModel_Factory(
      Provider<SecurityPolicyManager> securityPolicyManagerProvider,
      Provider<RedactionEngine> redactionEngineProvider,
      Provider<DataEncryptionService> dataEncryptionServiceProvider) {
    this.securityPolicyManagerProvider = securityPolicyManagerProvider;
    this.redactionEngineProvider = redactionEngineProvider;
    this.dataEncryptionServiceProvider = dataEncryptionServiceProvider;
  }

  @Override
  public SecuritySettingsViewModel get() {
    return newInstance(securityPolicyManagerProvider.get(), redactionEngineProvider.get(), dataEncryptionServiceProvider.get());
  }

  public static SecuritySettingsViewModel_Factory create(
      Provider<SecurityPolicyManager> securityPolicyManagerProvider,
      Provider<RedactionEngine> redactionEngineProvider,
      Provider<DataEncryptionService> dataEncryptionServiceProvider) {
    return new SecuritySettingsViewModel_Factory(securityPolicyManagerProvider, redactionEngineProvider, dataEncryptionServiceProvider);
  }

  public static SecuritySettingsViewModel newInstance(SecurityPolicyManager securityPolicyManager,
      RedactionEngine redactionEngine, DataEncryptionService dataEncryptionService) {
    return new SecuritySettingsViewModel(securityPolicyManager, redactionEngine, dataEncryptionService);
  }
}
