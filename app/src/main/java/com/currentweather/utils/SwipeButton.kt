package com.currentweather.utils

import android.animation.*
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.currentweather.R
import java.util.*


class SwipeButton : RelativeLayout {
    private lateinit var swipeButtonInner: ViewGroup
    private lateinit var swipeButtonBackgroundImage: CardView
    private lateinit var swipeButtonBackgroundText: TextView
    private var initialX = 0f
    var isActive = false
        private set
    private lateinit var centerText: TextView
    private lateinit var background: ViewGroup
    private var disabledDrawable: Drawable? = null
    private var enabledDrawable: Drawable? = null
    private lateinit var onStateChangeListener: (Boolean) -> Unit
    private lateinit var onActiveListener: () -> Unit
    private var collapsedWidth = 0
    private var collapsedHeight = 0
    private var layer: LinearLayout? = null
    private var trailEnabled = false
    private var hasActivationState = false

    var i = 40
    val timer = Timer()

    constructor(context: Context) : super(context) {
        init(context, null, -1, -1)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs, -1, -1)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, -1)
    }

    fun setText(text: String?) {
        centerText.text = text
    }

    override fun setBackground(drawable: Drawable) {
        background.background = drawable
    }

    fun setSlidingButtonBackground(drawable: Drawable?) {
        background.background = drawable
    }

    fun setDisabledDrawable(drawable: Drawable?) {
        disabledDrawable = drawable
        if (!isActive) {
            //swipeButtonInner.setImageDrawable(drawable)
        }
    }

    fun setButtonBackground(buttonBackground: Drawable?) {
        if (buttonBackground != null) {
            //swipeButtonInner.setImageDrawable(buttonBackground)
        }
    }

    fun setEnabledDrawable(drawable: Drawable?) {
        enabledDrawable = drawable
        if (isActive) {
            //swipeButtonInner.setImageDrawable(drawable)
        }
    }

    fun setOnStateChangeListener(onStateChangeListener: (Boolean) -> Unit) {
        this.onStateChangeListener = onStateChangeListener
    }

    fun setOnActiveListener(onActiveListener: () -> Unit) {
        this.onActiveListener = onActiveListener
    }

    fun setInnerTextPadding(left: Int, top: Int, right: Int, bottom: Int) {
        centerText.setPadding(left, top, right, bottom)
    }

    fun setSwipeButtonPadding(left: Int, top: Int, right: Int, bottom: Int) {
        swipeButtonInner.setPadding(left, top, right, bottom)
    }

    fun setHasActivationState(hasActivationState: Boolean) {
        this.hasActivationState = hasActivationState
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        hasActivationState = true
        background = RelativeLayout(context)
        val layoutParamsView = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParamsView.addRule(CENTER_IN_PARENT, TRUE)
        background.let {
            addView(background, layoutParamsView)
            val centerText = TextView(context)
            this.centerText = centerText
            centerText.gravity = Gravity.CENTER
            val layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParams.addRule(CENTER_IN_PARENT, TRUE)
            background.addView(centerText, layoutParams)
            val swipeButton = FrameLayout(context)
            swipeButtonInner = swipeButton
            swipeButtonBackgroundImage = CardView(context).apply {
                radius = 90f
                cardElevation = 0f
            }
            swipeButtonBackgroundText = TextView(context)
            //TODO: Move to property
            swipeButtonBackgroundText.text = "30"
            swipeButtonBackgroundText.setTextColor(Color.BLACK)
            swipeButtonBackgroundText.textSize = 20f
            swipeButtonBackgroundText.typeface = Typeface.DEFAULT_BOLD
            swipeButtonBackgroundText.gravity = Gravity.CENTER


            timer.schedule(object : TimerTask() {
                override fun run() {
                    i--
                    swipeButtonBackgroundText.text = i.toString()
                }
            }, 0, 1000)

            swipeButtonInner.addView(
                swipeButtonBackgroundImage, LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                ).apply { setMargins(20, 20, 20, 20 ) }
            )

            swipeButtonBackgroundImage.addView(
                swipeButtonBackgroundText, LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )

            if (attrs != null && defStyleAttr == -1 && defStyleRes == -1) {
                val typedArray = context.obtainStyledAttributes(
                    attrs, R.styleable.SwipeButton,
                    defStyleAttr, defStyleRes
                )
                collapsedWidth = typedArray.getDimension(
                    R.styleable.SwipeButton_button_image_width,
                    ViewGroup.LayoutParams.WRAP_CONTENT.toFloat()
                ).toInt()
                collapsedHeight = typedArray.getDimension(
                    R.styleable.SwipeButton_button_image_height,
                    ViewGroup.LayoutParams.WRAP_CONTENT.toFloat()
                ).toInt()
                trailEnabled = typedArray.getBoolean(
                    R.styleable.SwipeButton_button_trail_enabled,
                    false
                )
                val trailingDrawable = typedArray.getDrawable(
                    R.styleable.SwipeButton_button_trail_drawable
                )
                val backgroundDrawable = typedArray.getDrawable(
                    R.styleable.SwipeButton_inner_text_background
                )
                if (backgroundDrawable != null) {
                    background.background = backgroundDrawable
                } else {
                    background.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.shape_rounded
                    )
                }
                if (trailEnabled) {
                    layer = LinearLayout(context)
                    layer?.apply {
                        background = if (trailingDrawable != null) {
                            trailingDrawable
                        } else {
                            typedArray.getDrawable(R.styleable.SwipeButton_button_background)
                        }
                        gravity = Gravity.START
                        visibility = View.GONE
                    }
                    background.addView(layer, layoutParamsView)
                }
                centerText.text = typedArray.getText(R.styleable.SwipeButton_inner_text)
                centerText.setTextColor(
                    typedArray.getColor(
                        R.styleable.SwipeButton_inner_text_color,
                        Color.WHITE
                    )
                )
                val textSize = convertPixelsToSp(
                    typedArray.getDimension(R.styleable.SwipeButton_inner_text_size, 0f), context
                )
                if (textSize != 0f) {
                    centerText.textSize = textSize
                } else {
                    centerText.textSize = 12f
                }
                disabledDrawable =
                    typedArray.getDrawable(R.styleable.SwipeButton_button_image_disabled)
                enabledDrawable =
                    typedArray.getDrawable(R.styleable.SwipeButton_button_image_enabled)
                val innerTextLeftPadding = typedArray.getDimension(
                    R.styleable.SwipeButton_inner_text_left_padding, 0f
                )
                val innerTextTopPadding = typedArray.getDimension(
                    R.styleable.SwipeButton_inner_text_top_padding, 0f
                )
                val innerTextRightPadding = typedArray.getDimension(
                    R.styleable.SwipeButton_inner_text_right_padding, 0f
                )
                val innerTextBottomPadding = typedArray.getDimension(
                    R.styleable.SwipeButton_inner_text_bottom_padding, 0f
                )
                val initialState = typedArray.getInt(
                    R.styleable.SwipeButton_initial_state,
                    DISABLED
                )
                if (initialState == ENABLED) {
                    val layoutParamsButton = LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    layoutParamsButton.addRule(ALIGN_PARENT_LEFT, TRUE)
                    layoutParamsButton.addRule(CENTER_VERTICAL, TRUE)
                    //add text
                    //swipeButton.setImageDrawable(enabledDrawable)
                    addView(swipeButton, layoutParamsButton)
                    isActive = true
                } else {
                    val layoutParamsButton =
                        LayoutParams(collapsedWidth, collapsedHeight)
                    layoutParamsButton.addRule(ALIGN_PARENT_LEFT, TRUE)
                    layoutParamsButton.addRule(CENTER_VERTICAL, TRUE)
                    //swipeButton.setImageDrawable(disabledDrawable)
                    addView(swipeButton, layoutParamsButton)
                    isActive = false
                }
                centerText.setPadding(
                    innerTextLeftPadding.toInt(),
                    innerTextTopPadding.toInt(),
                    innerTextRightPadding.toInt(),
                    innerTextBottomPadding.toInt()
                )
                val buttonBackground = typedArray.getDrawable(
                    R.styleable.SwipeButton_button_background
                )
                if (buttonBackground != null) {
                    //swipeButtonBackgroundImage.background = buttonBackground
                } else {
//                    swipeButtonBackgroundImage.background = ContextCompat.getDrawable(
//                        context,
//                        R.drawable.shape_button
//                    )
                }
                val buttonLeftPadding = typedArray.getDimension(
                    R.styleable.SwipeButton_button_left_padding, 0f
                )
                val buttonTopPadding = typedArray.getDimension(
                    R.styleable.SwipeButton_button_top_padding, 0f
                )
                val buttonRightPadding = typedArray.getDimension(
                    R.styleable.SwipeButton_button_right_padding, 0f
                )
                val buttonBottomPadding = typedArray.getDimension(
                    R.styleable.SwipeButton_button_bottom_padding, 0f
                )
                swipeButton.setPadding(
                    buttonLeftPadding.toInt(),
                    buttonTopPadding.toInt(),
                    buttonRightPadding.toInt(),
                    buttonBottomPadding.toInt()
                )
                hasActivationState =
                    typedArray.getBoolean(R.styleable.SwipeButton_has_activate_state, true)
                typedArray.recycle()
            }
            setOnTouchListener(buttonTouchListener)
        }
    }

    private val buttonTouchListener: OnTouchListener
        get() = object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> return !isTouchOutsideInitialPosition(
                        event,
                        swipeButtonInner
                    ).also {
                        if (!it){
                            background.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.shape_rounded_pressed)
                            swipeButtonBackgroundImage.cardElevation = 20f
//                            swipeButtonBackgroundImage.background = ContextCompat.getDrawable(
//                                context,
//                                R.drawable.shape_button_shadow)
                        }
                    }
                    MotionEvent.ACTION_MOVE -> {
                        background.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.shape_rounded_pressed)
                        swipeButtonBackgroundImage.cardElevation = 20f
//                        swipeButtonBackgroundImage.background = ContextCompat.getDrawable(
//                            context,
//                            R.drawable.shape_button_shadow
//                        )
                        if (initialX == 0f) {
                            initialX = swipeButtonInner.x
                        }
                        if (event.x > swipeButtonInner.width / 2 &&
                            event.x + swipeButtonInner.width / 2 < width
                        ) {
                            swipeButtonInner.x = event.x - swipeButtonInner.width / 2
                            centerText.alpha =
                                1 - 1.3f * (swipeButtonInner.x + swipeButtonInner.width) / width
                            setTrailingEffect()
                        }
                        if (event.x + swipeButtonInner.width / 2 > width &&
                            swipeButtonInner.x + swipeButtonInner.width / 2 < width
                        ) {
                            swipeButtonInner.x = width - swipeButtonInner.width.toFloat()
                        }
                        if (event.x < swipeButtonInner.width / 2) {
                            swipeButtonInner.x = 0f
                        }
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        background.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.shape_rounded)
                        swipeButtonBackgroundImage.cardElevation = 0f
//                        swipeButtonBackgroundImage.background = ContextCompat.getDrawable(
//                            context,
//                            R.drawable.shape_button
//                        )
                        if (isActive) {
                            collapseButton()
                        } else {
                            if (swipeButtonInner.x + swipeButtonInner.width > width * 0.9) {
                                if (hasActivationState) {
                                    expandButton()
                                } else if (::onActiveListener.isInitialized) {
                                    onActiveListener.invoke()
                                    moveButtonBack()
                                }
                            } else {
                                moveButtonBack()
                            }
                        }
                        return true
                    }
                }
                return false
            }
        }

    private fun expandButton() {
        val positionAnimator = ValueAnimator.ofFloat(swipeButtonInner.x, 0f)
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            swipeButtonInner.x = x
        }
        val widthAnimator = ValueAnimator.ofInt(
            swipeButtonInner.width,
            width
        )
        widthAnimator.addUpdateListener {
            val params = swipeButtonInner.layoutParams
            params.width = (widthAnimator.animatedValue as Int)
            swipeButtonInner.layoutParams = params
        }
        val animatorSet = AnimatorSet()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                super.onAnimationStart(animation, isReverse)
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                isActive = true
//                swipeButtonInner.setImageDrawable(enabledDrawable)
                timer.cancel()

                swipeButtonBackgroundText.text = "ACCEPTED"
                if (::onStateChangeListener.isInitialized) {
                    onStateChangeListener.invoke(isActive)
                }
                if (::onActiveListener.isInitialized) {
                    onActiveListener.invoke()
                }
            }
        })
        animatorSet.playTogether(positionAnimator, widthAnimator)
        animatorSet.start()
    }

    private fun moveButtonBack() {
        val positionAnimator = ValueAnimator.ofFloat(swipeButtonInner.x, 0f)
        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            swipeButtonInner.x = x
            setTrailingEffect()
        }
        positionAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (layer != null) {
                    layer!!.visibility = View.GONE
                }
            }
        })
        val objectAnimator = ObjectAnimator.ofFloat(
            centerText, "alpha", 1f
        )
        positionAnimator.duration = 200
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimator, positionAnimator)
        animatorSet.start()
    }

    private fun collapseButton() {
        val finalWidth = if (collapsedWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
            swipeButtonInner.height
        } else {
            collapsedWidth
        }
        val widthAnimator =
            ValueAnimator.ofInt(swipeButtonInner.width, finalWidth)
        widthAnimator.addUpdateListener {
            val params = swipeButtonInner.layoutParams
            params.width = (widthAnimator.animatedValue as Int)
            swipeButtonInner.layoutParams = params
            setTrailingEffect()
        }
        widthAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                isActive = false
//                swipeButtonInner.setImageDrawable(disabledDrawable)
                if (::onStateChangeListener.isInitialized) {
                    onStateChangeListener.invoke(isActive)
                }
                if (layer != null) {
                    layer!!.visibility = View.GONE
                }
            }
        })
        val objectAnimator = ObjectAnimator.ofFloat(
            centerText, "alpha", 1f
        )
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimator, widthAnimator)
        animatorSet.start()
    }

    private fun setTrailingEffect() {
        if (trailEnabled) {
            layer!!.visibility = View.VISIBLE
            layer!!.layoutParams = LayoutParams(
                (swipeButtonInner.x + swipeButtonInner.width / 3).toInt(),
                centerText.height
            )
        }
    }

    fun toggleState() {
        if (isActive) {
            collapseButton()
        } else {
            expandButton()
        }
    }

    companion object {
        private const val ENABLED = 0
        private const val DISABLED = 1

        fun convertPixelsToSp(
            px: Float,
            context: Context
        ) = px / context.resources.displayMetrics.scaledDensity

        fun isTouchOutsideInitialPosition(
            event: MotionEvent,
            view: View
        ) = event.x > view.x + view.width
    }
}