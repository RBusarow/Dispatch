#!/bin/bash
rm -rf docs/kdoc

./gradlew clean dokka

cp CHANGES.md docs/changes.md

# delete any existing module_* file
find . -type f -name 'module_*' -exec rm -f {} \;

cp android-espresso/README.md docs/module_android-espresso.md
cp android-lifecycle-runtime/README.md docs/module_android-lifecycle-runtime.md
cp android-lifecycle-viewmodel/README.md docs/module_android-lifecycle-viewmodel.md
cp core/README.md docs/module_core.md
cp core-test/README.md docs/module_core-test.md
cp core-test-junit4/README.md docs/module_core-test-junit4.md
cp core-test-junit5/README.md docs/module_core-test-junit5.md
cp extensions/README.md docs/module_extensions.md

# mkdocs gh-pages
# mkdocs serve
# mkdocs serve --no-livereload

#rm docs/index.md
#rm docs/changes.md
#rm docs/android-espresso.md
#rm docs/android-lifecycle-runtime.md
#rm docs/android-lifecycle-viewmodel.md
#rm docs/core.md
#rm docs/core-test.md
#rm docs/extensions.md
