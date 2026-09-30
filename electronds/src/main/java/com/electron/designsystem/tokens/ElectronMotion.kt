package com.electron.designsystem.tokens

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing

/**
 * ElectronDS motion tokens.
 *
 * Motion in Electron is fast and decisive, like a circuit closing. It only
 * exists to explain a change: where something came from, what it became,
 * or that an input was received. Nothing loops or bounces for decoration.
 *
 * - Durations (ms): [instant] for press and focus feedback, [quick] for
 *   color and state changes, [standard] for elements moving or resizing,
 *   [emphasized] for elements entering the screen.
 * - Easing: [easeStandard] for on-screen changes, [easeEnter] for elements
 *   appearing (they decelerate into place), [easeExit] for elements leaving
 *   (they accelerate away, so exits feel shorter than entries).
 */
public object ElectronMotion {
    public const val instant: Int = 80
    public const val quick: Int = 150
    public const val standard: Int = 240
    public const val emphasized: Int = 400

    public val easeStandard: Easing = CubicBezierEasing(0.2f, 0f, 0f, 1f)
    public val easeEnter: Easing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1f)
    public val easeExit: Easing = CubicBezierEasing(0.3f, 0f, 0.8f, 0.15f)
}
