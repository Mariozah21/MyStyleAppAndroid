package cz.mendelu.pef.mystyleapp.ui.components

sealed class Category(val displayName: String) {
    object TShirts : Category("T-Shirts")
    object Pants : Category("Pants")
    object Dresses : Category("Dresses")
    object Sweaters : Category("Sweaters")
    object Jackets : Category("Jackets")
    object Skirts : Category("Skirts")
    object Shorts : Category("Shorts")
    object Activewear : Category("Activewear")
    object Swimwear : Category("Swimwear")
    object Lingerie : Category("Lingerie")
}