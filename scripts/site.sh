#!/bin/zsh
rm -rf docs/kdoc

./gradlew clean dokka

rm docs/changes.md
rm docs/index.md

cp README.md docs/index.md
cp CHANGES.md docs/changes.md

# delete any existing module-* file
find docs -type f -name 'module-*' -exec rm -f {} \;

cp android-espresso/README.md docs/module-android-espresso.md
cp android-lifecycle-runtime/README.md docs/module-android-lifecycle-runtime.md
cp android-lifecycle-viewmodel/README.md docs/module-android-lifecycle-viewmodel.md
cp core/README.md docs/module-core.md
cp core-test/README.md docs/module-core-test.md
cp core-test-junit4/README.md docs/module-core-test-junit4.md
cp core-test-junit5/README.md docs/module-core-test-junit5.md
cp extensions/README.md docs/module-extensions.md

./gradlew knit

# mkdocs gh-pages
 mkdocs serve
# mkdocs serve --no-livereload

#rm docs/index.md
#rm docs/android-espresso.md
#rm docs/android-lifecycle-runtime.md
#rm docs/android-lifecycle-viewmodel.md
#rm docs/core.md
#rm docs/core-test.md
#rm docs/extensions.md
