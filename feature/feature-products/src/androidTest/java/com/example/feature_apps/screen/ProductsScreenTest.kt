package com.example.feature_apps.screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.product.Genre
import com.example.core_model.data.api.product.GenreItem
import com.example.core_model.data.api.product.ProductItem
import com.example.core_network_domain.responseApi.Result
import com.example.core_ui.theme.MainTheme
import com.example.feature_apps.state.SearchState
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
class ProductsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun productScreen_productNotLoading_companyNotLoading() {

        val products = createProductsTestData()

        composeTestRule.setContent {
            MainTheme(
                darkTheme = true
            ) {
                ProductsScreen(
                    products = createProducts(PagingData.from(products)),
                    company = createCompanies()
                )
            }
        }

        products.forEach { product ->
            // product title is show
            composeTestRule
                .onNodeWithText(product.title)
                .assertExists()

            // product description is show
            composeTestRule
                .onNodeWithText(product.shortDescription)
                .assertExists()

            if (product.icon != null){
                // product icon is show
                composeTestRule
                    .onAllNodesWithTag(
                        testTag = ProductScreenTestTags.ProductIconImage(product.id).tag,
                        useUnmergedTree = true
                    )
                    .onFirst()
                    .assertExists()
            }else {
                // product icon is no show
                composeTestRule
                    .onAllNodesWithTag(
                        testTag = ProductScreenTestTags.ProductIconImage(product.id).tag,
                        useUnmergedTree = true
                    )
                    .onFirst()
                    .assertDoesNotExist()
            }
        }

        // apps text is show
        composeTestRule
            .onNodeWithTag(ProductScreenTestTags.AppsText.tag)
            .assertExists()

        // count products found text is show
        composeTestRule
            .onNodeWithTag(ProductScreenTestTags.CountProductsFoundText.tag)
            .assertExists()
    }

    @Test
    fun productScreen_search_open_state(){
        composeTestRule.setContent {
            MainTheme(
                darkTheme = true
            ) {
                ProductsScreen(
                    products = createProducts(),
                    company = createCompanies(),
                    searchState = SearchState.OPEN
                )
            }
        }

        // search is show
        composeTestRule
            .onNodeWithTag(ProductScreenTestTags.SearchTextField.tag)
            .assertExists()
    }

    @Test
    fun productScreen_search_button_click() {
        composeTestRule.setContent {

            var searchState by remember { mutableStateOf(SearchState.CLOSE) }

            MainTheme(
                darkTheme = true
            ) {
                ProductsScreen(
                    products = createProducts(),
                    company = createCompanies(),
                    searchState = searchState,
                    onSearchState = { searchState = it }
                )
            }
        }

        // search button is show
        // search click open SearchTextField
        composeTestRule
            .onNodeWithTag(ProductScreenTestTags.SearchButton.tag)
            .assertExists()
            .performClick()

        // search text field is show
        composeTestRule
            .onNodeWithTag(ProductScreenTestTags.SearchTextField.tag)
            .assertExists()

        // search button is show
        // search click close SearchTextField
        composeTestRule
            .onNodeWithTag(ProductScreenTestTags.SearchButton.tag)
            .assertExists()
            .performClick()

        // search text field is no show
        composeTestRule
            .onNodeWithTag(ProductScreenTestTags.SearchTextField.tag)
            .assertDoesNotExist()
    }

    @Test
    fun productScreen_genre_succes(){

        val genre = createGenreTestData()

        composeTestRule.setContent {
            MainTheme(
                darkTheme = true
            ) {
                ProductsScreen(
                    products = createProducts(),
                    company = createCompanies(),
                    genre = Result.Success(genre)
                )
            }
        }

        genre.items.forEach { genreItem ->
            composeTestRule
                .onNodeWithText(genreItem.title)
                .assertExists()
        }

        composeTestRule
            .onNodeWithTag(ProductScreenTestTags.GenresTextButton.tag)
            .assertExists()
            .performClick()

        composeTestRule
            .onAllNodes(hasTestTag(ProductScreenTestTags.GenreItemLoadingTextShimmer.tag))
            .onFirst()
            .assertDoesNotExist()
    }

    @Test
    fun productScreen_genre_loading(){

        composeTestRule.setContent {
            MainTheme(
                darkTheme = true
            ) {
                ProductsScreen(
                    products = createProducts(),
                    company = createCompanies(),
                    genre = Result.Loading()
                )
            }
        }

        composeTestRule
            .onAllNodes(hasTestTag(ProductScreenTestTags.GenreItemLoadingTextShimmer.tag))
            .onFirst()
            .assertExists()

        composeTestRule
            .onNodeWithTag(ProductScreenTestTags.GenresTextButton.tag)
            .assertExists()
    }

    @Test
    fun productScreen_genre_error(){

        composeTestRule.setContent {
            MainTheme(
                darkTheme = true
            ) {
                ProductsScreen(
                    products = createProducts(),
                    company = createCompanies(),
                    genre = Result.Error(message = "error")
                )
            }
        }

        composeTestRule
            .onAllNodes(hasTestTag(ProductScreenTestTags.GenreItemLoadingTextShimmer.tag))
            .onFirst()
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithTag(ProductScreenTestTags.GenresTextButton.tag)
            .assertDoesNotExist()
    }

    @Composable
    private fun createProducts(
        pagingData: PagingData<ProductItem> = PagingData.from(createProductsTestData())
    ): LazyPagingItems<ProductItem> = flow {
        emit(pagingData)
    }.collectAsLazyPagingItems()

    @Composable
    private fun createCompanies(
        pagingData: PagingData<CompanyItem> = PagingData.from(createCompanyTestData())
    ): LazyPagingItems<CompanyItem> = flow {
        emit(pagingData)
    }.collectAsLazyPagingItems()
}

private const val PRODUCT_TITLE_1 = "Store App"
private const val PRODUCT_ID_1 = 1
private const val PRODUCT_ICON_1 = "" +
        "https://avatars.mds.yandex.net/i?id=a02e229150a7d286c94519a57f99b5e8-3374823-images-thumbs&n=13"

private const val PRODUCT_TITLE_2 = "Notes"
private const val PRODUCT_ID_2 = 2
private const val PRODUCT_DESCRIPTION_2 = "" +
        "«Кинопо́иск» — крупнейший русскоязычный интернет-сервис о кино."

private fun createProductsTestData():List<ProductItem> = listOf(
    ProductItem(
        id = PRODUCT_ID_1,
        title = PRODUCT_TITLE_1,
        icon = PRODUCT_ICON_1
    ),
    ProductItem(
        id = PRODUCT_ID_2,
        title = PRODUCT_TITLE_2,
        shortDescription = PRODUCT_DESCRIPTION_2
    )
)

private const val COMPANY_TITLE_1 = "Google"
private const val COMPANY_ID_1 = 1

private const val COMPANY_TITLE_2 = "Apple"
private const val COMPANY_ID_2 = 2

private fun createCompanyTestData():List<CompanyItem> = listOf(
    CompanyItem(
        id = COMPANY_ID_1,
        title = COMPANY_TITLE_1
    ),
    CompanyItem(
        id = COMPANY_ID_2,
        title = COMPANY_TITLE_2
    )
)

private const val GENRE_TOTAL = 2
private const val GENRE_ITEM_ID_1 = 1
private const val GENRE_ITEM_TITLE_1 = "Movie"
private const val GENRE_ITEM_ID_2 = 2
private const val GENRE_ITEM_TITLE_2 = "Game"

private fun createGenreTestData():Genre = Genre(
    total = GENRE_TOTAL,
    items = listOf(
        GenreItem(
            id = GENRE_ITEM_ID_1,
            title = GENRE_ITEM_TITLE_1
        ),
        GenreItem(
            id = GENRE_ITEM_ID_2,
            title = GENRE_ITEM_TITLE_2
        )
    )
)