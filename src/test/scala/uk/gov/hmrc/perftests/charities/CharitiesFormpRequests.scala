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

  val createUploadTrackingUrlGiftAid            = s"$baseUrl/charities-claims-validation/#{claimIdGiftAid}/create-upload-tracking"
  val createUploadTrackingUrlConnectedCharities =
    s"$baseUrl/charities-claims-validation/#{claimIdConnectedCharities}/create-upload-tracking"
  val createUploadTrackingUrlOtherIncome        =
    s"$baseUrl/charities-claims-validation/#{claimIdOtherIncome}/create-upload-tracking"

  def commonHeaders: Map[CharSequence, String] = Map(
    HttpHeaderNames.Authorization -> s"#{bearerToken}",
    HttpHeaderNames.ContentType   -> "application/json"
  )

  def random10DigitNumber(): String =
  (Random.nextInt(9) + 1).toString +
  (1 to 9).map(_ => Random.nextInt(10)).mkString

  val randomClaimID = random10DigitNumber()

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


}