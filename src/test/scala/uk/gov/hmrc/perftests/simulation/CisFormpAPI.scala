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

package uk.gov.hmrc.perftests.simulation

import io.gatling.core.action.builder.ActionBuilder
import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.AuthLogin.AuthLoginRequests._
import uk.gov.hmrc.perftests.BaseRequests
import uk.gov.hmrc.perftests.cis.CisFormpRequests._

trait CisFormpAPI extends PerformanceTestRunner with BaseRequests {
  val cisMonthlyReturnsFormP: List[ActionBuilder] =
    getAuthToken(() => authPayloadCisOrg) ++
      List[ActionBuilder](
        getAllMonthlyReturns,
        getUnsubmittedMonthlyReturns,
        getSubmittedMonthlyReturns,
        postUpdateMonthlyReturn,
        getMonthlyReturnForEdit,
        getCompleteMonthlyReturnData,
        getSubmittedMonthlyReturnData,
        postUpdateSubmission
      )

  val cisSchemeFormP: List[ActionBuilder] =
    getAuthToken(() => authPayloadCisOrg) ++
      List[ActionBuilder](
        getSchemeEmail,
        getSchemeInfo("872"),
        postUpdateScheme,
        getBatchPollSubmissions
      )

  val cisSubcontractorFormP: List[ActionBuilder] =
    getAuthToken(() => authPayloadCisOrg) ++
      List[ActionBuilder](
        postCreateSubcontractor,
        getSubcontractorList("872")
      )

  val cisGovTalkFormP: List[ActionBuilder] =
    getAuthToken(() => authPayloadCisOrg) ++
      List[ActionBuilder](
        getGovTalkStatus,
        postUpdateGovTalkCorrelationId,
        postUpdateGovTalkStatus,
        postUpdateGovTalkStatistics
      )

  val cisVerificationFormP: List[ActionBuilder] =
    getAuthToken(() => authPayloadCisOrg) ++
      List[ActionBuilder](
        getNewestVerificationBatch("872"),
        getCurrentVerificationBatch("872"),
        getLastVerificationBatch("872"),
        postUpdateSubmissionFromChrisResponse,
        getSubmissionAndVerificationBatchRecord,
        postProceedVerification,
        postUpdateVerificationSubmission,
        getSubmissionWithVerificationBatchByRefs("872", "2")
      )

  setup(
    "cis-monthly-return",
    "CIS monthly returns GET & POST requests"
  ) withActions (
    cisMonthlyReturnsFormP: _*
  )

  setup(
    "cis-contractor-scheme",
    "CIS scheme GET & POST requests"
  ) withActions (
    cisSchemeFormP: _*
  )

  setup(
    "cis-subcontractor",
    "CIS subcontractor GET & POST requests"
  ) withActions (
    cisSubcontractorFormP: _*
  )

  setup(
    "cis-govtalk",
    "CIS govtalk GET & POST requests"
  ) withActions (
    cisGovTalkFormP: _*
  )

  setup(
    "cis-verifications",
    "CIS verifications GET & POST requests"
  ) withActions (
    cisVerificationFormP: _*
  )
}
