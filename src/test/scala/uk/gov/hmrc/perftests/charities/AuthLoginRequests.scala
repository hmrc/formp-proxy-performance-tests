/*
 * Copyright 2026 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.charities

import io.gatling.core.Predef._
import io.gatling.core.check.CheckBuilder
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.check.header.HttpHeaderRegexCheckType
import uk.gov.hmrc.performance.conf.ServicesConfiguration

import scala.util.Random.alphanumeric

object AuthLoginRequests extends ServicesConfiguration {
  val authBaseUrl: String     = baseUrlFor("auth-login-api")
  val authLoginApiUrl: String = s"$authBaseUrl/government-gateway/session/login"

  val getAuthToken: ChainBuilder = exec(
    http("Insert Auth Record for Org")
      .post(authLoginApiUrl)
      .headers(Map(HttpHeaderNames.ContentType -> "application/json"))
      .body(StringBody(authPayloadOrg))
      .asJson
      .check(status.is(201))
      .check(header(HttpHeaderNames.Authorization).optional.saveAs("bearerToken"))
  )

  private def authPayloadOrg: String = {
    val CredIdLength = 16
    val credId       = alphanumeric.take(CredIdLength).mkString

    s"""
       |{
       |  "confidenceLevel": 50,
       |  "affinityGroup": "Organisation",
       |  "credentialStrength": "strong",
       |  "credId": "$credId",
       |  "credentialRole": "User",
       |  "excludeGnapToken": true,
       |  "enrolments": [
       |    {
       |      "key": "HMRC-CHAR-ORG",
       |      "identifiers": [
       |        {
       |          "key": "CHARID",
       |          "value": "#{charId}"
       |        }
       |      ],
       |      "state": "Activated"
       |    }
       |  ]
       |}
       |""".stripMargin
  }
}