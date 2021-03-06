/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.firebase.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GooglePublicKeysManager;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;

import java.io.IOException;

/**
 * Provides trampolines into package-private Auth APIs used by components of Firebase
 *
 * <p>This class will not be compiled into the shipping library and can only be used in tests.
 */
public final class TestOnlyImplFirebaseAuthTrampolines {

  private TestOnlyImplFirebaseAuthTrampolines() {}

  /* FirebaseApp */
  public static FirebaseToken.FirebaseTokenImpl getToken(FirebaseToken tokenHolder) {
    return tokenHolder.getToken();
  }

  /* FirebaseToken */
  public static FirebaseToken parseToken(JsonFactory jsonFactory, String tokenString)
      throws IOException {
    return FirebaseToken.parse(jsonFactory, tokenString);
  }

  /* FirebaseCredentials */
  public static Task<GoogleCredential> getCertificate(FirebaseCredential credential) {
    if (credential instanceof FirebaseCredentials.CertCredential) {
      return ((FirebaseCredentials.CertCredential) credential).getCertificate();
    } else {
      return Tasks.forException(new FirebaseException("Cannot convert to CertCredential"));
    }
  }

  /* FirebaseCredentials */
  public static Task<String> getProjectId(FirebaseCredential credential) {
    return ((FirebaseCredentials.CertCredential) credential).getProjectId();
  }

  /* FirebaseAuth */
  public static FirebaseAuth getFirebaseAuthInstance(
      FirebaseApp firebaseApp, GooglePublicKeysManager googlePublicKeysManager, Clock clock) {
    return new FirebaseAuth(firebaseApp, googlePublicKeysManager, clock);
  }
}
