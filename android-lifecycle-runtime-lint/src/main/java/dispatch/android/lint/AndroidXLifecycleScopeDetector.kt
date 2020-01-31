/*
 * Copyright (C) 2020 Rick Busarow
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dispatch.android.lint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.*
import org.jetbrains.uast.*

val ISSUE_ANDROID_X_LIFECYCLE_SCOPE = Issue.create(
  id = "AndroidXLifecycleScopeUsage",
  briefDescription = "Usage of the AndroidX version of lifecycleScope",
  explanation = """This check ensures that the Dispatch version of lifecycleScope \
                isn't shadowed by that from androidx-lifecycle-runtime-ktx.

                Use of the AndroidX variant will not benefit from DispatcherProvider, \
                and may result in unpredictable behavior when using a custom CoroutineScope factory.
                
                It is recommended to simply remove any use of androidx-lifecycle-runtime-ktx \
                from your build.gradle.""",
  category = Category.USABILITY,
  severity = Severity.WARNING,
  implementation = Implementation(
    AndroidXLifecycleScopeDetector::class.java,
    Scope.JAVA_FILE_SCOPE
  ),
  androidSpecific = true
)

class AndroidXLifecycleScopeDetector : Detector(), Detector.UastScanner {

  override fun getApplicableReferenceNames() = listOf("lifecycleScope", "foo", "launchWhenResumed")
  //  override fun getApplicableCallNames() = listOf("lifecycleScope", "foo")
  override fun getApplicableMethodNames() = listOf("lifecycleScope", "foo", "launchWhenResumed")

  override fun visitReference(
    context: JavaContext,
    reference: UReferenceExpression,
    referenced: PsiElement
  ) {

    val evaluator = context.evaluator

    val isInSchedulers = reference.resolvedName == "dispatch.android.lifecycleScope"

    val isSchedulersMatch = (isInSchedulers || true)

    if ((isSchedulersMatch)) {

      context.report(
        ISSUE_ANDROID_X_LIFECYCLE_SCOPE,
        referenced,
        context.getCallLocation(reference.getUCallExpression(100)!!, true, true),
        "${reference.getQualifiedName()}"
      )
    }
  }

  fun ddddddafasdfasdfasdf() {

  }

  override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {

    val evaluator = context.evaluator

    val isInSchedulers =
      evaluator.isMemberInClass(method, "androidx.lifecycle.LifecycleCoroutineScope")

    val isSchedulersMatch =
      listOf("getLifecycleScope", "foo").contains(node.methodName) && (isInSchedulers || true)
//    val isAndroidSchedulersMatch =
//      androidSchedulersMethods.contains(node.methodName) && isInAndroidSchedulers

    val containingMethod = node.getContainingUMethod()
    val shouldIgnore = containingMethod != null && context.evaluator.getAllAnnotations(
      containingMethod as UAnnotated,
      false
    )
      .any { annotation ->
        listOf(
          "dagger.Provides",
          "io.reactivex.annotations.SchedulerSupport"
        ).any { it == annotation.qualifiedName }
      }

    if (true) {
//    if ((isSchedulersMatch) && !shouldIgnore) {
      context.report(
        ISSUE_ANDROID_X_LIFECYCLE_SCOPE,
        node,
        context.getNameLocation(node),
        "Inject this Scheduler instead of calling it directly."
      )
    }
  }
}
