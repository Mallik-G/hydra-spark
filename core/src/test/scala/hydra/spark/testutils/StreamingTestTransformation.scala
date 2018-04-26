/*
 * Copyright (C) 2017 Pluralsight, LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hydra.spark.testutils

import com.typesafe.config.ConfigFactory
import hydra.spark.api._
import hydra.spark.transform.SparkStreamingTransformation

import scala.reflect.runtime.universe._

/**
  * Created by alexsilva on 1/3/17.
  */
case class StreamingTestTransformation[S: TypeTag](source: Source[S], operations: Seq[DFOperation])
  extends Transformation[S] {

  val dsl = ConfigFactory.parseString(
    """
      |spark.master = "local[1]"
      |spark.default.parallelism	= 1
      |spark.ui.enabled = false
      |spark.driver.allowMultipleContexts = false
      |streaming.interval = 1s
      |spark.checkpoint = false
    """.stripMargin
  )

  val dispatch = SparkStreamingTransformation("test", source, operations, dsl)

  def run() = dispatch.run()

  val author = "tester"

  override def validate: ValidationResult = Valid

  override def stop(): Unit = dispatch.stop()

}