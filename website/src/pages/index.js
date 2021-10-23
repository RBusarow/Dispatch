/*
 * Copyright (C) 2021 Rick Busarow
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

import React from "react";
import CodeBlock from '@theme/CodeBlock';
import clsx from "clsx";
import Layout from "@theme/Layout";
import Link from "@docusaurus/Link";
import useDocusaurusContext from "@docusaurus/useDocusaurusContext";
import useBaseUrl from "@docusaurus/useBaseUrl";
import styles from "./styles.module.css";

const features = [
  {
    title: "CoroutineDispatcher Injection",
    code: (
`fun updateUI() = coroutineScope.launchMain { /* ... */ }

val somethingDeferred = coroutineScope.asyncDefault { /* ... */ }

suspend fun fetchData() = withIO { /* ... */ }

val state = flow { /* ... */ }.flowOnIO()
`
    ),
    description: (
        <>
          Change dispatchers without referencing a singleton or injecting a custom object.

          The dispatchers are a property of the <code>CoroutineScope</code> itself, so it's easy to
          inject custom dispatchers for testing.
        </>
    ),
    dest: "docs"
  },
  {
    title: "Test Utilities",
    code: (
`@Test
fun someTest() = runBlocking {

  val scope = TestProvidedCoroutineScope()
  scope.pauseDispatcher()

  // returns a Deferred<Data> using the "IO" dispatcher
  val dataDeferred = SomeClass(scope).getDataFromNetwork()

  dataDeferred.isCompleted shouldBe false

  scope.resumeDispatcher()

  dataDeferred.isCompleted shouldBe true

  scope.cleanupTestCoroutines()
}`
    ),
    description: (
        <>
          Dispatch's <code>TestProvidedCoroutineScope</code> is just a
          <code>TestCoroutineScope</code> under the hood, and every dispatcher is a
          <code>TestCoroutineDispatcher</code>.  This means your tests can have granular control
          over every dispatcher without modifying production code.
        </>
    ),
    dest: "docs/modules/dispatch-test"
  },
  {
    title: "Better Android Lifecycle Awareness",
    code: (
`class MyFragment : Fragment() {

  init {

    // dispatchLifecycleScope is bound to the Fragment lifecycle
    // withViewLifecycle creates a child scope each time the View is created
    dispatchLifecycleScope.withViewLifecycle(this) {
      myViewModel.dataFlow
        .onEach { /* ... */ }
        // starts and stops collection along with the view lifecycle
        .launchOnStart()
    }
  }
}`
    ),
    description: (
        <>
          Dispatch integrates with the Android lifecycle in a way similar to what the
          <code>lifecycle-ktx</code> library does, but with more extensibility.

          The resultant coroutineScopes all contain <code>DispatcherProviders</code> for easy
          integration with Espresso's <code>IdlingResource</code> system.
        </>
    ),
    dest: "docs/modules/dispatch-android-lifecycle"
  },
  {
    title: "Android Espresso",
    code: (
`// Retrieve the DispatcherProvider from a dependency graph,
// so that the same one is used throughout the codebase.
val customDispatcherProvider = testAppComponent.customDispatcherProvider

// Automatically register and unregister Dispatch's IdlingResource
@get:Rule val idlingRule = IdlingDispatcherProviderRule {
  IdlingDispatcherProvider(customDispatcherProvider)
}

@Test
fun testThings() = runBlocking { /* ... */ }`
    ),
    description: (
        <>
          Dispatch will sync all coroutines with Espresso's <code>IdlingRegistry</code>,
          ensuring that Espresso waits until work is done before performing assertions.
        </>
    ),
    dest: "docs/modules/dispatch-android-espresso"
  },
];

function Feature({imageUrl, title, description, code, dest}) {
  const imgUrl = useBaseUrl(imageUrl);
  return (
      <div className={styles.feature}>
        <div className={styles.row}>
          <div className={styles.column}>

         {imgUrl && (
                        <div className="text--center">
                          <img className={styles.featureImage} src={imgUrl} alt={title}/>
                        </div>
                    )}
            <h1 align="center">{title}</h1>
            <p>{description}</p>
            <a href={dest}>Read more</a>
          </div>

          <div className={styles.column}>
            {/*<div class='column'>*/}
            <div>
              <CodeBlock className="language-kotlin">{code}</CodeBlock>
            </div>
          </div>
        </div>

        <hr/>

      </div>
  );
}

function Home() {
  const context = useDocusaurusContext();
  const {siteConfig = {}} = context;
  return (
      <Layout
          //      noFooter={true}
          title={`${siteConfig.title}`}
          description="Description will go into a meta tag in <head />"
      >
        <header className={clsx("hero hero--primary", styles.heroBanner)}>
          <div className="container">
            <h1 className="hero__title">{siteConfig.title}</h1>
            <p className="hero__subtitle">{siteConfig.tagline}</p>
            <div className={styles.buttons}>
              <Link
                  className={clsx(
                      "button button--outline button--secondary button--lg",
                      styles.gettingStartedButton
                  )}
                  to={useBaseUrl("docs/")}
              >
                Get Started
              </Link>


            {/*            <iframe
              src="https://ghbtns.com/github-btn.html?user=rbusarow&repo=tangle&type=star&count=true&size=large"
              frameBorder="0" scrolling="0" width="170" height="30" title="GitHub"/>*/}

            </div>

          </div>
        </header>
        <main>

          {features && features.length > 0 && (
              <section className={styles.features}>
                <div className="container">
                  <div className="row">
                    {features.map((props, idx) => (
                        <Feature key={idx} {...props} />
                    ))}
                  </div>
                </div>
              </section>
          )}
        </main>
      </Layout>
  );
}

export default Home;
