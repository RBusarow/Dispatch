package util.psi.visitor

import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid
import kotlin.properties.Delegates

abstract class KotlinVisitor<T> : KtTreeVisitorVoid() {

  protected var root: KtFile by Delegates.notNull()
    private set

  abstract val properties: T

  fun parse(file: KtFile): T {
    root = file
    file.accept(this)
    return properties
  }
}
