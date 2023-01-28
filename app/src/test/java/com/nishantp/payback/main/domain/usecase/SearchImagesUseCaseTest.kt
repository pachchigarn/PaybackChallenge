package com.nishantp.payback.main.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.nishantp.payback.main.data.repository.ImageRepositoryTest
import com.nishantp.payback.main.domain.model.Image
import com.nishantp.payback.utils.DataState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchImagesUseCaseTest {

    private lateinit var getImageDetailsUseCase: GetImageDetailsUseCase
    private lateinit var fakeRepositoryTest: ImageRepositoryTest

    @Before
    fun setUp() {
        fakeRepositoryTest = ImageRepositoryTest()
        getImageDetailsUseCase = GetImageDetailsUseCase(repository = fakeRepositoryTest)
    }

    @Test
    fun `Get Images From Repository`() = runBlocking {
        var query = "fruits"
        val images : DataState<List<Image>> = fakeRepositoryTest.getImages(query = query)

        assertThat(images is DataState.Success).isTrue()

        assertThat((images as DataState.Success).data.isNotEmpty())
    }
}