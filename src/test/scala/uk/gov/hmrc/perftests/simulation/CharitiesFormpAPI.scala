package uk.gov.hmrc.perftests.simulation

import io.gatling.core.action.builder.ActionBuilder
import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.AuthLogin.AuthLoginRequests._
import uk.gov.hmrc.perftests.BaseRequests
import uk.gov.hmrc.perftests.charities.CharitiesFormpRequests.{getUnregulatedDonationsRef1, getUnregulatedDonationsRef2, getUnregulatedDonationsRef3, postUnregulatedDonationsRef1, postUnregulatedDonationsRef2, postUnregulatedDonationsRef3}

trait CharitiesFormpAPI extends PerformanceTestRunner with BaseRequests {
  val charitiesFormP: List[ActionBuilder] =
    getAuthToken(authPayloadCharitiesOrg) ++
    List[ActionBuilder](
      getUnregulatedDonationsRef1,
      postUnregulatedDonationsRef1,
      getUnregulatedDonationsRef2,
      postUnregulatedDonationsRef2,
      getUnregulatedDonationsRef3,
      postUnregulatedDonationsRef3
      )

  setup(
    "charities-get-post-formp",
    "Charities GET and POST unregulated Donations Formp APIs"
  ) withActions (
    charitiesFormP: _*
    )
}