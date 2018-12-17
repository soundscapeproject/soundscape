package com.example.dinhh.soundscape.common

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}


//Animation
/**
 * This method animate the view alpha from 0 to 1 and turn it to visible at the same time
 * @param durationInMillis the an animation duration by milliseconds
 */
fun View.fadeIn(durationInMillis: Int = context.resources.getInteger(android.R.integer.config_shortAnimTime)) {
    animate()
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                alpha = 0f
                visibility = View.VISIBLE
            }
        })
        .alpha(1f)
        .apply {
            duration = durationInMillis.toLong()
        }
}

/**
 * Fades Out the given [View]
 *
 * @param durationInMillis [Int] with the duration of the animation in milliseconds. If nothing
 * given, Android config_shortAnimTime used by default
 */
fun View.fadeOut(durationInMillis: Int = context.resources.getInteger(android.R.integer.config_shortAnimTime)) {
    animate()
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                visibility = View.INVISIBLE
            }
        })
        .alpha(0f)
        .duration = durationInMillis.toLong()
}

/**
 * Traverse all the views of the [Activity] and turn it to visible if its id is specified
 * @param visibleViewIds the ids of views that needs to be visible
 */
fun Activity.turnViewsToVisible(visibleViewIds: Set<Int>) {
    val rootView = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
    (rootView as? ViewGroup)?.turnViewsToVisible(visibleViewIds)
}
/**
 * Traverse all the views of the [ViewGroup] and turn it to visible if its id is specified
 * @param visibleViewIds the ids of views that needs to be visible
 */
fun ViewGroup.turnViewsToVisible(visibleViewIds: Set<Int>) {
    for (index in 0 until childCount) {
        if (visibleViewIds.contains(getChildAt(index).id)) {
            getChildAt(index).fadeIn()
        } else {
            getChildAt(index).fadeOut()
        }
    }
}

/**
 * Get the width of the [Activity]
 */
fun Activity.getWidth() : Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

/**
 * Get the height of the [Activity]
 */
fun Activity.getHeight() : Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}

/**
 * This method animate the view's x
 * @param from from which x position does the view animates
 * @param durationInMillis the an animation duration by milliseconds
 */
fun View.translateX(from: Float, durationInMillis: Long) {
    post {
        ValueAnimator.ofFloat(from, x)
            .also {
                it.duration = durationInMillis
                it.interpolator = AccelerateDecelerateInterpolator()
                it.addUpdateListener { a ->
                    this.x = a.animatedValue as Float
                }
                it.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {
                        animation?.cancel()
                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationStart(animation: Animator?) {}
                })
            }
            .start()
    }
}
/**
 * This method animate the view's y
 * @param from from which y position does the view animates
 * @param durationInMillis the an animation duration by milliseconds
 */
fun View.translateY(from: Float, durationInMillis: Long) {
    post {
        ValueAnimator.ofFloat(from, y)
            .also {
                it.duration = durationInMillis
                it.interpolator = AccelerateDecelerateInterpolator()
                it.addUpdateListener { a ->
                    this.y = a.animatedValue as Float
                }
                it.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {
                        animation?.cancel()
                    }

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationStart(animation: Animator?) {}
                })
            }
            .start()
    }
}