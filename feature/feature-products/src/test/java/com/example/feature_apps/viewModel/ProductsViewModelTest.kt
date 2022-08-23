package com.example.feature_apps.viewModel

import com.example.core_model.data.api.company.Company
import com.example.core_model.data.api.company.CompanyItem
import com.example.core_model.data.api.product.*
import com.example.core_network_domain.responseApi.Result
import com.example.core_network_domain.useCase.company.GetCompanyUseCase
import com.example.core_network_domain.useCase.product.GetCountryProductUseCase
import com.example.core_network_domain.useCase.product.GetGenreProductUseCase
import com.example.core_network_domain.useCase.product.GetProductUseCase
import com.example.core_testing.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `responseGenre method`() = runTest {
        // arrange
        val genre = createGenreTest()

        val getGenreProductUseCase = createGetGenreProductUseCase(Result.Success(genre))
        val viewModel = createViewModel(
            getGenreProductUseCase = getGenreProductUseCase
        )

        // act
        val responseGenre = viewModel.responseGenre.first()

        // assert
        // method getGenreProductUseCase.invoke() must be done once
        coVerify(exactly = 1) {
            getGenreProductUseCase.invoke()
        }
        confirmVerified(getGenreProductUseCase)

        // responseGenre equal success
        Assert.assertTrue(responseGenre is Result.Success)
        // responseGenre data and genre equal
        Assert.assertEquals(responseGenre.data, genre)
    }

    @Test
    fun `responseCountry method`() = runTest {
        // arrange
        val country = createCountryTest()

        val getCountryProductUseCase = createGetCountryProductUseCase(Result.Success(country))
        val viewModel = createViewModel(
            getCountryProductUseCase = getCountryProductUseCase
        )

        // act
        val responseCountry = viewModel.responseCountry.first()

        // assert
        // method getCountryProductUseCase.invoke() must be done once
        coVerify(exactly = 1) {
            getCountryProductUseCase.invoke()
        }
        confirmVerified(getCountryProductUseCase)

        // responseCountry equal success
        Assert.assertTrue(responseCountry is Result.Success)
        // responseCountry data and country equal
        Assert.assertEquals(responseCountry.data, country)
    }

    private fun createGetProductUseCase(
        returns:Product = createProductTest()
    ):GetProductUseCase = mockk(relaxed = true){
        // create impl method invoke()
        coEvery { this@mockk.invoke() } returns returns
    }

    private fun createGetCompanyUseCase(
        returns: Company = createCompanyTest()
    ):GetCompanyUseCase = mockk(relaxed = true){
        // create impl method invoke()
        coEvery { this@mockk.invoke(any()) } returns returns
    }

    private fun createGetGenreProductUseCase(
        returns: Result<Genre> = Result.Success(createGenreTest())
    ):GetGenreProductUseCase = mockk(relaxed = true){
        // create impl method invoke()
        coEvery { this@mockk.invoke() } returns flow { emit(returns) }
    }

    private fun createGetCountryProductUseCase(
        returns: Result<Country> = Result.Success(createCountryTest())
    ):GetCountryProductUseCase = mockk(relaxed = true){
        // create impl method invoke()
        coEvery { this@mockk.invoke() } returns flow { emit(returns) }
    }

    private fun createViewModel(
        getProductUseCase: GetProductUseCase = createGetProductUseCase(),
        getCompanyUseCase: GetCompanyUseCase = createGetCompanyUseCase(),
        getGenreProductUseCase: GetGenreProductUseCase = createGetGenreProductUseCase(),
        getCountryProductUseCase: GetCountryProductUseCase = createGetCountryProductUseCase()
    ):ProductsViewModel = ProductsViewModel(
        getCompanyUseCase = getCompanyUseCase,
        getCountryProductUseCase = getCountryProductUseCase,
        getGenreProductUseCase = getGenreProductUseCase,
        getProductUseCase = getProductUseCase
    )
}

// test data genre
private const val GENRE_TOTAL = 1
private const val GENRE_ITEM_ID = 1
private const val GENRE_ITEM_TITLE = "Movie"

// test data product
private const val PRODUCT_TOTAL = 1
private const val PRODUCT_ITEM_ID = 1
private const val PRODUCT_ITEM_TITLE = "Store App"

// test data company
private const val COMPANY_TOTAL = 1
private const val COMPANY_ITEM_ID = 1
private const val COMPANY_ITEM_TITLE = "Google"

// test data county
private const val COUNTRY_TOTAL = 1
private const val COUNTRY_ITEM_ID = 1
private const val COUNTRY_ITEM_TITLE = "Google"
private const val COUNTRY_ITEM_CONTINENT = "Google"

private fun createGenreTest():Genre = Genre(
    total = GENRE_TOTAL,
    items = listOf(
        GenreItem(
            id = GENRE_ITEM_ID,
            title = GENRE_ITEM_TITLE
        )
    )
)

private fun createProductTest():Product = Product(
    total = PRODUCT_TOTAL,
    items = listOf(
        ProductItem(
            id = PRODUCT_ITEM_ID,
            title = PRODUCT_ITEM_TITLE
        )
    )
)

private fun createCompanyTest():Company = Company(
    total = COMPANY_TOTAL,
    items = listOf(
        CompanyItem(
            id = COMPANY_ITEM_ID,
            title = COMPANY_ITEM_TITLE
        )
    )
)

private fun createCountryTest(): Country = Country(
    total = COUNTRY_TOTAL,
    items = listOf(
        CountryItem(
            id = COUNTRY_ITEM_ID,
            continent = COUNTRY_ITEM_CONTINENT,
            countryTitle = COUNTRY_ITEM_TITLE
        )
    )
)