package club.sk1er.vigilance.gui.components

import club.sk1er.elementa.UIComponent
import club.sk1er.elementa.components.UIBlock
import club.sk1er.elementa.components.UIRoundedRectangle
import club.sk1er.elementa.constraints.CenterConstraint
import club.sk1er.elementa.constraints.RelativeConstraint
import club.sk1er.elementa.constraints.animation.Animations
import club.sk1er.elementa.dsl.*
import club.sk1er.elementa.effects.StencilEffect
import club.sk1er.vigilance.data.PropertyData
import java.awt.Color

class Toggle(private val prop: PropertyData) : UIComponent() {
    private var toggled = prop.getValue<Boolean>()
    private var selected = false

    private val slide = UIRoundedRectangle(5f).constrain {
        y = CenterConstraint()
        x = CenterConstraint()
        width = 20.pixels()
        height = 10.pixels()
        color = Color(120, 120, 120, 0).asConstraint()
    }.enableEffects(StencilEffect()) childOf this

    private val slideOn = UIBlock().constrain {
        width = if (toggled) RelativeConstraint() else 0.pixels()
        height = RelativeConstraint()
        color = Color(0, 170, 165, 0).asConstraint()
    } childOf slide

    private val knob = Knob(14, toggled)

    init {
        constrain {
            x = 10.pixels(true)
            y = CenterConstraint()
            width = 30.pixels()
            height = 20.pixels()
        }.onMouseClick { _, _, _ ->
            if (!selected) return@onMouseClick
            toggle()
        }.onMouseEnter {
            if (!selected) return@onMouseEnter
            knob.hover()
        }.onMouseLeave {
            if (!selected) return@onMouseLeave
            knob.unHover()
        }

        if (toggled) knob.setX(15.pixels(true))
        else knob.setX(0.pixels())
        knob childOf this
    }

    fun fadeIn() {
        selected = true
        slide.animate { setColorAnimation(Animations.OUT_EXP, 0.5f, Color(120, 120, 120, 255).asConstraint()) }
        slideOn.animate { setColorAnimation(Animations.OUT_EXP, 0.5f, Color(0, 170, 165, 255).asConstraint()) }
        knob.fadeIn()
    }

    fun fadeOut() {
        selected = false
        slide.animate { setColorAnimation(Animations.OUT_EXP, 0.5f, Color(120, 120, 120, 0).asConstraint()) }
        slideOn.animate { setColorAnimation(Animations.OUT_EXP, 0.5f, Color(0, 170, 165, 0).asConstraint()) }
        knob.fadeOut()
    }

    private fun toggle() {
        knob.click(toggled)
        prop.setValue(toggled)

        if (toggled) {
            toggled = false
            slideOn.animate { setWidthAnimation(Animations.OUT_EXP, 0.5f, 0.pixels()) }

        } else {
            toggled = true
            slideOn.animate { setWidthAnimation(Animations.OUT_EXP, 0.5f, RelativeConstraint()) }
        }
    }
}