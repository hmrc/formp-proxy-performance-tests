/*
 * Copyright 2026 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.AuthLogin

import io.gatling.core.Predef._
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.BaseRequests

import scala.util.Random.alphanumeric

object AuthLoginRequests extends ServicesConfiguration with BaseRequests {

  def getAuthToken(authPayload: () => String): List[ActionBuilder] = exec(
    http("Insert Auth Record for Org")
      .post(authLoginApiUrl)
      .headers(Map(HttpHeaderNames.ContentType -> "application/json"))
      .body(StringBody(_ => authPayload()))
      .asJson
      .check(status.is(201))
      .check(header(HttpHeaderNames.Authorization).optional.saveAs("bearerToken"))
  ).actionBuilders

  def authPayloadCharitiesOrg(): String = {
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

//  private def authPayloadCISAgent(): String = {
//    val CredIdLength = 16
//    val credId       = alphanumeric.take(CredIdLength).mkString
//
//    s"""
//       |{
//       |  "confidenceLevel": 50,
//       |  "affinityGroup": "Agent",
//       |  "credentialStrength": "strong",
//       |  "credId": "$credId",
//       |  "credentialRole": "User",
//       |  "excludeGnapToken": true,
//       |  "enrolments": [
//       |    {
//       |      "key": "HMRC-CHAR-ORG",
//       |      "identifiers": [
//       |        {
//       |          "key": "CHARID",
//       |          "value": "#{charId}"
//       |        }
//       |      ],
//       |      "state": "Activated"
//       |    }
//       |  ]
//       |}
//       |""".stripMargin
//  }

}