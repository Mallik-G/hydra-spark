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

import sbt._

object ExclusionRules {
  val excludeCglib = ExclusionRule(organization = "org.sonatype.sisu.inject")
  val excludeScalaTest = ExclusionRule(organization = "org.scalatest")
  val excludeScala = ExclusionRule(organization = "org.scala-lang")
  val excludeNettyIo = ExclusionRule("io.netty", "netty-all")
  val excludeNetty = ExclusionRule("org.jboss.netty", "netty")
  val excludeAsm = ExclusionRule(organization = "asm")
  val excludeQQ = ExclusionRule(organization = "org.scalamacros")
  val excludeKryo = ExclusionRule(organization = "com.esotericsoftware.kryo")

  val sparkExcludes = excludeNetty
}

