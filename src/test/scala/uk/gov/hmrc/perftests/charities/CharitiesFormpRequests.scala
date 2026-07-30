/*
 * Copyright 2023 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.charities

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object CharitiesFormpRequests extends ServicesConfiguration {
  val baseUrl: String                           = baseUrlFor("charities-claims-validation")
  val createUploadTrackingUrlGiftAid            = s"$baseUrl/charities-claims-validation/#{claimIdGiftAid}/create-upload-tracking"
  val createUploadTrackingUrlConnectedCharities =
    s"$baseUrl/charities-claims-validation/#{claimIdConnectedCharities}/create-upload-tracking"
  val createUploadTrackingUrlOtherIncome        =
    s"$baseUrl/charities-claims-validation/#{claimIdOtherIncome}/create-upload-tracking"
  val createUploadTrackingUrlCommunityBuildings =
    s"$baseUrl/charities-claims-validation/#{claimIdCommunityBuilding}/create-upload-tracking"

  val updateFileStatusUrlGiftAid            = s"$baseUrl/charities-claims-validation/#{claimIdGiftAid}/upload-results/#{reference}"
  val updateFileStatusUrlConnectedCharities =
    s"$baseUrl/charities-claims-validation/#{claimIdConnectedCharities}/upload-results/#{reference}"
  val updateFileStatusUrlOtherIncome        =
    s"$baseUrl/charities-claims-validation/#{claimIdOtherIncome}/upload-results/#{reference}"
  val updateFileStatusUrlCommunityBuildings =
    s"$baseUrl/charities-claims-validation/#{claimIdCommunityBuilding}/upload-results/#{reference}"

  val upscanCallbackUrlGiftAid: String            = s"$baseUrl/charities-claims-validation/#{claimIdGiftAid}/upscan-callback"
  val upscanCallbackUrlConnectedCharities: String =
    s"$baseUrl/charities-claims-validation/#{claimIdConnectedCharities}/upscan-callback"
  val upscanCallbackUrlOtherIncome: String        =
    s"$baseUrl/charities-claims-validation/#{claimIdOtherIncome}/upscan-callback"
  val upscanCallbackUrlCommunityBuildings: String =
    s"$baseUrl/charities-claims-validation/#{claimIdCommunityBuilding}/upscan-callback"

  val deleteSingleUploadUrlGiftAid            =
    s"$baseUrl/charities-claims-validation/#{claimIdGiftAid}/upload-results/#{reference}"
  val deleteSingleUploadUrlConnectedCharities =
    s"$baseUrl/charities-claims-validation/#{claimIdConnectedCharities}/upload-results/#{reference}"
  val deleteSingleUploadUrlOtherIncome        =
    s"$baseUrl/charities-claims-validation/#{claimIdOtherIncome}/upload-results/#{reference}"
  val deleteSingleUploadUrlCommunityBuildings =
    s"$baseUrl/charities-claims-validation/#{claimIdCommunityBuilding}/upload-results/#{reference}"

  val getUploadSummaryUrlGiftAid            = s"$baseUrl/charities-claims-validation/#{claimIdGiftAid}/upload-results/#{reference}"
  val getUploadSummaryUrlConnectedCharities =
    s"$baseUrl/charities-claims-validation/#{claimIdConnectedCharities}/upload-results/#{reference}"
  val getUploadSummaryUrlOtherIncome        =
    s"$baseUrl/charities-claims-validation/#{claimIdOtherIncome}/upload-results/#{reference}"
  val getUploadSummaryUrlCommunityBuildings =
    s"$baseUrl/charities-claims-validation/#{claimIdCommunityBuilding}/upload-results/#{reference}"

  def commonHeaders: Map[CharSequence, String] = Map(
    HttpHeaderNames.Authorization -> s"#{bearerToken}",
    HttpHeaderNames.ContentType   -> "application/json"
  )

  val createUploadTrackingGiftAidResults: HttpRequestBuilder =
    http("Gift Aid Create Upload Tracking")
      .post(createUploadTrackingUrlGiftAid)
      .headers(commonHeaders)
      .body(
        StringBody(
          """
          {
            |  "reference": "#{reference}",
            |  "validationType": "GiftAid",
            |  "uploadUrl": "https://xxxx/upscan-upload-proxy/bucketName",
            |  "initiateTimestamp":"#{initiateTimestamp}"
            |}
            |""".stripMargin
        )
      )
      .asJson
      .check(status.is(201))

  val createUploadTrackingOtherIncomeResults: HttpRequestBuilder =
    http("Other income Create Upload Tracking")
      .post(createUploadTrackingUrlOtherIncome)
      .headers(commonHeaders)
      .body(
        StringBody(
          """
          {
            |  "reference": "#{reference}",
            |  "validationType": "OtherIncome",
            |  "uploadUrl": "https://xxxx/upscan-upload-proxy/bucketName",
            |  "initiateTimestamp": "#{initiateTimestamp}"
            |}
            |""".stripMargin
        )
      )
      .asJson
      .check(status.is(201))

  val createUploadTrackingCommunityBuildingsResults: HttpRequestBuilder =
    http("Community Buildings Create Upload Tracking")
      .post(createUploadTrackingUrlCommunityBuildings)
      .headers(commonHeaders)
      .body(
        StringBody(
          """
          {
            |  "reference": "#{reference}",
            |  "validationType": "CommunityBuildings",
            |  "uploadUrl": "https://xxxx/upscan-upload-proxy/bucketName",
            |  "initiateTimestamp": "#{initiateTimestamp}"
            |}
            |""".stripMargin
        )
      )
      .asJson
      .check(status.is(201))

  val createUploadTrackingConnectedCharitiesResults: HttpRequestBuilder =
    http("Connected Charities Create Upload Tracking")
      .post(createUploadTrackingUrlConnectedCharities)
      .headers(commonHeaders)
      .body(
        StringBody(
          """
          {
            |  "reference": "#{reference}",
            |  "validationType": "ConnectedCharities",
            |  "uploadUrl": "https://xxxx/upscan-upload-proxy/bucketName",
            |  "initiateTimestamp": "#{initiateTimestamp}"
            |}
            |""".stripMargin
        )
      )
      .asJson
      // .check(status.in(201,500))
      .check(status.is(201))

  val updateFileStatusResultsGA: HttpRequestBuilder =
    http("Update File Status")
      .put(updateFileStatusUrlGiftAid)
      .headers(commonHeaders)
      .body(
        StringBody(
          """
          {
            |  "fileStatus": "VERIFYING"
            |}
            |""".stripMargin
        )
      )
      .asJson
      .check(
        status.is(200),
        jsonPath("$.success").is("true")
      )

  val updateFileStatusResultsCC: HttpRequestBuilder =
    http("Update File Status")
      .put(updateFileStatusUrlConnectedCharities)
      .headers(commonHeaders)
      .body(
        StringBody(
          """
          {
            |  "fileStatus": "VERIFYING"
            |}
            |""".stripMargin
        )
      )
      .asJson
      .check(
        status.is(200),
        jsonPath("$.success").is("true")
      )

  val updateFileStatusResultsOI: HttpRequestBuilder =
    http("Update File Status")
      .put(updateFileStatusUrlOtherIncome)
      .headers(commonHeaders)
      .body(
        StringBody(
          """
          {
            |  "fileStatus": "VERIFYING"
            |}
            |""".stripMargin
        )
      )
      .asJson
      .check(
        status.is(200),
        jsonPath("$.success").is("true")
      )

  val updateFileStatusResultsCB: HttpRequestBuilder =
    http("Update File Status")
      .put(updateFileStatusUrlCommunityBuildings)
      .headers(commonHeaders)
      .body(
        StringBody(
          """
          {
            |  "fileStatus": "VERIFYING"
            |}
            |""".stripMargin
        )
      )
      .asJson
      .check(
        status.is(200),
        jsonPath("$.success").is("true")
      )

  val upscanCallbackResultsGiftAid: HttpRequestBuilder =
    http("Gift Aid Upscan Callback")
      .post(upscanCallbackUrlGiftAid)
      .headers(commonHeaders) // reuse your existing headers
      .body(
        StringBody(
          """
          {
            |  "reference": "#{reference}",
            |  "downloadUrl": "#{downloadUrl}/gift-aid-schedule-GoodData.ods",
            |  "fileStatus": "READY",
            |  "uploadDetails": {
            |    "fileName": "gift-aid-schedule-GoodData.ods",
            |    "fileMimeType": "application/vnd.oasis.opendocument.spreadsheet",
            |    "uploadTimestamp": "#{uploadTimestamp}",
            |    "checksum": "#{Checksum}",
            |    "size": 1024
            |  }
            |}
          """.stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  val upscanCallbackResultsOtherIncome: HttpRequestBuilder =
    http("Other Income Upscan Callback")
      .post(upscanCallbackUrlOtherIncome)
      .headers(commonHeaders) // reuse your existing headers
      .body(
        StringBody(
          """
          {
            |  "reference": "#{reference}",
            |  "downloadUrl": "#{downloadUrl}/other_income_schedule-GoodData.ods",
            |  "fileStatus": "READY",
            |  "uploadDetails": {
            |    "fileName": "other_income_schedule-GoodData.ods",
            |    "fileMimeType": "application/vnd.oasis.opendocument.spreadsheet",
            |    "uploadTimestamp": "#{uploadTimestamp}",
            |    "checksum": "#{Checksum}",
            |    "size": 1024
            |  }
            |}
          """.stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  val upscanCallbackResultsCommunityBuildings: HttpRequestBuilder =
    http("Community Buildings Upscan Callback")
      .post(upscanCallbackUrlCommunityBuildings)
      .headers(commonHeaders) // reuse your existing headers
      .body(
        StringBody(
          """
          {
            |  "reference": "#{reference}",
            |  "downloadUrl": "#{downloadUrl}/community_buildings_excel-GoodData.ods",
            |  "fileStatus": "READY",
            |  "uploadDetails": {
            |    "fileName": "community_buildings_excel-GoodData.ods",
            |    "fileMimeType": "application/vnd.oasis.opendocument.spreadsheet",
            |    "uploadTimestamp": "#{uploadTimestamp}",
            |    "checksum": "#{Checksum}",
            |    "size": 1024
            |  }
            |}
          """.stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  val upscanCallbackResultsConnectedCharities: HttpRequestBuilder =
    http("Connected Charities Upscan Callback")
      .post(upscanCallbackUrlConnectedCharities)
      .headers(commonHeaders) // reuse your existing headers
      .body(
        StringBody(
          """
          {
            |  "reference": "#{reference}",
            |  "downloadUrl": "#{downloadUrl}/connected_charities_schedule_Excel_GoodData.ods",
            |  "fileStatus": "READY",
            |  "uploadDetails": {
            |    "fileName": "connected_charities_schedule_Excel_GoodData.ods",
            |    "fileMimeType": "application/vnd.oasis.opendocument.spreadsheet",
            |    "uploadTimestamp": "#{uploadTimestamp}",
            |    "checksum": "#{Checksum}",
            |    "size": 1024
            |  }
            |}
          """.stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  val deleteSingleClaimGA: HttpRequestBuilder =
    http("Delete the claim with reference")
      .delete(deleteSingleUploadUrlGiftAid)
      .headers(commonHeaders)
      .check(status.in(200, 201, 204))

  val deleteSingleClaimOI: HttpRequestBuilder =
    http("Delete the claim with reference")
      .delete(deleteSingleUploadUrlOtherIncome)
      .headers(commonHeaders)
      .check(status.in(200, 201, 204))

  val deleteSingleClaimCC: HttpRequestBuilder =
    http("Delete the claim with reference")
      .delete(deleteSingleUploadUrlConnectedCharities)
      .headers(commonHeaders)
      // .check(status.in(200,404))
      .check(status.in(200, 201, 204))

  val deleteSingleClaimCB: HttpRequestBuilder =
    http("Delete the claim with reference")
      .delete(deleteSingleUploadUrlCommunityBuildings)
      .headers(commonHeaders)
      .check(status.in(200, 201, 204))

  val GetUploadSummaryResultGA: HttpRequestBuilder =
    http("Get summary of Upload file")
      .get(getUploadSummaryUrlGiftAid)
      .headers(commonHeaders)
      .check(
        status.in(200, 201),
        jsonPath("$.fileStatus").in("VALIDATED", "VALIDATING", "VALIDATION_FAILED")
      )
  val GetUploadSummaryResultOI: HttpRequestBuilder =
    http("Get summary of Upload file")
      .get(getUploadSummaryUrlOtherIncome)
      .headers(commonHeaders)
      .check(
        status.in(200, 201),
        jsonPath("$.fileStatus").in("VALIDATED", "VALIDATING", "VALIDATION_FAILED")
      )

  val GetUploadSummaryResultCC: HttpRequestBuilder =
    http("Get summary of Upload file")
      .get(getUploadSummaryUrlConnectedCharities)
      .headers(commonHeaders)
      .check(
        status.in(200, 201),
        jsonPath("$.fileStatus").in("VALIDATED", "VALIDATING", "VALIDATION_FAILED")
      )

  val GetUploadSummaryResultCB: HttpRequestBuilder =
    http("Get summary of Upload file")
      .get(getUploadSummaryUrlCommunityBuildings)
      .headers(commonHeaders)
      .check(
        status.in(200, 201),
        jsonPath("$.fileStatus").in("VALIDATED", "VALIDATING", "VALIDATION_FAILED")
      )
}