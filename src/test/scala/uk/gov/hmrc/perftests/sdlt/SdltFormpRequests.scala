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

package uk.gov.hmrc.perftests.sdlt

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.BaseRequests

import scala.util.Random

object SdltFormpRequests extends ServicesConfiguration with BaseRequests {

  val postReturns           = s"$formpUrl/create/return"
  val getReturns            = s"$formpUrl/retrieve-return"
  val createVendor          = s"$formpUrl/filing/create/vendor"
  val createPurchaser       = s"$formpUrl/filing/create/purchaser"
  val createLand            = s"$formpUrl/filing/create/land"
  val createResidency       = s"$formpUrl/filing/create/residency"
  val createLease           = s"$formpUrl/filing/create/lease"
  val updateTransaction     = s"$formpUrl/filing/update/transaction"
  val updateTaxCalc         = s"$formpUrl/filing/update/tax-calculation"

  def commonHeaders: Map[CharSequence, String] = Map(
    HttpHeaderNames.Authorization -> s"#{bearerToken}",
    HttpHeaderNames.ContentType   -> "application/json",
    "X-Session-ID"                -> "693b2579c9ae70489252dba5"
  )

  val stornId = "STORN12345"
  val returnResourceRef = "1"
  val userIdentifier = s"USER${Random.nextLong(999999999L)}"
  val formResultId = s"FRID-${Random.nextLong(999999999L)}"
  val correlationId = s"CORR-${Random.nextLong(999999999L)}"

  val postSdltReturns: HttpRequestBuilder =
    http("POST returns for SDLT")
      .post { _ =>
        val requestUrl = postReturns
        requestUrl
      }
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
             |"stornId": "$stornId",
             |"purchaserIsCompany": "YES",
             |"surNameOrCompanyName": "ABC Property Ltd",
             |"houseNumber": 100,
             |"addressLine1": "Business Park",
             |"transactionType": "NON_RESIDENTIAL"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(201))

  val getSDLTReturns: HttpRequestBuilder =
    http("GET returns for SDLT")
      .post { _ =>
        val requestUrl = getReturns
        requestUrl
      }
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
             |"storn": "$stornId",
             |"returnResourceRef": "$returnResourceRef"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200),
        jsonPath("$.returnResourceRef").exists
      )

  val postCreateVendor: HttpRequestBuilder =
    http("POST create vendor for SDLT")
      .post { _ =>
        val requestUrl = createVendor
        requestUrl
      }
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
             |"stornId": "$stornId",
             |"returnResourceRef": "$returnResourceRef",
             |"name": "Company Vendor Ltd",
             |"addressLine1": "VIT Park",
             |"isRepresentedByAgent": "yes"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(201))

  val postCreatePurchaser: HttpRequestBuilder =
    http("POST create purchaser for SDLT")
      .post { _ =>
        val requestUrl = createPurchaser
        requestUrl
      }
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
             |"stornId": "$stornId",
             |"returnResourceRef": "$returnResourceRef",
             |"isCompany":"YES",
             |"isTrustee":"NO",
             |"isConnectedToVendor":"NO",
             |"isRepresentedByAgent": "NO",
             |"companyName": "Tech Corp Ltd",
             |"address1": "Business Park",
             |"postcode": "EC1A 1BB"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(201))

  val postCreateLand: HttpRequestBuilder =
    http("POST create land for SDLT")
      .post { _ =>
        val requestUrl = createLand
        requestUrl
      }
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
             |"stornId": "$stornId",
             |"returnResourceRef": "$returnResourceRef",
             |"propertyType": "RESIDENTIAL",
             |"interestTransferredCreated":"FREEHOLD",
             |"addressLine1":"Business Park"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(201))

  val postCreateResidency: HttpRequestBuilder =
    http("POST create residency for SDLT")
      .post { _ =>
        val requestUrl = createResidency
        requestUrl
      }
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
             |"stornId": "$stornId",
             |"returnResourceRef": "$returnResourceRef",
             |"residency" : {
             |          "isNonUkResidents" : "no",
             |          "isCompany" : "no",
             |          "isCrownRelief" : "no"
             |          }
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(201))

  val postCreateLease: HttpRequestBuilder =
    http("POST create lease for SDLT")
      .post { _ =>
        val requestUrl = createLease
        requestUrl
      }
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
             |"stornId": "$stornId",
             |"returnResourceRef": "$returnResourceRef",
             |"lease" : {
             |          "isAnnualRentOver1000" : "YES",
             |          "contractEndDate" : "2030-12-31",
             |          "contractStartDate" : "2025-01-01",
             |          "leaseType" : "COMMERCIAL",
             |          "netPresentValue" : "50000",
             |          "totalPremiumPayable" : "10000",
             |          "rentFreePeriod" : "NO",
             |          "startingRent": "12000",
             |          "startingRentEndDate" : "2026-01-01",
             |          "laterRentKnown" : "YES",
             |          "vatAmount" : "2400"
             |          }
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(201))

  val postUpdateTransaction: HttpRequestBuilder =
    http("POST update transaction for SDLT")
      .post { _ =>
        val requestUrl = updateTransaction
        requestUrl
      }
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
             |"storn": "$stornId",
             |"returnResourceRef": "$returnResourceRef",
             |"transaction" : {
             |          "claimingRelief" : "N",
             |          "isLinked" : "N",
             |          "totalConsider" : "250000",
             |          "considerCash" : "Y",
             |          "contractDate" : "2025-01-15",
             |          "effectiveDate" : "2025-02-01",
             |          "transactionDescription" : "RESIDENTIAL"
             |          }
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

  val postUpdateTaxCalc: HttpRequestBuilder =
    http("POST update tax calc for SDLT")
      .post { _ =>
        val requestUrl = updateTaxCalc
        requestUrl
      }
      .headers(commonHeaders)
      .body(
        StringBody(
          s"""{
             |"stornId": "$stornId",
             |"returnResourceRef": "$returnResourceRef"
             |}""".stripMargin
        )
      )
      .asJson
      .check(status.is(200))

}
