/*
 * Copyright 2023 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.simulation

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner

class CharitiesFormpSimulation
  extends PerformanceTestRunner
    with CharitiesFormpAPI {
  runSimulation()

}
