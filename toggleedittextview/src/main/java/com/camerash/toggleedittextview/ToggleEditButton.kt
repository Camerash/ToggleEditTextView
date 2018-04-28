package com.camerash.toggleedittextview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v7.widget.AppCompatImageButton
import android.util.AttributeSet

/**
 * Created by camerash on 4/26/18.
 * Controller button for controlling single / grouped ToggleEditTextView(s)
 */
class ToggleEditButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int): AppCompatImageButton(context, attrs, defStyleAttr) {

    private val tetvArrayList = arrayListOf<ToggleEditTextView>()
    private val editToConfirmAnim = AnimatedVectorDrawableCompat.create(context, R.drawable.edit_to_confirm_anim)
    private val confirmToEditAnim = AnimatedVectorDrawableCompat.create(context, R.drawable.confirm_to_edit_anim)

    private var onClickListener: OnClickListener? = null

    private var editing = false
    private var animationOffset = 100L

    constructor(context: Context): this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0)

    init {
        if(attrs != null) {
            val styled = getContext().obtainStyledAttributes(attrs, R.styleable.ToggleEditButton)

            initAttributes(styled)

            styled.recycle()
        }

        super.setOnClickListener {
            onClickListener?.onClick(it)
            setEditing(!editing, true)
        }
    }

    private fun initAttributes(styled: TypedArray) {
        val tint = styled.getColor(R.styleable.ToggleEditButton_teb_tint, Color.BLACK)
        setTint(tint)

        val editing = styled.getBoolean(R.styleable.ToggleEditButton_teb_edit, false)
        setEditing(editing, false)

        val offset = styled.getInteger(R.styleable.ToggleEditButton_teb_animationOffset, 100).toLong()
        setAnimationOffset(offset)
    }

    private fun resetButton(animate: Boolean) {
        editToConfirmAnim?.stop()
        confirmToEditAnim?.stop()

        if(animate) {
            setImageDrawable(if (this.editing) editToConfirmAnim else confirmToEditAnim)
            if (this.editing) editToConfirmAnim?.start() else confirmToEditAnim?.start()
        } else {
            setImageDrawable(if (this.editing) confirmToEditAnim else editToConfirmAnim)
        }
    }

    fun getEditing(): Boolean = this.editing

    fun setEditing(editing: Boolean, animate: Boolean) {
        this.editing = editing
        if(animate) {
            tetvArrayList.forEachIndexed { i, tetv ->
                tetv.setEditTextEnabled(this.editing)
                Handler().postDelayed({ tetv.setEditing(this.editing, animate) }, animationOffset*i)
            }
            resetButton(animate)
        } else {
            tetvArrayList.forEach { it.setEditing(this.editing, animate) }
            resetButton(animate)
        }
    }

    fun setTint(color: Int) {
        editToConfirmAnim?.setTint(color)
        confirmToEditAnim?.setTint(color)
    }

    fun bind(vararg toggleEditTextView: ToggleEditTextView) {
        tetvArrayList.addAll(toggleEditTextView)
        tetvArrayList.forEach { it.setEditing(this.editing, false) }
    }

    fun unbind(toggleEditTextView: ToggleEditTextView) {
        tetvArrayList.remove(toggleEditTextView)
    }

    fun unbindAll() {
        tetvArrayList.clear()
    }

    fun getAnimationOffset(): Long = animationOffset

    fun setAnimationOffset(offset: Long) {
        this.animationOffset = offset
    }

    override fun setOnClickListener(l: OnClickListener?) {
        this.onClickListener = l
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState())
        bundle.putBoolean(EDIT_KEY, getEditing())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        var superState = state
        if (state is Bundle) {
            superState = state.getParcelable(SUPER_STATE_KEY)
            setEditing(state.getBoolean(EDIT_KEY),false)
        }
        super.onRestoreInstanceState(superState)
    }

    companion object {
        const val SUPER_STATE_KEY = "super_state"
        const val EDIT_KEY = "edit"
    }
}