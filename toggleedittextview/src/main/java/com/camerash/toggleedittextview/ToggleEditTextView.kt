package com.camerash.toggleedittextview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Parcelable
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.transition.TransitionSet
import android.text.InputType
import android.util.AttributeSet
import android.util.SparseArray
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView

/**
 * Created by camerash on 4/26/18.
 * Compound View to switch between Edit Text and Text View
 */
class ToggleEditTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : FrameLayout(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    var textView: TextView
        private set

    var editText: EditText
        private set

    private var editing = false

    init {
        inflate(getContext(), R.layout.view_toggle_edit_text, this)

        textView = findViewById(R.id.text_view)
        editText = findViewById(R.id.edit_text)

        if (attrs != null) {
            val styled = getContext().obtainStyledAttributes(attrs, R.styleable.ToggleEditTextView)

            initAttributes(styled)

            styled.recycle()
        }
    }

    private fun initAttributes(styled: TypedArray) {
        editing = styled.getBoolean(R.styleable.ToggleEditTextView_tetv_editing, false)
        textView.visibility = if (editing) View.GONE else View.VISIBLE
        editText.visibility = if (editing) View.VISIBLE else View.GONE

        val textViewColor = styled.getColor(R.styleable.ToggleEditTextView_tetv_textViewColor, Color.BLACK)
        setTextViewColor(textViewColor)

        val editTextColor = styled.getColor(R.styleable.ToggleEditTextView_tetv_editTextViewColor, Color.BLACK)
        setEditTextColor(editTextColor)

        val editTextBottomLineColor = styled.getColor(R.styleable.ToggleEditTextView_tetv_editTextBottomLineColor, editTextColor)
        setEditTextBottomLineColor(editTextBottomLineColor)

        val size = styled.getDimension(R.styleable.ToggleEditTextView_android_textSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18f, context.resources.displayMetrics))
        setTextSize(size)

        val hint = styled.getString(R.styleable.ToggleEditTextView_android_hint)
        if(hint != null) setHint(hint)

        val inputType = styled.getInteger(R.styleable.ToggleEditTextView_android_inputType, InputType.TYPE_CLASS_TEXT)
        setInputType(inputType)

        val minLines = styled.getInteger(R.styleable.ToggleEditTextView_android_minLines, 0)
        setMinLines(minLines)

        val maxLines = styled.getInteger(R.styleable.ToggleEditTextView_android_maxLines, Integer.MAX_VALUE)
        setMaxLines(maxLines)
    }

    private fun generateTransitionSet(editing: Boolean): TransitionSet {
        val transitionSet = TransitionSet()
        val textViewFade = Fade()
        textViewFade.addTarget(textView)
        val editTextFade = Fade()
        editTextFade.addTarget(editText)

        if(editing) textViewFade.startDelay = FADE_DELAY else editTextFade.startDelay = FADE_DELAY
        return transitionSet.addTransition(textViewFade).addTransition(editTextFade)
    }

    fun setEditing(editing: Boolean, animate: Boolean) {
        if (editing != this.editing) {
            this.editing = editing
            if (!this.editing) textView.text = editText.text // Set editText text to textView

            if(animate) TransitionManager.beginDelayedTransition(this, generateTransitionSet(this.editing))
            textView.visibility = if (this.editing) View.GONE else View.VISIBLE
            editText.visibility = if (this.editing) View.VISIBLE else View.GONE
            editText.isEnabled = this.editing
        }
    }

    fun getEditing(): Boolean = this.editing

    fun getText(): String = editText.text.toString()

    fun setText(text: String) {
        textView.text = text
        editText.setText(text)
    }

    fun getHint(): String = editText.hint.toString()

    fun setHint(hint: String) {
        textView.hint = hint
        editText.hint = hint
    }

    fun getTextSize(): Float = editText.textSize

    fun setTextSize(size: Float) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    fun getTextViewColor(): Int = textView.textColors.defaultColor

    fun setTextViewColor(color: Int) {
        textView.setTextColor(color)
    }

    fun getEditTextColor(): Int = editText.textColors.defaultColor

    fun setEditTextColor(color: Int) {
        editText.setTextColor(color)
    }

    fun getEditTextEnabled(): Boolean = editText.isEnabled

    fun setEditTextEnabled(enable: Boolean) {
        editText.isEnabled = enable
    }

    fun getInputType(): Int = editText.inputType

    fun setInputType(type: Int) {
        editText.inputType = type
    }

    fun setMinLines(minLines: Int) {
        textView.minLines = minLines
        editText.minLines = minLines
    }

    fun setMaxLines(maxLines: Int) {
        textView.maxLines = maxLines
        editText.maxLines = maxLines
    }

    fun setEditTextBottomLineColor(color: Int) {
        editText.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState())
        bundle.putString(TEXT_KEY, getText())
        bundle.putBoolean(EDIT_KEY, getEditing())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        var superState = state
        if (state is Bundle) {
            superState = state.getParcelable(SUPER_STATE_KEY)
            setEditing(state.getBoolean(EDIT_KEY),false)
            setText(state.getString(TEXT_KEY))
        }
        super.onRestoreInstanceState(superState)
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        super.dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        super.dispatchThawSelfOnly(container)
    }

    companion object {
        const val FADE_DELAY = 50L
        const val SUPER_STATE_KEY = "super_state"
        const val TEXT_KEY = "text"
        const val EDIT_KEY = "edit"
    }
}