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
import uk.gov.hmrc.perftests.charities.CharitiesFormpRequests.{getUnregulatedDonationsRef1, getUnregulatedDonationsRef2, getUnregulatedDonationsRef3, postUnregulatedDonationsRandom, postUnregulatedDonationsRef1, postUnregulatedDonationsRef2, postUnregulatedDonationsRef3}

trait CharitiesFormpAPI extends PerformanceTestRunner with BaseRequests {
  val charitiesFormP: List[ActionBuilder] =
    getAuthToken(authPayloadCharitiesOrg) ++
      List[ActionBuilder](
        postUnregulatedDonationsRandom,
        getUnregulatedDonationsRef1,
        postUnregulatedDonationsRandom,
        getUnregulatedDonationsRef2,
        postUnregulatedDonationsRandom,
        getUnregulatedDonationsRef3
      )

  val charitiesFormPinserts: List[ActionBuilder] =
    getAuthToken(authPayloadCharitiesOrg) ++
      List[ActionBuilder](
        postUnregulatedDonationsRef1,
        postUnregulatedDonationsRef2,
        postUnregulatedDonationsRef3
      )

  setup(
    "charities-get-post-formp",
    "Charities GET and POST unregulated Donations Formp APIs"
  ) withActions (
    charitiesFormP: _*
  )

  setup(
    "charities-static-chref-insert",
    "Charities POST FORMP for 3 Static Charity references"
  ) withActions (
    charitiesFormPinserts: _*
  )
}
