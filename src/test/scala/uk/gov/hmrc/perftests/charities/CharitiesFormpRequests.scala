/*
 * Copyright 2023 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.charities

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.BaseRequests

import scala.util.Random

object CharitiesFormpRequests extends ServicesConfiguration with BaseRequests {
  //val baseUrl: String                           = baseUrlFor("charities-claims-validation")
  val getUnregulatedDonationsUrlRef1                = s"$formpUrl/charities/OR1111/unregulated-donations"
  val postUnregulatedDonationsUrlRef1                = s"$formpUrl/charities/OR1111/unregulated-donations"

  val getUnregulatedDonationsUrlRef2                = s"$formpUrl/charities/OR2222/unregulated-donations"
  val postUnregulatedDonationsUrlRef2                = s"$formpUrl/charities/OR2222/unregulated-donations"

  val getUnregulatedDonationsUrlRef3                = s"$formpUrl/charities/OR3333/unregulated-donations"
  val postUnregulatedDonationsUrlRef3                = s"$formpUrl/charities/OR3333/unregulated-donations"

  val getUnregulatedDonationsUrlRef4                = s"$formpUrl/charities/OR4444/unregulated-donations"
  val postUnregulatedDonationsUrlRef4                = s"$formpUrl/charities/OR4444/unregulated-donations"

  def commonHeaders: Map[CharSequence, String] = Map(
    HttpHeaderNames.Authorization -> s"#{bearerToken}",
    HttpHeaderNames.ContentType   -> "application/json",
    "X-Session-ID" -> "693b2579c9ae70489252dba5"
  )

  def random10DigitNumber(): String =
    (Random.nextInt(9) + 1).toString +
      (1 to 9).map(_ => Random.nextInt(10)).mkString


  // val randomClaimID = random10DigitNumber()

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
          s"""
          {
            |  "claimId": ${random10DigitNumber()},
            |  "amount": 21345
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
             |  "claimId": ${random10DigitNumber()},
             |  "amount": 21111
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
             |  "claimId": ${random10DigitNumber()},
             |  "amount": 32453
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
             |  "claimId": ${random10DigitNumber()},
             |  "amount": 10000
             |}
             |""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))


}