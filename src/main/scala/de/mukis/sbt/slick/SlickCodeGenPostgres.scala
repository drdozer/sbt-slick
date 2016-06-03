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
    slickDriver := "slick.driver.PostgresDrive",
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
      val user = slickUser.value map (u => s"user=$u")
      val pass = slickPassword.value map (p => s"password=$p")

      Array(driver, jdbc, url(database), outputDir, rootPackage) ++ user ++ pass
    }
  )
}
