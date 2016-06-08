package de.mukis.sbt.slick

import sbt._
import SlickCodeGenPlugin.autoImport._
import sbt.Keys._


/**
 * Generate Tables from MySql
 */
object SlickCodeGenMySql extends AutoPlugin {

  override def requires = SlickCodeGenPlugin

  override lazy val projectSettings = Seq[Setting[_]](
    slickDriver := "slick.driver.MySQLDriver",
    slickJDBCDriver := "com.mysql.jdbc.Driver",
    slickPort := 3306,
    slickUrl := { database =>
      val db = database getOrElse ""
      val user = slickUser.value map (u => s"user=$u")
      val pass = slickPassword.value map (p => s"password=$p")
      val paramsList = List(user, pass).flatten
      val params = if (paramsList.isEmpty) "" else "?" + (paramsList mkString "&")
      s"jdbc:mysql://localhost:${slickPort.value}/$db$params"
    },
    slickCallForm := CallForm.WithoutCredentials
  )
}
