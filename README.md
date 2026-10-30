# Job Comparison UI

An Android app for comparing job offers. You enter your current job and any
offers you are weighing, set how much you care about salary, bonus and four
benefits, and the app ranks them and shows two of them side by side.

Five screens:

- Main menu
- Job details, used for both the current job and new offers
- Settings, six weights from 0 to 9
- Job rankings, best to worst, with the current job marked
- Compare jobs, two jobs side by side with every field and the score

## About this project

I built the user interface for a job comparison app as part of a four person
team project at Georgia Tech. The layouts, the five Activity classes and the
navigation between them were my work.

This repository is a standalone version of that interface. The team's backend
is not here. I wrote a small in memory store instead so the app runs on its
own, which keeps the repository to the part that is mine and keeps the course
project's code out of a public repo.

Because the store is in memory, data is not saved between runs. That is on
purpose. This project is about the interface.

## My role on the original project

The original was a four person team project. I owned the front end and the
testing.

What I built:

- All five screens. The layouts in this repository are the ones I wrote,
  including the field ordering, the scrolling form, and the button layout.
- The five Activity classes and the navigation between them.
- The team's test plan: 20 test cases mapped to the use case model, with the
  adequacy criteria and the bug tracking process.
- The unit test suite for the scoring and validation logic, 14 tests across
  two classes.
- Ran the interface test cases on an emulator and recorded the results.

Two things I found and fixed:

- An input field had no lower bound check, so a value of zero reached a
  division and threw at runtime. I wrote a failing test for it first, then
  added the check.
- The soft keyboard covered the input fields on two screens. The scrolling
  containers were already there, but the window was not resizing when the
  keyboard opened, so the scroll never had room. Fixed in the manifest.

My teammates built the backend: the data model, the manager class, the
application controller, and the SQLite persistence layer. None of their code
is in this repository.

## Why the test suite is not here

The unit tests are not in this repository, and neither is the test plan.

Those tests were written against the course's own specification. They encode
the scoring formula, the valid range for every input, and the expected
behavior of each screen. Georgia Tech reuses this project across semesters
with the same requirements, so publishing the tests would hand future
students a working answer key. The test plan has the same problem.

The tests in `app/src/test` are new ones I wrote against the code in this
repository, which is mine to publish.

If you are evaluating this and want to see the original work, I can walk
through it directly.

## Running it

Open the folder in Android Studio and run it on an emulator or a device.
Requires JDK 17 and API 26 or higher.

From a terminal:

```
./gradlew installDebug
```

## Layout

```
app/src/main/res/layout      the five screens
app/src/main/java/...        Activities, plus Job and JobStore
app/src/test/java/...        unit tests for the store
```

## Scoring

Each job gets a weighted average of its adjusted salary, adjusted bonus and
four benefit amounts. Salary and bonus are scaled to a living cost index of
100 so that pay in different cities compares fairly. Weights of all zero fall
back to treating everything equally.
