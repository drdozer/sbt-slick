package de.mukis.sbt.slick

import sbt._
import SlickCodeGenPlugin.autoImport._
import sbt.Keys._

/**
 * Generate Tables from H2
 */
object SlickCodeGenH2 extends AutoPlugin {

  override def requires = SlickCodeGenPlugin

  override lazy val projectSettings = Seq[Setting[_]](
    slickDriver := "slick.driver.H2Driver",
    slickJDBCDriver := "org.h2.Driver",
    slickUrl := { db =>
      s"jdbc:h2:mem${db.map(":" + _) getOrElse ""}"
    },
    slickArguments := { database =>
      val driver = slickDriver.value
      val jdbc = slickJDBCDriver.value
      val url = slickUrl.value
      val outputDir = (sourceManaged in Compile).value.getPath
      val rootPackage = slickPackage.value

      Array(driver, jdbc, url(database), outputDir, rootPackage)
    }
  )
}
