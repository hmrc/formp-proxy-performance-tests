/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.perftests

import io.gatling.core.Predef._
import io.gatling.core.check.CheckBuilder
import io.gatling.core.check.regex.RegexCheckType
import io.gatling.http.Predef._
import io.gatling.http.check.header.HttpHeaderRegexCheckType
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.AuthLogin.AuthLoginRequests.baseUrlFor

trait BaseRequests extends ServicesConfiguration {

  val authBaseUrl: String     = baseUrlFor("auth-login-api")
  val authLoginApiUrl: String = s"$authBaseUrl/government-gateway/session/login"
  val formphost: String       = baseUrlFor("formp-proxy")
  val formpUrl                = s"$formphost/formp-proxy"

  val authLoginStub: String = baseUrlFor("auth-login-stub")
  val authLoginStubUrl      = s"$authLoginStub/auth-login-stub/gg-sign-in"

  // Charities ---------------------------------

  val CsrfPattern           = """<input type="hidden" name="csrfToken" value="([^"]+)""""
  val lookupPattern         = """<form method="POST" novalidate action="([^"]+)""""

  def saveCsrfToken(): CheckBuilder[RegexCheckType, String] = regex(_ => CsrfPattern).saveAs("csrfToken")

  private val lookupRegexp = "(.*)/begin"

  def saveAddressLookupUrl: CheckBuilder[HttpHeaderRegexCheckType, Response] =
    headerRegex("Location", lookupRegexp).saveAs("lookupAddressLocation")

}
