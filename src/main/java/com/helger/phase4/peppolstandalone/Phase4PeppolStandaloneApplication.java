/*
 * Copyright (C) 2023-2025 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.phase4.peppolstandalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This is the application entrypoint.
 *
 * @author Philip Helger
 */
@SpringBootApplication
@EnableScheduling
public class Phase4PeppolStandaloneApplication
{
  public static void main (final String [] args)
  {
    // Convert environment variables to system properties before Spring Boot starts.

    convertEnvironmentVariablesToSystemProperties ();
    
    // Log the current peppol.seatid from the application.properties file
    System.out.println("Current peppol.seatid: " + APConfig.getMyPeppolSeatID());

    SpringApplication.run (Phase4PeppolStandaloneApplication.class, args);
  }

  /**
   * Converts environment variables to system properties using Spring Boot's conversion rules:
   * - Converts to lowercase
   * - Replaces underscores with dots
   * This ensures properties are available before AS4Configuration initializes.
   * 
   * Spring Boot automatically does this conversion, but we need to do it early
   * for system properties since AS4Configuration reads from System.getProperty().
   */
  private static void convertEnvironmentVariablesToSystemProperties ()
  {
    // Process all environment variables and convert them to system properties
    // Spring Boot's rule: ENV_VAR_NAME -> env.var.name
    // Example: PEPPOL_STAGE -> peppol.stage, PHASE4_ENDPOINT_ADDRESS -> phase4.endpoint.address
    System.getenv ().forEach ((envKey, envValue) -> {
      if (envValue != null && !envValue.isEmpty ())
      {
        // Convert ENV_VAR_NAME to env.var.name (Spring Boot's conversion rule)
        final String propertyKey = envKey.toLowerCase ().replace ('_', '.');
        System.setProperty (propertyKey, envValue);
      }
    });
  }
}
