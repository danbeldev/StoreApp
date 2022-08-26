package com.example.feature_apps.screen

internal sealed class ProductScreenTestTags(val tag:String) {
    object AppsText:ProductScreenTestTags("apps:text")
    object CountProductsFoundText:ProductScreenTestTags("count_products_found:text")
    object SearchTextField:ProductScreenTestTags("search:text_field")
    object SearchButton:ProductScreenTestTags("search:button")
    object GenreItemLoadingTextShimmer:ProductScreenTestTags("genre_item_loading:text_shimmer")
    object GenresTextButton:ProductScreenTestTags("genres:text_button")
    object GenreLazyRow:ProductScreenTestTags("genre:lazy_row")
    class ProductIconImage(productId:Int):ProductScreenTestTags("product_icon:$productId:image")
    object ProductBaseColumnShimmer:ProductScreenTestTags("product:base_column_shimmer")
    object ProductLazyColumn:ProductScreenTestTags("product:lazy_column")
}