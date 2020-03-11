package com.currentweather.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.currentweather.R


class SubtitledTextView : AppCompatTextView {
    private var popupSuggestionsList = mutableListOf<PopupMenuSuggestion>()
    private var chosenPosition = 0
    private lateinit var onMenuSuggestionListener: (PopupMenuSuggestion) -> Unit

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, androidx.appcompat.R.attr.switchStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.SubtitledTextView, 0, 0)
        try {
            val subtitlesList = attributes.getTextArray(R.styleable.SubtitledTextView_android_entries)
            chosenPosition = attributes.getInt(R.styleable.SubtitledTextView_defaultItem, 0)
            if (!subtitlesList.isNullOrEmpty()) {
                subtitlesList.forEachIndexed { index, title ->
                    popupSuggestionsList.add(PopupMenuSuggestion(index, title.toString()))
                }
                initPopupMenu()
                setSubTitle(popupSuggestionsList[chosenPosition].title)
            }
        } finally {
            attributes.recycle()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun initPopupMenu() {
        this.setOnClickListener { view ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                MenuPopupHelper(context,
                    PopupMenu(context, this).apply {
                        popupSuggestionsList.forEach { menu.add(0, 0, it.position, it.title) }
                        setOnMenuItemClickListener { item ->
                            chosenPosition = item.order
                            setSubTitle(popupSuggestionsList[chosenPosition].title)
                            if (::onMenuSuggestionListener.isInitialized) onMenuSuggestionListener.invoke(getCurrentSuggestion())
                            true
                        }
                    }.menu as MenuBuilder, this).apply {
                    val location = IntArray(2)
                    getLocationOnScreen(location)
                    show(location[0] + paddingStart, 0)
                }
            } else {
                showLegacyPopup(view)
            }
        }
    }

    private fun showLegacyPopup(view: View) {
        val menu = PopupMenu(context, view)
        popupSuggestionsList.forEach { menu.menu.add(0, 0, it.position, it.title) }
        menu.setOnMenuItemClickListener {
            chosenPosition = it.order
            setSubTitle(popupSuggestionsList[chosenPosition].title)
            if (::onMenuSuggestionListener.isInitialized) onMenuSuggestionListener.invoke(getCurrentSuggestion())
            true
        }
        menu.show()
    }


    private fun getSubtitleText(titleText: String, subtitleText: String) = SpannableString(titleText + "\n"
            + subtitleText).apply {
        setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
                titleText.length, titleText.length + subtitleText.length + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun setSubTitle(subtitleText: String) {
        val titleEndIndex = text.indexOf("\n")
        val titleText = text.substring(0, if (titleEndIndex == -1) text.length else titleEndIndex)
        setText(getSubtitleText(titleText, subtitleText), BufferType.SPANNABLE)
    }

    fun setOnMenuSuggestionChangeListener(listener: (PopupMenuSuggestion) -> Unit) {
        this.onMenuSuggestionListener = listener
    }

    fun setEntries(entries: List<String>, defaultItem: Int? = null) {
        defaultItem?.let { chosenPosition = it }
        if (chosenPosition >= entries.size) chosenPosition = 0
        popupSuggestionsList.clear()
        entries.forEachIndexed { index, title ->
            popupSuggestionsList.add(PopupMenuSuggestion(index, title))
        }
        initPopupMenu()
        setSubTitle(popupSuggestionsList[chosenPosition].title)
    }

    fun getCurrentSuggestion(): PopupMenuSuggestion {
        if (popupSuggestionsList.isNotEmpty()) {
            return popupSuggestionsList[chosenPosition]
        }
        return PopupMenuSuggestion(-1, "")
    }

    fun getSuggestionList() = popupSuggestionsList

    data class PopupMenuSuggestion(val position: Int, val title: String)

    interface OnSuggestionChangeListener {
        fun suggestionChanged(suggestion: PopupMenuSuggestion)
    }

}