package gg.essential.vigilance.data

import gg.essential.vigilance.gui.*
import gg.essential.vigilance.gui.settings.*

class Category(val name: String, val items: List<CategoryItem>, val description: String?) {
    override fun toString(): String {
        return "Category \"$name\"\n ${items.joinToString(separator = "\n") { "\t$it" }}"
    }
}

sealed class CategoryItem {
    abstract fun toSettingsObject(): Setting?
}

class DividerItem(val name: String, private val description: String?) : CategoryItem() {
    override fun toSettingsObject(): Setting {
        return Divider(name, description)
    }

    override fun toString(): String {
        return "Divider \"$name\""
    }
}

class PropertyItem(val data: PropertyData, val subcategory: String) : CategoryItem() {
    override fun toSettingsObject(): Setting {
        val component = when (data.getDataType()) {
            PropertyType.SWITCH -> SwitchComponent(data.getAsBoolean())
            PropertyType.CHECKBOX -> CheckboxComponent(data.getAsBoolean())
            PropertyType.PERCENT_SLIDER -> PercentSliderComponent(data.getValue())
            PropertyType.SLIDER -> SliderComponent(data.getValue(), data.attributes.min, data.attributes.max)
            PropertyType.DECIMAL_SLIDER -> DecimalSliderComponent(
                data.getValue(),
                data.attributes.minF,
                data.attributes.maxF,
                data.attributes.decimalPlaces
            )
            PropertyType.NUMBER -> NumberComponent(
                data.getValue(),
                data.attributes.min,
                data.attributes.max,
                data.attributes.increment
            )
            PropertyType.SELECTOR -> SelectorComponent(data.getValue(), data.attributes.options.toList())
            PropertyType.COLOR -> ColorComponent(data.getValue(), data.attributes.allowAlpha)
            PropertyType.TEXT -> TextComponent(
                data.getValue(),
                data.attributes.placeholder,
                false,
                data.attributes.protected
            )
            PropertyType.PARAGRAPH -> TextComponent(
                data.getValue(),
                data.attributes.placeholder,
                wrap = true,
                protected = false
            )
            PropertyType.BUTTON -> ButtonComponent(data.attributes.placeholder, data)
        }

        return DataBackedSetting(data, component)
    }

    override fun toString(): String {
        return "${data.attributes.type} \"${data.attributes.name}\""
    }
}
