package com.work.activitytracker.core.data.session;

import com.google.firebase.auth.FirebaseAuth;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class FirebaseUserSession_Factory implements Factory<FirebaseUserSession> {
  private final Provider<FirebaseAuth> authProvider;

  private FirebaseUserSession_Factory(Provider<FirebaseAuth> authProvider) {
    this.authProvider = authProvider;
  }

  @Override
  public FirebaseUserSession get() {
    return newInstance(authProvider.get());
  }

  public static FirebaseUserSession_Factory create(Provider<FirebaseAuth> authProvider) {
    return new FirebaseUserSession_Factory(authProvider);
  }

  public static FirebaseUserSession newInstance(FirebaseAuth auth) {
    return new FirebaseUserSession(auth);
  }
}
