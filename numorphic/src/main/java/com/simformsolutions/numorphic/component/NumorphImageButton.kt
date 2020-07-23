package com.simformsolutions.numorphic.component

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.simformsolutions.numorphic.R
import com.simformsolutions.numorphic.annotation.CornerFamily
import com.simformsolutions.numorphic.annotation.ShapeType
import com.simformsolutions.numorphic.drawable.NumorphShapeDrawable
import com.simformsolutions.numorphic.model.NumorphShapeAppearanceModel
import com.simformsolutions.numorphic.util.NumorphResources

class NumorphImageButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.numorphImageButtonStyle,
    defStyleRes: Int = R.style.Widget_Numorph_ImageButton
) : AppCompatImageButton(context, attrs, defStyleAttr) {

    private var isInitialized: Boolean = false
    private val shapeDrawable: NumorphShapeDrawable

    private var shadowColorLight: Int
    private var shadowColorDark: Int

    private var insetStart = 0
    private var insetEnd = 0
    private var insetTop = 0
    private var insetBottom = 0

    init {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.NumorphImageButton, defStyleAttr, defStyleRes
        )
        val fillColor = a.getColorStateList(R.styleable.NumorphImageButton_numorph_backgroundColor)
        val strokeColor = a.getColorStateList(R.styleable.NumorphImageButton_numorph_strokeColor)
        val strokeWidth = a.getDimension(R.styleable.NumorphImageButton_numorph_strokeWidth, 0f)
        val shapeType =
            a.getInt(R.styleable.NumorphImageButton_numorph_shapeType, ShapeType.DEFAULT)
        val inset = a.getDimensionPixelSize(
            R.styleable.NumorphImageButton_numorph_inset, 0
        )
        val insetStart = a.getDimensionPixelSize(
            R.styleable.NumorphImageButton_numorph_insetStart, -1
        )
        val insetEnd = a.getDimensionPixelSize(
            R.styleable.NumorphImageButton_numorph_insetEnd, -1
        )
        val insetTop = a.getDimensionPixelSize(
            R.styleable.NumorphImageButton_numorph_insetTop, -1
        )
        val insetBottom = a.getDimensionPixelSize(
            R.styleable.NumorphImageButton_numorph_insetBottom, -1
        )
        val shadowElevation = a.getDimension(
            R.styleable.NumorphImageButton_numorph_shadowElevation, 0f
        )
        shadowColorLight = NumorphResources.getColor(
            context, a,
            R.styleable.NumorphImageButton_numorph_shadowColorLight,
            R.color.default_color_shadow_light
        )
        shadowColorDark = NumorphResources.getColor(
            context, a,
            R.styleable.NumorphImageButton_numorph_shadowColorDark,
            R.color.default_color_shadow_dark
        )
        val noShadow = a.getBoolean(
            R.styleable.NumorphImageButton_noShadow, false
        )
        a.recycle()

        shapeDrawable = NumorphShapeDrawable(context, attrs, defStyleAttr, defStyleRes).apply {
            setInEditMode(isInEditMode)
            setShapeType(shapeType)
            setShadowElevation(shadowElevation)
            setShadowColorLight(shadowColorLight)
            setShadowColorDark(shadowColorDark)
            setFillColor(fillColor)
            setStroke(strokeWidth, strokeColor)
            setTranslationZ(translationZ)
        }
        internalSetInset(
            if (insetStart >= 0) insetStart else inset,
            if (insetTop >= 0) insetTop else inset,
            if (insetEnd >= 0) insetEnd else inset,
            if (insetBottom >= 0) insetBottom else inset
        )
        setBackgroundInternal(shapeDrawable)
        setNoShadow(noShadow)
        isInitialized = true
    }

    /**
     * Toggle to show or hide shadow.
     * @param flag true to show
     */
    fun setNoShadow(flag: Boolean) {
        if (flag) {
            hideShadow()
        } else {
            showShadow()
        }
    }

    /**
     * Show shadow.
     */
    fun showShadow() {
        setShadowColorLight(shadowColorLight)
        setShadowColorDark(shadowColorDark)
    }

    /**
     * Hide shadow.
     */
    fun hideShadow() {
        setShadowColorLight(ContextCompat.getColor(context, R.color.transparent))
        setShadowColorDark(ContextCompat.getColor(context, R.color.transparent))
    }

    /**
     * Intercept this method. Native implementation will draw over shadow.
     * @param drawable Drawable to draw
     */
    override fun setImageDrawable(drawable: Drawable?) {
        /**  Call as bitmap  */
        shapeDrawable.setImageBitmap(drawable?.toBitmap())
    }

    /**
     * Intercept this method. Native implementation will draw over shadow.
     * @param bm Bitmap to draw
     */
    override fun setImageBitmap(bm: Bitmap?) {
        shapeDrawable.setImageBitmap(bm)
    }

    override fun setBackground(drawable: Drawable?) {
        setBackgroundDrawable(drawable)
    }

    override fun setBackgroundDrawable(drawable: Drawable?) {
        Log.i(LOG_TAG, "Setting a custom background is not supported.")
    }

    private fun setBackgroundInternal(drawable: Drawable?) {
        super.setBackgroundDrawable(drawable)
    }

    fun setShapeAppearanceModel(shapeAppearanceModel: NumorphShapeAppearanceModel) {
        shapeDrawable.setShapeAppearanceModel(shapeAppearanceModel)
    }

    fun getShapeAppearanceModel(): NumorphShapeAppearanceModel {
        return shapeDrawable.getShapeAppearanceModel()
    }

    fun setBackgroundColor(backgroundColor: ColorStateList?) {
        shapeDrawable.setFillColor(backgroundColor)
    }

    override fun setBackgroundColor(color: Int) {
        shapeDrawable.setFillColor(ColorStateList.valueOf(color))
    }

    fun getBackgroundColor(): ColorStateList? {
        return shapeDrawable.getFillColor()
    }

    fun setStrokeColor(strokeColor: ColorStateList?) {
        shapeDrawable.setStrokeColor(strokeColor)
    }

    fun getStrokeColor(): ColorStateList? {
        return shapeDrawable.getStrokeColor()
    }

    fun setStrokeWidth(strokeWidth: Float) {
        shapeDrawable.setStrokeWidth(strokeWidth)
    }

    fun getStrokeWidth(): Float {
        return shapeDrawable.getStrokeWidth()
    }

    fun setShapeType(@ShapeType shapeType: Int) {
        shapeDrawable.setShapeType(shapeType)
    }

    @ShapeType
    fun getShapeType(): Int {
        return shapeDrawable.getShapeType()
    }

    fun setInset(left: Int, top: Int, right: Int, bottom: Int) {
        internalSetInset(left, top, right, bottom)
    }

    private fun internalSetInset(left: Int, top: Int, right: Int, bottom: Int) {
        var changed = false
        if (insetStart != left) {
            changed = true
            insetStart = left
        }
        if (insetTop != top) {
            changed = true
            insetTop = top
        }
        if (insetEnd != right) {
            changed = true
            insetEnd = right
        }
        if (insetBottom != bottom) {
            changed = true
            insetBottom = bottom
        }

        if (changed) {
            shapeDrawable.setInset(left, top, right, bottom)
            requestLayout()
            invalidateOutline()
        }
    }

    fun setShadowElevation(shadowElevation: Float) {
        shapeDrawable.setShadowElevation(shadowElevation)
    }

    fun getShadowElevation(): Float {
        return shapeDrawable.getShadowElevation()
    }

    fun setShadowColorLight(@ColorInt shadowColor: Int) {
        shapeDrawable.setShadowColorLight(shadowColor)
    }

    fun setShadowColorDark(@ColorInt shadowColor: Int) {
        shapeDrawable.setShadowColorDark(shadowColor)
    }

    override fun setTranslationZ(translationZ: Float) {
        super.setTranslationZ(translationZ)
        if (isInitialized) {
            shapeDrawable.setTranslationZ(translationZ)
        }
    }

    /**
     * Set all corner radius or specific corner radius.
     * @param cornerRadius Set all corner radius
     * @param cornerRadiusTopLeft Set top left corner radius
     * @param cornerRadiusTopRight Set top right corner radius
     * @param cornerRadiusBottomRight Set top right corner radius
     * @param cornerRadiusBottomLeft Set bottom left corner radius
     */
    fun setCorner(
        @Dimension cornerRadius: Float,
        @Dimension cornerRadiusTopLeft: Float = cornerRadius,
        @Dimension cornerRadiusTopRight: Float = cornerRadius,
        @Dimension cornerRadiusBottomRight: Float = cornerRadius,
        @Dimension cornerRadiusBottomLeft: Float = cornerRadius
    ) {
        val newShapeAppearanceModel = NumorphShapeAppearanceModel.builder(getShapeAppearanceModel())
            .setCorner(
                cornerRadius = cornerRadius,
                cornerRadiusTopLeft = cornerRadiusTopLeft,
                cornerRadiusTopRight = cornerRadiusTopRight,
                cornerRadiusBottomRight = cornerRadiusBottomRight,
                cornerRadiusBottomLeft = cornerRadiusBottomLeft
            )
            .build()

        setShapeAppearanceModel(newShapeAppearanceModel)
    }

    /**
     * Set corner family
     * @param cornerFamily Set corner family
     */
    fun setCornerFamily(@CornerFamily cornerFamily: Int) {
        val newShapeAppearanceModel = NumorphShapeAppearanceModel.builder(getShapeAppearanceModel())
            .setCornerFamily(cornerFamily)
            .build()

        setShapeAppearanceModel(newShapeAppearanceModel)
    }

    companion object {
        private const val LOG_TAG = "NumorphImageView"
    }
}
