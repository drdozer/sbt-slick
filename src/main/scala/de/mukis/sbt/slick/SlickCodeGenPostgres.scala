package de.mukis.sbt.slick

import de.mukis.sbt.slick.SlickCodeGenPlugin.autoImport._
import sbt._
import sbt.Keys._

/**
 * Generate Tables from MySql
 */
object SlickCodeGenPostgres extends AutoPlugin {

  override def requires = SlickCodeGenPlugin

  override lazy val projectSettings = Seq[Setting[_]](
    slickDriver := "slick.driver.PostgresDriver",
    slickJDBCDriver := "org.postgresql.Driver",
    slickPort := 5432,
    slickUrl := { database =>
      val db = database getOrElse ""
      s"jdbc:postgresql://localhost:${slickPort.value}/$db"
    },
    slickArguments := { database =>
      val driver = slickDriver.value
      val jdbc = slickJDBCDriver.value
      val url = slickUrl.value
      val outputDir = (sourceManaged in Compile).value.getPath
      val rootPackage = slickPackage.value
      val user = slickUser.value getOrElse sys.error("No user supplied")
      val pass = slickPassword.value getOrElse sys.error("No password supplied")

      Array(driver, jdbc, url(database), outputDir, rootPackage, user, pass)
    }
  )
}
