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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implie
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hydra.spark.api

import com.typesafe.config.Config
import org.apache.spark.SparkConf

/**
  * Contains all the information needed to run a dispatch, but without
  * a context associated with it.
  *
  * @param name        The unique name for this dispatch job.
  * @param source      The materialized source
  * @param operations  The list of operations
  * @param isStreaming Whether or not this is a streaming dispatch
  * @param dsl         The underlying DSL converted to a resolved Typesafe config format
  *
  *                    Created by alexsilva on 1/3/17.
  */
case class DispatchDetails[S](name: String, source: Source[S], operations: Seq[DFOperation], isStreaming: Boolean,
                              dsl: Config, sparkDefaults: Config) {

  lazy val sparkConf: SparkConf = {
    import configs.syntax._
    import hydra.spark.configs._
    val sparkDslConf = dsl.flattenAtKey("spark")
    val sparkConf = sparkDslConf ++ sparkDefaults.flattenAtKey("spark")
    val jars = dsl.get[List[String]]("spark.jars").valueOrElse(List.empty)
    val appName = sparkDslConf.get("spark.app.name").getOrElse(name)

    new SparkConf().setAll(sparkConf)
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .registerKryoClasses(Array(classOf[org.apache.avro.generic.GenericData.Record]))
      .setAppName(appName).setJars(jars)
  }
}
