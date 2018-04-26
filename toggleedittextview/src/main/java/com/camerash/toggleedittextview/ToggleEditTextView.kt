package com.camerash.toggleedittextview

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.transition.TransitionManager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView

/**
 * Created by camerash on 4/26/18.
 * Compound View to switch between Edit Text and Text View
 */
class ToggleEditTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int): FrameLayout(context, attrs, defStyleAttr) {

    constructor(context: Context): this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0)

    var textView: TextView
    var editText: EditText

    var editing = false

    init{
        inflate(getContext(), R.layout.view_toggle_edit_text, this)

        textView = findViewById(R.id.text_view)
        editText = findViewById(R.id.edit_text)

        if (attrs != null) {
            val styled = getContext().obtainStyledAttributes(attrs, R.styleable.ToggleEditTextView)

            editing = styled.getBoolean(R.styleable.ToggleEditTextView_editable, false)
            textView.visibility = if(editing) View.INVISIBLE else View.VISIBLE
            editText.visibility = if(editing) View.VISIBLE else View.INVISIBLE

            val textViewColor = styled.getColor(R.styleable.ToggleEditTextView_textViewColor, Color.BLACK)
            textView.setTextColor(textViewColor)

            val editTextColor = styled.getColor(R.styleable.ToggleEditTextView_editTextViewColor, Color.BLACK)
            editText.setTextColor(editTextColor)

            val editTextBottomLineColor = styled.getColor(R.styleable.ToggleEditTextView_editTextBottomLineColor, editTextColor)
            editText.background.setColorFilter(editTextBottomLineColor, PorterDuff.Mode.SRC_ATOP)

            val size = styled.getDimension(R.styleable.ToggleEditTextView_textSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18f, context.resources.displayMetrics))
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)

            val hint = styled.getString(R.styleable.ToggleEditTextView_hint)
            textView.hint = hint
            editText.hint = hint

            editing = styled.getBoolean(R.styleable.ToggleEditTextView_editable, false)

            styled.recycle()
        }
    }

    fun setEditable(editable: Boolean) {
        if(editable != editing) {
            editing = editable
            if(!editing) textView.text = editText.text // Set editText text to textView

            TransitionManager.beginDelayedTransition(this)
            textView.visibility = if(editing) View.INVISIBLE else View.VISIBLE
            editText.visibility = if(editing) View.VISIBLE else View.INVISIBLE
        }
    }

    fun getText(): String = editText.text.toString()

    fun setText(text: String) {
        textView.text = text
        editText.setText(text)
    }

    fun getHint() : String = editText.hint.toString()

    fun setHint(hint: String) {
        textView.hint = hint
        editText.hint = hint
    }

}