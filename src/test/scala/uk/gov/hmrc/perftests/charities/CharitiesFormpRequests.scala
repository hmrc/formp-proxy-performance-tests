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

package uk.gov.hmrc.perftests.charities

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.BaseRequests

import java.util.concurrent.ThreadLocalRandom
import scala.util.Random

object CharitiesFormpRequests extends ServicesConfiguration with BaseRequests {

  val getUnregulatedDonationsUrlRef1                = s"$formpUrl/charities/OR1111/unregulated-donations"
  val postUnregulatedDonationsUrlRef1                = s"$formpUrl/charities/OR1111/unregulated-donations"

  val getUnregulatedDonationsUrlRef2                = s"$formpUrl/charities/OR2222/unregulated-donations"
  val postUnregulatedDonationsUrlRef2                = s"$formpUrl/charities/OR2222/unregulated-donations"

  val getUnregulatedDonationsUrlRef3                = s"$formpUrl/charities/OR3333/unregulated-donations"
  val postUnregulatedDonationsUrlRef3                = s"$formpUrl/charities/OR3333/unregulated-donations"

  val getUnregulatedDonationsUrlRef4                = s"$formpUrl/charities/OR4444/unregulated-donations"
  val postUnregulatedDonationsUrlRef4                = s"$formpUrl/charities/OR4444/unregulated-donations"

  val postUnregulatedDonationsUrlRandom                = s"$formpUrl/charities/OR4444/unregulated-donations"

  def commonHeaders: Map[CharSequence, String] = Map(
    HttpHeaderNames.Authorization -> s"#{bearerToken}",
    HttpHeaderNames.ContentType   -> "application/json",
    "X-Session-ID" -> "693b2579c9ae70489252dba5"
  )

  def randomCharityRef(): String = {
    val letters =
      (1 to 2)
        .map(_ => ('A' + Random.nextInt(26)).toChar)
        .mkString
    val numbers =
      Random.nextInt(90000) + 10000
    s"$letters$numbers"
  }

  val postUnregulatedDonationsRandom: HttpRequestBuilder =
    http("POST Unregulated Donations for Charity Ref randomly generated")
      .post { _ =>
        val reference = randomCharityRef()
        val requestUrl = s"$formpUrl/charities/$reference/unregulated-donations"
        requestUrl
      }
      .headers(commonHeaders)
      .body(
        StringBody(
          """
          {
            |  "claimId": #{randomLong(1,999999999)},
            |  "amount": #{randomLong(1,99999999)}
            |}
            |""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))


  val getUnregulatedDonationsRef1: HttpRequestBuilder =
    http("GET Unregulated Donations for Charity Ref OR1111")
      .get(getUnregulatedDonationsUrlRef1)
      .headers(commonHeaders)
      .check(
        status.in(200, 201),
        jsonPath("$.unregulatedDonationsTotal").exists
      )

  val postUnregulatedDonationsRef1: HttpRequestBuilder =
    http("POST Unregulated Donations for Charity Ref OR1111")
      .post(postUnregulatedDonationsUrlRef1)
      .headers(commonHeaders)
      .body(
        StringBody(
          """
          {
             |  "claimId": #{randomLong(1,999999999)},
             |  "amount": #{randomLong(1,99999999)}
             |}
             |""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))


  val getUnregulatedDonationsRef2: HttpRequestBuilder =
    http("GET Unregulated Donations for Charity Ref OR2222")
      .get(getUnregulatedDonationsUrlRef2)
      .headers(commonHeaders)
      .check(
        status.in(200, 201),
        jsonPath("$.unregulatedDonationsTotal").exists
      )

  val postUnregulatedDonationsRef2: HttpRequestBuilder =
    http("POST Unregulated Donations for Charity Ref OR2222")
      .post(postUnregulatedDonationsUrlRef2)
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""
          {
             |  "claimId": #{randomLong(1,999999999)},
             |  "amount": #{randomLong(1,99999999)}
             |}
             |""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  val getUnregulatedDonationsRef3: HttpRequestBuilder =
    http("GET Unregulated Donations for Charity Ref OR3333")
      .get(getUnregulatedDonationsUrlRef3)
      .headers(commonHeaders)
      .check(
        status.in(200, 201),
        jsonPath("$.unregulatedDonationsTotal").exists
      )

  val postUnregulatedDonationsRef3: HttpRequestBuilder =
    http("POST Unregulated Donations for Charity Ref OR3333")
      .post(postUnregulatedDonationsUrlRef3)
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""
          {
             |  "claimId": #{randomLong(1,999999999)},
             |  "amount": #{randomLong(1,99999999)}
             |}
             |""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  val getUnregulatedDonationsRef4: HttpRequestBuilder =
    http("GET Unregulated Donations for Charity Ref OR4444")
      .get(getUnregulatedDonationsUrlRef4)
      .headers(commonHeaders)
      .check(
        status.in(200, 201),
        jsonPath("$.unregulatedDonationsTotal").exists
      )

  val postUnregulatedDonationsRef4: HttpRequestBuilder =
    http("POST Unregulated Donations for Charity Ref OR4444")
      .post(postUnregulatedDonationsUrlRef4)
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""
          {
             |  "claimId": #{randomLong(1,999999999)},
             |  "amount": #{randomLong(1,99999999)}
             |}
             |""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

}