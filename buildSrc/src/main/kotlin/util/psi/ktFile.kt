@file:Suppress("TooManyFunctions")

package util.psi

import org.jetbrains.kotlin.com.intellij.openapi.util.Key
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtImportDirective
import java.io.File
import java.io.FileNotFoundException

val RELATIVE_PATH: Key<String> = Key("relativePath")
val ABSOLUTE_PATH: Key<String> = Key("absolutePath")

fun File.asKtFile(): KtFile =
  (psiFileFactory.createFileFromText(name, KotlinLanguage.INSTANCE, readText()) as? KtFile)?.apply {
    putUserData(ABSOLUTE_PATH, this@asKtFile.absolutePath)
  } ?: throw FileNotFoundException("could not find file $this")

fun KtFile.asFile(): File = File(absolutePath())

fun KtFile.absolutePath(): String =
  getUserData(ABSOLUTE_PATH) ?: error("KtFile '$name' expected to have an absolute path.")

fun KtFile.relativePath(): String = getUserData(RELATIVE_PATH)
  ?: error("KtFile '$name' expected to have an relative path.")

fun KtFile.replaceClass(oldClass: KtClass, newClass: KtClass): KtFile {

  val newText = text.replace(oldClass.text, newClass.text)

  val path = absolutePath()

  return (psiFileFactory.createFileFromText(name, KotlinLanguage.INSTANCE, newText) as KtFile).apply {
    putUserData(ABSOLUTE_PATH, path)
  }
}

fun KtFile.write(path: String = absolutePath()) {

  val javaFile = File(path)

  javaFile.mkdirs()
  javaFile.writeText(text)
}
