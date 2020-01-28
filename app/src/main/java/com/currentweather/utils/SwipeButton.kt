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
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.currentweather.R
import java.util.*

/* Usage example:
         <SwipeButton
            android:id="@+id/swipe_btn"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginStart="20dp"
            android:layout_marginEnd="20dp"
            android:layout_gravity="center_vertical"
            app:inner_text="SWIPE"
            app:inner_text_color="@android:color/white"
            app:inner_text_size="16sp"
            app:inner_text_top_padding="16dp"
            app:inner_text_bottom_padding="16dp"
            app:inner_text_background="@drawable/background_green_shape_rounded"
            app:button_image_height="56dp"
            app:button_image_width="66dp"
            app:animation_end_text="ACCEPTED"
            app:button_image_start="@drawable/ic_double_chevron_solid"
            app:button_image_end="@mipmap/ic_launcher"
            app:button_top_padding="4dp"
            app:button_text_color="#32CC73"
            app:button_bottom_padding="4dp"
            app:button_right_padding="4dp"
            app:button_left_padding="4dp"
            app:image_top_padding="14dp"
            app:image_bottom_padding="14dp"
            app:image_right_padding="14dp"
            app:image_left_padding="14dp"
            app:initial_state="disabled"
            app:button_corner_radius="24dp"
            app:has_activate_state="true" />

 */

class SwipeButton : RelativeLayout {
    private val swipeButtonOuter: ViewGroup = FrameLayout(context)
    private val swipeButtonBackground = CardView(context).apply {
        radius = 90f
        cardElevation = 0f
    }

    private val swipeButtonText = TextView(context).apply {
        textSize = 20f
        typeface = Typeface.DEFAULT_BOLD
        gravity = Gravity.CENTER
    }
    private val swipeButtonImage = ImageView(context)

    private var initialX = 0f

    var isActive = false
        private set

    private val centerText = TextView(context).apply {
        gravity = Gravity.CENTER
    }
    private val background = RelativeLayout(context)
    private var startDrawable: Drawable? = null
    private var endDrawable: Drawable? = null
    private lateinit var onStateChangeListener: (Boolean) -> Unit
    private lateinit var onActiveListener: () -> Unit
    private var collapsedWidth = 0
    private var collapsedHeight = 0
    private var hasActivationState = false

    private var endText = ""

    private val timer = Timer()

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

    /**
     * Sets centered view text
     * @param text text that will be centered on view
     */
    fun setText(text: String) {
        centerText.text = text
    }

    override fun setBackground(drawable: Drawable) {
        background.background = drawable
    }

    /**
     * Drawable to set into swiped collapsed container
     * @param drawable image for collapsed container
     */
    fun setStartDrawable(drawable: Drawable?) {
        startDrawable = drawable
        if (!isActive) {
            swipeButtonBackground.removeAllViews()
            swipeButtonBackground.addView(
                swipeButtonImage, LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            swipeButtonImage.setImageDrawable(drawable)
        }
    }

    /**
     * Drawable to set into swiped expanded container
     * @param drawable image for expanded container
     */
    fun setEndDrawable(drawable: Drawable?) {
        endDrawable = drawable
        if (isActive) {
            swipeButtonImage.setImageDrawable(drawable)
        }
    }

    /**
     * Called every time when state changed
     * @param onStateChangeListener function that have Boolean parameter as indicator of state
     */
    fun setOnStateChangeListener(onStateChangeListener: (Boolean) -> Unit) {
        this.onStateChangeListener = onStateChangeListener
    }

    /**
     * Called every time when state active
     * @param onActiveListener function
     */
    fun setOnActiveListener(onActiveListener: () -> Unit) {
        this.onActiveListener = onActiveListener
    }

    /**
     * Sets timer to swiped button
     * Note: not work if image set
     * @param timesInSeconds seconds for timer
     */
    fun setTimer(timesInSeconds: Int) {
        swipeButtonText.text = timesInSeconds.toString()

        timer.schedule(object : TimerTask() {
            var i = timesInSeconds
            override fun run() {
                swipeButtonText.text = i.toString()
                if (i-- == 0) timer.cancel()
            }
        }, 0, 1000)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        hasActivationState = true

        background.let {
            addView(background, LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).also { it.addRule(CENTER_IN_PARENT, TRUE) })

            background.addView(centerText, LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).also { it.addRule(CENTER_IN_PARENT, TRUE) })


            swipeButtonBackground.addView(
                swipeButtonText, LayoutParams(
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

                background.background = typedArray.getDrawable(
                    R.styleable.SwipeButton_inner_text_background
                ) ?: ContextCompat.getDrawable(
                    context,
                    R.drawable.background_green_shape_rounded
                )

                centerText.apply {
                    text = typedArray.getText(R.styleable.SwipeButton_inner_text)
                    setTextColor(
                        typedArray.getColor(
                            R.styleable.SwipeButton_inner_text_color,
                            Color.WHITE
                        )
                    )
                    textSize = convertPixelsToSp(
                        typedArray.getDimension(R.styleable.SwipeButton_inner_text_size, 12f),
                        context
                    )
                }

                startDrawable =
                    typedArray.getDrawable(R.styleable.SwipeButton_button_image_start)

                endDrawable =
                    typedArray.getDrawable(R.styleable.SwipeButton_button_image_end)

                startDrawable?.let { setStartDrawable(it) }

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
                    addView(swipeButtonOuter, LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).also {
                        it.addRule(ALIGN_PARENT_LEFT, TRUE)
                        it.addRule(CENTER_VERTICAL, TRUE)
                    })
                    isActive = true
                } else {
                    addView(swipeButtonOuter, LayoutParams(collapsedWidth, collapsedHeight).also {
                        it.addRule(ALIGN_PARENT_LEFT, TRUE)
                        it.addRule(CENTER_VERTICAL, TRUE)
                    })
                    isActive = false
                }

                centerText.setPadding(
                    innerTextLeftPadding.toInt(),
                    innerTextTopPadding.toInt(),
                    innerTextRightPadding.toInt(),
                    innerTextBottomPadding.toInt()
                )

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

                val imageLeftPadding = typedArray.getDimension(
                    R.styleable.SwipeButton_image_left_padding, 0f
                )

                val imageTopPadding = typedArray.getDimension(
                    R.styleable.SwipeButton_image_top_padding, 0f
                )

                val imageRightPadding = typedArray.getDimension(
                    R.styleable.SwipeButton_image_right_padding, 0f
                )

                val imageBottomPadding = typedArray.getDimension(
                    R.styleable.SwipeButton_image_bottom_padding, 0f
                )

                val radius = typedArray.getDimension(
                    R.styleable.SwipeButton_button_corner_radius, -1f
                )

                if (radius != -1f) swipeButtonBackground.radius = radius

                val swipeButtonTextColor =
                    typedArray.getColor(R.styleable.SwipeButton_button_text_color, Color.BLACK)
                swipeButtonText.setTextColor(swipeButtonTextColor)

                endText = typedArray.getString(R.styleable.SwipeButton_animation_end_text) ?: ""

                swipeButtonImage.setPadding(
                    imageLeftPadding.toInt(),
                    imageTopPadding.toInt(),
                    imageRightPadding.toInt(),
                    imageBottomPadding.toInt()
                )

                swipeButtonOuter.addView(
                    swipeButtonBackground, LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    ).also {
                        it.setMargins(
                            buttonLeftPadding.toInt(),
                            buttonTopPadding.toInt(),
                            buttonRightPadding.toInt(),
                            buttonBottomPadding.toInt()
                        )
                    }
                )

                hasActivationState =
                    typedArray.getBoolean(R.styleable.SwipeButton_has_activate_state, true)
                typedArray.recycle()
            }
            setOnTouchListener(buttonTouchListener)

            //TODO: remove after debug
            setTimer(35)
        }
    }

    private val buttonTouchListener: OnTouchListener
        get() = object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> return !isTouchOutsideInitialPosition(
                        event,
                        swipeButtonOuter
                    ).also {
                        if (!it) {
                            background.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.background_green_shape_rounded_pressed
                            )
                            swipeButtonBackground.cardElevation = 20f
                        }
                    }
                    MotionEvent.ACTION_MOVE -> {
                        background.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.background_green_shape_rounded_pressed
                        )
                        swipeButtonBackground.cardElevation = 20f
                        if (initialX == 0f) {
                            initialX = swipeButtonOuter.x
                        }
                        if (event.x > swipeButtonOuter.width / 2 &&
                            event.x + swipeButtonOuter.width / 2 < width
                        ) {
                            swipeButtonOuter.x = event.x - swipeButtonOuter.width / 2
                            centerText.alpha =
                                1 - 1.3f * (swipeButtonOuter.x + swipeButtonOuter.width) / width
                        }
                        if (event.x + swipeButtonOuter.width / 2 > width &&
                            swipeButtonOuter.x + swipeButtonOuter.width / 2 < width
                        ) {
                            swipeButtonOuter.x = width - swipeButtonOuter.width.toFloat()
                        }
                        if (event.x < swipeButtonOuter.width / 2) {
                            swipeButtonOuter.x = 0f
                        }
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        background.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.background_green_shape_rounded
                        )
                        swipeButtonBackground.cardElevation = 0f

                        if (isActive) {
                            collapseButton()
                        } else {
                            if (swipeButtonOuter.x + swipeButtonOuter.width > width * 0.9) {
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
        val positionAnimator = ValueAnimator.ofFloat(swipeButtonOuter.x, 0f)
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            swipeButtonOuter.x = x
        }
        val widthAnimator = ValueAnimator.ofInt(
            swipeButtonOuter.width,
            width
        ).apply {
            interpolator = AccelerateDecelerateInterpolator()
        }
        widthAnimator.addUpdateListener {
            val params = swipeButtonOuter.layoutParams
            params.width = (widthAnimator.animatedValue as Int)
            swipeButtonOuter.layoutParams = params
        }

        val textAnimator = ObjectAnimator.ofFloat(
            swipeButtonText, "alpha", 0f
        ).apply {
            var playingOnce = false
            duration = 300
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                    super.onAnimationEnd(animation)
                    timer.cancel()
                    swipeButtonText.text = endText
                    if (!playingOnce) {
                        playingOnce = true
                        ObjectAnimator.ofFloat(
                            swipeButtonText, "alpha", 1f
                        ).apply { duration = 300 }.start()
                    }
                }

            })
        }

        val animatorSet = AnimatorSet()
        animatorSet.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                isActive = true

                endDrawable?.let {
                    swipeButtonBackground.removeAllViews()
                    swipeButtonBackground.addView(
                        swipeButtonImage, LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    )
                    swipeButtonImage.setImageDrawable(it)
                }

                if (::onStateChangeListener.isInitialized) {
                    onStateChangeListener.invoke(isActive)
                }
                if (::onActiveListener.isInitialized) {
                    onActiveListener.invoke()
                }
            }
        })
        animatorSet.playTogether(positionAnimator, widthAnimator, textAnimator)
        animatorSet.start()
    }

    private fun moveButtonBack() {
        val positionAnimator = ValueAnimator.ofFloat(swipeButtonOuter.x, 0f)
        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            swipeButtonOuter.x = x
        }

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
            swipeButtonOuter.height
        } else {
            collapsedWidth
        }
        val widthAnimator =
            ValueAnimator.ofInt(swipeButtonOuter.width, finalWidth)
        widthAnimator.addUpdateListener {
            val params = swipeButtonOuter.layoutParams
            params.width = (widthAnimator.animatedValue as Int)
            swipeButtonOuter.layoutParams = params
        }
        widthAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                isActive = false
                swipeButtonText.text = ""
                startDrawable?.let { setStartDrawable(startDrawable) }
                if (::onStateChangeListener.isInitialized) {
                    onStateChangeListener.invoke(isActive)
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