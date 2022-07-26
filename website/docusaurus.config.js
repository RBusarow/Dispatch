/*
 * Copyright (C) 2022 Rick Busarow
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

module.exports = {
  title: "Dispatch",
  tagline: "Simple CoroutineDispatcher injection",
  url: "https://rbusarow.github.io/",
  baseUrl: "/Dispatch/",
  onBrokenMarkdownLinks: "warn",
  favicon: "img/favicon.ico",
  organizationName: "rbusarow", // Usually your GitHub org/user name.
  projectName: "Dispatch", // Usually your repo name.
  themeConfig: {
    docs: {
      sidebar: {
        hideable: true,
      }
    },
    colorMode: {
      defaultMode: "light",
      disableSwitch: false,
      respectPrefersColorScheme: true,
    },
//    announcementBar: {
//      id: "supportus",
//      content:
//        '⭐️ If you like Dispatch, give it a star on <a target="_blank" rel="noopener noreferrer" href="https://github.com/rbusarow/Dispatch/">GitHub</a>! ⭐️',
//    },
    navbar: {
      title: "Dispatch",
      //      logo: {
      //        alt: 'Dispatch Logo',
      //        src: 'img/logo.svg',
      //      },
      items: [
        {
          label: "Docs",
          type: "doc",
          docId: "intro",
          position: "left",
        },
        {
          to: 'CHANGELOG',
          label: 'ChangeLog',
          position: 'left'
        },
        {
          type: "docsVersionDropdown",
          position: "left",
          dropdownActiveClassDisabled: true,
          // dropdownItemsAfter: [
          //   {
          //     to: "/versions",
          //     label: "All versions",
          //   },
          // ],
        },
        {
          label: "Api",
          href: 'pathname:///api/index.html',
          position: "left",
        },
        {
          href: "https://twitter.com/rbusarow",
          className: 'header-twitter-link',
          position: "right",
        },
        {
          href: "https://github.com/rbusarow/Dispatch",
          className: 'header-github-link',
          position: "right",
        },
      ],
    },
    footer: {
      copyright: `Copyright © ${new Date().getFullYear()} Rick Busarow, Built with Docusaurus.`,
    },
    prism: {
      theme: require("prism-react-renderer/themes/github"),
      darkTheme: require("prism-react-renderer/themes/dracula"),
      additionalLanguages: ["kotlin", "groovy", "java"],
    },
  },
  presets: [
    [
      "@docusaurus/preset-classic",
      {
        docs: {
          sidebarPath: require.resolve("./sidebars.js"),
          // Please change this to your repo.
          editUrl: "https://github.com/rbusarow/Dispatch/edit/main/website/",
        },
        blog: {
          showReadingTime: true,
          // Please change this to your repo.
          editUrl: "https://github.com/rbusarow/Dispatch/edit/main/website/",
        },
        theme: {
          customCss: require.resolve("./src/css/custom.css"),
        },
      },
    ],
  ],
};
