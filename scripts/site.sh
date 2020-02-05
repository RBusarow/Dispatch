#!/bin/bash
rm -rf docs/kdoc

./gradlew clean dokka

cp CHANGES.md docs/changes.md

mkdir docs/modules

cp android-espresso/README.md docs/modules/android-espresso.md
cp android-lifecycle-runtime/README.md docs/modules/android-lifecycle-runtime.md
cp android-lifecycle-viewmodel/README.md docs/modules/android-lifecycle-viewmodel.md
cp core/README.md docs/modules/core.md
cp core-test/README.md docs/modules/core-test.md
cp extensions/README.md docs/modules/extensions.md

# mkdocs gh-pages
mkdocs serve
# mkdocs serve --no-livereload

#rm docs/index.md
#rm docs/changes.md
#rm docs/android-espresso.md
#rm docs/android-lifecycle-runtime.md
#rm docs/android-lifecycle-viewmodel.md
#rm docs/core.md
#rm docs/core-test.md
#rm docs/extensions.md
