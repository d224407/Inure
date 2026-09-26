# Material 3 UI migration

This branch migrates the reusable interactive UI layer to Material 3 while keeping Inure's existing custom theme engine and business logic.

## Main changes

- Application themes remain on `Theme.Material3.DayNight.NoActionBar`.
- The custom `Switch` now wraps `MaterialSwitch` and preserves the old Inure callback/helpers.
- The custom `CheckBox` now wraps `MaterialCheckBox` and preserves the old Inure callback/helpers.
- `InureRadioButton` now wraps `MaterialRadioButton`.
- `Button` already uses `MaterialButton`; `DynamicRippleButton` was moved from `AppCompatButton` to `MaterialButton`.
- `TypeFaceEditText` now uses `TextInputEditText` as its base class.
- Bottom-sheet dialogs now use Material 3 bottom-sheet themes in both light and dark modes.
- Added shared Material 3 button/card style tokens for future XML screens.

## Compatibility approach

The existing preference screens, adapters and dialogs still reference the same custom Inure view class names and IDs. The migration changes their implementation underneath those APIs instead of replacing every call site.

## Validation

- All application XML resources parse successfully in a static XML parse pass.
- `git diff --check` passes.
- Full Gradle compilation could not be executed in the current environment because Gradle 8.14.3 is not cached and network access to `services.gradle.org` is unavailable.
