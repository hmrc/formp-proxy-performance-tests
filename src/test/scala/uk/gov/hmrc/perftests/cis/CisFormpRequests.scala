/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.perftests.cis

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.BaseRequests

import scala.util.Random

object CisFormpRequests extends ServicesConfiguration with BaseRequests {

  // Monthly Return URLs
  val getMonthlyReturnsUrl                        = s"$formpUrl/monthly-returns"
  val getUnsubmittedReturnsUrl                    = s"$formpUrl/cis/retrieve-unsubmitted-monthly-returns"
  val getSubmittedReturnsUrl                      = s"$formpUrl/cis-retrieve-submitted-monthly-returns"
  val postUpdateMonthlyReturnUrl                  = s"$formpUrl/cis/monthly-return/update"
  val getMonthlyReturnForEditUrl                  = s"$formpUrl/cis/monthly-return-edit"
  val getCompleteMonthlyReturnUrl                 = s"$formpUrl/cis/monthly-return-complete"
  val getSubmittedMonthlyReturnsDataUrl           = s"$formpUrl/cis/retrieve-submitted-monthly-returns-data"
  // Scheme URLs
  val getSchemeEmailUrl                           = s"$formpUrl/scheme/email"
  val getSchemeInfoUrl                            = s"$formpUrl/scheme"
  val postUpdateSchemeUrl                         = s"$formpUrl/scheme/update"
  // Subcontractor URLs
  val postCreateSubcontractorUrl                  = s"$formpUrl/cis/subcontractor/create-and-update"
  val getSubcontractorListUrl                     = s"$formpUrl/cis/subcontractors"
  // Submission URLs
  val postUpdateSubmissionUrl                     = s"$formpUrl/submissions/update" // Monthly Return
  val getBatchPollSubmissionsUrl                  = s"$formpUrl/cis/batchpoll-submissions" // Batch poll
  // GovTalk URLs
  val getGovTalkStatusUrl                         = s"$formpUrl/cis/govtalkstatus/get"
  val postUpdateGovTalkCorrelationIdUrl           = s"$formpUrl/cis/govtalkstatus/update-correlationID"
  val postUpdateGovTalkStatusUrl                  = s"$formpUrl/cis/govtalkstatus/update-status"
  val postUpdateGovTalkStatisticsUrl              = s"$formpUrl/cis/govtalkstatus/update-statistics"
  // Verification URLs
  val getNewestVerificationBatchUrl               = s"$formpUrl/cis/verification-batch/newest"
  val getCurrentVerificationBatchUrl              = s"$formpUrl/cis/verification-batch/current"
  val getLastVerificationBatchUrl                 = s"$formpUrl/cis/verification-batch/last"
  val postUpdateSubmissionFromChrisResponseUrl    = s"$formpUrl/cis/verification/response/process"
  val getSubmissionAndVerificationBatchRecordUrl  = s"$formpUrl/cis/verification/submission-batch"
  val postProceedVerificationUrl                  = s"$formpUrl/cis/verification/proceed"
  val getSubmittedVerificationsUrl                = s"$formpUrl/cis/verification/submitted-verifications"
  val postUpdateVerificationSubmissionUrl         = s"$formpUrl/cis/verification/submission/update"
  val getSubmissionWithVerificationBatchByRefsUrl = s"$formpUrl/cis/verification/submission-batch"

  def commonHeaders: Map[CharSequence, String] = Map(
    HttpHeaderNames.Authorization -> s"#{bearerToken}",
    HttpHeaderNames.ContentType   -> "application/json",
    "X-Session-ID"                -> "693b2579c9ae70489252dba5"
  )

  private def generateValidUtr: String = {

    val digits      = Array.fill(9)(Random.nextInt(10))
    val utrWeights  = Array(6, 7, 8, 9, 10, 5, 4, 3, 2)
    val total       = digits.zip(utrWeights).map { case (d, w) => d * w }.sum
    var checkNumber = 11 - (total % 11)
    if (checkNumber > 9) checkNumber -= 9

    val utr = checkNumber.toString + digits.mkString

    utr
  }

  val subcontractorType: List[String]               = List("soletrader", "company", "partnership", "trust")
  private def selectRandomSubcontractorType: String = subcontractorType(Random.nextInt(subcontractorType.length))
  val randomPhoneNumber: String                     = "0" + List.fill(10)(Random.nextInt(10)).mkString

  val submissionStatus: List[String]               = List(
    "SUBMITTED",
    "SUBMITTED_NO_RECEIPT",
    "DEPARTMENTAL_ERROR",
    "FATAL_ERROR",
    "STARTED",
    "VALIDATED",
    "PENDING",
    "ACCEPTED"
  )
  private def selectRandomSubmissionStatus: String = submissionStatus(Random.nextInt(submissionStatus.length))

  val govTalkGatewayUrl = "https://chris.ws.ibt.hmrc.gov.uk/ChRIS/CISR/Filing/action/CISR"

  val getAllMonthlyReturns: HttpRequestBuilder =
    http("GET All monthly returns")
      .post(getMonthlyReturnsUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
            |  "instanceId": "872"
            |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  val getUnsubmittedMonthlyReturns: HttpRequestBuilder =
    http("GET Unsubmitted monthly returns")
      .post(getUnsubmittedReturnsUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
            |  "instanceId": "872"
            |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  val getSubmittedMonthlyReturns: HttpRequestBuilder =
    http("GET Submitted monthly returns")
      .post(getUnsubmittedReturnsUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
            |  "instanceId": "872"
            |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  val postUpdateMonthlyReturn: HttpRequestBuilder =
    http("POST Update monthly return")
      .post(postUpdateMonthlyReturnUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
            |  "instanceId": "872",
            |  "taxYear": 2026,
            |  "taxMonth": 10,
            |  "amendment": "N",
            |  "decInformationCorrect": "Y",
            |  "decNilReturnNoPayments": "Y",
            |  "nilReturnIndicator": "Y",
            |  "status": "STARTED",
            |  "version": 1
            |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  val getMonthlyReturnForEdit: HttpRequestBuilder =
    http("GET monthly return for edit")
      .post(getMonthlyReturnForEditUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
            |  "instanceId": "872",
            |  "taxYear": 2026,
            |  "taxMonth": 10
            |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  val getCompleteMonthlyReturnData: HttpRequestBuilder =
    http("GET Complete monthly return data")
      .post(getCompleteMonthlyReturnUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
            |  "instanceId": "872",
            |  "taxYear": 2026,
            |  "taxMonth": 10,
            |  "amendment": "N"
            |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  val getSubmittedMonthlyReturnData: HttpRequestBuilder =
    http("GET Submitted monthly return data")
      .post(getSubmittedMonthlyReturnsDataUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
            |  "instanceId": "872",
            |  "taxYear": 2026,
            |  "taxMonth": 10,
            |  "amendment": "N"
            |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  val getSchemeEmail: HttpRequestBuilder =
    http("GET Scheme email")
      .post(getSchemeEmailUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
            |  "instanceId": "872"
            |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  def getSchemeInfo(instanceID: String): HttpRequestBuilder =
    http("GET Scheme info")
      .get(getSchemeInfoUrl + s"/$instanceID")
      .headers(commonHeaders)
      .check(status.is(200))

  val postUpdateScheme: HttpRequestBuilder = {
    val utr = generateValidUtr
    http("POST Update scheme")
      .post(postUpdateSchemeUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
            |  "schemeId": 10872,
            |  "instanceId": "872",
            |  "accountsOfficeReference" : "872PA87654872",
            |  "taxOfficeNumber" : "872",
            |  "taxOfficeReference" : "EZ10872",
            |  "utr" : "$utr",
            |  "name" : "Test Scheme Name",
            |  "emailAddress" : "testemail@test.com",
            |  "displayWelcomePage" : "Y",
            |  "prePopCount" : 0,
            |  "prePopSuccessful": "N",
            |  "version":388
            |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))
  }

  val postCreateSubcontractor: HttpRequestBuilder =
    http("POST create and update subcontractor")
      .post(postCreateSubcontractorUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
         |  "cisId": "872",
         |  "subcontractorType": "$selectRandomSubcontractorType",
         |  "utr": "$generateValidUtr",
         |  "nino": "AA123456A",
         |  "firstName": "John",
         |  "secondName": "Q",
         |  "surname": "Smith",
         |  "tradingName": "ACME",
         |  "addressLine1": "1 Main Street",
         |  "city": "London",
         |  "county": "Greater London",
         |  "country": "United Kingdom",
         |  "postcode": "AA1 1AA",
         |  "phoneNumber": "$randomPhoneNumber",
         |  "mobilePhoneNumber": "$randomPhoneNumber",
         |  "worksReferenceNumber": "34567"
         |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  def getSubcontractorList(cisId: String): HttpRequestBuilder =
    http("GET Subcontractor list")
      .get(getSubcontractorListUrl + s"/$cisId")
      .headers(commonHeaders)
      .check(status.is(200))

  val postUpdateSubmission: HttpRequestBuilder =
    http("POST update monthly return submission")
      .post(postUpdateSubmissionUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
             |  "instanceId": "872",
             |  "taxYear": 2026,
             |  "taxMonth": 10,
             |  "amendment": "N",
             |  "hmrcMarkGenerated": "Dj5TVJDyRYCn9zta5EdySeY4fyA=",
             |  "submittableStatus": "$selectRandomSubmissionStatus"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  val getGovTalkStatus: HttpRequestBuilder =
    http("GET GovTalk_Status Info")
      .post(getGovTalkStatusUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
             |  "userIdentifier": "872",
             |  "formResultID": "471618537"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  val postUpdateGovTalkCorrelationId: HttpRequestBuilder =
    http("POST Update GovTalk correlation id")
      .post(postUpdateGovTalkCorrelationIdUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
             |  "userIdentifier": "872",
             |  "formResultID": "471618537",
             |  "correlationID": "C742D5DEE7EB4D15B4F7EFD50B890525",
             |  "pollInterval": 5,
             |  "gatewayURL": "$govTalkGatewayUrl"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  val postUpdateGovTalkStatus: HttpRequestBuilder =
    http("POST Update GovTalk status")
      .post(postUpdateGovTalkStatusUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
             |  "userIdentifier": "872",
             |  "formResultID": "471618537",
             |  "endStateDate": "2026-02-03T00:00:00",
             |  "protocolStatus": "dataRequest"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  val postUpdateGovTalkStatistics: HttpRequestBuilder =
    http("POST Update GovTalk statistics")
      .post(postUpdateGovTalkStatisticsUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
             |  "userIdentifier": "872",
             |  "formResultID": "471618537",
             |  "correlationID": "C742D5DEE7EB4D15B4F7EFD50B890525",
             |  "lastMessageDate": "2026-10-12T14:30:18",
             |  "numPolls": 0,
             |  "pollInterval": 0,
             |  "gatewayURL": "$govTalkGatewayUrl"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  def getNewestVerificationBatch(instanceId: String): HttpRequestBuilder =
    http("GET Newest Verification Batch")
      .get(getNewestVerificationBatchUrl + s"/$instanceId")
      .headers(commonHeaders)
      .check(status.is(200))

  def getCurrentVerificationBatch(instanceId: String): HttpRequestBuilder =
    http("GET Current Verification Batch")
      .get(getCurrentVerificationBatchUrl + s"/$instanceId")
      .headers(commonHeaders)
      .check(status.is(200))

  def getLastVerificationBatch(instanceId: String): HttpRequestBuilder =
    http("GET Last Verification Batch")
      .get(getLastVerificationBatchUrl + s"/$instanceId")
      .headers(commonHeaders)
      .check(status.is(200))

  val postUpdateSubmissionFromChrisResponse: HttpRequestBuilder =
    http("POST Update submission from ChRIS response")
      .post(postUpdateSubmissionFromChrisResponseUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
             |  "instanceId": "872",
             |  "verificationBatchResourceRef": 2,
             |  "acceptedTime": "2026-10-11T13:11:54",
             |  "submissionStatus": "SUBMITTED",
             |  "irMarkReceived": "v6fHrpPRtaCFPQgBdx/dCEC9O1s=",
             |  "verificationResults": [
             |    {
             |      "resourceRef": 3,
             |      "matched": "Y",
             |      "verified": "Y",
             |      "verificationNumber": "V1234567890",
             |      "taxTreatment": "gross",
             |      "verifiedDate": "2026-10-11T13:11:53"
             |    },
             |      {
             |      "resourceRef": 4,
             |      "matched": "Y",
             |      "verified": "Y",
             |      "verificationNumber": "V1234567890",
             |      "taxTreatment": "gross",
             |      "verifiedDate": "2026-10-11T13:11:53"
             |    }
             |  ]
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  val getSubmissionAndVerificationBatchRecord: HttpRequestBuilder =
    http("GET Submission and verification batch record")
      .post(getSubmissionAndVerificationBatchRecordUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
             |  "instanceId": "872",
             |  "verificationBatchResourceRef": 2
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  val postProceedVerification: HttpRequestBuilder =
    http("POST Proceed verification")
      .post(postProceedVerificationUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
             |  "instanceId": "872",
             |  "verificationBatchResourceRef": 2,
             |  "verificationResourceRef": 3,
             |  "proceed": true,
             |  "taxTreatment": "gross"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  val getSubmittedVerifications: HttpRequestBuilder =
    http("GET Submitted verifications")
      .post(getSubmittedVerificationsUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
             |  "instanceId": "872"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  val postUpdateVerificationSubmission: HttpRequestBuilder =
    http("POST Update verification submission")
      .post(postUpdateVerificationSubmissionUrl)
      .headers(commonHeaders)
      .body(
        StringBody(
          """{
             |  "instanceId": "872",
             |  "verificationBatchResourceRef": 2,
             |  "hmrcMarkGenerated": null,
             |  "submissionRequestDate": null,
             |  "submittableStatus": "FATAL_ERROR",
             |  "govtalkErrorCode": "500",
             |  "govtalkErrorType": "timeOut"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(204))

  def getSubmissionWithVerificationBatchByRefs(instanceId: String, verificationBatchRef: String): HttpRequestBuilder =
    http("GET Submission with verification batch ref")
      .get(getSubmissionWithVerificationBatchByRefsUrl + s"/$instanceId/$verificationBatchRef")
      .headers(commonHeaders)
      .check(status.is(200))

  val getBatchPollSubmissions: HttpRequestBuilder =
    http("GET All batch poll submissions")
      .get(getBatchPollSubmissionsUrl)
      .headers(commonHeaders)
      .check(status.is(200))
}
